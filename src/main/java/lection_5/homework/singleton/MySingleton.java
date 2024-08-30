package lection_5.homework.singleton;

public class MySingleton {

    private static MySingleton instance;

    private MySingleton() {
        System.out.println("Instance not found. Creating new.");
    }

    public static MySingleton getInstance() {
        System.out.println("Looking for instance if MySingleton.");
        if (instance == null) instance = new MySingleton();
        System.out.println("Returning instance.");
        return instance;
    }
}
