package contests.network.dto;

import contests.model.InscrieriProba;

import java.io.Serializable;
import java.util.List;

public class ListInscrieriProbaDTO implements Serializable {
    private List<InscrieriProba> inscrieriProbe;

    public ListInscrieriProbaDTO(List<InscrieriProba> inscrieriProbe) {
        this.inscrieriProbe = inscrieriProbe;
    }

    public List<InscrieriProba> getInscrieriProbe() {
        return inscrieriProbe;
    }
}
