import java.util.ArrayList;
import java.util.List;

/**
 * Clase Cliente
 * Representa a la persona que utiliza el sistema para alquilar vehículos.
 * Un cliente puede registrarse y gestionar sus reservas.
 */
class Cliente {

    //Atributos
    // Identificador del cliente
    private int idCliente;

    // Datos personales
    private String nombre;
    private String cedula;
    private String telefono;
    private String email;
    private String direccion;

    // Importante para alquilar un vehículo
    private boolean licenciaConducir;

    // Relación 1:N con la clase Reserva
    // Un cliente puede tener múltiples reservas
    private List<Reserva> reservas;

    // Constructor
    public Cliente(int idCliente, String nombre, String cedula,
                   String telefono, String email,
                   String direccion, boolean licenciaConducir) {

        this.idCliente = idCliente;
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.licenciaConducir = licenciaConducir;

        this.reservas = new ArrayList<>();
    }

    /**
     * Metodo registrarCliente
     * Simula el registro del cliente en el sistema.
     */
    public void registrarse() {
        System.out.println("Cliente registrado correctamente.");
    }

    /**
     * Metodo para realizar una reserva.
     * Crea la relación entre cliente y reserva.
     * Si el cliente no cuenta con licencia no se le permite realizar una reserva
     */
    public void realizarReserva(Reserva reserva) {
        if (reserva != null && licenciaConducir && !reservas.contains(reserva)) {
            reservas.add(reserva);
            reserva.setCliente(this);
        } else {
            System.out.println("No puede realizar la reserva. Cliente sin licencia.");
        }
    }

    /**
     * Metodo para cancelar una reserva.
     * También actualiza el estado de la reserva.
     */
    public void cancelarReserva(Reserva reserva) {
        if (reserva != null && reservas.contains(reserva)) {
            reserva.cancelarReserva();
            reservas.remove(reserva);
        }
    }

    // Getters
    public int getIdCliente() {
        return idCliente;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }
}