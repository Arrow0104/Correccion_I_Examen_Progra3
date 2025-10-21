package service_layer;

import domain_layer.Task;
import data_acces_layer.Data;
import utilities.ChangeType;
import java.util.ArrayList;
import java.util.List;

public class TaskService implements IService<Task> {
    private final Data data;
    private final List<IServiceObserver<Task>> listeners = new ArrayList<>();
    private static TaskService instance;

    private TaskService() {
        this.data = Data.getInstance();
    }

    public static TaskService getInstance() {
        if (instance == null) instance = new TaskService();
        return instance;
    }

    @Override
    public void agregar(Task entity) {
        data.getTasks().add(entity);
        data.save();
        notifyObservers(ChangeType.CREATED, entity);
    }

    @Override
    public List<Task> leerTodos() {
        return data.getTasks();
    }

    @Override
    public Task leerPorId(int id) {
        String formattedId = String.format("T-%03d", id); // Formatear el ID
        return data.getTasks().stream()
                .filter(t -> t.getNumero().equals(formattedId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizar(Task entity) {
        List<Task> tasks = data.getTasks();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getNumero().equals(entity.getNumero())) {
                tasks.set(i, entity);
                data.save();
                notifyObservers(ChangeType.UPDATED, entity);
                break;
            }
        }
    }

    @Override
    public void addObserver(IServiceObserver<Task> listener) {
        if (listener != null) listeners.add(listener);
    }

    @Override
    public void removeObserver(IServiceObserver<Task> listener) {
        listeners.remove(listener);
    }

    private void notifyObservers(ChangeType type, Task entity) {
        for (IServiceObserver<Task> l : listeners) {
            l.onDataChanged(type, entity);
        }
    }
}



