import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MisReservasFrame extends JFrame {

    private JTable tablaReservas;
    private DefaultTableModel modeloTabla;
    private String usuarioCliente;

    public MisReservasFrame(String usuarioCliente) {
        this.usuarioCliente = usuarioCliente;

        setTitle("Mis Reservas");
        setSize(950, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
        cargarMisReservas();
    }

    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Mis Reservas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(390, 20, 200, 30);
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
        scroll.setBounds(40, 70, 850, 250);
        panel.add(scroll);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(300, 350, 130, 30);
        panel.add(btnActualizar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(470, 350, 130, 30);
        panel.add(btnCerrar);

        btnActualizar.addActionListener(e -> cargarMisReservas());
        btnCerrar.addActionListener(e -> dispose());

        add(panel);
    }

    private void cargarMisReservas() {
        modeloTabla.setRowCount(0);

        for (String[] reserva : ReservaArchivo.leerReservas()) {

            // reserva[8] es usuarioCliente
            if (reserva[8].equals(usuarioCliente)) {
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
}