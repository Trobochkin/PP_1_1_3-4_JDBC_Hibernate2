package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl a = new UserDaoJDBCImpl();
        a.createUsersTable();
        a.saveUser("Alex", "Ferguson", (byte) 44);
        a.saveUser("Lisa", "Smith", (byte) 65);
        a.saveUser("Jon", "Jons", (byte) 23);
        a.saveUser("Ana", "Amara", (byte) 24);
        for (User i : a.getAllUsers()) {
            System.out.println(i);
        }
        a.cleanUsersTable();
        a.dropUsersTable();
    }
}
