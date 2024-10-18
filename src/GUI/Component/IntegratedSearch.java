package GUI.Component;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class IntegratedSearch extends JPanel {
    private  JButton btnReset;
    private  JTextField txtSearchForm;

    public JButton getBtnReset() {
        return btnReset;
    }

    public JTextField getTxtSearchForm() {
        return txtSearchForm;
    }

    private void initComponent() {
        this.setBackground(Color.WHITE);
        BoxLayout bx = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(bx);

        JPanel jpSearch = new JPanel(new BorderLayout(5,10));
        jpSearch.setBorder(new EmptyBorder(18,15,18,15));
        jpSearch.setBackground(Color.white);

        txtSearchForm = new JTextField();
        txtSearchForm.setFont(new Font(FlatRobotoFont.FAMILY, 0, 13));
        txtSearchForm.putClientProperty("JTextField.placeholderText", "Nhập nội dung tìm kiếm...");
        txtSearchForm.putClientProperty("JTextField.showClearButton", true);
        jpSearch.add(txtSearchForm);

        // btnReset = new JButton("Làm mới");
        btnReset = new JButton("");
        btnReset.setFont(new java.awt.Font(FlatRobotoFont.FAMILY, 0, 14));
        btnReset.setIcon(new FlatSVGIcon("./icon/refresh.svg"));
        // btnReset.setPreferredSize(new Dimension(125, 0));
        btnReset.setPreferredSize(new Dimension(40, 0));
        btnReset.addActionListener(this::btnResetActionPerformed);
        jpSearch.add(btnReset,BorderLayout.EAST);
        this.add(jpSearch);
    }

    public IntegratedSearch() {
        initComponent();
    }

    private void btnResetActionPerformed(java.awt.event.ActionEvent e) {
        txtSearchForm.setText("");
    }

    public void setTxtSearchForm(String info) {
        txtSearchForm.setText(info);
    }
}
