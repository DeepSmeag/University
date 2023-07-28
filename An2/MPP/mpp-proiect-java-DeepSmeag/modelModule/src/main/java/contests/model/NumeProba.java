package contests.model;

import contests.model.entity.Entitate;

public class NumeProba extends Entitate<Integer> {
    private String numeProba;
    public NumeProba() {}
    public NumeProba(Integer id, String numeProba){
        super(id);
        this.numeProba = numeProba;
    }
    public String getNumeProba() {
        return numeProba;
    }

    public void setNumeProba(String numeProba) {
        this.numeProba = numeProba;
    }

    public String toString() {
        return numeProba;
    }
}
