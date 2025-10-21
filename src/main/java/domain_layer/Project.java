package domain_layer;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class Project {
    @XmlAttribute(name = "id")
    private String codigo;

    @XmlElement(name = "description")
    private String descripcion;

    @XmlElement(name = "manager")
    private User encargado;

    @XmlElementWrapper(name = "tasks")
    @XmlElement(name = "task")
    private List<Task> tareas;

    public Project() {
        this.tareas = new ArrayList<>();
    }

    public Project(String codigo, String descripcion, User encargado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.encargado = encargado;
        this.tareas = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public User getEncargado() {
        return encargado;
    }

    public void setEncargado(User encargado) {
        this.encargado = encargado;
    }

    public List<Task> getTareas() {
        return new ArrayList<>(tareas);
    }

    public void setTareas(List<Task> tareas) {
        this.tareas = new ArrayList<>(tareas);
    }

    public void addTarea(Task tarea) {
        this.tareas.add(tarea);
    }
}


