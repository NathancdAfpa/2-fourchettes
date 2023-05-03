package fr.afpa.fourchettes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ingredient {

    private int id;
    private StringProperty nom;
    private int quantite;
    private int idRecette;
    private StringProperty unite;
    
    public Ingredient(int id, String nom, int quantite, int idRecette, String unite) {
        this.id = id;
        this.nom = new SimpleStringProperty(nom);
        this.quantite = quantite;
        this.unite = new SimpleStringProperty(unite);
    }

    public Ingredient(String nom, int quantite, int idRecette, String unite) {
        this.nom = new SimpleStringProperty(nom);
        this.quantite = quantite;
        this.unite = new SimpleStringProperty(unite);
    }

    public Ingredient() {
        this.nom = new SimpleStringProperty();
        this.unite = new SimpleStringProperty();
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getIdRecette() {
        return idRecette;
    }

    public void setIdRecette(int idRecette) {
        this.idRecette = idRecette;
    }

    public StringProperty getNomProperty() {
        return nom;
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty getuniteProperty() {
        return unite;
    }

    public String getUnite() {
        return unite.get();
    }

    public void setUnite(String unite) {
        this.unite.set(unite);
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
    
}
