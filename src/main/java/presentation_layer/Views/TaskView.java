package presentation_layer.Views;

import domain_layer.Task;
import service_layer.TaskService;

import javax.swing.*;
import java.awt.*;

public class TaskView extends JDialog {
    //Panels
    private JPanel EditTaskPanel;
    private JPanel ButtonsPanel;

    //Labels
    private JLabel PrioridadLabel;
    private JLabel EstadoLabel;

    //Buttons
    private JButton okButton;
    private JButton cancelButton;

    //Combo boxes
    private JComboBox<Task.Priority> EditPrioridadCombobox;
    private JComboBox<Task.Status> EditEstadoCombobox;


    private Task currentTask;
    private final MainView parentView;
    private final TaskService taskService;

    public TaskView(MainView parentView) {
        super(parentView, true);
        this.parentView = parentView;
        this.taskService = TaskService.getInstance();

        setContentPane(EditTaskPanel);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parentView);
        setTitle("Editar Tarea");
        setSize(400, 250);

        EditPrioridadCombobox.setModel(new DefaultComboBoxModel<>(Task.Priority.values()));
        EditEstadoCombobox.setModel(new DefaultComboBoxModel<>(Task.Status.values()));

        okButton.addActionListener(e -> onSaveButtonClicked());
        cancelButton.addActionListener(e -> setVisible(false));

        // Mejorar apariencia
        getRootPane().setDefaultButton(okButton);
        EditTaskPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public void setTask(Task task) {
        this.currentTask = task;
        if (task != null) {
            EditPrioridadCombobox.setSelectedItem(task.getPrioridad());
            EditEstadoCombobox.setSelectedItem(task.getEstado());
        }
    }

    private void onSaveButtonClicked() {
        try {
            if (currentTask != null) {
                // Actualizar los datos de la tarea
                currentTask.setPrioridad((Task.Priority) EditPrioridadCombobox.getSelectedItem());
                currentTask.setEstado((Task.Status) EditEstadoCombobox.getSelectedItem());

                // Guardar los cambios directamente con el servicio
                taskService.actualizar(currentTask);

                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(this,
                        "Tarea actualizada exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                setVisible(false); // Cerrar el diálogo
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar la tarea: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}


