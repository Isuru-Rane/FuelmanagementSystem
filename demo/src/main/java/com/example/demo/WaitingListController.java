package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class WaitingListController implements Initializable {

    @FXML
    private TableColumn<tblPassenger, Double> colWaitingLiters;

    @FXML
    private TableColumn<tblPassenger, String> colWaitingName;

    @FXML
    private TableColumn<tblPassenger, String> colWaitingVehicle;

    @FXML
    private TableView<tblPassenger> tblWaitingList;

    @FXML
    private Button btnQueueStatus;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initiateTable();
        try {
            loadData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadData() throws FileNotFoundException {

        ObservableList<tblPassenger> list= FXCollections.observableArrayList();

        File file=new File("waitinglist.txt");
        Scanner data=new Scanner(file);
        Scanner dataFile = new Scanner(file);
        while(dataFile.hasNextLine()){
            String s = dataFile.nextLine();
            String[] split = s.split(",");
            tblPassenger passenger=new tblPassenger(split[0],split[1],Double.parseDouble(split[2]));
            list.add(passenger);
        }

        tblWaitingList.setItems(list);
    }

    private void initiateTable() {
        colWaitingName.setCellValueFactory(new PropertyValueFactory<tblPassenger,String>("name"));
        colWaitingVehicle.setCellValueFactory(new PropertyValueFactory<tblPassenger,String>("vehicleNo"));
        colWaitingLiters.setCellValueFactory(new PropertyValueFactory<tblPassenger,Double>("noLiters"));
    }

    public void loadQueueStatus(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("queue-status.fxml"));
        Scene scene=new Scene(fxmlLoader.load());
        Stage window=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
