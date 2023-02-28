package controller;

import domain.NotaDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.service.ServiceManager;

import java.io.IOException;
import java.util.ArrayList;
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
    Label welcomeMessage;

    public void welcomeUser(String name) {

        welcomeMessage.setText("Hello "+name);
    }

    @FXML
    ComboBox<String> comboBoxTeme;

    @FXML
    Button clearAllButton;

    @FXML
    Button deleteGradeButton;

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

        Predicate<NotaDto> p4 = n ->
                comboBoxTeme.getSelectionModel().getSelectedItem()==null
                || Objects.equals(comboBoxTeme.getSelectionModel().getSelectedItem(),"all")
                || n.getTemaId().equals(comboBoxTeme.getSelectionModel().getSelectedItem());

        modelGrade.setAll(getNotaDTOList()
                .stream()
                .filter(p1.and(p2).and(p3).and(p4))
                .collect(Collectors.toList()));
    }

    @FXML
    public void clearAllButtonClick() {
        // System.out.println("Clear");

        textFieldName.clear();
        textFieldTema.clear();
        textFieldNota.clear();
        comboBoxTeme.getSelectionModel().clearSelection();

    }

    @FXML
    public void deleteGradeButtonClick() {
        NotaDto nota = tableViewNote.getSelectionModel().getSelectedItem();
        modelGrade.remove(nota);
        deleteGradeButton.setDisable(true);
        editGradeButton.setDisable(true);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("S-a sters nota");
        alert.setContentText("Nota stearsa cu succes");

        alert.showAndWait();
    }

    public void setService(ServiceManager service) {
        this.service = service;
        modelGrade.setAll(getNotaDTOList());
        modelTema = service.findAllHomeWorks()
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());
        List<String> comboBoxTemeItems = new ArrayList<>();
        comboBoxTemeItems.add("all");
        comboBoxTemeItems.addAll(modelTema);
        //modelTema.add(modelTema.size(), "all");
        //comboBoxTeme.getItems().setAll(modelTema);
        comboBoxTeme.getItems().setAll(comboBoxTemeItems);
        //comboBoxTeme.getSelectionModel().selectFirst();


        //deleteGradeButton.setVisible(false);
        //deleteGradeButton.setDisable(true);

        //tableViewNote.set
        //tableViewNote.getSelectionModel()
    }

    @FXML
    public void tableViewNoteMouseClicked() {
        NotaDto nota = tableViewNote.getSelectionModel().getSelectedItem();
        deleteGradeButton.setDisable(false);
        editGradeButton.setDisable(false);
        //System.out.println(nota);
        //service.findAllGrades().remove(nota);
    }

    @FXML
    Button editGradeButton;

    @FXML
    public void editGradeButtonClicked() throws IOException {
        System.out.println("Clicked edit");

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/notaEdit.fxml"));
        AnchorPane root=loader.load();

        NotaEditController ctrl=loader.getController();
        //ctrl.setService(new ServiceManager());
        NotaDto nota = tableViewNote.getSelectionModel().getSelectedItem();
        ctrl.setNota(nota);
        ctrl.setListNote(modelGrade);

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }
}
