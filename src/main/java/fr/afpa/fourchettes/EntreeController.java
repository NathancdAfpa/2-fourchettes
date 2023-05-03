package fr.afpa.fourchettes;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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
    private RadioButton rbnonvege;

    @FXML
    private RadioButton rbtout;

    @FXML
    private RadioButton rbvege;

    @FXML
    private Rectangle rect;

    @FXML
    private VBox vboxNote;

    @FXML
    private ImageView im;

    @FXML
    private Button see;

    @FXML
    private ComboBox<Restaurant> selectResto;

    @FXML
    private Label title;

    @FXML
    private TableView<Recette> tv;

    private ObservableList<Recette> recettes = FXCollections.observableArrayList();

    private ObservableList<Restaurant> restaurants = FXCollections.observableArrayList();

    private Utilisateur utilisateur;

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
    void addResto(ActionEvent event) {
        Restaurant selectedRestaurant = selectResto.getValue();
        DAORecette daoRecette = new DAORecette();
        if (selectedRestaurant != null) {
            // Récupérer toutes les recettes d'entrées correspondantes au restaurant
            // sélectionné

            List<Recette> entreeRecettes = daoRecette.findByRestoEntree(selectedRestaurant);

            // Mettre à jour la TableView avec les recettes d'entrées
            selectResto.setItems(restaurants);
            recettes.clear();
            recettes.addAll(entreeRecettes);
            tv.setItems(recettes);
        }
    }

    public void ajouterRecette(Recette recette) {
        recettes.add(recette);
    }

    public void ajoutResto(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public void clearTableView() {
        recettes.clear();
        tv.setItems(recettes);
    }

    public void initialize() {

        DAORestaurant daoRestaurant = new DAORestaurant();
        List<Restaurant> restaurantList = daoRestaurant.findAll();
        restaurants.addAll(restaurantList);
        selectResto.setItems(restaurants);

        selectResto.setOnAction(e -> addResto(e));
        restaurants = FXCollections.observableArrayList(daoRestaurant.findAll());
        colTv.setCellValueFactory(nom -> new SimpleStringProperty(nom.getValue().getName()));
        DAORecette daoRecette = new DAORecette();
        // Récupération de tous les noms de recettes depuis la base de données
        List<Recette> nomsRecettes = daoRecette.findAll();
        recettes.addAll(nomsRecettes);
        // Mise à jour de la TableView avec les recettes créées
        tv.setItems(recettes);

        rbtout.setOnAction(this::handleRadioButtonAction);
        rbnonvege.setOnAction(this::handleRadioButtonAction);
        rbvege.setOnAction(this::handleRadioButtonAction);

        ToggleGroup group = new ToggleGroup();
        rbtout.setToggleGroup(group);
        rbnonvege.setToggleGroup(group);
        rbvege.setToggleGroup(group);

        clearTableView();

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
    void handleRadioButtonAction(ActionEvent event) {
        // Créer une nouvelle liste pour stocker les recettes filtrées
        ObservableList<Recette> filteredRecettes = FXCollections.observableArrayList();

        // Vérifier quel RadioButton est sélectionné
        if (rbtout.isSelected()) {
            // Si "tout" est sélectionné, ajouter toutes les recettes à la liste filtrée
            filteredRecettes.addAll(recettes);
        } else if (rbnonvege.isSelected()) {
            // Si "non-vegetarien" est sélectionné, filtrer les recettes non-végétariennes
            filteredRecettes.addAll(recettes.stream().filter(r -> !r.getVegeOuPas()).collect(Collectors.toList()));
        } else if (rbvege.isSelected()) {
            // Si "vegetarien" est sélectionné, filtrer les recettes végétariennes
            filteredRecettes.addAll(recettes.stream().filter(Recette::getVegeOuPas).collect(Collectors.toList()));
        }

        // Mettre à jour la TableView avec la liste filtrée
        tv.setItems(filteredRecettes);

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
            String prenomCreateur = selectedRecette.getCreateur().getFirstName();
            String nomCreateur = selectedRecette.getCreateur().getLastName();
            Double moyenne = selectedRecette.getMoyenne();

            DAOIngredient daoIngredient = new DAOIngredient();
            ArrayList<Ingredient> infoIngredients = daoIngredient.findByRecette(selectedRecette);

            String ingredientsText = "";

            for (Ingredient ingredient : infoIngredients) {
                ingredientsText += ingredient.getNom() + " : " + ingredient.getQuantite() + " " + ingredient.getUnite()
                        + "\n";
            }

            area.setText("\nTitre de la recette : " + name + "\nCréée par : " + prenomCreateur + " " + nomCreateur
                    + "\n\nType : " + type + "\n\nNombre de couverts : "
                    + nombreCouverts + "\n\nTemps de préparation : " + preparetime + " minutes"
                    + "\n\n\n            Ingrédients et quantités\n\n"
                    + ingredientsText + "\n\nPréparation :\n" + preparation);

            if (vege) {
                area.setStyle("-fx-control-inner-background: #ccffcc;"); // vert clair
            } else {
                area.setStyle("-fx-control-inner-background: #ffcccc;"); // rouge clair
            }

            DAORecette daoRecette = new DAORecette();

            ArrayList<Recette> infoRecettes = new ArrayList<>(recettes);

            String recetteText = "";

            URL etoile1 = CommentController.class.getResource("/fr/afpa/fourchettes/image1.png");
            String chemin = etoile1.toString();
   
            URL etoile2 = CommentController.class.getResource("/fr/afpa/fourchettes/image2.png");
            String chemin2 = etoile2.toString();

            URL etoile3 = CommentController.class.getResource("/fr/afpa/fourchettes/image3.png");
            String chemin3 = etoile3.toString();

            URL etoile4 = CommentController.class.getResource("/fr/afpa/fourchettes/image4.png");
            String chemin4 = etoile4.toString();

            URL etoile5 = CommentController.class.getResource("/fr/afpa/fourchettes/image5.png");
            String chemin5 = etoile5.toString();

            URL etoile6 = CommentController.class.getResource("/fr/afpa/fourchettes/1etdemi.jpg");
            String chemin6 = etoile6.toString();

            URL etoile7 = CommentController.class.getResource("/fr/afpa/fourchettes/2etdemi.jpg");
            String chemin7 = etoile7.toString();

            URL etoile8 = CommentController.class.getResource("/fr/afpa/fourchettes/3etdemi.jpg");
            String chemin8 = etoile8.toString();

            URL etoile9 = CommentController.class.getResource("/fr/afpa/fourchettes/4etdemi.png.jpg");
            String chemin9 = etoile9.toString();

            for (Recette recette : infoRecettes) {
                recetteText = "       " + recette.getMoyenne();

                Label label1 = new Label(recetteText);
                label1.setPrefWidth(500);
                Font myFont1 = new Font("Palatino Linotype", 60);
                label1.setFont(myFont1);

                Image image1 = new Image(chemin);
                Image image2 = new Image(chemin2);
                Image image3 = new Image(chemin3);
                Image image4 = new Image(chemin4);
                Image image5 = new Image(chemin5);
                Image image6 = new Image(chemin6);
                Image image7 = new Image(chemin7);
                Image image8 = new Image(chemin8);
                Image image9 = new Image(chemin9);



                if(recette.getMoyenne() == 1) {
                ImageView imageView1 = new ImageView(image1);
                vboxNote.getChildren().addAll(label1, imageView1);
            }
            else if(recette.getMoyenne() == 2) {
                ImageView imageView2 = new ImageView(image2);
                vboxNote.getChildren().addAll(label1, imageView2);
            }
            else if(recette.getMoyenne() == 3) {
                ImageView imageView3 = new ImageView(image3);
                vboxNote.getChildren().addAll(label1, imageView3);
            }
            else if(recette.getMoyenne() == 4) {
                ImageView imageView4 = new ImageView(image4);
                vboxNote.getChildren().addAll(label1, imageView4);
            }
            else if(recette.getMoyenne() == 5) {
                ImageView imageView5 = new ImageView(image5);
                vboxNote.getChildren().addAll(label1, imageView5);
            }
            else if(recette.getMoyenne() >= 1.5 && recette.getMoyenne() <= 2) {
                ImageView imageView6 = new ImageView(image6);
                vboxNote.getChildren().addAll(label1, imageView6);
            }
            else if(recette.getMoyenne() >= 2.5 && recette.getMoyenne() <= 3) {
                ImageView imageView7 = new ImageView(image7);
                vboxNote.getChildren().addAll(label1, imageView7);
            }
            else if(recette.getMoyenne() >= 3.5 && recette.getMoyenne() <= 4) {
                ImageView imageView8 = new ImageView(image8);
                vboxNote.getChildren().addAll(label1, imageView8);
            }
            else if(recette.getMoyenne() > 4.5 && recette.getMoyenne() <= 5) {
                ImageView imageView9 = new ImageView(image9);
                vboxNote.getChildren().addAll(label1, imageView9);
            }
        }
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
            LocalDate date = LocalDate.now();
            java.sql.Date sqlDate = java.sql.Date.valueOf(date);

            Commentaire com = new Commentaire(selectedRecette.getId(), texte, 0, sqlDate, note,
                    Session.getInstance().user.getId());

            // Save recipe data
            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                    "postgres",
                    "Nathan17340!")) {
                // Insert recipe data into the "recette" table
                String insertRecipeSql = "INSERT INTO commentaire (texte, id_recette, id_user, note, date) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(insertRecipeSql, Statement.RETURN_GENERATED_KEYS);

                stmt.setString(1, texte);
                stmt.setInt(2, recetteId);
                stmt.setInt(3, userId);
                stmt.setInt(4, note);
                stmt.setDate(5, sqlDate);

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
