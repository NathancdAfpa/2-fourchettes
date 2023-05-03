package fr.afpa.fourchettes;

public final class Session {

    private static Session instance;
    public Utilisateur user;
    public Recette recette;
    public Commentaire commentaire;
    public Restaurant restaurant;

    private Session(Utilisateur user) {
        this.user = user;
    }

    private Session(Recette recette) {
        this.recette = recette;
    }

    private Session(Commentaire commentaire) {
        this.commentaire = commentaire;
    }

    private Session(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public static Session getInstance(Utilisateur user) {
        if (instance == null) {
            instance = new Session(user);
        }
        return instance;
    }

    public static Session getInstance(Recette recette) {
        if (instance == null) {
            instance = new Session(recette);
        }
        return instance;
    }

    public static Session getInstance(Commentaire commentaire) {
        if (instance == null) {
            instance = new Session(commentaire);
        }
        return instance;
    }

    public static Session getInstance(Restaurant restaurant) {
        if (instance == null) {
            instance = new Session(restaurant);
        }
        return instance;
    }

    public static Session getInstance() {
        if (instance == null) {
            System.err.println("Session non instanciée");
            return null;
        }
        return instance;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void resetUser() {
        user = null;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    public Recette getRecette() {
        return recette;
    }

}