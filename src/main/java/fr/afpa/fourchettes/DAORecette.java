package fr.afpa.fourchettes;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Callback;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAORecette extends DAO<Recette> {

    @Override
    public Recette find(int id) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                    "postgres",
                    "Nathan17340!");
            Statement stm;
            stm = connection.createStatement();
            String selectUsers = "select * from recette where id = " + id;
            ResultSet resultat = stm.executeQuery(selectUsers);
            String resultString = "";
            while (resultat.next()) {

                int idRecette = resultat.getInt("id_recette");
                int idResto = resultat.getInt("id_restaurant");
                int tempsPrepa = resultat.getInt("temps_preparation");
                int nbCouverts = resultat.getInt("nombre_couverts");
                String type = resultat.getString("type_recette");
                String name = resultat.getString("name");
                int createur = resultat.getInt("id_createur");
                Boolean vege = resultat.getBoolean("vege");
                String preparation = resultat.getString("preparation");

                resultString = resultString + "-" + id;
                Recette recette = new Recette(idRecette, idResto, tempsPrepa, nbCouverts, type, name, createur, vege, preparation);
                return recette;
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

    public ArrayList<Recette> findAll() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                "postgres",
                "Nathan17340!");
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM recette")) {
            ArrayList<Recette> result = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id_recette");
                String name = rs.getString("name");
                int tempsPrepa = rs.getInt("temps_preparation");
                int nbrCouverts = rs.getInt("nombre_couverts");
                String type = rs.getString("type_recette");
                boolean vege = rs.getBoolean("vege");
                String preparation = rs.getString("preparation");

                Recette recette = new Recette(id, 0, tempsPrepa, nbrCouverts, name, type, 0, vege, preparation);
                result.add(recette);
            }
            return result;
        
        
    }catch(

    SQLException e)
    {
        e.printStackTrace();
        return null;
    }
    }

    public ArrayList<Recette> findAllEntree() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                "postgres",
                "Nathan17340!");
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM recette where type_recette like 'entrée'")) {
            ArrayList<Recette> result = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id_recette");
                String name = rs.getString("name");
                int tempsPrepa = rs.getInt("temps_preparation");
                int nbrCouverts = rs.getInt("nombre_couverts");
                String type = rs.getString("type_recette");
                boolean vege = rs.getBoolean("vege");
                String preparation = rs.getString("preparation");

                Recette recette = new Recette(id, 0, tempsPrepa, nbrCouverts, name, type, 0, vege, preparation);
                result.add(recette);
            }
            return result;
        
        
    }catch(

    SQLException e)
    {
        e.printStackTrace();
        return null;
    }
    }

    public ArrayList<Recette> findAllPlat() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                "postgres",
                "Nathan17340!");
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM recette where type_recette like 'plat'")) {
            ArrayList<Recette> result = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id_recette");
                String name = rs.getString("name");
                int tempsPrepa = rs.getInt("temps_preparation");
                int nbrCouverts = rs.getInt("nombre_couverts");
                String type = rs.getString("type_recette");
                boolean vege = rs.getBoolean("vege");
                String preparation = rs.getString("preparation");

                Recette recette = new Recette(id, 0, tempsPrepa, nbrCouverts, name, type, 0, vege, preparation);
                result.add(recette);
            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Recette> findAllDessert() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                "postgres",
                "Nathan17340!");
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM recette where type_recette like 'dessert'")) {
            ArrayList<Recette> result = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id_recette");
                String name = rs.getString("name");
                int tempsPrepa = rs.getInt("temps_preparation");
                int nbrCouverts = rs.getInt("nombre_couverts");
                String type = rs.getString("type_recette");
                boolean vege = rs.getBoolean("vege");
                String preparation = rs.getString("preparation");

                Recette recette = new Recette(id, 0, tempsPrepa, nbrCouverts, name, type, 0, vege, preparation);
                result.add(recette);

            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Recette delete(int id) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                    "postgres", "Nathan17340!");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM recette WHERE id_recette = ?");
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting recette failed, no rows affected.");
            }
            statement.close();
            connection.close();
            return new Recette(affectedRows, affectedRows, affectedRows, affectedRows, null, null, affectedRows, true, null);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Recette insert(Recette newrecette) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres",
                "Nathan17340!")) {

            // Préparer la requête SQL d'insertion
            String sql = "INSERT INTO recette (id_restaurant, temps_preparation, nombre_couverts, type_recette, name, id_createur, preparation) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, newrecette.getRestoId());
            pstmt.setInt(2, newrecette.getPrepareTime());
            pstmt.setInt(3, newrecette.getNombreCouverts());
            pstmt.setString(4, newrecette.getTypeRecette());
            pstmt.setString(5, newrecette.getName());
            pstmt.setInt(6, newrecette.getCreateur());
            pstmt.setString(7, newrecette.getPreparation());
            // Exécuter la requête SQL d'insertion et récupérer l'identifiant de la nouvelle
            // station
            int affectedRows = pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();

            while (res.next()) {
                int newId = res.getInt(1);
                newrecette.setId(newId);
            }

            if (affectedRows == 0) {
                throw new SQLException("Insertion de la recette a échoué, aucune ligne affectée.");
            }

            return newrecette;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Recette getRecetteById(String id) {
        String query = "SELECT * FROM recette WHERE name = ?";
        Recette recette = new Recette(0, 0, 0, 0, query, query, 0, true, query);

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres",
                "Nathan17340!");
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, recette.getName());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idRecette = rs.getInt("id_recette");
                int idResto = rs.getInt("id_restaurant");
                int tempsPrepa = rs.getInt("temps_preparation");
                int nbCouverts = rs.getInt("nombre_couverts");
                String type = rs.getString("type_recette");
                String name = rs.getString("name");
                int createur = rs.getInt("id_createur");
                Boolean vege = rs.getBoolean("vege");
                String preparation = rs.getString("preparation");
                recette = new Recette(idRecette, idResto, tempsPrepa, nbCouverts, type, name, createur, vege, preparation);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return recette;
    }

}
