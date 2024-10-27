package GUI.Panel;

import BUS.CustomerBUS;
import DTO.CustomerDTO;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Dialog.CustomerDialog;
import GUI.Main;
import java.util.logging.Logger;
import java.util.logging.Level;
import helper.JTableExporter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import helper.Tool;

public class CustomerPanel extends JPanel implements ActionListener, ItemListener {

    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JTable ctmTable;
    private JScrollPane ctmScrollTable;
    private MainFunction mainFunction;
    private JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    private IntegratedSearch search;
    private DefaultTableModel tblModel;
    private CustomerBUS ctmBUS;
    private ArrayList<CustomerDTO> ctmList;
    private Main m;
    private Color BackgroundColor = new Color(240, 247, 250);

    private void initComponent() {
        ctmBUS = new CustomerBUS();
        ctmList = ctmBUS.getList();

        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        ctmTable = new JTable();
        ctmScrollTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "Mã khách hàng", "Tên khách hàng", "Địa chỉ", "Số điện thoại" };
        tblModel.setColumnIdentifiers(header);
        ctmTable.setModel(tblModel);
        ctmTable.setFocusable(false);
        ctmScrollTable.setViewportView(ctmTable);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        ctmTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        ctmTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        ctmTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        ctmTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        ctmTable.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(ctmTable, 0, TableSorter.INTEGER_COMPARATOR);

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

        // functionBar là thanh bên trên chứa các nút chức năng như thêm xóa sửa, và tìm kiếm
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] action = { "create", "update", "import", "export" };
        mainFunction = new MainFunction(m.getEmployee().getPermissionID(), "FT000001", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch();
        search.getTxtSearchForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String txt = search.getTxtSearchForm().getText();
                ctmList = ctmBUS.search(txt);
                loadDataIntoTable(ctmList);
            }
        });

        search.getBtnReset().addActionListener((ActionEvent e) -> {
            search.setTxtSearchForm("");
            loadNewDataIntoTable();
        });
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        // main là phần ở dưới để thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        contentCenter.add(main, BorderLayout.CENTER);

        main.add(ctmScrollTable);
    }

    public CustomerPanel(Main m) {
        this.m = m;
        initComponent();
        ctmTable.setDefaultEditor(Object.class, null);
        loadDataIntoTable(ctmList);
    }

    public void loadDataIntoTable(ArrayList<CustomerDTO> result) {
        tblModel.setRowCount(0);
        if (result == null) {
            return;
        }

        for (CustomerDTO ctmDTO : result) {
            tblModel.addRow(new Object[] {
                    ctmDTO.getCustomerID(), ctmDTO.getCustomerName(), ctmDTO.getAddress(), ctmDTO.getPhone()
            });
        }
    }

    public void loadNewDataIntoTable() {
        ctmList = ctmBUS.getList();
        loadDataIntoTable(ctmList);
    }

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

                    String customerID = ctmBUS.createID();
                    String customerName = excelRow.getCell(0).getStringCellValue();
                    String phone = excelRow.getCell(1).getStringCellValue();
                    String address = excelRow.getCell(2).getStringCellValue();
                    if (Tool.isEmpty(customerName) || Tool.isEmpty(phone)
                            || !Tool.isPhoneNumber(phone) || phone.length() != 10 || Tool.isEmpty(address)) {
                        k++;
                        continue;
                    }
                    ctmBUS.add(new CustomerDTO(customerID, customerName, address, phone));
                }
                JOptionPane.showMessageDialog(this, "Nhập thành công");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (k != 0) {
            JOptionPane.showMessageDialog(this, "Những dữ liệu không hợp lệ không được thêm vào");
        }
        loadNewDataIntoTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btn.get("create")) {
            new CustomerDialog(this, ctmBUS, owner, "Thêm khách hàng", true, "create");
        } else if (e.getSource() == mainFunction.btn.get("update")) {
            int index = ctmTable.getSelectedRow();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng");
                return;
            }

            new CustomerDialog(this, ctmBUS, owner, "Chỉnh sửa khách hàng", true, "update",
                    ctmList.get(index));
        } else if (e.getSource() == mainFunction.btn.get("import")) {
            importExcel();
        } else if (e.getSource() == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(ctmTable);
            } catch (IOException ex) {
                Logger.getLogger(CustomerPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String txt = search.getTxtSearchForm().getText();
        loadDataIntoTable(ctmBUS.search(txt));
    }
}
