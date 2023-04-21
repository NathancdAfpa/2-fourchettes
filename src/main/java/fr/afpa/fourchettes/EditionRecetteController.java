package fr.afpa.fourchettes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class EditionRecetteController {

    @FXML
    private TextField TnbrCouverts;

    @FXML
    private TextArea area;

    @FXML
    private TextArea preparation;

    @FXML
    private Label createur;

    @FXML
    private TableView<Ingredient> tvIng;

    @FXML
    private Button plus;

    @FXML
    private ImageView logo;

    @FXML
    private Label nbCouverts;

    @FXML
    private RadioButton no;

    @FXML
    private TableColumn<Ingredient, String> colname;

    @FXML
    private TableColumn<Ingredient, String> colquantity;

    @FXML
    private TableColumn<Ingredient, String> colUnit;

    @FXML
    private Label nom;

    @FXML
    private Label recette;

    @FXML
    private Label recetteTtile;

    @FXML
    private Rectangle rect;

    @FXML
    private Button retour;

    @FXML
    private TextField tNom;

    @FXML
    private TextField tTime;

    @FXML
    private CheckBox checkDessert;

    @FXML
    private CheckBox checkEntree;

    @FXML
    private CheckBox checkPlat;

    @FXML
    private Label time;

    @FXML
    private Label title;

    @FXML
    private Button ajout;

    @FXML
    private Label vege;

    @FXML
    private RadioButton yes;

    private ObservableList<Ingredient> ingredient = FXCollections.observableArrayList();

    @FXML
    void addretour(ActionEvent event) throws IOException {
        Stage stage = (Stage) retour.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome_view.fxml"));
        Parent root = loader.load();
        WelcomeController user = loader.getController();
        stage.getScene().setRoot(root);
    }

    @FXML
    void addAjout(ActionEvent event) {
        String nom = tNom.getText();
        int createur = Session.getInstance().user.getId();
        String createurPrenom = Session.getInstance().user.getFirstName();
        String createurNom = Session.getInstance().user.getLastName();

        int nbCouverts = Integer.parseInt(TnbrCouverts.getText());
        int temps = Integer.parseInt(tTime.getText());
        boolean vege = yes.isSelected();
        String type = "";
        String prepa = preparation.getText();
        // String name = colname.getText();
        // int quantity = Integer.parseInt(colquantity.getText());
        // String unit = colUnit.getText();

        if (checkEntree.isSelected()) {
            type = "entrée";
        } else if (checkPlat.isSelected()) {
            type = "plat";
        } else if (checkDessert.isSelected()) {
            type = "dessert";
        }

        // Save recipe data
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres",
                "Nathan17340!")) {
            // Insert recipe data into the "recette" table
            String insertRecipeSql = "INSERT INTO recette (name, id_createur, nombre_couverts, temps_preparation, vege, type_recette, preparation) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertRecipeSql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, nom);
            stmt.setInt(2, createur);
            stmt.setInt(3, nbCouverts);
            stmt.setInt(4, temps);
            stmt.setBoolean(5, vege);
            stmt.setString(6, type);
            stmt.setString(7, prepa);

            stmt.executeUpdate();

            // Get the ID of the newly inserted recipe
            ResultSet rs = stmt.getGeneratedKeys();
            int recipeId = 0;
            if (rs.next()) {
                recipeId = rs.getInt(1);
            }

            // Get ingredient data from the "tIngredients" and "tQuantite" fields
            StringBuilder sb = new StringBuilder();

            ArrayList<String> ingredients = new ArrayList<>();
            ArrayList<Integer> quantities = new ArrayList<>();
            ArrayList<String> unities = new ArrayList<>();

            for (int i = 0; i < tvIng.getItems().size(); i++) {
                String name = tvIng.getColumns().get(0).getCellData(i).toString();
                String quantity = tvIng.getColumns().get(1).getCellData(i).toString();
                String unit = tvIng.getColumns().get(2).getCellData(i).toString();

                sb.append(name).append("\t").append(quantity).append("\t").append(unit).append("\n");
                ingredients.add(name);
                quantities.add(Integer.parseInt(quantity));
                unities.add(unit);
            }

            // Insert ingredient data into the "ingredients" table
            for (int i = 0; i < ingredients.size(); i++) {
                String insertIngredientSql = "INSERT INTO ingredient (nom, quantite, unite, id_recette) VALUES (?, ?, ?, ?)";
                stmt = conn.prepareStatement(insertIngredientSql);
                stmt.setString(1, ingredients.get(i));
                stmt.setInt(2, quantities.get(i));
                stmt.setString(3, unities.get(i));
                stmt.setInt(4, recipeId);
                stmt.executeUpdate();

            }

            // Add the recipe data to the text area
            String recette = String.format(
                    "Nom: %s\nNombre de couverts: %s\nCrée par : %s %s\nTemps :  %s\nVégétarien : %s\n\nInstructions : %s\n\nIngrédients :\n ",
                    nom, nbCouverts, createurPrenom, createurNom, temps, vege, prepa);
            for (int i = 0; i < ingredients.size(); i++) {
                recette += String.format("%s : %s : %s\n", ingredients.get(i), quantities.get(i), unities.get(i));
            }
            if (area.getText().isEmpty()) {
                area.setText(recette);
            } else {
                area.appendText("\n\n");
                area.appendText(recette);
            }

            tNom.clear();
            TnbrCouverts.clear();
            tTime.clear();
            yes.setSelected(false);
            preparation.clear();
            checkEntree.setSelected(false);
            checkPlat.setSelected(false);
            checkDessert.setSelected(false);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleAjouterButtonAction(ActionEvent event) throws IOException {
        String nom = tNom.getText();
        String texte = area.getText();
        String prepa = preparation.getText();
        Recette recette = new Recette(0, 0, 0, 0, nom, texte, 0, true, prepa);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Entree.fxml"));
        Parent root = loader.load();
        EntreeController controller = loader.getController();
        controller.ajouterRecette(recette);

        Stage stage = (Stage) tNom.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void addIngredient(ActionEvent event) {

        Ingredient ing = new Ingredient();
        ingredient.add(ing);
    }

    @FXML
    void initialize() {
        TnbrCouverts.setOnMouseClicked(event -> area.clear());
        tNom.setOnMouseClicked(event -> area.clear());
        tTime.setOnMouseClicked(event -> area.clear());
        yes.setOnMouseClicked(event -> area.clear());
        preparation.setOnMouseClicked(event -> area.clear());


        colname.setCellValueFactory(event -> event.getValue().getNomProperty());
        colname.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setNom(event.getNewValue()));

        colquantity.setCellValueFactory(
                quantite -> new SimpleStringProperty(Integer.toString(quantite.getValue().getQuantite())));
        colquantity.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setQuantite(Integer.valueOf(event.getNewValue())));

        colUnit.setCellValueFactory(event -> event.getValue().getuniteProperty());
        colUnit.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setUnite(event.getNewValue()));

        tvIng.setItems(ingredient);

        colname.setCellFactory(TextFieldTableCell.forTableColumn());
        colquantity.setCellFactory(TextFieldTableCell.forTableColumn());
        colUnit.setCellFactory(TextFieldTableCell.forTableColumn());
    }

}
