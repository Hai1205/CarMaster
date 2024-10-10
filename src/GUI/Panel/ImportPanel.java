package GUI.Panel;

import GUI.Main;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Dialog.DetailDialog;
import helper.Formater;
import helper.JTableExporter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import BUS.ImportBUS;
import DTO.EmployeeDTO;
import DTO.ImportDTO;

public final class ImportPanel extends JPanel
        implements ActionListener, KeyListener, PropertyChangeListener, ItemListener {

    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JTable ipTable;
    private JScrollPane scrollImportTable;
    private MainFunction mainFunction;
    private IntegratedSearch search;
    private DefaultTableModel tblModel;

    private CreateImportPanel cipPanel;
    private Main m;
    private EmployeeDTO epDTO;

    private ImportBUS ipBUS;

    private Color BackgroundColor = new Color(240, 247, 250);

    public ImportPanel(Main m, EmployeeDTO epDTO) {
        ipBUS = new ImportBUS();
        this.m = m;
        this.epDTO = epDTO;
        initComponent();
        loadDataIntoTable(ipBUS.getList());
    }

    public void initPadding() {
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        ipTable = new JTable();
        scrollImportTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "Mã phiếu nhập", "Nhà cung cấp", "Nhân viên nhập", "Thời gian tạo",
                "Tổng tiền" };
        tblModel.setColumnIdentifiers(header);
        ipTable.setModel(tblModel);
        ipTable.setDefaultEditor(Object.class, null);
        scrollImportTable.setViewportView(ipTable);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        ipTable.setDefaultRenderer(Object.class, centerRenderer);
        ipTable.setFocusable(false);
        ipTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        ipTable.getColumnModel().getColumn(1).setPreferredWidth(10);
        ipTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        ipTable.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(ipTable, 4, TableSorter.VND_CURRENCY_COMPARATOR);

        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] action = { "create", "detail", "export" };
        mainFunction = new MainFunction(m.getEmployee().getPermissionID(), "FT000004", action);

        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }

        functionBar.add(mainFunction);
        search = new IntegratedSearch();
        search.getTxtSearchForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String txt = search.getTxtSearchForm().getText();
                loadDataIntoTable(ipBUS.search(txt));
            }
        });
        search.getBtnReset().addActionListener(this);
        functionBar.add(search);
        contentCenter.add(functionBar, BorderLayout.NORTH);

        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        main.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollImportTable);
    }

    public void loadDataIntoTable(ArrayList<ImportDTO> ipList) {
        tblModel.setRowCount(0);

        if (tblModel == null) {
            return;
        }

        for (ImportDTO ipDTO : ipList) {
            String supplierName = ipBUS.getSupplierNameByID(ipDTO.getSupplierID());
            String employeeName = ipBUS.getEmployeeNameByID(ipDTO.getEmployeeID());

            tblModel.addRow(new Object[] {
                    ipDTO.getImportID(), supplierName, employeeName, ipDTO.getCreationDate(),
                    Formater.FormatVND(ipDTO.getTotalCost())
            });
        }
    }

    public int getRowSelected() {
        int index = ipTable.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập");
        }
        return index;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == mainFunction.btn.get("create")) {
            cipPanel = new CreateImportPanel(epDTO, "create", m);
            m.setPanel(cipPanel);
        } else if (source == mainFunction.btn.get("detail")) {
            int index = getRowSelected();
            if (index != -1) {
                new DetailDialog(m, "Thông tin phiếu nhập", true, ipBUS.getList().get(index));
            }
        } else if (source == search.getBtnReset()) {
            search.getTxtSearchForm().setText("");
            loadDataIntoTable(ipBUS.getList());
        } else if (source == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(ipTable);
            } catch (IOException ex) {
                Logger.getLogger(ImportPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }
}
