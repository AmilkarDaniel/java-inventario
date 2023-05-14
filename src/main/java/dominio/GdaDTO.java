package dominio;
public class GdaDTO {
    private int id;
    private String gerencia;
    private String departamento;
    private String area;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGerencia() {
        return gerencia;
    }

    public void setGerencia(String gerencia) {
        this.gerencia = gerencia;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "GdaDTO{" + "id=" + id + ", gerencia=" + gerencia + ", departamento=" + departamento + ", area=" + area + '}';
    }
    
}
