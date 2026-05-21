import java.time.LocalDate;

/**
 * Clase Reporte
 * Representa un reporte generado dentro del sistema de alquiler de autos.
 * Cada reporte es creado por un administrador.
 */
public class Reporte {

    // ATRIBUTOS
    private int idReporte;
    private String tipoReporte;
    private LocalDate fechaGeneracion;
    private String contenido;

    // Relación: cada reporte pertenece a un administrador
    private Administrador administrador;

    // CONSTRUCTOR

    public Reporte(int idReporte, String tipoReporte, String contenido) {
        this.idReporte = idReporte;
        this.tipoReporte = tipoReporte;
        this.contenido = contenido;
        this.fechaGeneracion = LocalDate.now();
    }

    // MÉTODOS

    /**
     * Genera o actualiza el contenido del reporte
     */
    public void generarReporte(String contenido) {
        this.contenido = contenido;
        this.fechaGeneracion = LocalDate.now();
    }

    /**
     * Imprime el reporte en consola
     */
    public void imprimirReporte() {
        System.out.println("=== REPORTE ===");
        System.out.println("ID: " + idReporte);
        System.out.println("Tipo: " + tipoReporte);
        System.out.println("Fecha: " + fechaGeneracion);
        System.out.println("Contenido: " + contenido);

        if (administrador != null) {
            System.out.println("Generado por: " + administrador.getNombre());
        }
    }

    // GETTERS Y SETTERS

    public int getIdReporte() {
        return idReporte;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public String getContenido() {
        return contenido;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    /**
     * En esta parte se conecta el reporte con su administrador
     */
    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }
}