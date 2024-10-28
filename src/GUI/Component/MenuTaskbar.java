package GUI.Component;

import BUS.*;
import DTO.*;
import GUI.*;
import GUI.Dialog.*;
import GUI.Panel.*;
import GUI.Panel.Statistic.Statistic;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MenuTaskbar extends JPanel {

    private HomePage homePage;
    private ProductPanel pdPanel;
    private PropertyPanel pptPanel;
    private ImportPanel ipPanel;
    private InvoicePanel ivPanel;
    private CustomerPanel ctmPanel;
    private SupplierPanel spPanel;
    private EmployeePanel epPanel;
    private PermissionPanel pmsPanel;
    private Statistic statistic;
    private final String[][] getSt = {
            { "Trang chủ", "home.svg", "homePage" },
            { "Sản phẩm", "product.svg", "FT000006" },
            { "Thuộc tính", "brand.svg", "FT000008" },
            { "Nhập hàng", "import.svg", "FT000004" },
            { "Bán hàng", "export.svg", "FT000009" },
            { "Khách hàng", "customer.svg", "FT000001" },
            { "Nhà cung cấp", "supplier.svg", "FT000002" },
            { "Nhân viên", "staff.svg", "FT000003" },
            { "Phân quyền", "permission.svg", "FT000005" },
            { "Thống kê", "statistical.svg", "FT000007" },
            { "Đăng xuất", "log_out.svg", "logout" }, };

    private final Main main;
    private EmployeeDTO epDTO;
    public itemTaskbar[] listitem;
    private EmployeeBUS epBUS;

    private JLabel lblPermissionName, lblEmail;
    private JScrollPane scrollPane;

    // tasbarMenu chia thành 3 phần chính là pnlCenter, pnlTop, pnlBottom
    private JPanel pnlCenter, pnlTop, pnlBottom, bar1, bar2, bar3, bar4;

    private final Color FontColor = new Color(96, 125, 139);
    private final Color DefaultColor = new Color(255, 255, 255);
    private final Color HowerFontColor = new Color(1, 87, 155);
    private final Color HowerBackgroundColor = new Color(187, 222, 251);
    private ArrayList<PermissionDetailDTO> pmsdtList;
    private PermissionDTO pmsDTO;
    private final PermissionBUS pmsBUS = new PermissionBUS();
    private final JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);

    public MenuTaskbar(Main main) {
        this.main = main;
        initComponent();
    }

    public MenuTaskbar(Main main, EmployeeDTO epDTO) {
        this.main = main;
        this.epDTO = epDTO;
        this.pmsDTO = pmsBUS.getPermissionByID(epDTO.getPermissionID());
        pmsdtList = pmsBUS.getPmsdtList(epDTO.getPermissionID());
        initComponent();
    }

    private void initComponent() {
        epBUS = new EmployeeBUS();
        listitem = new itemTaskbar[getSt.length];
        this.setOpaque(true);
        this.setBackground(DefaultColor);
        this.setLayout(new BorderLayout(0, 0));

        // bar1, bar là các đường kẻ mỏng giữa taskbarMenu và MainContent
        pnlTop = new JPanel();
        pnlTop.setPreferredSize(new Dimension(250, 80));
        pnlTop.setBackground(DefaultColor);
        pnlTop.setLayout(new BorderLayout(0, 0));
        this.add(pnlTop, BorderLayout.NORTH);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BorderLayout(0, 0));
        pnlTop.add(info, BorderLayout.CENTER);

        // Thông tin tài khoản và quyền
        information(info);

        bar1 = new JPanel();
        bar1.setBackground(new Color(204, 214, 219));
        bar1.setPreferredSize(new Dimension(1, 0));
        pnlTop.add(bar1, BorderLayout.EAST);

        bar2 = new JPanel();
        bar2.setBackground(new Color(204, 214, 219));
        bar2.setPreferredSize(new Dimension(0, 1));
        pnlTop.add(bar2, BorderLayout.SOUTH);

        pnlCenter = new JPanel();
        pnlCenter.setPreferredSize(new Dimension(230, 600));
        pnlCenter.setBackground(DefaultColor);
        pnlCenter.setLayout(new FlowLayout(0, 0, 5));

        bar3 = new JPanel();
        bar3.setBackground(new Color(204, 214, 219));
        bar3.setPreferredSize(new Dimension(1, 1));
        this.add(bar3, BorderLayout.EAST);

        scrollPane = new JScrollPane(pnlCenter, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(5, 10, 0, 10));
        this.add(scrollPane, BorderLayout.CENTER);

        pnlBottom = new JPanel();
        pnlBottom.setPreferredSize(new Dimension(250, 50));
        pnlBottom.setBackground(DefaultColor);
        pnlBottom.setLayout(new BorderLayout(0, 0));

        bar4 = new JPanel();
        bar4.setBackground(new Color(204, 214, 219));
        bar4.setPreferredSize(new Dimension(1, 1));
        pnlBottom.add(bar4, BorderLayout.EAST);

        this.add(pnlBottom, BorderLayout.SOUTH);

        for (int i = 0; i < getSt.length; i++) {
            if (i + 1 == getSt.length) {
                listitem[i] = new itemTaskbar(getSt[i][1], getSt[i][0]);
                pnlBottom.add(listitem[i]);
            } else {
                listitem[i] = new itemTaskbar(getSt[i][1], getSt[i][0]);
                pnlCenter.add(listitem[i]);
                if (i != 0) {
                    if (!checkRole(getSt[i][2])) {
                        listitem[i].setVisible(false);
                    }
                }
            }
        }

        listitem[0].setBackground(HowerBackgroundColor);
        listitem[0].setForeground(HowerFontColor);
        listitem[0].isSelected = true;

        for (int i = 0; i < getSt.length; i++) {
            listitem[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    pnlMenuTaskbarMousePress(evt);
                }
            });
        }

        listitem[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                homePage = new HomePage();
                main.setPanel(homePage);
            }
        });

        listitem[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                pdPanel = new ProductPanel(main);
                main.setPanel(pdPanel);
            }
        });

        listitem[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                pptPanel = new PropertyPanel(main);
                main.setPanel(pptPanel);
            }
        });

        listitem[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                ipPanel = new ImportPanel(main, epDTO);
                main.setPanel(ipPanel);
            }
        });

        listitem[4].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                ivPanel = new InvoicePanel(main, epDTO);
                main.setPanel(ivPanel);
            }
        });

        listitem[5].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                ctmPanel = new CustomerPanel(main);
                main.setPanel(ctmPanel);
            }
        });

        listitem[6].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                spPanel = new SupplierPanel(main);
                main.setPanel(spPanel);
            }
        });

        listitem[7].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                epPanel = new EmployeePanel(main);
                main.setPanel(epPanel);
            }
        });

        listitem[8].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                pmsPanel = new PermissionPanel(main);
                main.setPanel(pmsPanel);
            }
        });

        listitem[9].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                statistic = new Statistic();
                main.setPanel(statistic);
            }
        });

        listitem[10].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn đăng xuất?", "Đăng xuất",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == 0) {
                    LogIn login = new LogIn();
                    main.dispose();
                    login.setVisible(true);
                }
            }
        });
    }

    public EmployeeDTO getEmployee() {
        return epDTO;
    }

    public boolean checkRole(String s) {
        boolean check = false;
        for (int i = 0; i < pmsdtList.size(); i++) {
            if (pmsdtList.get(i).getAction().equals("view")) {
                if (s.equals(pmsdtList.get(i).getFunctionID())) {
                    check = true;
                    return check;
                }
            }
        }
        return check;
    }

    public void pnlMenuTaskbarMousePress(MouseEvent evt) {
        for (int i = 0; i < getSt.length; i++) {
            if (evt.getSource() == listitem[i]) {
                listitem[i].isSelected = true;
                listitem[i].setBackground(HowerBackgroundColor);
                listitem[i].setForeground(HowerFontColor);
            } else {
                listitem[i].isSelected = false;
                listitem[i].setBackground(DefaultColor);
                listitem[i].setForeground(FontColor);
            }
        }
    }

    public void resetChange() {
        epDTO = epBUS.getEmployeeByID(epDTO.getEmployeeID());
    }

    public void information(JPanel info) {
        JPanel pnlIcon = new JPanel(new FlowLayout());
        pnlIcon.setPreferredSize(new Dimension(60, 0));
        pnlIcon.setOpaque(false);
        info.add(pnlIcon, BorderLayout.WEST);
        JLabel lblIcon = new JLabel();
        lblIcon.setPreferredSize(new Dimension(50, 70));
        if (epDTO.getGender().equals("Nam")) {
            lblIcon.setIcon(new FlatSVGIcon("./icon/man_50px.svg"));
        } else {
            lblIcon.setIcon(new FlatSVGIcon("./icon/women_50px.svg"));
        }
        pnlIcon.add(lblIcon);

        JPanel pnlInfo = new JPanel();
        pnlInfo.setOpaque(false);
        pnlInfo.setLayout(new FlowLayout(0, 10, 5));
        pnlInfo.setBorder(new EmptyBorder(15, 0, 0, 0));
        info.add(pnlInfo, BorderLayout.CENTER);

        lblEmail = new JLabel(epDTO.getEmployeeName());
        lblEmail.putClientProperty("FlatLaf.style", "font: 150% $semibold.font");
        pnlInfo.add(lblEmail);

        lblPermissionName = new JLabel(pmsDTO.getPermissionName());
        lblPermissionName.putClientProperty("FlatLaf.style", "font: 120% $light.font");
        lblPermissionName.setForeground(Color.GRAY);
        pnlInfo.add(lblPermissionName);

        lblIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                EmployeeBUS epBUS = new EmployeeBUS();
                new EmployeeDialog(epBUS, owner, true, "Thông tin tài khoản", "update", epDTO);
            }
        });
    }
}
