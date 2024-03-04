package com.example.lab6_fx_15_11.utils;




import com.example.lab6_fx_15_11.utils.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E t);
}
