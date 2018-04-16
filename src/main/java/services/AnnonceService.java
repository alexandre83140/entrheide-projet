package services;

import daos.AnnonceDao;
import pojos.Annonce;

import java.util.List;

public class AnnonceService {

    private static class AnnonceServiceHolder{
        private static AnnonceService instance = new AnnonceService();
    }

    public static AnnonceService getInstance(){
        return AnnonceService.AnnonceServiceHolder.instance;
    }

    private AnnonceService(){
    }

    private AnnonceDao annonceDao = new AnnonceDao();

    public List<Annonce> listAnnonce(){ return annonceDao.listAnnonce();}

    public Annonce getAnnonce(Integer idAnnonce) { return annonceDao.getAnnonce(idAnnonce);}

    public Annonce addAnnonce(Annonce annonce){ return annonceDao.addAnnonce(annonce);}

    public List<Annonce> listAnnonceByCategorie(Integer idCategorie) { return annonceDao.listAnnonceByCategorie(idCategorie);}

    public List<Annonce> listAnnonceByMotsClefs(String motsClefsAnnonce) { return annonceDao.listAnnonceByMotsClefs(motsClefsAnnonce);}

    public List<Annonce> listAnnonceByUtilisateur(String mailUtilisateur) { return annonceDao.listAnnonceByUtilisateur(mailUtilisateur);}

    public List<Annonce> listEvenement(){return annonceDao.listEvenement();}

    public List<Annonce> listAnnoncesSignalees(){return annonceDao.listAnnoncesSignalees();}

    public void deleteAnnonce(String titreAnnonce){annonceDao.deleteAnnonce(titreAnnonce);}

    public void modifAnnonceSignalee(Integer idAnnonce){annonceDao.modifAnnonceSignalee(idAnnonce);}

    public List<Annonce> listAnnonceDuJour(){ return annonceDao.listAnnonceDuJour();}

    public List<Annonce> listAnnonceWithoutEvent(){ return annonceDao.listAnnonceWithoutEvent();}
}
