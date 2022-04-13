package org.glove.app.entity;


import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@Data
public class MaterialEntity {
    // ID, Title, CountInPack, Unit, CountInStock, MinCount, Description, Cost, Image, MaterialType
    private int id;
    private String title;
    private int countInPack;
    private String unit;
    private int countInStock;
    private int minCount;
    private String description;
    private double cost;
    private String imagePath;
    private String materialType;
    private ImageIcon image;

    public MaterialEntity(int id, String title, int countInPack, String unit, int countInStock, int minCount, String description, double cost, String imagePath, String materialType) {
        this.id = id;
        this.title = title;
        this.countInPack = countInPack;
        this.unit = unit;
        this.countInStock = countInStock;
        this.minCount = minCount;
        this.description = description;
        this.cost = cost;
        this.imagePath = imagePath;
        this.materialType = materialType;
        try {
            this.image = new ImageIcon(ImageIO.read(MaterialEntity.class.getClassLoader().getResource(imagePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
