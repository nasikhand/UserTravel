/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.DestinasiDAO;
import java.util.List;
import model.Destinasi;

public class CustomTripController {
    
    private final DestinasiDAO destinasiDAO;
    
    public CustomTripController() {
        this.destinasiDAO = new DestinasiDAO();
    }
    
    public List<Destinasi> getAllDestinasi() {
        return destinasiDAO.getAllDestinasi();
    }
}
