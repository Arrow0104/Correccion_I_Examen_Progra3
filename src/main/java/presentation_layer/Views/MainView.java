package presentation_layer.Views;

import com.toedter.calendar.JDateChooser;
import domain_layer.Project;
import domain_layer.Task;
import domain_layer.User;
import presentation_layer.Controllers.ProjectsController;
import presentation_layer.Controllers.TasksController;
import presentation_layer.Models.ProjectsTableModel;
import presentation_layer.Models.TasksTableModel;
import service_layer.UserService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class MainView extends JFrame {
    //Panels
    public JPanel MainPanel;
    private JPanel ProjectsPanel;
    private JPanel CreatesProjectsPanel;
    private JPanel TableProjectsPanel;
    private JPanel DatePickerPanel;
    private JPanel TasksPanel;
    private JPanel CreatesTasksPanel;
    private JPanel TableTasksPanel;

    //Labels
    private JLabel PDescripcionLabel;
    private JLabel EncargadoLabel;
    private JLabel TDescripcionLabel;
    private JLabel VenceLabel;
    private JLabel PrioridadLabel;
    private JLabel EstadoLabel;
    private JLabel ResponsableLabel;

    //Textfields
    private JTextField TDescriptionField;
    private JTextField PDescripcionField;

    //Buttons
    private JButton crearProyectosButton;
    private JComboBox<User> EncargadoComboBox;
    private JButton crearTareasButton;

   //Combo boxes
   private JComboBox<Task.Priority> PrioridadComboBox;
    private JComboBox<Task.Status> EstadoComboBox;
    private JComboBox<User> ResponsableComboBox;

    //Tables and ScrollPanes
    private JTable ProjectsTable;
    private JTable TasksTable;
    private JScrollPane TasksScroll;
    private JScrollPane ProjectsScroll;
    private JDateChooser dateChooser;

    private Project selectedProject;

    private final ProjectsController projectsController;
    private final TasksController tasksController;
    private final ProjectsTableModel projectsTableModel;
    private final TasksTableModel tasksTableModel;

    public MainView(ProjectsController projectsController,
                    TasksController tasksController,
                    ProjectsTableModel projectsTableModel,
                    TasksTableModel tasksTableModel) {
        this.projectsController = projectsController;
        this.tasksController = tasksController;
        this.projectsTableModel = projectsTableModel;
        this.tasksTableModel = tasksTableModel;

        if (ProjectsTable != null && projectsTableModel != null) {
            ProjectsTable.setModel(projectsTableModel);
        }
        if (TasksTable != null && tasksTableModel != null) {
            TasksTable.setModel(tasksTableModel);
            TasksTable.setRowHeight(30);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Gestión de Proyectos y Tareas");
    }

    public void initialize() {
        initDatePicker();
        populateComboBoxes();
        disableTaskCreation();
        setupListeners();
        improveAppearance();
    }

    public void initDatePicker() {
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");

        DatePickerPanel.removeAll();
        DatePickerPanel.setLayout(new BorderLayout());
        DatePickerPanel.add(dateChooser, BorderLayout.CENTER);
        DatePickerPanel.revalidate();
        DatePickerPanel.repaint();
    }

    private void populateComboBoxes() {
        try {
            List<User> users = UserService.getInstance().getUsers();

            // EncargadoComboBox
            EncargadoComboBox.removeAllItems();
            EncargadoComboBox.addItem(null);
            for (User user : users) {
                EncargadoComboBox.addItem(user);
            }
            EncargadoComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    setText(value == null ? "Seleccione" : ((User) value).getName());
                    return this;
                }
            });

            // ResponsableComboBox
            ResponsableComboBox.removeAllItems();
            ResponsableComboBox.addItem(null);
            for (User user : users) {
                ResponsableComboBox.addItem(user);
            }
            ResponsableComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    setText(value == null ? "Seleccione" : ((User) value).getName());
                    return this;
                }
            });

            // PrioridadComboBox
            PrioridadComboBox.removeAllItems();
            PrioridadComboBox.addItem(null);
            for (Task.Priority priority : Task.Priority.values()) {
                PrioridadComboBox.addItem(priority);
            }
            PrioridadComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    setText(value == null ? "Seleccione" : ((Task.Priority) value).name());
                    return this;
                }
            });

            // EstadoComboBox
            EstadoComboBox.removeAllItems();
            EstadoComboBox.addItem(null);
            for (Task.Status status : Task.Status.values()) {
                EstadoComboBox.addItem(status);
            }
            EstadoComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    setText(value == null ? "Seleccione" : value.toString());
                    return this;
                }
            });

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al poblar los ComboBoxes: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void improveAppearance() {
        MainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ProjectsTable.setRowHeight(30);
        TasksTable.setRowHeight(30);
        ProjectsPanel.setBorder(BorderFactory.createTitledBorder("Gestión de Proyectos"));
        TasksPanel.setBorder(BorderFactory.createTitledBorder("Gestión de Tareas"));
    }

    private void disableTaskCreation() {
        TDescriptionField.setEnabled(false);
        dateChooser.setEnabled(false);
        PrioridadComboBox.setEnabled(false);
        EstadoComboBox.setEnabled(false);
        ResponsableComboBox.setEnabled(false);
        crearTareasButton.setEnabled(false);
        tasksTableModel.setRows(List.of());
    }

    private void enableTaskCreation() {
        TDescriptionField.setEnabled(true);
        dateChooser.setEnabled(true);
        PrioridadComboBox.setEnabled(true);
        EstadoComboBox.setEnabled(true);
        ResponsableComboBox.setEnabled(true);
        crearTareasButton.setEnabled(true);

        TDescriptionField.setText("");
        dateChooser.setDate(null);
        PrioridadComboBox.setSelectedIndex(0);
        EstadoComboBox.setSelectedIndex(0);
        ResponsableComboBox.setSelectedIndex(0);
    }

    private void setupListeners() {
        crearProyectosButton.addActionListener(e -> createProject());
        crearTareasButton.addActionListener(e -> createTask());

        ProjectsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = ProjectsTable.getSelectedRow();
                if (row >= 0) {
                    selectedProject = projectsTableModel.getAt(row);
                    enableTaskCreation();
                    tasksTableModel.setRows(tasksController.getTasksForProject(selectedProject));
                }
            }
        });

        TasksTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Task selectedTask = getSelectedTask();
                    if (selectedTask != null) {
                        openTaskEditDialog(selectedTask);
                    }
                }
            }
        });
    }

    private void openTaskEditDialog(Task task) {
        TaskView taskView = new TaskView(this);
        taskView.setTask(task);
        taskView.setVisible(true);
    }

    private void createProject() {
        String descripcion = PDescripcionField.getText().trim();
        User encargado = (User) EncargadoComboBox.getSelectedItem();

        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese una descripción para el proyecto",
                    "Campo requerido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (encargado == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un encargado",
                    "Campo requerido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            projectsController.createProject(descripcion, encargado);
            PDescripcionField.setText("");
            EncargadoComboBox.setSelectedIndex(0);
            JOptionPane.showMessageDialog(this,
                    "Proyecto creado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al crear el proyecto: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createTask() {
        if (selectedProject == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un proyecto primero",
                    "Proyecto requerido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String descripcion = TDescriptionField.getText().trim();
        String fechaStr = getSelectedDate();
        Task.Priority prioridad = (Task.Priority) PrioridadComboBox.getSelectedItem();
        Task.Status estado = (Task.Status) EstadoComboBox.getSelectedItem();
        User responsable = (User) ResponsableComboBox.getSelectedItem();

        if (descripcion.isEmpty() || fechaStr.isEmpty() || prioridad == null || estado == null || responsable == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor complete todos los campos",
                    "Campos requeridos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            LocalDate fecha = LocalDate.parse(fechaStr);
            Task newTask = tasksController.createTaskForProject(selectedProject, descripcion, fecha, prioridad, estado, responsable);
            tasksTableModel.setRows(tasksController.getTasksForProject(selectedProject));
            JOptionPane.showMessageDialog(this,
                    "Tarea creada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al crear la tarea: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getSelectedDate() {
        if (dateChooser.getDate() == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(dateChooser.getDate());
    }

    public Task getSelectedTask() {
        int row = TasksTable.getSelectedRow();
        if (row >= 0) {
            return tasksTableModel.getAt(row);
        }
        return null;
    }
}







