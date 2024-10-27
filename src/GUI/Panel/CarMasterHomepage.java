package GUI.Panel;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class CarMasterHomepage extends JFrame {
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel footerPanel;
    private JPanel mainPanel; // JPanel chính chứa header, content và footer

    public CarMasterHomepage() {
        setTitle("CarMaster Homepage");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeComponents();

        // Tạo JScrollPane cho mainPanel và thêm vào JFrame
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initializeComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        initializeHeader();
        initializeContent();
        initializeFooter();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private void initializeHeader() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel bannerLabel = new JLabel(new ImageIcon("path/to/banner/image.jpg"));
        bannerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(bannerLabel);

        JPanel brandPanel = new JPanel(new FlowLayout());
        String[] brands = {"Toyota", "Honda", "Ford", "Chevrolet", "Nissan"};
        for (String brand : brands) {
            JLabel brandLabel = new JLabel(new ImageIcon("./src/img/OIP.jpg"));
            brandPanel.add(brandLabel);
        }
        headerPanel.add(brandPanel);
    }

    private void initializeContent() {
        contentPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] productNames = {"Toyota Camry", "Honda Civic", "Ford F-150", "Chevrolet Malibu"};
        double[] originalPrices = {35000, 30000, 45000, 32000};
        double[] discountedPrices = {32000, 28000, 42000, 29500};
        String[] imagePaths = {
            "./src/img_product/Toyota-Vios.png",
            "./src/img_product/Toyota-Vios.png",
            "./src/img_product/Toyota-Vios.png",
            "./src/img_product/Toyota-Vios.png"
        };

        for (int i = 0; i < productNames.length; i++) {
            JPanel productPanel = createProductPanel(productNames[i], originalPrices[i], discountedPrices[i], imagePaths[i]);
            contentPanel.add(productPanel);
        }
    }

    private JPanel createProductPanel(String name, double originalPrice, double discountedPrice, String imagePath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel imageLabel = new JLabel(new ImageIcon(imagePath));
        panel.add(imageLabel);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(nameLabel);

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

        JLabel originalPriceLabel = new JLabel(currencyFormat.format(originalPrice));
        originalPriceLabel.setForeground(Color.GRAY);
        originalPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(originalPriceLabel);

        JLabel discountedPriceLabel = new JLabel(currencyFormat.format(discountedPrice));
        discountedPriceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        discountedPriceLabel.setForeground(new Color(0, 123, 255));
        discountedPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(discountedPriceLabel);

        JButton detailsButton = new JButton("View Details");
        detailsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(detailsButton);

        return panel;
    }

    private void initializeFooter() {
        footerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel contactPanel = new JPanel();
        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.Y_AXIS));
        contactPanel.setBackground(new Color(245, 245, 245));
        contactPanel.add(new JLabel("Contact Us"));
        contactPanel.add(new JLabel("123 Car Street, Auto City, AC 12345"));
        contactPanel.add(new JLabel("Phone: (123) 456-7890"));
        contactPanel.add(new JLabel("Email: info@carmaster.com"));
        footerPanel.add(contactPanel);

        JPanel hoursPanel = new JPanel();
        hoursPanel.setLayout(new BoxLayout(hoursPanel, BoxLayout.Y_AXIS));
        hoursPanel.setBackground(new Color(245, 245, 245));
        hoursPanel.add(new JLabel("Opening Hours"));
        hoursPanel.add(new JLabel("Monday - Friday: 9am - 6pm"));
        hoursPanel.add(new JLabel("Saturday: 10am - 4pm"));
        hoursPanel.add(new JLabel("Sunday: Closed"));
        footerPanel.add(hoursPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CarMasterHomepage().setVisible(true);
            }
        });
    }
}
