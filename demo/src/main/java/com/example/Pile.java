package com.example;

import java.util.Arrays;


public class Pile {
    private double[] elements = new double[10];
    private int taille = 0;

   public void empiler(double valeur) {
        if (taille == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }

        if (valeur == 0) {
            valeur = 0.0;
        }

        elements[taille] = valeur;
        taille++;
    }

    public double depiler() {
        if (estVide()) {
            throw new IllegalStateException("Pile vide");
        }
        taille--;
        return elements[taille];
    }

    public double sommet() {
        if (estVide()) {
            throw new IllegalStateException("Pile vide");
        }
        return elements[taille - 1];
    }

    public boolean estVide() {
        return taille == 0;
    }

    public int taille() {
        return taille;
    }

    public void vider() {
        taille = 0;
    }
    public double[] contenu() {
        return Arrays.copyOf(elements, taille);
    }
}

