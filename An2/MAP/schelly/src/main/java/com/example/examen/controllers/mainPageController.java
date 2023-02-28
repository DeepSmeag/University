package com.example.examen.controllers;
import com.example.examen.config.Config;
import com.example.examen.domain.entities.TestEntity;
import com.example.examen.domain.entities.TestEnum;
import com.example.examen.service.ServiceAll;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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
    FilteredList<TestEntity> filteredList = new FilteredList<>(modelTest, this::filterFunc);
    SortedList<TestEntity> sortedModel = new SortedList<>(filteredList);


    TestEnum testEnum;
    ObservableList<String> modelEnum = FXCollections.observableArrayList(Stream.of(TestEnum.values())
            .map(Enum::name)
            .collect(Collectors.toList()));
    private ServiceAll serviceAll;
    @FXML
    TableView<TestEntity> testTable;
    @FXML
    TableColumn<TestEntity,Integer> tableColId;
    @FXML
    TableColumn<TestEntity,String> tableCol1;
    @FXML
    TableColumn<TestEntity, Date> tableCol2;

    @FXML
    Button addButton;

    @FXML
    ComboBox<String> comboBox;
    @FXML
    DatePicker datePicker;
    Button alertButton;
    @FXML
    Spinner<Integer> spinnerElem;


    @FXML
    public void initialize() {

        tableCol1.setCellValueFactory(new PropertyValueFactory<TestEntity, String>("name"));
        tableCol2.setCellValueFactory(new PropertyValueFactory<TestEntity, Date>("date"));
        tableColId.setCellValueFactory(new PropertyValueFactory<TestEntity, Integer>("id"));

//        sortedModel.comparatorProperty().bind(testTable.comparatorProperty());
        sortedModel.setComparator((o1, o2) -> sortFunc(o1, o2));

        testTable.setItems(sortedModel);

        testTable.getSelectionModel().selectedItemProperty().addListener(o -> handleSelection());

        comboBoxConfigure(comboBox, new ArrayList<>(modelEnum));
        datePicker.valueProperty().addListener(o -> handleDatePicker());
        spinnerElem.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        spinnerElem.setEditable(true);
        spinnerElem.valueProperty().addListener(o -> handleSpinner());

    }




    private void handleSelectionCombo() {
        System.out.println(comboBox.getSelectionModel().getSelectedItem().toString());
    }

    private void handleSelection() {
        TestEntity entity = testTable.getSelectionModel().getSelectedItem();
        tableColId.setText(String.valueOf(entity.getId()));
        tableCol1.setText(entity.getName());
        tableCol2.setText(String.valueOf(entity.getDate()));
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
    public void setService(ServiceAll serviceAll) {
        this.serviceAll = serviceAll;

        List<TestEntity> users = StreamSupport.stream(serviceAll.findAlltestUsers().spliterator(), false).toList();


        modelTest.setAll(users);


    }
    public void comboBoxConfigure(ComboBox comboBox, ArrayList<String> list){
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
    private int sortFunc(TestEntity o1, TestEntity o2) {
//        Write any kind of sorting stuff here
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
