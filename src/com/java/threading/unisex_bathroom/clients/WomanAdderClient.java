package com.java.threading.unisex_bathroom.clients;

import com.java.threading.unisex_bathroom.Bathroom;
import com.java.threading.unisex_bathroom.Woman;

public class WomanAdderClient implements Runnable {

    Bathroom bathroom;

    public WomanAdderClient(Bathroom bt) {
        bathroom = bt;
    }

    @Override
    public void run() {
        try {
            Woman w = new Woman();
            boolean status = this.bathroom.enter(w);
            if(status == true) {
                System.out.println("Woman has entered -" + status);
                Thread.sleep(300);
                this.bathroom.exit(w);
            } else {
                System.out.println("Woman could not entered -" + status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
