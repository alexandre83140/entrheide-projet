package services;

import daos.CategorieDao;
import pojos.Categorie;

import java.util.List;

public class CategorieService {

    private static class CategorieServiceHolder{
        private static CategorieService instance = new CategorieService();
    }

    public static CategorieService getInstance(){
        return CategorieServiceHolder.instance;
    }

    private CategorieService(){
    }

    private CategorieDao categorieDao = new CategorieDao();

    public List<Categorie> listCategorie(){
        return categorieDao.listCategorie();
    }

    public void addCategorie(String nomCategorie) { categorieDao.addCategorie(nomCategorie);}

    public Categorie getCategorie(String nomCategorie){ return categorieDao.getCategorie(nomCategorie);}

}
