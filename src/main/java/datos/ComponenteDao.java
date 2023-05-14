package datos;

import dominio.ComponenteDTO;
import java.sql.SQLException;
import java.util.List;

public interface ComponenteDao {
   
    public List<ComponenteDTO> select() throws SQLException;
    
    public int insert(ComponenteDTO componente) throws SQLException;
    
    public int update(ComponenteDTO componente) throws SQLException;
    
    public int delete(ComponenteDTO componente) throws SQLException;
}
