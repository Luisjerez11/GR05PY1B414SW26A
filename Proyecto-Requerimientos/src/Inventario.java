import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Inventario
 * Representa el conjunto de vehículos del sistema de alquiler.
 * Su función es administrar la colección de vehículos.
 */
class Inventario {

    // Identificador del inventario
    private int idInventario;
    // Fecha de actualización del inventario
    private LocalDate fecha;
    // Lista de vehículos registrados en el sistema
    private List<Vehiculo> vehiculos;

    // Constructor
    public Inventario(int idInventario) {
        this.idInventario = idInventario;
        this.fecha = LocalDate.now();
        this.vehiculos = new ArrayList<>();
    }

    /**
     * Agrega un vehículo al inventario.
     */
    public void agregarVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null && !vehiculos.contains(vehiculo)) {
            vehiculos.add(vehiculo);
        }
    }

    /**
     * Elimina un vehículo del inventario.
     */
    public void eliminarVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null) {
            vehiculos.remove(vehiculo);
        }
    }

    /**
     * Busca un vehículo por su ID.
     */
    public Vehiculo buscarVehiculo(int idVehiculo) {
        for (Vehiculo v : vehiculos) {
            if (v.getIdVehiculo() == idVehiculo) {
                return v;
            }
        }
        return null;
    }

    /**
     * Consulta el estado general del inventario.
     * Los datos se calculan dinámicamente desde los vehículos.
     */
    public void consultarInventario() {
        System.out.println("Total vehículos: " + vehiculos.size());
        System.out.println("Disponibles: " + getVehiculosDisponibles());
        System.out.println("No disponibles: " + getVehiculosNoDisponibles());
    }

    /**
     * Actualiza la fecha del inventario.
     */
    public void actualizarInventario() {
        this.fecha = LocalDate.now();
    }

    /**
     * Calcula vehículos disponibles.
     */
    private int getVehiculosDisponibles() {
        int contador = 0;
        for (Vehiculo v : vehiculos) {
            if (v.estaDisponible()) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Calcula vehículos no disponibles.
     */
    private int getVehiculosNoDisponibles() {
        int contador = 0;
        for (Vehiculo v : vehiculos) {
            if (!v.estaDisponible()) {
                contador++;
            }
        }
        return contador;
    }

    // Getters
    public int getIdInventario() {
        return idInventario;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }
}