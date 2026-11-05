package com.example;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Style Nimbus, à choisir avant de créer la fenêtre.
            // S'il est indisponible, Swing garde son style par défaut.
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                System.err.println("Nimbus indisponible, style par défaut utilisé : " + e);
            }

            VueCalculette fenetre = new VueCalculette();

            Calculatrice calculatrice = new Calculatrice();

            //Création du contrôleur pour gérer les interactions entre la vue et le modèle  
            new Controleur(calculatrice, fenetre);
            //fenetre.ajouterEcouteur(e -> System.out.println(e.getActionCommand()));
            
            //Affcihage de la fenêtre
            fenetre.setVisible(true);

        });
    }
}