package com.example.lab6_fx_15_11.domain;


import java.time.LocalDateTime;

public class Mesaje extends Entity<Tuple<Long,Long>> {

    LocalDateTime date;
    String text;
    String replay;

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     *
     * @return the date when the friendship was created
     */
    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Mesaje{" +
                "date=" + date +
                ", text='" + text + '\'' +
                ", replay=" + replay +
                '}';
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    public String getText() {
        return text;
    }

    public String getReplay() {
        return replay;
    }
}