package jvm.pablohdz.daorepositorypatternexample.persistence;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jvm.pablohdz.daorepositorypatternexample.dto.UserDto;

// TODO: 9/29/21 response with userDto
@Component
public class MySQLDatabase<T> implements Database<T> {
    private final String URL_DATABASE = "jdbc:mysql://localhost:3306/example";

    @Override
    public T fetchOne() {

        try (Connection connection = DriverManager.getConnection(URL_DATABASE, "root",
                "my-secret-pw"
        )) {
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM user_patterns");

            try (ResultSet resultSet = statement.getResultSet()) {
                UserDto userDto = null;
                while (resultSet.next()) {
                    long userId = resultSet.getLong(1);
                    String userUsername = resultSet.getString(2);
                    String userName = resultSet.getString(3);
                    String userEmail = resultSet.getString(4);

                    userDto = new UserDto(userId, userUsername, userName, userEmail);
                }

                return (T) userDto;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
