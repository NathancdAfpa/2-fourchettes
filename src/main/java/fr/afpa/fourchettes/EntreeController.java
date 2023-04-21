package fr.afpa.fourchettes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class EntreeController {

    @FXML
    private TextArea area;

    @FXML
    private TableColumn<Recette, String> colTv;

    @FXML
    private Label lab1;

    @FXML
    private Label lab2;

    @FXML
    private Label lab3;

    @FXML
    private Label lab4;

    @FXML
    private Label lab5;

    @FXML
    private Slider slider;

    @FXML
    private Label note;

    @FXML
    private TextArea comment;

    @FXML
    private Label lab6;

    @FXML
    private Label lab7;

    @FXML
    private Button delete;

    @FXML
    private Button add;

    @FXML
    private ImageView logo;

    @FXML
    private Label recetteLabel;

    @FXML
    private Button retour;

    @FXML
    private Rectangle rect;

    @FXML
    private ImageView im;

    @FXML
    private Button see;

    @FXML
    private Label title;

    private Recette recette;

    private Ingredient ingredient;

    @FXML
    private TableView<Recette> tv;

    private ObservableList<Recette> recettes = FXCollections.observableArrayList();

    @FXML
    void addretour(ActionEvent event) throws IOException {
        Stage stage = (Stage) retour.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome_view.fxml"));
        Parent root = loader.load();
        WelcomeController user = loader.getController();
        stage.getScene().setRoot(root);

    }

    public void ajouterRecette(Recette recette) {
        recettes.add(recette);
    }

    public void initialize() {
        colTv.setCellValueFactory(nom -> new SimpleStringProperty(nom.getValue().getName()));
        DAORecette daoRecette = new DAORecette();
        // Récupération de tous les noms de recettes depuis la base de données
        List<Recette> nomsRecettes = daoRecette.findAllEntree();
        recettes.addAll(nomsRecettes);
        // Mise à jour de la TableView avec les recettes créées
        tv.setItems(recettes);
    }

    @FXML
    void addDelete(ActionEvent event) {
        DAORecette daoRecette = new DAORecette();
        DAOIngredient daoIngredient = new DAOIngredient();
        Recette selectedRecette = tv.getSelectionModel().getSelectedItem();

        if (selectedRecette != null) {
            int recetteId = selectedRecette.getId();
            daoIngredient.deleteByRecetteId(recetteId); // supprimer tous les ingrédients associés à la recette
            daoRecette.delete(recetteId); // supprimer la recette elle-même
            tv.getItems().remove(selectedRecette);
        }
    }

    @FXML
    void seeRecette(ActionEvent event) {
        Recette selectedRecette = tv.getSelectionModel().getSelectedItem();
        if (selectedRecette != null) {

            String name = selectedRecette.getName();
            String type = selectedRecette.getTypeRecette();
            int nombreCouverts = selectedRecette.getNombreCouverts();
            int preparetime = selectedRecette.getPrepareTime();
            Boolean vege = selectedRecette.getVegeOuPas();
            String preparation = selectedRecette.getPreparation();

            DAOIngredient daoIngredient = new DAOIngredient();

            ArrayList<Ingredient> infoIngredients = daoIngredient.findByRecette(selectedRecette);

            String ingredientsText = "";

            for (Ingredient ingredient : infoIngredients) {
                ingredientsText += ingredient.getNom() + " : " + ingredient.getQuantite() + " " + ingredient.getUnite()
                        + "\n";
            }

            area.setText("\nTitre de la recette : " + name + "\n\nType : " + type + "\n\nNombre de couverts : "
                    + nombreCouverts + "\n\nTemps de préparation : " + preparetime + "\n\nRecette végétarienne : "
                    + vege + "\n\n\n            Ingrédients et quantités\n\n"
                    + ingredientsText + "\n\nPréparation : " + preparation);

        }
    }

    @FXML
    void addajout(ActionEvent event) {
        Recette selectedRecette = tv.getSelectionModel().getSelectedItem();

        if (selectedRecette != null) {

            String texte = comment.getText();
            int note = (int) slider.getValue();
            System.out.println(note);
            int recetteId = selectedRecette.getId();
            int userId = Session.getInstance().user.getId();
            String userPrenom = Session.getInstance().user.getFirstName();
            String userNom = Session.getInstance().user.getLastName();

            // Save recipe data
            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                    "postgres",
                    "Nathan17340!")) {
                // Insert recipe data into the "recette" table
                String insertRecipeSql = "INSERT INTO commentaire (texte, id_recette, id_user, note) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(insertRecipeSql, Statement.RETURN_GENERATED_KEYS);

                stmt.setString(1, texte);
                stmt.setInt(2, recetteId);
                stmt.setInt(3, userId);
                stmt.setInt(4, note);
                stmt.executeUpdate();

                // Get the ID of the newly inserted recipe
                ResultSet rs = stmt.getGeneratedKeys();
                int recipeId = 0;
                if (rs.next()) {
                    recipeId = rs.getInt(1);
                }

                String commentaire = String.format(
                        "",
                        texte, userPrenom, userNom, note);
                if (comment.getText().isEmpty()) {
                    comment.setText(commentaire);
                } else {
                    comment.appendText("\n\n");
                    comment.appendText(commentaire);
                }

                area.clear();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
