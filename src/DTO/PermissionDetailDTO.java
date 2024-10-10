/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.Objects;

/**
 *
 * @author ASUS
 */
public class PermissionDetailDTO {
    private String permissionID;
    private String functionID;
    private String action;

    public void setPermissionID(String permissionID) {
        this.permissionID = permissionID;
    }

    public void setFunctionID(String functionID) {
        this.functionID = functionID;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPermissionID() {
        return permissionID;
    }

    public String getFunctionID() {
        return functionID;
    }

    public String getAction() {
        return action;
    }

    public PermissionDetailDTO(String permissionID, String functionID, String action) {
        this.permissionID = permissionID;
        this.functionID = functionID;
        this.action = action;
    }
    
        @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PermissionDetailDTO other = (PermissionDetailDTO) obj;
        if (!Objects.equals(this.permissionID, other.permissionID)) {
            return false;
        }
        if (!Objects.equals(this.functionID, other.functionID)) {
            return false;
        }
        return Objects.equals(this.action, other.action);
    }

    @Override
    public String toString() {
        return "PermissionDetailDTO{" + "permissionID=" + permissionID + ", functionID=" + functionID + ", functionID=" + action + '}';
    }
}
