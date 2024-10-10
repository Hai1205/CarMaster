/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import BUS.PermissionBUS;
import DTO.FunctionDTO;
import DTO.PermissionDTO;
import DTO.PermissionDetailDTO;
import GUI.Component.ButtonCustom;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import helper.Tool;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author ASUS
 */
public class PermissionDialog extends JDialog implements ActionListener {
    private JLabel lblPermissionName;
    private JTextField txtPermissionName;
    private JPanel jpTop, jpLeft, jpCen, jpBottom;
    private JCheckBox[][] listCheckBox;
    private ButtonCustom btnAdd, btnUpdate,btnCancel;
    private int sizeFunction, sizeAction;
    private ArrayList<FunctionDTO> ftList;
    private final String[] actionID = {"view", "create", "update"};
    private ArrayList<PermissionDetailDTO> pmsdtList;
    private PermissionDTO pmsDTO;
    private final PermissionBUS pmsBUS;
    
    public void initComponents(String type) {
        ftList = pmsBUS.getFtList();
        String[] action = {"Xem", "Tạo mới", "Cập nhật"};
        this.setSize(new Dimension(1000, 580));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));
        
        // Hiển thị tên nhóm quyền
        jpTop = new JPanel(new BorderLayout(20, 10));
        jpTop.setBorder(new EmptyBorder(20, 20, 20, 20));
        jpTop.setBackground(Color.WHITE);
        lblPermissionName = new JLabel("Tên nhóm quyền");
        txtPermissionName = new JTextField();
        txtPermissionName.setPreferredSize(new Dimension(150, 35));
        jpTop.add(lblPermissionName, BorderLayout.WEST);
        jpTop.add(txtPermissionName, BorderLayout.CENTER);

        // Hiển thị danh mục chức năng
        jpLeft = new JPanel(new GridLayout(ftList.size() + 1, 1));
        jpLeft.setBackground(Color.WHITE);
        jpLeft.setBorder(new EmptyBorder(0, 20, 0, 14));
        JLabel function = new JLabel("Danh mục chức năng ");
        function.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 15));
        jpLeft.add(function);
        for (FunctionDTO i : ftList) {
            JLabel lblTenchucnang = new JLabel(i.getFunctionName());
            jpLeft.add(lblTenchucnang);
        }
        
        // Hiển thị chức năng CRUD        
        sizeFunction = ftList.size();
        sizeAction = action.length;
        jpCen = new JPanel(new GridLayout(sizeFunction + 1, sizeAction));
        jpCen.setBackground(Color.WHITE);
        listCheckBox = new JCheckBox[sizeFunction][sizeAction];
        for (int i = 0; i < sizeAction; i++) {
            JLabel lblhanhdong = new JLabel(action[i]);
            lblhanhdong.setHorizontalAlignment(SwingConstants.CENTER);
            jpCen.add(lblhanhdong);
        }
        for (int i = 0; i < sizeFunction; i++) {
            for (int j = 0; j < sizeAction; j++) {
                listCheckBox[i][j] = new JCheckBox();
                listCheckBox[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                jpCen.add(listCheckBox[i][j]);
            }
        }

        // Hiển thị nút thêm
        jpBottom = new JPanel(new FlowLayout());
        jpBottom.setBackground(Color.white);
        jpBottom.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        switch (type) {
            case "create" -> {
                btnAdd = new ButtonCustom("Thêm nhóm quyền", "success", 14);
                btnAdd.addActionListener(this);
                jpBottom.add(btnAdd);
            }
            case "update" -> {
                btnUpdate = new ButtonCustom("Cập nhật nhóm quyền", "success", 14);
                btnUpdate.addActionListener(this);
                jpBottom.add(btnUpdate);
                initUpdate();
            }
            case "view" -> {
                initUpdate();
            }
            default -> throw new AssertionError();
        }
        
        
        btnCancel = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnCancel.addActionListener(this);
        
        jpBottom.add(btnCancel);

        this.add(jpTop, BorderLayout.NORTH);
        this.add(jpLeft, BorderLayout.WEST);
        this.add(jpCen, BorderLayout.CENTER);
        this.add(jpBottom, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    public PermissionDialog(PermissionBUS pmsBUS, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.pmsBUS = pmsBUS;
        initComponents(type);
    }
    
    public PermissionDialog(PermissionBUS pmsBUS, JFrame owner, String title, boolean modal, String type, PermissionDTO pmsDTO) {
        super(owner, title, modal);
        this.pmsBUS = pmsBUS;
        this.pmsDTO = pmsDTO;
        this.pmsdtList = pmsBUS.getPmsdtList(pmsDTO.getPermissionID());
        initComponents(type);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            String permissionID = "PMS" + Tool.randomID();
            String permissionName = txtPermissionName.getText();
            pmsdtList = getPermisionDetailList(permissionID);
            pmsBUS.add(permissionID, permissionName, pmsdtList);
            dispose();
        } else if(e.getSource() == btnUpdate){
            pmsdtList = getPermisionDetailList(pmsDTO.getPermissionID());
            pmsDTO.setPermissionName(txtPermissionName.getText());
            pmsBUS.update(pmsDTO, pmsdtList);
            dispose();
        } else if (e.getSource() == btnCancel) {
            dispose();
        }
    }

    public ArrayList<PermissionDetailDTO> getPermisionDetailList(String permissionID) {
        ArrayList<PermissionDetailDTO> result = new ArrayList<>();
        for (int i = 0; i < sizeFunction; i++) {
            for (int j = 0; j < sizeAction; j++) {
                if (listCheckBox[i][j].isSelected()) {
                    result.add(new PermissionDetailDTO(permissionID, ftList.get(i).getFuctionID(), actionID[j]));
                }
            }
        }
        return result;
    }

    public void initUpdate() {
        this.txtPermissionName.setText(pmsDTO.getPermissionName());
        for (PermissionDetailDTO k : pmsdtList) {
            for (int i = 0; i < sizeFunction; i++) {
                for (int j = 0; j < sizeAction; j++) {
                    if(k.getAction().equals(actionID[j]) && k.getFunctionID().equals(ftList.get(i).getFuctionID())) {
                        listCheckBox[i][j].setSelected(true);
                    }
                }
            }
        }
    }
}
