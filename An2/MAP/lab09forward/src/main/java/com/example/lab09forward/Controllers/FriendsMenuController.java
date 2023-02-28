package com.example.lab09forward.Controllers;

import com.example.lab09forward.config.Config;
import com.example.lab09forward.domain.Entities.Friendship;
import com.example.lab09forward.domain.Entities.User;
import com.example.lab09forward.service.NetworkService;
import com.example.lab09forward.service.friendships.ServiceFriendships;
import com.example.lab09forward.service.users.ServiceUsers;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.time.LocalDate;
import java.util.UUID;

public class FriendsMenuController extends AbstractSubMenuController {
    //    -----------VBOXes
    @FXML
    VBox userFirstName;
    @FXML
    VBox userLastName;
    @FXML
    VBox friendshipInfo;
    @FXML
    Pane pagePane;

    @FXML
    public void initialize() {
//        List of friends and their status
        refresh();
        pagePane.setPrefHeight(Config.pagePaneHeight);
        pagePane.setPrefWidth(Config.pagePaneWidth);
    }

    private void configureText(Text text, String textContent) {
        text.setText(textContent);
        text.setTextAlignment(TextAlignment.CENTER);
        Font font = new Font(Config.friendTextSize);
        text.setFont(font);
    }

    private void configureButton(Button button, String textContent) {
        button.setText(textContent);
        button.setAlignment(Pos.CENTER);
        button.setPrefWidth(Config.friendButtonWidth);
        button.setPrefHeight(Config.friendButtonHeight);
    }

    private void configureHBox(HBox hBox) {
        hBox.setPrefHeight(Config.friendBoxHeight);
        hBox.setPrefWidth(Config.friendBoxWidth);
        hBox.alignmentProperty().setValue(Pos.CENTER);
        hBox.setSpacing(Config.friendBoxSpacing);
    }

    @FXML
    public void refresh() {
        try {
            userFirstName.getChildren().clear();
            userLastName.getChildren().clear();
            friendshipInfo.getChildren().clear();
            loggedUser.clearFriendsList();
            serviceUsers.buildFriendsList(loggedUser);
            for (User friend : serviceUsers.findAllUsers()) {
                if (friend.getId().equals(loggedUser.getId())) {
                    continue;
                }
                Friendship friendship = serviceFriendships.findFriendship(loggedUser.getId(), friend.getId());
                if (serviceUsers.areFriends(friend, loggedUser)) {
                    friendshipStatusConfirmed(friendship.getDate().toString(), friendship);
                } else if (friendship != null) {
                    friendshipStatusCheck(friendship);

                } else {
                    friendshipStatusAdd(friend);
                }
                setFriendNames(friend);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFriendNames(User friend) {
        Text firstName = new Text(friend.getFirstName());
        Text lastName = new Text(friend.getLastName());
        configureText(firstName, friend.getFirstName());
        configureText(lastName, friend.getLastName());
        HBox hBox = new HBox();
        configureHBox(hBox);
        HBox hBox1 = new HBox();
        configureHBox(hBox1);
        hBox.getChildren().add(firstName);
        hBox1.getChildren().add(lastName);
        userFirstName.getChildren().add(hBox);
        userLastName.getChildren().add(hBox1);
    }

    private void friendshipStatusAdd(User friend) {
        try {
            HBox hBox = new HBox();
            configureHBox(hBox);
            Button button = new Button();
            button.setId(friend.getId().toString());
            configureButton(button, "Add friend");
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new AddFriendButtonClickHandler());
            hBox.getChildren().add(button);
            friendshipInfo.getChildren().add(hBox);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void friendshipStatusCheck(Friendship friendship) {
        HBox hBox = new HBox();

//        1. LoggedUser is sender, so he has to wait for confirmation; show "Pending"
        if (friendship.getId1().equals(loggedUser.getId())) {
            Text text = new Text("");
            configureText(text, "Pending");
            configureHBox(hBox);
            Text text2 = new Text("");
            configureText(text2, "Sent: " + friendship.getSentDate().toString());
            Button button = new Button();
            configureButton(button, "Cancel");
            button.setId(friendship.getId().toString());
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new RemoveFriendButtonClickHandler());
            hBox.getChildren().add(text);
            hBox.getChildren().add(text2);
            hBox.getChildren().add(button);
        }
//        2. LoggedUser is receiver, so he can confirm or deny
        else {
            Button button = new Button();
            Button button2 = new Button();
            configureButton(button, "Confirm");
            configureButton(button2, "Deny");
            button.setId(friendship.getId1().toString());
            button2.setId(friendship.getId1().toString());
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new ConfirmFriendButtonClickHandler());
            button2.addEventHandler(MouseEvent.MOUSE_CLICKED, new DenyFriendButtonClickHandler());
            hBox.getChildren().add(button);
            hBox.getChildren().add(button2);
        }
        friendshipInfo.getChildren().add(hBox);
    }

    private void friendshipStatusConfirmed(String since, Friendship friendship) {
        HBox hBox = new HBox();
        Text text = new Text("");
        Button button = new Button();
        configureText(text, "Since " + since);
        configureButton(button, "Remove friend");
        button.setId(friendship.getId().toString());
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new RemoveFriendButtonClickHandler());
        hBox.getChildren().add(text);
        hBox.getChildren().add(button);
        friendshipInfo.getChildren().add(hBox);
    }

    private class AddFriendButtonClickHandler implements EventHandler<Event> {
        @Override
        public void handle(Event evt) {
            Button button = (Button) evt.getSource();
            String id = button.getId();
            User friend = serviceUsers.findUser(UUID.fromString(id));
            serviceFriendships.addFriendship(loggedUser, friend, LocalDate.now().toString());
            refresh();
        }
    }

    private class ConfirmFriendButtonClickHandler implements EventHandler<Event> {
        @Override
        public void handle(Event evt) {
            Button button = (Button) evt.getSource();
            String id = button.getId();
            User friend = serviceUsers.findUser(UUID.fromString(id));
            Friendship friendship = serviceFriendships.findFriendship(loggedUser.getId(), friend.getId());
            serviceFriendships.confirmFriendship(friendship);
            refresh();
        }
    }

    private class DenyFriendButtonClickHandler implements EventHandler<Event> {
        @Override
        public void handle(Event evt) {
            Button button = (Button) evt.getSource();
            String id = button.getId();
            User friend = serviceUsers.findUser(UUID.fromString(id));
//            Friendship friendship = serviceFriendships.findFriendship(loggedUser.getId(), friend.getId());
            serviceFriendships.removeFriendship(friend, loggedUser);
            refresh();
        }
    }

    private class RemoveFriendButtonClickHandler implements EventHandler<Event> {
        @Override
        public void handle(Event evt) {
            Button button = (Button) evt.getSource();
            String id = button.getId();

            Friendship friendship = serviceFriendships.findFriendship(UUID.fromString(id));

            User friend;
            if (friendship.getId1().equals(loggedUser.getId())) {
                friend = serviceUsers.findUser(friendship.getId2());
            } else {
                friend = serviceUsers.findUser(friendship.getId1());
            }
            serviceFriendships.removeFriendship(friend, loggedUser);
            refresh();
        }
    }
}
