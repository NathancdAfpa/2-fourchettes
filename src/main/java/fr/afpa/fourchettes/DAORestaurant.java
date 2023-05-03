package fr.afpa.fourchettes;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAORestaurant extends DAO<Restaurant> {

    @Override
    public Restaurant find(int id) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                    "postgres",
                    "Nathan17340!");
            Statement stm;
            stm = connection.createStatement();
            String selectUsers = "select * from restaurant where id = " + id;
            ResultSet resultat = stm.executeQuery(selectUsers);
            String resultString = "";
            while (resultat.next()) {

                int idrestaurant = resultat.getInt("id_restaurant");
                String name = resultat.getString("name");
                String address = resultat.getString("adresse");
                String city = resultat.getString("ville");
                String tel = resultat.getString("telephone");
                String mail = resultat.getString("e_mail");
            
                resultString = resultString + "-" + id;
                Restaurant restaurant = new Restaurant(idrestaurant, name, address, city, tel, mail);
                return restaurant;
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

    public ArrayList<Restaurant> findAll() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                "postgres",
                "Nathan17340!");
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM restaurant")) {
            ArrayList<Restaurant> result = new ArrayList<>();
            while (rs.next()) {

                int idrestaurant = rs.getInt("id_restaurant");
                String name = rs.getString("name");
                String address = rs.getString("adresse");
                String city = rs.getString("ville");
                String tel = rs.getString("telephone");
                String mail = rs.getString("e_mail");

                Restaurant restaurant = new Restaurant(idrestaurant, name, address, city, tel, mail);
                result.add(restaurant);
            }
            return result;
        
        
    }catch(

    SQLException e)
    {
        e.printStackTrace();
        return null;
    }
    }

    public void delete(int id) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes",
                    "postgres", "Nathan17340!");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM restaurant WHERE id_restaurant = ?");
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting restaurant failed, no rows affected.");
            }
            statement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public Restaurant insert(Restaurant newrestaurant) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres",
                "Nathan17340!")) {

            // Préparer la requête SQL d'insertion
            String sql = "INSERT INTO restaurant (name, adresse, ville, telephone, e_mail) VALUES ( ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

          
            pstmt.setString(1, newrestaurant.getName());
            pstmt.setString(2, newrestaurant.getAdresse());
            pstmt.setString(3, newrestaurant.getVille());
            pstmt.setString(4, newrestaurant.getNumeroDeTelephone());
            pstmt.setString(5, newrestaurant.getEmail());
            // Exécuter la requête SQL d'insertion et récupérer l'identifiant de la nouvelle
            // station
            int affectedRows = pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();

            while (res.next()) {
                int newId = res.getInt(1);
                newrestaurant.setId(newId);
            }

            if (affectedRows == 0) {
                throw new SQLException("Insertion de la restaurant a échoué, aucune ligne affectée.");
            }

            return newrestaurant;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Restaurant getrestaurantById(String id) {
        String query = "SELECT * FROM restaurant WHERE name = ?";
        Restaurant restaurant = new Restaurant(0, query, query, query, query, query);

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fourchettes", "postgres",
                "Nathan17340!");
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, restaurant.getName());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idrestaurant = rs.getInt("id_restaurant");
                String name = rs.getString("name");
                String address = rs.getString("adresse");
                String city = rs.getString("ville");
                String tel = rs.getString("telephone");
                String mail = rs.getString("e_mail");
                restaurant = new Restaurant(idrestaurant, name, address, city, tel, mail);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return restaurant;
    }


}
