package com.example.lab6_fx_15_11.utils;
import com.example.lab6_fx_15_11.utils.Event;

public interface Observer <E extends Event> {
    void update(E e);
}
