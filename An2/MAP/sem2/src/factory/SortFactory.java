package factory;
import container.MergeSortStrategy;
import container.QSortStrategy;
import container.SortingStrategy;

public class SortFactory {
    String strategy;

    public SortFactory(String strategy) {
        this.strategy = strategy;
    }
    public SortingStrategy generateSorter(){
        if(strategy == "qsort"){
            return new QSortStrategy();
        } else if (strategy == "mergesort") {
            return new MergeSortStrategy();
        }
        // just a default value
        return new QSortStrategy();
    }
}
