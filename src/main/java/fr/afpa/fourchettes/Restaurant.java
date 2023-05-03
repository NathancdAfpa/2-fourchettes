package fr.afpa.fourchettes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Restaurant {
    
    private int id;
    private StringProperty name;
    private String adresse;
    private String ville;
    private String numeroDeTelephone;
    private String email;
    
    public Restaurant(int id, String name, String adresse, String ville, String numeroDeTelephone, String email) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.adresse = adresse;
        this.ville = ville;
        this.numeroDeTelephone = numeroDeTelephone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StringProperty NameProperty() {
        return name;
    }
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getNumeroDeTelephone() {
        return numeroDeTelephone;
    }

    public void setNumeroDeTelephone(String numeroDeTelephone) {
        this.numeroDeTelephone = numeroDeTelephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return  name.get();
    }
    
}
