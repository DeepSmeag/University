package controller;


import domain.NotaDto;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import utils.service.ServiceManager;

public class NotaEditController {
    @FXML
    TextField studentText;

    @FXML
    TextField profText;

    @FXML
    TextField notaText;

    NotaDto nota;

    public void setNota(NotaDto nota) {
        this.nota=nota;
        studentText.setText(nota.getStudentName());
        profText.setText(nota.getTemaId());
        notaText.setText(""+nota.getNota());
    }

    ObservableList<NotaDto> list;

    void setListNote(ObservableList<NotaDto> lst) {list=lst;}
    @FXML
    public void okButtonPressed(){
        double valNota=Double.parseDouble(notaText.getText());
        list.remove(nota);
        nota.setNota(valNota);
        list.add(nota);

    }
}
