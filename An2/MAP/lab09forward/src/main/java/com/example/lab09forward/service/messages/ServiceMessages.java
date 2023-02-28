package com.example.lab09forward.service.messages;

import com.example.lab09forward.domain.Entities.Message;
import com.example.lab09forward.domain.Entities.User;
import com.example.lab09forward.domain.exceptions.ServiceException;
import com.example.lab09forward.repository.RepoControllerUF;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServiceMessages {
    private static ServiceMessages instance = null;

    private static RepoControllerUF repoControllerUF = RepoControllerUF.getInstance();
    private ServiceMessages() {
    }
    public static ServiceMessages getInstance() {
        if (instance == null) {
            instance = new ServiceMessages();
        }
        return instance;
    }
    public void addMessage(User user1, User user2, String messageText) throws ServiceException {
        Message message = new Message(user1.getId(), user2.getId(), messageText, LocalDate.now());
        message.setId(UUID.randomUUID());
        repoControllerUF.saveMessage(message);

    }

    public List<Message> getReceivedMessages(User loggedUser) {
        return repoControllerUF.getReceivedMessages(loggedUser);
    }

    public List<Message> getSentMessages(User loggedUser) {
        return repoControllerUF.getSentMessages(loggedUser);
    }
}
