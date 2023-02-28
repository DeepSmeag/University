package com.example.examen.repository;
import com.example.examen.config.Config;
import com.example.examen.domain.entities.TestEntity;
import com.example.examen.repository.database.TestDBRepo;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class RepoController {
    private static RepoController instance;

    private static TestDBRepo repoTest;
    private RepoController() {
    }
    public static synchronized RepoController getInstance() {
        if (instance == null) {
            instance = new RepoController();
//            repoFriendships = new FriendshipDBRepo(Config.dbLink, Config.username, Config.password , ValidatorFriendship.getInstance());
            repoTest = new TestDBRepo(Config.dbLink, Config.username, Config.password, null);
        }
        return instance;
    }

    public Iterable<TestEntity> findAllTests() {
        return repoTest.loadAllData();
    }
    public TestEntity findTestEntity(Integer id) {
        return repoTest.extractEntity(id);
    }
}
