package daos;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import pojos.Annonce;
import pojos.Categorie;
import pojos.Utilisateur;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnonceDao {

    public DataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("entrheide");
        dataSource.setUser("root");
        dataSource.setPassword("");
        return dataSource;
    }

    public List<Annonce> listAnnonce() {
        String query = "SELECT * FROM (annonce INNER JOIN utilisateur ON utilisateur.idUtilisateur = annonce.idUtilisateur) INNER JOIN categorie ON categorie.idCategorie = annonce.idCategorie ";
        List<Annonce> annonces = new ArrayList<>();
        try (
                Connection connection = getDataSource().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                annonces.add(
                        new Annonce(
                                resultSet.getInt("idAnnonce"),
                                resultSet.getString("titreAnnonce"),
                                resultSet.getString("descriptionAnnonce"),
                                resultSet.getString("motsClefsAnnonce"),
                                resultSet.getDate("dateAnnonce").toLocalDate(),
                                resultSet.getBoolean("evenement"),
                                new Categorie(resultSet.getInt("idCategorie"), resultSet.getString("nomCategorie")),
                                new Utilisateur(resultSet.getInt("idUtilisateur"), resultSet.getString("nomUtilisateur"), resultSet.getString("prenomUtilisateur"), resultSet.getString("telephoneUtilisateur"), resultSet.getString("mailUtilisateur"), resultSet.getString("promoUtilisateur"), resultSet.getBoolean("administrateur"), resultSet.getString("mdpUtilisateur"))
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return annonces;
    }

    public Annonce getAnnonce(Integer idAnnonce) {
        String query = "SELECT * FROM (annonce INNER JOIN utilisateur ON utilisateur.idUtilisateur = annonce.idUtilisateur) INNER JOIN categorie ON categorie.idCategorie = annonce.idCategorie WHERE idAnnonce=?";
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idAnnonce);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Annonce(
                            resultSet.getInt("idAnnonce"),
                            resultSet.getString("titreAnnonce"),
                            resultSet.getString("descriptionAnnonce"),
                            resultSet.getString("motsClefsAnnonce"),
                            resultSet.getDate("dateAnnonce").toLocalDate(),
                            resultSet.getBoolean("evenement"),
                            new Categorie(resultSet.getInt("idCategorie"), resultSet.getString("nomCategorie")),
                            new Utilisateur(resultSet.getInt("idUtilisateur"), resultSet.getString("nomUtilisateur"), resultSet.getString("prenomUtilisateur"), resultSet.getString("telephoneUtilisateur"), resultSet.getString("mailUtilisateur"), resultSet.getString("promoUtilisateur"), resultSet.getBoolean("administrateur"), resultSet.getString("mdpUtilisateur"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Annonce addAnnonce(Annonce annonce) {
        String query = "INSERT INTO annonce(titreAnnonce, descriptionAnnonce, motsClefsAnnonce, dateAnnonce, evenement, idCategorie, idUtilisateur) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, annonce.getTitreAnnonce());
            statement.setString(2, annonce.getDescriptionAnnonce());
            statement.setString(3, annonce.getMotsClefsAnnonce());
            statement.setDate(4, Date.valueOf(annonce.getDateAnnonce()));
            statement.setBoolean(5, annonce.getEvenement());
            statement.setInt(6, annonce.getCategorieAnnonce().getIdCategorie());
            statement.setInt(7, annonce.getUtilisateurAnnonce().getIdUtilisateur());
            statement.executeUpdate();
            try (ResultSet ids = statement.getGeneratedKeys()) {
                if (ids.next()) {
                    int generatedId = ids.getInt(1);
                    annonce.setIdAnnonce(generatedId);
                    return annonce;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Annonce> listAnnonceByCategorie(String nomCategorie) {
        String query = "SELECT * FROM (annonce INNER JOIN utilisateur ON utilisateur.idUtilisateur = annonce.idUtilisateur) INNER JOIN categorie ON categorie.idCategorie = annonce.idCategorie WHERE categorie.nomCategorie LIKE ?";
        List<Annonce> annonces = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nomCategorie);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    annonces.add(
                            new Annonce(
                                    resultSet.getInt("idAnnonce"),
                                    resultSet.getString("titreAnnonce"),
                                    resultSet.getString("descriptionAnnonce"),
                                    resultSet.getString("motsClefsAnnonce"),
                                    resultSet.getDate("dateAnnonce").toLocalDate(),
                                    resultSet.getBoolean("evenement"),
                                    new Categorie(resultSet.getInt("idCategorie"), resultSet.getString("nomCategorie")),
                                    new Utilisateur(resultSet.getInt("idUtilisateur"), resultSet.getString("nomUtilisateur"), resultSet.getString("prenomUtilisateur"), resultSet.getString("telephoneUtilisateur"), resultSet.getString("mailUtilisateur"), resultSet.getString("promoUtilisateur"), resultSet.getBoolean("administrateur"), resultSet.getString("mdpUtilisateur"))
                            ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return annonces;
    }

    public List<Annonce> listAnnonceByMotsClefs(String motsClefsAnnonce) {
        String query = "SELECT * FROM (annonce INNER JOIN utilisateur ON utilisateur.idUtilisateur = annonce.idUtilisateur) INNER JOIN categorie ON categorie.idCategorie = annonce.idCategorie WHERE annonce.motsClefsAnnonce LIKE ?";
        List<Annonce> annonces = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, motsClefsAnnonce);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    annonces.add(
                            new Annonce(
                                    resultSet.getInt("idAnnonce"),
                                    resultSet.getString("titreAnnonce"),
                                    resultSet.getString("descriptionAnnonce"),
                                    resultSet.getString("motsClefsAnnonce"),
                                    resultSet.getDate("dateAnnonce").toLocalDate(),
                                    resultSet.getBoolean("evenement"),
                                    new Categorie(resultSet.getInt("idCategorie"), resultSet.getString("nomCategorie")),
                                    new Utilisateur(resultSet.getInt("idUtilisateur"), resultSet.getString("nomUtilisateur"), resultSet.getString("prenomUtilisateur"), resultSet.getString("telephoneUtilisateur"), resultSet.getString("mailUtilisateur"), resultSet.getString("promoUtilisateur"), resultSet.getBoolean("administrateur"), resultSet.getString("mdpUtilisateur"))
                            ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return annonces;
    }

    public List<Annonce> listAnnonceByUtilisateur(String mailUtilisateur) {
        String query = "SELECT * FROM (annonce INNER JOIN utilisateur ON utilisateur.idUtilisateur = annonce.idUtilisateur) INNER JOIN categorie ON categorie.idCategorie = annonce.idCategorie WHERE utilisateur.mailUtilisateur LIKE ?";
        List<Annonce> annonces = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mailUtilisateur);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    annonces.add(
                            new Annonce(
                                    resultSet.getInt("idAnnonce"),
                                    resultSet.getString("titreAnnonce"),
                                    resultSet.getString("descriptionAnnonce"),
                                    resultSet.getString("motsClefsAnnonce"),
                                    resultSet.getDate("dateAnnonce").toLocalDate(),
                                    resultSet.getBoolean("evenement"),
                                    new Categorie(resultSet.getInt("idCategorie"), resultSet.getString("nomCategorie")),
                                    new Utilisateur(resultSet.getInt("idUtilisateur"), resultSet.getString("nomUtilisateur"), resultSet.getString("prenomUtilisateur"), resultSet.getString("telephoneUtilisateur"), resultSet.getString("mailUtilisateur"), resultSet.getString("promoUtilisateur"), resultSet.getBoolean("administrateur"), resultSet.getString("mdpUtilisateur"))
                            ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return annonces;
    }

    public void modifAnnonce(Integer idAnnonce, String newTitre, String newDescription, String newMotsClefs, Integer newIdCategorie){
       String query = "UPDATE annonce SET titreAnnonce=?, descriptionAnnonce=?, motsClefsAnnonce=?, idCategorie=? WHERE idAnnonce=?";
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,newTitre);
            statement.setString(2,newDescription);
            statement.setString(3,newMotsClefs);
            statement.setInt(4,newIdCategorie);
            statement.setInt(5,idAnnonce);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAnnonce(Integer idAnnonce){
        String query = "DELETE FROM annonce WHERE idAnnonce=?";
        try(Connection connection = getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1,idAnnonce);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

