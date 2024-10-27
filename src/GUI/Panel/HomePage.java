package GUI.Panel;

import java.awt.*;
import javax.swing.*;
import com.formdev.flatlaf.FlatIntelliJLaf;

public class HomePage extends JPanel {

    private JPanel center;
    private final Image backgroundImage;

    public HomePage() {
        backgroundImage = new ImageIcon(getClass().getResource("/img/background.png")).getImage();
        initComponent();
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void initComponent() {
        this.setLayout(new BorderLayout());
        center = new JPanel();
        center.setOpaque(false);
        center.setPreferredSize(new Dimension(1100, 800));
        center.setLayout(new FlowLayout(1, 50, 50));
        this.add(center, BorderLayout.CENTER);
    }
}