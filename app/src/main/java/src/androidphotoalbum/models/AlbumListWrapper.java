package src.androidphotoalbum.models;

import java.util.List;
import java.util.ArrayList;

public class AlbumListWrapper implements java.io.Serializable {

    private List<Album> albumList;

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

    public boolean editAlbum(String oldName, String newName)
    {
        // TODO: Add check to make sure no duplicates
        for (Album album : albumList){
            if (oldName.equals(album.getName())){
                album.setName(newName);
                return true;
            }
        }
        return false;
    }
}
