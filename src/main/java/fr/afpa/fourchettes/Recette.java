package fr.afpa.fourchettes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Recette {

    private int id;
    private int restoId;
    private int prepareTime;
    private int nombreCouverts;
    private StringProperty name;
    private StringProperty typeRecette;
    private boolean vegeOuPas;
    private StringProperty preparation;
    private Double moyenne;
    private Utilisateur createur;

    public Recette(int id, int restoId, int prepareTime, int nombreCouverts, String name,
            String typeRecette, boolean vegeOuPas, String preparation,
            Double moyenne, Utilisateur createur) {
        this.id = id;
        this.restoId = restoId;
        this.prepareTime = prepareTime;
        this.nombreCouverts = nombreCouverts;
        this.typeRecette = new SimpleStringProperty(typeRecette);
        this.name = new SimpleStringProperty(name);
        this.vegeOuPas = vegeOuPas;
        this.preparation = new SimpleStringProperty(preparation);
        this.moyenne = moyenne;
        this.createur = createur;

    }

    public Recette(int restoId, int prepareTime, int nombreCouverts, String name,
            String typeRecette, boolean vegeOuPas, String preparation, 
            Double moyenne, Utilisateur createur) {
        this.restoId = restoId;
        this.prepareTime = prepareTime;
        this.nombreCouverts = nombreCouverts;
        this.typeRecette = new SimpleStringProperty(typeRecette);
        this.name = new SimpleStringProperty(name);
        this.vegeOuPas = vegeOuPas;
        this.preparation = new SimpleStringProperty(preparation);
        this.moyenne = moyenne;
        this.createur = createur;
    }



    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRestoId() {
        return restoId;
    }

    public void setRestoId(int restoId) {
        this.restoId = restoId;
    }

    public int getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(int prepareTime) {
        this.prepareTime = prepareTime;
    }

    public int getNombreCouverts() {
        return nombreCouverts;
    }

    public void setNombreCouverts(int nombreCouverts) {
        this.nombreCouverts = nombreCouverts;
    }

    public StringProperty typeProperty() {
        return typeRecette;
    }

    public String getTypeRecette() {
        return typeRecette.get();
    }

    public void setTypeRecette(String typeRecette) {
        this.typeRecette.set(typeRecette);
    }

    public StringProperty preparationProperty() {
        return preparation;
    }

    public String getPreparation() {
        return preparation.get();
    }

    public void setPreparation(String preparation) {
        this.preparation.set(preparation);
    }

    public Utilisateur getCreateur() {
        return createur;
    }

    public Boolean getVegeOuPas() {
        return vegeOuPas;
        // ?
        // "Végétarien" : "Non végétarien";
    }

    public void setVegeOuPas(Boolean vegeOuPas) {
        this.vegeOuPas = vegeOuPas;

    }

    public Double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(Double moyenne) {
        this.moyenne = moyenne;
    }

    public void setCreateur(Utilisateur createur) {
        this.createur = createur;
    }

    

    // @Override
    // public String toString() {
    // return name + prepareTime + typeRecette;
    // }

}
