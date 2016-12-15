package src.androidphotoalbum.models;

import java.util.ArrayList;
import java.util.List;

public class Photo implements java.io.Serializable {

    private String filePath;
    private List<TagValuePair> tagList;

    public Photo(String filePath){
        this.filePath = filePath;
        this.tagList = new ArrayList<TagValuePair>();
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public String getFilePath(){
        return this.filePath;
    }

    public void addTag(TagValuePair t){
        this.tagList.add(t);
    }

    public void removeTag(TagValuePair t){
        this.tagList.remove(t);
    }
}
