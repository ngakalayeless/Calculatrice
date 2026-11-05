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
                }
                
                else {
                    switch (touche) {

                        case "clear":
                            saisie.setLength(0); // Effacer la saisie
                            break;


                        case ".":
                            if (saisie.indexOf(".") == -1){
                                if (saisie.length() == 0) {
                                    saisie.append("0.");
                                } else {
                                    saisie.append(".");
                                }break;
                            }
                        case "push":
                            if (saisie.length() > 0) {
                                double valeur = Double.parseDouble(saisie.toString()); //Convertit le texte en décimal
                                calculatrice.entrer(valeur); //empile la valeur dans la pile
                                saisie.setLength(0); // Effacer la saisie après l'entrée
                            }
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


                fenetre.rafraichir(calculatrice.contenuPile(), saisie.toString());
            });


        };
    }

