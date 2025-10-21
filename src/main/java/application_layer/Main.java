package application_layer;

import domain_layer.User;
import presentation_layer.Controllers.ProjectsController;
import presentation_layer.Controllers.TasksController;
import presentation_layer.Models.ProjectsTableModel;
import presentation_layer.Models.TasksTableModel;
import presentation_layer.Views.MainView;
import service_layer.ProjectService;
import service_layer.TaskService;
import service_layer.UserService;

import javax.swing.*;
import java.awt.*;

public class  Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Para mejorar la apariencia y que se vea mas bonito
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

                // Crear modelos de tablas
                ProjectsTableModel projectsTableModel = new ProjectsTableModel();
                TasksTableModel tasksTableModel = new TasksTableModel();

                // Registrar observadores
                ProjectService.getInstance().addObserver(projectsTableModel);
                TaskService.getInstance().addObserver(tasksTableModel);

                // Poblar modelos con datos existentes
                projectsTableModel.setRows(ProjectService.getInstance().leerTodos());
                tasksTableModel.setRows(TaskService.getInstance().leerTodos());

                // Crear la vista principal
                MainView mainView = new MainView(
                        new ProjectsController(
                                new JTable(projectsTableModel),
                                new JComboBox<>()
                        ),
                        new TasksController(
                                new JTable(tasksTableModel),
                                new JComboBox<>(),
                                new JComboBox<>(),
                                new JComboBox<>()
                        ),
                        projectsTableModel,
                        tasksTableModel
                );

                // Inicializar el JDateChooser antes de hacer initialize()
                mainView.initDatePicker();

                // Inicializar la vista
                mainView.initialize();

                // Mostrar la ventana
                mainView.setContentPane(mainView.MainPanel);
                mainView.pack();
                mainView.setLocationRelativeTo(null);
                mainView.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error iniciando la aplicación: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}









