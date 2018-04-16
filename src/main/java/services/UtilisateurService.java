package services;

import daos.UtilisateurDao;
import pojos.Utilisateur;

import java.util.List;

public class UtilisateurService {

    private static class UtilisateurServiceHolder{
        private static UtilisateurService instance = new UtilisateurService();
    }

    public static UtilisateurService getInstance(){
        return UtilisateurService.UtilisateurServiceHolder.instance;
    }

    private UtilisateurService(){
    }

    private UtilisateurDao utilisateurDao = new UtilisateurDao();

    public List<Utilisateur> listUtilisateur(){ return utilisateurDao.listUtilisateur();}

    public Utilisateur getUtilisateur(String mailUtilisateur) { return utilisateurDao.getUtilisateur(mailUtilisateur);}

    public Utilisateur addUtilisateur(Utilisateur utilisateur){ return utilisateurDao.addUtilisateur(utilisateur);}

    public void modifMdpUtilisateur(Integer idUtilisateur, String newMdp){ utilisateurDao.modifMdpUtilisateur(idUtilisateur, newMdp);}

    public void modifPromoUtilisateur(Integer idUtilisateur, String NP){ utilisateurDao.modifPromoUtilisateur(idUtilisateur, NP);}

    public void addTelephoneUtilisateur(Integer idUtilisateur, String numTelephone){ utilisateurDao.addTelephoneUtilisateur( idUtilisateur, numTelephone);}

    public String getStoredPassword(String mailUtilisateur) {return utilisateurDao.getStoredPassword(mailUtilisateur);}
}
