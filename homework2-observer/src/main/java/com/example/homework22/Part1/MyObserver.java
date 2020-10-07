package com.example.homework22.Part1;

import java.util.ArrayList;
import java.util.List;

public class MyObserver {

    interface MyObserverActionListener {
        void notifyDataChanged(ArrayList<Double> data);
    }

    private static MyObserver INSTANCE;

    public static MyObserver getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new MyObserver();
        }
        return INSTANCE;
    }

    public void subscribe(MyObserverActionListener myObserverActionListener){
        if(!listeners.contains(myObserverActionListener)) {
            listeners.add(myObserverActionListener);
        }
    }

    public void unSubscribe(MyObserverActionListener myObserverActionListener){
        if(listeners.contains(myObserverActionListener)) {
            listeners.remove(myObserverActionListener);
        }
    }

    public void notify(ArrayList<Double> data){
        for(MyObserverActionListener listener : listeners){
            listener.notifyDataChanged(data);
        }
    }

    private final List<MyObserverActionListener> listeners = new ArrayList<>();

    private MyObserver() {
    }
}
