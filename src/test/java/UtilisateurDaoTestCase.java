import daos.UtilisateurDao;
import org.junit.Before;
import org.junit.Test;
import pojos.Utilisateur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

public class UtilisateurDaoTestCase {

    private UtilisateurDao utilisateurDao = new UtilisateurDao();

    @Before
    public void initDatabase() {
        try (Connection connection = utilisateurDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM photo");
            stmt.executeUpdate("DELETE FROM annonce");
            stmt.executeUpdate("DELETE FROM categorie");
            stmt.executeUpdate("DELETE FROM utilisateur");
            stmt.executeUpdate("INSERT INTO `utilisateur`(`idUtilisateur`, `nomUtilisateur`, `prenomUtilisateur`, `telephoneUtilisateur`, `mailUtilisateur`, `promoUtilisateur`, `administrateur`, `mdpUtilisateur`) VALUES (1,'Nom #1', 'Prenom #1', 'Tel #1', 'Mail #1', 'H1', false, 'Mdp #1')");
            stmt.executeUpdate("INSERT INTO `utilisateur`(`idUtilisateur`, `nomUtilisateur`, `prenomUtilisateur`, `telephoneUtilisateur`, `mailUtilisateur`, `promoUtilisateur`, `administrateur`, `mdpUtilisateur`) VALUES (2,'Nom #2', 'Prenom #2', 'Tel #2', 'Mail #2', 'H2', false, 'Mdp #2')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shoulListUtilisateurs() {
        //WHEN
        List<Utilisateur> utilisateurs = utilisateurDao.listUtilisateur();
        //THEN
        assertThat(utilisateurs).hasSize(2);
        assertThat(utilisateurs).extracting("idUtilisateur", "nomUtilisateur", "prenomUtilisateur", "telephoneUtilisateur", "mailUtilisateur", "promoUtilisateur", "administrateur", "mdpUtilisateur").containsOnly(
                tuple(1,"Nom #1", "Prenom #1", "Tel #1", "Mail #1", "H1", false, "Mdp #1"),
                tuple(2,"Nom #2", "Prenom #2", "Tel #2", "Mail #2", "H2",false, "Mdp #2")
        );
    }

    @Test
    public void shouldGetUtilisateur() {
        // WHEN
        Utilisateur utilisateur = utilisateurDao.getUtilisateur("Mail #1", "Mdp #1");
        // THEN
        assertThat(utilisateur).isNotNull();
        assertThat(utilisateur.getIdUtilisateur()).isEqualTo(1);
        assertThat(utilisateur.getNomUtilisateur()).isEqualTo("Nom #1");
        assertThat(utilisateur.getPrenomUtilisateur()).isEqualTo("Prenom #1");
        assertThat(utilisateur.getTelephoneUtilisateur()).isEqualTo("Tel #1");
        assertThat(utilisateur.getMailUtilisateur()).isEqualTo("Mail #1");
        assertThat(utilisateur.getPromoUtilisateur()).isEqualTo("H1");
        assertThat(utilisateur.getAdministrateur()).isEqualTo(false);
        assertThat(utilisateur.getMdpUtilisateur()).isEqualTo("Mdp #1");
    }

    @Test
    public void shouldAddUtilisateur() throws SQLException {
        //GIVEN
        Utilisateur newUtilisateur = new Utilisateur(null, "New Name", "New Firstname", "New Phone", "New Mail", "NP", false, "New Mdp");
        //WHEN
        Utilisateur createdUtilisateur = utilisateurDao.addUtilisateur(newUtilisateur);
        // THEN
        assertThat(createdUtilisateur).isNotNull();
        assertThat(createdUtilisateur.getIdUtilisateur()).isNotNull();
        assertThat(createdUtilisateur.getIdUtilisateur()).isGreaterThan(0);
        assertThat(createdUtilisateur.getNomUtilisateur()).isEqualTo("New Name");
        assertThat(createdUtilisateur.getPrenomUtilisateur()).isEqualTo("New Firstname");
        assertThat(createdUtilisateur.getTelephoneUtilisateur()).isEqualTo("New Phone");
        assertThat(createdUtilisateur.getMailUtilisateur()).isEqualTo("New Mail");
        assertThat(createdUtilisateur.getPromoUtilisateur()).isEqualTo("NP");
        assertThat(createdUtilisateur.getAdministrateur()).isEqualTo(false);
        assertThat(createdUtilisateur.getMdpUtilisateur()).isEqualTo("New Mdp");
        try (Connection connection = utilisateurDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM utilisateur WHERE nomUtilisateur = 'New Name'")) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getInt("idUtilisateur")).isEqualTo(createdUtilisateur.getIdUtilisateur());
                assertThat(rs.getString("nomUtilisateur")).isEqualTo("New Name");
                assertThat(rs.getString("prenomUtilisateur")).isEqualTo("New Firstname");
                assertThat(rs.getString("telephoneUtilisateur")).isEqualTo("New Phone");
                assertThat(rs.getString("mailUtilisateur")).isEqualTo("New Mail");
                assertThat(rs.getString("promoUtilisateur")).isEqualTo("NP");
                assertThat(rs.getBoolean("administrateur")).isEqualTo(false);
                assertThat(rs.getString("mdpUtilisateur")).isEqualTo("New Mdp");
                assertThat(rs.next()).isFalse();
            }
        }
    }

    @Test
    public void shouldModifMdpUtilisateur(){
        //WHEN
        utilisateurDao.modifMdpUtilisateur(1, "Mdp Modif");
        //THEN
        try (Connection connection = utilisateurDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM utilisateur WHERE idUtilisateur = 1")) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getString("mdpUtilisateur")).isEqualTo("Mdp Modif");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldModifPromoUtilisateur(){
        //WHEN
        utilisateurDao.modifPromoUtilisateur(1, "NP");
        //THEN
        try (Connection connection = utilisateurDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM utilisateur WHERE idUtilisateur = 1")) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getString("promoUtilisateur")).isEqualTo("NP");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldAddTelephoneUtilisateur(){
        //WHEN
        utilisateurDao.addTelephoneUtilisateur(1, "New Phone");
        //THEN
        try (Connection connection = utilisateurDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM utilisateur WHERE idUtilisateur = 1")) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getString("telephoneUtilisateur")).isEqualTo("New Phone");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
