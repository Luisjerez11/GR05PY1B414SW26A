import java.time.LocalDate;

/**
 * Clase Reserva
 * Representa el proceso de alquiler de un vehículo por un cliente.
 * Es la entidad central que conecta Cliente y Vehiculo.
 */
class Reserva {

    // Identificador de la reserva
    private int idReserva;

    // Fechas del proceso de reserva
    private LocalDate fechaReserva;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // Estado de la reserva:
    // pendiente, activa, cancelada, finalizada
    private String estado;

    // Información económica
    private double costoTotal;

    // Tipo de reserva:
    // diaria, semanal, mensual
    private String tipoReserva;

    // Relaciones
    private Vehiculo vehiculo;
    private Cliente cliente;
    private Pago pago;

    // Relación 1:0..1 con Devolucion
    // Una reserva puede tener una devolución o ninguna
    private Devolucion devolucion;

    // Constructor vacío
    public Reserva() {
        this.estado = "pendiente";
    }

    /**
     * Crea una reserva y asigna cliente y vehículo
     */
    public void crearReserva(int idReserva,
                             LocalDate fechaInicio,
                             LocalDate fechaFin,
                             String tipoReserva,
                             Vehiculo vehiculo,
                             Cliente cliente) {

        this.idReserva = idReserva;
        this.fechaReserva = LocalDate.now();
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipoReserva = tipoReserva;

        this.estado = "activa";

        this.vehiculo = vehiculo;
        this.cliente = cliente;

        // Relación con vehículo
        if (vehiculo != null && vehiculo.estaDisponible()) {

            vehiculo.realizarReserva(this);
            vehiculo.actualizarEstado("reservado");
        }

        // Relación con cliente
        if (cliente != null) {

            cliente.realizarReserva(this);
        }

        // Cálculo básico del costo
        if (vehiculo != null) {

            this.costoTotal = vehiculo.getPrecioAlquiler();
        }
    }

    /**
     * Cancela la reserva y libera el vehículo
     */
    public void cancelarReserva() {

        this.estado = "cancelada";

        if (vehiculo != null) {

            vehiculo.actualizarEstado("disponible");
            vehiculo.eliminarReserva(this);
        }
    }

    /**
     * Finaliza la reserva
     */
    public void finalizarReserva() {

        this.estado = "finalizada";

        if (vehiculo != null) {

            vehiculo.actualizarEstado("disponible");
        }
    }

    /**
     * Cambia manualmente el estado de la reserva
     */
    public void cambiarEstado(String estado) {

        this.estado = estado;
    }

    /**
     * Relaciona una devolución con la reserva
     * Mantiene relación bidireccional
     */
    public void setDevolucion(Devolucion devolucion) {

        this.devolucion = devolucion;

        if (devolucion != null && devolucion.getReserva() != this) {

            devolucion.setReserva(this);
        }
    }

    // Getters

    public int getIdReserva() {
        return idReserva;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public String getTipoReserva() {
        return tipoReserva;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pago getPago() {
        return pago;
    }

    public Devolucion getDevolucion() {
        return devolucion;
    }

    // Setters

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
}