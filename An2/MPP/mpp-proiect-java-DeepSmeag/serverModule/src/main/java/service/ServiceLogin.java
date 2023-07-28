package service;

import contests.model.PersoanaOficiu;
import contests.persistence.PersoanaOficiuRepo;

public class ServiceLogin {
    PersoanaOficiuRepo persoanaOficiuRepo;

    public ServiceLogin(PersoanaOficiuRepo repo) {
        persoanaOficiuRepo = repo;
    }

    public boolean attemptLogin(String username, String password) {
        PersoanaOficiu persoanaOficiu = persoanaOficiuRepo.getOneByUsername(username);
        if (persoanaOficiu == null) {
            return false;
        }
        return persoanaOficiu.getPassword().equals(password);
    }
}
