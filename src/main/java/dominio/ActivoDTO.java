package dominio;
public class ActivoDTO {
    
    private int id_activo;
    private String registro;
    private String nombre;
    private String marca;
    private String estado;
    private String tamanio;
    private String color;
    private String material;
    private int num_cajas;
    private int num_patas;
    private int num_puertas;
    private String accesorio;
    private String accesorio2;
    private String creador;

    public int getId_activo() {
        return id_activo;
    }

    public void setId_activo(int id_activo) {
        this.id_activo = id_activo;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getNum_cajas() {
        return num_cajas;
    }

    public void setNum_cajas(int num_cajas) {
        this.num_cajas = num_cajas;
    }

    public int getNum_patas() {
        return num_patas;
    }

    public void setNum_patas(int num_patas) {
        this.num_patas = num_patas;
    }

    public int getNum_puertas() {
        return num_puertas;
    }

    public void setNum_puertas(int num_puertas) {
        this.num_puertas = num_puertas;
    }

    public String getAccesorio() {
        return accesorio;
    }

    public void setAccesorio(String accesorio) {
        this.accesorio = accesorio;
    }

    public String getAccesorio2() {
        return accesorio2;
    }

    public void setAccesorio2(String accesorio2) {
        this.accesorio2 = accesorio2;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    @Override
    public String toString() {
        return "ActivoDTO{" + "id_activo=" + id_activo + ", registro=" + registro + ", nombre=" + nombre + ", marca=" + marca + ", estado=" + estado + ", tamanio=" + tamanio + ", color=" + color + ", material=" + material + ", num_cajas=" + num_cajas + ", num_patas=" + num_patas + ", num_puertas=" + num_puertas + ", accesorio=" + accesorio + ", accesorio2=" + accesorio2 + ", creador=" + creador + '}';
    }

}
