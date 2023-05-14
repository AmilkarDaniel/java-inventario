package datos;

import dominio.NomactDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NomactDaoJDBC implements NomactDao {

    private Connection conexionTransaccional;

    private static final String SQL_SELECT = "SELECT * FROM nomact";
    private static final String SQL_INSERT = "INSERT INTO nomact(nombre, accesorio) VALUES(?, ?)";
    private static final String SQL_UPDATE = "UPDATE nomact SET nombre=?, accesorio=?  WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM nomact WHERE id=?";

    public NomactDaoJDBC() {

    }

    public NomactDaoJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }

    public List<NomactDTO> select() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        NomactDTO nomact = null;
        List<NomactDTO> nomacts = new ArrayList<NomactDTO>();

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
//                int id = rs.getInt("id");
//                String contrasena = rs.getString("nombre");
//                int accesorio = rs.getInt("accesorio");
                int id = rs.getInt("id");

                nomact = new NomactDTO();
//                nomact.setId(id);
//                nomact.setNombre(contrasena);
//                nomact.setAccesorio(accesorio);
                nomact.setId(id);

                nomacts.add(nomact);
            }
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        return nomacts;
    }

    public int insert(NomactDTO nomact) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, nomact.getNombre());
            stmt.setString(2, nomact.getAccesorio());

            System.out.println("ejecutando query: " + SQL_INSERT);
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

    public int update(NomactDTO nomact) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            System.out.println("Ejecutando query: " + SQL_UPDATE);

            stmt.setString(1, nomact.getNombre());
            stmt.setString(2, nomact.getAccesorio());
            stmt.setInt(3, nomact.getId());

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

    public int delete(NomactDTO nomact) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_DELETE);

            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, nomact.getId());

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
