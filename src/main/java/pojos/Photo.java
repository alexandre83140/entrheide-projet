package pojos;

public class Photo {

    private Integer idPhoto;
    private String cheminPhoto;

    private Annonce annoncePhoto;

    public Photo(Integer idPhoto, String cheminPhoto, Annonce annoncePhoto){
        this.idPhoto = idPhoto;
        this.cheminPhoto = cheminPhoto;
        this.annoncePhoto = annoncePhoto;
    }

    public Integer getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(Integer idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getCheminPhoto() {
        return cheminPhoto;
    }

    public void setCheminPhoto(String cheminPhoto) {
        this.cheminPhoto = cheminPhoto;
    }

    public Annonce getAnnoncePhoto() {
        return annoncePhoto;
    }

    public void setAnnoncePhoto(Annonce annoncePhoto) {
        this.annoncePhoto = annoncePhoto;
    }
}
