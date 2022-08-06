package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConn;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = getConn().createStatement()) {
            statement.executeUpdate("CREATE TABLE TableUser(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "UserName VARCHAR(50), " +
                    "UserLastName VARCHAR(50), " +
                    "UserAge TINYINT)");
            System.out.println("Таблица создана успешно!");
        } catch (SQLException e) {
            System.out.println("Таблица не создана!");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = getConn().createStatement();){
            statement.executeUpdate("DROP TABLE TableUser");
        } catch (SQLException e) {
            System.out.println();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = getConn().prepareStatement("INSERT INTO " +
                "TableUser(Username, UserLastName, UserAge) " +
                "VALUES (?,?,?)")){
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
        try (PreparedStatement preparedStatement = getConn().prepareStatement("DELETE FROM TableUser WHERE ID = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        User user = new User();
        List<User> list = new LinkedList<>();
        try (Statement statement = getConn().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TableUser");
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
        try (Statement statement = getConn().createStatement()){
            statement.executeUpdate("TRUNCATE TABLE TableUser");
        } catch (SQLException e) {
            System.out.println();
        }
    }
}

