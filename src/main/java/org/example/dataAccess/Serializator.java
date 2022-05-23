package org.example.dataAccess;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serializator<T>  {
   private String file;

    public Serializator(String file) {
        this.file = file;
    }

    public final void writeObject(T obj){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);

            out.writeObject(obj);
            out.close();
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public final T readObject(){
        T obj = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
                obj = (T)in.readObject();
            in.close();
            fileInputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
       } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
