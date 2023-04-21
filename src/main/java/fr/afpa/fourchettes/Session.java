package fr.afpa.fourchettes;

public final class Session {

    private static Session instance;
    public Utilisateur user;

    private Session(Utilisateur user) {
        this.user = user;
    }

    public static Session getInstance(Utilisateur user) {
        if (instance == null) {
            instance = new Session(user);
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
}