package src.androidphotoalbum.lib;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import src.androidphotoalbum.models.AlbumListWrapper;

public class Manager
{
    public AlbumListWrapper open()
    {
        try
        {
            ObjectInputStream inStream = new ObjectInputStream(new FileInputStream("data/state.dat"));
            AlbumListWrapper.instance = (AlbumListWrapper)inStream.readObject();
            return AlbumListWrapper.instance;
        }
        catch (IOException | ClassNotFoundException e)
        {
            return null;
        }
    }

    public void save()
    {
        ObjectOutputStream outStream;
        try {
            outStream = new ObjectOutputStream(new FileOutputStream("data/state.dat"));
            outStream.writeObject(AlbumListWrapper.instance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
