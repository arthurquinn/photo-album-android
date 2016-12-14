package src.androidphotoalbum.models;

import java.util.List;
import java.util.ArrayList;

public class Album implements java.io.Serializable {

    private String name;
    private List<Photo> photoList;

    public Album(String name) {
        this.name = name;
        this.photoList = new ArrayList<Photo>();
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void addPhoto(Photo p){
        this.photoList.add(p);
    }

    public void removePhoto(Photo p){
        this.photoList.remove(p);
    }

    public List<Photo> getPhotoList(){
        return this.photoList;
    }

    @Override
    public String toString(){
        return this.name;
    }
}

