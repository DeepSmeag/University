package controller;

import domain.NotaDto;
import domain.Tema;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import service.ServiceManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NotaController {

    ObservableList<NotaDto> modelGrade = FXCollections.observableArrayList();
    List<String> modelTema;
    private ServiceManager service;


    @FXML
    TableColumn<NotaDto, String> tableColumnName;
    @FXML
    TableColumn<NotaDto, String> tableColumnTema;
    @FXML
    TableColumn<NotaDto, Double> tableColumnNota;
    @FXML
    TableView<NotaDto> tableViewNote;
    //----------------------end TableView fx:id----------------

    @FXML
    TextField textFieldName;
    @FXML
    TextField textFieldTema;
    @FXML
    TextField textFieldNota;

    @FXML
    ComboBox<String> comboBoxTeme;

    @FXML
    Button buttonClear;

    @FXML
    public void initialize() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<NotaDto, String>("studentName"));
        tableColumnTema.setCellValueFactory(new PropertyValueFactory<NotaDto, String>("temaId"));
        tableColumnNota.setCellValueFactory(new PropertyValueFactory<NotaDto, Double>("nota"));

        tableViewNote.setItems(modelGrade);

        textFieldName.textProperty().addListener(o -> handleFilter());
        textFieldTema.textProperty().addListener(o -> handleFilter());
        textFieldNota.textProperty().addListener(o -> handleFilter());

        comboBoxTeme.getSelectionModel().selectedItemProperty().addListener(
                (x,y,z)->handleFilter()
        );

        buttonClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonClear_OnClick();
            }
        });

        tableViewNote.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NotaDto>() {
            @Override
            public void changed(ObservableValue<? extends NotaDto> observable, NotaDto oldValue, NotaDto newValue) {

            }
        });
    }

    private List<NotaDto> getNotaDTOList() {
        return service.findAllGrades()
                .stream()
                .map(n -> new NotaDto(n.getStudent().getName(), n.getTema().getId(), n.getValue(), n.getProfesor()))
                .collect(Collectors.toList());
    }

    private void handleFilter() {
        Predicate<NotaDto> p1 = n -> n.getStudentName().startsWith(textFieldName.getText());
        Predicate<NotaDto> p2 = n -> n.getTemaId().startsWith(textFieldTema.getText());
        Predicate<NotaDto> p3 = n -> {
            try {
                return n.getNota() > Double.parseDouble(textFieldNota.getText());
            } catch (NumberFormatException ex) {
                return true;
            }
        };

        Predicate<NotaDto> p4 = n -> n.getTemaId().equals(comboBoxTeme.getSelectionModel().getSelectedItem()) ||
                                Objects.equals(comboBoxTeme.getSelectionModel().getSelectedItem(), "all") ||
                                comboBoxTeme.getSelectionModel().isEmpty();

        modelGrade.setAll(getNotaDTOList()
                .stream()
                .filter(p1.and(p2).and(p3).and(p4))
                .collect(Collectors.toList()));
    }


    public void setService(ServiceManager service) {
        this.service = service;
        modelGrade.setAll(getNotaDTOList());
        List<String> modelTema = new LinkedList<>();
        modelTema.add("all");
        modelTema.addAll(service.findAllHomeWorks()
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList()));
        comboBoxTeme.getItems().setAll(modelTema);
//        comboBoxTeme.getSelectionModel().selectFirst();
    }


    public void buttonClear_OnClick(){
//        System.out.println("Clear");
        textFieldName.clear();
        textFieldNota.clear();
        textFieldTema.clear();
        comboBoxTeme.getSelectionModel().clearSelection();
    }
}
