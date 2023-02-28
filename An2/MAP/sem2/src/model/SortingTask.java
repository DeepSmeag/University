package model;

import container.AbstractSorter;


public class SortingTask extends Task {

    private AbstractSorter sorter;
    private int num[];

    public SortingTask(String _taskID, String _description, String sortStrategy, int num[]) {
        super(_taskID, _description);
        this.sorter = new AbstractSorter(sortStrategy);
        this.num = num;
    }

    @Override
    public void execute() {
        sorter.sort(num);
    }

    public void printNum() {
        for (int i = 0; i < num.length; i++) {
            System.out.printf("%d ", num[i]);
        }
        System.out.printf("\n");
    }
}
