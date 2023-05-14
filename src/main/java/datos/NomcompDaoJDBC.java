
package datos;

import dominio.NomcompDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class NomcompDaoJDBC implements NomcompDao {
    private Connection conexionTransaccional;

    private static final String SQL_SELECT = "SELECT * FROM nomcomp";
    private static final String SQL_INSERT = "INSERT INTO nomcomp(nombre, caracteristica) VALUES(?, ?)";
    private static final String SQL_UPDATE = "UPDATE nomcomp SET nombre=?, caracteristica=?  WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM nomcomp WHERE id=?";

    public NomcompDaoJDBC() {

    }

    public NomcompDaoJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }


    public List<NomcompDTO> select() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        NomcompDTO nomcomp = null;
        List<NomcompDTO> nomcomps = new ArrayList<NomcompDTO>();

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
//                int id = rs.getInt("id");
//                String contrasena = rs.getString("nombre");
//                int caracteristica = rs.getInt("caracteristica");
                int id = rs.getInt("id");
                
                nomcomp = new NomcompDTO();
//                nomcomp.setId(id);
//                nomcomp.setNombre(contrasena);
//                nomcomp.setCaracteristica(caracteristica);
                nomcomp.setId(id);
                
                nomcomps.add(nomcomp);
            }
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        return nomcomps;
    }
    
    public int insert(NomcompDTO nomcomp) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, nomcomp.getNombre());
            stmt.setString(2, nomcomp.getCaracteristica());
            
            
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
    
    public int update(NomcompDTO nomcomp) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            System.out.println("Ejecutando query: " + SQL_UPDATE);
            
            
            stmt.setString(1, nomcomp.getNombre());
            stmt.setString(2, nomcomp.getCaracteristica());
            stmt.setInt(3, nomcomp.getId());
            
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
    
    public int delete(NomcompDTO nomcomp) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_DELETE);
            
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, nomcomp.getId());
                        
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
