package com.example;

public class Controleur {
    // Attributs du contrôleur
    private Calculatrice calculatrice;
    private VueCalculette fenetre;
    private StringBuilder saisie = new StringBuilder();

    // Constructeur du contrôleur
    public Controleur(Calculatrice calculatrice, VueCalculette fenetre) {
        this.calculatrice = calculatrice;
        this.fenetre = fenetre;

        // écouteurs d'événements pour les boutons de la vue
        fenetre.ajouterEcouteur(e -> {
            String touche = e.getActionCommand();

            if (touche.matches("[0-9]")) {
                saisie.append(touche);
            } else {
                switch (touche) {

                    case "C":
                        calculatrice.clear();
                        saisie.setLength(0);
                        break;

                    case ".":
                        if (saisie.indexOf(".") == -1) {
                            if (saisie.length() == 0) {
                                saisie.append("0.");
                            } else {
                                saisie.append(".");
                            }
                        }
                        break;

                    case "push":
                        push();
                        break;

                    case "pop":
                        calculatrice.supprimer();
                        break;

                    case "swap":
                        calculatrice.swap();
                        break;

                    case "<":
                        if (saisie.length() > 0) {
                            saisie.deleteCharAt(saisie.length() - 1);
                            if (saisie.length() == 1 && saisie.charAt(0) == '-') {
                                saisie.setLength(0);
                            }
                        }
                        break;

                    case "+/-":
                        if (saisie.length() == 0) {
                            calculatrice.opposite();
                        } else if (saisie.charAt(0) == '-') {
                            saisie.deleteCharAt(0);
                        } else {
                            saisie.insert(0, '-');
                        }
                        break;

                    case "+":
                        push();
                        calculatrice.add();
                        break;
                    case "-":
                        push();
                        calculatrice.subtract();
                        break;
                    case "*":
                        push();
                        calculatrice.multiply();
                        break;
                    case "/":
                        push();
                        calculatrice.divide();
                        break;
                    
                }
            }

            mettreAJourBoutons();
            fenetre.rafraichir(calculatrice.contenuPile(), saisie.toString());
        });

        // État des boutons au démarrage (pile et saisie vides)
        mettreAJourBoutons();
    }

    // Empile la saisie en cours, si elle n'est pas vide
    private void push() {
        if (saisie.length() > 0) {
            double valeur = Double.parseDouble(saisie.toString());
            calculatrice.entrer(valeur);
            saisie.setLength(0);
        }
    }

    // Recalcule l'état de tous les boutons à partir de la pile et de la saisie.
    // Chaque bouton est réglé une seule fois : l'ordre des lignes n'a pas d'importance.
    private void mettreAJourBoutons() {
        int taillePile = calculatrice.contenuPile().length;
        boolean saisieEnCours = saisie.length() > 0;

        // Nombre de valeurs utilisables par un opérateur : la saisie compte pour une,
        // puisqu'elle est empilée automatiquement avant le calcul.
        int nbValeurs = taillePile;
        if (saisieEnCours) {
            nbValeurs++;}
        boolean assezDeValeurs = nbValeurs >= 2;

        // Le diviseur est le nombre saisi s'il y en a un, sinon le sommet de la pile.
        // Le sommet n'est lu que si la pile a au moins 2 éléments, donc jamais sur une pile vide.
        boolean diviseurNul = false;
        if (assezDeValeurs) {
            double diviseur = saisieEnCours ? Double.parseDouble(saisie.toString()) : calculatrice.tete();
            diviseurNul = diviseur == 0;
        }

        fenetre.activerBouton("+", assezDeValeurs);
        fenetre.activerBouton("-", assezDeValeurs);
        fenetre.activerBouton("*", assezDeValeurs);
        fenetre.activerBouton("/", assezDeValeurs && !diviseurNul);

        fenetre.activerBouton("pop", taillePile > 0);
        fenetre.activerBouton("+/-", taillePile > 0 || saisieEnCours);
        fenetre.activerBouton(".", saisie.indexOf(".") == -1);
        fenetre.activerBouton("swap", taillePile >= 2);

        // Active push uniquement lorsqu'un nombre est en cours de saisie
        fenetre.activerBouton("push", saisieEnCours);
    }
}
