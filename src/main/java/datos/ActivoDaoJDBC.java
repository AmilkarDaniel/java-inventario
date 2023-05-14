package datos;

import dominio.ActivoDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivoDaoJDBC  implements ActivoDao{

    private Connection conexionTransaccional;

    private static final String SQL_SELECT = "SELECT id_activo, fkregistro, nombre, marca, estado, color, material, accesorio, creador FROM activo";
    private static final String SQL_INSERT = "INSERT INTO activo(id_activo , fkregistro, nombre, marca, estado, color, material, accesorio, creador) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE activo SET fkregistro=?, nombre=?, marca=?, estado=?, color=?, material=?, accesorio=?, creador=? WHERE id_activo = ?";
    private static final String SQL_DELETE = "DELETE FROM activo WHERE id_activo=?";

    public ActivoDaoJDBC() {

    }

    public ActivoDaoJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }

    public List<ActivoDTO> select() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ActivoDTO activo = null;
        List<ActivoDTO> activos = new ArrayList<ActivoDTO>();

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id_activo = rs.getInt("id_activo");
                String registro = rs.getString("fkregistro");
                String nombre = rs.getString("nombre");
                String marca = rs.getString("marca");
                String estado = rs.getString("estado");
                String color = rs.getString("color");
                String material = rs.getString("material");
                String accesorio = rs.getString("accesorio");
                String creador = rs.getString("creador");
                
                activo = new ActivoDTO();
                activo.setId_activo(id_activo);
                activo.setRegistro(registro);
                activo.setNombre(nombre);
                activo.setMarca(marca);
                activo.setEstado(estado);
                activo.setColor(color);
                activo.setMaterial(material);
                activo.setAccesorio(accesorio);
                activo.setCreador(creador);
                
                activos.add(activo);
            }
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        return activos;
    }
    
    public int insert(ActivoDTO activo) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, activo.getId_activo());
            stmt.setString(2, activo.getRegistro());
            stmt.setString(3, activo.getNombre());
            stmt.setString(4, activo.getMarca());
            stmt.setString(5, activo.getEstado());
            stmt.setString(6, activo.getColor());
            stmt.setString(7, activo.getMaterial());
            stmt.setString(8, activo.getAccesorio());
            stmt.setString(9, activo.getCreador());
            
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
    
    public int update(ActivoDTO activo) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            System.out.println("Ejecutando query: " + SQL_UPDATE);
            
            
            stmt.setString(1, activo.getRegistro());
            stmt.setString(2, activo.getNombre());
            stmt.setString(3, activo.getMarca());
            stmt.setString(4, activo.getEstado());
            stmt.setString(5, activo.getColor());
            stmt.setString(6, activo.getMaterial());
            stmt.setString(7, activo.getAccesorio());
            stmt.setString(8, activo.getCreador());
            stmt.setInt(9, activo.getId_activo());
                        
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
    
    public int delete(ActivoDTO activo) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_DELETE);
            
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, activo.getId_activo());
                        
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
