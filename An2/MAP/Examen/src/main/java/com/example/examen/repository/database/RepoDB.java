package com.example.examen.repository.database;

import com.example.examen.domain.entities.Client;
import com.example.examen.domain.entities.Flight;
import com.example.examen.domain.entities.Ticket;
import com.example.examen.domain.exceptions.ValidationException;
import com.example.examen.domain.validators.Validator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RepoDB extends AbstractDBRepo<String, Client> {

    public RepoDB(String dbLink, String username, String password, Validator<Client> validator) {
        super(dbLink, username, password, validator);
    }

    @Override
    public List<Client> loadAllData() {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"clients\"")) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String name = resultSet.getString("name");
                Client client = new Client(username, name);
                try {
                    validator.validate(client);
                    clients.add(client);
                }
                catch (ValidationException e){
                    // skipping this client
                }

            }
            return clients;
        } catch (SQLException e) {
//            throw new RuntimeException("\n\tThere are no Clients to be selected\n");
            return null;
        }
    }

    @Override
    public Client extractEntity(String entityId) {

        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"clients\" where \"username\" = ?")) {
            ps.setString(1, entityId);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            String username = resultSet.getString("username");
            String name = resultSet.getString("name");
            Client client = new Client(username, name);
            try {
                validator.validate(client);
                return client;
            }
            catch(ValidationException e){
                throw new SQLException();
            }


        } catch (SQLException e) {
//            throw new RuntimeException("\n\tThere is no Client to be selected\n");
            return null;
        }
    }

    public void addClient(String username, String name) {
        Client client = new Client(username, name);
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO \"clients\" (\"username\", \"name\") VALUES (" +
                "'" + username + "'" +
                ", " +
                "'"  + name + "'" +
                ");");
        super.execQueryNoResult(sql.toString());
    }

    public Iterable<Flight> findAllFlights() {
        List<Flight> flights = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"flights\"")) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("flightid");
                String from = resultSet.getString("from");
                String to = resultSet.getString("to");
                LocalDateTime dep = resultSet.getTimestamp("departuretime").toLocalDateTime();
                LocalDateTime land = resultSet.getTimestamp("landingtime").toLocalDateTime();
                int seats = resultSet.getInt("seats");
                Flight flight = new Flight(id, from, to, dep, land, seats);

                flights.add(flight);

            }
            return flights;
        } catch (SQLException e) {
            e.printStackTrace();
//            throw new RuntimeException("\n\tThere are no Clients to be selected\n");
            return null;
        }
    }
    public Flight findFlight(Long id) {
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"flights\" where \"flightid\" = " + id.toString())) {
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            String from = resultSet.getString("from");
            String to = resultSet.getString("to");
            LocalDateTime dep = resultSet.getTimestamp("departuretime").toLocalDateTime();
            LocalDateTime land = resultSet.getTimestamp("landingtime").toLocalDateTime();
            int seats = resultSet.getInt("seats");
            Flight flight = new Flight(id, from, to, dep, land, seats);




            return flight;
        } catch (SQLException e) {
//            throw new RuntimeException("\n\tThere is no Client to be selected\n");
            return null;
        }

        }
    public Iterable<Ticket> findAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"tickets\"")) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {

                String username = resultSet.getString("username");
                Long flightid = resultSet.getLong("flightid");
                LocalDateTime purchase = resultSet.getTimestamp("purchasetime").toLocalDateTime();

                Ticket ticket = new Ticket(1, username, flightid, purchase);

                tickets.add(ticket);

            }
            return tickets;
        } catch (SQLException e) {
            e.printStackTrace();
//            throw new RuntimeException("\n\tThere are no Clients to be selected\n");
            return null;
        }
    }
    public void addTicket(String username, Long flightid, LocalDateTime purchase) {
        Ticket ticket = new Ticket(1, username, flightid, purchase);
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO \"tickets\" (\"username\", \"flightid\", \"purchasetime\") VALUES (" +
                "'" + username + "'" +
                ", " +
                "'"  + flightid + "'" +
                ", " +
                "'" + purchase.toString() + "'" +
                ");");
        super.execQueryNoResult(sql.toString());
    }
}
