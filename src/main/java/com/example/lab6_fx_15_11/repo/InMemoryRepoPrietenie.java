package com.example.lab6_fx_15_11.repo;


import com.example.lab6_fx_15_11.domain.Entity;
import com.example.lab6_fx_15_11.Validator.*;
import com.example.lab6_fx_15_11.domain.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryRepoPrietenie<E extends Entity<Tuple<Long,Long>>> {
    private Validator<E> validator;
    List<E> entities;

    public InMemoryRepoPrietenie(Validator<E> validator) {
        this.validator = validator;
        //entities=new HashMap<ID,E>();
        this.entities=new ArrayList<E>();
    }

    public E findOne(Long id1,Long id2){
        Tuple tu=new Tuple(id1,id2);
        Entity ent=new Entity<Tuple>();
        ent.setId(tu);
        if(entities.contains(ent))
            return (E) ent;
        else
        {
            Tuple tu1=new Tuple(id2,id1);
            Entity ent1=new Entity<Tuple>();
            ent1.setId(tu1);
            if(entities.contains(ent1))
                return (E) ent1;

        }
        return null;
    }


    public Iterable<E> findAll() {
        return entities;
    }

    public E save(E entity) {
        if (entity==null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
//        if(entities.get(entity.getId()) != null) {
//            return entity;
//        }

        entities.add(entity);

        return null;
    }

    public E delete(long id1,long id2) {
        E e=findOne(id1,id2);
        entities.remove(e);
        return e;
    }

//    @Override
//    public E update(E entity) {
//
//        if(entity == null)
//            throw new IllegalArgumentException("entity must be not null!");
//        validator.validate(entity);
//
//        entities.put(entity.getId(),entity);
//
//        if(entities.get(entity.getId()) != null) {
//            entities.put(entity.getId(),entity);
//            return null;
//        }
//        return entity;
//
//    }
}

