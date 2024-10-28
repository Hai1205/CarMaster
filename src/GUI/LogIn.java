package GUI;

import BUS.EmployeeBUS;
import GUI.Component.InputForm;
import GUI.Dialog.ForgotPassword;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EmptyBorder;

public class LogIn extends JFrame implements KeyListener {

    private JPanel pnlMain, pnlLogIn;
    private JLabel lbl3, lbl6, lbl7;
    private InputForm txtEmail, txtPassword;
    private EmployeeBUS epBUS;

    Color FontColor = new Color(96, 125, 139);

    public LogIn() {
        initComponent();
        txtEmail.setText("hainguyenhoang1205@gmail.com");
        txtPassword.setPass("nhh1205");
    }

    private void initComponent() {
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/CarMaster.png"));
        setIconImage(icon);

        FlatRobotoFont.install();
        FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
        FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
        FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();
        UIManager.put("PasswordField.showRevealButton", true);
        
        this.setSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));
        this.setTitle("Đăng nhập");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame jf = this;

        pnlMain = new JPanel();
        pnlMain.setBackground(Color.white);
        pnlMain.setBorder(new EmptyBorder(20, 0, 0, 0));

        pnlMain.setPreferredSize(new Dimension(500, 740));
        pnlMain.setLayout(new FlowLayout(1, 0, 10));

        epBUS = new EmployeeBUS(this);

        lbl3 = new JLabel("ĐĂNG NHẬP VÀO HỆ THỐNG");
        lbl3.setFont(new Font(FlatRobotoFont.FAMILY_SEMIBOLD, Font.BOLD, 20));
        pnlMain.add(lbl3);

        JPanel paneldn = new JPanel();
        paneldn.setBackground(Color.BLACK);
        paneldn.setPreferredSize(new Dimension(400, 200));
        paneldn.setLayout(new GridLayout(2, 1));

        txtEmail = new InputForm("Tên đăng nhập");
        paneldn.add(txtEmail);
        txtPassword = new InputForm("Mật khẩu", "password");
        paneldn.add(txtPassword);

        txtEmail.getTxtForm().addKeyListener(this);
        txtPassword.getTxtPass().addKeyListener(this);

        pnlMain.add(paneldn);

        lbl6 = new JLabel("ĐĂNG NHẬP");
        lbl6.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 16));
        lbl6.setForeground(Color.white);

        pnlLogIn = new JPanel();
        pnlLogIn.putClientProperty(FlatClientProperties.STYLE, "arc: 99");
        pnlLogIn.setBackground(Color.BLACK);
        pnlLogIn.setPreferredSize(new Dimension(380, 45));
        pnlLogIn.setLayout(new FlowLayout(1, 0, 15));

        pnlLogIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                pnlLogInMouseEntered(evt);
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                try {
                    pnlLogInMousePressed(evt);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                pnlLogInMouseExited(evt);
            }
        });
        pnlLogIn.add(lbl6);

        lbl7 = new JLabel("Quên mật khẩu", JLabel.RIGHT);
        lbl7.setPreferredSize(new Dimension(380, 50));
        lbl7.setFont(new Font(FlatRobotoFont.FAMILY, Font.ITALIC, 18));
        lbl7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                ForgotPassword fgp = new ForgotPassword(jf, true);
                fgp.setVisible(true);
            }
        });
        pnlMain.add(lbl7);

        pnlMain.add(pnlLogIn);

        this.add(pnlMain, BorderLayout.EAST);
    }

    public String getTxtEmail() {
        return txtEmail.getText();
    }

    public String getTxtPassword() {
        return txtPassword.getPass();
    }

    private void pnlLogInMousePressed(java.awt.event.MouseEvent evt) throws UnsupportedLookAndFeelException {
        epBUS.checkLogin();
    }

    private void pnlLogInMouseEntered(java.awt.event.MouseEvent evt) {
        pnlLogIn.setBackground(FontColor);
        pnlLogIn.setForeground(Color.black);
    }

    private void pnlLogInMouseExited(java.awt.event.MouseEvent evt) {
        pnlLogIn.setBackground(Color.BLACK);
        pnlLogIn.setForeground(Color.white);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                epBUS.checkLogin();
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        LogIn login = new LogIn();
        login.setVisible(true);
    }
}   