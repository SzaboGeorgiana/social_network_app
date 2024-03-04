package com.example.lab6_fx_15_11;

import com.example.lab6_fx_15_11.Validator.PrietenieValidator;
import com.example.lab6_fx_15_11.domain.Utilizator;
import com.example.lab6_fx_15_11.Validator.UtilizatorValidator;
import com.example.lab6_fx_15_11.repo.*;
import com.example.lab6_fx_15_11.service.PrietenieSrv;
import com.example.lab6_fx_15_11.service.UtilizatorSrv;
import com.example.lab6_fx_15_11.ui.Console;
//import org.example.ui.Retele;

import java.util.List;

public class Main {

    public static void main(String[] args) {


        //UtilizatorFileRepo repo=new UtilizatorFileRepo("C:\\Users\\andre\\Documents\\facultate\\an2\\MAP\\lab3_23_10 - lab4\\src\\main\\java\\org\\example\\repo\\data.csv",new UtilizatorValidator()) ;
        //InMemoryRepo repo=new InMemoryRepo(new UtilizatorValidator()) ;
        UtilozatorDBRepo repo=new UtilozatorDBRepo(new UtilizatorValidator(),"jdbc:postgresql://localhost:5432/socialnetwork","postgres","1234");
        UtilizatorSrv srv=new UtilizatorSrv( repo);

        //InMemoryRepoPrietenie repo1=new InMemoryRepoPrietenie(new PrietenieValidator()) ;
        //InMemoryRepo repo=new InMemoryRepo(new UtilizatorValidator()) ;

        PrietenieDBRepo repo1=new PrietenieDBRepo(new PrietenieValidator(),"jdbc:postgresql://localhost:5432/socialnetwork","postgres","1234");


        PrietenieSrv srv1=new PrietenieSrv( repo1);

        Console c=new Console(srv,srv1);


        c.start();

        System.out.println("ok");
    }
}
