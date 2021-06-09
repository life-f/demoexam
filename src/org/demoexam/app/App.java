package org.demoexam.app;

import org.demoexam.app.ui.PersonTableForm;
import org.demoexam.app.util.BaseForm;
import org.demoexam.app.util.Dialog;
import org.demoexam.app.util.FontUtil;
import org.demoexam.app.util.Form;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static App instance;

    public static void main(String[] args) {
        new App();
    }

    public App() {
        instance = this;

        initUi();
        initDatabase();

        new PersonTableForm();
    }

    private void initUi() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Form.setBaseApplicationTitle("Люди");

            URL url = App.class.getClassLoader().getResource("service_logo.png");
            if (url != null)
                Form.setBaseApplicationIcon(ImageIO.read(url));
        } catch (Exception e) {
            e.printStackTrace();
        }

        FontUtil.changeAllFonts(new FontUIResource("Times New Roman", Font.TRUETYPE_FONT, 16));
    }

    private void initDatabase() {
        try (Connection c = this.getConnection()) {

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Dialog.showError("Нет подключения к бд");
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "1234");
    }
}
