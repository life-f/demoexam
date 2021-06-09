package org.demoexam.app;

import org.demoexam.app.ui.PersonTableForm;
import org.demoexam.app.util.BaseForm;
import org.demoexam.app.util.DialogUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {
    public static Application instance;

    public static void main(String[] args) {
        new Application();
    }

    public Application() {
        instance = this;

        this.initDatabase();
        this.initUi();

        new PersonTableForm();
    }

    private void initUi() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        BaseForm.setBaseApplicationTitle("Мой ДЭ");

        try {
            URL url = Application.class.getClassLoader().getResource("service_logo.png");
            if (url != null) {
                BaseForm.setBaseApplicationIcon(ImageIO.read(url));
            } else {
                DialogUtil.showWarn("Иконка не найдена");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDatabase() {
        try (Connection c = getConnection()) {

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.exit(-1);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "1234");
    }
}
