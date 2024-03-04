package com.example.lab6_fx_15_11.controller;



        import com.example.lab6_fx_15_11.ControllerU;
        import javafx.fxml.FXML;
        import javafx.scene.control.*;
        import javafx.stage.Stage;
        import com.example.lab6_fx_15_11.domain.Utilizator;
        import com.example.lab6_fx_15_11.Validator.ValidationException;
        import com.example.lab6_fx_15_11.service.UtilizatorSrv;

        import java.time.LocalDateTime;
        import java.util.Objects;
        import java.util.Optional;

public class EditUtilizatorController {

    public TextField textFieldParola;
    public Label Pass;
    public TextField textFieldParolaV;
    public Label Info;
    @FXML
    private TextField textFieldId;
    @FXML
    private TextField textFieldNume;
    @FXML
    private TextField textFieldPrenume;

    private UtilizatorSrv service;
    Stage dialogStage;
    Utilizator u;

    @FXML
    private void initialize() {
    }

    public void setService(UtilizatorSrv service,  Stage stage, Utilizator m) {
        this.service = service;
        this.dialogStage=stage;
        this.u=m;
        textFieldParolaV.setVisible(true);
        Pass.setVisible(true);
        if (null != m) {
           setFields(m);
            textFieldId.setEditable(false);
        }
        else {
            textFieldId.setText("id-ul se va genera automat");
            textFieldId.setEditable(false);
            textFieldParolaV.setVisible(false);
            Pass.setVisible(false);

        }
    }

    @FXML
    public void handleSave_adauga(){
        String nume=textFieldNume.getText();
        String prenu=textFieldPrenume.getText();
        String idd=textFieldId.getText();
        String pass=textFieldParola.getText();

        if(Objects.equals(idd, "id-ul se va genera automat")) {//adauga
            try {
                long id=1;
                service.add(nume, prenu, id, pass);
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Slavare mesaj", "Mesajul a fost salvat");
            } catch (ValidationException e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
            dialogStage.close();
        }
        else {//modifica
            try {
                String parola=textFieldParolaV.getText();
                textFieldParolaV.clear();
                Optional rez=service.valid(parola,u);
                if(rez.isEmpty())
                    Info.setText("Parola gresita. Incearca din nou.");
                else
                {

                service.mod(nume, prenu, Long.parseLong(idd),pass);
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Salvare", "Utilizatorul a fost salvat");
                dialogStage.close();

                }
            } catch (ValidationException e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }

        }

    }

//    private void updateMessage(MessageTask m)
//    {
//        try {
//            MessageTask r= this.service.updateMessageTask(m);
//            if (r==null)
//                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Modificare mesaj","Mesajul a fost modificat");
//        } catch (ValidationException e) {
//            MessageAlert.showErrorMessage(null,e.getMessage());
//        }
//        dialogStage.close();
//    }


//    private void saveMessage(MessageTask m)
//    {
//        // TODO
//        try {
//            MessageTask r= this.service.addMessageTaskTask(m);
//            if (r==null)
//                dialogStage.close();
//            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Slavare mesaj","Mesajul a fost salvat");
//        } catch (ValidationException e) {
//            MessageAlert.showErrorMessage(null,e.getMessage());
//        }
//        dialogStage.close();
//
//    }

    private void clearFields() {
        textFieldId.setText("");
        textFieldNume.setText("");
        textFieldPrenume.setText("");

    }
    private void setFields(Utilizator s)
    {
        textFieldId.setText(s.getId().toString());
        textFieldNume.setText(s.getLastName());
        textFieldPrenume.setText(s.getFirstName());

    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}
