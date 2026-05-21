import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RealizarReservaFrame extends JFrame {

    private JTextField txtIdReserva;
    private JTextField txtIdVehiculo;
    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;
    private JComboBox<String> cmbTipoReserva;

    private String usuarioCliente;

    public RealizarReservaFrame(String usuarioCliente) {
        this.usuarioCliente = usuarioCliente;

        setTitle("Realizar Reserva");
        setSize(430, 360);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Realizar Reserva");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(120, 20, 220, 30);
        panel.add(lblTitulo);

        JLabel lblIdReserva = new JLabel("ID Reserva:");
        lblIdReserva.setBounds(40, 70, 120, 25);
        panel.add(lblIdReserva);

        txtIdReserva = new JTextField(ReservaArchivo.generarNuevoIdReserva());
        txtIdReserva.setEditable(false);
        txtIdReserva.setBounds(170, 70, 180, 25);
        panel.add(txtIdReserva);

        JLabel lblIdVehiculo = new JLabel("ID Vehículo:");
        lblIdVehiculo.setBounds(40, 105, 120, 25);
        panel.add(lblIdVehiculo);

        txtIdVehiculo = new JTextField();
        txtIdVehiculo.setBounds(170, 105, 180, 25);
        panel.add(txtIdVehiculo);

        JLabel lblFechaInicio = new JLabel("Fecha Inicio:");
        lblFechaInicio.setBounds(40, 140, 120, 25);
        panel.add(lblFechaInicio);

        txtFechaInicio = new JTextField("2026-05-20");
        txtFechaInicio.setBounds(170, 140, 180, 25);
        panel.add(txtFechaInicio);

        JLabel lblFechaFin = new JLabel("Fecha Fin:");
        lblFechaFin.setBounds(40, 175, 120, 25);
        panel.add(lblFechaFin);

        txtFechaFin = new JTextField("2026-05-22");
        txtFechaFin.setBounds(170, 175, 180, 25);
        panel.add(txtFechaFin);

        JLabel lblTipo = new JLabel("Tipo Reserva:");
        lblTipo.setBounds(40, 210, 120, 25);
        panel.add(lblTipo);

        cmbTipoReserva = new JComboBox<>(new String[]{"Diaria", "Semanal", "Mensual"});
        cmbTipoReserva.setBounds(170, 210, 180, 25);
        panel.add(cmbTipoReserva);

        JButton btnReservar = new JButton("Reservar");
        btnReservar.setBounds(90, 265, 110, 30);
        panel.add(btnReservar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(220, 265, 110, 30);
        panel.add(btnCancelar);

        btnReservar.addActionListener(e -> realizarReserva());
        btnCancelar.addActionListener(e -> dispose());

        add(panel);
    }

    private void realizarReserva() {
        String idReserva = txtIdReserva.getText();
        String idVehiculo = txtIdVehiculo.getText();
        String fechaInicio = txtFechaInicio.getText();
        String fechaFin = txtFechaFin.getText();
        String tipoReserva = cmbTipoReserva.getSelectedItem().toString();

        if (idReserva.isEmpty() || idVehiculo.isEmpty()
                || fechaInicio.isEmpty() || fechaFin.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Complete todos los campos",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] vehiculoSeleccionado = null;

        for (String[] vehiculo : VehiculoArchivo.leerVehiculos()) {
            if (vehiculo[0].equals(idVehiculo)) {
                vehiculoSeleccionado = vehiculo;
                break;
            }
        }

        if (vehiculoSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "No existe un vehículo con ese ID",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!vehiculoSeleccionado[5].equalsIgnoreCase("Disponible")) {
            JOptionPane.showMessageDialog(this,
                    "El vehículo no está disponible",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {

            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fechaReserva = LocalDate.now();

            int dias = 0;
            LocalDate fin = inicio;

            if (tipoReserva.equalsIgnoreCase("Diaria")) {
                dias = 1;
                fin = inicio.plusDays(1);
            }
            else if (tipoReserva.equalsIgnoreCase("Semanal")) {
                dias = 7;
                fin = inicio.plusDays(7);
            }
            else if (tipoReserva.equalsIgnoreCase("Mensual")) {
                dias = 30;
                fin = inicio.plusDays(30);
            }

            txtFechaFin.setText(fin.toString());

            double precioAlquiler = Double.parseDouble(vehiculoSeleccionado[6]);
            double costoTotal = dias * precioAlquiler;

            fechaFin = fin.toString();

            boolean guardado = ReservaArchivo.guardarReserva(
                    idReserva,
                    fechaInicio,
                    fechaFin,
                    fechaReserva.toString(),
                    "Activa",
                    String.valueOf(costoTotal),
                    tipoReserva,
                    idVehiculo,
                    usuarioCliente
            );

            if (guardado) {
                VehiculoArchivo.actualizarEstadoVehiculo(idVehiculo, "Alquilado");

                JOptionPane.showMessageDialog(this,
                        "Reserva realizada correctamente\nCosto total: $" + costoTotal);

                txtIdReserva.setText(ReservaArchivo.generarNuevoIdReserva());
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Ya existe una reserva con ese ID",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Revise las fechas. Use el formato: yyyy-MM-dd",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}