
package datos;

import dominio.DescripcionactDTO;
import java.sql.SQLException;
import java.util.List;


public interface DescripcionactDao {
    public List<DescripcionactDTO> select() throws SQLException;
    
    public int insert(DescripcionactDTO descripcion) throws SQLException;
    
    public int update(DescripcionactDTO descripcion) throws SQLException;
    
    public int delete(DescripcionactDTO descripcion) throws SQLException;
}
