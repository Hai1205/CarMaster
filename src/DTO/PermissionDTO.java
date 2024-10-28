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
public class PermissionDTO {

    private String permissionID, permissionName;
    private int slot, applied;

    public PermissionDTO() {
    }

    public void setPermissionID(String permissionID) {
        this.permissionID = permissionID;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setApplied(int applied) {
        this.applied = applied;
    }

    public String getPermissionID() {
        return permissionID;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public int getSlot() {
        return slot;
    }

    public int getApplied() {
        return applied;
    }

    public PermissionDTO(String permissionID, String permissionName, int slot, int applied) {
        this.permissionID = permissionID;
        this.permissionName = permissionName;
        this.slot = slot;
        this.applied = applied;
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
        final PermissionDTO other = (PermissionDTO) obj;
        if (!this.permissionID.equals(permissionID)) {
            return false;
        }
        return Objects.equals(this.permissionName, other.permissionName);
    }

    @Override
    public String toString() {
        return "NhomQuyenDTO{" + "permissionID=" + permissionID + ", permissionName=" + permissionName + '}';
    }
}
