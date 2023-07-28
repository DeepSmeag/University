package service;

import contests.model.Inscriere;
import contests.persistence.InscriereRepo;

import java.util.List;

public class ServiceInscrieri {
    InscriereRepo inscriereRepo;

    public ServiceInscrieri(InscriereRepo repo) {
        inscriereRepo = repo;
    }

    public List<Inscriere> getByParticipantId(Integer idParticipant) {
        return inscriereRepo.getInscrieriByParticipantId(idParticipant);
    }

    public void addInscriere(Integer idParticipant, Integer idProba) {
        Inscriere inscriere = new Inscriere(idParticipant, idProba);
        inscriereRepo.saveEntity(inscriere);
    }
}
