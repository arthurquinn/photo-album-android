package src.androidphotoalbum.state;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;

import java.io.File;
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

    private static final String logCode = "androidPhotoAlbumLog";
    private AlbumListWrapper albumListWrapper;
    private Album activeAlbum;
    private Photo activePhoto;

    // Turn off constructor
    private ApplicationInstance(){
        albumListWrapper = new AlbumListWrapper();
    }

    public static void instantiate(Context ctx){
        instance = new ApplicationInstance();
        instance.getAlbumListWrapper().addAlbum(new Album("Meteora"));
        instance.getAlbumListWrapper().addAlbum(new Album("Minutes to Midnight"));
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

            for (Album a : albumListWrapper.getAlbumList()){
                Log.i(logCode, "Saved album: " + a.getName());
            }

            Log.i(logCode, "Save successful...");
            /// fileOutputStream.close();
        } catch (Exception e) {
            Log.i(logCode, "Save not successful... : " + e.getMessage());
        }
    }

    public void load(Context ctx){
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        try {
            fileInputStream = ctx.openFileInput("usr.dat");
            objectInputStream = new ObjectInputStream(fileInputStream);
            this.albumListWrapper = (AlbumListWrapper)objectInputStream.readObject();

            for (Album a : albumListWrapper.getAlbumList()){
                Log.i(logCode, "Album found: " + a.getName());
            }

            Log.i(logCode, "Successfully loaded data...");
        } catch (Exception e) {
            Log.i(logCode, "Data not loaded... : " + e.getMessage());
        }
    }

    public Bitmap transferImageToInternalStorage(Context ctx, Uri uri){
        try{
            File imgFile = new File(uri.getPath());
            Log.i(logCode, "Image Uri: " + uri.toString());
            Log.i(logCode, "Image name: " + imgFile.getName());
            Log.i(logCode, "Image path: " + imgFile.getAbsolutePath());
            Log.i(logCode, "Image ext: " + extractExtension(imgFile.getPath()));

            // Create a BitMap object of the image to be worked with
            InputStream inputStream = ctx.getContentResolver().openInputStream(uri);
            final Bitmap img = BitmapFactory.decodeStream(inputStream);

            // The openfileOutput() method creates a file on the phone/internal storage in the context of your application
            final FileOutputStream fos = ctx.openFileOutput(imgFile.getName(), Context.MODE_PRIVATE);

            img.compress(Bitmap.CompressFormat.PNG, 90, fos);

            InputStream test = ctx.openFileInput(imgFile.getName());
            Bitmap opened = BitmapFactory.decodeStream(test);

            Log.i(logCode, "Returning opened file...");
            return opened;

        } catch (Exception e) {
            Log.i(logCode, "Exception thrown in transer... : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
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
