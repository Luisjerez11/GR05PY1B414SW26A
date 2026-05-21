import java.util.ArrayList;
import java.util.List;

/**
 * Clase Administrador
 * Representa al usuario que gestiona el sistema de alquiler de autos.
 * Puede gestionar inventario, reservas y generar reportes.
 */
class Administrador {

    // ATRIBUTOS

    private int idAdministrador;
    private String nombre;
    private String password;
    private String cedula;
    private String email;

    // Relación 1:1 con Inventario
    private Inventario inventario;

    // Relación 1:N con Reservas
    private List<Reserva> reservas;

    // Relación 1:N con Reportes
    private List<Reporte> reportes;

    // CONSTRUCTOR

    public Administrador(int idAdministrador, String nombre,
                         String password, String cedula, String email) {

        this.idAdministrador = idAdministrador;
        this.nombre = nombre;
        this.password = password;
        this.cedula = cedula;
        this.email = email;

        this.reservas = new ArrayList<>();
        this.reportes = new ArrayList<>();
    }

    // MÉTODOS

    /**
     * Administra el inventario de vehículos
     */
    public void administrarVehiculos() {
        System.out.println("Gestionando inventario...");
    }

    /**
     * Genera un reporte y lo asocia al administrador
     */
    public void administrarReportes(Reporte reporte) {
        if (reporte != null && !reportes.contains(reporte)) {

            reportes.add(reporte);   // Admin guarda el reporte
            reporte.setAdministrador(this); // Reporte sabe quién lo creó
        }
    }

    /**
     * Administra lascreservas del sistema
     */
    public void administrarReservas(Reserva reserva) {
        if (reserva != null && !reservas.contains(reserva)) {
            reservas.add(reserva);
        }
    }

    // GETTERS

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public String getNombre() {
        return nombre;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public List<Reporte> getReportes() {
        return reportes;
    }
}