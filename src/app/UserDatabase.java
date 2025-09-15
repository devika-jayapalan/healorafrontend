package app;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    public static List<User> users = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
    }
}

