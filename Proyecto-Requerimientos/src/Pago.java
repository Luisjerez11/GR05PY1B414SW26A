import java.util.Date;

/**
 * Clase Pago
 * Representa el pago generado a partir de una reserva.
 * Relación 1 a 1 con Reserva y 1 a 1 con Factura.
 */
public class Pago {

    // Atributos
    private int idPago;
    private Date fechaPago;
    private double monto;
    private String metodoPago;
    private String estado;

    // Relación 1 a 1 con Reserva
    private Reserva reserva;

    // Relación 1 a 1 con Factura
    private Factura factura;

    // Constructor vacío
    public Pago() {
    }

    public Pago(int idPago, Date fechaPago, double monto, String metodoPago, String estado) {
        this.idPago = idPago;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
        if (reserva != null) {
            reserva.setPago(this); // relación bidireccional
        }
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
        if (factura != null) {
            factura.setPago(this); // relación bidireccional
        }
    }

    // Métodos del sistema

    /**
     * Procesa el pago de una reserva.
     */
    public void procesarPago() {
        if (reserva != null) {
            this.estado = "PROCESADO";
            System.out.println("Pago procesado para la reserva ID: "
                    + reserva.getIdReserva());
        } else {
            System.out.println("No se puede procesar sin reserva.");
        }
    }

    /**
     * Confirma el pago.
     */
    public void confirmarPago() {
        if ("PROCESADO".equals(this.estado)) {
            this.estado = "CONFIRMADO";
            System.out.println("Pago confirmado.");
        } else {
            System.out.println("El pago no está procesado.");
        }
    }

    /**
     * Cancela el pago.
     */
    public void cancelarPago() {
        this.estado = "CANCELADO";
        System.out.println("Pago cancelado.");
    }
}
