package com.example.examen.repository;
import com.example.examen.config.Config;
import com.example.examen.domain.entities.*;
import com.example.examen.repository.database.*;
import javafx.collections.ObservableList;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class RepoController {
    private static RepoController instance;

    private static TestDBRepo repoTest;
    private static HotelDBRepo repoHotel;
    private static LocationDBRepo repoLocation;
    private static OffersDBRepo repoOffers;
    private static ClientsDBRepo repoClients;
    private RepoController() {
    }
    public static synchronized RepoController getInstance() {
        if (instance == null) {
            instance = new RepoController();
//            repoFriendships = new FriendshipDBRepo(Config.dbLink, Config.username, Config.password , ValidatorFriendship.getInstance());
            repoTest = new TestDBRepo(Config.dbLink, Config.username, Config.password, null);
            repoHotel = new HotelDBRepo(Config.dbLink, Config.username, Config.password, null);
            repoLocation = new LocationDBRepo(Config.dbLink, Config.username, Config.password, null);
            repoOffers = new OffersDBRepo(Config.dbLink, Config.username, Config.password, null);
            repoClients = new ClientsDBRepo(Config.dbLink, Config.username, Config.password, null);
        }
        return instance;
    }

    public Iterable<TestEntity> findAllTests() {
        return repoTest.loadAllData();
    }
    public Iterable<Hotel> findAllHotels() {
        return repoHotel.loadAllData();
    }
    public Hotel findHotel(Double id) {
        return repoHotel.extractEntity(id);
    }
    public Iterable<Location> findAllLocations() {
        return repoLocation.loadAllData();
    }
    public Location findLocation(Double id) {
        return repoLocation.extractEntity(id);
    }
    public TestEntity findTestEntity(Integer id) {
        return repoTest.extractEntity(id);
    }

    public Iterable<SpecialOffer> getAllSpecialOffers() {
        return repoOffers.loadAllData();
    }

    public Iterable<Client> findAllCLients() {
        return repoClients.loadAllData();
    }
}
