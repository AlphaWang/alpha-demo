package com.alphawang.designpattern.creational.singleton.test;

import com.alphawang.designpattern.creational.singleton.HungrySingleton;
import com.alphawang.designpattern.creational.singleton.LazyHolderSingleton;
import com.alphawang.designpattern.creational.singleton.LazyHolderSingleton2;
import com.alphawang.designpattern.creational.singleton.LazySingleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectionAttack {

    public static void main(String[] args) {
        // HACKED!!! 
        attackSingleton(HungrySingleton.class);
        // HACKED!!!
        attackSingleton(LazySingleton.class);
        // HACKED!!!
        attackSingleton(LazyHolderSingleton.class);
        attackSingleton(LazyHolderSingleton2.class);
        
    }
    
    private static void attackSingleton(Class clazz) {
        try {
            Constructor constructor = clazz.getDeclaredConstructor(null);
            constructor.setAccessible(true);
            
            Object instance1 = constructor.newInstance();
            
            Object instance2 = constructor.newInstance();
            
            boolean singleInstance = (instance1 == instance2);
            System.out.println("Attack " + clazz.getSimpleName() + " : " + singleInstance);
            if (!singleInstance) {
                System.out.println("HACKED!!!");
            }
            
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
