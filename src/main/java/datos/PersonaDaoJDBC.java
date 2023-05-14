package datos;

import dominio.PersonaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaDaoJDBC implements PersonaDao{
    private Connection conexionTransaccional;

    private static final String SQL_SELECT = "SELECT cod_persona, nombre, apellido, telefono, direccion, email, gerencia, departamento, area FROM persona";
    private static final String SQL_INSERT = "INSERT INTO persona(cod_persona, nombre, apellido, telefono, direccion, email, gerencia, departamento, area) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE persona SET cod_persona=?, nombre=? , apellido=? , telefono=? , direccion=?, email=?, gerencia=?, departamento=?, area=? WHERE cod_persona = ?";
    private static final String SQL_DELETE = "DELETE FROM persona WHERE cod_persona=?";
    
    public PersonaDaoJDBC() {

    }

    public PersonaDaoJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }

    public List<PersonaDTO> select() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        PersonaDTO persona = null;
        List<PersonaDTO> personas = new ArrayList<PersonaDTO>();

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String codigo = rs.getString("cod_persona");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString("direccion");
                String email = rs.getString("email");
                String gerencia = rs.getString("gerencia");
                String departamento = rs.getString("departamento");
                String area = rs.getString("area");
                
                persona = new PersonaDTO();
                persona.setCodigo(codigo);
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                persona.setTelefono(telefono);
                persona.setDireccion(direccion);
                persona.setEmail(email);
                persona.setGerencia(gerencia);
                persona.setDepartamento(departamento);
                persona.setArea(area);
                
                personas.add(persona);
            }
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        return personas;
    }
    
    public int insert(PersonaDTO persona) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, persona.getCodigo());
            stmt.setString(2, persona.getNombre());
            stmt.setString(3, persona.getApellido());
            stmt.setInt(4, persona.getTelefono());
            stmt.setString(5, persona.getDireccion());
            stmt.setString(6, persona.getEmail());
            stmt.setString(7, persona.getGerencia());
            stmt.setString(8, persona.getDepartamento());
            stmt.setString(9, persona.getArea());
            
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
    
    public int update(PersonaDTO persona) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, persona.getCodigo());
            stmt.setString(2, persona.getNombre());
            stmt.setString(3, persona.getApellido());
            stmt.setInt(4, persona.getTelefono());
            stmt.setString(5, persona.getDireccion());
            stmt.setString(6, persona.getEmail());
            stmt.setString(7, persona.getGerencia());
            stmt.setString(8, persona.getDepartamento());
            stmt.setString(9, persona.getArea());
            stmt.setString(10, persona.getCodigo());
            
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
    
    public int delete(PersonaDTO persona) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_DELETE);
            
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setString(1, persona.getCodigo());
                        
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
