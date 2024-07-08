public class AreaEvaluada {
    private Long id;
    private String nombre;
    private String ubicacion;
    private double areaTotal;
    private double areaBosqueNativo;
    private String estado;
    private Propietario propietario;
    private Evaluador evaluador;

    public Evaluador getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(Evaluador evaluador) {
        this.evaluador = evaluador;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getAreaBosqueNativo() {
        return areaBosqueNativo;
    }

    public void setAreaBosqueNativo(double areaBosqueNativo) {
        this.areaBosqueNativo = areaBosqueNativo;
    }

    public double getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(double areaTotal) {
        this.areaTotal = areaTotal;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nombre + " (ID: " + id + ")";
    }
}

