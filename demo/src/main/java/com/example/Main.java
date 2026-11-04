package com.example;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VueCalculette fenetre = new VueCalculette();
            fenetre.ajouterEcouteur(e -> System.out.println(e.getActionCommand()));
            fenetre.setVisible(true);
        });
    }
}