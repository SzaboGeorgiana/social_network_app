package com.example.lab6_fx_15_11.service;

import com.example.lab6_fx_15_11.domain.Entity;
import com.example.lab6_fx_15_11.domain.Prietenie;
import com.example.lab6_fx_15_11.domain.Tuple;
import com.example.lab6_fx_15_11.repo.PrietenieDBRepo;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Long.parseLong;

public class PrietenieSrv<E extends Entity<Tuple<Long,Long>>,ID> {


    PrietenieDBRepo repo;
    public PrietenieSrv(PrietenieDBRepo repo) {
        this.repo = repo;
    }

    public void add(Long id1, Long id2, LocalDateTime d) {


        Tuple tuple=new Tuple(id1,id2);
        Prietenie u1=new Prietenie();
        u1.setId(tuple);
        // u1.getId().setLeft(id1);
        //u1.getId().setRight(id2);
        u1.setDate(d);
        repo.save(u1);
    }

    public Optional dell(long id1, long id2) {
        Optional e=repo.delete(id1,id2);
        return e;
    }

    public Iterable<E> find_all_srv() {
        return repo.findAll();

    }
    public Iterable<E> get_all_srv() {
        return repo.getAll();

    }
    public boolean findPrietenie(Long id1, Long id2){

        Optional e=repo.findOne(id1,id2);
        if(e==null)
            return false;
        else
            return true;
    }

}



