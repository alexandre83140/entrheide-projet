import daos.AnnonceDao;
import org.junit.Before;
import org.junit.Test;
import pojos.Annonce;
import pojos.Categorie;
import pojos.Utilisateur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

public class AnnonceDaoTestCase {

    private AnnonceDao annonceDao = new AnnonceDao();

    @Before
    public void initDatabase() {
        try (Connection connection = annonceDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM annonce");
            stmt.executeUpdate("DELETE FROM categorie");
            stmt.executeUpdate("DELETE FROM utilisateur");
            stmt.executeUpdate("INSERT INTO categorie(idCategorie, nomCategorie) VALUES (1, 'Nom #1')");
            stmt.executeUpdate("INSERT INTO categorie(idCategorie, nomCategorie) VALUES (2, 'Nom #2')");
            stmt.executeUpdate("INSERT INTO utilisateur(`idUtilisateur`, `nomUtilisateur`, `prenomUtilisateur`, `telephoneUtilisateur`, `mailUtilisateur`, `promoUtilisateur`, `administrateur`, `mdpUtilisateur`) VALUES (1,'Nom #1', 'Prenom #1', 'Tel #1', 'Mail #1', 'H1', 0, 'Mdp #1')");
            stmt.executeUpdate("INSERT INTO utilisateur(`idUtilisateur`, `nomUtilisateur`, `prenomUtilisateur`, `telephoneUtilisateur`, `mailUtilisateur`, `promoUtilisateur`, `administrateur`, `mdpUtilisateur`) VALUES (2,'Nom #2', 'Prenom #2', 'Tel #2', 'Mail #2', 'H2', 0, 'Mdp #2')");
            stmt.executeUpdate("INSERT INTO annonce (idAnnonce, titreAnnonce, descriptionAnnonce, motsClefsAnnonce, dateAnnonce, evenement, signalee, idCategorie, idUtilisateur) VALUES (1,'Titre #1', 'Describ #1', 'Mots #1', '2018-01-01', false, true, 1, 1)");
            stmt.executeUpdate("INSERT INTO annonce (idAnnonce, titreAnnonce, descriptionAnnonce, motsClefsAnnonce, dateAnnonce, evenement, signalee, idCategorie, idUtilisateur) VALUES (2,'Titre #2', 'Describ #2', 'Mots #2', '2018-02-02', false, false,2, 2)");
            stmt.executeUpdate("INSERT INTO annonce (idAnnonce, titreAnnonce, descriptionAnnonce, motsClefsAnnonce, dateAnnonce, evenement, signalee, idCategorie, idUtilisateur) VALUES (3,'Titre #3', 'Describ #3', 'Mots #3', '2018-03-03', true, false, 1, 2)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shoulListAnnonces() {
        //WHEN
        List<Annonce> annonces = annonceDao.listAnnonce();
        //THEN
        assertThat(annonces).hasSize(3);
        assertThat(annonces).extracting("idAnnonce", "titreAnnonce", "descriptionAnnonce", "motsClefsAnnonce", "dateAnnonce", "evenement", "signalee", "categorieAnnonce.idCategorie", "categorieAnnonce.nomCategorie", "utilisateurAnnonce.idUtilisateur", "utilisateurAnnonce.nomUtilisateur", "utilisateurAnnonce.prenomUtilisateur", "utilisateurAnnonce.telephoneUtilisateur", "utilisateurAnnonce.mailUtilisateur", "utilisateurAnnonce.promoUtilisateur", "utilisateurAnnonce.administrateur", "utilisateurAnnonce.mdpUtilisateur").containsOnly(
                tuple(1, "Titre #1", "Describ #1", "Mots #1", LocalDate.of(2018, 01, 01), false, true, 1, "Nom #1", 1, "Nom #1", "Prenom #1", "Tel #1", "Mail #1", "H1", 0, "Mdp #1"),
                tuple(2, "Titre #2", "Describ #2", "Mots #2", LocalDate.of(2018, 02, 02), false, false, 2, "Nom #2", 2, "Nom #2", "Prenom #2", "Tel #2", "Mail #2", "H2", 0, "Mdp #2"),
                tuple(3, "Titre #3", "Describ #3", "Mots #3", LocalDate.of(2018, 03, 03), true, false, 1, "Nom #1", 2, "Nom #2", "Prenom #2", "Tel #2", "Mail #2", "H2", 0, "Mdp #2")
        );
    }

    @Test
    public void shouldGetAnnonce() {
        // WHEN
        Annonce annonce = annonceDao.getAnnonce(1);
        // THEN
        assertThat(annonce).isNotNull();
        assertThat(annonce.getIdAnnonce()).isEqualTo(1);
        assertThat(annonce.getTitreAnnonce()).isEqualTo("Titre #1");
        assertThat(annonce.getDescriptionAnnonce()).isEqualTo("Describ #1");
        assertThat(annonce.getMotsClefsAnnonce()).isEqualTo("Mots #1");
        assertThat(annonce.getDateAnnonce()).isEqualTo(LocalDate.of(2018, 01, 01));
        assertThat(annonce.getEvenement()).isEqualTo(false);
        assertThat(annonce.getSignalee()).isEqualTo(true);
        assertThat(annonce.getCategorieAnnonce().getIdCategorie()).isEqualTo(1);
        assertThat(annonce.getCategorieAnnonce().getNomCategorie()).isEqualTo("Nom #1");
        assertThat(annonce.getUtilisateurAnnonce().getIdUtilisateur()).isEqualTo(1);
        assertThat(annonce.getUtilisateurAnnonce().getNomUtilisateur()).isEqualTo("Nom #1");
        assertThat(annonce.getUtilisateurAnnonce().getPrenomUtilisateur()).isEqualTo("Prenom #1");
        assertThat(annonce.getUtilisateurAnnonce().getTelephoneUtilisateur()).isEqualTo("Tel #1");
        assertThat(annonce.getUtilisateurAnnonce().getMailUtilisateur()).isEqualTo("Mail #1");
        assertThat(annonce.getUtilisateurAnnonce().getPromoUtilisateur()).isEqualTo("H1");
        assertThat(annonce.getUtilisateurAnnonce().getAdministrateur()).isEqualTo(0);
        assertThat(annonce.getUtilisateurAnnonce().getMdpUtilisateur()).isEqualTo("Mdp #1");
    }

    @Test
    public void shouldAddAnnonce() throws SQLException {
        //GIVEN
        Annonce newAnnonce = new Annonce(null, "New Titre", "New Description", "New Mots", LocalDate.of(2018, 12, 12), false, false, new Categorie(1, "New Name"), new Utilisateur(1, "New Nom", "New Prenom", "New Phone", "New Mail", "NP", 0, "New Mdp"));
        //WHEN
        Annonce createdAnnonce = annonceDao.addAnnonce(newAnnonce);
        // THEN
        assertThat(createdAnnonce).isNotNull();
        assertThat(createdAnnonce.getIdAnnonce()).isNotNull();
        assertThat(createdAnnonce.getIdAnnonce()).isGreaterThan(0);
        assertThat(createdAnnonce.getTitreAnnonce()).isEqualTo("New Titre");
        assertThat(createdAnnonce.getDescriptionAnnonce()).isEqualTo("New Description");
        assertThat(createdAnnonce.getMotsClefsAnnonce()).isEqualTo("New Mots");
        assertThat(createdAnnonce.getDateAnnonce()).isEqualTo(LocalDate.of(2018, 12, 12));
        assertThat(createdAnnonce.getEvenement()).isEqualTo(false);
        assertThat(createdAnnonce.getSignalee()).isEqualTo(false);
        assertThat(createdAnnonce.getCategorieAnnonce().getIdCategorie()).isEqualTo(1);
        assertThat(createdAnnonce.getCategorieAnnonce().getNomCategorie()).isEqualTo("New Name");
        assertThat(createdAnnonce.getUtilisateurAnnonce().getIdUtilisateur()).isEqualTo(1);
        assertThat(createdAnnonce.getUtilisateurAnnonce().getNomUtilisateur()).isEqualTo("New Nom");
        assertThat(createdAnnonce.getUtilisateurAnnonce().getPrenomUtilisateur()).isEqualTo("New Prenom");
        assertThat(createdAnnonce.getUtilisateurAnnonce().getTelephoneUtilisateur()).isEqualTo("New Phone");
        assertThat(createdAnnonce.getUtilisateurAnnonce().getMailUtilisateur()).isEqualTo("New Mail");
        assertThat(createdAnnonce.getUtilisateurAnnonce().getPromoUtilisateur()).isEqualTo("NP");
        assertThat(createdAnnonce.getUtilisateurAnnonce().getAdministrateur()).isEqualTo(0);
        assertThat(createdAnnonce.getUtilisateurAnnonce().getMdpUtilisateur()).isEqualTo("New Mdp");
        try (Connection connection = annonceDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM annonce WHERE titreAnnonce = 'New Titre'")) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getInt("idAnnonce")).isEqualTo(createdAnnonce.getIdAnnonce());
                assertThat(rs.getString("titreAnnonce")).isEqualTo("New Titre");
                assertThat(rs.getString("descriptionAnnonce")).isEqualTo("New Description");
                assertThat(rs.getString("motsClefsAnnonce")).isEqualTo("New Mots");
                assertThat(rs.getDate("dateAnnonce").toLocalDate()).isEqualTo(LocalDate.of(2018, 12, 12));
                assertThat(rs.getBoolean("evenement")).isEqualTo(false);
                assertThat(rs.getBoolean("signalee")).isEqualTo(false);
                assertThat(rs.getInt("idCategorie")).isEqualTo(1);
                assertThat(rs.getInt("idUtilisateur")).isEqualTo(1);

                assertThat(rs.next()).isFalse();
            }
        }
    }

    @Test
    public void shoulListAnnoncesByCategorie() {
        //WHEN
        List<Annonce> annonces = annonceDao.listAnnonceByCategorie(1);
        //THEN
        assertThat(annonces).hasSize(2);
        assertThat(annonces).extracting("idAnnonce", "titreAnnonce", "descriptionAnnonce", "motsClefsAnnonce", "dateAnnonce", "evenement", "signalee", "categorieAnnonce.idCategorie", "categorieAnnonce.nomCategorie", "utilisateurAnnonce.idUtilisateur", "utilisateurAnnonce.nomUtilisateur", "utilisateurAnnonce.prenomUtilisateur", "utilisateurAnnonce.telephoneUtilisateur", "utilisateurAnnonce.mailUtilisateur", "utilisateurAnnonce.promoUtilisateur", "utilisateurAnnonce.administrateur", "utilisateurAnnonce.mdpUtilisateur").containsOnly(
                tuple(1, "Titre #1", "Describ #1", "Mots #1", LocalDate.of(2018, 01, 01), false, true, 1, "Nom #1", 1, "Nom #1", "Prenom #1", "Tel #1", "Mail #1", "H1", 0, "Mdp #1"),
                tuple(3, "Titre #3", "Describ #3", "Mots #3", LocalDate.of(2018, 03, 03), true, false, 1, "Nom #1", 2, "Nom #2", "Prenom #2", "Tel #2", "Mail #2", "H2", 0, "Mdp #2")
        );
    }


    @Test
    public void shoulListAnnoncesByMotsClefs() {
        //WHEN
        List<Annonce> annonces = annonceDao.listAnnonceByMotsClefs("Mots #1");
        //THEN
        assertThat(annonces).hasSize(1);
        assertThat(annonces).extracting("idAnnonce", "titreAnnonce", "descriptionAnnonce", "motsClefsAnnonce", "dateAnnonce", "evenement", "signalee", "categorieAnnonce.idCategorie", "categorieAnnonce.nomCategorie", "utilisateurAnnonce.idUtilisateur", "utilisateurAnnonce.nomUtilisateur", "utilisateurAnnonce.prenomUtilisateur", "utilisateurAnnonce.telephoneUtilisateur", "utilisateurAnnonce.mailUtilisateur", "utilisateurAnnonce.promoUtilisateur", "utilisateurAnnonce.administrateur", "utilisateurAnnonce.mdpUtilisateur").containsOnly(
                tuple(1, "Titre #1", "Describ #1", "Mots #1", LocalDate.of(2018, 01, 01), false, true, 1, "Nom #1", 1, "Nom #1", "Prenom #1", "Tel #1", "Mail #1", "H1", 0, "Mdp #1")
        );
    }

    @Test
    public void shoulListAnnoncesByUtilisateur() {
        //WHEN
        List<Annonce> annonces = annonceDao.listAnnonceByUtilisateur("Mail #1");
        //THEN
        assertThat(annonces).hasSize(1);
        assertThat(annonces).extracting("idAnnonce", "titreAnnonce", "descriptionAnnonce", "motsClefsAnnonce", "dateAnnonce", "evenement", "signalee", "categorieAnnonce.idCategorie", "categorieAnnonce.nomCategorie", "utilisateurAnnonce.idUtilisateur", "utilisateurAnnonce.nomUtilisateur", "utilisateurAnnonce.prenomUtilisateur", "utilisateurAnnonce.telephoneUtilisateur", "utilisateurAnnonce.mailUtilisateur", "utilisateurAnnonce.promoUtilisateur", "utilisateurAnnonce.administrateur", "utilisateurAnnonce.mdpUtilisateur").containsOnly(
                tuple(1, "Titre #1", "Describ #1", "Mots #1", LocalDate.of(2018, 01, 01), false, true, 1, "Nom #1", 1, "Nom #1", "Prenom #1", "Tel #1", "Mail #1", "H1", 0, "Mdp #1")
        );
    }

    @Test
    public void shouldModifAnnonce() {
        // WHEN
        annonceDao.modifAnnonce(1, "Titre Modif", "Description Modif", "Mots Modif", 2);
        //THEN
        try (Connection connection = annonceDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM annonce WHERE idAnnonce = 1")) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getString("titreAnnonce")).isEqualTo("Titre Modif");
                assertThat(rs.getString("descriptionAnnonce")).isEqualTo("Description Modif");
                assertThat(rs.getString("motsClefsAnnonce")).isEqualTo("Mots Modif");
                assertThat(rs.getInt("idCategorie")).isEqualTo(2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void shouldDeleteAnnonce() {
        //WHEN
        annonceDao.deleteAnnonce("Titre #2");
        //THEN
        try (Connection connection = annonceDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM annonce WHERE titreAnnonce = 'Titre #2'")) {
                assertThat(rs.getInt("idAnnonce")).isNull();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shoulListEvenements() {
        //WHEN
        List<Annonce> annonces = annonceDao.listEvenement();
        //THEN
        assertThat(annonces).hasSize(1);
        assertThat(annonces).extracting("idAnnonce", "titreAnnonce", "descriptionAnnonce", "motsClefsAnnonce", "dateAnnonce", "evenement", "signalee", "categorieAnnonce.idCategorie", "categorieAnnonce.nomCategorie", "utilisateurAnnonce.idUtilisateur", "utilisateurAnnonce.nomUtilisateur", "utilisateurAnnonce.prenomUtilisateur", "utilisateurAnnonce.telephoneUtilisateur", "utilisateurAnnonce.mailUtilisateur", "utilisateurAnnonce.promoUtilisateur", "utilisateurAnnonce.administrateur", "utilisateurAnnonce.mdpUtilisateur").containsOnly(
                tuple(3, "Titre #3", "Describ #3", "Mots #3", LocalDate.of(2018, 03, 03), true, false, 1, "Nom #1", 2, "Nom #2", "Prenom #2", "Tel #2", "Mail #2", "H2", 0, "Mdp #2")
        );
    }

    @Test
    public void shoulListAnnoncesSignalees() {
        //WHEN
        List<Annonce> annonces = annonceDao.listAnnoncesSignalees();
        //THEN
        assertThat(annonces).hasSize(1);
        assertThat(annonces).extracting("idAnnonce", "titreAnnonce", "descriptionAnnonce", "motsClefsAnnonce", "dateAnnonce", "evenement", "signalee", "categorieAnnonce.idCategorie", "categorieAnnonce.nomCategorie", "utilisateurAnnonce.idUtilisateur", "utilisateurAnnonce.nomUtilisateur", "utilisateurAnnonce.prenomUtilisateur", "utilisateurAnnonce.telephoneUtilisateur", "utilisateurAnnonce.mailUtilisateur", "utilisateurAnnonce.promoUtilisateur", "utilisateurAnnonce.administrateur", "utilisateurAnnonce.mdpUtilisateur").containsOnly(
                tuple(1, "Titre #1", "Describ #1", "Mots #1", LocalDate.of(2018, 01, 01), false, true, 1, "Nom #1", 1, "Nom #1", "Prenom #1", "Tel #1", "Mail #1", "H1", 0, "Mdp #1")
        );
    }

    @Test
    public void shouldModifAnnonceSignalee() {
        // WHEN
        annonceDao.modifAnnonceSignalee(2);
        //THEN
        try (Connection connection = annonceDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM annonce WHERE idAnnonce = 2")) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getBoolean("titreAnnonce")).isTrue();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
