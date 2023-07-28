package contests.model;


import contests.model.entity.Entitate;

public class CategorieVarsta extends Entitate<Integer> {
    private int varstaMinima;
    private int varstaMaxima;

    public CategorieVarsta() {}
    public CategorieVarsta(Integer id, int varstaMinima, int varstaMaxima) {
        super(id);
        this.varstaMinima = varstaMinima;
        this.varstaMaxima = varstaMaxima;
    }

    public int getVarstaMinima() {
        return varstaMinima;
    }

    public int getVarstaMaxima() {
        return varstaMaxima;
    }

    public void setVarstaMinima(int varstaMinima) {
        this.varstaMinima = varstaMinima;
    }

    public void setVarstaMaxima(int varstaMaxima) {
        this.varstaMaxima = varstaMaxima;
    }

    public String toString() {
        return varstaMinima + "-" + varstaMaxima;
    }
}
