package com.alphawang.designpattern.creational.singleton.test;

import com.alphawang.designpattern.creational.singleton.LazyHolderSingleton;
import com.alphawang.designpattern.creational.singleton.LazyHolderSingleton3Serialize;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializeAttack {

    public static void main(String[] args) {
        LazyHolderSingleton instance1 = null;
        LazyHolderSingleton instance2 = LazyHolderSingleton.getInstance();

        // HACKED!
        attackSerializable(instance1, instance2);

        LazyHolderSingleton3Serialize instance3 = null;
        LazyHolderSingleton3Serialize instance4 = LazyHolderSingleton3Serialize.getInstance();

        // NO HACKED
        attackSerializable(instance3, instance4);
    }
    
    private static void attackSerializable(Serializable instance1, Serializable instance2) {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream("singleton.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(instance2);
            
            oos.flush();
            oos.close();

            FileInputStream fis = new FileInputStream("singleton.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            instance1 = (Serializable) ois.readObject();
            
            ois.close();

            boolean singleInstance = (instance1 == instance2);
            if (!singleInstance) {
                System.out.println("HACKED!!!");
            }
            
            System.out.println(instance1);
            System.out.println(instance2);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
