package contests.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import contests.model.entity.Entitate;

public class Proba extends Entitate<Integer> {
//    mai multe probe pot fi asociate unui concurs
//    o proba are un singur concurs asociat
//    @JsonUnwrapped
    private NumeProba numeProba;
//    @JsonUnwrapped
    private CategorieVarsta categorieVarsta;

    public Proba() {}
    public Proba(Integer id, NumeProba numeProba, CategorieVarsta categorieVarsta) {
        super(id);
        this.numeProba = numeProba;
        this.categorieVarsta = categorieVarsta;
    }

    public NumeProba getNumeProba() {
        return numeProba;
    }

    public CategorieVarsta getCategorieVarsta() {
        return categorieVarsta;
    }

    public void setNumeProba(NumeProba numeProba) {
        this.numeProba = numeProba;
    }

    public void setCategorieVarsta(CategorieVarsta categorieVarsta) {
        this.categorieVarsta = categorieVarsta;
    }

    @Override
    public String toString() {
        return "Proba{" +
                "id=" + getId() +
                ", numeProba=" + numeProba +
                ", categorieVarsta=" + categorieVarsta +
                '}';
    }
}
