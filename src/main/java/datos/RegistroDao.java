package datos;

import dominio.RegistroDTO;
import java.sql.SQLException;
import java.util.List;

public interface RegistroDao {
    
    public List<RegistroDTO> select() throws SQLException;
    
    public int insert(RegistroDTO activo) throws SQLException;
    
    public int update(RegistroDTO activo) throws SQLException;
    
    public int delete(RegistroDTO activo) throws SQLException;
}
