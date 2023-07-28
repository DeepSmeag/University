package contests.model;

import contests.model.entity.Entitate;

public class Inscriere extends Entitate<String> {
    private int idParticipant;
    private int idProba;
    public Inscriere(int idParticipant, int idProba) {
        super(idParticipant + "" + idProba);
        this.idParticipant = idParticipant;
        this.idProba = idProba;
    }

    public int getIdParticipant() {
        return idParticipant;
    }

    public int getIdProba() {
        return idProba;
    }
}
