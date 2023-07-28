package contests.persistence.interfaces;

import contests.model.CategorieVarsta;

public interface CategorieVarstaRepoInterface extends RepoInterface<CategorieVarsta, Integer> {

    CategorieVarsta getCategorieVarstaByAgeGroup(int minAge, int maxAge);

}
