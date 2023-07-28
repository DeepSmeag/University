package service;

import contests.model.InscrieriProba;
import contests.persistence.InscriereRepo;
import contests.persistence.ProbaRepo;

import java.util.ArrayList;
import java.util.List;

public class ServiceMergeReg {
    ProbaRepo probaRepo;
    InscriereRepo inscriereRepo;

    public ServiceMergeReg(ProbaRepo repo1, InscriereRepo repo2) {
        probaRepo = repo1;
        inscriereRepo = repo2;
    }

    public List<InscrieriProba> getInscrieriMerged() {
        List<InscrieriProba> inscrieriProbaList = new ArrayList<>();

        probaRepo.getAll().forEach(proba -> {
            int numRegistrations = inscriereRepo.getNumRegistrations(proba.getId());
            InscrieriProba inscrieriProba = new InscrieriProba(proba.getId(), numRegistrations, proba.getNumeProba().toString(), proba.getCategorieVarsta().toString());
            inscrieriProbaList.add(inscrieriProba);
            System.out.println(proba.getNumeProba() + " " + proba.getCategorieVarsta() + " " + numRegistrations);
//            System.out.println(proba.getNumeProba());
        });

        return inscrieriProbaList;
    }
}
