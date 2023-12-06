package com.laca.service;

import com.laca.BL.FactoryUnitTransport.FactoryUnitTransporter;
import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class UnitTransportService {
    private final DataSource dataSource;

    public UnitTransportService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    public List<UnitTransporterAbstract> getAllUnitTransporters() {
        List<UnitTransporterAbstract> UnitTransport = new ArrayList<UnitTransporterAbstract>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM unit_transport";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UnitTransport.add(FactoryUnitTransporter.createUnitTransport(
                        resultSet.getString("Name"),
                        resultSet.getString("Plate"),
                        resultSet.getLong("high"),
                        resultSet.getLong("width"),
                        resultSet.getString("Type"),
                        resultSet.getLong("Max_Weight"),
                        resultSet.getBoolean("Is_Active"),
                        resultSet.getLong("id")
                ));
            }
        } catch (SQLException e) {
            // Manejo de excepciones
        }
        return UnitTransport;
    }
    @Transactional
    public UnitTransporterAbstract saveUnitTransporter(UnitTransporterAbstract unitTransporterAbstract) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO unit_transport (name, plate,high,width,type,Max_Weight,Is_Active) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, unitTransporterAbstract.getName());
            statement.setString(2, unitTransporterAbstract.getPlate());
            statement.setDouble(3, unitTransporterAbstract.getHigh());
            statement.setDouble(4, unitTransporterAbstract.getWidth());
            statement.setString(5, unitTransporterAbstract.getType());
            statement.setDouble(6, unitTransporterAbstract.getMaxWeight());
            statement.setBoolean(7, unitTransporterAbstract.getIsActive());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    UnitTransporterAbstract.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving new Unit transporter");
        }
        return unitTransporterAbstract;// Duda de como crear esto
    }

    @Transactional
    public UnitTransporterAbstract updateUnitTransporter(Long UnitTransportId, UnitTransporterAbstract upadatedUnitTransporterAbstract) {
        try (Connection connection = dataSource.getConnection()) {
            String storedProcedureCall = "{call update_unit_transport(?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement statement = connection.prepareCall(storedProcedureCall);
            statement.setLong(1, UnitTransportId);
            statement.setString(1, upadatedUnitTransporterAbstract.getName());
            statement.setString(2, upadatedUnitTransporterAbstract.getPlate());
            statement.setDouble(3, upadatedUnitTransporterAbstract.getHigh());
            statement.setDouble(4, upadatedUnitTransporterAbstract.getWidth());
            statement.setString(5, upadatedUnitTransporterAbstract.getType());
            statement.setDouble(6, upadatedUnitTransporterAbstract.getMaxWeight());
            statement.setBoolean(7, upadatedUnitTransporterAbstract.getIsActive());
            boolean hasResults = statement.execute();
            if (!hasResults) {
                throw new RuntimeException("Error updating Unit transporter: No results from the stored procedure.");
            }
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                int updatedId = resultSet.getInt("id");
                String updatedName = resultSet.getString("Name");
                String updatedPlate = resultSet.getString("Plate");
                double updatedHigh = resultSet.getDouble("high");
                double updatedWidth = resultSet.getDouble("width");
                String updatedType = resultSet.getString("Type");
                double updatedMaxWeight = resultSet.getDouble(("Max_Weight"));
                boolean updatedIsActive = resultSet.getBoolean("Is_Active");
                // Crea un nuevo Transporter con los datos actualizados y devuélvelo
                upadatedUnitTransporterAbstract.setId((long) updatedId);
                upadatedUnitTransporterAbstract.setName(updatedName);
                upadatedUnitTransporterAbstract.setPlate(updatedPlate);
                upadatedUnitTransporterAbstract.setHigh(updatedHigh);
                upadatedUnitTransporterAbstract.setWidth(updatedWidth);
                upadatedUnitTransporterAbstract.setType(updatedType);
                upadatedUnitTransporterAbstract.setMaxWeight(updatedMaxWeight);
                upadatedUnitTransporterAbstract.setIsActive(updatedIsActive);
                return upadatedUnitTransporterAbstract;
            } else {
                throw new RuntimeException("Unit Transporter not found by ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Unit transporter: " + e.getMessage(), e);
        }
    }
    @Transactional
    public UnitTransporterAbstract getUnitTransporterById(Long UnitTransporterId) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id,name,plate,high,width,type,Max_Weight,Is_Active FROM unit_transport WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, UnitTransporterId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UnitTransporterAbstract unitTransporterAbstract = new UnitTransporterAbstract();
                unitTransporterAbstract.setName(resultSet.getString("name"));
                unitTransporterAbstract.setPlate(resultSet.getString("plate"));
                unitTransporterAbstract.setHigh(resultSet.getLong("high"));
                unitTransporterAbstract.setWidth(resultSet.getLong("width"));
                unitTransporterAbstract.setType(resultSet.getString("type"));
                unitTransporterAbstract.setMaxWeight(resultSet.getLong("Max_Weight"));
                unitTransporterAbstract.setIsActive(resultSet.getBoolean("Is_Active"));
                return unitTransporterAbstract;
            } else {
                throw new RuntimeException("Unit Transporter not found with ID: " + UnitTransporterId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving transporter: " + e.getMessage(), e);
        }
    }

    @Transactional
    public UnitTransporterAbstract DuplicateUnitTransport(Long UnitTransporterId) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id,name,plate,high,width,type,Max_Weight,Is_Active FROM unit_transport WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, UnitTransporterId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                UnitTransporterAbstract unitTransporterAbstract = new UnitTransporterAbstract();
                unitTransporterAbstract.setName(resultSet.getString("name"));
                unitTransporterAbstract.setPlate(resultSet.getString("plate"));
                unitTransporterAbstract.setHigh(resultSet.getLong("high"));
                unitTransporterAbstract.setWidth(resultSet.getLong("width"));
                unitTransporterAbstract.setType(resultSet.getString("type"));
                unitTransporterAbstract.setMaxWeight(resultSet.getLong("name"));
                unitTransporterAbstract.setIsActive(resultSet.getBoolean("isActive"));
                return unitTransporterAbstract;

            } else {
                throw new RuntimeException("Unit Transporter not found with ID: " + UnitTransporterId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving transporter: " + e.getMessage(), e);
        }
    }
    @Transactional
    public Boolean deleteUnitTransporter(Long transporterId) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "DELETE FROM unit_transport where unit_transport.id  = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, transporterId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Unit Transporter: " + e.getMessage(), e);
        }
    }
}
