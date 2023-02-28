package model;

import java.util.Objects;

public abstract class Task {
    private String taskID;
    private String description;

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskID() { return taskID;}
    public String getDescription() { return description;}

    public Task(String _taskID, String _description)
    {
        taskID = _taskID;
        description = _description;
    }

    public abstract void execute();

    @Override
    public String toString() { return taskID + " " + description; }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if(!(obj instanceof Task))
        {
            return false;
        }
        Task task = (Task)obj;
        return Objects.equals(getTaskID(), task.taskID) &&
                Objects.equals(getDescription(), task.description);
    }

    @Override
    public int hashCode(){
        return Objects.hash(taskID, description);
    }
}
