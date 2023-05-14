
package datos;

import dominio.NomcompDTO;
import java.sql.SQLException;
import java.util.List;


public interface NomcompDao {
    public List<NomcompDTO> select() throws SQLException;
    
    public int insert(NomcompDTO usuario) throws SQLException;
    
    public int update(NomcompDTO usuario) throws SQLException;
    
    public int delete(NomcompDTO usuario) throws SQLException;
}
