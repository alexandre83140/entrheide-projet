package pojos;

public class Utilisateur {

    private Integer idUtilisateur;
    private String nomUtilisateur;
    private String prenomUtilisateur;
    private String telephoneUtilisateur;
    private String mailUtilisateur;
    private String promoUtilisateur;
    private Integer administrateur;
    private String mdpUtilisateur;

    public Utilisateur(Integer idUtilisateur, String nomUtilisateur, String prenomUtilisateur, String telephoneUtilisateur, String mailUtilisateur, String promoUtilisateur, Integer administrateur, String mdpUtilisateur){
        this.idUtilisateur = idUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
        this.prenomUtilisateur = prenomUtilisateur;
        this.telephoneUtilisateur = telephoneUtilisateur;
        this.mailUtilisateur = mailUtilisateur;
        this.promoUtilisateur = promoUtilisateur;
        this.administrateur = administrateur;
        this.mdpUtilisateur = mdpUtilisateur;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPrenomUtilisateur() {
        return prenomUtilisateur;
    }

    public void setPrenomUtilsateur(String prenomUtilsateur) {
        this.prenomUtilisateur = prenomUtilsateur;
    }

    public String getTelephoneUtilisateur() {
        return telephoneUtilisateur;
    }

    public void setTelephoneUtilisateur(String telephoneUtilisateur) {
        this.telephoneUtilisateur = telephoneUtilisateur;
    }

    public String getMailUtilisateur() {
        return mailUtilisateur;
    }

    public void setMailUtilisateur(String mailUtilisateur) {
        this.mailUtilisateur = mailUtilisateur;
    }

    public String getPromoUtilisateur() {
        return promoUtilisateur;
    }

    public void setPromoUtilisateur(String promoUtilisateur) {
        this.promoUtilisateur = promoUtilisateur;
    }

    public Integer getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(Integer administrateur) {
        this.administrateur = administrateur;
    }


    public String getMdpUtilisateur() {
        return mdpUtilisateur;
    }

    public void setMdpUtilisateur(String mdpUtilisateur) {
        this.mdpUtilisateur = mdpUtilisateur;
    }
}
