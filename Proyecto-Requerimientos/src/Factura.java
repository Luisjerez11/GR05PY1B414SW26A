import java.util.Date;

/**
 * Clase Factura
 * Se genera a partir de un Pago.
 */
public class Factura {

    private int idFactura;
    private Date fecha;
    private double total;
    private double impuesto;
    private String estado;

    // Relación 1 a 1 con Pago
    private Pago pago;

    public Factura() {
    }

    public Factura(int idFactura, Date fecha, double total, double impuesto, String estado) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.total = total;
        this.impuesto = impuesto;
        this.estado = estado;
    }

    // Getters y Setters
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    // Métodos

    public void generarFactura() {
        if (pago != null) {
            this.total = pago.getMonto() + (pago.getMonto() * impuesto);
            this.estado = "GENERADA";
        }
    }

    public void imprimirFactura() {
        System.out.println("----- FACTURA -----");
        System.out.println("ID: " + idFactura);
        System.out.println("Fecha: " + fecha);
        System.out.println("Estado: " + estado);
        System.out.println("Impuesto: " + impuesto);
        System.out.println("Total: " + total);

        if (pago != null) {
            System.out.println("Pago ID: " + pago.getIdPago());
        }

        System.out.println("-------------------");
    }
}
