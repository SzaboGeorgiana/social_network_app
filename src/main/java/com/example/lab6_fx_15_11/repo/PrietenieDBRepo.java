package com.example.lab6_fx_15_11.repo;


import com.example.lab6_fx_15_11.Validator.Validator;
import com.example.lab6_fx_15_11.domain.Entity;
import com.example.lab6_fx_15_11.domain.Prietenie;
import com.example.lab6_fx_15_11.domain.Tuple;
import com.example.lab6_fx_15_11.domain.Utilizator;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

public class PrietenieDBRepo <E extends Entity<Tuple<Long,Long>>>{
    List<Prietenie> entities;

    private String url;
    private String username;
    private String password;

    private Validator<E> validator;


    public PrietenieDBRepo(Validator<E> validator, String url, String username, String password) {
        this.validator = validator;
        this.entities=new ArrayList<Prietenie>();
        this.url = url;
        this.username = username;
        this.password = password;
    }

    // @Override
//    public Optional<E> findOne(long longID,long lID2) {
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement("select * from prietenie " +
//                     "where id = ?");
//
//        ) {
//            statement.setInt(1, Math.toIntExact((Long) longID));
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//
//                Long id1=resultSet.getLong("id1");
//                Long id2=resultSet.getLong("id2");
//
//                Tuple tu=new Tuple(id1,id2);
//                Entity ent=new Entity<Tuple>();
//                ent.setId(tu);
//
//                Prietenie pr=new Prietenie();
//
//                pr.setId(tu);
//
//                if(entities.contains(pr))
//                    return Optional.of((E) ent);
//                else
//                {
//                    Tuple tu1=new Tuple(id2,id1);
//                    Entity ent1=new Entity<Tuple>();
//                    ent1.setId(tu1);
//                    if(entities.contains(ent1))
//                        return Optional.of((E) ent1);
//
//                }
//
////
////                String firstName = resultSet.getString("first_name");
////                String lastName = resultSet.getString("last_name");
////                Utilizator u = new Utilizator(firstName, lastName);
////                u.setId((Long) longID);
////                return (Optional<E>) Optional.ofNullable(u);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return Optional.empty();
//    }


    public Optional<E> findOne(long id1,long id2)
    {

        Tuple tu=new Tuple(id1,id2);
        Entity ent=new Entity<Tuple>();
        ent.setId(tu);

        Prietenie pr=new Prietenie();

        pr.setId(tu);

        if(entities.contains(pr))
            return Optional.of((E) ent);
        else
        {
            Tuple tu1=new Tuple(id2,id1);
            Entity ent1=new Entity<Tuple>();
            ent1.setId(tu1);
            if(entities.contains(ent1))
                return Optional.of((E) ent1);

        }
        return null;
    }


    public Iterable<E> getAll() {

        return (Iterable<E>) entities;

    }

    //@Override
    public Iterable<E> findAll() {
        //Set<Utilizator> users= new HashSet<>();
        this.entities=new ArrayList<Prietenie>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from prietenie");
             ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
//                Long id = resultSet.getLong("id");
//                String firstName = resultSet.getString("first_name");
//                String lastName = resultSet.getString("last_name");

                Long id1=resultSet.getLong("id1");
                Long id2=resultSet.getLong("id2");
                java.sql.Date dateSQL = resultSet.getDate("friendsfrom");
                LocalDateTime localDate = dateSQL.toLocalDate().atTime(11,11,11);

                Tuple tu=new Tuple(id1,id2);
                Entity ent=new Entity<Tuple>();
                ent.setId(tu);
                Prietenie pr=new Prietenie();

                pr.setId(tu);
                pr.setDate(localDate);


                entities.add( pr);

            }
            return (Iterable<E>) entities;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    // @Override
    public Optional<E> save(E entity) {
        String insertSQL="insert into prietenie (id1,id2,friendsfrom) values(?,?,?)";
        try (var connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement=connection.prepareStatement(insertSQL);)
        {
            Prietenie e= (Prietenie) entity;
            statement.setLong(1,((Prietenie) entity).getId().getLeft());
            statement.setLong(2, ((Prietenie) entity).getId().getRight());
            statement.setObject(3, ((Prietenie) entity).getDate());

            // setDate(3, ((Prietenie) entity).getDate());
            int response=statement.executeUpdate();
            entities.add((Prietenie) entity);
            return response==0 ? Optional.of(entity) : Optional.empty();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // @Override

    public Optional<E> delete(long id1,long id2) {

        String deleteSQL="delete from prietenie where id1=? and id2=?";
        try (var connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteSQL);
        ) {
            statement.setLong(1, (Long) id1);
            statement.setLong(2, (Long) id2);
            Optional<E> foundUser = findOne(id1,id2);

            int response = 0;
            if (foundUser.isPresent()) {
                response = statement.executeUpdate();

            }
            entities.remove(foundUser);
            if(response!=0)
                return  foundUser;

            statement.setLong(1, (Long) id2);
            statement.setLong(2, (Long) id1);
            Optional<E> foundUser1 = findOne(id1,id2);

            int response1 = 0;
            if (foundUser1.isPresent()) {
                response1 = statement.executeUpdate();
            }
            entities.remove(foundUser1);
            return response1 == 0 ? Optional.empty() : foundUser1;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
