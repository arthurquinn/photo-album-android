package src.androidphotoalbum.models;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class Photo implements java.io.Serializable {

    private Uri fileUri;
    private List<TagValuePair> tagList;

    public Photo(Uri uri){
        this.fileUri = uri;
        this.tagList = new ArrayList<TagValuePair>();
    }

    public void setFileUri(Uri uri){
        this.fileUri = uri;
    }

    public Uri getFileUri(){
        return this.fileUri;
    }

    public void addTag(TagValuePair t){
        this.tagList.add(t);
    }

    public void removeTag(TagValuePair t){
        this.tagList.remove(t);
    }
}
