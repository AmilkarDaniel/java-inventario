
package datos;

import dominio.DescripcioncompDTO;
import java.sql.SQLException;
import java.util.List;


public interface DescripcioncompDao {
    public List<DescripcioncompDTO> select() throws SQLException;
    
    public int insert(DescripcioncompDTO descripcion) throws SQLException;
    
    public int update(DescripcioncompDTO descripcion) throws SQLException;
    
    public int delete(DescripcioncompDTO descripcion) throws SQLException;
}
