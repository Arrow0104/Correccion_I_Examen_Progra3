package data_acces_layer;

import domain_layer.Task;
import java.io.File;
import java.util.List;

public class TaskFileStore implements IFileStore<Task> {
    private final Data data;

    public TaskFileStore(File xmlFile) {
        this.data = Data.getInstance();
    }

    @Override
    public List<Task> readAll() {
        return data.getTasks();
    }

    @Override
    public void writeAll(List<Task> tasks) {
        data.getTasks().clear();
        if (tasks != null) {
            data.getTasks().addAll(tasks);
        }
        data.save();
    }
}


