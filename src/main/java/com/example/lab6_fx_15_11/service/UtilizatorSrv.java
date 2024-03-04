package com.example.lab6_fx_15_11.service;

import com.example.lab6_fx_15_11.domain.Entity;
import com.example.lab6_fx_15_11.domain.Utilizator;
import com.example.lab6_fx_15_11.repo.AbstractFileRepo;
import com.example.lab6_fx_15_11.repo.InMemoryRepo;
import com.example.lab6_fx_15_11.repo.paging.Page;
import com.example.lab6_fx_15_11.repo.paging.Pageable;
import com.example.lab6_fx_15_11.utils.ChangeEventType;
import com.example.lab6_fx_15_11.utils.MessageTaskChangeEvent;
import com.example.lab6_fx_15_11.utils.Observable;
import com.example.lab6_fx_15_11.utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilizatorSrv <ID, E extends Entity<ID>> implements Observable<MessageTaskChangeEvent> {

    private List<Observer<MessageTaskChangeEvent>> observers=new ArrayList<>();

    InMemoryRepo repo;
    public UtilizatorSrv(InMemoryRepo repo) {
        this.repo = repo;
    }

    public void add(String nume, String prenume, long id, String pass) {
        Utilizator u1=new Utilizator(prenume, nume,pass);
        u1.setId(id);
        Optional u=repo.save(u1);
        if(u==Optional.empty())
        {
            notifyObservers(new MessageTaskChangeEvent(ChangeEventType.ADD,u1));
        }

    }
    public Optional<E> valid(String pass,Utilizator u) {
        return   repo.authenticate(u,pass);

    }

    public void mod(String nume, String prenume, long id, String pass) {
        Utilizator u1=new Utilizator(prenume, nume,pass);
        u1.setId(id);
        Utilizator u= (Utilizator) repo.update(u1);
        if(u == null) {
            notifyObservers(new MessageTaskChangeEvent(ChangeEventType.UPDATE,u));
        }
    }

    @Override
    public void addObserver(Observer<MessageTaskChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<MessageTaskChangeEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers(MessageTaskChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    public Optional<Utilizator> dell(long id) {
        Optional <Utilizator> e=repo.delete(id);

        if(e != null) {
            notifyObservers(new MessageTaskChangeEvent(ChangeEventType.DELETE,e.get()));
        }
        return e;
    }

    public Iterable<Utilizator> find_all_srv() {
        return repo.findAll();

    }

    public Page<Utilizator> find_all(Pageable pageable) {
        return (Page<Utilizator>) repo.findAll(pageable);

    }
    public Iterable<Utilizator> get_all_srv() {
        return repo.getall_repo();

    }
    public Optional<E> findOneSrv(ID id){
        return (Optional<E>) repo.findOne(id);
    }

    public Utilizator getOneSrv(ID id){
        return repo.getOne(id);
    }
    public void actualizare_prieteni(ID id1,ID id2){
        repo.actualizare_prieteni(id1,id2);
        repo.actualizare_prieteni(id2,id1);

    }
    public void dell_prieteni(ID id1,ID id2){
        repo.dell_prieteni(id1,id2);
        repo.dell_prieteni(id2,id1);

    }




}
