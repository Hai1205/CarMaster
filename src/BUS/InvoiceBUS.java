package BUS;

import java.util.ArrayList;
import DAO.InvoiceDAO;
import DAO.InvoiceDetailDAO;
import DTO.InvoiceDTO;
import DTO.InvoiceDetailDTO;

/**
 *
 * @author robot
 */
public class InvoiceBUS {

    public InvoiceBUS() {
    }

    public ArrayList<InvoiceDTO> getList() {
        return InvoiceDAO.getList();
    }

    public void insert(InvoiceDTO ivDTO, ArrayList<InvoiceDetailDTO> ivdList) {
        InvoiceDAO.insert(ivDTO);

        InvoiceDetailDAO.insert(ivdList);
    }

    public ArrayList<InvoiceDetailDTO> getListDetailByID(String invcoiceID) {
        return InvoiceDetailDAO.getListByID(invcoiceID);
    }
}
