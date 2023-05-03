package fr.afpa.fourchettes;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOCommentaire extends DAO<Commentaire> {

    @Override
    public Commentaire find(int id) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres",
                    "Nathan17340!");
            Statement stm;
            stm = connection.createStatement();
            String selectUsers = "select * from commentaire where id_ingredient = " + id;
            ResultSet resultat = stm.executeQuery(selectUsers);
            String resultString = "";
            while (resultat.next()) {

                int idCommentaire = resultat.getInt("id_commentaire");
                String texte = resultat.getString("texte");
                int note = resultat.getInt("note");
                Date date = resultat.getDate("date");
                int idUser = resultat.getInt("id_user");
                int idRecette = resultat.getInt("id_recette");
                
                resultString = resultString + "-" + id;
                Commentaire commentaire = new Commentaire(idCommentaire, texte, note, date, idUser, idRecette);
                return commentaire;
            }
            // fermeture de la requête
            stm.close();
            resultat.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Commentaire> findAll() {
        try {
            ArrayList<Commentaire> commentaires = new ArrayList<Commentaire>();
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!");
            Statement stm;
            stm = connection.createStatement();
            String selectComment = "select * from commentaire";
            ResultSet resultat = stm.executeQuery(selectComment);
            String resultString = "";
            
            while (resultat.next()) {
                
                int idCommentaire = resultat.getInt("id_commentaire");
                String texte = resultat.getString("texte");
                int note = resultat.getInt("note");
                Date date = resultat.getDate("date");
                int idUser = resultat.getInt("id_user");
                int idRecette = resultat.getInt("id_recette");
                
                resultString = resultString + "-" + commentaires;
                Commentaire commentaire = new Commentaire(idCommentaire, texte, note, date, idUser, idRecette);
                commentaires.add(commentaire);
                
            }
            // fermeture de la requête
            stm.close();
            resultat.close();
            connection.close();
            return commentaires;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
        
    }



    public ArrayList<Commentaire> findByCommentaire(Commentaire commentaire) {
        try {
            ArrayList<Commentaire> commentaires = new ArrayList<Commentaire>();
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM commentaire WHERE id_commentaire = ?");
            statement.setInt(1, commentaire.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                int idCommentaire = resultSet.getInt("id_commentaire");
                String texte = resultSet.getString("texte");
                int note = resultSet.getInt("note");
                Date date = resultSet.getDate("date");
                int idUser = resultSet.getInt("id_user");
                int idRecette = resultSet.getInt("id_recette");
                

                commentaires.add(new Commentaire(idCommentaire, texte, note, date, idUser, idRecette));
            }
            resultSet.close();
            statement.close();
            connection.close();
            return commentaires;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Commentaire> findByRecette(Recette recette) {
        ArrayList<Commentaire> commentaires = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!");
            String query = "SELECT * FROM commentaire WHERE id_recette = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, recette.getId());
            ResultSet resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                int idCommentaire = resultSet.getInt("id_commentaire");
                String texte = resultSet.getString("texte");
                int note = resultSet.getInt("note");
                Date date = resultSet.getDate("date");
                int idUser = resultSet.getInt("id_user");
                int idRecette = resultSet.getInt("id_recette");
    
                Commentaire commentaire = new Commentaire(idCommentaire, texte, note, date, idUser, idRecette);
                commentaires.add(commentaire);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentaires;
    }

    public ArrayList<Commentaire> findByuser(Utilisateur utilisateur) {
        ArrayList<Commentaire> commentaires = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!");
            String query = "SELECT * FROM commentaire WHERE id_user = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, utilisateur.getId());
            ResultSet resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                int idCommentaire = resultSet.getInt("id_commentaire");
                String texte = resultSet.getString("texte");
                int note = resultSet.getInt("note");
                Date date = resultSet.getDate("date");
                int idUser = resultSet.getInt("id_user");
                int idRecette = resultSet.getInt("id_recette");
    
                Commentaire commentaire = new Commentaire(idCommentaire, texte, note, date, idUser, idRecette);
                commentaires.add(commentaire);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentaires;
    }
    
    public void delete(int id) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM commentaire WHERE id_commentaire = ?");
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting commentaire failed, no rows affected.");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void deleteByRecetteId(int recetteId) {
        try {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!");
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM commentaire WHERE id_recette = ?");
            stmt.setInt(1, recetteId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting commentaire by recette id: " + e.getMessage());
        }
    }
    

    public Commentaire insert(Commentaire newcommentaire) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!")) {

            // Préparer la requête SQL d'insertion
            String sql = "INSERT INTO commentaire (texte, id_recette) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, newcommentaire.getText());
            pstmt.setInt(2, newcommentaire.getIdRecette());


            // Exécuter la requête SQL d'insertion et récupérer l'identifiant de la nouvelle station
            int affectedRows = pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();


            while (res.next()) {     
                int newId = res.getInt(1); 
                newcommentaire.setId(newId);
            }

            


            if (affectedRows == 0) {
                throw new SQLException("Insertion du commentaire a échoué, aucune ligne affectée.");
            }

            return newcommentaire;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Commentaire getCommentaireById(String id) {
        String query = "SELECT * FROM commentaire WHERE id_commentaire = ?";
        Commentaire commentaire = new Commentaire(0, query, 0, null, 0, 0);
    
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, commentaire.getId());
       
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                int idCommentaire = rs.getInt("id_commentaire");
                String texte = rs.getString("texte");
                int note = rs.getInt("note");
                Date date = rs.getDate("date");
                int idUser = rs.getInt("id_user");
                int idRecette = rs.getInt("id_recette");

                Commentaire commentaires = new Commentaire(idCommentaire, texte, note, date, idUser, idRecette);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    
        return commentaire;
    }
    
    

    
}
