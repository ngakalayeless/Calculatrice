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
        fenetre.activerBouton("+", false);
        fenetre.activerBouton("-",false );
        fenetre.activerBouton("*", false);
        fenetre.activerBouton("/", false);
        fenetre.activerBouton("+/-", false);
        fenetre.activerBouton("pop", false);
        // écouteurs d'événements pour les boutons de la vue
        fenetre.ajouterEcouteur(e -> {
            String touche = e.getActionCommand();

            if (touche.matches("[0-9]")) {
                    saisie.append(touche);
                }
                
                else {
                    switch (touche) {

                        case "clear":
                            calculatrice.clear();
                            break;


                        case ".":
                            if (saisie.indexOf(".") == -1){
                                if (saisie.length() == 0) {
                                    saisie.append("0.");
                                } else {
                                    saisie.append(".");
                                }
                                fenetre.activerBouton(".",false);
                            }
                            break;
                        case "push":
                            if (saisie.length() > 0) {
                                double valeur = Double.parseDouble(saisie.toString()); //Convertit le texte en décimal
                                calculatrice.entrer(valeur); //empile la valeur dans la pile
                                saisie.setLength(0); // Effacer la saisie après l'entrée
                                fenetre.activerBouton(".", true);
                            }
                            break;

                        case "pop":
                            calculatrice.supprimer();
                            break;

                        case "<":
                            if (saisie.length() > 0) {
                                if (saisie.charAt(saisie.length() - 1) == '.') {
                                    fenetre.activerBouton(".", true);
                                }
                                saisie.deleteCharAt(saisie.length() - 1);
                            }
                            break;

                        case "+/-":
                            calculatrice.opposite();
                            break;

                        case "+":
                            calculatrice.add();
                            break;
                        case "-":
                            calculatrice.subtract();
                            break;
                        case "*":
                            calculatrice.multiply();
                            break;
                        case "/":
                            calculatrice.divide();
                            break;
                    }
                }
                if (calculatrice.contenuPile().length > 0) {
                    fenetre.activerBouton("+/-", true);
                    fenetre.activerBouton("pop", true);
                } else {
                    fenetre.activerBouton("+/-", false);
                    fenetre.activerBouton("pop", false);
                }
                if (calculatrice.contenuPile().length >= 2) {
                    fenetre.activerBouton("+", true);
                    fenetre.activerBouton("-", true);
                    fenetre.activerBouton("*", true);
                    if (calculatrice.tete() != 0) {
                        fenetre.activerBouton("/", true);
                    } else {
                        fenetre.activerBouton("/", false);
                    }
                } else {
                    fenetre.activerBouton("+", false);
                    fenetre.activerBouton("-", false);
                    fenetre.activerBouton("*", false);
                    fenetre.activerBouton("/", false);
                }
                fenetre.rafraichir(calculatrice.contenuPile(), saisie.toString());
            });


        };
    }

