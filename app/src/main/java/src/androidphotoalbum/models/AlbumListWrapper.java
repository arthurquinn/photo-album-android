package src.androidphotoalbum.models;

import java.util.List;
import java.util.ArrayList;

public class AlbumListWrapper implements java.io.Serializable {
    private static final long serialVersionUID = 6760982934587815490L;

    private ArrayList<Album> albumList;

    public AlbumListWrapper(){
        this.albumList = new ArrayList<Album>();
    }

    public List<Album> getAlbumList(){
        return this.albumList;
    }

    public void addAlbum(Album a){
        this.albumList.add(a);
    }

    public void removeAlbum(int position){
        this.albumList.remove(position);
    }

    public boolean checkDuplicate(String name) {
        for (Album album : albumList)
        {
            if (album.getName().equals(name))
                return true;
        }
        return false;
    }

    public boolean editAlbum(String oldName, String newName) {
        for (Album album : albumList){
            if (oldName.equals(album.getName())){
                album.setName(newName);
                return true;
            }
        }
        return false;
    }

    public boolean movePhotoToAlbum(Photo p, Album source, String targetName) {
        source.removePhoto(p);
        for (Album album : albumList){
            if (album.getName().equals(targetName)){
                album.addPhoto(p);
                return true;
            }
        }
        return false;
    }
}
