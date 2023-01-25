package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class QueueStatusController implements Initializable {

    @FXML
    private TableColumn<tblPassenger, Double> colQ1Liters;

    @FXML
    private TableColumn<tblPassenger, String> colQ1Name;

    @FXML
    private TableColumn<tblPassenger, String> colQ1Vehicle;

    @FXML
    private TableColumn<tblPassenger, Double> colQ2Liters;

    @FXML
    private TableColumn<tblPassenger, String> colQ2Name;

    @FXML
    private TableColumn<tblPassenger, String> colQ2Vehicle;

    @FXML
    private TableColumn<tblPassenger, Double> colQ3Liters;

    @FXML
    private TableColumn<tblPassenger, String> colQ3Name;

    @FXML
    private TableColumn<tblPassenger, String> colQ3Vehicle;

    @FXML
    private TableColumn<tblPassenger, Double> colQ4Liters;

    @FXML
    private TableColumn<tblPassenger, String> colQ4Name;

    @FXML
    private TableColumn<tblPassenger, String> colQ4Vehicle;

    @FXML
    private TableColumn<tblPassenger, Double> colQ5Liters;

    @FXML
    private TableColumn<tblPassenger, String> colQ5Name;

    @FXML
    private TableColumn<tblPassenger, String> colQ5Vehicle;

    @FXML
    private TableView<tblPassenger> tblQ1;

    @FXML
    private TableView<tblPassenger> tblQ2;

    @FXML
    private TableView<tblPassenger> tblQ3;

    @FXML
    private TableView<tblPassenger> tblQ4;

    @FXML
    private TableView<tblPassenger> tblQ5;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnWaitingList;

    public FuelQueue[] queues;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initiateTables();
        try {
            loadData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadData() throws FileNotFoundException {

        ObservableList<tblPassenger> q1= FXCollections.observableArrayList();
        ObservableList<tblPassenger> q2= FXCollections.observableArrayList();
        ObservableList<tblPassenger> q3= FXCollections.observableArrayList();
        ObservableList<tblPassenger> q4= FXCollections.observableArrayList();
        ObservableList<tblPassenger> q5= FXCollections.observableArrayList();

        File file = new File("fillingstation.txt");
        FuelQueue[] queues = new FuelQueue[5];
        int queueIndex=0;
        Scanner dataFile = new Scanner(file);
        while(dataFile.hasNextLine()){
            String s = dataFile.nextLine();
            Passenger[] passengers=new Passenger[6];
            int index=0;

            String[] split = s.split(":");
            String[] s1 = split[0].split(" ");
            FuelQueue fuelQueue=new FuelQueue(Integer.parseInt(s1[1]));

            String[] split1 = split[1].split("/");
            for(int i=0;i<split1.length-1;i++){
                if(split1[i].equals("empty")){
                    passengers[index]=null;
                }else{
                    String[] split2 = split1[i].split(",");
                    String[] name = split2[0].split(" ");
                    Passenger passenger=new Passenger(name[0],name[1],split2[1],Double.parseDouble(split2[2]));
                    passengers[index]=passenger;
                }
                index++;
            }
            fuelQueue.setPassengerQueue(passengers);
            queues[queueIndex]=fuelQueue;
            queueIndex++;
        }


        for(int i=0;i<queues.length;i++){
            FuelQueue queue = queues[i];
            Passenger[] passengerQueue = queue.getPassengerQueue();
            if(i==0){
                for(int x=0;x<passengerQueue.length;x++){
                    Passenger passenger1 = passengerQueue[x];
                    if(passenger1!=null){
                        tblPassenger passenger=new tblPassenger(passenger1.getFirstName()+" "+passenger1.getSecondName(),passenger1.getVehicleNo(),passenger1.getNoLiters());
                        q1.add(passenger);
                    }
                }
            }else if(i==1){
                for(int x=0;x<passengerQueue.length;x++){
                    Passenger passenger1 = passengerQueue[x];
                    if(passenger1!=null){
                        tblPassenger passenger=new tblPassenger(passenger1.getFirstName()+" "+passenger1.getSecondName(),passenger1.getVehicleNo(),passenger1.getNoLiters());
                        q2.add(passenger);
                    }
                }

            }else if(i==2){
                for(int x=0;x<passengerQueue.length;x++){
                    Passenger passenger1 = passengerQueue[x];
                    if(passenger1!=null){
                        tblPassenger passenger=new tblPassenger(passenger1.getFirstName()+" "+passenger1.getSecondName(),passenger1.getVehicleNo(),passenger1.getNoLiters());
                        q3.add(passenger);
                    }
                }

            }else if(i==3){
                for(int x=0;x<passengerQueue.length;x++){
                    Passenger passenger1 = passengerQueue[x];
                    if(passenger1!=null){
                        tblPassenger passenger=new tblPassenger(passenger1.getFirstName()+" "+passenger1.getSecondName(),passenger1.getVehicleNo(),passenger1.getNoLiters());
                        q4.add(passenger);
                    }
                }

            }else{
                for(int x=0;x<passengerQueue.length;x++){
                    Passenger passenger1 = passengerQueue[x];
                    if(passenger1!=null){
                        tblPassenger passenger=new tblPassenger(passenger1.getFirstName()+" "+passenger1.getSecondName(),passenger1.getVehicleNo(),passenger1.getNoLiters());
                        q5.add(passenger);
                    }
                }

            }
        }

        this.queues=queues;

        tblQ1.setItems(q1);
        tblQ2.setItems(q2);
        tblQ3.setItems(q3);
        tblQ4.setItems(q4);
        tblQ5.setItems(q5);

    }

    private void initiateTables() {
        colQ1Name.setCellValueFactory(new PropertyValueFactory<tblPassenger,String>("name"));
        colQ1Vehicle.setCellValueFactory(new PropertyValueFactory<tblPassenger,String>("vehicleNo"));
        colQ1Liters.setCellValueFactory(new PropertyValueFactory<tblPassenger,Double>("noLiters"));

        colQ2Name.setCellValueFactory(new PropertyValueFactory<tblPassenger,String>("name"));
        colQ2Vehicle.setCellValueFactory(new PropertyValueFactory<tblPassenger,String>("vehicleNo"));
        colQ2Liters.setCellValueFactory(new PropertyValueFactory<tblPassenger,Double>("noLiters"));

        colQ3Name.setCellValueFactory(new PropertyValueFactory<tblPassenger,String>("name"));
        colQ3Vehicle.setCellValueFactory(new PropertyValueFactory<tblPassenger,String>("vehicleNo"));
        colQ3Liters.setCellValueFactory(new PropertyValueFactory<tblPassenger,Double>("noLiters"));

        colQ4Name.setCellValueFactory(new PropertyValueFactory<tblPassenger,String>("name"));
        colQ4Vehicle.setCellValueFactory(new PropertyValueFactory<tblPassenger,String>("vehicleNo"));
        colQ4Liters.setCellValueFactory(new PropertyValueFactory<tblPassenger,Double>("noLiters"));

        colQ5Name.setCellValueFactory(new PropertyValueFactory<tblPassenger,String>("name"));
        colQ5Vehicle.setCellValueFactory(new PropertyValueFactory<tblPassenger,String>("vehicleNo"));
        colQ5Liters.setCellValueFactory(new PropertyValueFactory<tblPassenger,Double>("noLiters"));

    }


    public void searchCustomer(ActionEvent actionEvent) {
        String name = txtSearch.getText();

        ObservableList<tblPassenger> q1= FXCollections.observableArrayList();
        ObservableList<tblPassenger> q2= FXCollections.observableArrayList();
        ObservableList<tblPassenger> q3= FXCollections.observableArrayList();
        ObservableList<tblPassenger> q4= FXCollections.observableArrayList();
        ObservableList<tblPassenger> q5= FXCollections.observableArrayList();

        for(int i=0;i<queues.length;i++){
            FuelQueue queue = queues[i];
            Passenger[] passengerQueue = queue.getPassengerQueue();
            if(i==0){
                for(int x=0;x<passengerQueue.length;x++){
                    Passenger passenger1 = passengerQueue[x];
                    if(passenger1!=null){
                        if(passenger1.getFirstName().contains(name)||passenger1.getSecondName().contains(name)){
                            tblPassenger passenger=new tblPassenger(passenger1.getFirstName()+" "+passenger1.getSecondName(),passenger1.getVehicleNo(),passenger1.getNoLiters());
                            q1.add(passenger);
                        }
                    }
                }
            }else if(i==1){
                for(int x=0;x<passengerQueue.length;x++){
                    Passenger passenger1 = passengerQueue[x];
                    if(passenger1!=null){
                        if(passenger1.getFirstName().contains(name)||passenger1.getSecondName().contains(name)){
                            tblPassenger passenger=new tblPassenger(passenger1.getFirstName()+" "+passenger1.getSecondName(),passenger1.getVehicleNo(),passenger1.getNoLiters());
                            q2.add(passenger);
                        }
                    }
                }

            }else if(i==2){
                for(int x=0;x<passengerQueue.length;x++){
                    Passenger passenger1 = passengerQueue[x];
                    if(passenger1!=null){
                        if(passenger1.getFirstName().contains(name)||passenger1.getSecondName().contains(name)){
                            tblPassenger passenger=new tblPassenger(passenger1.getFirstName()+" "+passenger1.getSecondName(),passenger1.getVehicleNo(),passenger1.getNoLiters());
                            q3.add(passenger);
                        }
                    }
                }

            }else if(i==3){
                for(int x=0;x<passengerQueue.length;x++){
                    Passenger passenger1 = passengerQueue[x];
                    if(passenger1!=null){
                        if(passenger1.getFirstName().contains(name)||passenger1.getSecondName().contains(name)){
                            tblPassenger passenger=new tblPassenger(passenger1.getFirstName()+" "+passenger1.getSecondName(),passenger1.getVehicleNo(),passenger1.getNoLiters());
                            q4.add(passenger);
                        }
                    }
                }

            }else{
                for(int x=0;x<passengerQueue.length;x++){
                    Passenger passenger1 = passengerQueue[x];
                    if(passenger1!=null){
                        if(passenger1.getFirstName().contains(name)||passenger1.getSecondName().contains(name)){
                            tblPassenger passenger=new tblPassenger(passenger1.getFirstName()+" "+passenger1.getSecondName(),passenger1.getVehicleNo(),passenger1.getNoLiters());
                            q5.add(passenger);
                        }
                    }
                }

            }
        }

        tblQ1.setItems(q1);
        tblQ2.setItems(q2);
        tblQ3.setItems(q3);
        tblQ4.setItems(q4);
        tblQ5.setItems(q5);
    }

    public void clearTable(ActionEvent actionEvent) throws FileNotFoundException {
        txtSearch.clear();
        loadData();
    }

    public void loadWaitingListView(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("waiting-list.fxml"));
        Scene scene=new Scene(fxmlLoader.load());
        Stage window=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}

