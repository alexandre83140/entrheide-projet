package daos;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import pojos.Annonce;
import pojos.Categorie;
import pojos.Photo;
import pojos.Utilisateur;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhotoDao {

    public DataSource getDataSource(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("entrheide");
        dataSource.setUser("root");
        dataSource.setPassword("");
        return dataSource;
    }

    public List<Photo> listPhoto(){
        String query = "SELECT * FROM photo INNER JOIN annonce ON annonce.idAnnonce = photo.idAnnonce INNER JOIN categorie ON categorie.idCategorie = annonce.idCategorie INNER JOIN utilisateur ON utilisateur.idUtilisateur = annonce.idUtilisateur  ";  //prendre tout dans le sql
        List<Photo> photos = new ArrayList<>();
        try(
                Connection connection = getDataSource().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ){
            while(resultSet.next()) {
                photos.add(
                        new Photo(
                                resultSet.getInt("idPhoto"),
                                resultSet.getString("cheminPhoto"),
                                new Annonce(
                                        resultSet.getInt("idAnnonce"),
                                        resultSet.getString("titreAnnonce"),
                                        resultSet.getString("descriptionAnnonce"),
                                        resultSet.getString("motsClefsAnnonce"),
                                        resultSet.getDate("dateAnnonce").toLocalDate(),
                                        resultSet.getBoolean("evenement"),
                                        new Categorie(resultSet.getInt("idCategorie"), resultSet.getString("nomCategorie")),
                                        new Utilisateur( resultSet.getInt("idUtilisateur"), resultSet.getString("nomUtilisateur"), resultSet.getString("prenomUtilisateur"), resultSet.getString("telephoneUtilisateur"), resultSet.getString("mailUtilisateur"), resultSet.getString("promoUtilisateur"), resultSet.getBoolean("administrateur"), resultSet.getString("mdpUtilisateur"))
                                )));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photos;
    }

    public Photo getPhoto(Integer idPhoto) {
        String query = "SELECT * FROM photo INNER JOIN annonce ON annonce.idAnnonce = photo.idAnnonce INNER JOIN categorie ON categorie.idCategorie = annonce.idCategorie INNER JOIN utilisateur ON utilisateur.idUtilisateur = annonce.idUtilisateur WHERE idPhoto=? ";
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPhoto);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Photo(
                            resultSet.getInt("idPhoto"),
                            resultSet.getString("cheminPhoto"),
                            new Annonce(resultSet.getInt("idAnnonce"), resultSet.getString("titreAnnonce"), resultSet.getString("descriptionAnnonce"), resultSet.getString("motsClefsAnnonce"), resultSet.getDate("dateAnnonce").toLocalDate(), resultSet.getBoolean("evenement"),  new Categorie( resultSet.getInt("idCategorie"), resultSet.getString("nomCategorie")), new Utilisateur (resultSet.getInt("idUtilisateur"), resultSet.getString("nomUtilisateur"), resultSet.getString("prenomUtilisateur"), resultSet.getString("telephoneUtilisateur"), resultSet.getString("mailUtilisateur"), resultSet.getString("promoUtilisateur"), resultSet.getBoolean("administrateur"), resultSet.getString("mdpUtilisateur"))))
                ;}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Photo addPhoto(Photo photo){
        String query = "INSERT INTO photo(cheminPhoto, idAnnonce) VALUES (?, ?)";
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,photo.getCheminPhoto());
            statement.setInt(2,photo.getAnnoncePhoto().getIdAnnonce());
            statement.executeUpdate();

            try (ResultSet ids = statement.getGeneratedKeys()) {
                if (ids.next()) {
                    int generatedId = ids.getInt(1);
                    photo.setIdPhoto(generatedId);
                    return photo;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
