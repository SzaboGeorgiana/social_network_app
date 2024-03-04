package com.example.lab6_fx_15_11.domain;

import java.time.LocalDateTime;

public class Request extends Entity<Tuple<Long,Long>> {

    LocalDateTime date;

   String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request{" +
                "date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}