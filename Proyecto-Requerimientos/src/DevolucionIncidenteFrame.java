import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class DevolucionIncidenteFrame extends JFrame {

    private JTextField txtIdDevolucion;
    private JTextField txtIdReserva;
    private JTextField txtFechaDevolucion;
    private JComboBox<String> cmbEstadoVehiculo;
    private JTextArea txtObservaciones;

    private JTextField txtIdIncidente;
    private JTextArea txtDescripcionIncidente;
    private JTextField txtCostoReparacion;

    public DevolucionIncidenteFrame() {
        setTitle("Devoluciones e Incidentes");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Devoluciones e Incidentes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(180, 20, 350, 30);
        panel.add(lblTitulo);

        JLabel lblIdDevolucion = new JLabel("ID Devolución:");
        lblIdDevolucion.setBounds(40, 70, 130, 25);
        panel.add(lblIdDevolucion);

        txtIdDevolucion = new JTextField(DevolucionArchivo.generarNuevoIdDevolucion());
        txtIdDevolucion.setBounds(180, 70, 180, 25);
        txtIdDevolucion.setEditable(false);
        panel.add(txtIdDevolucion);

        JLabel lblIdReserva = new JLabel("ID Reserva:");
        lblIdReserva.setBounds(40, 110, 130, 25);
        panel.add(lblIdReserva);

        txtIdReserva = new JTextField();
        txtIdReserva.setBounds(180, 110, 180, 25);
        panel.add(txtIdReserva);

        JLabel lblFecha = new JLabel("Fecha Devolución:");
        lblFecha.setBounds(40, 150, 130, 25);
        panel.add(lblFecha);

        txtFechaDevolucion = new JTextField(LocalDate.now().toString());
        txtFechaDevolucion.setBounds(180, 150, 180, 25);
        txtFechaDevolucion.setEditable(false);
        panel.add(txtFechaDevolucion);

        JLabel lblEstado = new JLabel("Estado Vehículo:");
        lblEstado.setBounds(40, 190, 130, 25);
        panel.add(lblEstado);

        cmbEstadoVehiculo = new JComboBox<>(new String[]{"Bueno", "Con daños"});
        cmbEstadoVehiculo.setBounds(180, 190, 180, 25);
        panel.add(cmbEstadoVehiculo);

        JLabel lblObservaciones = new JLabel("Observaciones:");
        lblObservaciones.setBounds(40, 230, 130, 25);
        panel.add(lblObservaciones);

        txtObservaciones = new JTextArea();
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        scrollObs.setBounds(180, 230, 380, 70);
        panel.add(scrollObs);

        JLabel lblIncidente = new JLabel("Datos del Incidente");
        lblIncidente.setFont(new Font("Arial", Font.BOLD, 18));
        lblIncidente.setBounds(230, 320, 250, 25);
        panel.add(lblIncidente);

        JLabel lblIdIncidente = new JLabel("ID Incidente:");
        lblIdIncidente.setBounds(40, 360, 130, 25);
        panel.add(lblIdIncidente);

        txtIdIncidente = new JTextField(IncidenteArchivo.generarNuevoIdIncidente());
        txtIdIncidente.setBounds(180, 360, 180, 25);
        txtIdIncidente.setEditable(false);
        panel.add(txtIdIncidente);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(40, 400, 130, 25);
        panel.add(lblDescripcion);

        txtDescripcionIncidente = new JTextArea();
        JScrollPane scrollDesc = new JScrollPane(txtDescripcionIncidente);
        scrollDesc.setBounds(180, 400, 380, 70);
        panel.add(scrollDesc);

        JLabel lblCosto = new JLabel("Costo Reparación:");
        lblCosto.setBounds(40, 485, 130, 25);
        panel.add(lblCosto);

        txtCostoReparacion = new JTextField();
        txtCostoReparacion.setBounds(180, 485, 180, 25);
        panel.add(txtCostoReparacion);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(100, 545, 120, 30);
        panel.add(btnRegistrar);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(260, 545, 120, 30);
        panel.add(btnLimpiar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(420, 545, 120, 30);
        panel.add(btnVolver);

        cmbEstadoVehiculo.addActionListener(e -> actualizarCamposIncidente());
        btnRegistrar.addActionListener(e -> registrarDevolucion());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnVolver.addActionListener(e -> dispose());

        actualizarCamposIncidente();

        add(panel);
    }

    private void actualizarCamposIncidente() {
        boolean conDanios = cmbEstadoVehiculo.getSelectedItem().toString().equals("Con daños");

        txtIdIncidente.setEnabled(conDanios);
        txtDescripcionIncidente.setEnabled(conDanios);
        txtCostoReparacion.setEnabled(conDanios);
    }

    private void registrarDevolucion() {
        String idDevolucion = txtIdDevolucion.getText();
        String idReserva = txtIdReserva.getText();
        String fechaDevolucion = txtFechaDevolucion.getText();
        String estadoVehiculo = cmbEstadoVehiculo.getSelectedItem().toString();
        String observaciones = txtObservaciones.getText();

        if (idReserva.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese el ID de la reserva",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idVehiculo = null;
        String usuarioCliente = null;
        boolean reservaEncontrada = false;
        boolean reservaActiva = false;

        for (String[] reserva : ReservaArchivo.leerReservas()) {
            if (reserva[0].equals(idReserva)) {
                reservaEncontrada = true;
                idVehiculo = reserva[7];
                usuarioCliente = reserva[8];

                if (reserva[4].equalsIgnoreCase("Activa")) {
                    reservaActiva = true;
                }

                break;
            }
        }

        if (!reservaEncontrada) {
            JOptionPane.showMessageDialog(this,
                    "No existe una reserva con ese ID",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!reservaActiva) {
            JOptionPane.showMessageDialog(this,
                    "La reserva no está activa o ya fue finalizada/cancelada",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean devolucionGuardada = DevolucionArchivo.guardarDevolucion(
                idDevolucion,
                idReserva,
                idVehiculo,
                usuarioCliente,
                fechaDevolucion,
                estadoVehiculo,
                observaciones
        );

        if (!devolucionGuardada) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar la devolución",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (estadoVehiculo.equals("Con daños")) {
            String descripcion = txtDescripcionIncidente.getText();
            String costoReparacion = txtCostoReparacion.getText();

            if (descripcion.isEmpty() || costoReparacion.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Complete la descripción y costo del incidente",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            IncidenteArchivo.guardarIncidente(
                    txtIdIncidente.getText(),
                    idDevolucion,
                    idVehiculo,
                    fechaDevolucion,
                    descripcion,
                    costoReparacion
            );

            VehiculoArchivo.actualizarEstadoVehiculo(idVehiculo, "Mantenimiento");
        } else {
            VehiculoArchivo.actualizarEstadoVehiculo(idVehiculo, "Disponible");
        }

        ReservaArchivo.actualizarEstadoReserva(idReserva, "Finalizada");

        JOptionPane.showMessageDialog(this,
                "Devolución registrada correctamente");

        limpiarCampos();
    }

    private void limpiarCampos() {
        txtIdDevolucion.setText(DevolucionArchivo.generarNuevoIdDevolucion());
        txtIdReserva.setText("");
        txtFechaDevolucion.setText(LocalDate.now().toString());
        cmbEstadoVehiculo.setSelectedIndex(0);
        txtObservaciones.setText("");

        txtIdIncidente.setText(IncidenteArchivo.generarNuevoIdIncidente());
        txtDescripcionIncidente.setText("");
        txtCostoReparacion.setText("");

        actualizarCamposIncidente();
    }
}