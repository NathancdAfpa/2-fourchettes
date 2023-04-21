package fr.afpa.fourchettes;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LogController {

    @FXML
    private Button btLog;

    @FXML
    private Button see;

    @FXML
    private Label id;

    @FXML
    private Label log;

    @FXML
    private ImageView logo;

    @FXML
    private Label mdp;

    @FXML
    private Rectangle rect;

    @FXML
    private Rectangle rect2;

    @FXML
    private Button register;

    @FXML
    private TextField tId;

    @FXML
    private TextField tMdp;

    @FXML
    private Label title;

    @FXML
    private Button profile;

    @FXML
    private String motdepasse = "";

    @FXML
    private String bc = "\b";

    private boolean mdpVisible = false;

    private Utilisateur utilisateur;

    private ObservableList<Utilisateur> users = FXCollections.observableArrayList();

    @FXML
    void onKeyTyped(KeyEvent key) {

        motdepasse = motdepasse + key.getCharacter();
        System.out.println(motdepasse);

    }

    @FXML
    void onKeyPressed(KeyEvent event) {

        for (int i = 0; i > 0; i--) {

            if (motdepasse.contains(bc)) {

                motdepasse.lastIndexOf(i);
                System.out.println("Backspace pressed");
            }

        }

    }

    @FXML
    void cansee(ActionEvent event) {

        motdepasse = motdepasse.substring(0, tMdp.getLength());

        mdpVisible = !mdpVisible;

        if (!mdpVisible) {
            tMdp.setText("*".repeat(tMdp.getText().length()));

        } else {
            System.out.println(tMdp.getText());

            tMdp.setText(motdepasse);
        }
        if (mdpVisible) {
            tMdp.setText(motdepasse);
        }
    }


/**
 * Méthode checkLogin qui permet de vérifier les informations de l'utilisateur en instanciant également le singleton (classe session).
 * 
 */
    private boolean checkLogin(String identifiant, String password) {
        DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
        Utilisateur utilisateur = daoUtilisateur.getUtilisateurById(identifiant);

        if (utilisateur != null && utilisateur.getPassword().equals(password)) {
            Session.getInstance(utilisateur);
            return true;
        } else {
            return false;
        }
    }

    @FXML
    void addLog(ActionEvent event) throws IOException {
        if (checkLogin(tId.getText(), tMdp.getText())) {
            Stage stage = (Stage) btLog.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome_view.fxml"));
            Parent root = loader.load();
            WelcomeController accueil = loader.getController();
            this.utilisateur = utilisateur;
            accueil.setIdentifiant(tId.getText());
            accueil.setPassword(tMdp.getText());
            accueil.setUtilisateur(users);
            stage.getScene().setRoot(root);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid login");
            alert.setContentText("The username or password you entered is incorrect.");
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        mdpVisible = false;
        tId.setText("jeandupont@live.fr");
        tMdp.setText("salut");

        tMdp.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!mdpVisible) {
                String asterisks = "";
                for (int i = 0; i < newValue.length(); i++) {
                    asterisks += "*";
                }
                tMdp.setText(asterisks);
            }
        });

    }

    @FXML
    void addregister(ActionEvent event) throws IOException {
        Stage stage = (Stage) register.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register_view.fxml"));
        Parent root = loader.load();
        RegisterController user = loader.getController();
        stage.getScene().setRoot(root);
    }


    @FXML
    void profilePage(ActionEvent event) {

    }
    
}
