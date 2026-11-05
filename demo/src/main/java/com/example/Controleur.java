package com.example;

public class Controleur {
    // Attributs du contrôleur
    private Calculatrice calculatrice;
    private VueCalculette fenetre;
    private StringBuilder saisie = new StringBuilder();
    //Méthode du controleur
    private void push() {
        if (saisie.length() > 0) {
            double valeur = Double.parseDouble(saisie.toString());
            calculatrice.entrer(valeur);
            saisie.setLength(0);
            fenetre.activerBouton(".", true);
        }
    }
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
                            push();
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
                if (calculatrice.contenuPile().length > 0) {
                    fenetre.activerBouton("+/-", true);
                    fenetre.activerBouton("pop", true);
                    if (saisie.length() >0) {
                        fenetre.activerBouton("+", true);
                        fenetre.activerBouton("-", true);
                        fenetre.activerBouton("*", true);
                        if (Double.parseDouble(saisie.toString())==0) {
                            fenetre.activerBouton("/", false);
                        } else {
                            fenetre.activerBouton("/", true);
                        }
                    }
                } else {
                    fenetre.activerBouton("+/-", false);
                    fenetre.activerBouton("pop", false);
                }
                fenetre.rafraichir(calculatrice.contenuPile(), saisie.toString());
            });


        };
    }

