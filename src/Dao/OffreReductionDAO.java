package Dao;


import Model.OffreReduction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface OffreReductionDAO {
    List<OffreReduction> all() throws SQLException;

    OffreReduction createOffreReduction(ResultSet rset) throws SQLException;
}
