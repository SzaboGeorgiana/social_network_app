package com.example.lab6_fx_15_11;
import com.example.lab6_fx_15_11.Validator.UtilizatorValidator;
import com.example.lab6_fx_15_11.repo.InMemoryRepo;
import com.example.lab6_fx_15_11.repo.UtilozatorDBRepo;
import com.example.lab6_fx_15_11.repo.paging.PagingRepository;
import com.example.lab6_fx_15_11.service.UtilizatorSrv;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



import java.io.IOException;

public class StartApp extends Application {

    PagingRepository repo;
    UtilizatorSrv srv;

    public static void main(String[] args) {
        //System.out.println("ok");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //System.out.println("ok");
        //String fileN = ApplicationContext.getPROPERTIES().getProperty("data.tasks.messageTask");
         repo=new UtilozatorDBRepo(new UtilizatorValidator(),"jdbc:postgresql://localhost:5432/socialnetwork","postgres","1234");
         srv=new UtilizatorSrv((InMemoryRepo) repo);
        //messageTaskService.getAll().forEach(System.out::println);
        srv.find_all_srv();
        //setP();
        initView(primaryStage);
        primaryStage.setWidth(800);
        primaryStage.show();
    }

    private void initView(Stage primaryStage) throws IOException {

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("user-view.fxml"));
        AnchorPane Layout = Loader.load();
        primaryStage.setScene(new Scene(Layout));

        ControllerU c = Loader.getController();
        c.setUtilizatorService(srv);

    }
}
/*
        18	"Andrei"	"Vasilescu"	"parola_criptata"
        1	"Pop"	"Mihai"	"parola_criptata"
        5	"Anca"	"Daria"	"parola_criptata"
        16	"Flaviu"	"Popescu"	"parola_criptata"
        17	"Maria"	"Ionescu"	"parola_criptata"
        19	"Elena"	"Dumitru"	"parola_criptata"
        20	"Radu"	"Georgescu"	"parola_criptata"
        22	"Sara"	"Ionescu"	"parola_criptata"
        24	"Elena"	"Voica"	"parola_criptata"
        23	"Andrei"	"Dann"	"parola_criptata"
        13	"Maria11"	"Ana"	"parola_criptata"
        21	"Man"	"Popescu"	"parola_criptata"
*/