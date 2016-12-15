package src.androidphotoalbum.state;

import android.app.Application;
import android.util.Log;

import java.io.File;

import src.androidphotoalbum.models.Album;
import src.androidphotoalbum.models.AlbumListWrapper;
import src.androidphotoalbum.models.Photo;

public class ApplicationInstance {

    private static ApplicationInstance instance;

    private static final String logCode = "androidPhotoAlbumLog";
    private AlbumListWrapper albumListWrapper;
    private Album activeAlbum;
    private Photo activePhoto;

    // Turn off constructor
    private ApplicationInstance(){
        albumListWrapper = new AlbumListWrapper();
    }

    public static void instantiateTest(){
        instance = new ApplicationInstance();
        instance.getAlbumListWrapper().addAlbum(new Album("Meteora"));
        instance.getAlbumListWrapper().addAlbum(new Album("Minutes to Midnight"));

        /*
        try{
            File file = new File();
            file.createNewFile();
            Log.i(logCode, "Created file... " + file.getAbsolutePath());
        } catch (Exception e) {
            Log.i(logCode, "Failed create file... " + e.getMessage());
        }
        */

    }

    public static ApplicationInstance getInstance(){
        return instance == null ? new ApplicationInstance() : instance;
    }

    public AlbumListWrapper getAlbumListWrapper(){
        return this.albumListWrapper;
    }

    public Album getActiveAlbum(){
        return this.activeAlbum;
    }

    public Photo getActivePhoto(){
        return this.activePhoto;
    }

    public void setActiveAlbum(Album a){
        this.activeAlbum = a;
    }

    public void setActivePhoto(Photo p){
        this.activePhoto = p;
    }
}
