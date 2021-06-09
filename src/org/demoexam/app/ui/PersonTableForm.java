package org.demoexam.app.ui;

import org.demoexam.app.data.entity.PersonEntity;
import org.demoexam.app.data.manager.PersonManager;
import org.demoexam.app.util.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.sql.SQLException;

public class PersonTableForm extends Form {
    private JTable table;
    private JPanel mainPanel;
    private JButton birthdaySortButton;
    private JComboBox genderBox;
    private JLabel rowCountLabel;
    private JTextField nameSearchField;
    private JButton clearButton;
    private JButton addButton;

    private PersonManager personManager = new PersonManager();
    CustomTableModel<PersonEntity> tableModel;

    boolean birthdaySorter = false;

    public PersonTableForm() {
        setContentPane(mainPanel);

        this.initTable();
        this.initElements();

        setVisible(true);
    }

    private void initTable() {
        this.table.getTableHeader().setReorderingAllowed(false);

        try {
            tableModel = new CustomTableModel<PersonEntity>(PersonEntity.class,
                    new String[]{
                            "id", "фамилия", "имя", "отчество", "пол", "дата рождения", "место рождения", "путь к фото"
                    }
            ) {
                @Override
                public void onUpdateRowsEvent() {
                    rowCountLabel.setText(this.getFilteredRows().size() + "/" + this.getAllRows().size());
                }
            };
            tableModel.setAllRows(personManager.getAll());
            table.setModel(tableModel);

            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (e.getClickCount() == 2 && row != -1) {
                    dispose();
                    new AddEditForm(tableModel.getAllRows().get(row));
                }
            }
        });

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int row = table.getSelectedRow();
                if (row != -1 && e.getKeyCode() == KeyEvent.VK_DELETE) {
                    if (DialogUtil.showConfirm("Вы действительно хотите удалить эту запись?")) {
                        try {
                            personManager.delete(tableModel.getAllRows().get(row));
                            tableModel.getFilteredRows().remove(row);
                            tableModel.fireTableDataChanged();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            DialogUtil.showError("Запись не удалена");
                        }
                    }
                }
            }
        });

        tableModel.getFilters()[0] = personEntity -> {
            String searchText = nameSearchField.getText();
            if (searchText.isEmpty() || searchText == null) {
                return true;
            }
            String str = personEntity.getSurname() + " " + personEntity.getName() + " " + personEntity.getPatronymic();
            return str.contains(searchText);
        };
    }

    private void initElements() {
        nameSearchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                tableModel.updateRows();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                tableModel.updateRows();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                tableModel.updateRows();
            }
        });

        genderBox.addItem("Все");
        genderBox.addItem("Мужской");
        genderBox.addItem("Женский");

        genderBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int selected = genderBox.getSelectedIndex();
                if (selected == 0) {
                    tableModel.getFilters()[1] = null;
                } else if (selected == 1) {
                    tableModel.getFilters()[1] = personEntity -> personEntity.getGender().equals("мужской");
                } else if (selected == 2) {
                    tableModel.getFilters()[1] = personEntity -> personEntity.getGender().equals("женский");
                }
                tableModel.updateRows();
            }
        });

        birthdaySortButton.addActionListener(e -> {
            if (birthdaySorter) {
                tableModel.setSorter((o1, o2) -> o2.getBirthdate().compareTo(o1.getBirthdate()));
            } else {
                tableModel.setSorter((o1, o2) -> o1.getBirthdate().compareTo(o2.getBirthdate()));
            }
            birthdaySorter = !birthdaySorter;
        });

        clearButton.addActionListener(e -> {
            nameSearchField.setText("");
            genderBox.setSelectedIndex(0);
            birthdaySorter = false;
            tableModel.setSorter(null);
        });

        addButton.addActionListener(e -> {
            dispose();
            setVisible(false);

            new AddEditForm();
        });
    }

    @Override
    public int getFormWidth() {
        return 900;
    }

    @Override
    public int getFormHeight() {
        return 700;
    }

    public CustomTableModel<PersonEntity> getTableModel() {
        return tableModel;
    }
}
