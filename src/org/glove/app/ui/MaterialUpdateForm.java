package org.glove.app.ui;

import org.glove.app.entity.MaterialEntity;
import org.glove.app.manager.MaterialEntityManager;
import org.glove.app.ui.MaterialTableForm;
import org.glove.app.util.BaseForm;
import org.glove.app.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class MaterialUpdateForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField titleField;
    private JSpinner countInPackField;
    private JTextField unitField;
    private JSpinner countInStockField;
    private JSpinner minCountField;
    private JTextField descriptionField;
    private JTextField costField;
    private JTextField imagePathField;
    private JTextField materialTypeField;
    private JButton backButton;
    private JButton addButton;
    private JButton deleteButton;
    private JTextField idField;

    private MaterialEntity material;

    public MaterialUpdateForm(MaterialEntity material){
        super(500, 400);
        this.material = material;
        setContentPane(mainPanel);
        initFields();
        initButtons();
        setVisible(true);
    }

    private void initFields(){
        idField.setEditable(false);
        idField.setText(String.valueOf(material.getId()));
        titleField.setText(material.getTitle());
        countInPackField.setValue(material.getCountInPack());
        unitField.setText(material.getUnit());
        countInStockField.setValue(material.getCountInStock());
        minCountField.setValue(material.getMinCount());
        descriptionField.setText(material.getDescription());
        costField.setText(String.valueOf(material.getCost()));
        imagePathField.setText(material.getImagePath());
        materialTypeField.setText(material.getMaterialType());
    }

    public void initButtons(){
        backButton.addActionListener(e -> {
            dispose();
            new MaterialTableForm();
        });
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            if(title.isEmpty() || title.length() > 100){
                DialogUtil.showError(this, "Название введено неправильно");
                return;
            }
            int countInPack = (int) countInPackField.getValue();
            if(countInPack < 0){
                DialogUtil.showError(this, "Количетсво в упаковке введено неправильно");
                return;
            }
            String unit = unitField.getText();
            if(unit.isEmpty() || unit.length() > 100){
                DialogUtil.showError(this, "Единица измерения введено неправильно");
                return;
            }
            int countInStock = (int) countInStockField.getValue();
            if(countInStock < 0){
                DialogUtil.showError(this, "Количетсво в упаковке введено неправильно");
                return;
            }
            int minCount = (int) minCountField.getValue();
            if(minCount < 0){
                DialogUtil.showError(this, "Количетсво в упаковке введено неправильно");
                return;
            }
            String description = descriptionField.getText();
            if(description.isEmpty()){
                DialogUtil.showError(this, "Количетсво в упаковке введено неправильно");
                return;
            }
            double cost = Double.parseDouble(costField.getText());
            if(cost < 0){
                DialogUtil.showError(this, "Количетсво в упаковке введено неправильно");
            }
            String imagePath = imagePathField.getText();
            if(imagePath.isEmpty() || imagePath.length() > 100){
                DialogUtil.showError(this, "Количетсво в упаковке введено неправильно");
                return;
            }
            String materialType = materialTypeField.getText();
            if(materialType.isEmpty() || materialType.length() > 100){
                DialogUtil.showError(this, "Количетсво в упаковке введено неправильно");
                return;
            }

            material.setTitle(title);
            material.setCountInPack(countInPack);
            material.setUnit(unit);
            material.setCountInStock(countInStock);
            material.setMinCount(minCount);
            material.setDescription(description);
            material.setCost(cost);
            material.setImagePath(imagePath);
            material.setMaterialType(materialType);

            try {
                MaterialEntityManager.update(material);
                DialogUtil.showInfo(this,"Запись изменена");
                dispose();
                new MaterialTableForm();
            } catch (SQLException ex) {
                ex.printStackTrace();
                DialogUtil.showError(this, "Запись не изменена");
            }
        });
        deleteButton.addActionListener(e -> {
            if(JOptionPane.showConfirmDialog(this, "Удалить запись?","Подтверждение", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try {
                    MaterialEntityManager.delete(material);
                    DialogUtil.showInfo(this, "Запись была удалена");
                    dispose();
                    new MaterialTableForm();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    DialogUtil.showError(this, "Запись не удалена");
                }
            }
        });
    }
}
