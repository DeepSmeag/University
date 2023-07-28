package com.example.issproject.controllers;

import com.example.issproject.models.*;
import com.example.issproject.repository.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowRoomControl {
    ReservationRepo reservationRepo;
    RoomConfigurationRepo roomConfigurationRepo;
    SeatRepo seatRepo;
    ShowRepo showRepo;
    SpectatorRepo spectatorRepo;
    //    selected seats map
    HashMap<Integer, SeatsEntity> selectedSeats = new HashMap<>();
    @FXML
    Button backButton;
    @FXML
    FlowPane seatsPane;
    @FXML
    TextField nameField;
    @FXML
    TextField ageField;
    @FXML
    Button reservationButton;

    @FXML
    public void initialize() {
        System.out.println("show room initialized");
        reservationRepo = new ReservationRepo();
        roomConfigurationRepo = new RoomConfigurationRepo();
        seatRepo = new SeatRepo();
        showRepo = new ShowRepo();
        spectatorRepo = new SpectatorRepo();
//        seatsPane.setOpacity(0.3);
        seatsPane.setStyle("-fx-background-color: rgba(128, 128, 128, 0.2);");
        showRoomConfig();
    }

    public void showRoomConfig() {
        selectedSeats.clear();
        seatsPane.getChildren().clear();
        ShowsEntity show = showRepo.getCurrentShow();
        RoomconfigurationsEntity room = roomConfigurationRepo.retrieveConfig(show.getRoomid());
        String seats = room.getSeatsid();
        String[] seatsArray = seats.split(",");
        List<SeatsEntity> seatsList = new ArrayList<>();
        for (var seatId : seatsArray) {
//            System.out.println("seat id: " + seatId);
            seatsList.add(seatRepo.findSeat(Integer.parseInt(seatId)));
        }
        HashMap<Integer, SeatsEntity> seatsMap = new HashMap<>();
//        get all reserved seats
        var reservations = reservationRepo.getReservations();
        for (var reservation : reservations) {
            if (reservation.getShowid() == show.getShowid()) {
                var reservedSeats = reservation.getSeatsid();
                String[] reservedSeatsArray = reservedSeats.split(",");
//                build list of reserved seats
                for (var seatId : reservedSeatsArray) {
                    seatsMap.put(Integer.parseInt(seatId), seatRepo.findSeat(Integer.parseInt(seatId)));
                }
            }
        }
        for (var seat : seatsList) {
            Button seatButton = new Button();
            seatButton.setId(String.valueOf(seat.getSeatid()));
            seatButton.setText(seat.getSeatname());
            seatButton.setOpacity(1);
            seatButton.setPrefSize(50, 50);
            seatButton.setStyle("-fx-background-color: #00ff00");
            if (seatsMap.containsKey(seat.getSeatid())) {
                seatButton.setStyle("-fx-background-color: #aaaaaa");
                seatButton.setDisable(true);
            } else {

                seatButton.setOnAction(e -> {
                            if (seatButton.getStyle().equals("-fx-background-color: #00ff00")) {
                                seatButton.setStyle("-fx-background-color: #ff0000");
                                selectedSeats.put(seat.getSeatid(), seat);
                            } else {
                                seatButton.setStyle("-fx-background-color: #00ff00");
                                selectedSeats.remove(seat.getSeatid());
                            }
                            System.out.println("seat " + seatButton.getId() + " pressed");
                        }
                );
            }
            seatsPane.getChildren().add(seatButton);
        }

    }

    @FXML
    public void pressReservationButton() {
        System.out.println("reservation pressed");
        try {
            String name = nameField.getText();
            if (name.equals("")) {
                throw new IOException();
            }
            int age = Integer.parseInt(ageField.getText());

//        construct string with list of selected seats ids
            String seats = "";
            for (var seat : selectedSeats.values()) {
                seats += seat.getSeatid() + ",";
            }
            seats = seats.substring(0, seats.length() - 1);
            if(seats.equals("")){
                throw new Exception();
            }
            System.out.println("seats: " + seats);
            ShowsEntity show = showRepo.getCurrentShow();
            SpectatorsEntity spectator = spectatorRepo.findSpectator(name, age);
            if(spectator == null){
                spectator = spectatorRepo.addSpectator(name, age);
            }
            ReservationsEntity reservation = reservationRepo.addReservation(spectator.getSpectatorid(), show.getShowid(), seats);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Reservation successful");
            alert.setContentText("Your reservation is now confirmed.");
            alert.showAndWait();
            showRoomConfig();
        } catch (NumberFormatException e) {
//            popup laert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid age");
            alert.setContentText("Please enter a valid age");
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid name");
            alert.setContentText("Please enter a valid name");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid reservation");
            alert.setContentText("Please select at least one seat");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    public void pressBackButton() throws IOException {
        System.out.println("back pressed");
//        goes back to main menu window
        String basePath = System.getProperty("user.dir");
        String resourcePath = basePath + "/src/main/resources/com.example.issproject/mainMenu.fxml";
        File resourceFile = new File(resourcePath);
        if (resourceFile.exists()) {
            System.out.println("Resource found at: " + resourcePath);
        } else {
            System.out.println("Resource not found.");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:///" + resourcePath));
        MainControl controller = new MainControl();
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 426, 647);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setTitle("Main Menu");
        stage.setScene(scene);
    }

    public void testRepo() {
        //        testing the repository
        reservationRepo.addReservation(1, 1, "1");
        var reservations = reservationRepo.getReservations();
        for (var reservation : reservations) {
            System.out.println(reservation.getReservationid());
        }
        roomConfigurationRepo.retrieveConfig(1);
        seatRepo.findSeat(1);
        showRepo.getCurrentShow();
        spectatorRepo.addSpectator("Ion", 20);
        SpectatorsEntity spectator = spectatorRepo.findSpectator("Ion", 20);
        System.out.println(spectator.getSpectatorid());
    }
}
