package com.example.examen.controllers;

import com.example.examen.domain.entities.Hotel;
import com.example.examen.domain.entities.SpecialOffer;
import com.example.examen.service.ServiceAll;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.security.Provider;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SecondPageController {
    private ServiceAll serviceAll;

    ObservableList<SpecialOffer> modelOffers = FXCollections.observableArrayList();
    FilteredList<SpecialOffer> filterOffers = new FilteredList<>(modelOffers, this::filterFunc);

    private boolean filterFunc(SpecialOffer offer) {
//        if datepickers etc, then true
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date offerDate = offer.getStartDate();
        Date offerEndDate = offer.getEndDate();
        if(startPicker == null || endPicker == null)
            return true;
        if(startPicker.getValue() == null || endPicker.getValue() == null)
            return true;
        Date startDate = Date.from(startPicker.getValue().atStartOfDay(defaultZoneId).toInstant());
        Date endDate = Date.from(endPicker.getValue().atStartOfDay(defaultZoneId).toInstant());
        if(offerDate.after(startDate) && offerDate.before(endDate) && offerEndDate.after(startDate) && offerEndDate.before(endDate))
            return true;
        return false;
    }

    @FXML
    TableView<SpecialOffer> testTable;
    @FXML
    TableColumn<SpecialOffer,Double> tableColId;
    @FXML
    TableColumn<SpecialOffer,Double> tableCol1; // hotel Id
    @FXML
    TableColumn<SpecialOffer, Integer> tableCol2; // percent

    @FXML
    Button addButton;

    @FXML
    ComboBox<String> comboBox;
    @FXML
    DatePicker startPicker;
    @FXML
    DatePicker endPicker;

    @FXML
    Spinner<Integer> spinnerElem;

    @FXML
    public void initialize() {
        tableColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableCol1.setCellValueFactory(new PropertyValueFactory<>("hotelId"));
        tableCol2.setCellValueFactory(new PropertyValueFactory<>("percent"));

        startPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            refreshTable();
        });
        endPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            refreshTable();
        });

        testTable.setItems(filterOffers);


    refreshTable();

    }
    public void setService(ServiceAll serviceAll) {
        this.serviceAll = serviceAll;


    }
    public void refreshTable() {
        List<SpecialOffer> offers = StreamSupport.stream(serviceAll.findAllSpecialOffers().spliterator(), false)
                .collect(Collectors.toList());
        modelOffers.setAll(offers);
    }
}
