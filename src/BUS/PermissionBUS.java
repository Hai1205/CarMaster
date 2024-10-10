/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.FunctionDAO;
import DAO.PermissionDAO;
import DAO.PermissionDetailDAO;
import DTO.FunctionDTO;
import DTO.PermissionDTO;
import DTO.PermissionDetailDTO;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class PermissionBUS {

    private final ArrayList<PermissionDTO> pmsList;

    public PermissionBUS() {
        this.pmsList = PermissionDAO.getList();
    }

    public ArrayList<PermissionDTO> getPmsList() {
        return PermissionDAO.getList();
    }

    public ArrayList<FunctionDTO> getFtList() {
        return FunctionDAO.getList();
    }

    public PermissionDTO getByIndex(int index) {
        return this.pmsList.get(index);
    }

    public PermissionDTO getByID(String permissionID) {
        return PermissionDAO.getByID(permissionID);
    }

    public String getNameByID(String permissionName) {
        return PermissionDAO.getNameByID(permissionName);
    }

    public String getIDByName(String permissionName) {
        return PermissionDAO.getIDByName(permissionName);
    }

    public ArrayList<PermissionDetailDTO> getPmsdtList(String permissionID) {
        return PermissionDetailDAO.getList(permissionID);
    }

    public void add(String permissionID, String permissionName, ArrayList<PermissionDetailDTO> pmsdtList) {
        PermissionDAO.insert(new PermissionDTO(permissionID, permissionName));

        PermissionDetailDAO.insert(pmsdtList);
    }

    public void update(PermissionDTO pmsDTO, ArrayList<PermissionDetailDTO> pmsdtList) {
        PermissionDAO.update(pmsDTO);

        PermissionDetailDAO.delete(pmsDTO.getPermissionID());
        PermissionDetailDAO.insert(pmsdtList);
    }

    public boolean checkPermisson(String permissionID, String functionID, String action) {
        ArrayList<PermissionDetailDTO> pmsdtList = getPmsdtList(permissionID);
        boolean check = false;
        int i = 0;
        while (i < pmsdtList.size() && check == false) {
            if (pmsdtList.get(i).getFunctionID().equals(functionID) && pmsdtList.get(i).getAction().equals(action)) {
                check = true;
            } else {
                i++;
            }
        }
        return check;
    }

    public ArrayList<PermissionDTO> search(String text) {
        return PermissionDAO.search(text);
    }

    public String[] getPermission() {
        String[] list = new String[pmsList.size()];
        for (int i = 0; i < pmsList.size(); i++) {
            list[i] = pmsList.get(i).getPermissionName();
        }
        return list;
    }
}
