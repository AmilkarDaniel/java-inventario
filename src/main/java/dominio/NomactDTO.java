
package dominio;


public class NomactDTO {
    private int id;
    private String nombre;
    private String accesorio;

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

    @Override
    public String toString() {
        return "NomactDTO{" + "id=" + id + ", nombre=" + nombre + ", accesorio=" + accesorio + '}';
    }
 
}
