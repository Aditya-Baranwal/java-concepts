package com.java.threading.unisex_bathroom;

import com.java.threading.unisex_bathroom.interfaces.Person;

import java.util.concurrent.ConcurrentLinkedDeque;

public class Bathroom {

    ConcurrentLinkedDeque<Man> manList;
    ConcurrentLinkedDeque<Woman> womanList;

    public Bathroom() {
        manList = new ConcurrentLinkedDeque<>();
        womanList = new ConcurrentLinkedDeque<>();
    }

    public synchronized boolean enter(Person person) {
        try {
            if(person instanceof Man) {
                if(manList.size() == 3 || womanList.size() > 0) return false;
                manList.add((Man) person);
            } else if(person instanceof  Woman) {
                if(womanList.size() == 2 || manList.size() > 0) return false;
                womanList.add((Woman) person);
            }
            return true;
        } catch (Exception e) {
            throw  e;
        }
    }

    public synchronized boolean exit(Person person) {
        try {
            if(person instanceof Man) {
                if(manList.isEmpty()) return false;
                manList.remove((Man) person);
            } else if(person instanceof  Woman) {
                if(womanList.isEmpty()) return false;
                womanList.remove((Woman) person);
            }
            return true;
        } catch (Exception e) {
            throw  e;
        }
    }

}

