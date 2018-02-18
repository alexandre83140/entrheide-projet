import daos.PhotoDao;
import org.junit.Before;
import org.junit.Test;
import pojos.Annonce;
import pojos.Categorie;
import pojos.Photo;
import pojos.Utilisateur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

public class PhotoDaoTestCase {

    private PhotoDao photoDao = new PhotoDao();

    @Before
    public void initDatabase() throws Exception{
        try (Connection connection = photoDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()){
            stmt.executeUpdate("DELETE FROM photo");
            stmt.executeUpdate("DELETE FROM annonce");
            stmt.executeUpdate("DELETE FROM categorie");
            stmt.executeUpdate("DELETE FROM utilisateur");
            stmt.executeUpdate("INSERT INTO categorie(idCategorie, nomCategorie) VALUES (1, 'Nom #1')");
            stmt.executeUpdate("INSERT INTO categorie(idCategorie, nomCategorie) VALUES (2, 'Nom #2')");
            stmt.executeUpdate("INSERT INTO utilisateur(`idUtilisateur`, `nomUtilisateur`, `prenomUtilisateur`, `telephoneUtilisateur`, `mailUtilisateur`, `promoUtilisateur`, `administrateur`, `mdpUtilisateur`) VALUES (1,'Nom #1', 'Prenom #1', 'Tel #1', 'Mail #1', 'H1', false, 'Mdp #1')");
            stmt.executeUpdate("INSERT INTO utilisateur(`idUtilisateur`, `nomUtilisateur`, `prenomUtilisateur`, `telephoneUtilisateur`, `mailUtilisateur`, `promoUtilisateur`, `administrateur`, `mdpUtilisateur`) VALUES (2,'Nom #2', 'Prenom #2', 'Tel #2', 'Mail #2', 'H2', false, 'Mdp #2')");
            stmt.executeUpdate("INSERT INTO annonce (idAnnonce, titreAnnonce, descriptionAnnonce, motsClefsAnnonce, dateAnnonce, evenement, idCategorie, idUtilisateur) VALUES (1,'Titre #1', 'Describ #1', 'Mots #1', '2018-01-01', false, 1, 1)");
            stmt.executeUpdate("INSERT INTO annonce (idAnnonce, titreAnnonce, descriptionAnnonce, motsClefsAnnonce, dateAnnonce, evenement, idCategorie, idUtilisateur) VALUES (2,'Titre #2', 'Describ #2', 'Mots #2', '2018-02-02', false, 2, 2)");
            stmt.executeUpdate("INSERT INTO photo(idPhoto, cheminPhoto, idAnnonce) VALUES (1, 'Chemin #1', 1)");
            stmt.executeUpdate("INSERT INTO photo(idPhoto, cheminPhoto, idAnnonce) VALUES (2, 'Chemin #2', 2)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shoulListPhoto(){
        //WHEN
        List<Photo> photos = photoDao.listPhoto();
        //THEN
        assertThat(photos).hasSize(2);
        assertThat(photos).extracting("idPhoto","cheminPhoto", "annoncePhoto.idAnnonce", "annoncePhoto.titreAnnonce", "annoncePhoto.descriptionAnnonce", "annoncePhoto.motsClefsAnnonce", "annoncePhoto.dateAnnonce", "annoncePhoto.evenement", "annoncePhoto.categorieAnnonce.idCategorie", "annoncePhoto.categorieAnnonce.nomCategorie", "annoncePhoto.utilisateurAnnonce.idUtilisateur", "annoncePhoto.utilisateurAnnonce.nomUtilisateur", "annoncePhoto.utilisateurAnnonce.prenomUtilisateur", "annoncePhoto.utilisateurAnnonce.telephoneUtilisateur", "annoncePhoto.utilisateurAnnonce.mailUtilisateur", "annoncePhoto.utilisateurAnnonce.promoUtilisateur", "annoncePhoto.utilisateurAnnonce.administrateur", "annoncePhoto.utilisateurAnnonce.mdpUtilisateur").containsOnly(
                tuple(1, "Chemin #1", 1, "Titre #1", "Describ #1", "Mots #1", LocalDate.of(2018, 01, 01), false, 1, "Nom #1", 1, "Nom #1", "Prenom #1", "Tel #1", "Mail #1", "H1", false, "Mdp #1"),
                tuple(2, "Chemin #2", 2, "Titre #2", "Describ #2", "Mots #2", LocalDate.of(2018, 02, 02), false, 2, "Nom #2", 2, "Nom #2", "Prenom #2", "Tel #2", "Mail #2", "H2", false, "Mdp #2")
        );
    }

    @Test
    public void shouldGetPhoto() {
        // WHEN
        Photo photo = photoDao.getPhoto(1);
        // THEN
        assertThat(photo).isNotNull();
        assertThat(photo.getIdPhoto()).isEqualTo(1);
        assertThat(photo.getCheminPhoto()).isEqualTo("Chemin #1");
        assertThat(photo.getAnnoncePhoto().getIdAnnonce()).isEqualTo(1);
        assertThat(photo.getAnnoncePhoto().getTitreAnnonce()).isEqualTo("Titre #1");
        assertThat(photo.getAnnoncePhoto().getDescriptionAnnonce()).isEqualTo("Describ #1");
        assertThat(photo.getAnnoncePhoto().getMotsClefsAnnonce()).isEqualTo("Mots #1");
        assertThat(photo.getAnnoncePhoto().getDateAnnonce()).isEqualTo(LocalDate.of(2018, 01, 01));
        assertThat(photo.getAnnoncePhoto().getEvenement()).isEqualTo(false);
        assertThat(photo.getAnnoncePhoto().getCategorieAnnonce().getIdCategorie()).isEqualTo(1);
        assertThat(photo.getAnnoncePhoto().getCategorieAnnonce().getNomCategorie()).isEqualTo("Nom #1");
        assertThat(photo.getAnnoncePhoto().getUtilisateurAnnonce().getIdUtilisateur()).isEqualTo(1);
        assertThat(photo.getAnnoncePhoto().getUtilisateurAnnonce().getNomUtilisateur()).isEqualTo("Nom #1");
        assertThat(photo.getAnnoncePhoto().getUtilisateurAnnonce().getPrenomUtilisateur()).isEqualTo("Prenom #1");
        assertThat(photo.getAnnoncePhoto().getUtilisateurAnnonce().getTelephoneUtilisateur()).isEqualTo("Tel #1");
        assertThat(photo.getAnnoncePhoto().getUtilisateurAnnonce().getMailUtilisateur()).isEqualTo("Mail #1");
        assertThat(photo.getAnnoncePhoto().getUtilisateurAnnonce().getPromoUtilisateur()).isEqualTo("H1");
        assertThat(photo.getAnnoncePhoto().getUtilisateurAnnonce().getAdministrateur()).isEqualTo(false);
        assertThat(photo.getAnnoncePhoto().getUtilisateurAnnonce().getMdpUtilisateur()).isEqualTo("Mdp #1");
    }

    @Test
    public void shouldAddPhoto() throws SQLException {
        //GIVEN
        Photo newPhoto = new Photo(null, "New Chemin", new Annonce(1, "New Titre", "New Description", "New Mots", LocalDate.of(2018,12,12), false, new Categorie(1, "New Name"), new Utilisateur(1, "New Nom", "New Prenom", "New Phone", "New Mail", "NP", false, "New Mdp")));
        //WHEN
        Photo createdPhoto = photoDao.addPhoto(newPhoto);
        // THEN
        assertThat(createdPhoto).isNotNull();
        assertThat(createdPhoto.getIdPhoto()).isNotNull();
        assertThat(createdPhoto.getIdPhoto()).isGreaterThan(0);
        assertThat(createdPhoto.getCheminPhoto()).isEqualTo("New Chemin");
        assertThat(createdPhoto.getAnnoncePhoto().getIdAnnonce()).isEqualTo(1);
        assertThat(createdPhoto.getAnnoncePhoto().getTitreAnnonce()).isEqualTo("New Titre");
        assertThat(createdPhoto.getAnnoncePhoto().getDescriptionAnnonce()).isEqualTo("New Description");
        assertThat(createdPhoto.getAnnoncePhoto().getMotsClefsAnnonce()).isEqualTo("New Mots");
        assertThat(createdPhoto.getAnnoncePhoto().getDateAnnonce()).isEqualTo(LocalDate.of(2018, 12, 12));
        assertThat(createdPhoto.getAnnoncePhoto().getEvenement()).isEqualTo(false);
        assertThat(createdPhoto.getAnnoncePhoto().getCategorieAnnonce().getIdCategorie()).isEqualTo(1);
        assertThat(createdPhoto.getAnnoncePhoto().getCategorieAnnonce().getNomCategorie()).isEqualTo("New Name");
        assertThat(createdPhoto.getAnnoncePhoto().getUtilisateurAnnonce().getIdUtilisateur()).isEqualTo(1);
        assertThat(createdPhoto.getAnnoncePhoto().getUtilisateurAnnonce().getNomUtilisateur()).isEqualTo("New Nom");
        assertThat(createdPhoto.getAnnoncePhoto().getUtilisateurAnnonce().getPrenomUtilisateur()).isEqualTo("New Prenom");
        assertThat(createdPhoto.getAnnoncePhoto().getUtilisateurAnnonce().getTelephoneUtilisateur()).isEqualTo("New Phone");
        assertThat(createdPhoto.getAnnoncePhoto().getUtilisateurAnnonce().getMailUtilisateur()).isEqualTo("New Mail");
        assertThat(createdPhoto.getAnnoncePhoto().getUtilisateurAnnonce().getPromoUtilisateur()).isEqualTo("NP");
        assertThat(createdPhoto.getAnnoncePhoto().getUtilisateurAnnonce().getAdministrateur()).isEqualTo(false);
        assertThat(createdPhoto.getAnnoncePhoto().getUtilisateurAnnonce().getMdpUtilisateur()).isEqualTo("New Mdp");
        try (Connection connection = photoDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM photo WHERE cheminPhoto = 'New Chemin'")) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getInt("idPhoto")).isEqualTo(createdPhoto.getIdPhoto());
                assertThat(rs.getString("cheminPhoto")).isEqualTo("New Chemin");
                assertThat(rs.getInt("idAnnonce")).isEqualTo(1);
                assertThat(rs.next()).isFalse();
            }
        }
    }

}
