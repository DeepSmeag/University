package com.example.examen.controllers;
import com.example.examen.Main;
import com.example.examen.config.Config;
import com.example.examen.domain.entities.*;
import com.example.examen.service.ServiceAll;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class mainPageController {

    ObservableList<TestEntity> modelTest = FXCollections.observableArrayList();
    ObservableList<Hotel> modelHotel = FXCollections.observableArrayList();
    ObservableList<Location> modelLocation = FXCollections.observableArrayList();

    FilteredList<TestEntity> filteredList = new FilteredList<>(modelTest, this::filterFunc);
    FilteredList<Hotel> filteredListHotel = new FilteredList<>(modelHotel, this::filterFuncHotel);



    SortedList<TestEntity> sortedModel = new SortedList<>(filteredList);
    SortedList<Hotel> sortedHotels = new SortedList<>(filteredListHotel);

    TestEnum testEnum;
    ObservableList<String> modelEnum = FXCollections.observableArrayList(Stream.of(TypeEnum.values())
            .map(Enum::name)
            .collect(Collectors.toList()));
    private ServiceAll serviceAll;
    @FXML
    TableView<Hotel> testTable;
    @FXML
    TableColumn<Hotel,Double> tableColId;
    @FXML
    TableColumn<Hotel,String> tableCol1;
    @FXML
    TableColumn<Hotel, Double> tableCol2;

    @FXML
    Button addButton;

    @FXML
    ComboBox<String> comboBox;
    @FXML
    DatePicker datePicker;

    @FXML
    Spinner<Integer> spinnerElem;


    @FXML
    public void initialize() {

        tableCol1.setCellValueFactory(new PropertyValueFactory<Hotel, String>("hotelName"));
        tableCol2.setCellValueFactory(new PropertyValueFactory<Hotel, Double>("locationId"));
        tableColId.setCellValueFactory(new PropertyValueFactory<Hotel, Double>("id"));

        comboBoxConfigure(comboBox, new ArrayList<>(modelEnum));
        comboBox.valueProperty().addListener(o -> handleSelectionCombo());

//        sortedModel.comparatorProperty().bind(testTable.comparatorProperty());
        sortedModel.setComparator((o1, o2) -> sortFunc(o1, o2));
        sortedHotels.setComparator((o1, o2) -> sortFuncHotels(o1, o2));

        testTable.setItems(sortedHotels);

        testTable.getSelectionModel().selectedItemProperty().addListener(o -> handleSelection());


        datePicker.valueProperty().addListener(o -> handleDatePicker());
        spinnerElem.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        spinnerElem.setEditable(true);
        spinnerElem.valueProperty().addListener(o -> handleSpinner());

        refreshTable();

    }




    private void handleSelectionCombo() {
        refreshTable();
    }

    private void handleSelection() {
        Hotel entity = testTable.getSelectionModel().getSelectedItem();
//        deschidere fereastra noua
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("secondPage.fxml"));
        SecondPageController secondPageController = new SecondPageController();

        secondPageController.setService(serviceAll.getInstance());
        fxmlLoader.setController(secondPageController);
        Scene secondPageScene = new Scene(fxmlLoader.load(), Config.windowWidth, Config.windowHeight);

        Stage stage = new Stage();

        stage.setTitle("2nd Menu");
        stage.setScene(secondPageScene);
        stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void handleModelUpdate() {
        List<TestEntity> users = StreamSupport.stream(serviceAll.findAlltestUsers().spliterator(), false).toList();
        modelTest.setAll(users);
    }
    private void handleDatePicker() {
        System.out.println(datePicker.getValue().toString());
    }
    private void handleSpinner() {
        System.out.println(spinnerElem.getValue());
    }
    public void refreshTable() {
        List<Hotel> hotels = StreamSupport.stream(serviceAll.findAllHotels().spliterator(), false).toList();
        List<Location> locations = StreamSupport.stream(serviceAll.findAllLocations().spliterator(), false).toList();
        List<String> locationsStrings = locations.stream().map(Location::getLocationName).collect(Collectors.toList());
        comboBoxConfigure( comboBox, (ArrayList<String>) locationsStrings);
        modelHotel.setAll(hotels);
    }
    public void setService(ServiceAll serviceAll) {
        this.serviceAll = serviceAll;

        refreshTable();
    }
    public void comboBoxConfigure(ComboBox comboBox, ArrayList<String> list){
        if(comboBox == null) {
            return;
        }
        comboBox.setItems(FXCollections.observableArrayList(list));
    }
    private void textConfigure(Text text, String textContent) {
        text.setText(textContent);
        text.setTextAlignment(TextAlignment.CENTER);
        Font font = new Font(Config.textSize);
        text.setFont(font);
    }
    private void configureButton(Button button, String textContent) {
        button.setText(textContent);
        button.setAlignment(Pos.CENTER);
        button.setPrefWidth(Config.buttonWidth);
        button.setPrefHeight(Config.buttonHeight);
    }
    private void configureHBox(HBox hBox) {
        hBox.setPrefHeight(Config.boxHeight);
        hBox.setPrefWidth(Config.boxWidth);
        hBox.alignmentProperty().setValue(Pos.CENTER);
        hBox.setSpacing(Config.boxSpacing);
    }
    private boolean filterFunc(TestEntity testEntity) {
//        Write any kind of filtering stuff here
        return true;
    }
    private boolean filterFuncHotel(Hotel hotel) {

        if( this.comboBox == null || this.comboBox.getValue() == null) {
            return true;
        }
        String locationName = serviceAll.findLocation(hotel.getLocationId()).getLocationName();
        return comboBox.getValue().equals(locationName);
    }
    private int sortFunc(TestEntity o1, TestEntity o2) {
//        Write any kind of sorting stuff here
        return o1.getId() > o2.getId() ? 1 : -1;
    }
    private int sortFuncHotels(Hotel o1, Hotel o2) {
        return o1.getId() > o2.getId() ? 1 : -1;
    }
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
