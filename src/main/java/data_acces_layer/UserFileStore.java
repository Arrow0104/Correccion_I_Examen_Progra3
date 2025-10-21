package data_acces_layer;

import domain_layer.User;
import java.io.File;
import java.util.List;

public class UserFileStore implements IFileStore<User> {
    private final Data data;

    public UserFileStore(File xmlFile) {
        this.data = Data.getInstance();
    }

    @Override
    public List<User> readAll() {
        return data.getUsers();
    }

    @Override
    public void writeAll(List<User> users) {
        data.getUsers().clear();
        if (users != null) {
            data.getUsers().addAll(users);
        }
        data.save();
    }
}


