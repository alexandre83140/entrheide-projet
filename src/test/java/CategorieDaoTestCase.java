import daos.CategorieDao;
import org.junit.Before;
import org.junit.Test;
import pojos.Categorie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

public class CategorieDaoTestCase {

    private CategorieDao categorieDao = new CategorieDao();

    @Before
    public void initDatabase() throws Exception{
        try (Connection connection = categorieDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()){
            stmt.executeUpdate("DELETE FROM `categorie`");
            stmt.executeUpdate("INSERT INTO `categorie`(idCategorie, nomCategorie) VALUES (1, 'Nom #1')");
            stmt.executeUpdate("INSERT INTO `categorie`(idCategorie, nomCategorie) VALUES (2, 'Nom #2')");
            stmt.executeUpdate("INSERT INTO `categorie`(idCategorie, nomCategorie) VALUES (3, 'Nom #3')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shoulListCategorie(){
        //WHEN
        List<Categorie> categories = categorieDao.listCategorie();
        //THEN
        assertThat(categories).hasSize(3);
        assertThat(categories).extracting("idCategorie","nomCategorie").containsOnly(
                tuple(1, "Nom #1"),
                tuple(2, "Nom #2"),
                tuple(3, "Nom #3")
        );
    }

    @Test
    public void shouldGetCategorie() {
        // WHEN
        Categorie categorie = categorieDao.getCategorie("Nom #1");
        // THEN
        assertThat(categorie).isNotNull();
        assertThat(categorie.getIdCategorie()).isEqualTo(1);
        assertThat(categorie.getNomCategorie()).isEqualTo("Nom #1");
    }

    @Test
    public void shouldAddCategorie() throws SQLException {
        //WHEN
        categorieDao.addCategorie("test");
        //THEN
        try (Connection connection = categorieDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM categorie WHERE nomCategorie = 'test'")) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getInt("idCategorie")).isGreaterThan(0);
                assertThat(rs.getString("nomCategorie")).isEqualTo("test");
                assertThat(rs.next()).isFalse();
            }
        }
    }
}
