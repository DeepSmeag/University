package com.example.lab09forward.Controllers;

import com.example.lab09forward.config.Config;
import com.example.lab09forward.domain.Entities.Friendship;
import com.example.lab09forward.domain.Entities.Message;
import com.example.lab09forward.domain.Entities.User;
import com.example.lab09forward.service.friendships.ServiceFriendships;
import com.example.lab09forward.service.messages.ServiceMessages;
import com.example.lab09forward.service.users.ServiceUsers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public class MessagesMenuController extends AbstractSubMenuController {
    @FXML
    Pane pagePane;
    @FXML
    VBox receivedMessagesBox;
    @FXML
    VBox sentMessagesBox;

    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    TextField message;
    @FXML
    Text errorSendText;

    @FXML
    Button sendMessageButton;

    @FXML
    public void initialize() {
//        List of friends and their status
        refresh();
        pagePane.setPrefHeight(Config.pagePaneHeight);
        pagePane.setPrefWidth(Config.pagePaneWidth);
    }
//    public void setServices(ServiceUsers serviceUsers, ServiceFriendships serviceFriendships, ServiceMessages serviceMessages) {
//        super.setServices(serviceUsers, serviceFriendships, serviceMessages);
//    }

    public void refresh() {
        try {
//            Clear Boxes of messages
            receivedMessagesBox.getChildren().clear();
            sentMessagesBox.getChildren().clear();
//            Add received header
            Text receivedHeader = new Text("Received Messages");
            configureHeader(receivedHeader);
            receivedMessagesBox.getChildren().add(receivedHeader);
//            Add received messages
            try {
                List<Message> receivedMessages = serviceMessages.getReceivedMessages(loggedUser);
                for (Message message : receivedMessages) {
                    System.out.println(message);
                    Text text = new Text();
                    User sender = serviceUsers.findUser(message.getId1());
                    text.setText(sender.getFirstName() + " " + sender.getLastName() + ": " + message.getMessage() + " | " + message.getDate().toString());
                    receivedMessagesBox.getChildren().add(text);
                }
            } catch (Exception e) {
//                swallowing exception in case of no messages
                e.printStackTrace();
            }
//            Add sent header
            Text sentHeader = new Text("Sent Messages");
            configureHeader(sentHeader);
            sentMessagesBox.getChildren().add(sentHeader);
//            Add sent messages
            try {
                List<Message> sentMessages = serviceMessages.getSentMessages(loggedUser);
                for (Message message : sentMessages) {
                    System.out.println(message);
                    Text text = new Text();
                    User receiver = serviceUsers.findUser(message.getId2());
                    text.setText("To " + receiver.getFirstName() + " " + receiver.getLastName() + ": " + message.getMessage() + " | " + message.getDate().toString());
                    sentMessagesBox.getChildren().add(text);
                }
            } catch (Exception e) {
//                swallowing exception in case of no messages
                e.printStackTrace();
            }
            setErrorTextVisibility(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sendMessage() {
        String firstNameText = firstName.getText();
        String lastNameText = lastName.getText();
        String messageText = message.getText();
        if (messageText.equals("")) {
            System.out.println(firstNameText + " " + lastNameText + " " + messageText);
            setErrorTextVisibility(true);
            return;
        }
        try {
            User receiver = serviceUsers.findUser(firstNameText, lastNameText);
            if (receiver == null || receiver.getId().toString().equals(loggedUser.getId().toString())) {
                throw new Exception();
            }
            serviceMessages.addMessage(loggedUser, receiver, messageText);
            refresh();
            System.out.println("SHould be successful");
        } catch (Exception e) {
            e.printStackTrace();
            setErrorTextVisibility(true);
        }
    }


    private void setErrorTextVisibility(boolean visible) {
        errorSendText.setVisible(visible);
    }

    private void configureHeader(Text header) {
//        header.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        header.setStyle("-fx-font-weight: bold");
//        Set font size
        final int headerFontSize = 20;
        header.setFont(Font.font(headerFontSize));
    }
}
