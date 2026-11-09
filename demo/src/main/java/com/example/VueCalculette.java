// "package" indique le dossier logique de la classe : ce fichier est rangé dans com/example/.
// Les classes d'un même package se voient sans import (Main utilise VueCalculette directement).
package com.example;

// Les "import" évitent d'écrire le nom complet des classes (java.awt.GridLayout, etc.).
// Deux bibliothèques graphiques cohabitent :
//   - java.awt : la plus ancienne. On lui emprunte ici les gestionnaires de placement (layouts)
//     et le système d'événements.
//   - javax.swing : construite par-dessus AWT. Elle fournit les composants visibles,
//     reconnaissables à leur préfixe "J" (JButton, JFrame...).
import java.awt.BorderLayout;              // Placement par zones : nord, sud, est, ouest, centre
import java.awt.GridLayout;                // Placement en grille : lignes et colonnes de même taille
import java.util.ArrayList;                // Implémentation concrète d'une liste (tableau redimensionnable)
import java.util.List;                     // Interface générale d'une liste
import javax.swing.JButton;                // Bouton cliquable
import javax.swing.JFrame;                 // Fenêtre principale (barre de titre, boutons fermer/réduire)
import javax.swing.JPanel;                 // Conteneur invisible servant à regrouper des composants
import javax.swing.JTextArea;              // Zone de texte sur plusieurs lignes
import javax.swing.JTextField;             // Champ de texte sur une seule ligne
import javax.swing.SwingUtilities;         // Utilitaires Swing (ici : invokeLater)
import java.awt.event.ActionListener;      // Interface de l'objet prévenu lors d'un clic

/**
 * La VUE de la calculette : ce que l'utilisateur voit à l'écran.
 *
 * Elle ne fait aucun calcul et ne connaît pas la pile. Elle se contente de :
 *   1. construire la fenêtre (constructeur) ;
 *   2. signaler les clics à un autre objet (ajouterEcouteur) ;
 *   3. afficher les valeurs qu'on lui donne (rafraichir).
 *
 * "extends JFrame" signifie que VueCalculette hérite de JFrame : elle EST une fenêtre.
 * Elle récupère donc toutes ses méthodes, ce qui explique qu'on écrive setTitle(...)
 * ou add(...) plus bas sans préciser d'objet : on les appelle sur la fenêtre elle-même.
 *
 * Aperçu de la fenêtre :
 *
 *   +-------------------------------+
 *   | Calculette                [X] |
 *   +-------------------------------+  --+
 *   | affichagePile (la pile)       |    |  panelAffichage
 *   |-------------------------------|    |  (zone NORTH de la fenêtre)
 *   |                   champSaisie |    |
 *   +-------------------------------+  --+
 *   |  pop  |   <   |  +/-  | clear |    |
 *   |   7   |   8   |   9   |   /   |    |
 *   |   4   |   5   |   6   |   *   |    |  panelBoutons
 *   |   1   |   2   |   3   |   -   |    |  (zone CENTER de la fenêtre)
 *   |   0   |   .   | push  |   +   |    |
 *   +-------------------------------+  --+
 */
public class VueCalculette extends JFrame {

    // ATTRIBUTS : les données conservées par l'objet tant qu'il existe.
    // "private" : seule cette classe y a accès. Les autres classes doivent passer
    // par les méthodes publiques (rafraichir, ajouterEcouteur...). C'est l'encapsulation.
    //
    // Pourquoi des attributs et pas de simples variables dans le constructeur ?
    // Parce que d'autres méthodes en ont besoin après la fin du constructeur :
    // rafraichir() modifie les deux zones de texte, ajouterEcouteur() parcourt les boutons.

    private JTextArea affichagePile;   // Affiche le contenu de la pile, une valeur par ligne
    private JTextField champSaisie;    // Affiche le nombre en cours de saisie

    // Liste de tous les boutons, remplie dans le constructeur.
    // - List<JButton> : le <JButton> précise que la liste ne contient que des JButton (généricité).
    // - On déclare le type List (l'interface) et on crée un ArrayList (l'implémentation).
    // - new ArrayList<>() : les <> vides laissent Java déduire le type <JButton>.
    private List<JButton> boutons = new ArrayList<>();

    /**
     * CONSTRUCTEUR : exécuté à chaque "new VueCalculette()".
     * Il fabrique tous les composants et les assemble, mais n'affiche rien :
     * une fenêtre reste invisible tant qu'on n'a pas appelé setVisible(true).
     */
    public VueCalculette() {
        // --- Réglages de la fenêtre (méthodes héritées de JFrame) ---

        setTitle("Calculette");     // Texte de la barre de titre
        setSize(400, 500);          // Largeur puis hauteur, en pixels

        // Quitte le programme quand on clique sur la croix.
        // Sans cette ligne, la fenêtre serait seulement masquée et le programme continuerait de tourner.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centre la fenêtre sur l'écran. "null" veut dire : relatif à aucun autre composant.
        // Cet appel vient après setSize() car le centrage dépend de la taille de la fenêtre.
        setLocationRelativeTo(null);

        // Affichage (haut)

        // Zone de la pile : 10 lignes et 20 colonnes. Ces nombres donnent sa taille "préférée",
        // mesurée en caractères et non en pixels.
        affichagePile = new JTextArea(10, 20);
        // Empêche l'utilisateur d'écrire dedans : seul le programme modifie le texte.
        affichagePile.setEditable(false);

        // Champ de saisie : pas modifiable au clavier non plus, on saisit avec les boutons.
        champSaisie = new JTextField();
        champSaisie.setEditable(false);
        // Texte aligné à droite, comme sur une vraie calculatrice.
        champSaisie.setHorizontalAlignment(JTextField.RIGHT);

        // Un panneau pour regrouper les deux zones de texte et les traiter comme un seul bloc.
        // Le gestionnaire de placement (layout) décide de la position et de la taille des
        // composants : on ne donne pas de coordonnées, et tout s'adapte si la fenêtre change de taille.
        //
        // BorderLayout découpe l'espace en 5 zones :
        //
        //   +----------------------+
        //   |        NORTH         |   NORTH et SOUTH : hauteur préférée du composant
        //   +------+-------+-------+
        //   | WEST | CENTER| EAST  |   CENTER : récupère toute la place restante
        //   +------+-------+-------+
        //   |        SOUTH         |   Une zone sans composant n'occupe aucune place
        //   +----------------------+
        JPanel panelAffichage = new JPanel(new BorderLayout());
        panelAffichage.add(affichagePile, BorderLayout.CENTER);   // La pile prend la place disponible
        panelAffichage.add(champSaisie, BorderLayout.SOUTH);      // Le champ de saisie se place dessous

        // add() est appelé ici sur la fenêtre. Une JFrame utilise BorderLayout par défaut,
        // d'où l'emploi direct de BorderLayout.NORTH sans layout à configurer.
        // Le bloc d'affichage garde donc sa hauteur préférée, en haut de la fenêtre.
        add(panelAffichage, BorderLayout.NORTH);

        // Boutons (centre)

        // Le texte des 20 boutons, dans l'ordre où ils apparaîtront : la grille se remplit
        // de gauche à droite, puis de haut en bas. La disposition sur plusieurs lignes
        // ne sert qu'à la lisibilité : pour Java, c'est un tableau simple de 20 chaînes.
        String[] libelles = {
            "pop", "swap", "+/-", "actions",
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "push", "+"
        };

        // Grille de 5 lignes et 4 colonnes, sans espace entre les cases (les deux 0 :
        // écart horizontal et vertical en pixels). Toutes les cases ont la même taille.
        JPanel panelBoutons = new JPanel(new GridLayout(5, 4, 0, 0));

        // Boucle "for-each" : pour chaque chaîne du tableau, on crée le bouton correspondant.
        
        for (String libelle : libelles) {

            if (libelle.equals("actions")) {

                // Un panneau contenant deux boutons côte à côte
                JPanel panelActions = new JPanel(new GridLayout(1, 2, 2, 0));

                JButton boutonRetour = new JButton("<");
                JButton boutonClear = new JButton("C");

                // Enregistrer les boutons pour les événements
                boutons.add(boutonRetour);
                boutons.add(boutonClear);

                panelActions.add(boutonRetour);
                panelActions.add(boutonClear);

                // Le panneau occupe une seule cellule de la grille principale
                panelBoutons.add(panelActions);

            } else {

                JButton bouton = new JButton(libelle);

                boutons.add(bouton);
                panelBoutons.add(bouton);
            }
        }

        // La variable "bouton" disparaît à chaque tour de boucle, mais pas le bouton lui-même :
        // la liste et le panneau gardent chacun une référence vers le même objet.

        // Zone CENTER : les boutons occupent toute la place laissée par l'affichage,
        // et s'agrandissent avec la fenêtre.
        add(panelBoutons, BorderLayout.CENTER);
    }

    /**
     * Point d'entrée pour lancer la vue seule et vérifier son apparence.
     * Le programme complet démarre depuis Main.main(). Une classe peut avoir son propre main :
     * c'est la classe qu'on choisit de lancer qui détermine lequel s'exécute.
     */
    public static void main(String[] args) {
        // Swing n'est pas "thread-safe" : l'interface ne doit être manipulée que depuis un
        // fil d'exécution dédié, l'EDT (Event Dispatch Thread), qui traite aussi les clics.
        // invokeLater() lui confie le code à exécuter.
        //
        // "() -> { ... }" est une lambda : un bout de code passé en paramètre.
        // Elle ne prend aucun paramètre, d'où les parenthèses vides.
        SwingUtilities.invokeLater(() -> {
            VueCalculette fenetre = new VueCalculette();   // Construit la fenêtre, encore invisible
            fenetre.setVisible(true);                      // Et l'affiche
        });
    }

    /**
     * Enregistre l'objet à prévenir quand on clique sur un bouton (programmation événementielle :
     * le programme n'attend pas dans une boucle, il réagit quand un événement arrive).
     *
     * Fonctionnement d'un clic :
     *   1. l'utilisateur clique sur un bouton ;
     *   2. Swing appelle la méthode actionPerformed(ActionEvent e) de l'écouteur ;
     *   3. l'écouteur lit e.getActionCommand(), qui vaut par défaut le texte du bouton ("7", "push"...).
     *
     * ActionListener est une interface : elle impose la présence de la méthode actionPerformed.
     * Comme c'est sa seule méthode, on peut fournir une lambda. C'est ce que fait Main :
     *     fenetre.ajouterEcouteur(e -> System.out.println(e.getActionCommand()));
     *
     * Le même écouteur est donné à tous les boutons, et il les distingue grâce à leur texte.
     * Ainsi la vue ne contient aucune logique : elle ne sait pas ce que fait un clic.
     *
     * @param ecouteur l'objet à prévenir à chaque clic
     */
    public void ajouterEcouteur(ActionListener ecouteur) {
        for (JButton bouton : boutons) {          // D'où l'intérêt d'avoir gardé la liste des boutons
            bouton.addActionListener(ecouteur);
        }
    }

    /**
     * Met à jour l'écran avec les données reçues. La vue ne va pas les chercher elle-même :
     * c'est à l'appelant de les lui fournir.
     *
     * @param pile   les valeurs de la pile, affichées de haut en bas dans l'ordre du tableau
     * @param saisie le texte à placer dans le champ de saisie
     */
    public void rafraichir(double[] pile, String saisie){
        // StringBuilder assemble une chaîne morceau par morceau. C'est plus efficace que des
        // "+" répétés, car une String ne peut pas être modifiée : chaque "+" en créerait une nouvelle.
        
        StringBuilder sb = new StringBuilder();

        // Déterminer à partir de quel nombre afficher la pile
        int debut = Math.max(0, pile.length - 10);

        // Afficher uniquement les 10 derniers nombres
        for (int i = debut; i < pile.length; i++) {
            sb.append(pile[i]).append("\n");
        }

        // setText() remplace tout le texte existant : l'ancien contenu n'a pas besoin d'être effacé.
        // Le composant se redessine ensuite automatiquement.
        affichagePile.setText(sb.toString());
        champSaisie.setText(saisie);
    }

    /**
     * Prévue pour activer ou griser un bouton d'après son texte.
     * Pas encore implémentée : pour l'instant, l'appeler ne fait rien.
     *
     * @param libelle le texte du bouton concerné
     * @param actif   true pour l'activer, false pour le griser
     */
    public void activerBouton(String libelle, boolean actif){
        for (JButton bouton : boutons) {
            if (bouton.getText().equals(libelle)) {
                bouton.setEnabled(actif);
                break;  // On a trouvé le bouton, inutile de continuer la boucle
            }
        }
    }
}
