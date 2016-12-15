package src.androidphotoalbum.models;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Photo implements java.io.Serializable {

    private String uniqueId;
    private String uriString;
    private List<TagValuePair> tagList;

    public Photo(String uriString){
        this.uriString = uriString;
        this.tagList = new ArrayList<TagValuePair>();
        this.uniqueId = UUID.randomUUID().toString();
    }

    public void setUriString(String uriString){
        this.uriString = uriString;
    }

    public Uri getUri(){
        return Uri.parse(this.uriString);
    }

    public void addTag(TagValuePair t){
        this.tagList.add(t);
    }

    public void removeTag(TagValuePair t){
        this.tagList.remove(t);
    }

    @Override
    public boolean equals(Object o){
        return o instanceof Photo && ((Photo)o).uniqueId.equals(uniqueId);
    }
}
