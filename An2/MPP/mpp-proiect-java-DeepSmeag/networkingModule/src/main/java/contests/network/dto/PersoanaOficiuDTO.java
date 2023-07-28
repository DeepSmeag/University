package contests.network.dto;

import contests.model.PersoanaOficiu;

import java.io.Serializable;

public class PersoanaOficiuDTO implements Serializable {
    PersoanaOficiu persoanaOficiu;
    public PersoanaOficiuDTO() {
    }
    public PersoanaOficiuDTO(PersoanaOficiu persoanaOficiu) {
        this.persoanaOficiu = persoanaOficiu;
    }

    public PersoanaOficiu getPersoanaOficiu() {
        return persoanaOficiu;
    }
}
