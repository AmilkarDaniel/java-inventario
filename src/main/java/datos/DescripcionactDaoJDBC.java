
package datos;

import dominio.DescripcionactDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DescripcionactDaoJDBC implements DescripcionactDao{
    private Connection conexionTransaccional;

    private static final String SQL_SELECT = "SELECT * FROM descripcionactivo";
    private static final String SQL_INSERT = "INSERT INTO descripcionactivo(nombre, accesorio, descripcion, id_activo) VALUES(?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE descripcionactivo SET nombre=?, accesorio=?, descripcion=?, id_activo =?  WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM descripcionactivo WHERE id=?";

    public DescripcionactDaoJDBC() {

    }

    public DescripcionactDaoJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }


    public List<DescripcionactDTO> select() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        DescripcionactDTO descripcion = null;
        List<DescripcionactDTO> descripcions = new ArrayList<DescripcionactDTO>();

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
//                int id = rs.getInt("id");
//                String accesorio = rs.getString("accesorio");
//                int descripcion = rs.getInt("descripcion");
                String nombre = rs.getString("nombre");
                
                descripcion = new DescripcionactDTO();
//                descripcion.setId(id);
//                descripcion.setAccesorio(accesorio);
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
    
    public int insert(DescripcionactDTO descripcion) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, descripcion.getNombre());
            stmt.setString(2, descripcion.getAccesorio());
            stmt.setString(3, descripcion.getDescripcion());
            stmt.setInt(4, descripcion.getId_activo());
            
            
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
    
    public int update(DescripcionactDTO descripcion) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            System.out.println("Ejecutando query: " + SQL_UPDATE);
            
            stmt.setString(1, descripcion.getNombre());
            stmt.setString(2, descripcion.getAccesorio());
            stmt.setString(3, descripcion.getDescripcion());
            stmt.setInt(4, descripcion.getId_activo());
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
    
    public int delete(DescripcionactDTO descripcion) throws SQLException {
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
