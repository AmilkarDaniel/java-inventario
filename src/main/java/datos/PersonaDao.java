package datos;

import dominio.PersonaDTO;
import java.sql.SQLException;
import java.util.List;

public interface PersonaDao {
    
    public List<PersonaDTO> select() throws SQLException;
    
    public int insert(PersonaDTO activo) throws SQLException;
    
    public int update(PersonaDTO activo) throws SQLException;
    
    public int delete(PersonaDTO activo) throws SQLException;
    
}

