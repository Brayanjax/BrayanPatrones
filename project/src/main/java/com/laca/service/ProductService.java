package com.laca.service;


import com.laca.entity.concretProduct.Product;
import jakarta.transaction.Transactional;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final DataSource dataSource;

    public ProductService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM products";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();

                product.setType(resultSet.getString("type"));
                product.setWeight(resultSet.getDouble("weight"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setHeight(resultSet.getDouble("height"));
                product.setWidth(resultSet.getDouble("width"));
                products.add(product);
            }
        } catch (SQLException e) {
            // Manejo de excepciones
        }
        return products;
    }

    @Transactional
    public Product saveProduct(Product product) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO transporters (name, company) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving new transporter");
        }
        return product;
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
