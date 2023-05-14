package datos;

import dominio.UsuarioDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDaoJDBC  implements UsuarioDao{

    private Connection conexionTransaccional;

    private static final String SQL_SELECT = "SELECT fkpersona FROM usuario";
    private static final String SQL_INSERT = "INSERT INTO usuario(fkpersona, contrasenia, tipo) VALUES(?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE usuario SET fkpersona=?, contrasenia=?, tipo=?  WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM usuario WHERE id=?";

    public UsuarioDaoJDBC() {

    }

    public UsuarioDaoJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }


    public List<UsuarioDTO> select() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UsuarioDTO usuario = null;
        List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
//                int id = rs.getInt("id");
//                String contrasena = rs.getString("contrasenia");
//                int tipo = rs.getInt("tipo");
                String persona = rs.getString("fkpersona");
                
                usuario = new UsuarioDTO();
//                usuario.setId(id);
//                usuario.setContrasena(contrasena);
//                usuario.setTipo(tipo);
                usuario.setPersona(persona);
                
                usuarios.add(usuario);
            }
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        return usuarios;
    }
    
    public int insert(UsuarioDTO usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, usuario.getPersona());
            stmt.setString(2, usuario.getContrasena());
            stmt.setString(3, usuario.getTipo());
            
            
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
    
    public int update(UsuarioDTO usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            System.out.println("Ejecutando query: " + SQL_UPDATE);
            
            stmt.setString(1, usuario.getPersona());
            stmt.setString(2, usuario.getContrasena());
            stmt.setString(3, usuario.getTipo());
            stmt.setInt(4, usuario.getId());
            
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
    
    public int delete(UsuarioDTO usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_DELETE);
            
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, usuario.getId());
                        
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
