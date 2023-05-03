package fr.afpa.fourchettes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Utilisateur {
    
private int id;
private StringProperty identifiant;
private int restoId;
private StringProperty lastName;
private StringProperty firstName;
private StringProperty password;

public Utilisateur(int id, String identifiant, int restoId, String lastName, String firstName, String password) {

    this.id = id;
    this.identifiant = new SimpleStringProperty(identifiant);
    this.restoId = restoId;
    this.lastName = new SimpleStringProperty(lastName);
    this.firstName = new SimpleStringProperty(firstName);
    this.password = new SimpleStringProperty(password);

}


public Utilisateur() {
}

public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

public StringProperty identifiantProperty() {
    return identifiant;
}

public String getIdentifiant() {
    return identifiant.get();
}

public void setIdentifiant(String identifiant) {
    this.identifiant.set(identifiant);
}

public int getRestoId() {
    return restoId;
}

public void setRestoId(int restoId) {
    this.restoId = restoId;
}

public StringProperty lastNameProperty() {
    return lastName;
}

public String getLastName() {
    return lastName.get();
}

public void setLastName(String lastName) {
    this.lastName.set(lastName);
}

public StringProperty firstNameProperty() {
    return firstName;
}


public String getFirstName() {
    return firstName.get();
}

public void setFirstName(String firstName) {
    this.firstName.set(firstName);
}


public StringProperty passwordProperty() {
    return password;
}

public String getPassword() {
    return password.get();
}

public void setPassword(String password) {
    this.password.set(password);
}


public Utilisateur checkUser(String string, int int1, String string2, String string3, String string4) {
    return null;
}


public Utilisateur getCreateur() {
    return null;
}

}
