package container;

import factory.SortFactory;

public class AbstractSorter {
    String strategy;

    public AbstractSorter(String strategy) {
        this.strategy = strategy;
    }

    public int[] sort(int num[]){
        SortFactory factory = new SortFactory(strategy);
        SortingStrategy sortStrat = factory.generateSorter();
        sortStrat.sort(num);
        return num;
    }
}
