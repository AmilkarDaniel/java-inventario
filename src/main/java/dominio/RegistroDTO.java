package dominio;
public class RegistroDTO {
    
    private String id;
    private int numero;
    private String persona;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    @Override
    public String toString() {
        return "RegistroDTO{" + "id=" + id + ", numero=" + numero + ", persona=" + persona + '}';
    }

}
