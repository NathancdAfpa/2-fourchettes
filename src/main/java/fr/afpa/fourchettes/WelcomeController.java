package fr.afpa.fourchettes;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class WelcomeController {

    @FXML
    private Label accueil;

    @FXML
    private Button profile;

    @FXML
    private Button btEntree;

    @FXML
    private Button deco;

    @FXML
    private Button btnDessert;

    @FXML
    private Button btnPlat;

    @FXML
    private Label dessert;

    @FXML
    private Label entree;

    @FXML
    private Button comment;

    @FXML
    private ImageView im1;

    @FXML
    private ImageView im2;

    @FXML
    private ImageView im3;

    @FXML
    private ImageView logo;

    @FXML
    private ImageView rest;

    @FXML
    private Label plats;

    @FXML
    private Rectangle rect;

    @FXML
    private Button plus;

    private Utilisateur utilisateur;

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        Session session = Session.getInstance(utilisateur); // Crée une instance de session avec l'utilisateur
        session.setUser(utilisateur); // Définit l'utilisateur dans la session
        System.out.println(utilisateur.getFirstName());
    }
    

    @FXML
    void addDessert(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnDessert.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dessert_view.fxml"));
        Parent root = loader.load();
        DessertController user = loader.getController();
        stage.getScene().setRoot(root);
    }

    @FXML
    void addRecette(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnDessert.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recette_view.fxml"));
        Parent root = loader.load();
        EditionRecetteController user = loader.getController();
        stage.getScene().setRoot(root);
    }

    @FXML
    void addEntree(ActionEvent event) throws IOException {
        Stage stage = (Stage) btEntree.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("entree_view.fxml"));
        Parent root = loader.load();
        EntreeController user = loader.getController();
        stage.getScene().setRoot(root);
    }

    @FXML
    void addPlat(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnPlat.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("plat_view.fxml"));
        Parent root = loader.load();
        PlatController user = loader.getController();
        stage.getScene().setRoot(root);
    }

    public void checkUser(String string, String string2, String string3, String string4, String string5) {
    }

    public void setId(String text) {
    }

    public void setPassword(String text) {
    }

    public void setIdentifiant(String text) {
    }

    @FXML
    void addDeco(ActionEvent event) throws IOException {
        Session.getInstance().resetUser();

        Stage stage = (Stage) deco.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("log_view.fxml"));
        Parent root = loader.load();
        LogController user = loader.getController();
        stage.getScene().setRoot(root);
    }

    @FXML
    void viewComment(ActionEvent event) throws IOException {
        Stage stage = (Stage) btEntree.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("comment_view.fxml"));
        Parent root = loader.load();
        CommentController user = loader.getController();
        stage.getScene().setRoot(root);
    }

    @FXML
    void profilePage(ActionEvent event) throws IOException {
        Stage stage = (Stage) btEntree.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile_view.fxml"));
        Parent root = loader.load();
        ProfileController user = loader.getController();
        stage.getScene().setRoot(root);
    }

    public ImageView getProfilePhoto() {
        return null;
    }

}
