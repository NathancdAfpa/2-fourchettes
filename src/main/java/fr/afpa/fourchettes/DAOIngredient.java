package fr.afpa.fourchettes;

import java.sql.Statement;
import java.util.ArrayList;

import javafx.util.Callback;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOIngredient extends DAO<Ingredient> {

    @Override
    public Ingredient find(int id) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres",
                    "Nathan17340!");
            Statement stm;
            stm = connection.createStatement();
            String selectUsers = "select * from ingredient where id_ingredient = " + id;
            ResultSet resultat = stm.executeQuery(selectUsers);
            String resultString = "";
            while (resultat.next()) {

                int idIngredient = resultat.getInt("id_ingredient");
                String name = resultat.getString("nom");
                int quantite = resultat.getInt("quantite");
                int idRecette = resultat.getInt("id_recette");
                String unite = resultat.getString("unite");
                
                resultString = resultString + "-" + id;
                Ingredient ingredient = new Ingredient(idIngredient, name, quantite, idRecette, unite);
                return ingredient;
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
    public ArrayList<Ingredient> findAll() {
        try {
            ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!");
            Statement stm;
            stm = connection.createStatement();
            String selectUsers = "select * from ingredient";
            ResultSet resultat = stm.executeQuery(selectUsers);
            String resultString = "";
            
            while (resultat.next()) {
                
                int idIngredient = resultat.getInt("id_ingredient");
                String name = resultat.getString("nom");
                int quantite = resultat.getInt("quantite");
                int idRecette = resultat.getInt("id_recette");
                String unite = resultat.getString("unite");
                
        
                resultString = resultString + "-" + ingredients;
                Ingredient ingredient = new Ingredient(idIngredient, name, quantite, idRecette, unite);
                ingredients.add(ingredient);
                
            }
            // fermeture de la requête
            stm.close();
            resultat.close();
            connection.close();
            return ingredients;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
        
    }

    public ArrayList<Ingredient> findByIngredient(Ingredient ingredient) {
        try {
            ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ingredient WHERE id_ingredient = ?");
            statement.setInt(1, ingredient.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                int idIngredient = resultSet.getInt("id_ingredient");
                String name = resultSet.getString("nom");
                int quantite = resultSet.getInt("quantite");
                int idRecette = resultSet.getInt("id_recette");
                String unite = resultSet.getString("unite");
                

                ingredients.add(new Ingredient(idIngredient, name, quantite, idRecette, unite));
            }
            resultSet.close();
            statement.close();
            connection.close();
            return ingredients;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Ingredient> findByRecette(Recette recette) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!");
            String query = "SELECT * FROM ingredient WHERE id_recette = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, recette.getId());
            ResultSet resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                int id = resultSet.getInt("id_ingredient");
                String nom = resultSet.getString("nom");
                int quantite = resultSet.getInt("quantite");
                int idRecette = resultSet.getInt("id_recette");
                String unite = resultSet.getString("unite");
    
                Ingredient ingredient = new Ingredient(id, nom, quantite, idRecette, unite);
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }
    
    

    public Ingredient delete(int id) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM ingredient WHERE id_ingredient = ?");
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting ingredient failed, no rows affected.");
            }
            statement.close();
            connection.close();
            return new Ingredient(affectedRows, null, affectedRows, affectedRows, null);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void deleteByRecetteId(int recetteId) {
        try {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!");
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM ingredient WHERE id_recette = ?");
            stmt.setInt(1, recetteId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting ingredients by recette id: " + e.getMessage());
        }
    }
    

    public Ingredient insert(Ingredient newingredient) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!")) {

            // Préparer la requête SQL d'insertion
            String sql = "INSERT INTO ingredient (id_ingredient, nom, quantite, unite, id_recette) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
           
            pstmt.setInt(1, newingredient.getId());
            pstmt.setString(2, newingredient.getNom());
            pstmt.setInt(3, newingredient.getQuantite());
            pstmt.setString(4, newingredient.getUnite());
            pstmt.setInt(5, newingredient.getIdRecette());
            // Exécuter la requête SQL d'insertion et récupérer l'identifiant de la nouvelle station
            int affectedRows = pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();


            while (res.next()) {     
                int newId = res.getInt(1); 
                newingredient.setId(newId);
            }

            


            if (affectedRows == 0) {
                throw new SQLException("Insertion de l'ingredient a échoué, aucune ligne affectée.");
            }

            return newingredient;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Ingredient getIngredientById(String id) {
        String query = "SELECT * FROM ingredient WHERE id_ingredient = ?";
        Ingredient ingredient = new Ingredient(0, query, 0, 0, query);
    
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ingredient.getId());
       
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                int idIngredient = rs.getInt("id_ingredient");
                String name = rs.getString("nom");
                int quantite = rs.getInt("quantite");
                int idRecette = rs.getInt("id_recette");
                String unite = rs.getString("unite");
                ingredient = new Ingredient(idIngredient, name, quantite, idRecette, unite);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    
        return ingredient;
    }
    
    

    
}
