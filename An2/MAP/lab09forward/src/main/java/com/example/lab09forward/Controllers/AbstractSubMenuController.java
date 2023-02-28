package com.example.lab09forward.Controllers;


import com.example.lab09forward.domain.Entities.User;
import com.example.lab09forward.service.friendships.ServiceFriendships;
import com.example.lab09forward.service.messages.ServiceMessages;
import com.example.lab09forward.service.users.ServiceUsers;


public abstract class AbstractSubMenuController {

    protected User loggedUser;
    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
    protected ServiceUsers serviceUsers;
    protected ServiceFriendships serviceFriendships;
    protected ServiceMessages serviceMessages;

    public void setServices(ServiceUsers serviceUsers, ServiceFriendships serviceFriendships, ServiceMessages serviceMessages) {
        this.serviceUsers = serviceUsers;
        this.serviceFriendships = serviceFriendships;
        this.serviceMessages = serviceMessages;
    }
}
