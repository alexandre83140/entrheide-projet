package daos;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import pojos.Utilisateur;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDao {

    public DataSource getDataSource(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("entrheide");
        dataSource.setUser("root");
        dataSource.setPassword("");
        return dataSource;
    }

    public List<Utilisateur> listUtilisateur(){
        String query = "SELECT * FROM utilisateur";
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try(
                Connection connection = getDataSource().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ){
            while(resultSet.next()) {
                utilisateurs.add(
                        new Utilisateur(
                                resultSet.getInt("idUtilisateur"),
                                resultSet.getString("nomUtilisateur"),
                                resultSet.getString("prenomUtilisateur"),
                                resultSet.getString("telephoneUtilisateur"),
                                resultSet.getString("mailUtilisateur"),
                                resultSet.getString("promoUtilisateur"),
                                resultSet.getBoolean("administrateur"),
                                resultSet.getString("mdpUtilisateur")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    public Utilisateur getUtilisateur(String mailUtilisateur, String mdpUtilisateur) {
        String query = "SELECT * FROM utilisateur WHERE mailUtilisateur=? AND mdpUtilisateur=?";
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mailUtilisateur);
            statement.setString(2, mdpUtilisateur);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Utilisateur(
                            resultSet.getInt("idUtilisateur"),
                            resultSet.getString("nomUtilisateur"),
                            resultSet.getString("prenomUtilisateur"),
                            resultSet.getString("telephoneUtilisateur"),
                            resultSet.getString("mailUtilisateur"),
                            resultSet.getString("promoUtilisateur"),
                            resultSet.getBoolean("administrateur"),
                            resultSet.getString("mdpUtilisateur"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Utilisateur addUtilisateur(Utilisateur utilisateur){
        String query = "INSERT INTO utilisateur(nomUtilisateur, prenomUtilisateur, telephoneUtilisateur, mailUtilisateur, promoUtilisateur, administrateur, mdpUtilisateur) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,utilisateur.getNomUtilisateur());
            statement.setString(2,utilisateur.getPrenomUtilisateur());
            statement.setString(3,utilisateur.getTelephoneUtilisateur());
            statement.setString(4,utilisateur.getMailUtilisateur());
            statement.setString(5,utilisateur.getPromoUtilisateur());
            statement.setBoolean(6,utilisateur.getAdministrateur());
            statement.setString(7, utilisateur.getMdpUtilisateur());
            statement.executeUpdate();

            try (ResultSet ids = statement.getGeneratedKeys()) {
                if (ids.next()) {
                    int generatedId = ids.getInt(1);
                    utilisateur.setIdUtilisateur(generatedId);
                    return utilisateur;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void modifMdpUtilisateur(Integer idUtilisateur, String newMdp){
        String query = "UPDATE utilisateur SET mdpUtilisateur = ? WHERE idUtilisateur = ?";
        try(Connection connection = getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, newMdp);
            statement.setInt(2, idUtilisateur);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifPromoUtilisateur(Integer idUtilisateur, String NP){
        String query = "UPDATE utilisateur SET promoUtilisateur = ? WHERE idUtilisateur = ?";
        try(Connection connection = getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, NP);
            statement.setInt(2, idUtilisateur);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTelephoneUtilisateur(Integer idUtilisateur, String numTelephone){
        String query = "UPDATE utilisateur SET telephoneUtilisateur = ? WHERE idUtilisateur = ?";
        try(Connection connection = getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, numTelephone);
            statement.setInt(2, idUtilisateur);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
