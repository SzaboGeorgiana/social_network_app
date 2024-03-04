package com.example.lab6_fx_15_11;

import com.example.lab6_fx_15_11.controller.EditMessageView;
import com.example.lab6_fx_15_11.controller.EditRequestView;
import com.example.lab6_fx_15_11.domain.Utilizator;
import com.example.lab6_fx_15_11.service.UtilizatorSrv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


        import com.example.lab6_fx_15_11.Validator.ValidationException;
        import com.example.lab6_fx_15_11.domain.Utilizator;
        import com.example.lab6_fx_15_11.service.UtilizatorSrv;
        import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;



        import com.example.lab6_fx_15_11.ControllerU;
        import javafx.fxml.FXML;
        import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
        import javafx.stage.Stage;
        import com.example.lab6_fx_15_11.domain.Utilizator;
        import com.example.lab6_fx_15_11.Validator.ValidationException;
        import com.example.lab6_fx_15_11.service.UtilizatorSrv;

        import java.time.LocalDateTime;
        import java.util.Objects;
import java.util.Optional;

public class EditContView {
    public Button Prietenii;
    public Button Mess;
    public Label Informare;
    public TextField Nume;
    public TextField Parola;

//    @FXML
//    private TextField textFieldId;
//    @FXML
//    private TextField textFieldNume;
//    @FXML
//    private TextField textFieldPrenume;

    private UtilizatorSrv service;
    Stage dialogStage;
    Utilizator u;

    @FXML
    private void initialize() {
    }

    public void setService(UtilizatorSrv service,  Stage stage, Utilizator m) {
        this.service = service;
        this.dialogStage = stage;
        this.u = m;
        Nume.setText(u.getFirstName()+"_"+u.getLastName());
        Nume.setEditable(false);
    }

    @FXML
    public void handleValid(){
        String parola=Parola.getText();
        Parola.clear();
        Optional rez=service.valid(parola,u);
        if(rez.isEmpty())
            Informare.setText("Parola gresita. Incearca din nou.");
        else
        {
            Informare.setText("Parola corecta. Optiunile tale:");
            Mess.setDisable(false);
            Prietenii.setDisable(false);
        }
    }


    public void intraInReq(ActionEvent ev) {
        // TODO
//        Utilizator selected = tableView.getSelectionModel().getSelectedItem();
//        if (selected != null) {
//            showRequestEditDialog(selected);
//        } else
//            MessageAlert.showErrorMessage(null, "NU ati selectat nici un student");
          showRequestEditDialog(u);
    }


    public void showRequestEditDialog(Utilizator messageTask) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/editRequest-view.fxml"));

            AnchorPane root = (AnchorPane) loader.load();


            // Create the dialog Stage.
            Stage dialogStage1 = new Stage();
            dialogStage1.setTitle("contul lui "+ messageTask.getLastName()+" "+messageTask.getFirstName() );
            dialogStage1.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage1.setScene(scene);

            EditRequestView editMessageViewController = loader.getController();
            editMessageViewController.setService(service, dialogStage1, messageTask);

            dialogStage1.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void intraInMess(ActionEvent ev) {
        showMessEditDialog(u);
    }


    public void showMessEditDialog(Utilizator messageTask) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/editMessage-view.fxml"));



            AnchorPane root = (AnchorPane) loader.load();


            // Create the dialog Stage.
            Stage dialogStage1 = new Stage();
            dialogStage1.setTitle("contul lui "+ messageTask.getLastName()+" "+messageTask.getFirstName() );
            dialogStage1.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene1 = new Scene(root);
            dialogStage1.setScene(scene1);

            EditMessageView editMessageViewController = loader.getController();
            editMessageViewController.setService(service, dialogStage1, messageTask);

            dialogStage1.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}

