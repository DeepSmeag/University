package contests.model;

import contests.model.entity.Entitate;

import java.io.Serializable;

public class Participant extends Entitate<Integer> implements Serializable {
    // cel mult 2 probe la care se poate inscrie un participant
    protected String nume;
    protected int varsta;


    public Participant(Integer id, String nume, int varsta) {
        super(id);
        this.nume = nume;
        this.varsta = varsta;
    }

    public String getNume() {
        return nume;
    }

    public int getVarsta() {
        return varsta;
    }



}
