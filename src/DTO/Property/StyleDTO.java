/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.Property;

/**
 *
 * @author ASUS
 */
public class StyleDTO {
    private String styleID, styleName;

    public String getStyleID() {
        return styleID;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleID(String styleID) {
        this.styleID = styleID;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public StyleDTO(String styleID, String styleName) {
        this.styleID = styleID;
        this.styleName = styleName;
    }
    
}
