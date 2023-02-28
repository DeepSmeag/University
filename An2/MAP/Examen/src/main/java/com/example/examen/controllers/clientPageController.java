package com.example.examen.controllers;

import com.example.examen.config.Config;
import com.example.examen.domain.entities.Client;
import com.example.examen.domain.entities.Flight;
import com.example.examen.domain.entities.Ticket;
import com.example.examen.service.ServiceAll;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class clientPageController {

    private ServiceAll serviceAll;
    private Client client;
    ObservableList<Flight> modelFlight = FXCollections.observableArrayList();
    FilteredList<Flight> filteredList = new FilteredList<>(modelFlight, this::filterFunc);
    SortedList<Flight> sortedModel = new SortedList<>(filteredList);
    @FXML
    TableView<Flight> tableView;
    @FXML
    TableColumn<Flight, Long> colId;
    @FXML
    TableColumn<Flight, String> colFrom;
    @FXML
    TableColumn<Flight, String> colTo;
    @FXML
    TableColumn<Flight, LocalDateTime> colTime;
    @FXML
    TableColumn<Flight, Integer> colSeats;
    @FXML
    Button buyButton;
    @FXML
    ComboBox<String> comboFrom;
    @FXML
    ComboBox<String> comboTo;
    @FXML
    DatePicker dayPicker;
    private List<clientPageController> pages;

    @FXML
    public void initialize() {
        try {
            colId.setCellValueFactory(new PropertyValueFactory<Flight, Long>("id"));
            colId.setText("id");
            colFrom.setCellValueFactory(new PropertyValueFactory<Flight, String>("from"));
            colFrom.setText("from");
            colTo.setCellValueFactory(new PropertyValueFactory<Flight, String>("to"));
            colTo.setText("to");
            colTime.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("departure"));
            colTime.setText("departure");
            colSeats.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("remainingSeats"));
            colSeats.setText("remaining");


            List<String> options = extractDest((List<Flight>) serviceAll.findAllFlights());
            comboBoxConfigure(comboFrom, (ArrayList<String>) options);
            comboBoxConfigure(comboTo, (ArrayList<String>) options);
            tableView.setItems(filteredList);

            tableView.getSelectionModel().selectedItemProperty().addListener(o -> handleSelection());

            comboFrom.valueProperty().addListener(o -> refreshTable());
            comboTo.valueProperty().addListener(o -> refreshTable());
            dayPicker.valueProperty().addListener(o -> refreshTable());

            refreshTable();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setService(ServiceAll serviceAll, Client client, List<clientPageController> pages) {
        this.serviceAll = serviceAll;
        this.client = client;
        this.pages = pages;
    }

    public int getEmptySeats(Flight flight) {
        return serviceAll.getEmptySeats(flight);

    }

    public void comboBoxConfigure(ComboBox comboBox, ArrayList<String> list) {
        comboBox.setItems(FXCollections.observableArrayList(list));
    }


    public List<String> extractDest(List<Flight> flights) {
        return serviceAll.extractDest(flights);

    }

    public void refreshTable() {
        List<Flight> flights = serviceAll.getUpdatedModel();

        modelFlight.setAll(flights);

    }

    private boolean filterFunc(Flight testEntity) {
        boolean ok = true;
        if(dayPicker.getValue() != null && !dayPicker.getValue().equals(testEntity.getDeparture().toLocalDate())) {
            ok = false;
        }
        if(comboFrom.getValue() != null && !testEntity.getFrom().equals(comboFrom.getValue())) {
            ok = false;
        }
        if(comboTo.getValue() != null && !testEntity.getTo().equals(comboTo.getValue())){
            ok = false;
        }

        return ok;

    }

    private void handleSelection() {
        Flight entity = tableView.getSelectionModel().getSelectedItem();
    }



    public void buyTicket() {
        String username = client.getId();
        Flight selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            this.showErrorAlert("ERR", "Nothing selected");
            return;
        }
        if (getEmptySeats(selected) == 0) {
            this.showErrorAlert("ERR", "No more seats");
            return;
        }
        Long flightid = selected.getId();
        LocalDateTime purchase = LocalDateTime.now();
        serviceAll.addTicket(username, flightid, purchase);
        for (clientPageController page : pages) {
            page.refreshTable();

        }

    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
