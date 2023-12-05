package com.laca.service;


import com.laca.entity.RouteC.Point;
import com.laca.entity.RouteC.Route;
import com.laca.entity.Transporter;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class RouteService {
    private final DataSource dataSource;

    public RouteService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    public List<Route> getAllRoutes() {
        List<Route> routes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM routes";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Route route = new Route();
                route.setId(resultSet.getLong("id"));
                route.setName(resultSet.getString("name"));
                route.setDescription(resultSet.getString("description"));
                Point point =new Point();
                point.setName(resultSet.getString("namePoint"));
                point.setDescription(resultSet.getString("description"));
                point.setCoordinates(resultSet.getDouble("coordinates"));
                route.setStartPoint((Point) resultSet.getObject(String.valueOf(point)));
                route.setEndPoint((Point) resultSet.getObject(String.valueOf(point)));
                routes.add(route);
            }
        } catch (SQLException e) {
            // Manejo de excepciones
        }
        return routes;
    }

    @Transactional
    public Transporter saveRoutes(Route route) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO transporters (name, company) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, route.getName());
            statement.setString(2, route.getDescription());
            statement.setString(3, route.getEndPoint().ge);
            statement.setString(4, transporter.getCompany());
            statement.setString(5, transporter.getName());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    transporter.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving new transporter");
        }
        return transporter;
    }

    @Transactional
    public Transporter updateTransporter(Long transporterId, Transporter updatedTransporter) {
        try (Connection connection = dataSource.getConnection()) {
            String storedProcedureCall = "{call update_transporter(?, ?, ?)}";
            CallableStatement statement = connection.prepareCall(storedProcedureCall);

            statement.setLong(1, transporterId);
            statement.setString(2, updatedTransporter.getName());
            statement.setString(3, updatedTransporter.getCompany());

            boolean hasResults = statement.execute();

            if (!hasResults) {
                throw new RuntimeException("Error updating transporter: No results from the stored procedure.");
            }

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                int updatedId = resultSet.getInt("id");
                String updatedName = resultSet.getString("name");
                String updatedCompany = resultSet.getString("company");

                // Crea un nuevo Transporter con los datos actualizados y devuélvelo
                updatedTransporter.setId((long) updatedId);
                updatedTransporter.setName(updatedName);
                updatedTransporter.setCompany(updatedCompany);

                return updatedTransporter;
            } else {
                throw new RuntimeException("Transporter not found by ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating transporter: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Transporter getTransporterById(Long transporterId) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id, name, company FROM transporters WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, transporterId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Transporter transporter = new Transporter();
                transporter.setId(resultSet.getLong("id"));
                transporter.setName(resultSet.getString("name"));
                transporter.setCompany(resultSet.getString("company"));
                return transporter;
            } else {
                throw new RuntimeException("Transporter not found with ID: " + transporterId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving transporter: " + e.getMessage(), e);
        }
    }


    @Transactional
    public Boolean deleteTransporter(Long transporterId) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "DELETE FROM transporters where transporters.id  = ?";
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
