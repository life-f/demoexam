package org.demoexam.app.util;

import javax.swing.*;
import java.awt.*;

public abstract class BaseForm extends JFrame {
    private static Image baseApplicationIcon = null;
    private static String baseApplicationTitle = "Заголовок приложения";

    public BaseForm() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(baseApplicationTitle);
        if (baseApplicationIcon != null) {
            setIconImage(baseApplicationIcon);
        }
        setMinimumSize(new Dimension(getFormWidth(), getFormHeight()));
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getFormWidth() / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getFormHeight() / 2
        );
    }

    public Image getBaseApplicationIcon() {
        return baseApplicationIcon;
    }

    public static void setBaseApplicationIcon(Image baseApplicationIcon) {
        BaseForm.baseApplicationIcon = baseApplicationIcon;
    }

    public String getBaseApplicationTitle() {
        return baseApplicationTitle;
    }

    public static void setBaseApplicationTitle(String baseApplicationTitle) {
        BaseForm.baseApplicationTitle = baseApplicationTitle;
    }

    public abstract int getFormWidth();

    public abstract int getFormHeight();
}
