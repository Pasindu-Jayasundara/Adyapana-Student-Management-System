package model;

import java.awt.Image;
import java.awt.Toolkit;

public class Icon {

    private Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/letter-a (1).png"));

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

}
