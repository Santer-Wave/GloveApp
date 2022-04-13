package org.glove.app.ui;

import org.glove.app.entity.MaterialEntity;
import org.glove.app.manager.MaterialEntityManager;
import org.glove.app.util.BaseForm;
import org.glove.app.util.CustomTableModel;
import org.glove.app.util.DialogUtil;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class MaterialTableForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JButton createButton;

    private CustomTableModel<MaterialEntity> model;

    public MaterialTableForm(){
        super(1200, 800);
        setContentPane(mainPanel);
        initButtons();
        initTable();
        setVisible(true);
    }

    public void initTable(){
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(50);


        try {
            model = new CustomTableModel<>(
                    MaterialEntity.class,
                    new String[]{"ID","Название","Количество в упаковке","Единица измерения","Количество на складе","Минимальное количество","Описание","Стоимость","Путь до картинки","Типа материала"},
                    MaterialEntityManager.selectAll()
                );
            table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtil.showError(this, "Не удалось загрузить данные");
        }
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    int row = table.rowAtPoint(e.getPoint());
                    dispose();
                    new MaterialUpdateForm(model.getRows().get(row));
                }
            }
        });
    }

    public void initButtons(){
        createButton.addActionListener(e -> {
            dispose();
            new MaterialCreateForm();
        });
    }
}
