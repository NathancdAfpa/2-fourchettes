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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class EditionRecetteController {

    @FXML
    private Button moins;

    @FXML
    private Button more;

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
    private Label num;

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

        // Before calling the addAjout method
        String nom = tNom.getText();
        int nbCouverts = Integer.parseInt(num.getText());
        int temps = Integer.parseInt(tTime.getText());
        boolean vege = yes.isSelected();
        String type = "";
        String prepa = preparation.getText();
        int idResto = Session.getInstance().user.getRestoId();
        Double moyenne = (Session.getInstance().recette != null)
                ? Double.parseDouble(String.valueOf(Session.getInstance().recette.getMoyenne()))
                : 0.0;
        Utilisateur createUtilisateur = Session.getInstance().user;
             

        if (checkEntree.isSelected()) {
            type = "entrée";
        } else if (checkPlat.isSelected()) {
            type = "plat";
        } else if (checkDessert.isSelected()) {
            type = "dessert";
        }

        // int restoId, int prepareTime, int nombreCouverts, String name,
        // String typeRecette, int iDcreateur, boolean vegeOuPas, String preparation,
        // String nomCreateur,
        // Double moyenne
        // Save recipe data
        Recette newRecette = new Recette(idResto, temps, nbCouverts, nom, type, vege, prepa, moyenne,
                createUtilisateur);
        DAORecette daorecette = new DAORecette();
        daorecette.insert(newRecette);

        // Get ingredient data from the table view
        ArrayList<Integer> quantities = new ArrayList<>();
        ObservableList<Ingredient> listIngredient = tvIng.getItems();

        for (Ingredient ingredient : listIngredient) {
            quantities.add(ingredient.getQuantite());

            Ingredient newIngredient = new Ingredient(ingredient.getNom(), ingredient.getQuantite(), newRecette.getId(),
                    ingredient.getUnite());
            DAOIngredient daoIngredient = new DAOIngredient();
            daoIngredient.insert(newIngredient);

        }

        // Multiply quantities by the number of couverts
        for (int i = 0; i < quantities.size(); i++) {
            int multipliedQuantity = quantities.get(i) * nbCouverts;
            quantities.set(i, multipliedQuantity);
        }

        // Insert ingredient data into the "ingredients" table

        // Add the recipe data to the text area
        String recetteText = String.format(
                "Nom: %s\nNombre de couverts: %s\nCrée par : %s %s\nTemps de préparation :  %s\nVégétarien : %s\n\nInstructions : %s\n\nIngrédients :\n ",
                nom, nbCouverts, createUtilisateur.getFirstName(), createUtilisateur.getLastName(), temps + " minutes", vege, prepa);

        for (int i = 0; i < listIngredient.size(); i++) {
            Ingredient newIngredient = listIngredient.get(i);
            recetteText += String.format("%s : %s: %s\n", newIngredient.getNom(), quantities.get(i),
                    newIngredient.getUnite());
        }
        if (area.getText().isEmpty()) {
            area.setText(recetteText);
        } else {
            area.appendText("\n\n");
            area.appendText(recetteText);
        }

        tNom.clear();
        tTime.clear();
        yes.setSelected(false);
        preparation.clear();
        checkEntree.setSelected(false);
        checkPlat.setSelected(false);
        checkDessert.setSelected(false);
    }

    @FXML
    private void handleAjouterButtonAction(ActionEvent event) throws IOException {
        String nom = tNom.getText();
        String texte = area.getText();
        String prepa = preparation.getText();
        Utilisateur createUtilisateure = Session.getInstance().recette.getCreateur();

        Recette recette = new Recette(0, 0, 0, 0, nom, texte, true, prepa, 0.0, createUtilisateure);

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
    void addMore(ActionEvent event) {
        int currentValue = Integer.parseInt(num.getText());
        int newValue = currentValue + 1;
        num.setText(String.valueOf(newValue));
        updateQuantities(newValue);
    }

    @FXML
    void addMoins(ActionEvent event) {
        int currentNumberOfGuests = Integer.parseInt(num.getText());
        if (currentNumberOfGuests > 1) {
            int newNumberOfGuests = currentNumberOfGuests - 1;
            num.setText(Integer.toString(newNumberOfGuests));
            updateQuantities(newNumberOfGuests);
        }
    }

    @FXML
    void initialize() {
        num.setOnMouseClicked(event -> area.clear());
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

        num.textProperty().addListener((observable, oldValue, newValue) -> {
            updateQuantities(Integer.parseInt(newValue));
        });

        num.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                int newNumberOfGuests = Integer.parseInt(newValue);
                for (Ingredient ingredient : ingredient) {
                    int oldQuantity = ingredient.getQuantite();
                    int newQuantity;
                    if (newNumberOfGuests == 0) {
                        newQuantity = 0; // Éviter une division par zéro
                    } else {
                        newQuantity = oldQuantity * newNumberOfGuests / Integer.parseInt(oldValue);
                    }
                    ingredient.setQuantite(newQuantity);
                }
                tvIng.refresh(); // Rafraîchir la TableView pour afficher les nouvelles quantités
            }
        });

    }

    private void updateQuantities(int newNumberOfGuests) {
        for (Ingredient ingredient : ingredient) {
            int oldQuantity = ingredient.getQuantite();
            int newQuantity;
            if (newNumberOfGuests == 0) {
                newQuantity = 0; // Éviter une division par zéro
            } else {
                newQuantity = oldQuantity * newNumberOfGuests / Integer.parseInt(num.getText());
            }
            ingredient.setQuantite(newQuantity);
        }
        tvIng.refresh(); // Rafraîchir la TableView pour afficher les nouvelles quantités
    }

}