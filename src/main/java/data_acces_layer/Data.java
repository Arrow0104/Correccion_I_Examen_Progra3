package data_acces_layer;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import domain_layer.Project;
import domain_layer.Task;
import domain_layer.User;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {
    private static Data instance;

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    private List<User> users;

    @XmlElementWrapper(name = "projects")
    @XmlElement(name = "project")
    private List<Project> projects;

    @XmlElementWrapper(name = "tasks")
    @XmlElement(name = "task")
    private List<Task> tasks;

    public Data() {
        users = new ArrayList<>();
        projects = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    public static Data getInstance() {
        if (instance == null) {
            try {
                instance = XmlPersister.instance().load();
            } catch (Exception e) {
                System.err.println("Error cargando datos: " + e.getMessage());
                instance = new Data();
            }
        }
        return instance;
    }

    public void save() {
        try {
            XmlPersister.instance().store(this);
        } catch (Exception e) {
            System.err.println("Error guardando datos: " + e.getMessage());
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}



