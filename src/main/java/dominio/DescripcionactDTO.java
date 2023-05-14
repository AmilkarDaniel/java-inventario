
package dominio;

public class DescripcionactDTO {
    private int id;
    private String nombre;
    private String accesorio;
    private String descripcion;
    private int id_activo;

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

    public String getAccesorio() {
        return accesorio;
    }

    public void setAccesorio(String accesorio) {
        this.accesorio = accesorio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_activo() {
        return id_activo;
    }

    public void setId_activo(int id_activo) {
        this.id_activo = id_activo;
    }

    @Override
    public String toString() {
        return "DescripcionactDTO{" + "id=" + id + ", nombre=" + nombre + ", accesorio=" + accesorio + ", descripcion=" + descripcion + ", id_activo=" + id_activo + '}';
    }

}
