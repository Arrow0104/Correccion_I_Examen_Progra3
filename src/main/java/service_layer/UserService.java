package service_layer;

import domain_layer.User;
import data_acces_layer.Data;
import java.util.List;

public class UserService {
    private final Data data;
    private static UserService instance;

    private UserService() {
        this.data = Data.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) instance = new UserService();
        return instance;
    }

    public List<User> getUsers() {
        return data.getUsers();
    }

    public User getUserById(String id) {
        return data.getUsers().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}


