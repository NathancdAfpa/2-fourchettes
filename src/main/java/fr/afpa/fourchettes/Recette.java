package fr.afpa.fourchettes;

import java.sql.Time;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Recette {

    private int id;
    private int restoId;
    private int prepareTime;
    private int nombreCouverts;
    private StringProperty name;
    private StringProperty typeRecette;
    private int iDcreateur;
    private boolean vegeOuPas;
    private StringProperty preparation;

    public Recette(int id, int restoId, int prepareTime, int nombreCouverts, String name,
            String typeRecette, int iDcreateur, boolean vegeOuPas, String preparation) {
        this.id = id;
        this.restoId = restoId;
        this.prepareTime = prepareTime;
        this.nombreCouverts = nombreCouverts;
        this.typeRecette = new SimpleStringProperty(typeRecette);
        this.name = new SimpleStringProperty(name);
        this.iDcreateur = iDcreateur;
        this.vegeOuPas = vegeOuPas;
        this.preparation = new SimpleStringProperty(preparation);

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

    public int getCreateur() {
        return iDcreateur;
    }

    public void setCreateur(int createur) {
        this.iDcreateur = iDcreateur;
    }

    public boolean getVegeOuPas() {
        return vegeOuPas;
    }

    public void setVegeOuPas(Boolean vegeOuPas) {
        this.vegeOuPas = vegeOuPas;
    }

    // @Override
    // public String toString() {
    // return name + prepareTime + typeRecette;
    // }

}
