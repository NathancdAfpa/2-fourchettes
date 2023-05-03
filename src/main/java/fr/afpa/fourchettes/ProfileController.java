package fr.afpa.fourchettes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProfileController {

    @FXML
    private ImageView img;

    @FXML
    private Line line;

    @FXML
    private Label mail;

    @FXML
    private Button modif;

    @FXML
    private Label nom;

    @FXML
    private Label prenom;

    @FXML
    private Button retour;

    private String fileName;
    

    @FXML
    void addretour(ActionEvent event) throws IOException {
        Stage stage = (Stage) retour.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome_view.fxml"));
        Parent root = loader.load();
        WelcomeController user = loader.getController();
        stage.getScene().setRoot(root);
    }


    @FXML
    void addmodif(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionnez une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers image", "*.jpg", "*.jpeg", "*.png"));

        // Ouvrir la boîte de dialogue de sélection de fichier et attendre que
        // l'utilisateur sélectionne un fichier
        File selectedFile = fileChooser.showOpenDialog(modif.getScene().getWindow());

        // Si l'utilisateur a sélectionné un fichier, charger l'image dans l'ImageView
        if (selectedFile != null) {
            try {
                // Créer un FileInputStream à partir du fichier sélectionné
                FileInputStream input = new FileInputStream(selectedFile);
                // Créer une Image à partir du FileInputStream
                Image image = new Image(input);
                // Afficher l'image dans l'ImageView
                img.setImage(image);
                fileName = saveImage(selectedFile, 0);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String saveImage(File file, int userId) throws IOException {
        // Générer un nom de fichier unique avec UUID
        String fileName = UUID.randomUUID().toString() + ".png";
        // Créer un fichier pour stocker l'image
        File imageFile = new File(fileName);
        // Copier le contenu du fichier sélectionné dans le fichier de l'utilisateur
        Files.copy(file.toPath(), imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    private void loadProfileImage(String fileName) throws IOException {
        try {
            // Créer un fichier à partir du nom de fichier passé en paramètre
            File imageFile = new File(fileName);
            // Vérifier si le fichier existe
            if (imageFile.exists()) {
                // Créer une Image à partir du contenu du fichier
                Image image = new Image(new FileInputStream(imageFile));
                // Afficher l'image dans l'ImageView
                img.setImage(image);
            } else {
                // Si le fichier n'existe pas, afficher une image par défaut
              
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    

    

    @FXML
    public void initialize() throws IOException {
        fileName = UUID.randomUUID().toString() + ".png";
        loadProfileImage(fileName);    }

}
