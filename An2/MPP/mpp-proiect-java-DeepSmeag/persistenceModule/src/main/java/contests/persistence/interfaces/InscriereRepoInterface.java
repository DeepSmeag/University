package contests.persistence.interfaces;

import contests.model.Inscriere;

public interface InscriereRepoInterface extends RepoInterface<Inscriere, Integer> {

    Inscriere getInscriereByParticipantAndProba(int idParticipant, int idProba);
}
