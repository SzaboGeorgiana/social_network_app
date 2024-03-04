package com.example.lab6_fx_15_11.repo;



import com.example.lab6_fx_15_11.domain.Entity;
import com.example.lab6_fx_15_11.Validator.*;
import com.example.lab6_fx_15_11.domain.Utilizator;
import com.example.lab6_fx_15_11.repo.paging.Page;
import com.example.lab6_fx_15_11.repo.paging.Pageable;
import com.example.lab6_fx_15_11.repo.paging.Paginator;
import com.example.lab6_fx_15_11.repo.paging.PagingRepository;

import java.util.*;


public abstract class InMemoryRepo<ID, E extends Entity<ID>> implements PagingRepository<ID,E> {
    private Validator<E> validator;
    Map<ID,E> entities;

    public InMemoryRepo(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<ID,E>();
    }

    @Override
    public Optional<E> findOne(ID id){
        if (id==null)
            throw new IllegalArgumentException("id must be not null");
        return Optional.ofNullable(entities.get(id));
    }

    //  public Optional<E> findOne(Long longID);

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        Paginator<E> paginator = new Paginator<E>(pageable, this.findAll());
        return paginator.paginate();
    }

//    @Override
//    public E save(E entity) {
//        if (entity==null)
//            throw new IllegalArgumentException("entity must be not null");
//
//        validator.validate(entity);
//
//        if(entities.get(entity.getId()) != null) {
//            return entity;
//        }
//
//        else
//        {
//            entities.put(entity.getId(),entity);
//        }
//        return null;
//    }

//    @Override
//    public E delete(ID id) {
//        E e=findOne(id);
//        if(e!=null) {
//            entities.remove(id);
//        }
//        return e;
//    }

//    @Override
//    public E update(E entity) {
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
//    }

    @Override
    public E update(E entity) {
        if(entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);
        if(entities.get(entity.getId()) != null) {
            entities.put(entity.getId(),entity);
            return null;
        }
        return entity;

    }

    public void actualizare_prieteni(ID id1,ID id2){

        Utilizator e1=  (Utilizator) entities.get(id1);
        Utilizator e2=  (Utilizator) entities.get(id2);
        e1.addFriend(e2);

    }
    public void dell_prieteni(ID id1,ID id2){

        Utilizator e1=  (Utilizator) entities.get(id1);
        Utilizator e2=  (Utilizator) entities.get(id2);
    }


    @Override public Optional<E> save(E entity)
    {
        if (entity == null)
        {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }
    @Override public Optional<E> delete(ID id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    public Iterable<Utilizator> getall_repo() {
        return (Iterable<Utilizator>) entities.values();
    }
    public Utilizator getOne(ID id){
        if (id==null)
            throw new IllegalArgumentException("id must be not null");
        return (Utilizator) entities.get(id);
    }

    public Optional<E> authenticate(Utilizator u, String pass) {
        return Optional.empty();
    }
}

