package com.okay;

import com.okay.inspectors.IInspector;

import java.util.ArrayList;
import java.util.List;

public class Investigator {

    private static Investigator instance = null;
    private List<IInspector> inspectorList;

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
        inspectorList = new ArrayList<IInspector>();
    }

    public void investigate(Charge charge) {
        for(IInspector inspector : inspectorList) {
            System.out.print(inspector);
        }
    }
}
