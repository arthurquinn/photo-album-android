package src.androidphotoalbum.models;

public class Photo implements java.io.Serializable {
    private String filePath;

    public Photo(String filePath){
        this.filePath = filePath;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public String getFilePath(){
        return this.filePath;
    }
}
