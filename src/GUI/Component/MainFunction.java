package GUI.Component;

import BUS.PermissionBUS;
import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

public final class MainFunction extends JToolBar {
    public ButtonToolBar btnAdd, btnDelete, btnEdit, btnDetail, btnNhapExcel, btnXuatExcel, btnHuyPhieu;
    public JSeparator separator1;
    public HashMap<String, ButtonToolBar> btn = new HashMap<>();
    private final PermissionBUS pmsBus = new PermissionBUS();

    public MainFunction(String permissionID, String functionID, String[] listBtn) {
        initData();
        initComponent(permissionID, functionID, listBtn);
    }

    public void initData() {
        btn.put("create", new ButtonToolBar("THÊM", "add.svg", "create"));
        btn.put("detail", new ButtonToolBar("CHI TIẾT", "detail.svg", "update"));
        btn.put("update", new ButtonToolBar("CẬP NHẬT", "edit.svg", "update"));
        // btn.put("cancel", new ButtonToolBar("HUỶ PHIẾU", "cancel.svg", "delete"));
        btn.put("import", new ButtonToolBar("NHẬP EXCEL", "import_excel.svg", "create"));
        btn.put("export", new ButtonToolBar("XUẤT EXCEL", "export_excel.svg", "view"));
    }

    private void initComponent(String permissionID, String functionID, String[] listBtn) {
        this.setBackground(Color.WHITE);
        this.setRollover(true);
        for (String btnn : listBtn) {
            this.add(btn.get(btnn));
            if (!pmsBus.checkPermisson(permissionID, functionID, btn.get(btnn).getPermisson())) {
                btn.get(btnn).setEnabled(false);
            }
        }
    }
}
