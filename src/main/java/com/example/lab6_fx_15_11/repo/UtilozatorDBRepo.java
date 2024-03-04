package com.example.lab6_fx_15_11.repo;


import com.example.lab6_fx_15_11.Validator.Validator;
import com.example.lab6_fx_15_11.domain.Entity;
import com.example.lab6_fx_15_11.domain.Utilizator;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

//import org.mindrot.jbcrypt.BCrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UtilozatorDBRepo <ID, E extends Entity<ID>> extends InMemoryRepo<ID,E> {
    Set<Utilizator> users;
    private String url;
    private String username;
    private String password;

//    private Validator<User> validator;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UtilozatorDBRepo(Validator<E> validator, String url, String username, String password) {
        super(validator);
        //   super();
        this.users = new HashSet<>();
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<E> findOne(ID longID) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from users " +
                     "where id = ?");

        ) {
            statement.setInt(1, Math.toIntExact((Long) longID));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String pass = resultSet.getString("password");

                Utilizator u = new Utilizator(firstName, lastName,pass);
                u.setId((Long) longID);
                return (Optional<E>) Optional.ofNullable(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

//    private String hashPassword(String password) {
//        return BCrypt.hashpw(password, BCrypt.gensalt());
//    }
//
//    // Metoda pentru verificarea parolei
//    private boolean verifyPassword(String candidate, String hashedPassword) {
//        return BCrypt.checkpw(candidate, hashedPassword);
//    }

    // Metoda pentru criptarea parolei
    public static String hashPassword(String password) {
        return encoder.encode(password);

    }

//     Metoda pentru verificarea parolei
    public static boolean verifyPassword(String candidate, String hashedPassword) {
        return encoder.matches(candidate, hashedPassword);
    }


    // Metoda pentru autentificare
    @Override
    public Optional<E> authenticate(Utilizator u, String pass) {
        try (Connection connection = DriverManager.getConnection(url, username, this.password);
             PreparedStatement statement = connection.prepareStatement("select * from users where id = ?")) {
            Long longID=u.getId();
            statement.setInt(1, Math.toIntExact((Long) longID));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password"); // coloana pentru parola criptată
                if (verifyPassword(pass, hashedPassword)) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    Utilizator ut = new Utilizator(firstName, lastName,pass);
                    u.setId(resultSet.getLong("id"));
                    return Optional.ofNullable((E) u);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<E> save(E entity) {
        String insertSQL = "insert into users ( password, first_name, last_name) values(?,?,?)";
        try (var connection = DriverManager.getConnection(url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(insertSQL);) {

            Utilizator user = (Utilizator) entity;
            statement.setString(1, hashPassword(user.getPassword())); // Criptare parolă
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());

            int response = statement.executeUpdate();
            users.add((Utilizator) entity);
            return response == 0 ? Optional.of(entity) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Utilizator getOne(ID longID) {
        for(Utilizator u:users){
            if(u.getId()==longID)
                return u;
        }
        return null;
    }

    public Iterable<Utilizator> getall_repo() {
        return (Iterable<Utilizator>) users;
    }

    @Override
    public Iterable<E> findAll() {
        // Set <Utilizator> users= new HashSet<>();
        users.clear();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from users");
             ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String pass = resultSet.getString("password");

                Utilizator user = new Utilizator(firstName, lastName,pass);
                user.setId(id);
                if(!users.contains(user)){
                    // user.setId(id);
                    users.add( user);
                }

            }
            return (Iterable<E>) users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void actualizare_prieteni(ID id1,ID id2){
        // Variabilă pentru a stoca rezultatul căutării
        Utilizator e1=null;
        Utilizator e2=null;
        // Iterare prin Set pentru a găsi elementul
        for (Utilizator element : users) {
            if (element.getId().equals(id1)) {
                e1 = element;
            }
            if (element.getId().equals(id2)) {
                e2 = element;
            }
        }
        e1.addFriend(e2);
    }

//
//    @Override
//    public Optional<E> save(E entity) {
//        String insertSQL="insert into users (first_name,last_name) values(?,?)";
//        try (var connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement=connection.prepareStatement(insertSQL);)
//        {
//            Utilizator e= (Utilizator) entity;
//            statement.setString(1,((Utilizator) entity).getFirstName());
//            statement.setString(2, ((Utilizator) entity).getLastName());
//            int response=statement.executeUpdate();
//            users.add((Utilizator) entity);
//            return response==0 ? Optional.of(entity) : Optional.empty();
//        }
//        catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    @Override
    public Optional<E> delete(ID aLong) {
        if (aLong == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        String deleteSQL="delete from users where id=?";
        try (var connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteSQL);
        ) {
            statement.setLong(1, (Long) aLong);

            Optional<E> foundUser = findOne(aLong);

            int response = 0;
            if (foundUser.isPresent()) {
                response = statement.executeUpdate();
            }
            users.remove(foundUser);
            return response == 0 ? Optional.empty() : foundUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public E update(E entity) {
        if(entity==null)
        {
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        String updateSQL="update users set first_name=?,last_name=?, password=? where id=?";
        try(var connection= DriverManager.getConnection(url, username, password);
            PreparedStatement statement=connection.prepareStatement(updateSQL);)
        {
            Utilizator u= (Utilizator) entity;
            statement.setString(1,u.getFirstName());
            statement.setString(2,u.getLastName());
            statement.setLong(4,u.getId());
            statement.setString(3, hashPassword(u.getPassword())); // Criptare parolă

            int response= statement.executeUpdate();
            return response==0 ? entity : null;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }

}