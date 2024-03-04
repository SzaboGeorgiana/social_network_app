package com.example.lab6_fx_15_11.utils;



import com.example.lab6_fx_15_11.domain.Utilizator;

public class TaskStatusEvent extends Event {
    private TaskExecutionStatusEventType type;
    private Utilizator task;
    public TaskStatusEvent(TaskExecutionStatusEventType type, Utilizator task) {
        this.task=task;
        this.type=type;
    }

    public Utilizator getTask() {
        return task;
    }

    public void setTask(Utilizator task) {
        this.task = task;
    }

    public TaskExecutionStatusEventType getType() {
        return type;
    }

    public void setType(TaskExecutionStatusEventType type) {
        this.type = type;
    }
}

