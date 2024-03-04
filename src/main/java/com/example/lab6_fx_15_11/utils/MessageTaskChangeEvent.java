package com.example.lab6_fx_15_11.utils;

import com.example.lab6_fx_15_11.domain.Utilizator;

public class MessageTaskChangeEvent extends Event {
    private ChangeEventType type;
    private Utilizator data, oldData;

    public MessageTaskChangeEvent(ChangeEventType type, Utilizator data) {
        this.type = type;
        this.data = data;
    }
    public MessageTaskChangeEvent(ChangeEventType type, Utilizator data, Utilizator oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Utilizator getData() {
        return data;
    }

    public Utilizator getOldData() {
        return oldData;
    }
}