/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import view.TripDetailView;

import dao.PaketPerjalananDAO;
import model.PaketPerjalanan;
import view.SearchResultView;
import java.util.List;


public class PaketController {
    
    private final PaketPerjalananDAO dao;

    public PaketController() {
        this.dao = new PaketPerjalananDAO();
    }
    
    public void searchPackages(String destination) {
        if (destination == null || destination.trim().isEmpty()) {
            // Sebaiknya ada pemberitahuan ke user
            System.out.println("Tujuan tidak boleh kosong.");
            return;
        }
        
        List<PaketPerjalanan> results = dao.searchAvailablePackages(destination);
        
        // Buka jendela hasil pencarian dengan data yang ditemukan
        new SearchResultView(destination, results).setVisible(true);
    }
    
    /**
     * Membuka jendela TripDetailView untuk paket yang dipilih.
     * @param paket Objek PaketPerjalanan yang akan ditampilkan detailnya.
     */
    public void showTripDetails(PaketPerjalanan paket) {
        new TripDetailView(paket).setVisible(true);
    }
}
