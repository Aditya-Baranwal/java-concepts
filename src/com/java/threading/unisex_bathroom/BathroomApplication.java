package com.java.threading.unisex_bathroom;

import com.java.threading.unisex_bathroom.Bathroom;
import com.java.threading.unisex_bathroom.clients.ManAdderClient;
import com.java.threading.unisex_bathroom.clients.WomanAdderClient;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BathroomApplication {

    public static void main(String[] args) {
        Bathroom bt = new Bathroom();
        ManAdderClient manAdder = new ManAdderClient(bt);
        WomanAdderClient womanAdder = new WomanAdderClient(bt);
        Executor executor = Executors.newCachedThreadPool();
        for(int i=0; i<10; i++) {
            if(i%2 == 0) {
                executor.execute(manAdder);
            } else {
                executor.execute(womanAdder);
            }
        }
    }
}
