package presentation_layer.Controllers;

import domain_layer.Project;
import domain_layer.User;
import service_layer.ProjectService;
import service_layer.UserService;

import javax.swing.*;
import java.util.List;

public class ProjectsController {
    private final ProjectService projectService;
    private final UserService userService;
    private final JTable projectsTable;
    private final JComboBox<User> encargadoComboBox;

    public ProjectsController(JTable projectsTable, JComboBox<User> encargadoComboBox) {
        this.projectService = ProjectService.getInstance();
        this.userService = UserService.getInstance();
        this.projectsTable = projectsTable;
        this.encargadoComboBox = encargadoComboBox;
        initializeComponents();
    }

    private void initializeComponents() {
        DefaultComboBoxModel<User> encargadoModel = new DefaultComboBoxModel<>();
        userService.getUsers().forEach(encargadoModel::addElement);
        encargadoComboBox.setModel(encargadoModel);
    }

    public List<Project> getProjects() {
        return projectService.leerTodos();
    }

    public Project getProject(int id) {
        return projectService.leerPorId(id);
    }

    public void createProject(String descripcion, User encargado) {
        int newId = projectService.leerTodos().size() + 1;
        String formattedId = String.format("PROJ-%03d", newId);
        Project project = new Project(formattedId, descripcion, encargado);
        projectService.agregar(project);
    }

    public void updateProject(String codigo, String descripcion, User encargado) {
        Project project = new Project(codigo, descripcion, encargado);
        projectService.actualizar(project);
    }
}


