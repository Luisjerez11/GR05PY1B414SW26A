import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class PagoFacturaFrame extends JFrame {

    private JTextField txtIdPago;
    private JTextField txtIdFactura;
    private JTextField txtIdReserva;
    private JTextField txtFechaPago;
    private JTextField txtMonto;
    private JComboBox<String> cmbMetodoPago;
    private JTextField txtEstadoPago;
    private JTextField txtImpuesto;
    private JTextField txtTotalFactura;

    private String usuarioCliente;

    public PagoFacturaFrame(String usuarioCliente) {
        this.usuarioCliente = usuarioCliente;

        setTitle("Pago y Factura");
        setSize(520, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        iniciarComponentes();
        cargarReservaActiva();
    }

    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Pago y Factura");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(170, 20, 250, 30);
        panel.add(lblTitulo);

        JLabel lblIdPago = new JLabel("ID Pago:");
        lblIdPago.setBounds(40, 70, 130, 25);
        panel.add(lblIdPago);

        txtIdPago = new JTextField(PagoArchivo.generarNuevoIdPago());
        txtIdPago.setBounds(180, 70, 200, 25);
        txtIdPago.setEditable(false);
        panel.add(txtIdPago);

        JLabel lblIdFactura = new JLabel("ID Factura:");
        lblIdFactura.setBounds(40, 105, 130, 25);
        panel.add(lblIdFactura);

        txtIdFactura = new JTextField(FacturaArchivo.generarNuevoIdFactura());
        txtIdFactura.setBounds(180, 105, 200, 25);
        txtIdFactura.setEditable(false);
        panel.add(txtIdFactura);

        JLabel lblIdReserva = new JLabel("ID Reserva:");
        lblIdReserva.setBounds(40, 140, 130, 25);
        panel.add(lblIdReserva);

        txtIdReserva = new JTextField();
        txtIdReserva.setBounds(180, 140, 200, 25);
        txtIdReserva.setEditable(false);
        panel.add(txtIdReserva);

        JLabel lblFecha = new JLabel("Fecha Pago:");
        lblFecha.setBounds(40, 175, 130, 25);
        panel.add(lblFecha);

        txtFechaPago = new JTextField(LocalDate.now().toString());
        txtFechaPago.setBounds(180, 175, 200, 25);
        txtFechaPago.setEditable(false);
        panel.add(txtFechaPago);

        JLabel lblMetodo = new JLabel("Método Pago:");
        lblMetodo.setBounds(40, 210, 130, 25);
        panel.add(lblMetodo);

        cmbMetodoPago = new JComboBox<>(new String[]{
                "Efectivo",
                "Tarjeta",
                "Transferencia"
        });
        cmbMetodoPago.setBounds(180, 210, 200, 25);
        panel.add(cmbMetodoPago);

        JLabel lblMonto = new JLabel("Monto:");
        lblMonto.setBounds(40, 245, 130, 25);
        panel.add(lblMonto);

        txtMonto = new JTextField();
        txtMonto.setBounds(180, 245, 200, 25);
        txtMonto.setEditable(false);
        panel.add(txtMonto);

        JLabel lblImpuesto = new JLabel("Impuesto:");
        lblImpuesto.setBounds(40, 280, 130, 25);
        panel.add(lblImpuesto);

        txtImpuesto = new JTextField();
        txtImpuesto.setBounds(180, 280, 200, 25);
        txtImpuesto.setEditable(false);
        panel.add(txtImpuesto);

        JLabel lblTotal = new JLabel("Total Factura:");
        lblTotal.setBounds(40, 315, 130, 25);
        panel.add(lblTotal);

        txtTotalFactura = new JTextField();
        txtTotalFactura.setBounds(180, 315, 200, 25);
        txtTotalFactura.setEditable(false);
        panel.add(txtTotalFactura);

        JLabel lblEstado = new JLabel("Estado Pago:");
        lblEstado.setBounds(40, 350, 130, 25);
        panel.add(lblEstado);

        txtEstadoPago = new JTextField("Pendiente");
        txtEstadoPago.setBounds(180, 350, 200, 25);
        txtEstadoPago.setEditable(false);
        panel.add(txtEstadoPago);

        JButton btnPagar = new JButton("Procesar Pago");
        btnPagar.setBounds(120, 410, 140, 30);
        panel.add(btnPagar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(290, 410, 100, 30);
        panel.add(btnVolver);

        btnPagar.addActionListener(e -> procesarPago());
        btnVolver.addActionListener(e -> dispose());

        add(panel);
    }

    private void cargarReservaActiva() {
        for (String[] reserva : ReservaArchivo.leerReservas()) {

            if (reserva[8].equals(usuarioCliente)
                    && reserva[4].equalsIgnoreCase("Activa")
                    && !PagoArchivo.reservaPagada(reserva[0])) {

                String idReserva = reserva[0];
                double monto = Double.parseDouble(reserva[5]);
                double impuesto = monto * 0.15;
                double total = monto + impuesto;

                txtIdReserva.setText(idReserva);
                txtMonto.setText(String.valueOf(monto));
                txtImpuesto.setText(String.valueOf(impuesto));
                txtTotalFactura.setText(String.valueOf(total));

                return;
            }
        }

        JOptionPane.showMessageDialog(this,
                "No tiene reservas activas pendientes de pago",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void procesarPago() {
        String idReserva = txtIdReserva.getText();
        if (PagoArchivo.reservaPagada(idReserva)) {
            JOptionPane.showMessageDialog(this,
                    "Esta reserva ya fue pagada anteriormente",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String monto = txtMonto.getText();
        String impuesto = txtImpuesto.getText();
        String totalFactura = txtTotalFactura.getText();
        String metodoPago = cmbMetodoPago.getSelectedItem().toString();

        if (idReserva.isEmpty() || monto.isEmpty()
                || impuesto.isEmpty() || totalFactura.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "No existe una reserva activa para procesar el pago",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idPago = txtIdPago.getText();
        String idFactura = txtIdFactura.getText();
        String fecha = txtFechaPago.getText();

        boolean pagoGuardado = PagoArchivo.guardarPago(
                idPago,
                fecha,
                totalFactura,
                metodoPago,
                "Confirmado",
                idFactura,
                idReserva,
                usuarioCliente
        );

        boolean facturaGuardada = FacturaArchivo.guardarFactura(
                idFactura,
                fecha,
                totalFactura,
                impuesto,
                "Generada",
                idPago,
                idReserva,
                usuarioCliente
        );

        if (pagoGuardado && facturaGuardada) {
            txtEstadoPago.setText("Confirmado");

            JOptionPane.showMessageDialog(this,
                    "Pago confirmado y factura generada correctamente.\n" +
                            "ID Pago: " + idPago + "\n" +
                            "ID Factura: " + idFactura);

            VerFacturaFrame facturaFrame = new VerFacturaFrame(usuarioCliente);
            facturaFrame.setVisible(true);

            limpiarCampos();

        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar el pago o la factura",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtIdPago.setText(PagoArchivo.generarNuevoIdPago());
        txtIdFactura.setText(FacturaArchivo.generarNuevoIdFactura());
        txtIdReserva.setText("");
        txtFechaPago.setText(LocalDate.now().toString());
        txtMonto.setText("");
        txtImpuesto.setText("");
        txtTotalFactura.setText("");
        txtEstadoPago.setText("Pendiente");

        cargarReservaActiva();
    }
}