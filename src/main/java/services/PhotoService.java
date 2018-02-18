package services;

import daos.PhotoDao;
import pojos.Photo;

import java.util.List;

public class PhotoService {

    private static class PhotoServiceHolder{
        private static PhotoService instance = new PhotoService();
    }

    public static PhotoService getInstance(){
        return PhotoService.PhotoServiceHolder.instance;
    }

    private PhotoService(){
    }

    private PhotoDao photoDao = new PhotoDao();

    public List<Photo> listPhoto(){ return photoDao.listPhoto();}

    public Photo getPhoto(Integer idPhoto) {return photoDao.getPhoto(idPhoto);}

    public Photo addPhoto(Photo photo){ return photoDao.addPhoto(photo);}
}
