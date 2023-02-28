package com.example.examen.repository;
import com.example.examen.config.Config;
import com.example.examen.domain.entities.Client;
import com.example.examen.domain.entities.Flight;
import com.example.examen.domain.entities.Ticket;
import com.example.examen.domain.validators.ValidatorUser;
import com.example.examen.repository.database.RepoDB;

import java.time.LocalDateTime;

public class RepoController {
    private static RepoController instance;

    private static RepoDB repoDB;
    private RepoController() {
    }
    public static synchronized RepoController getInstance() {
        if (instance == null) {
            instance = new RepoController();
//            repoFriendships = new FriendshipDBRepo(Config.dbLink, Config.username, Config.password , ValidatorFriendship.getInstance());
            repoDB = new RepoDB(Config.dbLink, Config.username, Config.password, ValidatorUser.getInstance());
        }
        return instance;
    }



    public void addClient(String userName, String name) {
        repoDB.addClient(userName, name);
    }
    public Iterable<Client> findAllClients() {
        return repoDB.loadAllData();
    }
    public Client findClient(String username) {
        return repoDB.extractEntity(username);
    }
    public Iterable<Flight> findAllFlights() {
        return repoDB.findAllFlights();
    }
    public Flight findFlight(Long id){
        return  repoDB.findFlight(id);
    }
    public Iterable<Ticket> findAllTickets() {
        return repoDB.findAllTickets();
    }
    public void addTicket(String username, Long flightid, LocalDateTime purchase) {
        repoDB.addTicket(username, flightid, purchase);
    }
}
