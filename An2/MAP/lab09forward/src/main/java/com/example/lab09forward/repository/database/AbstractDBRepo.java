package com.example.lab09forward.repository.database;

import com.example.lab09forward.domain.Entities.Entity;
import com.example.lab09forward.domain.validators.Validator;
import com.example.lab09forward.repository.memory.InMemoryRepository0;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDBRepo<ID, E extends Entity<ID>> {


    protected String username;
    protected String password;
    /**
     * dbLink member
     */
    protected String dbLink;


    protected Validator<E> validator;

    /**
     * Constructor for InMemoryRepository0
     *
     * @param validator - Validator
     */
    public AbstractDBRepo(String dbLink, String username, String password, Validator<E> validator) {
        this.dbLink = dbLink;
        this.username = username;
        this.password = password;
        this.validator = validator;
        try{
            Connection connection = DriverManager.getConnection(dbLink, username, password);
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    /**
     * Method to load data from db
     */
    public abstract List<E> loadAllData();

    /**
     * Method to extract entity from string
     *
     * @param entityId - List of attributes
     * @return - E
     */
    public abstract E extractEntity(ID entityId);


    /**
     * Method to execute various queries without repeating implementation
     *
     * @param query - String
     */
    protected void execQueryNoResult(String query){
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement(query)
        ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
