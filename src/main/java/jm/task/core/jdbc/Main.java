package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();

        userDaoJDBC.createUsersTable();

        userDaoJDBC.saveUser("Name1", "LastName1", (byte) 20);
        userDaoJDBC.saveUser("Name2", "LastName2", (byte) 25);
        userDaoJDBC.saveUser("Name3", "LastName3", (byte) 31);
        userDaoJDBC.saveUser("Name4", "LastName4", (byte) 38);

        userDaoJDBC.removeUserById(1);
        userDaoJDBC.getAllUsers();
        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();
    }
}
