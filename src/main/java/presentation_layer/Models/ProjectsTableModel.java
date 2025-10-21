package presentation_layer.Models;

import domain_layer.Project;
import service_layer.IServiceObserver;
import utilities.ChangeType;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProjectsTableModel extends AbstractTableModel implements IServiceObserver<Project> {
    private final String[] cols = {"Código", "Descripción", "Encargado", "# de Tareas"};
    private final Class<?>[] types = {String.class, String.class, String.class, Integer.class};
    private final List<Project> rows = new ArrayList<>();

    public void setRows(List<Project> data) {
        rows.clear();
        if (data != null) rows.addAll(data);
        fireTableDataChanged();
    }

    public Project getAt(int row) {
        return (row >= 0 && row < rows.size()) ? rows.get(row) : null;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int c) {
        return cols[c];
    }

    @Override
    public Class<?> getColumnClass(int c) {
        return types[c];
    }

    @Override
    public boolean isCellEditable(int r, int c) {
        return false;
    }

    @Override
    public Object getValueAt(int r, int c) {
        Project p = rows.get(r);
        return switch (c) {
            case 0 -> p.getCodigo(); // Código del proyecto
            case 1 -> p.getDescripcion(); // Descripción
            case 2 -> p.getEncargado().getName(); // Nombre del encargado
            case 3 -> p.getTareas().size(); // Número de tareas asociadas
            default -> null;
        };
    }

    @Override
    public void onDataChanged(ChangeType type, Project entity) {
        switch (type) {
            case CREATED -> {
                rows.add(entity);
                int i = rows.size() - 1;
                fireTableRowsInserted(i, i);
            }
            case UPDATED -> {
                int i = indexOf(entity.getCodigo());
                if (i >= 0) {
                    rows.set(i, entity);
                    fireTableRowsUpdated(i, i);
                }
            }
        }
    }

    private int indexOf(String codigo) {
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getCodigo().equals(codigo)) {
                return i;
            }
        }
        return -1;
    }
}



