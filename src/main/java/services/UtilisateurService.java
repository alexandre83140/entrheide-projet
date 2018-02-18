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

    public Utilisateur getUtilisateur(String mailUtilisateur, String mdpUtilisateur) { return utilisateurDao.getUtilisateur(mailUtilisateur, mdpUtilisateur);}

    public Utilisateur addUtilisateur(Utilisateur utilisateur){ return utilisateurDao.addUtilisateur(utilisateur);}
}
