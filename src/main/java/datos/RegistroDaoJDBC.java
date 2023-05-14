package datos;

import dominio.RegistroDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistroDaoJDBC  implements RegistroDao{
    
    private Connection conexionTransaccional;
    
    private static final String SQL_SELECT = "SELECT id, fecha, numero, fkpersona FROM registro";
    
    //EN INSERT EL ID TAMBIEN SE INSERTA NO ES AUTOINCREMENTABLE
    private static final String SQL_INSERT = "INSERT INTO registro(id, numero, fkpersona) VALUES(?, ?, ?)";
    
    //UPDATE TAMBIEN SE USA EL ID PARA MODIFICAR HAY QUE VERLO MAS A FONDO
    private static final String SQL_UPDATE = "UPDATE registro SET id=?, numero=?, fkpersona=? WHERE id = ?";
    
    private static final String SQL_DELETE = "DELETE FROM registro WHERE id=?";
    
    public RegistroDaoJDBC(){
        
    }
    
    public RegistroDaoJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }
    
    public List<RegistroDTO> select() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        RegistroDTO registro = null;
        List<RegistroDTO> registros = new ArrayList<RegistroDTO>();

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                int numero = rs.getInt("numero");
                String persona = rs.getString("fkpersona");
                
                registro = new RegistroDTO();
                registro.setId(id);
                registro.setNumero(numero);
                registro.setPersona(persona);
                
                registros.add(registro);
            }
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        return registros;
    }
    
    public int insert(RegistroDTO registro) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, registro.getId());
            stmt.setInt(2, registro.getNumero());
            stmt.setString(3, registro.getPersona());
            
            
            System.out.println("ejecutando query: "+ SQL_INSERT);
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados: " + rows);
        } finally {
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        return rows;

    }
    
    public int update(RegistroDTO registro) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_UPDATE);
                                 
            stmt.setString(1, registro.getId());
            stmt.setInt(2, registro.getNumero());
            stmt.setString(3, registro.getPersona());
            stmt.setString(4, registro.getId());
            
            rows = stmt.executeUpdate();
            System.out.println("Registros actualizado: " + rows);
            
        } finally {
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        return rows;

    }
    
    public int delete(RegistroDTO registro) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_DELETE);
            
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setString(1, registro.getId());
                        
            rows = stmt.executeUpdate();
            System.out.println("Registros actualizados: " + rows);
            
        } finally {
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }
}   

