package datos;

import dominio.ComponenteDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComponenteDaoJDBC implements ComponenteDao{
   
    private Connection conexionTransaccional;
    
    private static final String SQL_SELECT = "SELECT id, fkregistro, nombre, marca, estado, color, descripcion, creador FROM componente";
    private static final String SQL_INSERT = "INSERT INTO componente(id, fkregistro, nombre, marca, estado, color, descripcion, creador) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE componente SET fkregistro=?, nombre=?, marca=?, estado=?, color=?, descripcion=?, creador=? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM componente WHERE id=?";
    
    public ComponenteDaoJDBC() {
        
    }
    
    public ComponenteDaoJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }
    
    
    
    
    
    
    public List<ComponenteDTO> select() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ComponenteDTO componente = null;
        List<ComponenteDTO> componentes = new ArrayList<ComponenteDTO>();

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String registro = rs.getString("fkregistro");
                String nombre = rs.getString("nombre");
                String marca = rs.getString("marca");
                String estado = rs.getString("estado");
                String color = rs.getString("color");
                String descripcion = rs.getString("descripcion");
                String creador = rs.getString("creador");
                
                componente = new ComponenteDTO();
                componente.setId(id);
                componente.setRegistro(registro);
                componente.setNombre(nombre);
                componente.setMarca(marca);
                componente.setEstado(estado);
                componente.setColor(color);
                componente.setDescripcion(descripcion);
                componente.setCreador(creador);
                
                componentes.add(componente);
            }
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        return componentes;
    }
    
    public int insert(ComponenteDTO componente) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, componente.getId());
            stmt.setString(2, componente.getRegistro());
            stmt.setString(3, componente.getNombre());
            stmt.setString(4, componente.getMarca());
            stmt.setString(5, componente.getEstado());
            stmt.setString(6, componente.getColor());
            stmt.setString(7, componente.getDescripcion());
            stmt.setString(8, componente.getCreador());
            
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
    
    public int update(ComponenteDTO componente) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            System.out.println("Ejecutando query: " + SQL_UPDATE);
            
            stmt.setString(1, componente.getRegistro());
            stmt.setString(2, componente.getNombre());
            stmt.setString(3, componente.getMarca());
            stmt.setString(4, componente.getEstado());
            stmt.setString(5, componente.getColor());
            stmt.setString(6, componente.getDescripcion());
            stmt.setString(7, componente.getCreador());
            stmt.setInt(8, componente.getId());
            
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
    
    public int delete(ComponenteDTO componente) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_DELETE);
            
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, componente.getId());
                        
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
