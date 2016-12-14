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

    public Album getAlbum(int index){
        return (Album)this.albumList.get(index);
    }
}
