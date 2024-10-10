/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ASUS
 */
public class CustomerDTO {
    private String customerID, customerName, address, phone;

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public CustomerDTO() {
    }

    public CustomerDTO(String customerID, String customerName, String address, String phone) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
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
        final CustomerDTO other = (CustomerDTO) obj;
        if (!this.customerID.equals(other.customerID)) {
            return false;
        }
        if (!this.customerName.equals(other.customerName)) {
            return false;
        }
        if (!this.address.equals(other.address)) {
            return false;
        }
        return this.phone.equals(other.phone);
    }

    @Override
    public String toString() {
        return "CustomerDTO{" + "customerID=" + customerID + ", customerName=" + customerName + ", address=" + address
                + ", phone=" + phone + '}';
    }
}
