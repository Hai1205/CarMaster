package GUI.Dialog;

import BUS.ImportBUS;
import BUS.InvoiceBUS;
import DTO.ImportDetailDTO;
import DTO.ImportDTO;
import DTO.InvoiceDTO;
import DTO.InvoiceDetailDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import helper.Formater;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import helper.WritePDF;

public final class DetailDialog extends JDialog implements ActionListener {

    private HeaderTitle titlePage;
    private JPanel pnmain, pnmain_top, pnmain_bottom, pnmain_bottom_left, pnmain_btn;
    private InputForm txtID, txtEmployeeID, txtTransactionID, txtCreationDate, txtTotalCost;
    private DefaultTableModel tblModel;
    private JTable table;
    private JScrollPane scrollTable;

    private ImportDTO ipDTO;
    private InvoiceDTO ivDTO;
    private ImportBUS ipBUS;
    private InvoiceBUS ivBUS;

    private ButtonCustom btnPdf;
    private ButtonCustom btnCanel;

    private ArrayList<ImportDetailDTO> ipdList;
    private ArrayList<InvoiceDetailDTO> ivdList;

    public DetailDialog(JFrame owner, String title, boolean modal, ImportDTO ipDTO) {
        super(owner, title, modal);
        this.ipDTO = ipDTO;
        ipBUS = new ImportBUS();
        ipdList = ipBUS.getListDetailByID(ipDTO.getImportID());
        initComponent(title);
        initImport();
        loadDataIntoImportDetailTable(ipdList);
        this.setVisible(true);
    }

    public DetailDialog(JFrame owner, String title, boolean modal, InvoiceDTO ivDTO) {
        super(owner, title, modal);
        this.ivDTO = ivDTO;
        ivBUS = new InvoiceBUS();
        ivdList = ivBUS.getListDetailByID(ivDTO.getInvoiceID());
        initComponent(title);
        initInvoice();
        loadDataIntoInvoiceDetailTable(ivdList);
        this.setVisible(true);
    }

    public void initImport() {
        txtID.setText(ipDTO.getImportID());
        txtTransactionID.setText(ipBUS.getSupplierNameByID(ipDTO.getSupplierID()));
        txtEmployeeID.setText(ipBUS.getEmployeeNameByID(ipDTO.getEmployeeID()));
        txtCreationDate.setText(Formater.FormatTime(ipDTO.getCreationDate()));
        txtTotalCost.setText(Formater.FormatVND(ipDTO.getTotalCost()));
    }
    
    public void initInvoice() {
        txtID.setText(ivDTO.getInvoiceID());
        txtTransactionID.setText(ivBUS.getCustomerNameByID(ivDTO.getCustomerID()));
        txtEmployeeID.setText(ivBUS.getEmployeeNameByID(ivDTO.getEmployeeID()));
        txtCreationDate.setText(Formater.FormatTime(ivDTO.getCreationDate()));
        txtTotalCost.setText(Formater.FormatVND(ivDTO.getTotalCost()));
    }

    public void loadDataIntoImportDetailTable(ArrayList<ImportDetailDTO> detailList) {
        tblModel.setRowCount(0);
        if (detailList == null) {
            return;
        }

        for (ImportDetailDTO ipdDTO : detailList) {
            tblModel.addRow(new Object[] {
                    ipdDTO.getProductID(), ipdDTO.getPrice(), ipdDTO.getQuantity(), ipdDTO.getCost() });
        }
    }

    public void loadDataIntoInvoiceDetailTable(ArrayList<InvoiceDetailDTO> detailList) {
        tblModel.setRowCount(0);
        if (detailList == null) {
            return;
        }

        for (InvoiceDetailDTO ivdDTO : detailList) {
            tblModel.addRow(new Object[] {
                    ivdDTO.getProductID(), ivdDTO.getPrice(), ivdDTO.getQuantity(), ivdDTO.getCost() });
        }
    }

    public void initComponent(String title) {
        this.setSize(new Dimension(1100, 500));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());

        pnmain = new JPanel(new BorderLayout());

        pnmain_top = new JPanel(new GridLayout(1, 4));
        txtEmployeeID = new InputForm("Nhân viên");

        if (ivDTO == null) {
            txtID = new InputForm("Mã phiếu nhập");
        } else {
            txtID = new InputForm("Mã hóa đơn");
        }

        if (ivDTO == null) {
            txtTransactionID = new InputForm("Nhà cung cấp");
        } else {
            txtTransactionID = new InputForm("Khách hàng");
        }

        txtCreationDate = new InputForm("Thời gian tạo");
        txtTotalCost = new InputForm("Tổng tiền");

        txtID.setEditable(false);
        txtEmployeeID.setEditable(false);
        txtTransactionID.setEditable(false);
        txtCreationDate.setEditable(false);
        txtTotalCost.setEditable(false);

        pnmain_top.add(txtID);
        pnmain_top.add(txtEmployeeID);
        pnmain_top.add(txtTransactionID);
        pnmain_top.add(txtCreationDate);
        pnmain_top.add(txtTotalCost);

        pnmain_bottom = new JPanel(new BorderLayout(5, 5));
        pnmain_bottom.setBorder(new EmptyBorder(5, 5, 5, 5));
        pnmain_bottom.setBackground(Color.WHITE);

        pnmain_bottom_left = new JPanel(new GridLayout(1, 1));
        table = new JTable();
        table.setDefaultEditor(Object.class, null);
        scrollTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "Mã sản phẩm", "Đơn giá", "Số lượng", "Thành tiền" };
        tblModel.setColumnIdentifiers(header);
        table.setModel(tblModel);
        table.setFocusable(false);
        scrollTable.setViewportView(table);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        pnmain_bottom_left.add(scrollTable);
        pnmain_bottom.add(pnmain_bottom_left, BorderLayout.CENTER);

        pnmain_btn = new JPanel(new FlowLayout());
        pnmain_btn.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnmain_btn.setBackground(Color.white);
        btnPdf = new ButtonCustom("In", "success", 14);
        btnCanel = new ButtonCustom("Đóng", "danger", 14);
        btnPdf.addActionListener(this);
        btnCanel.addActionListener(this);
        pnmain_btn.add(btnPdf);
        pnmain_btn.add(btnCanel);

        pnmain.add(pnmain_top, BorderLayout.NORTH);
        pnmain.add(pnmain_bottom, BorderLayout.CENTER);
        pnmain.add(pnmain_btn, BorderLayout.SOUTH);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnmain, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnCanel) {
            dispose();
        }
        if (source == btnPdf) {
            WritePDF w = new WritePDF();
            if (this.ivDTO != null) {
                w.writeInvoice(ivDTO.getInvoiceID());
            } else {
                w.writeImport(ipDTO.getImportID());
            }
        }
    }
}
