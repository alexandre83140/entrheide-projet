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

}
