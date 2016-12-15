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

    private static final String logCode = "androidPhotoAlbumLog";

    private String uniqueId;
    private String filename;
    private List<TagValuePair> tagList;

    public Photo(String filename){
        this.filename = filename;
        this.tagList = new ArrayList<TagValuePair>();

        // TODO: ONLY FOR TESTING!!!
        tagList.add(new TagValuePair("hi", "hi"));
        tagList.add(new TagValuePair("hi", "hi"));
        tagList.add(new TagValuePair("hi", "hi"));
        tagList.add(new TagValuePair("hi", "hi"));
        tagList.add(new TagValuePair("hi", "hi"));
        tagList.add(new TagValuePair("hi", "hi"));
        tagList.add(new TagValuePair("hi", "hi"));
        tagList.add(new TagValuePair("hi", "hi"));
        tagList.add(new TagValuePair("hi", "hi"));

        this.uniqueId = UUID.randomUUID().toString();
    }

    public void setFilename(String filename){
        this.filename = filename;
    }

    public String getFilename(){
        return this.filename;
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
            InputStream inputStream = ctx.openFileInput(filename);
            Bitmap img = BitmapFactory.decodeStream(inputStream);

            Log.i(logCode, "Returning opened file...");
            return img;
        } catch (Exception e) {
            Log.i(logCode, "Failed to open image: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object o){
        return o instanceof Photo && ((Photo)o).uniqueId.equals(uniqueId);
    }
}
