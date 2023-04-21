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
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private Label mail;

    @FXML
    private Label mdp;

    @FXML
    private Label nom;

    @FXML
    private Label prenom;

    @FXML
    private Button retour;

    @FXML
    private Button signin;

    @FXML
    private Label title;

    @FXML
    private TextField tmail;

    @FXML
    private TextField tmdp;

    @FXML
    private TextField tnom;

    @FXML
    private TextField tprenom;

    private ObservableList<Utilisateur> utilisateur = FXCollections.observableArrayList();

    @FXML
    void addretour(ActionEvent event) throws IOException {
        Stage stage = (Stage) retour.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("log_view.fxml"));
        Parent root = loader.load();
        LogController user = loader.getController();
        stage.getScene().setRoot(root);
    }

    @FXML
    void register(ActionEvent event) throws IOException {
        Utilisateur newUtilisateur = new Utilisateur(0, tnom.getText(), 0, tprenom.getText(), tmail.getText(), tmdp.getText());
        DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
        daoUtilisateur.insert(newUtilisateur);
        utilisateur.add(newUtilisateur);

        tnom.clear();
        tprenom.clear();
        tmail.clear();
        tmdp.clear();

        Stage stage = (Stage) signin.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("log_view.fxml"));
        Parent root = loader.load();
        LogController user = loader.getController();
        stage.getScene().setRoot(root);
    }

    @FXML
    public void initialize() {
    DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
    utilisateur = FXCollections.observableArrayList(daoUtilisateur.findAll());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur !");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
