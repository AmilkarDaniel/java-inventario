package dominio;
public class UsuarioDTO {
    private int id;
    private String persona;
    private String contrasena;
    private String tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" + "id=" + id + ", persona=" + persona + ", contrasena=" + contrasena + ", tipo=" + tipo + '}';
    }
    
}
