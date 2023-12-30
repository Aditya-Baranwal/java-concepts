package com.java.threading.unisex_bathroom.clients;

import com.java.threading.unisex_bathroom.Bathroom;
import com.java.threading.unisex_bathroom.Man;

public class ManAdderClient implements Runnable {

    Bathroom bathroom;

    public ManAdderClient(Bathroom bt) {
        bathroom = bt;
    }

    @Override
    public void run() {
        try {
            Man m = new Man();
            boolean status = this.bathroom.enter(m);
            if(status == true) {
                System.out.println("Man has entered -" + status);
                Thread.sleep(300);
                this.bathroom.exit(m);
            } else {
                System.out.println("Man could not entered -" + status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
