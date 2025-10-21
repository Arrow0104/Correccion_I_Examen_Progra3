package service_layer;

import domain_layer.Project;
import domain_layer.Task;
import data_acces_layer.Data;
import utilities.ChangeType;
import java.util.ArrayList;
import java.util.List;

public class ProjectService implements IService<Project> {
    private final Data data;
    private final List<IServiceObserver<Project>> listeners = new ArrayList<>();
    private static ProjectService instance;

    private ProjectService() {
        this.data = Data.getInstance();
    }

    public static ProjectService getInstance() {
        if (instance == null) instance = new ProjectService();
        return instance;
    }

    @Override
    public void agregar(Project entity) {
        data.getProjects().add(entity);
        data.save();
        notifyObservers(ChangeType.CREATED, entity);
    }

    @Override
    public List<Project> leerTodos() {
        return data.getProjects();
    }

    @Override
    public Project leerPorId(int id) {
        String formattedId = String.format("PROJ-%03d", id); // Formatear el ID
        return data.getProjects().stream()
                .filter(p -> p.getCodigo().equals(formattedId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizar(Project entity) {
        List<Project> projects = data.getProjects();
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getCodigo().equals(entity.getCodigo())) {
                projects.set(i, entity);
                data.save();
                notifyObservers(ChangeType.UPDATED, entity);
                break;
            }
        }
    }
    public void reasignarCodigos() {
        List<Project> projects = data.getProjects();
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            String nuevoCodigoProyecto = String.format("PROJ-%03d", i + 1);
            project.setCodigo(nuevoCodigoProyecto);

            List<Task> tareas = project.getTareas();
            for (int j = 0; j < tareas.size(); j++) {
                Task tarea = tareas.get(j);
                String nuevoCodigoTarea = String.format("T-%03d", j + 1);
                tarea.setNumero(nuevoCodigoTarea);
            }
        }
        data.save();
    }

    @Override
    public void addObserver(IServiceObserver<Project> listener) {
        if (listener != null) listeners.add(listener);
    }

    @Override
    public void removeObserver(IServiceObserver<Project> listener) {
        listeners.remove(listener);
    }

    private void notifyObservers(ChangeType type, Project entity) {
        for (IServiceObserver<Project> l : listeners) {
            l.onDataChanged(type, entity);
        }
    }
}



