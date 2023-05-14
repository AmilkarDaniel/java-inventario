
package datos;

import dominio.NomactDTO;
import java.sql.SQLException;
import java.util.List;

public interface NomactDao {
    public List<NomactDTO> select() throws SQLException;
    
    public int insert(NomactDTO usuario) throws SQLException;
    
    public int update(NomactDTO usuario) throws SQLException;
    
    public int delete(NomactDTO usuario) throws SQLException;
}
