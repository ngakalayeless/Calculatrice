package com.example;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
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