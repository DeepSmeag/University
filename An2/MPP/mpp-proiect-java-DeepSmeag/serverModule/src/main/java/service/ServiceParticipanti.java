package service;

import contests.model.Participant;
import contests.persistence.ParticipantRepo;

import java.util.List;

public class ServiceParticipanti {

    ParticipantRepo participantRepo ;
    public ServiceParticipanti(ParticipantRepo repo) {
        participantRepo = repo;
    }
    public List<Participant> getAll() {
        return participantRepo.getAll();
    }
    public List<Participant> getByProba(int idProba) {
        return participantRepo.getParticipantsByProba(idProba);
    }

    public Participant getByName(String participantName) {
        return participantRepo.getParticipantByName(participantName);
    }

    public void addParticipant(String participantName, int age) {
        Participant participant = new Participant(1, participantName, age);
        participantRepo.saveEntity(participant);
    }
}
