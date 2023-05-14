
package datos;

import dominio.DescripcioncompDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DescripcioncompDaoJDBC implements DescripcioncompDao{
    private Connection conexionTransaccional;

    private static final String SQL_SELECT = "SELECT * FROM descripcioncomponente";
    private static final String SQL_INSERT = "INSERT INTO descripcioncomponente(nombre, caracteristica, descripcion, id_componente) VALUES(?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE descripcioncomponente SET nombre=?, caracteristica=?, descripcion=?, id_componente =?  WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM descripcioncomponente WHERE id=?";

    public DescripcioncompDaoJDBC() {

    }

    public DescripcioncompDaoJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }


    public List<DescripcioncompDTO> select() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        DescripcioncompDTO descripcion = null;
        List<DescripcioncompDTO> descripcions = new ArrayList<DescripcioncompDTO>();

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
//                int id = rs.getInt("id");
//                String caracteristica = rs.getString("caracteristica");
//                int descripcion = rs.getInt("descripcion");
                String nombre = rs.getString("nombre");
                
                descripcion = new DescripcioncompDTO();
//                descripcion.setId(id);
//                descripcion.setCaracteristica(caracteristica);
//                descripcion.setDescripcion(descripcion);
                descripcion.setNombre(nombre);
                
                descripcions.add(descripcion);
            }
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        return descripcions;
    }
    
    public int insert(DescripcioncompDTO descripcion) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, descripcion.getNombre());
            stmt.setString(2, descripcion.getCaracteristica());
            stmt.setString(3, descripcion.getDescripcion());
            stmt.setInt(4, descripcion.getId_componente());
            
            
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
    
    public int update(DescripcioncompDTO descripcion) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            System.out.println("Ejecutando query: " + SQL_UPDATE);
            
            stmt.setString(1, descripcion.getNombre());
            stmt.setString(2, descripcion.getCaracteristica());
            stmt.setString(3, descripcion.getDescripcion());
            stmt.setInt(4, descripcion.getId_componente());
            stmt.setInt(5, descripcion.getId());
            
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
    
    public int delete(DescripcioncompDTO descripcion) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_DELETE);
            
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, descripcion.getId());
                        
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
