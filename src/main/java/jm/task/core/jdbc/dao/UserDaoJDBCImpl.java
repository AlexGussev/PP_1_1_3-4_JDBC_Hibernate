package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sqlCreateUsersTable = "CREATE TABLE TableUser(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                "UserName VARCHAR(50), " +
                "UserLastName VARCHAR(50), " +
                "UserAge TINYINT)";
        try (Statement statement = getConn().createStatement()) {
            statement.executeUpdate(sqlCreateUsersTable);
            System.out.println("Таблица создана успешно!");
        } catch (SQLException e) {
            System.out.println("Таблица не создана!");
        }
    }

    public void dropUsersTable() {
        String sqlDropUsersTable = "DROP TABLE TableUser";
        try (Statement statement = getConn().createStatement();){
            statement.executeUpdate(sqlDropUsersTable);
        } catch (SQLException e) {
            System.out.println();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlSaveUser = "INSERT INTO TableUser(Username, UserLastName, UserAge) " +
                "VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = getConn().prepareStatement(sqlSaveUser)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sqlremoveUsersById = "DELETE FROM TableUser WHERE ID = ?";
        try (PreparedStatement preparedStatement = getConn().prepareStatement(sqlremoveUsersById)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String sqlGetAllUsers = "SELECT * FROM TableUser";
        User user = new User();
        List<User> list = new LinkedList<>();
        try (Statement statement = getConn().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlGetAllUsers);
            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("UserName"));
                user.setLastName(resultSet.getString("UserLastName"));
                user.setAge(resultSet.getByte("UserAge"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    public void cleanUsersTable() {
        String sqlCleanUsersTable = "TRUNCATE TABLE TableUser";
        try (Statement statement = getConn().createStatement();){
            statement.executeUpdate(sqlCleanUsersTable);
        } catch (SQLException e) {
            System.out.println();
        }
    }
}

