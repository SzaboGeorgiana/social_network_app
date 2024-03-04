package com.example.lab6_fx_15_11.repo;






import com.example.lab6_fx_15_11.Validator.Validator;
import com.example.lab6_fx_15_11.domain.*;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

public class MesajeDBRepo <E extends Entity<Tuple<Long,Long>>>{
    List<Mesaje> entities;

    private String url;
    private String username;
    private String password;

    private Validator<E> validator;


    public MesajeDBRepo(Validator<E> validator, String url, String username, String password) {
        this.validator = validator;
        this.entities=new ArrayList<Mesaje>();
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
        this.entities=new ArrayList<Mesaje>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM mesaje");
             ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
//                Long id = resultSet.getLong("id");
//                String firstName = resultSet.getString("first_name");
//                String lastName = resultSet.getString("last_name");

                Long id1=resultSet.getLong("id_de_la");
                Long id2=resultSet.getLong("id_pentru");
                String replay=resultSet.getString("replay");

                java.sql.Timestamp dateSQL = resultSet.getTimestamp("data");
                LocalDateTime localDate = dateSQL.toLocalDateTime();
                String status=resultSet.getString("text");

                Tuple tu=new Tuple(id1,id2);
                Entity ent=new Entity<Tuple>();
                ent.setId(tu);
                Mesaje pr=new Mesaje();

                pr.setId(tu);
                pr.setDate(localDate);
                pr.setText(status);
                pr.setReplay(replay);
                entities.add( pr);

            }
            return (Iterable<E>) entities;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    // @Override
    public Optional<E> save(E entity,int ok) {
        if(ok == 1) {
            String insertSQL = "insert into Mesaje (id_de_la,id_pentru,replay,text,data) values(?,?,?,?,?)";
            try (var connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement statement = connection.prepareStatement(insertSQL);) {
                Mesaje e = (Mesaje) entity;
                statement.setLong(1, (entity).getId().getLeft());
                statement.setLong(2, (entity).getId().getRight());

                statement.setString(3, ((Mesaje) entity).getReplay());
                statement.setObject(5, ((Mesaje) entity).getDate());
                statement.setString(4, ((Mesaje) entity).getText());

                // setDate(3, ((Prietenie) entity).getDate());
                int response = statement.executeUpdate();
                entities.add((Mesaje) entity);
                return response == 0 ? Optional.of(entity) : Optional.empty();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            String insertSQL = "insert into Mesaje (id_de_la,id_pentru,text,data) values(?,?,?,?)";
         try (var connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(insertSQL);) {
            Mesaje e = (Mesaje) entity;
            statement.setLong(1, (entity).getId().getLeft());
            statement.setLong(2, (entity).getId().getRight());

          //  statement.setLong(3, ((Mesaje) entity).getReplay());
            statement.setObject(4, ((Mesaje) entity).getDate());
            statement.setString(3, ((Mesaje) entity).getText());

            // setDate(3, ((Prietenie) entity).getDate());
            int response = statement.executeUpdate();
            entities.add((Mesaje) entity);
            return response == 0 ? Optional.of(entity) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }

//    public E update(E entity) {
//        if(entity==null)
//        {
//            throw new IllegalArgumentException("Entity cannot be null!");
//        }
//        String updateSQL="update \"Request\" set trimisa_in=?,status=? where id1=? and id2=?";
//        try(var connection= DriverManager.getConnection(url, username, password);
//            PreparedStatement statement=connection.prepareStatement(updateSQL);)
//        {
//            Request u= (Request) entity;
//            statement.setObject(1,u.getDate());
//            statement.setString(2,u.getStatus());
//            statement.setLong(3,u.getId().getLeft());
//            statement.setLong(4,u.getId().getRight());
//
//            int response= statement.executeUpdate();
//            return response==0 ? entity : null;
//        }
//        catch (SQLException e)
//        {
//            throw new RuntimeException(e);
//        }
//
//    }

}
