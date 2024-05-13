package Controller.listeners;

import Model.Voiture;
import View.listeners.EventListener;

public interface ClientListener extends EventListener {
    void clientadd(MailEvent<Voiture> event);
}
