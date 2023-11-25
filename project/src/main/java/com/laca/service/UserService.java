package com.laca.service;



import com.laca.BL.FactoryUsers.FactoryUsers;
import com.laca.entity.Interfaces.IConstructUser;
import com.laca.entity.PackageUnitAbstract.Users;
import jakarta.transaction.Transactional;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final DataSource dataSource;

    public UserService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    public List<IConstructUser> getAllUsers() {
        List<IConstructUser> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM users";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(FactoryUsers.createUser(
                        resultSet.getString("name"),
                        resultSet.getString("identificacion "),
                        resultSet.getString("factoryName"),
                        resultSet.getString("type")
                ));


            }
        } catch (SQLException e) {
            // Manejo de excepciones
        }
        return users;
    }

    @Transactional
    public Users saveUser(Users users) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO transporters (name,identificacion,factoryName,type) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, users.getName());
            statement.setString(2, users.getIdentification());
            statement.setString(3, users.getFactoryName());
            statement.setString(4, users.getType());


            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    users.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving new transporter");
        }
        return users;
    }

    @Transactional
    public Users updateUser(Long userId, Users updatedUser) {
        try (Connection connection = dataSource.getConnection()) {
            String storedProcedureCall = "{call update_user(?, ?, ?, ?, ?)}";
            CallableStatement statement = connection.prepareCall(storedProcedureCall);

            statement.setLong(1, userId);
            statement.setString(1, updatedUser.getName());
            statement.setString(2, updatedUser.getIdentification());
            statement.setString(3, updatedUser.getFactoryName());
            statement.setString(4, updatedUser.getType());

            boolean hasResults = statement.execute();

            if (!hasResults) {
                throw new RuntimeException("Error updating transporter: No results from the stored procedure.");
            }

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                int updatedId = resultSet.getInt("id");
                String updatedName = resultSet.getString("name");
                String updatedIdentification = resultSet.getString("identification");
                String updatedFactoryName = resultSet.getString("factoryName");
                String updatedType = resultSet.getString("type");

                // Crea un nuevo Transporter con los datos actualizados y devuélvelo
                updatedUser.setId((long) updatedId);
                updatedUser.setName(updatedName);
                updatedUser.setIdentification(updatedIdentification);
                updatedUser.setFactoryName(updatedFactoryName);
                updatedUser.setType(updatedType);

                return updatedUser;
            } else {
                throw new RuntimeException("Transporter not found by ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating transporter: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Users getUserById(Long transporterId) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id,name,identificacion,factoryName,type FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, transporterId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Users user = new Users();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setIdentification(resultSet.getString("identificacion "));
                user.setFactoryName(resultSet.getString("factoryName"));
                user.setType(resultSet.getString("type"));

                return user;
            } else {
                throw new RuntimeException("Transporter not found with ID: " + transporterId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving transporter: " + e.getMessage(), e);
        }
    }


    @Transactional
    public Boolean deleteUser(Long transporterId) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "DELETE FROM users where users.id  = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, transporterId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                return false;
            }

            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting transporter: " + e.getMessage(), e);
        }
    }
}
