package org.glove.app.manager;

import org.glove.app.App;
import org.glove.app.entity.MaterialEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialEntityManager {
    public static List<MaterialEntity> selectAll() throws SQLException {
        try (Connection c = App.getConnection()){
            String sql = "SELECT * FROM material";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<MaterialEntity> list = new ArrayList<>();

            while(resultSet.next()){
                list.add(new MaterialEntity(
                        // ID, Title, CountInPack, Unit, CountInStock, MinCount, Description, Cost, Image, MaterialType
                        resultSet.getInt("ID"),
                        resultSet.getString("Title"),
                        resultSet.getInt("CountInPack"),
                        resultSet.getString("Unit"),
                        resultSet.getInt("CountInStock"),
                        resultSet.getInt("MinCount"),
                        resultSet.getString("Description"),
                        resultSet.getDouble("Cost"),
                        resultSet.getString("Image"),
                        resultSet.getString("MaterialType")
                ));
            }

            return list;
        }
    }

    public static void insert(MaterialEntity material) throws SQLException{
        try(Connection c = App.getConnection()){
            String sql = "INSERT INTO material(Title, CountInPack, Unit, CountInStock, MinCount, Description, Cost, Image, MaterialType) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, material.getTitle());
            ps.setInt(2, material.getCountInPack());
            ps.setString(3, material.getUnit());
            ps.setInt(4, material.getCountInStock());
            ps.setInt(5, material.getMinCount());
            ps.setString(6, material.getDescription());
            ps.setDouble(7, material.getCost());
            ps.setString(8, material.getImagePath());
            ps.setString(9, material.getMaterialType());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()){
                material.setId(keys.getInt(1));
                return;
            }
        }
    }

    public static void update(MaterialEntity material) throws SQLException{
        try(Connection c = App.getConnection()){
            String sql = "UPDATE material SET Title=?, CountInPack=?, Unit=?, CountInStock=?, MinCount=?, Description=?, Cost=?, Image=?, MaterialType=? WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, material.getTitle());
            ps.setInt(2, material.getCountInPack());
            ps.setString(3, material.getUnit());
            ps.setInt(4, material.getCountInStock());
            ps.setInt(5, material.getMinCount());
            ps.setString(6, material.getDescription());
            ps.setDouble(7, material.getCost());
            ps.setString(8, material.getImagePath());
            ps.setString(9, material.getMaterialType());
            ps.setInt(10,material.getId());

            ps.executeUpdate();
        }
    }

    public static void delete(MaterialEntity material) throws SQLException{
        try(Connection c = App.getConnection()){
            String sql = "DELETE FROM material WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, material.getId());
            ps.executeUpdate();
        }
    }
}
