package com.example.lab6_fx_15_11.repo;




import com.example.lab6_fx_15_11.Validator.Validator;
import com.example.lab6_fx_15_11.domain.*;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

public class RequestDBRepo <E extends Entity<Tuple<Long,Long>>>{
    List<Request> entities;

    private String url;
    private String username;
    private String password;

    private Validator<E> validator;


    public RequestDBRepo(Validator<E> validator, String url, String username, String password) {
        this.validator = validator;
        this.entities=new ArrayList<Request>();
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Optional<E> findOne(long id1,long id2)
    {

        Tuple tu=new Tuple(id1,id2);
        Entity ent=new Entity<Tuple>();
        ent.setId(tu);

        Prietenie pr=new Prietenie();

        pr.setId(tu);

        if(entities.contains(pr))
            return Optional.of((E) ent);
//        else
//        {
//            Tuple tu1=new Tuple(id2,id1);
//            Entity ent1=new Entity<Tuple>();
//            ent1.setId(tu1);
//            if(entities.contains(ent1))
//                return Optional.of((E) ent1);
//
//        }
        return null;
    }


    public Iterable<E> getAll() {

        return (Iterable<E>) entities;

    }

    //@Override
    public Iterable<E> findAll() {
        //Set<Utilizator> users= new HashSet<>();
        this.entities=new ArrayList<Request>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"Request\"\n");
             ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
//                Long id = resultSet.getLong("id");
//                String firstName = resultSet.getString("first_name");
//                String lastName = resultSet.getString("last_name");

                Long id1=resultSet.getLong("id1");
                Long id2=resultSet.getLong("id2");
                java.sql.Date dateSQL = resultSet.getDate("trimisa_in");
                LocalDateTime localDate = dateSQL.toLocalDate().atTime(11,11,11);
                String status=resultSet.getString("status");

                Tuple tu=new Tuple(id1,id2);
                Entity ent=new Entity<Tuple>();
                ent.setId(tu);
                Request pr=new Request();

                pr.setId(tu);
                pr.setDate(localDate);
                pr.setStatus(status);

                entities.add( pr);

            }
            return (Iterable<E>) entities;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    // @Override
    public Optional<E> save(E entity) {
        String insertSQL="insert into \"Request\" (id1,id2,trimisa_in,status) values(?,?,?,?)";
        try (var connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement=connection.prepareStatement(insertSQL);)
        {
            Request e= (Request) entity;
            statement.setLong(1,((Request) entity).getId().getLeft());
            statement.setLong(2, ((Request) entity).getId().getRight());
            statement.setObject(3, ((Request) entity).getDate());
            statement.setString(4, ((Request) entity).getStatus());

            // setDate(3, ((Prietenie) entity).getDate());
            int response=statement.executeUpdate();
            entities.add((Request) entity);
            return response==0 ? Optional.of(entity) : Optional.empty();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public E update(E entity) {
        if(entity==null)
        {
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        String updateSQL="update \"Request\" set trimisa_in=?,status=? where id1=? and id2=?";
        try(var connection= DriverManager.getConnection(url, username, password);
            PreparedStatement statement=connection.prepareStatement(updateSQL);)
        {
            Request u= (Request) entity;
            statement.setObject(1,u.getDate());
            statement.setString(2,u.getStatus());
            statement.setLong(3,u.getId().getLeft());
            statement.setLong(4,u.getId().getRight());

            int response= statement.executeUpdate();
            return response==0 ? entity : null;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }



    // @Override

//    public Optional<E> delete(long id1,long id2) {
//
//        String deleteSQL="delete from prietenie where id1=? and id2=?";
//        try (var connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement(deleteSQL);
//        ) {
//            statement.setLong(1, (Long) id1);
//            statement.setLong(2, (Long) id2);
//            Optional<E> foundUser = findOne(id1,id2);
//
//            int response = 0;
//            if (foundUser.isPresent()) {
//                response = statement.executeUpdate();
//
//            }
//            entities.remove(foundUser);
//            if(response!=0)
//                return  foundUser;
//
//            statement.setLong(1, (Long) id2);
//            statement.setLong(2, (Long) id1);
//            Optional<E> foundUser1 = findOne(id1,id2);
//
//            int response1 = 0;
//            if (foundUser1.isPresent()) {
//                response1 = statement.executeUpdate();
//            }
//            entities.remove(foundUser1);
//            return response1 == 0 ? Optional.empty() : foundUser1;
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }


}
