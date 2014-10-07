import java.util.ArrayList;
import java.util.List;

public class Investigator {

    private static Investigator instance = null;
    private List<IObserver> observersList;

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
        observersList = new ArrayList<IObserver>();
    }

    public void investigate(Charge charge) {
        for(IObserver observer : observersList) {
            System.out.print(observer);
        }
    }
}
