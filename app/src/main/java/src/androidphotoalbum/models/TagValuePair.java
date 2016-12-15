package src.androidphotoalbum.models;

public class TagValuePair {

    private String key;
    private String value;

    public TagValuePair(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return this.key;
    }

    public String getValue(){
        return this.getValue();
    }

    @Override
    public String toString(){
        return String.format("[Type: %s | Value: %s]", this.key, this.value);
    }
}
