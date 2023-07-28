package contests.persistence.interfaces;

import contests.model.NumeProba;
import contests.model.Proba;

import java.util.List;

public interface ProbaRepoInterface extends RepoInterface<Proba, Integer> {


    List<Proba> getProbeByName(NumeProba numeProba);
//    List<Proba> getProbeByAgeCategory(int ageMin, int ageMax);

}
