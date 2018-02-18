package pojos;

public class Commentaire {

    private String texteCommentaire;
    private Utilisateur utilisateurCommentaire;
    private Annonce annonceCommentaire;

    public Commentaire(String texteCommentaire, Utilisateur utilisateurCommentaire, Annonce annonceCommentaire){
        this.texteCommentaire = texteCommentaire;
        this.utilisateurCommentaire = utilisateurCommentaire;
        this.annonceCommentaire = annonceCommentaire;
    }


    public Utilisateur getUtilisateurCommentaire() {
        return utilisateurCommentaire;
    }

    public void setUtilisateurCommentaire(Utilisateur utilisateurCommentaire) {
        this.utilisateurCommentaire = utilisateurCommentaire;
    }

    public String getTexteCommentaire() {
        return texteCommentaire;
    }

    public void setTexteCommentaire(String texteCommentaire) {
        this.texteCommentaire = texteCommentaire;
    }

    public Annonce getAnnonceCommentaire() {
        return annonceCommentaire;
    }

    public void setAnnonceCommentaire(Annonce annonceCommentaire) {
        this.annonceCommentaire = annonceCommentaire;
    }
}
