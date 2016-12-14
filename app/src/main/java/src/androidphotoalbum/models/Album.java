package src.androidphotoalbum.models;

public class Album implements java.io.Serializable {

    private String name;

    public Album(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}

