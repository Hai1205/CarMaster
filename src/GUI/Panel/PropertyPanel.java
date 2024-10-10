/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;

import GUI.Component.itemTaskbar;
import GUI.Dialog.Property.ColorDialog;
import GUI.Dialog.Property.DiscountDialog;
import GUI.Dialog.Property.FuelDialog;
import GUI.Dialog.Property.BrandDialog;
import GUI.Dialog.Property.SeatDialog;
import GUI.Dialog.Property.StyleDialog;
import GUI.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author ASUS
 */
public class PropertyPanel extends JPanel {

    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    private JScrollPane scrPane;
    private Main m;
    private itemTaskbar[] listitem;

    String iconst[] = {"brand_100px.svg", "fuel_100px.svg", "seat_100px.svg", "style_100px.svg", "discount_100px.svg", "color_100px.svg"};

    String header[] = {"Thương hiệu", "Nhiên liệu", "Số ghế ngồi", "Kiểu dáng", "Khuyến mãi", "Màu sắc"};
    Color BackgroundColor = new Color(240, 247, 250);
    Color FontColor = new Color(96, 125, 139);
    Color DefaultColor = new Color(255, 255, 255);

    public PropertyPanel(Main m) {
        this.m = m;
        initComponent();
    }

    private void initComponent() {
        listitem = new itemTaskbar[header.length];

        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new GridLayout(3, 2, 20, 20));

        scrPane = new JScrollPane(contentCenter);
        scrPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.add(contentCenter, BorderLayout.CENTER);

        for (int i = 0; i < header.length; i++) {
            listitem[i] = new itemTaskbar(iconst[i], header[i], header[i]);
            contentCenter.add(listitem[i]);
        }

        listitem[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                new BrandDialog(owner, "Quản lý thương hiệu", true, m.getEmployee().getPermissionID());
            }
        });
        listitem[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                new FuelDialog(owner, "Quản lý nhiên liệu", true, m.getEmployee().getPermissionID());
            }
        });
        listitem[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                new SeatDialog(owner, "Quản lý số ghế ngồi", true, m.getEmployee().getPermissionID());
            }
        });

        listitem[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                new StyleDialog(owner, "Quản lý kiểu dáng", true, m.getEmployee().getPermissionID());
            }
        });

        listitem[4].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                new DiscountDialog(owner, "Quản lý khuyến mãi", true, m.getEmployee().getPermissionID());
            }
        });
        listitem[5].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                new ColorDialog(owner, "Quản lý màu sắc", true, m.getEmployee().getPermissionID());
            }
        });
    }

    public void Mouseopress(MouseEvent evt) {
        for (itemTaskbar listitem1 : listitem) {
            if (evt.getSource() == listitem1) {
            }
        }
    }

    public void initPadding() {
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 40));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 40));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(40, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(40, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);
    }
}
