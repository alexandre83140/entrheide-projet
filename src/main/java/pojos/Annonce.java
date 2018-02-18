package pojos;


import java.time.LocalDate;

public class Annonce {

    private Integer idAnnonce;
    private String titreAnnonce;
    private String descriptionAnnonce;
    private String motsClefsAnnonce;
    private LocalDate dateAnnonce;
    private boolean evenement;
    private Categorie CategorieAnnonce;
    private Utilisateur UtilisateurAnnonce;


    public Annonce(Integer idAnnonce, String titreAnnonce, String descriptionAnnonce, String motsClefsAnnonce, LocalDate dateAnnonce, boolean evenement, Categorie CategorieAnnonce, Utilisateur UtilisateurAnnonce){
        this.idAnnonce = idAnnonce;
        this.titreAnnonce = titreAnnonce;
        this.descriptionAnnonce = descriptionAnnonce;
        this.motsClefsAnnonce = motsClefsAnnonce;
        this.dateAnnonce = dateAnnonce;
        this.evenement = evenement;
        this.CategorieAnnonce = CategorieAnnonce;
        this.UtilisateurAnnonce = UtilisateurAnnonce;
    }

    public Integer getIdAnnonce(){
        return idAnnonce;
    }
    public void setIdAnnonce(Integer idAnnonce){
        this.idAnnonce = idAnnonce;
    }

    public String getTitreAnnonce(){
        return titreAnnonce;
    }
    public void setTitreAnnonce(String titreAnnonce){
        this.titreAnnonce = titreAnnonce;
    }

    public String getDescriptionAnnonce() {
        return descriptionAnnonce;
    }
    public void setDescriptionAnnonce(String descriptionAnnonce) {
        this.descriptionAnnonce = descriptionAnnonce;
    }

    public String getMotsClefsAnnonce() {
        return motsClefsAnnonce;
    }
    public void setMotsClefsAnnonce(String motsClefsAnnonce) {
        this.motsClefsAnnonce = motsClefsAnnonce;
    }

    public LocalDate getDateAnnonce() {
        return dateAnnonce;
    }
    public void setDateAnnonce(LocalDate dateAnnonce) {
        this.dateAnnonce = dateAnnonce;
    }

    public boolean getEvenement() { return evenement;}
    public void setEvenement(boolean evenement) { this.evenement = evenement;}

    public Categorie getCategorieAnnonce() {
        return CategorieAnnonce;
    }
    public void setCategorieAnnonce(Categorie categorieAnnonce) {
        this.CategorieAnnonce = categorieAnnonce;
    }

    public Utilisateur getUtilisateurAnnonce() {
        return UtilisateurAnnonce;
    }
    public void setUtilisateurAnnonce(Utilisateur utilisateurAnnonce) {
        this.UtilisateurAnnonce = utilisateurAnnonce;
    }

}
