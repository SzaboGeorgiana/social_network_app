package com.example.lab6_fx_15_11.ui;

import com.example.lab6_fx_15_11.Validator.UtilizatorValidator;
import com.example.lab6_fx_15_11.Validator.ValidationException;
import com.example.lab6_fx_15_11.domain.Entity;
import com.example.lab6_fx_15_11.domain.Prietenie;
import com.example.lab6_fx_15_11.domain.Utilizator;
import com.example.lab6_fx_15_11.repo.InMemoryRepo;
import com.example.lab6_fx_15_11.service.PrietenieSrv;
import com.example.lab6_fx_15_11.service.UtilizatorSrv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Long.parseLong;
import static java.lang.Long.signum;

public class Console <ID, E extends Entity<ID>> {
    UtilizatorSrv srv;
    PrietenieSrv srv1;

    public Console(UtilizatorSrv srv, PrietenieSrv srv1) {
        this.srv = srv;
        this.srv1=srv1;

    }

    public void get_allU(){
        srv.get_all_srv().forEach(x-> System.out.println(x.toString()));
    }

    public void addU(){
        String nume,prenume;
        long id;
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.Nume utilizator");
        nume = scanner.nextLine();
        System.out.println("2.Prenume utilizator");
        prenume = scanner.nextLine();
        System.out.println("3.ID utilizator");

        String expresie = scanner.nextLine();
        id=parseLong(expresie) ;


        try  {
            srv.add(nume,prenume,id,"pass");

        } catch (ValidationException e) {
            System.out.println(e.toString());
        }
    }
    public void dellU(){

        long id;
        Scanner scanner = new Scanner(System.in);

        System.out.println("3.ID utilizator");

        String expresie = scanner.nextLine();


        try  {
            id=parseLong(expresie) ;
            Optional e=srv.dell(id);
            System.out.println(e.toString()+"sters cu succes");

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }


    public void setP(){
        List<Prietenie> entities= (List<Prietenie>) srv1.find_all_srv();
        entities.forEach(x->
                srv.actualizare_prieteni(x.getId().getLeft(),x.getId().getRight()));
        //  System.out.println("Utilizatorii:["+x.getId().toString()+"] la data de "+x.toString())
    }

    public void get_allP(){
        List<Prietenie> entities= (List<Prietenie>) srv1.get_all_srv();
        entities.forEach(x->
                System.out.println("Utilizatorii:["+x.getId().toString()+"] la data de "+x.toString())
        );
    }

    public void addP(){
        String nume,prenume;
        long id;
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.ID utilizator1");
        nume = scanner.nextLine();
        long id1=parseLong(nume);
        System.out.println("2.ID utilizator1");
        prenume = scanner.nextLine();
        long id2=parseLong(prenume);
        if(srv.findOneSrv(id1)!=null&&srv.findOneSrv(id2)!=null)
        {
            if(id1!=id2){
                if(srv1.findPrietenie(id1,id2)==false){

                    try  {
                        LocalDateTime date1=LocalDateTime.now();
                        srv1.add(id1,id2,date1);
                        srv.actualizare_prieteni(id1,id2);
                        System.out.println(srv.findOneSrv(id1).toString()+" si "+srv.findOneSrv(id2).toString()+" s-au imprietenit cu succes");

                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
                else
                    System.out.println("prietenia exista deja");
            }
            else
                System.out.println("id urile trebuie sa fie diferite");
        }
        else
            System.out.println("ati introdus id uri inexistente in lista de utilizatori");
    }


    public void dellP(){

        String nume,prenume;
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.ID utilizator1");
        nume = scanner.nextLine();
        long id1=parseLong(nume);
        System.out.println("2.ID utilizator1");
        prenume = scanner.nextLine();
        long id2=parseLong(prenume);
        if(srv.getOneSrv(id1)!=null&&srv.getOneSrv(id2)!=null)
        {
            if(id1!=id2){
                if(srv1.findPrietenie(id1,id2)==true){

                    Optional e=srv1.dell(id1,id2);
                    srv.dell_prieteni(id1,id2);
                    System.out.println(e.toString()+"sters cu succes");
                }
                else
                    System.out.println("prietenia nu exista");
            }
            else
                System.out.println("id urile trebuie sa fie diferite");
        }
        else
            System.out.println("ati introdus id uri inexistente in lista de utilizatori");

    }

    void ww(){
        Retele r=new Retele(srv,srv1);
        r.main1();;
    }

    void data(){
        String utilizator;
        String luna;

        Scanner scanner = new Scanner(System.in);
        System.out.println("1.ID utilizator1");
        utilizator = scanner.nextLine();
        long id1=parseLong(utilizator);
        System.out.println("2.Luna din an");
        luna = scanner.nextLine();
        //  int id2= Integer.parseInt(luna);
        if(srv.findOneSrv(id1)!=null) {

            List<Prietenie> list = (List<Prietenie>) srv1.get_all_srv();
            for(Prietenie x:list) {
                if (Objects.equals(x.getDate().getMonth().toString(), luna)) {
                    if (x.getId().getLeft() == id1){

                        Utilizator u=srv.getOneSrv(x.getId().getRight());
                        System.out.println(u.toString() + x.getDate());

                        System.out.println(u.getFirstName()+" "+u.getLastName()+" " + x.getDate());

                    }
                    if (x.getId().getRight() == id1){
                        Utilizator u=srv.getOneSrv(x.getId().getLeft());
                        System.out.println(u.toString() + x.getDate());
                        System.out.println(u.getFirstName()+" "+u.getLastName()+" " + x.getDate());

                    }
                }
            }
        }

    }

    public void print_meniu()
    {
        System.out.println("01.Afisaza utilizatori");
        System.out.println("02.Afisaza prietenii");

        System.out.println("1.Adauga utilizator");
        System.out.println("2.Sterge utilizator");

        System.out.println("3.Adauga prietenie");
        System.out.println("4.Sterge prietenie");

        System.out.println("5.Afisaza numar de comunitati si cea mai sociabila comunitate");

        System.out.println("6.Afisaza pentru un utilizator toate prieteniile lui dintr-o luna data.\n exemplu luna: OCTOBER");

        System.out.println("7. Exit");

        System.out.println("Selecteaza o optiune:");
    }



    public void start(){

        // ok;
        boolean ok = true;
        while(ok) {
            print_meniu();
            srv.find_all_srv();
            setP();
            Scanner scanner = new Scanner(System.in);
            String expresie = scanner.nextLine();
            //scanner.close(); // Închideți scannerul la sfârșitul programului.

            if (Objects.equals(expresie, "1."))
                //System.out.println("1");
                addU();

            if (Objects.equals(expresie, "2."))
                //System.out.println("2");
                dellU();
            if (Objects.equals(expresie, "01."))
                //System.out.println("1");
                get_allU();

            if (Objects.equals(expresie, "02."))
                //System.out.println("02");
                get_allP();

            if (Objects.equals(expresie, "3."))
                //   System.out.println("3");
                addP();

            if (Objects.equals(expresie, "4."))
                //System.out.println("4");
                dellP();

            if (Objects.equals(expresie, "5."))
                //System.out.println("5");
                ww();

            if (Objects.equals(expresie, "6."))
                //System.out.println("5");
                data();

            //  if (Objects.equals(expresie, "6."))
            //    System.out.println("6");
            if (Objects.equals(expresie, "7."))
                ok = false;


        }
    }
}
