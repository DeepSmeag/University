package contests.persistence.interfaces;

import contests.model.NumeProba;

public interface NumeProbaInterface extends RepoInterface<NumeProba, Integer> {
    NumeProba getNumeProbaByName(String name);


}
