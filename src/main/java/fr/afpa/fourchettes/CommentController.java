package fr.afpa.fourchettes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class CommentController {

    @FXML
    private TextArea area;

    @FXML
    private TableColumn<Recette, String> colname;

    @FXML
    private Button retour;
    
    @FXML
    private Button voir;

    @FXML
    private Label title;

    @FXML
    private TableView<Recette> tv;

    private ObservableList<Recette> recettes = FXCollections.observableArrayList();

    private Recette recette;
    
    @FXML
    void addretour(ActionEvent event) throws IOException {
        Stage stage = (Stage) retour.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome_view.fxml"));
        Parent root = loader.load();
        WelcomeController user = loader.getController();
        stage.getScene().setRoot(root);
    }
    
    @FXML
    void initialize() {
        colname.setCellValueFactory(nom -> new SimpleStringProperty(nom.getValue().getName()));
        DAORecette daoRecette = new DAORecette();
        List<Recette> nomsRecettes = daoRecette.findAll();
        recettes.addAll(nomsRecettes);
        tv.setItems(recettes);
    }

    
    @FXML
    void see(ActionEvent event) {
        String userPrenom = Session.getInstance().user.getFirstName();
        String userNom = Session.getInstance().user.getLastName();
        Recette selectedRecette = tv.getSelectionModel().getSelectedItem();
        if (selectedRecette != null) {
    
            DAOCommentaire daoCommentaire = new DAOCommentaire();
    
            ArrayList<Commentaire> infoCommentaires = daoCommentaire.findByRecette(selectedRecette);
    
            String CommentaireText = "";
    
            for (Commentaire commentaire : infoCommentaires) {
                CommentaireText += userPrenom + " " + userNom + " A mis la note de " + commentaire.getNote() + " et à commenté : " + commentaire.getText() + "\n\n";
            }
            area.setText(CommentaireText);
        }
    }

}
