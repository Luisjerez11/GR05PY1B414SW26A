import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase Devolucion
 * Representa la devolución de un vehículo luego de finalizar una reserva.
 * Una devolución puede tener varios incidentes asociados.
 */
class Devolucion {

    // Identificador de la devolución
    private int idDevolucion;

    // Fecha de devolución del vehículo
    private Date fechaDevolucion;

    // Estado del vehículo
    // Valores recomendados: "Bueno" o "Dañado"
    private String estadoVehiculo;

    // Observaciones adicionales sobre la devolución
    private String observaciones;

    // Relación 1:N con Incidentes
    // Una devolución puede tener cero o muchos incidentes
    private List<Incidentes> incidentes;

    // Relación 1:1 con Reserva
    // Una devolución pertenece a una reserva
    private Reserva reserva;

    // Constructor
    public Devolucion(int idDevolucion,
                      Date fechaDevolucion,
                      String estadoVehiculo,
                      String observaciones) {

        this.idDevolucion = idDevolucion;
        this.fechaDevolucion = fechaDevolucion;
        this.estadoVehiculo = estadoVehiculo;

        // Si el vehículo está en buen estado
        // no se almacenan observaciones
        if (estadoVehiculo.equalsIgnoreCase("Bueno")) {
            this.observaciones = "";
        } else {
            this.observaciones = observaciones;
        }

        this.incidentes = new ArrayList<>();
    }

    /**
     * Registra la devolución en el sistema
     */
    public boolean registrarDevolucion() {

        System.out.println("Devolución registrada correctamente.");

        return true;
    }

    /**
     * Retorna el estado del vehículo
     */
    public String revisarEstado() {

        return estadoVehiculo;
    }

    /**
     * Agrega un incidente a la devolución
     * Mantiene relación bidireccional
     */
    public void agregarIncidente(Incidentes incidente) {

        if (incidente != null && !incidentes.contains(incidente)) {

            incidentes.add(incidente);

            // Relación bidireccional
            if (incidente.getDevolucion() != this) {
                incidente.setDevolucion(this);
            }
        }
    }

    /**
     * Elimina un incidente de la devolución
     */
    public void eliminarIncidente(Incidentes incidente) {

        if (incidente != null) {
            incidentes.remove(incidente);
        }
    }

    /**
     * Relaciona la devolución con una reserva
     * Mantiene relación bidireccional
     */
    public void setReserva(Reserva reserva) {

        this.reserva = reserva;

        if (reserva != null && reserva.getDevolucion() != this) {
            reserva.setDevolucion(this);
        }
    }

    // Getters

    public int getIdDevolucion() {
        return idDevolucion;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public String getEstadoVehiculo() {
        return estadoVehiculo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public List<Incidentes> getIncidentes() {
        return incidentes;
    }

    public Reserva getReserva() {
        return reserva;
    }
}