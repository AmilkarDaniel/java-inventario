
package dominio;

public class DescripcioncompDTO {
    private int id;
    private String nombre;
    private String caracteristica;
    private String descripcion;
    private int id_componente;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_componente() {
        return id_componente;
    }

    public void setId_componente(int id_componente) {
        this.id_componente = id_componente;
    }

    @Override
    public String toString() {
        return "DescripcionDTO{" + "id=" + id + ", nombre=" + nombre + ", caracteristica=" + caracteristica + ", descripcion=" + descripcion + ", id_componente=" + id_componente + '}';
    }
    
}
