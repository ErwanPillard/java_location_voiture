package Controller;

import Controller.listeners.MailEvent;
import Controller.listeners.OffreReductionListener;
import Model.OffreReduction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OffreReductionController {
    private static final OffreReductionController instance = new OffreReductionController();

    private List<OffreReductionListener> offreReductionListeners = new ArrayList<>();

    public static OffreReductionController getInstance() {
        return instance;
    }

    public List<OffreReduction> allOffresReduction() throws SQLException {
        return OffreReduction.all();
    }

    public void add(OffreReduction offreReduction) throws SQLException {
        if (offreReduction != null) {
            offreReduction.add(offreReduction);
            notifyListeners(offreReduction);
        }
    }

    private void notifyListeners(OffreReduction offreReduction) {
        MailEvent<OffreReduction> event = new MailEvent<>(offreReduction);
        for (OffreReductionListener listener : offreReductionListeners) {
            listener.offreadd(event);
        }
    }

    public synchronized void addOffreListener(OffreReductionListener l) {
        if (!offreReductionListeners.contains(l)) {
            offreReductionListeners.add(l);
        }
    }
}
