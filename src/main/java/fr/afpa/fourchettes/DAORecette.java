package fr.afpa.fourchettes;

import java.sql.Statement;
import java.util.ArrayList;
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
            DAOUtilisateur daoutilisateur = new DAOUtilisateur();
            while (resultat.next()) {

                int idRecette = resultat.getInt("id_recette");
                int idResto = resultat.getInt("id_restaurant");
                int tempsPrepa = resultat.getInt("temps_preparation");
                int nbCouverts = resultat.getInt("nombre_couverts");
                String type = resultat.getString("type_recette");
                String name = resultat.getString("name");
                int createurId = resultat.getInt("id_createur");
                Boolean vege = resultat.getBoolean("vege");
                String preparation = resultat.getString("preparation");
                Double moyenne = resultat.getDouble("moyenne");
                Utilisateur createur = daoutilisateur.find(createurId);

                // int id, int restoId, int prepareTime, int nombreCouverts, String name,
                // String typeRecette, int iDcreateur, boolean vegeOuPas, String preparation,
                // Double moyenne, Utilisateur createur


                resultString = resultString + "-" + id;
                Recette recette = new Recette(idRecette, idResto, tempsPrepa, nbCouverts, name, type, vege,
                        preparation, moyenne, createur);
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
            DAOUtilisateur daoutilisateur = new DAOUtilisateur();
            while (rs.next()) {
                int idRecette = rs.getInt("id_recette");
                int idResto = rs.getInt("id_restaurant");
                int tempsPrepa = rs.getInt("temps_preparation");
                int nbCouverts = rs.getInt("nombre_couverts");
                String type = rs.getString("type_recette");
                String name = rs.getString("name");
                int createurId = rs.getInt("id_createur");
                Boolean vege = rs.getBoolean("vege");
                String preparation = rs.getString("preparation");
                Double moyenne = rs.getDouble("moyenne");
                Utilisateur createur = daoutilisateur.find(createurId);

                Recette recette = new Recette(idRecette, idResto, tempsPrepa, nbCouverts, name, type, vege,
                preparation, moyenne, createur);
                result.add(recette);
            }
            return result;

        } catch (

        SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Recette> findByRestoEntree(Restaurant resto) {
        try {
            ArrayList<Recette> recettes = new ArrayList<Recette>();
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                    "postgres",
                    "Nathan17340!");
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM recette where type_recette like 'entrée' and id_restaurant = ?");
            statement.setInt(1, resto.getId());
            ResultSet resultSet = statement.executeQuery();
            DAOUtilisateur daoutilisateur = new DAOUtilisateur();
            while (resultSet.next()) {
                int idRecette = resultSet.getInt("id_recette");
                int idResto = resultSet.getInt("id_restaurant");
                int tempsPrepa = resultSet.getInt("temps_preparation");
                int nbCouverts = resultSet.getInt("nombre_couverts");
                String type = resultSet.getString("type_recette");
                String name = resultSet.getString("name");
                int createurId = resultSet.getInt("id_createur");
                Boolean vege = resultSet.getBoolean("vege");
                String preparation = resultSet.getString("preparation");
                Double moyenne = resultSet.getDouble("moyenne");
                Utilisateur createur = daoutilisateur.find(createurId);
                recettes.add(new Recette(idRecette, idResto, tempsPrepa, nbCouverts, name, type, vege,
                preparation, moyenne, createur));
            }
            resultSet.close();
            statement.close();
            connection.close();
            return recettes;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Recette> findByRestoPlat(Restaurant resto) {
        try {
            ArrayList<Recette> recettes = new ArrayList<Recette>();
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                    "postgres",
                    "Nathan17340!");
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM recette where type_recette like 'plat' and id_restaurant = ?");
            statement.setInt(1, resto.getId());
            ResultSet resultSet = statement.executeQuery();
            DAOUtilisateur daoutilisateur = new DAOUtilisateur();
            while (resultSet.next()) {
                int idRecette = resultSet.getInt("id_recette");
                int idResto = resultSet.getInt("id_restaurant");
                int tempsPrepa = resultSet.getInt("temps_preparation");
                int nbCouverts = resultSet.getInt("nombre_couverts");
                String type = resultSet.getString("type_recette");
                String name = resultSet.getString("name");
                int createurId = resultSet.getInt("id_createur");
                Boolean vege = resultSet.getBoolean("vege");
                String preparation = resultSet.getString("preparation");
                Double moyenne = resultSet.getDouble("moyenne");
                Utilisateur createur = daoutilisateur.find(createurId);
                recettes.add(new Recette(idRecette, idResto, tempsPrepa, nbCouverts, name, type, vege,
                preparation, moyenne, createur));
            }
            resultSet.close();
            statement.close();
            connection.close();
            return recettes;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Recette> findByRestoDessert(Restaurant resto) {
        try {
            ArrayList<Recette> recettes = new ArrayList<Recette>();
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                    "postgres",
                    "Nathan17340!");
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM recette where type_recette like 'dessert' and id_restaurant = ?");
            statement.setInt(1, resto.getId());
            ResultSet resultSet = statement.executeQuery();
            DAOUtilisateur daoutilisateur = new DAOUtilisateur();

            while (resultSet.next()) {
                int idRecette = resultSet.getInt("id_recette");
                int idResto = resultSet.getInt("id_restaurant");
                int tempsPrepa = resultSet.getInt("temps_preparation");
                int nbCouverts = resultSet.getInt("nombre_couverts");
                String type = resultSet.getString("type_recette");
                String name = resultSet.getString("name");
                int createurId = resultSet.getInt("id_createur");
                Boolean vege = resultSet.getBoolean("vege");
                String preparation = resultSet.getString("preparation");
                Double moyenne = resultSet.getDouble("moyenne");
                Utilisateur createur = daoutilisateur.find(createurId);
                recettes.add(new Recette(idRecette, idResto, tempsPrepa, nbCouverts, name, type, vege,
                preparation, moyenne, createur));
            }
            resultSet.close();
            statement.close();
            connection.close();
            return recettes;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void delete(int id) {
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

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public Recette insert(Recette newrecette) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres",
                "Nathan17340!")) {
    
            // Préparer la requête SQL d'insertion
            String sql = "INSERT INTO recette (id_restaurant, temps_preparation, nombre_couverts, type_recette, name, id_createur, preparation) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            DAOUtilisateur daoutilisateur = new DAOUtilisateur();
    
            pstmt.setInt(1, newrecette.getRestoId());
            pstmt.setInt(2, newrecette.getPrepareTime());
            pstmt.setInt(3, newrecette.getNombreCouverts());
            pstmt.setString(4, newrecette.getTypeRecette());
            pstmt.setString(5, newrecette.getName());
            
            // Récupérer l'objet Utilisateur correspondant à l'identifiant du créateur
            Utilisateur createur = newrecette.getCreateur();
            if (createur == null) {
                throw new SQLException("L'utilisateur de la recette n'a pas été trouvé.");
            }
            pstmt.setInt(6, createur.getId());
    
            pstmt.setString(7, newrecette.getPreparation());
    
            // Exécuter la requête SQL d'insertion et récupérer l'identifiant de la nouvelle recette
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
    

    // int id, int restoId, int prepareTime, int nombreCouverts, String name,
    // String typeRecette, boolean vegeOuPas, String preparation,
    // Double moyenne, Utilisateur createur

    public Recette getRecetteById(String id) {
        String query = "SELECT * FROM recette WHERE name = ?";
        Recette recette = new Recette(0, 0, 0, 0, query, query, true, query, 0.0, null);

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres",
                "Nathan17340!");
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, recette.getName());
            DAOUtilisateur daoutilisateur = new DAOUtilisateur();

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idRecette = rs.getInt("id_recette");
                int idResto = rs.getInt("id_restaurant");
                int tempsPrepa = rs.getInt("temps_preparation");
                int nbCouverts = rs.getInt("nombre_couverts");
                String type = rs.getString("type_recette");
                String name = rs.getString("name");
                int createurId = rs.getInt("id_createur");
                Boolean vege = rs.getBoolean("vege");
                String preparation = rs.getString("preparation");
                Double moyenne = rs.getDouble("moyenne");
                Utilisateur createur = daoutilisateur.find(createurId);
                recette = new Recette(idRecette, idResto, tempsPrepa, nbCouverts, name, type, vege,
                preparation, moyenne, createur);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return recette;
    }

    public ArrayList<Recette> findByRestoId(Restaurant restaurant) {
        ArrayList<Recette> recettes = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                    "postgres", "Nathan17340!");
            String query = "SELECT * FROM recette WHERE id_restaurant = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, restaurant.getId());
            ResultSet resultSet = statement.executeQuery();
            DAOUtilisateur daoutilisateur = new DAOUtilisateur();


            while (resultSet.next()) {
                int idRecette = resultSet.getInt("id_recette");
                int idResto = resultSet.getInt("id_restaurant");
                int tempsPrepa = resultSet.getInt("temps_preparation");
                int nbCouverts = resultSet.getInt("nombre_couverts");
                String type = resultSet.getString("type_recette");
                String name = resultSet.getString("name");
                int createurId = resultSet.getInt("id_createur");
                Boolean vege = resultSet.getBoolean("vege");
                String preparation = resultSet.getString("preparation");
                Double moyenne = resultSet.getDouble("moyenne");
                Utilisateur createur = daoutilisateur.find(createurId);

                Recette recette = new Recette(idRecette, idResto, tempsPrepa, nbCouverts, name, type, vege,
                        preparation, moyenne, createur);
                recettes.add(recette);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recettes;
    }

}
