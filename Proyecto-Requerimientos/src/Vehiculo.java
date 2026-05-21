import java.util.ArrayList;
import java.util.List;

/**
 * Clase Vehiculo
 * Representa un vehículo disponible dentro del sistema de alquiler.
 */
class Vehiculo {

    private int idVehiculo;

    private String placa;
    private String modelo;
    private String marca;
    private int anio;
    private String color;

    private String estado;
    private double precioAlquiler;
    private int kilometraje;

    // Relación 1:N con Reserva
    private List<Reserva> reservas;

    // Relación 1:N con Incidentes
    private List<Incidentes> incidentes;

    public Vehiculo(int idVehiculo, String placa, String modelo,
                    String marca, int anio, String color,
                    double precioAlquiler, int kilometraje) {

        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.anio = anio;
        this.color = color;
        this.precioAlquiler = precioAlquiler;
        this.kilometraje = kilometraje;

        this.estado = "disponible";

        this.reservas = new ArrayList<>();
        this.incidentes = new ArrayList<>();
    }

    /**
     * Verifica disponibilidad del vehículo
     */
    public boolean estaDisponible() {
        return estado.equalsIgnoreCase("disponible");
    }

    /**
     * Actualiza estado del vehículo
     */
    public void actualizarEstado(String estado) {
        if (estado != null) {
            this.estado = estado;
        }
    }

    /**
     * Agrega una reserva
     */
    public void realizarReserva(Reserva reserva) {
        if (reserva != null && !reservas.contains(reserva)) {
            reservas.add(reserva);
        }
    }

    public void eliminarReserva(Reserva reserva) {
        if (reserva != null) {
            reservas.remove(reserva);
        }
    }

    // INCIDENTES

    /**
     * Agrega un incidente al vehículo
     * Mantiene relación bidireccional con Incidentes
     */
    public void agregarIncidente(Incidentes incidente) {

        if (incidente != null && !incidentes.contains(incidente)) {
            incidentes.add(incidente);

            if (incidente.getVehiculo() != this) {
                incidente.setVehiculo(this);
            }
        }
    }

    public void eliminarIncidente(Incidentes incidente) {
        if (incidente != null) {
            incidentes.remove(incidente);
        }
    }

    // Getters

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public String getEstado() {
        return estado;
    }

    public double getPrecioAlquiler() {
        return precioAlquiler;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public List<Incidentes> getIncidentes() {
        return incidentes;
    }
}