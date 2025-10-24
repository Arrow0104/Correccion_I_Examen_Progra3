package presentation_layer.Controllers;

import domain_layer.Project;
import domain_layer.Task;
import domain_layer.User;
import service_layer.ProjectService;
import service_layer.TaskService;
import service_layer.UserService;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class TasksController {
    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;
    private final JTable tasksTable;
    private final JComboBox<User> responsableComboBox;
    private final JComboBox<Task.Priority> prioridadComboBox;
    private final JComboBox<Task.Status> estadoComboBox;

    public TasksController(JTable tasksTable, JComboBox<User> responsableComboBox,
                           JComboBox<Task.Priority> prioridadComboBox, JComboBox<Task.Status> estadoComboBox) {
        this.taskService = TaskService.getInstance();
        this.projectService = ProjectService.getInstance();
        this.userService = UserService.getInstance();
        this.tasksTable = tasksTable;
        this.responsableComboBox = responsableComboBox;
        this.prioridadComboBox = prioridadComboBox;
        this.estadoComboBox = estadoComboBox;
        initializeComponents();
    }

    private void initializeComponents() {
        DefaultComboBoxModel<User> responsableModel = new DefaultComboBoxModel<>();
        userService.getUsers().forEach(responsableModel::addElement);
        responsableComboBox.setModel(responsableModel);

        DefaultComboBoxModel<Task.Priority> prioridadModel = new DefaultComboBoxModel<>(Task.Priority.values());
        prioridadComboBox.setModel(prioridadModel);

        DefaultComboBoxModel<Task.Status> estadoModel = new DefaultComboBoxModel<>(Task.Status.values());
        estadoComboBox.setModel(estadoModel);
    }

    public List<Task> getTasks() {
        return taskService.leerTodos();
    }

    public List<Task> getTasksForProject(Project project) {
        return project.getTareas();
    }

    public Task getTask(int id) {
        return taskService.leerPorId(id);
    }

    public Task createTaskForProject(Project project, String descripcion, LocalDate fechaFinalizacion,
                                     Task.Priority prioridad, Task.Status estado, User responsable) {
        if (project == null) {
            throw new IllegalArgumentException("Debe seleccionar un proyecto para crear la tarea");
        }

        int newId = project.getTareas().size() + 1;
        String formattedId = String.format("T-%03d", newId);
        Task task = new Task(formattedId, descripcion, fechaFinalizacion, prioridad, estado, responsable);

        // Agregar la tarea al proyecto y a la lista global
        project.addTarea(task);
        ProjectService.getInstance().actualizar(project);
        TaskService.getInstance().agregar(task);

        return task;
    }

}



