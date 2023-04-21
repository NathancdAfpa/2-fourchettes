package fr.afpa.fourchettes;

import java.sql.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Commentaire {
 
    private int id;
    private StringProperty text;
    private int note;
    private Date date;
    private int idRecette;
    private int idUser;

    public Commentaire(int id, String text, int note, Date date, int idRecette, int idUser) {
        this.id = id;
        this.text = new SimpleStringProperty(text);
        this.note = note;
        this.date = date;
        this.idRecette = idRecette;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

     public StringProperty getTextProperty() {
        return text;
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdRecette() {
        return idRecette;
    }

    public void setIdRecette(int idRecette) {
        this.idRecette = idRecette;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

   
}
