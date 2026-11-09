package com.example;

import javax.swing.SwingUtilities;
import javax.swing.UIManager; 

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                System.err.println("Nimbus indisponible, style par défaut utilisé : " + e);
            }

            VueCalculette fenetre = new VueCalculette();

            Calculatrice calculatrice = new Calculatrice();

            new Controleur(calculatrice, fenetre);

            fenetre.setVisible(true);

        });
    }
}