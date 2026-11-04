package com.example;

public class Calculatrice {
    private final Pile pile = new Pile();

    public void entrer (double valeur) {
        pile.empiler(valeur);
    }
    public void swap() {
        if (pile.taille() < 2) {
            throw new IllegalStateException("Pas assez d'éléments pour swap");
        }
        double a = pile.depiler();
        double b = pile.depiler();
        pile.empiler(a);
        pile.empiler(b);
    }
    public void clear() {
        pile.vider();
    }
    public void supprimer() {
        if (pile.estVide()) {
            throw new IllegalStateException("Pile vide");
        }
        pile.depiler();
    }
    public void add() {
        if (pile.taille() < 2) {
            throw new IllegalStateException("Pas assez d'éléments pour addition");
        }
        double a = pile.depiler();
        double b = pile.depiler();
        pile.empiler(a + b);
    }
    public void subtract() {
        if (pile.taille() < 2) {
            throw new IllegalStateException("Pas assez d'éléments pour soustraction");
        }
        double a = pile.depiler();
        double b = pile.depiler();
        pile.empiler(b - a);
    }
    public void multiply() {
        if (pile.taille() < 2) {
            throw new IllegalStateException("Pas assez d'éléments pour multiplication");
        }
        double a = pile.depiler();
        double b = pile.depiler();
        pile.empiler(a * b);
    }
    public void divide() {
        if (pile.taille() < 2) {
            throw new IllegalStateException("Pas assez d'éléments pour division");
        }
        double a = pile.depiler();
        double b = pile.depiler();
        if (a == 0) {
            throw new ArithmeticException("Division par zéro");
        }
        pile.empiler(b / a);
    }
    public void opposite() {
        if (pile.estVide()) {
            throw new IllegalStateException("Pile vide");
        }
        double a = pile.depiler();
        pile.empiler(-a);
    }

}
