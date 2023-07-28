package contests.persistence.interfaces;

import contests.model.PersoanaOficiu;

public interface PersoanaOficiuInterface extends RepoInterface<PersoanaOficiu, Integer> {
    PersoanaOficiu getOneByUsername(String username);
}
