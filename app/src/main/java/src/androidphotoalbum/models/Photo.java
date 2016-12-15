package src.androidphotoalbum.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Photo implements java.io.Serializable {
    private static final long serialVersionUID = 676098297609856790L;

    private static final String IMAGE_NAME_FORMAT = "image_%s";
    private static final String logCode = "androidPhotoAlbumLog";

    private String uniqueId;
    private List<TagValuePair> tagList;

    public Photo(){
        this.tagList = new ArrayList<TagValuePair>();
        this.uniqueId = UUID.randomUUID().toString();
    }

    public String getFilename(){
        return String.format(IMAGE_NAME_FORMAT, uniqueId);
    }

    public void addTag(TagValuePair t){
        this.tagList.add(t);
    }

    public void removeTag(TagValuePair t){
        this.tagList.remove(t);
    }

    public List<TagValuePair> getTags(){
        return this.tagList;
    }

    public Bitmap loadBitmap(Context ctx){
        try{
            InputStream inputStream = ctx.openFileInput(getFilename());
            Bitmap img = BitmapFactory.decodeStream(inputStream);
            return img;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object o){
        return o instanceof Photo && ((Photo)o).uniqueId.equals(uniqueId);
    }
}
