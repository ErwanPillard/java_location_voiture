package Controller;

import Model.OffreReduction;

import java.sql.SQLException;
import java.util.List;

public class OffreReductionController {
    private static final OffreReductionController instance = new OffreReductionController();

    public static OffreReductionController getInstance() {
        return instance;
    }


    public List<OffreReduction> allOffresReduction() throws SQLException {
        return OffreReduction.all();
    }
}
