package fr.afpa.fourchettes;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CommentController {
    
    @FXML
    private TableColumn<Recette, String> colname;

    @FXML
    private Button retour;

    @FXML
    private Button voir;

    @FXML
    private VBox vboxComment;

    @FXML
    private Label title;

    @FXML
    private TableView<Recette> tv;

    @FXML
    private ImageView imageView;

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

            String commentaireText = "";

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

            for (Commentaire commentaire : infoCommentaires) {
                commentaireText = "     " + userPrenom + " " + userNom + ", le " + commentaire.getDate() + ":" + "\n\n     " + commentaire.getText() +  "\n\n";

                if (Session.getInstance().commentaire != null) {
                    Date date = Session.getInstance().commentaire.getDate();
                    // rest of the code that uses date
                    
                }

                Label label1 = new Label(commentaireText);
                label1.setPrefWidth(500);
                Font myFont1 = new Font("Serif", 18);
                label1.setFont(myFont1);

                Image image1 = new Image(chemin);
                Image image2 = new Image(chemin2);
                Image image3 = new Image(chemin3);
                Image image4 = new Image(chemin4);
                Image image5 = new Image(chemin5);



                if(commentaire.getNote() == 1) {
                ImageView imageView1 = new ImageView(image1);
                vboxComment.getChildren().addAll(label1, imageView1);
            }
            else if(commentaire.getNote() == 2) {
                ImageView imageView2 = new ImageView(image2);
                vboxComment.getChildren().addAll(label1, imageView2);
            }
            else if(commentaire.getNote() == 3) {
                ImageView imageView3 = new ImageView(image3);
                vboxComment.getChildren().addAll(label1, imageView3);
            }
            else if(commentaire.getNote() == 4) {
                ImageView imageView4 = new ImageView(image4);
                vboxComment.getChildren().addAll(label1, imageView4);
            }
            else if(commentaire.getNote() == 5) {
                ImageView imageView5 = new ImageView(image5);
                vboxComment.getChildren().addAll(label1, imageView5);
            }
        }
            
        }
    }

}
