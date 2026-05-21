import java.util.Date;

/**
 * Clase Incidentes
 * Representa daños o problemas encontrados en un vehículo.
 * Un incidente pertenece a un vehículo y opcionalmente a una devolución.
 */
class Incidentes {

    private int idIncidente;
    private String descripcion;
    private double costo;
    private String tipo;
    private Date fecha;
    private String estado;

    // Relación N:1 con Vehiculo
    private Vehiculo vehiculo;

    // Relación N:1 con Devolucion
    private Devolucion devolucion;

    public Incidentes(int idIncidente, String descripcion,
                      double costo, String tipo,
                      Date fecha, String estado) {

        this.idIncidente = idIncidente;
        this.descripcion = descripcion;
        this.costo = costo;
        this.tipo = tipo;
        this.fecha = fecha;
        this.estado = estado;
    }

    /**
     * Registra el incidente
     */
    public boolean registrarIncidente() {
        System.out.println("Incidente registrado correctamente.");
        return true;
    }

    /**
     * Actualiza estado del vehículo
     */
    public void actualizarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
        System.out.println("Estado actualizado a: " + nuevoEstado);
    }

    /**
     * Calcula costo del incidente
     */
    public double calcularCosto() {
        return costo;
    }

    /**
     * Relación con Vehículo
     */
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;

        if (vehiculo != null && !vehiculo.getIncidentes().contains(this)) {
            vehiculo.agregarIncidente(this);
        }
    }

    /**
     * Relación con Devolución
     */
    public void setDevolucion(Devolucion devolucion) {
        this.devolucion = devolucion;

        if (devolucion != null && !devolucion.getIncidentes().contains(this)) {
            devolucion.agregarIncidente(this);
        }
    }

    // Getters

    public int getIdIncidente() {
        return idIncidente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getCosto() {
        return costo;
    }

    public String getTipo() {
        return tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Devolucion getDevolucion() {
        return devolucion;
    }
}