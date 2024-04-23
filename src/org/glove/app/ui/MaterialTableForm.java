package org.glove.app.ui;

import org.glove.app.entity.MaterialEntity;
import org.glove.app.manager.MaterialEntityManager;
import org.glove.app.util.BaseForm;
import org.glove.app.util.CustomTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class MaterialTableForm extends BaseForm {
    private JPanel mainPane;
    private JTable table;
    private JButton createButton;

    private CustomTableModel<MaterialEntity> model;

    public MaterialTableForm(){
        super(1200, 800);
        setContentPane(mainPane);
        initTable();
        initButtons();
        setVisible(true);
    }

    public void initTable(){
        try {
            model = new CustomTableModel<>(
                    MaterialEntity.class,
                    new String[]{"ID","Название","Количество в упаковке","Единица измерения","Количество на складе","Минимальное количество","Описание","Стоимость","Путь до картинки","Типа материала","Картинка"},
                    MaterialEntityManager.selectAll()
            );
            table.setModel(model);
            table.setRowHeight(50);
            TableColumn column = table.getColumn("Путь до картинки");
            column.setMaxWidth(0);
            column.setMinWidth(0);
            column.setPreferredWidth(0);
            table.getTableHeader().setReorderingAllowed(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
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
