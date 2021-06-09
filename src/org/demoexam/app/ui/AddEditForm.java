package org.demoexam.app.ui;

import org.demoexam.app.data.entity.PersonEntity;
import org.demoexam.app.data.manager.PersonManager;
import org.demoexam.app.util.BaseForm;
import org.demoexam.app.util.TableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Date;

public class AddEditForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField surnameField;
    private JTextField nameField;
    private JTextField patronomicField;
    private JTextField birthdateField;
    private JTextField birthPlaceField;
    private JTextField idField;
    private JTextField imagePathField;
    private JComboBox genderBox;
    private JButton cancelButton;
    private JButton saveButton;
    private JLabel idLabel;

    PersonEntity personEntity = null;

    public AddEditForm() {
        setContentPane(mainPanel);
        initUi();

        setVisible(true);
    }

    public AddEditForm(PersonEntity personEntity) {
        setContentPane(mainPanel);
        this.personEntity = personEntity;
        initUi();

        setVisible(true);
    }

    private void initUi() {
        genderBox.addItem("мужской");
        genderBox.addItem("женский");

        if (personEntity != null) {
            idField.setText(String.valueOf(personEntity.getId()));
            idField.setEditable(false);
            surnameField.setText(personEntity.getSurname());
            nameField.setText(personEntity.getName());
            patronomicField.setText(personEntity.getPatronymic());
            genderBox.setSelectedItem(personEntity.getGender());
            birthdateField.setText(String.valueOf(personEntity.getBirthdate()));
            birthPlaceField.setText(personEntity.getPlace_of_birth());
            imagePathField.setText(personEntity.getPhoto_path());
        } else {
            idLabel.setVisible(false);
            idField.setVisible(false);
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PersonManager personManager = new PersonManager();
                if (personEntity == null) {
                    personEntity = new PersonEntity(
                            surnameField.getText(),
                            nameField.getText(),
                            patronomicField.getText(),
                            genderBox.getSelectedItem().toString(),
                            new Date(birthdateField.getText()),
                            birthPlaceField.getText(),
                            imagePathField.getText()
                    );

                    personManager.add(personEntity);

                    dispose();
                    setVisible(false);
                    new PersonTableForm();
                } else {
                    personEntity.setSurname(surnameField.getText());
                    personEntity.setName(nameField.getText());
                    personEntity.setPatronymic(patronomicField.getText());
                    personEntity.setGender(genderBox.getSelectedItem().toString());
                    personEntity.setBirthdate(new Date(birthdateField.getText()));
                    personEntity.setPlace_of_birth(birthPlaceField.getText());
                    personEntity.setPhoto_path(imagePathField.getText());

                    personManager.update(personEntity);

                    dispose();
                    setVisible(false);
                    new PersonTableForm();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PersonTableForm();
            }
        });
    }

    @Override
    public int getFormWidth() {
        return 400;
    }

    @Override
    public int getFormHeight() {
        return 400;
    }
}
