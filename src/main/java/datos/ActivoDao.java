package datos;

import dominio.ActivoDTO;
import java.sql.SQLException;
import java.util.List;

public interface ActivoDao {
    
    public List<ActivoDTO> select() throws SQLException;
    
    public int insert(ActivoDTO activo) throws SQLException;
    
    public int update(ActivoDTO activo) throws SQLException;
    
    public int delete(ActivoDTO activo) throws SQLException;
    
}
