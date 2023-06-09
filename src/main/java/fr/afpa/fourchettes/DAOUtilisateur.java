package fr.afpa.fourchettes;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUtilisateur extends DAO<Utilisateur> {

    @Override
    public Utilisateur find(int id) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                    "postgres",
                    "Nathan17340!");
            Statement stm;
            stm = connection.createStatement();
            String selectUsers = "select * from utilisateur where id_user = " + id;
            ResultSet resultat = stm.executeQuery(selectUsers);
            String resultString = "";
            while (resultat.next()) {

                int idUser = resultat.getInt("id_user");
                String identifiant = resultat.getString("identifiant");
                int idresto = resultat.getInt("id_restaurant");
                String lastName = resultat.getString("last_name");
                String firstName = resultat.getString("first_name");
                String password = resultat.getString("password");

                resultString = resultString + "-" + id;
                Utilisateur utilisateur = new Utilisateur(idUser, identifiant, idresto, lastName, firstName, password);
                return utilisateur;
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

    public ArrayList<Utilisateur> findByRecette(Recette recette) {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres", "Nathan17340!");
            String query = "SELECT * FROM utilisateur WHERE id_recette = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, recette.getId());
            ResultSet resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                int idUser = resultSet.getInt("id_user");
                String identifiant = resultSet.getString("identifiant");
                int idresto = resultSet.getInt("id_restaurant");
                String lastName = resultSet.getString("last_name");
                String firstName = resultSet.getString("first_name");
                String password = resultSet.getString("password");
    
                Utilisateur utilisateur = new Utilisateur(idUser, identifiant, idresto, lastName, firstName, password);
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    @Override
    public ArrayList<Utilisateur> findAll() {
        try {
            ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                    "postgres", "Nathan17340!");
            Statement stm;
            stm = connection.createStatement();
            String selectUsers = "select * from utilisateur";
            ResultSet resultat = stm.executeQuery(selectUsers);
            String resultString = "";

            while (resultat.next()) {

                int idUser = resultat.getInt("id_user");
                String identifiant = resultat.getString("identifiant");
                int idresto = resultat.getInt("id_restaurant");
                String lastName = resultat.getString("last_name");
                String firstName = resultat.getString("first_name");
                String password = resultat.getString("password");

                resultString = resultString + "-" + utilisateurs;
                Utilisateur utilisateur = new Utilisateur(idUser, identifiant, idresto, lastName, firstName, password);
                utilisateurs.add(utilisateur);

            }
            // fermeture de la requête
            stm.close();
            resultat.close();
            connection.close();
            return utilisateurs;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;

    }

    public ArrayList<Utilisateur> findByUtilisateur(Utilisateur utilisateur) {
        try {
            ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                    "postgres", "Nathan17340!");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM utilisateur WHERE id = ?");
            statement.setInt(1, utilisateur.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                int idUser = resultSet.getInt("id_user");
                String identifiant = resultSet.getString("identifiant");
                int idresto = resultSet.getInt("id_restaurant");
                String lastName = resultSet.getString("last_name");
                String firstName = resultSet.getString("first_name");
                String password = resultSet.getString("password");

                utilisateurs.add(new Utilisateur(idUser, identifiant, idresto, lastName, firstName, password));
            }
            resultSet.close();
            statement.close();
            connection.close();
            return utilisateurs;
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM utilisateur WHERE id = ?");
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting utilisateur failed, no rows affected.");
            }
            statement.close();
            connection.close();
         
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
       
    }

    public Utilisateur insert(Utilisateur newutilisateur) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres",
                "Nathan17340!")) {

            // Préparer la requête SQL d'insertion
            String sql = "INSERT INTO utilisateur (identifiant, last_name, first_name, password, id_restaurant) VALUES ( ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, newutilisateur.getIdentifiant());
            pstmt.setString(2, newutilisateur.getLastName());
            pstmt.setString(3, newutilisateur.getFirstName());
            pstmt.setString(4, newutilisateur.getPassword());
            pstmt.setInt(5, newutilisateur.getRestoId());


            // Exécuter la requête SQL d'insertion et récupérer l'identifiant de la nouvelle
            // station
            int affectedRows = pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();

            while (res.next()) {
                int newId = res.getInt(1);
                newutilisateur.setId(newId);
            }

            if (affectedRows == 0) {
                throw new SQLException("Insertion de l'utilisateur a échoué, aucune ligne affectée.");
            }

            return newutilisateur;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utilisateur getUtilisateurById(String id) {
        String query = "SELECT * FROM utilisateur WHERE identifiant = ?";
        Utilisateur utilisateur = new Utilisateur(0, query, 0, query, query, query);

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres",
                    "Nathan17340!");
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idUser = rs.getInt("id_user");
                String identifiant = rs.getString("identifiant");
                int idresto = rs.getInt("id_restaurant");
                String lastName = rs.getString("last_name");
                String firstName = rs.getString("first_name");
                String restoName = rs.getString("resto_name");
                String password = rs.getString("password");

                utilisateur = new Utilisateur(idUser, identifiant, idresto, lastName, firstName, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return utilisateur;
    }

    public void setPhotoPath(String id, String photoPath) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres",
        "Nathan17340!");
        String query = "UPDATE utilisateur SET photoPath = ? WHERE identifiant = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, photoPath);
        statement.setString(2, id);
        statement.executeUpdate();
        connection.close();
    }

    public String getPhotoPath(String id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres",
        "Nathan17340!");
        String query = "SELECT photoPath FROM utilisateur WHERE identifiant = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        String photoPath = null;
        if (resultSet.next()) {
            photoPath = resultSet.getString("photoPath");
        }
        connection.close();
        return photoPath;
    }

    public void updateUtilisateur(Utilisateur utilisateur) throws SQLException {
        
        String sql = "UPDATE utilisateur SET password = ?, last_name = ?, first_name = ? WHERE identifiant = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres",
        "Nathan17340!");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, utilisateur.getPassword());
            preparedStatement.setString(2, utilisateur.getLastName());
            preparedStatement.setString(3, utilisateur.getFirstName());
            preparedStatement.setString(4, utilisateur.getIdentifiant());
            preparedStatement.executeUpdate();
        }
    }

}
