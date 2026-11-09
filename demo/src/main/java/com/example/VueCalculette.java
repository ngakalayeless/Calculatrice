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
import java.awt.event.ActionListener;


public class VueCalculette extends JFrame {

    private JTextArea affichagePile;
    private JTextField champSaisie;
    private List<JButton> boutons = new ArrayList<>();

    public VueCalculette() {
        setTitle("Calculette");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Zone d'affichage (haut) : la pile et le nombre en cours de saisie
        affichagePile = new JTextArea(10, 20);
        affichagePile.setEditable(false);

        champSaisie = new JTextField();
        champSaisie.setEditable(false);
        champSaisie.setHorizontalAlignment(JTextField.RIGHT);

        JPanel panelAffichage = new JPanel(new BorderLayout());
        panelAffichage.add(affichagePile, BorderLayout.CENTER);
        panelAffichage.add(champSaisie, BorderLayout.SOUTH);
        add(panelAffichage, BorderLayout.NORTH);

        // Grille des boutons (centre)
        String[] libelles = {
            "pop", "swap", "+/-", "actions",
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "push", "+"
        };

        JPanel panelBoutons = new JPanel(new GridLayout(5, 4, 0, 0));

        for (String libelle : libelles) {
            if (libelle.equals("actions")) {
                // Les boutons "<" et "C" partagent une même case de la grille
                JPanel panelActions = new JPanel(new GridLayout(1, 2, 2, 0));

                JButton boutonRetour = new JButton("<");
                JButton boutonClear = new JButton("C");

                boutons.add(boutonRetour);
                boutons.add(boutonClear);

                panelActions.add(boutonRetour);
                panelActions.add(boutonClear);
                panelBoutons.add(panelActions);
            } else {
                JButton bouton = new JButton(libelle);
                boutons.add(bouton);
                panelBoutons.add(bouton);
            }
        }

        add(panelBoutons, BorderLayout.CENTER);
    }

    /**
     * Lance la vue seule, pour vérifier son apparence.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VueCalculette fenetre = new VueCalculette();
            fenetre.setVisible(true);
        });
    }

    /**
     * Enregistre l'écouteur prévenu à chaque clic sur un bouton.
     */
    public void ajouterEcouteur(ActionListener ecouteur) {
        for (JButton bouton : boutons) {
            bouton.addActionListener(ecouteur);
        }
    }

    /**
     * Met à jour l'affichage de la pile (10 dernières valeurs) et de la saisie.
     */
    public void rafraichir(double[] pile, String saisie){
        StringBuilder sb = new StringBuilder();

        int debut = Math.max(0, pile.length - 10);
        for (int i = debut; i < pile.length; i++) {
            sb.append(pile[i]).append("\n");
        }

        affichagePile.setText(sb.toString());
        champSaisie.setText(saisie);
    }

    /**
     * Active ou grise un bouton d'après son texte.
     */
    public void activerBouton(String libelle, boolean actif){
        for (JButton bouton : boutons) {
            if (bouton.getText().equals(libelle)) {
                bouton.setEnabled(actif);
                break;
            }
        }
    }
}
