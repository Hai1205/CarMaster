/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.ProductDAO;
import DAO.ProductDetailDAO;
import DTO.ProductDTO;
import DTO.ProductDetailDTO;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class ProductBUS {
    private ArrayList<ProductDTO> list = new ArrayList<>();

    public ProductBUS() {
        list = getProductList();
    }

    public ArrayList<ProductDTO> getProductList() {
        return ProductDAO.getList();
    }

    public void addProduct(ProductDTO pdDTO) {
        ProductDAO.insert(pdDTO);
    }

    public void updateProduct(ProductDTO pdDTO) {
        ProductDAO.update(pdDTO);
    }

    public void addProductDetail(ProductDetailDTO pddDTO) {
        ProductDetailDAO.insert(pddDTO);
    }

    public void updateProductDetail(ProductDetailDTO pddDTO) {
        ProductDetailDAO.update(pddDTO);
    }

    public ProductDetailDTO getProductDetailByID(String productID) {
        return ProductDetailDAO.getProductDetailByID(productID);
    }

    public ArrayList<ProductDTO> search(String text) {
        return ProductDAO.search(text);
    }

    public ProductDTO getProductByID(String productID) {
        return ProductDAO.getProductByID(productID);
    }

    public int getQuantity() {
        int n = 0;
        for (ProductDTO i : this.list) {
            if (i.getQuantity() != 0) {
                n += i.getQuantity();
            }
        }
        return n;
    }

    public String getSupplierNameByID(String supplierID) {
        SupplierBUS spBUS = new SupplierBUS();
        return spBUS.getNameByID(supplierID);
    }

    public ArrayList<ProductDTO> getListBySupplierID(String supplierID) {
        return ProductDAO.getListBySupplierID(supplierID);
    }
}
