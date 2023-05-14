
package dominio;


public class NomcompDTO {
    private int id;
    private String nombre;
    private String caracteristica;

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

    @Override
    public String toString() {
        return "NomcompDTO{" + "id=" + id + ", nombre=" + nombre + ", caracteristica=" + caracteristica + '}';
    }

    
    
}
