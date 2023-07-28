package contests.persistence.interfaces;

import contests.model.Participant;

import java.util.List;

public interface ParticipantRepoInterface extends RepoInterface<Participant, Integer> {


    Participant getParticipantByName(String name);
    List<Participant> getParticipantsByAgeCategory(int ageMin, int ageMax);
}
