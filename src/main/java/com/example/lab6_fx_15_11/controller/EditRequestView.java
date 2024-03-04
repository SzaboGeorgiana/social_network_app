package com.example.lab6_fx_15_11.controller;

import com.example.lab6_fx_15_11.Validator.PrietenieValidator;
import com.example.lab6_fx_15_11.Validator.UtilizatorValidator;
import com.example.lab6_fx_15_11.Validator.ValidationException;
import com.example.lab6_fx_15_11.domain.Prietenie;
import com.example.lab6_fx_15_11.domain.Request;
import com.example.lab6_fx_15_11.domain.Utilizator;
import com.example.lab6_fx_15_11.repo.PrietenieDBRepo;
import com.example.lab6_fx_15_11.repo.RequestDBRepo;
import com.example.lab6_fx_15_11.service.PrietenieSrv;
import com.example.lab6_fx_15_11.service.RequestSrv;
import com.example.lab6_fx_15_11.service.UtilizatorSrv;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EditRequestView {

//    @FXML
//    private TextField textFieldId;
//    @FXML
//    private TextField textFieldNume;
//    @FXML
//    private TextField textFieldPrenume;

    ObservableList<Utilizator> model = FXCollections.observableArrayList();
    ObservableList<Request> model1 = FXCollections.observableArrayList();

    Set<Utilizator> users;

    @FXML
    TableView<Utilizator> tableView;
    @FXML
    TableColumn<Utilizator,String> tableColumnN;
    @FXML
    TableColumn<Utilizator,String> tableColumnP;


    @FXML
    TableView<TableRowData> tableView1;
    @FXML
    TableColumn<TableRowData,String> tableColumn1;

    @FXML
    TableColumn<TableRowData, String> tableColumn2;
    @FXML
    TableColumn<TableRowData,String> tableColumn3;


    @FXML
    TableView<TableRowData> tableView2;
    @FXML
    TableColumn<TableRowData,String> tableColumn4;

    @FXML
    TableColumn<TableRowData, String> tableColumn5;
    @FXML
    TableColumn<TableRowData,String> tableColumn6;

    private UtilizatorSrv service;
    private RequestSrv service1;

    private RequestDBRepo repo1;

    private PrietenieSrv service2;

    private PrietenieDBRepo repo2;
    Stage dialogStage;
    Utilizator u;

    @FXML
    private void initialize() {
        repo1= new RequestDBRepo(new UtilizatorValidator(),"jdbc:postgresql://localhost:5432/socialnetwork","postgres","1234");
        service1=new RequestSrv( repo1);
        //messageTaskService.getAll().forEach(System.out::println);
        service1.find_all_srv();

        repo2= new PrietenieDBRepo(new PrietenieValidator(),"jdbc:postgresql://localhost:5432/socialnetwork","postgres","1234");
        service2=new PrietenieSrv( repo2);
        //messageTaskService.getAll().forEach(System.out::println);
        service2.find_all_srv();


        tableColumnN.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        tableColumnP.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        tableView.setItems(model);


        tableColumn1.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana1()) : null);
        tableView1.getColumns().add(tableColumn1);

        tableColumn2.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana2()) : null);
        tableView1.getColumns().add(tableColumn2);

        tableColumn3.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana3()) : null);
        tableView1.getColumns().add(tableColumn3);


        tableColumn4.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana1()) : null);
        tableView2.getColumns().add(tableColumn4);

        tableColumn5.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana2()) : null);
        tableView2.getColumns().add(tableColumn5);

        tableColumn6.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana3()) : null);
        tableView2.getColumns().add(tableColumn6);



//        ObservableList<TableRowData> rowDataList = FXCollections.observableArrayList();
//        rowDataList.add(new TableRowData("ana", "bb", "cristina"));

//        tableView1.setItems(rowDataList);



//
//        tableColumn1.setCellValueFactory(new PropertyValueFactory<Request, String>("id1"));
//        tableColumn2.setCellValueFactory(new PropertyValueFactory<Request, Date>("trimisa_in"));
//        tableColumn3.setCellValueFactory(new PropertyValueFactory<Request, String>("status"));
//
//
//        tableView1.setItems(model1);
    }

    public void setService(UtilizatorSrv service,  Stage stage, Utilizator m) {
        this.service = service;
        this.dialogStage=stage;
        this.u=m;
        initModel();
        initModel1();
        initModel2();
    }


    private void initModel() {
        Iterable<Utilizator> messages ;
        List<Utilizator> messagesList = new ArrayList<>((Collection) service.get_all_srv());
        Iterable<Request> messages1 = service1.get_all_srv();

        for(Request x: messages1){
            if(Objects.equals(x.getId().getLeft(), u.getId()))
            {
                Optional<Utilizator> ut=service.findOneSrv(x.getId().getRight());
                messagesList.remove(ut.get());

            }

            if(Objects.equals(x.getId().getRight(), u.getId())){
                Optional<Utilizator> ut=service.findOneSrv(x.getId().getLeft());
                messagesList.remove(ut.get());

            }

        }

        List<Utilizator> messageTaskList = StreamSupport.stream(messagesList.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(messageTaskList);
    }


    private void initModel1() {
        service1.find_all_srv();
        Iterable<Request> lst = service1.get_all_srv();
        ObservableList<TableRowData> rowDataList = FXCollections.observableArrayList();

        ////id1 a trimis la id2; id2 a primit; lista primite pt id2 cu nume id1


        for(Request x: lst){
            if(Objects.equals(x.getId().getRight(), u.getId())){
                Optional<Utilizator> ut=service.findOneSrv(x.getId().getLeft());
                rowDataList.add(new TableRowData(ut.get().getFirstName()+" "+ut.get().getLastName(),x.getDate().toString() , x.getStatus()));
            }

        }
        tableView1.setItems(rowDataList);

    }

    private void initModel2() {
        service2.find_all_srv();
        Iterable<Prietenie> lst = service2.get_all_srv();
        ObservableList<TableRowData> rowDataList = FXCollections.observableArrayList();

        ////id1 a trimis la id2; id2 a primit; lista primite pt id2 cu nume id1


        for(Prietenie x: lst){
            if(Objects.equals(x.getId().getRight(), u.getId())){
                Optional<Utilizator> ut=service.findOneSrv(x.getId().getLeft());
                rowDataList.add(new TableRowData(ut.get().getFirstName(),ut.get().getLastName(),x.getDate().toString() ));
            }
            if(Objects.equals(x.getId().getLeft(), u.getId())){
                Optional<Utilizator> ut=service.findOneSrv(x.getId().getRight());
                rowDataList.add(new TableRowData(ut.get().getFirstName(),ut.get().getLastName(),x.getDate().toString() ));
            }

        }
        tableView2.setItems(rowDataList);

    }

    @FXML
    public void handleSave(){

            try {
                Long id1= u.getId();
                Utilizator selected = tableView.getSelectionModel().getSelectedItem();
                if (selected == null)
                    MessageAlert.showErrorMessage(null, "NU ati selectat nici un utilizator");
                Long id2=selected.getId();

                service1.add(id1, id2, LocalDateTime.now());
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Cerere", "Cerere trimisa");
            } catch (ValidationException e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
            initModel();

//            dialogStage.close();
//        }
//        else {//modifica
//            try {
//                service.mod(nume, prenu, Long.parseLong(idd));
//                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Slavare mesaj", "Mesajul a fost salvat");
//            } catch (ValidationException e) {
//                MessageAlert.showErrorMessage(null, e.getMessage());
//            }
//            dialogStage.close();
//        }

    }
    @FXML
    private void handleUpdate_reject()
    {
        handleUpdate("rejected");

    }
    @FXML
    private void handleUpdate_approved()
    {
        handleUpdate("approved");


    }
    private void handleUpdate(String str)
    {

        try {
            TableRowData selected = tableView1.getSelectionModel().getSelectedItem();
            int in = tableView1.getSelectionModel().getSelectedIndex();
            if (selected == null)
                MessageAlert.showErrorMessage(null, "NU ati selectat nici un utilizator");
            else {
                Iterable<Request> lst = service1.get_all_srv();

                ////id1 a trimis la id2; id2 a primit; lista primite pt id2 cu nume id1
                Long id1 = null;
                Long id2 = u.getId();
                int i = 0;
                for (Request x : lst) {
                    if (Objects.equals(x.getId().getRight(), u.getId())) {
                        Optional<Utilizator> ut = service.findOneSrv(x.getId().getLeft());
                        if (i == in) {
                            id1 = ut.get().getId();
                        }
                        i++;
                    }

                }

                if (id1 != null) {
                    service1.mod_ap(id1, id2, str);
                    if (str == "approved") {
                        service2.add(id1, id2, LocalDateTime.now());
                        initModel2();
                    }
                    initModel1();
                }

                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Modificare stare cerere", " Cerere de la pending la " + str);
            }
            } catch (ValidationException e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }

        }



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

//    private void clearFields() {
//        textFieldId.setText("");
//        textFieldNume.setText("");
//        textFieldPrenume.setText("");
//
//    }
//    private void setFields(Utilizator s)
//    {
//        textFieldId.setText(s.getId().toString());
//        textFieldNume.setText(s.getLastName());
//        textFieldPrenume.setText(s.getFirstName());
//
//    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}

