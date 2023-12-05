package com.laca.service;
import com.laca.entity.concretProduct.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class ProductService {
    private final DataSource dataSource;

    public ProductService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM product";
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
            String query = "INSERT INTO product (type,weight,name, description,price,height,width) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(1, product.getType());
            statement.setDouble(2, product.getWeight());
            statement.setString(3, product.getName());
            statement.setString(4, product.getDescription());
            statement.setDouble(5, product.getPrice());
            statement.setDouble(6, product.getHeight());
            statement.setDouble(7, product.getWidth());



            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving new Product");
        }
        return product;
    }

    @Transactional
    public Product updatedProduct(Long ProductId, Product updatedProduct) {
        try (Connection connection = dataSource.getConnection()) {
            String storedProcedureCall = "{call update_Product(?, ?, ?,?, ?, ?,?)}";
            CallableStatement statement = connection.prepareCall(storedProcedureCall);

            statement.setString(1, updatedProduct.getType());
            statement.setDouble(2, updatedProduct.getWeight());
            statement.setString(3, updatedProduct.getName());
            statement.setString(4, updatedProduct.getDescription());
            statement.setDouble(5, updatedProduct.getPrice());
            statement.setDouble(6, updatedProduct.getHeight());
            statement.setDouble(7, updatedProduct.getWidth());

            boolean hasResults = statement.execute();

            if (!hasResults) {
                throw new RuntimeException("Error updating Product: No results from the stored procedure.");
            }

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                int updatedId = resultSet.getInt("id");
                String updatedType = resultSet.getString("Type");
                double updatedWeight = resultSet.getDouble("Weight");
                String updatedName = resultSet.getString("Name");
                String updatedDescription = resultSet.getString("Description");
                double updatedPrice = resultSet.getDouble("Price");
                double updatedHeight = resultSet.getDouble("Height");
                double updatedWidth = resultSet.getDouble("Width");

                // Crea un nuevo Transporter con los datos actualizados y devuélvelo
                updatedProduct.setId((long) updatedId);
                updatedProduct.setType(updatedType);
                updatedProduct.setWeight(updatedWeight);
                updatedProduct.setName(updatedName);
                updatedProduct.setDescription(updatedDescription);
                updatedProduct.setPrice(updatedPrice);
                updatedProduct.setHeight(updatedHeight);
                updatedProduct.setWidth(updatedWidth);

                return updatedProduct;
            } else {
                throw new RuntimeException("Product not found by ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Product: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Product getProductById(Long productId) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id,type,weight,name, description,price,height,width FROM products WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, productId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setType(resultSet.getString("type"));
                product.setWeight(resultSet.getDouble("weight"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("Price"));
                product.setHeight(resultSet.getDouble("Height"));
                product.setWidth(resultSet.getDouble("Width"));
                return product;
            } else {
                throw new RuntimeException("Transporter not found with ID: " + productId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving transporter: " + e.getMessage(), e);
        }
    }


    @Transactional
    public Boolean deleteProduct(Long ProductId) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "DELETE FROM product where product.id  = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, ProductId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                return false;
            }

            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Product: " + e.getMessage(), e);
        }
    }
}
