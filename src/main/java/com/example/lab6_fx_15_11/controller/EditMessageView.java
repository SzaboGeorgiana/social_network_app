package com.example.lab6_fx_15_11.controller;

import com.example.lab6_fx_15_11.Validator.PrietenieValidator;
import com.example.lab6_fx_15_11.Validator.UtilizatorValidator;
import com.example.lab6_fx_15_11.Validator.ValidationException;
import com.example.lab6_fx_15_11.domain.Mesaje;
import com.example.lab6_fx_15_11.domain.Prietenie;
import com.example.lab6_fx_15_11.domain.Request;
import com.example.lab6_fx_15_11.domain.Utilizator;
import com.example.lab6_fx_15_11.repo.MesajeDBRepo;
import com.example.lab6_fx_15_11.repo.PrietenieDBRepo;
import com.example.lab6_fx_15_11.repo.RequestDBRepo;
import com.example.lab6_fx_15_11.service.MesajeSrv;
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

public class EditMessageView {

    @FXML
    private TextField text;
    @FXML
    private TextField text1;
//    @FXML
//    private TextField textFieldPrenume;

    ObservableList<Utilizator> model = FXCollections.observableArrayList();
    ObservableList<Request> model1 = FXCollections.observableArrayList();
//
    List<Utilizator> users;


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

    @FXML
    TableView<TableRowData> tableView3;
    @FXML
    TableColumn<TableRowData,String> tableColumn7;

    @FXML
    TableColumn<TableRowData, String> tableColumn8;
    @FXML
    TableColumn<TableRowData,String> tableColumn9;


    private UtilizatorSrv service;
    private MesajeSrv service1;

    private MesajeDBRepo repo1;

    private PrietenieSrv service2;

    private PrietenieDBRepo repo2;
    Stage dialogStage;
    Utilizator u;

    @FXML
    private void initialize() {
        repo1= new MesajeDBRepo(new UtilizatorValidator(),"jdbc:postgresql://localhost:5432/socialnetwork","postgres","1234");
        service1=new MesajeSrv( repo1);
        //messageTaskService.getAll().forEach(System.out::println);
        service1.find_all_srv();

        repo2= new PrietenieDBRepo(new PrietenieValidator(),"jdbc:postgresql://localhost:5432/socialnetwork","postgres","1234");
        service2=new PrietenieSrv( repo2);
        //messageTaskService.getAll().forEach(System.out::println);
        service2.find_all_srv();

        users=new ArrayList<Utilizator>();


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


        tableColumn7.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana1()) : null);
        tableView3.getColumns().add(tableColumn7);

        tableColumn8.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana2()) : null);
        tableView3.getColumns().add(tableColumn8);

        tableColumn9.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana3()) : null);
        tableView3.getColumns().add(tableColumn9);


    }

    public void setService(UtilizatorSrv service,  Stage stage, Utilizator m) {
        this.service = service;
        this.dialogStage=stage;
        this.u=m;
//        initModel();
//        initModel1();
        initModel2();
    }


    private void initModel3() {
        //service1.find_all_srv();
       // Iterable<Mesaje> lst = service1.get_all_srv();
        ObservableList<TableRowData> rowDataList = FXCollections.observableArrayList();
//        tableColumn1.setText(ut.getFirstName()+" "+ut.getLastName());
//        List<Mesaje> mes = StreamSupport.stream(lst.spliterator(), false).collect(Collectors.toList());
//        ////id1 a trimis la id2; id2 a primit; lista primite pt id2 cu nume id1
//        mes.sort(Comparator.comparing(Mesaje::getDate));

        for(Utilizator x: users){
                    rowDataList.add(new TableRowData(x.getFirstName(), x.getLastName(),""));
            }


        tableView3.setItems(rowDataList);

    }
    private void initModel1(Utilizator ut) {
        service1.find_all_srv();
        Iterable<Mesaje> lst = service1.get_all_srv();
        ObservableList<TableRowData> rowDataList = FXCollections.observableArrayList();
        tableColumn1.setText(ut.getFirstName()+" "+ut.getLastName());
        List<Mesaje> mes = StreamSupport.stream(lst.spliterator(), false).collect(Collectors.toList());
        ////id1 a trimis la id2; id2 a primit; lista primite pt id2 cu nume id1
        mes.sort(Comparator.comparing(Mesaje::getDate));

        for(Mesaje x: mes){
            if(Objects.equals(x.getId().getRight(), u.getId())&&Objects.equals(x.getId().getLeft(), ut.getId())){
                if(x.getReplay()==null){
                    rowDataList.add(new TableRowData(x.getText(),"" , x.getDate().toString()));
                }
                else
                    rowDataList.add(new TableRowData(x.getText(),"" , x.getDate().toString()+"  !"));
            }
            if(Objects.equals(x.getId().getLeft(), u.getId())&&Objects.equals(x.getId().getRight(), ut.getId())){
                if(x.getReplay()==null){

                    rowDataList.add(new TableRowData("",x.getText(),x.getDate().toString()));}
                else
                    rowDataList.add(new TableRowData("",x.getText(),x.getDate().toString()+"  !"));
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
            TableRowData selected = tableView2.getSelectionModel().getSelectedItem();
            if (selected == null)
                MessageAlert.showErrorMessage(null, "NU ati selectat nici un utilizator");

            int nr=tableView2.getSelectionModel().getSelectedIndex();

            Utilizator utz=utilizator_pozitie_data(nr);
            Long id2_pt = utz.getId();
            String  text2=text.getText();

            TableRowData selected1 = tableView1.getSelectionModel().getSelectedItem();
            String replay=null;

            if (selected1 != null) {
                int nr1=tableView1.getSelectionModel().getSelectedIndex();

                Mesaje msj=mesaj_pozitie_data(nr1,utz);
                    replay=msj.getText()+" , "+msj.getDate().toString();
            }


            if(!Objects.equals(text2, "")){
                service1.add(id1,id2_pt,LocalDateTime.now(),text2,replay);
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Mesaj", "Mesaj trimis");
                text.clear();
                initModel1(utz);
            }
            else
                MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Mesaj", "Mesajul nu poate fi null");

    } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }


    }

    Utilizator utilizator_pozitie_data(int nr){
        Iterable<Prietenie> lst = service2.get_all_srv();
        int i=0;

        Utilizator utz=null;
        for(Prietenie x: lst){
            if(Objects.equals(x.getId().getRight(), u.getId())){
                Optional<Utilizator> ut=service.findOneSrv(x.getId().getLeft());
                if(i==nr)
                {
                    utz=ut.get();
                }
                i++;}

            if(Objects.equals(x.getId().getLeft(), u.getId())){
                Optional<Utilizator> ut=service.findOneSrv(x.getId().getRight());
                if(i==nr)
                {
                    utz=ut.get();
                }
                i++;
            }
        }
        return utz;
    }
    Mesaje mesaj_pozitie_data(int nr1, Utilizator utz)
    {
        service1.find_all_srv();
        Iterable<Mesaje> lst1 = service1.get_all_srv();

        List<Mesaje> mes = StreamSupport.stream(lst1.spliterator(), false).collect(Collectors.toList());
        ////id1 a trimis la id2; id2 a primit; lista primite pt id2 cu nume id1
        mes.sort(Comparator.comparing(Mesaje::getDate));
        int i1=0;
        Mesaje msj = null;
        for(Mesaje x: mes){
            if(Objects.equals(x.getId().getRight(), u.getId())&&Objects.equals(x.getId().getLeft(), utz.getId())) {
                if (i1 == nr1) {
                    msj = x;
                }
                i1++;
            }
            if(Objects.equals(x.getId().getLeft(), u.getId())&&Objects.equals(x.getId().getRight(), utz.getId())) {
                if (i1 == nr1) {
                    msj = x;
                }
                i1++;

            }
        }
        return msj;
    }
    @FXML
    public void handleDetalii(){

        try {
            Long id1= u.getId();
            TableRowData selected = tableView2.getSelectionModel().getSelectedItem();
            if (selected == null)
                MessageAlert.showErrorMessage(null, "NU ati selectat nici un utilizator");

            int nr=tableView2.getSelectionModel().getSelectedIndex();
            Utilizator utz=utilizator_pozitie_data(nr);
            TableRowData selected1 = tableView1.getSelectionModel().getSelectedItem();


            if (selected1 != null) {

                int nr1=tableView1.getSelectionModel().getSelectedIndex();
                Mesaje msj=mesaj_pozitie_data(nr1,utz);
                if(msj.getReplay()!=null)
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Mesaj", "Raspuns la mesajul: "+msj.getReplay());
                else
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Mesaj", "Mesaj general(nu e replay)");

            }
            else {
                MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Mesaj", "Mesajul nu poate fi null");

            }

        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }


    }


    @FXML
    public void handleAdd(){

        try {
            TableRowData selected = tableView2.getSelectionModel().getSelectedItem();
            if (selected == null)
                MessageAlert.showErrorMessage(null, "NU ati selectat nici un utilizator");

            int nr=tableView2.getSelectionModel().getSelectedIndex();
            Utilizator utz=utilizator_pozitie_data(nr);
            if(users.contains(utz)==false)
            {
                users.add(utz);
                initModel3();
            }
            else
                MessageAlert.showErrorMessage(null, "Lista contine deja acest utilizator");

        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    @FXML
    public void handleMultiplii(){

        try {
            Long id1= u.getId();
            int ok=1;
            for(Utilizator utz:users) {
                Long id2_pt = utz.getId();
                String text2 = text1.getText();
                String replay = null;

                if (!Objects.equals(text2, "")) {
                    service1.add(id1, id2_pt, LocalDateTime.now(), text2, replay);
                    initModel1(utz);
                } else {
                    MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Mesaj", "Mesajul nu poate fi null");
                    ok=0;
                    break;
                }

            }
            if(ok==1)
            {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Mesaj", "Mesaj trimis");
            text1.clear();
            users=new ArrayList<Utilizator>();
            initModel3();
            }
        }catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }


    }


    @FXML
    public void handleConv(){

        try {
            TableRowData selected = tableView2.getSelectionModel().getSelectedItem();
            if (selected == null)
                MessageAlert.showErrorMessage(null, "NU ati selectat nici un utilizator");

            int nr=tableView2.getSelectionModel().getSelectedIndex();

            Utilizator uti=utilizator_pozitie_data(nr);
            initModel1(uti);

        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }

    }

}
