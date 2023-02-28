package com.example.examen.service;



import com.example.examen.domain.entities.*;
import com.example.examen.domain.exceptions.RepoException;
import com.example.examen.domain.exceptions.ServiceException;
import com.example.examen.repository.RepoController;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * Class models ServiceUsers type of service
 * Singleton Pattern
 */
public class ServiceAll {
    /**
     * instance - instance of ServiceUsers
     */
    private static ServiceAll instance;

    /**
     * Constructor for ServiceUsers - private because of Singleton Pattern
     */
    private ServiceAll() {
    }
    /**
     * Method to get Instance of ServiceUsers
     * @return - instance of ServiceUsers
     */
    public static synchronized ServiceAll getInstance() {
        if (instance == null) {
            instance = new ServiceAll();


        }
        return instance;
    }


    public void addClient(String userName, String name) {
        RepoController.getInstance().addClient(userName, name);
    }

    /**
     * Method to modify a user
     * @param names1 - original names
     * @param names2 - new names
     * @throws RepoException - error in case repo does not find original user
     */
    public void modUser(List<String> names1, List<String> names2) throws RepoException{
//        repoControllerUF.modifyUser(names1, names2);
    }

    /**
     * Method to delete User
     * @param firstName - first name of user
     * @param lastName - last name of user
     * @throws ServiceException - if user doesn't exist
     */
    public void removeUser(String firstName, String lastName) throws RepoException {
//        User user = repoControllerUF.findUserByName(firstName, lastName);
//        repoControllerUF.deleteUser(user.getId());
    }



    /**
     * Method to find all users
     * @return - Iterable of Users
     */
    public Iterable<Client> findAllClients() {
        return RepoController.getInstance().findAllClients();
    }


    public Client findClient(String username) {
        return RepoController.getInstance().findClient(username);

    }

    public Iterable<Flight> findAllFlights() {
        return RepoController.getInstance().findAllFlights();
    }
    public Flight findFlight(Long id) {
        return RepoController.getInstance().findFlight(id);
    }
    public Iterable<Ticket> findAllTickets() {
        return  RepoController.getInstance().findAllTickets();
    }
    public void addTicket(String username, Long flightid, LocalDateTime purchase) {
        RepoController.getInstance().addTicket(username, flightid, purchase);
    }

    public int getEmptySeats(Flight flight) {
        Iterable<Ticket> tickets = findAllTickets();
        if (tickets == null) {
            return flight.getSeats();
        }
        int no = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getFlightid() == flight.getId()) {
                no++;
            }
        }
        return flight.getSeats() - no;
    }

    public List<String> extractDest(List<Flight> flights) {
        List<String> strings = new ArrayList<>();
        for (Flight flight : flights) {

            strings.add(flight.getFrom());
            strings.add(flight.getTo());
        }
        List<String> listDistinct = strings.stream().distinct().collect(Collectors.toList());
        return listDistinct;
    }

    public List<Flight> getUpdatedModel() {
        List<Flight> flights = StreamSupport.stream(findAllFlights().spliterator(), false)
                .collect(Collectors.toList());
        for (Flight flight : flights) {
            flight.setRemainingSeats(getEmptySeats(flight));
        }
        return flights;
    }
}
