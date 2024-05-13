package Controller.listeners;

import Model.OffreReduction;
import Model.Voiture;

public interface OffreReductionListener {
    void offreadd(MailEvent<OffreReduction> event);
}
