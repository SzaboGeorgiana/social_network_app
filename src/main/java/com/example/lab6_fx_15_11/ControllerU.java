package com.example.lab6_fx_15_11;
        import com.example.lab6_fx_15_11.controller.EditUtilizatorController;
        import com.example.lab6_fx_15_11.controller.MessageAlert;
        import com.example.lab6_fx_15_11.domain.Utilizator;
        import com.example.lab6_fx_15_11.repo.paging.Page;
        import com.example.lab6_fx_15_11.repo.paging.Pageable;
        import com.example.lab6_fx_15_11.repo.paging.PageableImplementation;
        import com.example.lab6_fx_15_11.service.UtilizatorSrv;
        import com.example.lab6_fx_15_11.utils.MessageTaskChangeEvent;
        import com.example.lab6_fx_15_11.utils.Observer;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.AnchorPane;
        import javafx.stage.Modality;
        import javafx.stage.Stage;


        import java.io.IOException;
        import java.util.List;
        import java.util.Objects;
        import java.util.Optional;
        import java.util.Set;
        import java.util.stream.Collectors;
        import java.util.stream.StreamSupport;

public class ControllerU implements Observer<MessageTaskChangeEvent> {
    UtilizatorSrv service;

    int nrPg;
    int PgSize;

    @FXML
    private TextField textNr;
    @FXML
    private Label label;
    Pageable pageable;
    ObservableList<Utilizator> model = FXCollections.observableArrayList();

    Set<Utilizator> users;

    @FXML
    TableView<Utilizator> tableView;
    @FXML
    TableColumn<Utilizator,String> tableColumnN;
    @FXML
    TableColumn<Utilizator,String> tableColumnP;
//    @FXML
//    TableColumn<Utilizator,String> tableColumnTo;
//    @FXML
//    TableColumn<Utilizator,String> tableColumnData;
   // private Object MessageAlert;
@FXML
Button yourButton;
    @FXML
    Button next;
    @FXML
    Button prev;
    public void setUtilizatorService(UtilizatorSrv s) {
        service = s;
        service.addObserver((Observer<MessageTaskChangeEvent>) this);
//        initModel();
        nrPg=0;
        PgSize=5;
        handlePag(nrPg,PgSize);

    }

    @FXML
    public void initialize() {
        tableColumnN.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        tableColumnP.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        tableView.setItems(model);


//        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/lab6_fx_15_11/a.jpg")));
//
//        // Set the image to the ImageView
//        ImageView imageView = (ImageView) yourButton.getGraphic();
//        if (imageView != null) {
//            imageView.setImage(image);}
       // imageView.setImage(image);
    }

    private void initModel(int nrPg,int Pgsize) {
//        Iterable<Utilizator> messages = service.get_all_srv();
//        List<Utilizator> messageTaskList = StreamSupport.stream(messages.spliterator(), false)
//                .collect(Collectors.toList());
//        model.setAll(messageTaskList);
        pageable = new PageableImplementation(nrPg,Pgsize);
        Page<Utilizator> usrPg=service.find_all(pageable);

        List<Utilizator> messageTaskList = usrPg.getContent().
                collect(Collectors.toList());
        model.setAll(messageTaskList);

    }

    public void handlePag(int nrPg,int Pgsize){
        initModel(nrPg,Pgsize);
        long size= StreamSupport.stream(service.get_all_srv().spliterator(), false).count();
        String st= String.valueOf(size / PgSize);
        if( size%PgSize==0) {
            st= String.valueOf(size / PgSize-1);
        }
        label.setText(String.valueOf(nrPg)+"/"+st);
        updatePgButtons();

    }

    public  void updatePgButtons(){
        long size= StreamSupport.stream(service.get_all_srv().spliterator(), false).count();
        if( size%PgSize==0) {
            if (nrPg == size / PgSize - 1)
                next.setVisible(false);
            else

                next.setVisible(true);
        }
        else
        {
            if( nrPg==size/PgSize)
            {
                next.setVisible(false);

            }
            else
            {
                next.setVisible(true);
            }

        }


        if( nrPg==0)
        {
            prev.setVisible(false);
        }
        else
            prev.setVisible(true);


    }
    @FXML
    private void Next(ActionEvent actionEvent){
        nrPg++;
        handlePag(nrPg,PgSize);
    }
    @FXML
    private void Prev(ActionEvent actionEvent) {
        if (nrPg > 0) {
            nrPg--;
            handlePag(nrPg, PgSize);
        }
    }


    public void handleDeleteMessage(ActionEvent actionEvent) {
        Utilizator selected = (Utilizator) tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            long id=selected.getId();
            Optional deleted = service.dell(id);
            if (null != deleted) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Studentul a fost sters cu succes!");
            }
        } else MessageAlert.showErrorMessage(null, "Nu ati selectat nici un student!");

    }

    @Override
    public void update(MessageTaskChangeEvent messageTaskChangeEvent) {
        service.find_all_srv();
        initModel(nrPg,PgSize);
    }

    @FXML
    public void reincarca( ) {

        int pgs= Integer.parseInt(textNr.getText());
        PgSize=pgs;
        handlePag(nrPg,PgSize);
        textNr.clear();;

    }

    @FXML
    public void handleUpdateMessage(ActionEvent ev) {
        // TODO
        Utilizator selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showUtilizatorEditDialog(selected);
        } else
            MessageAlert.showErrorMessage(null, "NU ati selectat nici un student");
    }

    @FXML
    public void handleAddMessage(ActionEvent ev) {
        // TODO
        showUtilizatorEditDialog(null);

    }

    public void showUtilizatorEditDialog(Utilizator messageTask) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/editUser-view.fxml"));


            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Message");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditUtilizatorController editMessageViewController = loader.getController();
            editMessageViewController.setService(service, dialogStage, messageTask);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void intraInCont(ActionEvent ev) {
        // TODO
        Utilizator selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showContEditDialog(selected);
        } else
            MessageAlert.showErrorMessage(null, "NU ati selectat nici un student");
    }

    public void showContEditDialog(Utilizator messageTask) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/editCont.fxml"));


            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("contul lui "+ messageTask.getLastName()+" "+messageTask.getFirstName() );
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditContView editMessageViewController = loader.getController();
            editMessageViewController.setService(service, dialogStage, messageTask);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


