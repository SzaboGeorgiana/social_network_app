package com.example.lab6_fx_15_11.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utilizator extends Entity<Long> {
    private String firstName;
    private String lastName;
    private String password;
    private List<Utilizator> friends;

    public Utilizator(String firstName, String lastName,String pass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password=pass;
        friends=new ArrayList<Utilizator>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Utilizator> getFriends() {
        return friends;
    }
    public void addFriend(Utilizator fr) {
        friends.add(fr);

    }
    public void removeFriend(Utilizator fr) {
        friends.remove(fr);

    }
    public List<String> toStringfr(List<Utilizator> friends) {
        List<String> str=new ArrayList<>();
        List<String> finalStr = str;
        friends.forEach(x->
                finalStr.add("Firstname: "+x.firstName+" id: "+x.getId()));
//        for(int i=0;i<friends.size();i++)
//        {
//            Utilizator x=friends.get(i);
//            str += x.firstName+" "+x.lastName+",";
//        };
        // str+='\b';
        //str+="]";
        return str;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", friends=" +  toStringfr(friends)+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getFriends().equals(that.getFriends());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }
}
