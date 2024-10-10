package GUI.Dialog;

import BUS.ImportBUS;
import BUS.InvoiceBUS;
import DTO.ImportDetailDTO;
import DTO.ImportDTO;
import DTO.InvoiceDTO;
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
    private InputForm txtID, txtEmployeeID, txtSupplierID, txtCreationDate;
    private DefaultTableModel tblModel;
    private JTable table;
    private JScrollPane scrollTable;

    private ImportDTO ipDTO;
    // private InvoiceDTO ivDTO;
    private ImportBUS ipBUS;
    // private InvoiceBUS ivBUS;

    private ButtonCustom btnPdf;
    private ButtonCustom btnCanel;

    private ArrayList<ImportDetailDTO> ipdList;

    public DetailDialog(JFrame owner, String title, boolean modal, ImportDTO ipDTO) {
        super(owner, title, modal);
        this.ipDTO = ipDTO;
        ipBUS = new ImportBUS();
        ipdList = ipBUS.getListDetailByID(ipDTO.getImportID());
        initComponent(title);
        initImport();
        loadDataIntoDetailTable(ipdList);
        this.setVisible(true);
    }

    // public DetailDialog(JFrame owner, String title, boolean modal, InvoiceDTO ivDTO) {
    //     super(owner, title, modal);
    //     this.ivDTO = ivDTO;
    //     ivBUS = new InvoiceBUS();
    //     ipdList = ivBUS.selectCTP(ivDTO.getInvoiceID());
    //     chitietsanpham = ctspBus.getChiTietSanPhamFromMaPX(ivDTO.getInvoiceID());
    //     initComponent(title);
    //     initInvoice();
    //     loadDataIntoDetailTable(ipdList);
    //     this.setVisible(true);
    // }

    public void initImport() {
        txtID.setText(ipDTO.getImportID());
        txtSupplierID.setText(ipBUS.getSupplierNameByID(ipDTO.getSupplierID()));
        txtEmployeeID.setText(ipBUS.getEmployeeNameByID(ipDTO.getEmployeeID()));
        txtCreationDate.setText(Formater.FormatTime(ipDTO.getCreationDate()));
    }

    // public void initInvoice() {
    //     txtID.setText("PX" + Integer.toString(this.ivDTO.getInvoiceID()));
    //     txtSupplierID.setTitle("Khách hàng");
    //     txtSupplierID.setText(KhachHangDAO.getInstance().selectById(ivDTO.getMakh() + "").getHoten());
    //     txtEmployeeID.setText(NhanVienDAO.getInstance().selectById(ivDTO.getManguoitao() + "").getHoten());
    //     txtCreationDate.setText(Formater.FormatTime(ivDTO.getThoigiantao()));
    // }

    public void loadDataIntoDetailTable(ArrayList<ImportDetailDTO> detailList) {
        tblModel.setRowCount(0);
        if(detailList == null) {
            return;
        }

        for(ImportDetailDTO ipdDTO : detailList) {
            tblModel.addRow(new Object[]{
                ipdDTO.getProductID(), ipdDTO.getPrice(), ipdDTO.getQuantity(), ipdDTO.getCost()});
        }
    }

    public void initComponent(String title) {
        this.setSize(new Dimension(1100, 500));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());

        pnmain = new JPanel(new BorderLayout());

        pnmain_top = new JPanel(new GridLayout(1, 4));
        txtID = new InputForm("Mã phiếu");
        txtEmployeeID = new InputForm("Nhân viên nhập");
        txtSupplierID = new InputForm("Nhà cung cấp");
        txtCreationDate = new InputForm("Thời gian tạo");

        txtID.setEditable(false);
        txtEmployeeID.setEditable(false);
        txtSupplierID.setEditable(false);
        txtCreationDate.setEditable(false);

        pnmain_top.add(txtID);
        pnmain_top.add(txtEmployeeID);
        pnmain_top.add(txtSupplierID);
        pnmain_top.add(txtCreationDate);

        pnmain_bottom = new JPanel(new BorderLayout(5, 5));
        pnmain_bottom.setBorder(new EmptyBorder(5, 5, 5, 5));
        pnmain_bottom.setBackground(Color.WHITE);

        pnmain_bottom_left = new JPanel(new GridLayout(1, 1));
        table = new JTable();
        scrollTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Mã sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"};
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
            // if (this.ivDTO != null) {
            //     w.writePX(ivDTO.getInvoiceID());
            // }
            if (this.ipDTO != null) {
                w.writeImport(ipDTO.getImportID());
            }
        }
    }
}
