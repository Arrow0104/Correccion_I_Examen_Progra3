package data_acces_layer;

import domain_layer.Project;
import java.io.File;
import java.util.List;

public class ProjectFileStore implements IFileStore<Project> {
    private final Data data;

    public ProjectFileStore(File xmlFile) {
        this.data = Data.getInstance();
    }

    @Override
    public List<Project> readAll() {
        return data.getProjects();
    }

    @Override
    public void writeAll(List<Project> projects) {
        data.getProjects().clear();
        if (projects != null) {
            data.getProjects().addAll(projects);
        }
        data.save();
    }
}

