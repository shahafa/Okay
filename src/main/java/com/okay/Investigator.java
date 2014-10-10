package com.okay;

import com.okay.inspectors.IInspector;

import java.util.ArrayList;
import java.util.List;

public class Investigator {

    private static Investigator instance = null;
    private List<IInspector> observersList;

    protected  Investigator() {
        registerObservers();
    }

    public static Investigator getInstance() {
        if(instance == null) {
            instance = new Investigator();
        }
        return instance;
    }

    private void registerObservers() {
        observersList = new ArrayList<IInspector>();
    }

    public void investigate(Charge charge) {
        for(IInspector observer : observersList) {
            System.out.print(observer);
        }
    }
}
