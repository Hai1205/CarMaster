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
public class FunctionDTO {
    private String fuctionID;
    private String functionName;

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
        final FunctionDTO other = (FunctionDTO) obj;
        if (!Objects.equals(this.fuctionID, other.fuctionID)) {
            return false;
        }
        return Objects.equals(this.functionName, other.functionName);
    }

    @Override
    public String toString() {
        return "FunctionDTO{" + "fuctionID=" + fuctionID + ", functionName=" + functionName + '}';
    }
    
    public void setFuctionID(String fuctionID) {
        this.fuctionID = fuctionID;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFuctionID() {
        return fuctionID;
    }

    public String getFunctionName() {
        return functionName;
    }

    public FunctionDTO(String fuctionID, String functionName) {
        this.fuctionID = fuctionID;
        this.functionName = functionName;
    }
}
