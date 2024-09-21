package main.dao;

import main.Util;
import main.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Util util = new Util();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String query = "create table if not exists users (\n" +
                "    id       bigint auto_increment not null,\n" +
                "    name     varchar(45) not null,\n" +
                "    lastName varchar(45) not null,\n" +
                "    age      tinyint     not null,\n" +
                "    constraint users_pk\n primary key (id)\n" +
                ");";
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String query = "drop table if exists users";
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "insert into users (name, lastName, age) values (?,?,?)";
        try (PreparedStatement statement = util.getConnection().prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = "delete from users where id = ?";
        try (PreparedStatement statement = util.getConnection().prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String query = "select * from users";
        try (PreparedStatement statement = util.getConnection().prepareStatement(query);
             ResultSet result = statement.executeQuery();) {
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong(1));
                user.setName(result.getString(2));
                user.setLastName(result.getString(3));
                user.setAge(result.getByte(4));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String query = "delete from users";
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
