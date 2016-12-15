package src.androidphotoalbum.state;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import src.androidphotoalbum.models.Album;
import src.androidphotoalbum.models.AlbumListWrapper;
import src.androidphotoalbum.models.Photo;

public class ApplicationInstance {

    private static ApplicationInstance instance;

    private AlbumListWrapper albumListWrapper;
    private Album activeAlbum;
    private Photo activePhoto;

    // Turn off constructor
    private ApplicationInstance(){
        albumListWrapper = new AlbumListWrapper();
    }

    public static void instantiate(Context ctx){
        instance = new ApplicationInstance();
        instance.save(ctx);
    }

    public static ApplicationInstance getInstance(){
        return instance == null ? (instance = new ApplicationInstance()) : instance;
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

    public void save(Context ctx){
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = ctx.openFileOutput("usr.dat", Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(albumListWrapper);


            /// fileOutputStream.close();
        } catch (Exception e) {
        }
    }

    public void load(Context ctx){
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        try {
            fileInputStream = ctx.openFileInput("usr.dat");
            objectInputStream = new ObjectInputStream(fileInputStream);
            this.albumListWrapper = (AlbumListWrapper)objectInputStream.readObject();


        } catch (Exception e) {
        }
    }

    public void transferImageToInternalStorage(Context ctx, String filename, Uri uri){
        try{
            InputStream inputStream = ctx.getContentResolver().openInputStream(uri);
            final Bitmap img = BitmapFactory.decodeStream(inputStream);
            final FileOutputStream fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
            img.compress(Bitmap.CompressFormat.PNG, 90, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String extractExtension(String filepath){
        String extension = "";
        int i = filepath.lastIndexOf('.');
        if (i >= 0) {
            extension = filepath.substring(i+1);
        }
        return extension;
    }
}
