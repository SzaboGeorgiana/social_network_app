package com.example.lab6_fx_15_11.service;



import com.example.lab6_fx_15_11.domain.*;
import com.example.lab6_fx_15_11.repo.MesajeDBRepo;
import com.example.lab6_fx_15_11.repo.PrietenieDBRepo;
import com.example.lab6_fx_15_11.repo.RequestDBRepo;
import com.example.lab6_fx_15_11.utils.ChangeEventType;
import com.example.lab6_fx_15_11.utils.MessageTaskChangeEvent;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Long.parseLong;

public class MesajeSrv<E extends Entity<Tuple<Long,Long>>,ID> {


    MesajeDBRepo repo;
    public MesajeSrv(MesajeDBRepo repo) {
        this.repo = repo;
    }

    public void add(Long id1, Long id2, LocalDateTime d,String text,String replay) {


        Tuple tuple=new Tuple(id1,id2);
        Mesaje u1=new Mesaje();
        u1.setId(tuple);
        // u1.getId().setLeft(id1);
        //u1.getId().setRight(id2);
        u1.setDate(d);
        u1.setText(text);
        int ok=0;
        if (replay!=null){
             u1.setReplay(replay);
             ok=1;
        }
        repo.save(u1,ok);
    }


//    public void mod_ap( long id1, long id2, String str) {
//        Tuple tuple=new Tuple(id1,id2);
//        Request u1=new Request();
//        u1.setId(tuple);
//        // u1.getId().setLeft(id1);
//        //u1.getId().setRight(id2);
//        u1.setDate(LocalDateTime.now());
//        u1.setStatus(str);
//        // repo.save(u1);
//        repo.update(u1);
//    }



    public Iterable<E> find_all_srv() {
        return repo.findAll();

    }
    public Iterable<E> get_all_srv() {
        return repo.getAll();

    }
    public boolean findMesaj(Long id1, Long id2){

        Optional e=repo.findOne(id1,id2);
        if(e==null)
            return false;
        else
            return true;
    }

}



