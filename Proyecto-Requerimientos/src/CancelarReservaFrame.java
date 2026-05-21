import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CancelarReservaFrame extends JFrame {

    private JTable tablaReservas;
    private DefaultTableModel modeloTabla;
    private JTextField txtIdReserva;
    private String usuarioCliente;

    public CancelarReservaFrame(String usuarioCliente) {
        this.usuarioCliente = usuarioCliente;

        setTitle("Cancelar Reserva");
        setSize(950, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
        cargarReservasActivas();
    }

    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Cancelar Reserva");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(370, 20, 250, 30);
        panel.add(lblTitulo);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID Reserva");
        modeloTabla.addColumn("Fecha Inicio");
        modeloTabla.addColumn("Fecha Fin");
        modeloTabla.addColumn("Fecha Reserva");
        modeloTabla.addColumn("Estado");
        modeloTabla.addColumn("Costo Total");
        modeloTabla.addColumn("Tipo Reserva");
        modeloTabla.addColumn("ID Vehículo");

        tablaReservas = new JTable(modeloTabla);

        JScrollPane scroll = new JScrollPane(tablaReservas);
        scroll.setBounds(40, 70, 850, 230);
        panel.add(scroll);

        JLabel lblIdReserva = new JLabel("ID Reserva:");
        lblIdReserva.setBounds(260, 330, 100, 25);
        panel.add(lblIdReserva);

        txtIdReserva = new JTextField();
        txtIdReserva.setBounds(360, 330, 180, 25);
        panel.add(txtIdReserva);

        JButton btnCancelarReserva = new JButton("Cancelar Reserva");
        btnCancelarReserva.setBounds(560, 330, 160, 30);
        panel.add(btnCancelarReserva);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(320, 390, 130, 30);
        panel.add(btnActualizar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(500, 390, 130, 30);
        panel.add(btnCerrar);

        btnCancelarReserva.addActionListener(e -> cancelarReserva());
        btnActualizar.addActionListener(e -> cargarReservasActivas());
        btnCerrar.addActionListener(e -> dispose());

        add(panel);
    }

    private void cargarReservasActivas() {
        modeloTabla.setRowCount(0);

        for (String[] reserva : ReservaArchivo.leerReservas()) {
            if (reserva[8].equals(usuarioCliente)
                    && reserva[4].equalsIgnoreCase("Activa")) {

                modeloTabla.addRow(new Object[]{
                        reserva[0],
                        reserva[1],
                        reserva[2],
                        reserva[3],
                        reserva[4],
                        reserva[5],
                        reserva[6],
                        reserva[7]
                });
            }
        }
    }

    private void cancelarReserva() {
        String idReserva = txtIdReserva.getText();

        if (idReserva.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese el ID de la reserva",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idVehiculo = null;
        boolean perteneceAlCliente = false;
        boolean estaActiva = false;

        for (String[] reserva : ReservaArchivo.leerReservas()) {
            if (reserva[0].equals(idReserva)) {

                if (reserva[8].equals(usuarioCliente)) {
                    perteneceAlCliente = true;
                    idVehiculo = reserva[7];

                    if (reserva[4].equalsIgnoreCase("Activa")) {
                        estaActiva = true;
                    }
                }

                break;
            }
        }

        if (!perteneceAlCliente) {
            JOptionPane.showMessageDialog(this,
                    "No existe una reserva activa con ese ID para este cliente",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!estaActiva) {
            JOptionPane.showMessageDialog(this,
                    "La reserva no está activa",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de cancelar esta reserva?",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean reservaActualizada =
                    ReservaArchivo.actualizarEstadoReserva(idReserva, "Cancelada");

            boolean vehiculoActualizado =
                    VehiculoArchivo.actualizarEstadoVehiculo(idVehiculo, "Disponible");

            if (reservaActualizada && vehiculoActualizado) {
                JOptionPane.showMessageDialog(this,
                        "Reserva cancelada correctamente.\nVehículo disponible nuevamente.");
            } else {
                JOptionPane.showMessageDialog(this,
                        "La reserva se canceló, pero no se pudo actualizar el vehículo.\nRevise que el ID del vehículo coincida en reservas.csv y vehiculos.csv.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
            }

            txtIdReserva.setText("");
            cargarReservasActivas();
        }
    }
}