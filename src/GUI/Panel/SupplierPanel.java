package GUI.Panel;

import GUI.Dialog.SupplierDialog;
import BUS.SupplierBUS;
import DTO.SupplierDTO;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Main;
import helper.JTableExporter;
import helper.Tool;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class SupplierPanel extends JPanel implements ActionListener, ItemListener {

    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JTable spTable;
    private JScrollPane spTableScroll;
    private MainFunction mainFunction;
    private IntegratedSearch search;
    private JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    private Color BackgroundColor = new Color(240, 247, 250);
    private DefaultTableModel tblModel;
    private Main m;
    private SupplierBUS spBUS;
    private ArrayList<SupplierDTO> spList;

    private void initComponent() {
        spBUS = new SupplierBUS();
        spList = spBUS.getList();

        // Set model table
        spTable = new JTable();
        spTableScroll = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Email", "Số điện thoại" };
        tblModel.setColumnIdentifiers(header);
        spTable.setModel(tblModel);
        spTable.setFocusable(false);
        spTable.setAutoCreateRowSorter(true);
        spTableScroll.setViewportView(spTable);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = spTable.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(centerRenderer);
        columnModel.getColumn(0).setPreferredWidth(2);
        columnModel.getColumn(2).setPreferredWidth(300);
        columnModel.getColumn(4).setCellRenderer(centerRenderer);

        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4 chỉ để thêm contentCenter ở
        // giữa mà contentCenter không bị dính sát vào các thành phần khác
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

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        // functionBar là thanh bên trên chứa các nút chức năng như thêm xóa sửa, và tìm
        // kiếm
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] action = { "create", "update", "import", "export" };
        mainFunction = new MainFunction(m.getEmployee().getPermissionID(), "FT000002", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch();
        search.getTxtSearchForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String txt = search.getTxtSearchForm().getText();
                loadDataIntoTable(spBUS.search(txt));
            }
        });

        search.getBtnReset().addActionListener(this);
        functionBar.add(search);
        contentCenter.add(functionBar, BorderLayout.NORTH);
        // main là phần ở dưới để thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        // main.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(spTableScroll);
    }

    public SupplierPanel(Main m) {
        this.m = m;
        initComponent();
        spTable.setDefaultEditor(Object.class, null);
        loadNewDataIntoTable();
    }

    public void loadNewDataIntoTable() {
        spList = spBUS.getList();
        loadDataIntoTable(spList);
    }

    public void loadDataIntoTable(ArrayList<SupplierDTO> result) {
        tblModel.setRowCount(0);
        if (result == null) {
            return;
        }

        for (SupplierDTO spDTO : result) {
            tblModel.addRow(new Object[] {
                    spDTO.getSupplierID(), spDTO.getSupplierName(), spDTO.getAddress(), spDTO.getEmail(),
                    spDTO.getPhone()
            });
        }
    }

    public void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @SuppressWarnings("resource")
    public void importExcel() {
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;
        JFileChooser jf = new JFileChooser();
        int result = jf.showOpenDialog(null);
        jf.setDialogTitle("Open file");
        int k = 0;
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = jf.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);
                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
                    XSSFRow excelRow = excelSheet.getRow(row);

                    String supplierID = "SP" + Tool.randomID();
                    String supplierName = excelRow.getCell(0).getStringCellValue();
                    String address = excelRow.getCell(1).getStringCellValue();
                    String email = excelRow.getCell(2).getStringCellValue();
                    String phone = excelRow.getCell(3).getStringCellValue();
                    if (Tool.isEmpty(supplierName) || Tool.isEmpty(email)
                            || !Tool.isEmail(email) || Tool.isEmpty(phone) || !Tool.isPhoneNumber(phone)
                            || phone.length() != 10 || Tool.isEmpty(address)) {
                        k++;
                        return;
                    }

                    spBUS.add(new SupplierDTO(supplierID, supplierName, address, email, phone));
                }

                JOptionPane.showMessageDialog(this, "Nhập dữ liệu thành công");

                if (k != 0) {
                    JOptionPane.showMessageDialog(this, "Những dữ liệu không chuẩn không được thêm vào");
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Lỗi đọc file");
            } catch (IOException ex) {
                System.out.println("Lỗi đọc file");
            }
        }

        loadNewDataIntoTable();
    }

    public int getRowSelected() {
        int index = spTable.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp");
        }
        return index;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btn.get("create")) {
            new SupplierDialog(this, owner, "Thêm nhà cung cấp", true, "create");
            loadNewDataIntoTable();
        } else if (e.getSource() == mainFunction.btn.get("update")) {
            int index = getRowSelected();
            if (index != -1) {
                new SupplierDialog(this, owner, "Chỉnh sửa nhà cung cấp", true, "update",
                        spList.get(index));
            }
            loadNewDataIntoTable();
        } else if (e.getSource() == search.getBtnReset()) {
            search.getTxtSearchForm().setText("");
            loadNewDataIntoTable();
        } else if (e.getSource() == mainFunction.btn.get("import")) {
            importExcel();
        } else if (e.getSource() == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(spTable);
            } catch (IOException ex) {
                Logger.getLogger(SupplierPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public SupplierBUS getSupplierBUS() {
        return spBUS;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String txt = search.getTxtSearchForm().getText();
        loadDataIntoTable(spBUS.search(txt));
    }
}
