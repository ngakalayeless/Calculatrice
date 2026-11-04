package com.example;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class VueCalculette extends JFrame {

    private JTextArea affichagePile;
    private JTextField champSaisie;
    private List<JButton> boutons = new ArrayList<>();

    public VueCalculette() {
        setTitle("Calculette");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Affichage (haut)
        affichagePile = new JTextArea(10, 20);
        affichagePile.setEditable(false);

        champSaisie = new JTextField();
        champSaisie.setEditable(false);
        champSaisie.setHorizontalAlignment(JTextField.RIGHT);

        JPanel panelAffichage = new JPanel(new BorderLayout());
        panelAffichage.add(affichagePile, BorderLayout.CENTER);
        panelAffichage.add(champSaisie, BorderLayout.SOUTH);

        add(panelAffichage, BorderLayout.NORTH);

        // Boutons (centre)
        String[] libelles = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "push", "+"
        };

        JPanel panelBoutons = new JPanel(new GridLayout(4, 4, 5, 5));
        for (String libelle : libelles) {
            JButton bouton = new JButton(libelle);
            boutons.add(bouton);
            panelBoutons.add(bouton);
        }

        add(panelBoutons, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VueCalculette fenetre = new VueCalculette();
            fenetre.setVisible(true);
        });
    }
}