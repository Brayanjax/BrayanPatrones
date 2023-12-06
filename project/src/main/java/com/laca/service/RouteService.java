package com.laca.service;


import com.laca.entity.RouteC.Coordinates;
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
                Coordinates coordinates = new Coordinates();
                coordinates.setLatitude(resultSet.getDouble("latitude"));
                coordinates.setLongitude(resultSet.getDouble("longitude"));
                Point point =new Point();
                point.setName(resultSet.getString("namePoint"));
                point.setDescription(resultSet.getString("description"));
                point.setCoordinates((Double) resultSet.getObject(String.valueOf(coordinates)));
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
    public Route saveRoutes(Route route) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO transporters (name, company) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, route.getName());
            statement.setString(2, route.getDescription());


            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    route.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving new route");
        }
        return route;
    }

    @Transactional
    public Route updateRoute(Long routeId, Route updatedRoute) {
        try (Connection connection = dataSource.getConnection()) {
            String storedProcedureCall = "{call update_route(?, ?, ?)}";
            CallableStatement statement = connection.prepareCall(storedProcedureCall);

            statement.setLong(1, routeId);
            statement.setString(2, updatedRoute.getName());
            statement.setString(3, updatedRoute.getDescription());

            boolean hasResults = statement.execute();

            if (!hasResults) {
                throw new RuntimeException("Error updating route: No results from the stored procedure.");
            }

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                int updatedId = resultSet.getInt("id");
                String updatedName = resultSet.getString("name");
                String updatedDescription = resultSet.getString("description");

                // Crea un nuevo Transporter con los datos actualizados y devuélvelo
                updatedRoute.setId((long) updatedId);
                updatedRoute.setName(updatedName);
                updatedRoute.setDescription(updatedDescription);

                return updatedRoute;
            } else {
                throw new RuntimeException("Route not found by ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating route: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Route getRouteById(Long routeId) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id, name, description FROM route WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, routeId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Route route = new Route();
                route.setId(resultSet.getLong("id"));
                route.setName(resultSet.getString("name"));
                route.setDescription(resultSet.getString("description"));
                return route;
            } else {
                throw new RuntimeException("Route not found with ID: " + routeId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving route: " + e.getMessage(), e);
        }
    }


    @Transactional
    public Boolean deleteRoute(Long routeId) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "DELETE FROM route where route.id  = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, routeId);
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
