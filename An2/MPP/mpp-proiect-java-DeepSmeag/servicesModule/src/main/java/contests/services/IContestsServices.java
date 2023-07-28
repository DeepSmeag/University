package contests.services;

import contests.model.InscrieriProba;
import contests.model.Participant;
import contests.model.PersoanaOficiu;


import java.util.List;

public interface IContestsServices {
    void login(PersoanaOficiu user, String client) throws ContestsException;

    void logout(PersoanaOficiu user, String client) throws ContestsException;

    List<InscrieriProba> getContests() throws ContestsException;
    List<Participant> getParticipants() throws ContestsException;
    List<Participant> getParticipants(int idProba) throws ContestsException;

    void attemptRegisterParticipant(String participantName, int age, List<InscrieriProba> selectedItems) throws ContestsException;
}
