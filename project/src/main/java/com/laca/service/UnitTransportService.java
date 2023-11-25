package com.laca.service;

import com.laca.BL.FactoryUnitTransport.FactoryUnitTransporter;
import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;
import jakarta.transaction.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnitTransportService {
    private final DataSource dataSource;

    public UnitTransportService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    public List<UnitTransporterAbstract> getAllUnitTransporters() {
        List<UnitTransporterAbstract> UnitTransport = new ArrayList<UnitTransporterAbstract>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM unitTransporters";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UnitTransport.add(FactoryUnitTransporter.createUnitTransport(
                        resultSet.getString("name"),
                        resultSet.getString("plate"),
                        resultSet.getLong("high"),
                        resultSet.getLong("width"),
                        resultSet.getString("type"),
                        resultSet.getLong("maxWeight"),
                        resultSet.getBoolean("isActive")
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
            String query = "INSERT INTO Unit_Transporter (name, plate,high,width,type,maxWeight,isActive) VALUES (?,?,?,?,?,?,?)";
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
            throw new RuntimeException("Error saving new transporter");
        }
        return unitTransporterAbstract;// Duda de como crear esto
    }

    @Transactional
    public UnitTransporterAbstract updateUnitTransporter(Long UnitTransportId, UnitTransporterAbstract upadatedUnitTransporterAbstract) {
        try (Connection connection = dataSource.getConnection()) {
            String storedProcedureCall = "{call update_unit_Transport(?, ?, ?, ?, ?, ?, ?)}";
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
                throw new RuntimeException("Error updating transporter: No results from the stored procedure.");
            }

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                int updatedId = resultSet.getInt("id");
                String updatedName = resultSet.getString("name");
                String updatedPlate = resultSet.getString("plate");
                double updatedHigh = resultSet.getDouble("high");
                double updatedWidth = resultSet.getDouble("width");
                String updatedType = resultSet.getString("type");
                double updatedMaxWeight = resultSet.getDouble(("maxWeight"));
                boolean updatedIsActive = resultSet.getBoolean("isActive");

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
                throw new RuntimeException("Transporter not found by ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating transporter: " + e.getMessage(), e);
        }
    }

    @Transactional
    public UnitTransporterAbstract getUnitTransporterById(Long UnitTransporterId) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id,name,plate,high,width,type,maxWeight,isActive FROM UnitTransport WHERE id = ?";
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
                throw new RuntimeException("Transporter not found with ID: " + UnitTransporterId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving transporter: " + e.getMessage(), e);
        }
    }


    @Transactional
    public Boolean deleteUnitTransporter(Long transporterId) {
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
