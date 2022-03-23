package com.example.sqlto_dolist;

public class Task {
    private int Id;
    private String TaskName;

    public Task(int id, String taskName) {
        Id = id;
        TaskName = taskName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }
}
