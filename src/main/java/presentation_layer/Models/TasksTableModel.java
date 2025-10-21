package presentation_layer.Models;

import domain_layer.Task;
import service_layer.IServiceObserver;
import utilities.ChangeType;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TasksTableModel extends AbstractTableModel implements IServiceObserver<Task> {
    private final String[] cols = {"Número", "Descripción", "Vence", "Prioridad", "Estado", "Asignado a"};
    private final Class<?>[] types = {String.class, String.class, String.class, String.class, String.class, String.class};
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final List<Task> rows = new ArrayList<>();

    public void setRows(List<Task> data) {
        rows.clear();
        if (data != null) rows.addAll(data);
        fireTableDataChanged();
    }

    public Task getAt(int row) {
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
        Task t = rows.get(r);
        return switch (c) {
            case 0 -> t.getNumero();
            case 1 -> t.getDescripcion();
            case 2 -> t.getFechaFinalizacion() != null ? t.getFechaFinalizacion().format(dateFormat) : "";
            case 3 -> t.getPrioridad().name();
            case 4 -> t.getEstado().name();
            case 5 -> t.getResponsable().getName();
            default -> null;
        };
    }

    @Override
    public void onDataChanged(ChangeType type, Task entity) {
        switch (type) {
            case CREATED -> {
                rows.add(entity);
                int i = rows.size() - 1;
                fireTableRowsInserted(i, i);
            }
            case UPDATED -> {
                int i = indexOf(entity.getNumero());
                if (i >= 0) {
                    rows.set(i, entity);
                    fireTableRowsUpdated(i, i);
                }
            }
        }
    }

    private int indexOf(String numero) {
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getNumero().equals(numero)) {
                return i;
            }
        }
        return -1;
    }
}

