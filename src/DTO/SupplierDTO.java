/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ASUS
 */
public class SupplierDTO {
    private String supplierID, supplierName, address, email, phone;

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public SupplierDTO(String supplierID, String supplierName, String address, String email, String phone) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.address = address;
        this.email = email;
        this.phone = phone;
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
        final SupplierDTO other = (SupplierDTO) obj;
        if (!this.supplierID.equals(other.supplierID)) {
            return false;
        }
        if (!this.supplierName.equals(other.supplierName)) {
            return false;
        }
        if (!this.address.equals(other.address)) {
            return false;
        }
        if (!this.email.equals(other.email)) {
            return false;
        }
        return this.phone.equals(other.phone);
    }

    @Override
    public String toString() {
        return "SupplierDTO{" + "supplierID=" + supplierID + ", supplierName=" + supplierName + ", address=" + address + ", email=" + email + ", phone=" + phone + '}';
    }
}
