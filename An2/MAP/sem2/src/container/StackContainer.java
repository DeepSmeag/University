package container;

import model.Task;
import utils.Constants;

import java.lang.invoke.ConstantBootstraps;

public class StackContainer extends QSSuperContainer implements Container {


    public StackContainer() {
        super();
    }



    @Override
    public Task remove() {
        if(!isEmpty()) {
            size--;
            return tasks[size];
        }
        return null;
    }

}
