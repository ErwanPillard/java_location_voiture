package Controller;

import Controller.listeners.MailEvent;
import Controller.listeners.VoitureListener;
import Model.Voiture;
import View.Employe.VoitureJTableView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class VoitureController {

    private List<VoitureListener> voitureListeners = new ArrayList<VoitureListener>();

    private static final VoitureController instance = new VoitureController();
    public static VoitureController getInstance() {
        return instance;
    }

    public void addVoiture(Voiture voiture) throws SQLException {
        if (voiture != null){
            voiture.add(voiture);
            notifyListeners(voiture);
        }
    }

    public List<Voiture> allVoitures() throws SQLException{
        return Voiture.all();
    }

    public boolean immatExists(String immat)throws SQLException {return Voiture.immatExists(immat);}

    public synchronized void addUserListener(VoitureListener l) {
        if(!voitureListeners.contains(l)) {
            voitureListeners.add(l);
        }
    }

    private void notifyListeners(Voiture voiture) {
        MailEvent<Voiture> event = new MailEvent<Voiture>(voiture);
        for (VoitureListener listener : voitureListeners) {
            listener.voitureadd(event);
        }
    }


}
