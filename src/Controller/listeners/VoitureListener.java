package Controller.listeners;

import Model.Voiture;
import View.listeners.EventListener;

public interface VoitureListener extends EventListener {
    void voitureadd(MailEvent<Voiture> event);
}
