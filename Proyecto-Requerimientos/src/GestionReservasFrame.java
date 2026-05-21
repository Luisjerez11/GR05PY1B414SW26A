import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GestionReservasFrame extends JFrame {

    private JTable tablaReservas;
    private DefaultTableModel modeloTabla;

    public GestionReservasFrame() {
        setTitle("Gestión de Reservas");
        setSize(1100, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
        cargarReservas();
    }

    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Gestión de Reservas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(420, 20, 250, 30);
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
        modeloTabla.addColumn("Cliente");

        tablaReservas = new JTable(modeloTabla);

        JScrollPane scroll = new JScrollPane(tablaReservas);
        scroll.setBounds(30, 70, 1020, 280);
        panel.add(scroll);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(380, 390, 130, 30);
        panel.add(btnActualizar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(560, 390, 130, 30);
        panel.add(btnCerrar);

        btnActualizar.addActionListener(e -> cargarReservas());
        btnCerrar.addActionListener(e -> dispose());

        add(panel);
    }

    private void cargarReservas() {
        modeloTabla.setRowCount(0);

        for (String[] reserva : ReservaArchivo.leerReservas()) {
            modeloTabla.addRow(new Object[]{
                    reserva[0],
                    reserva[1],
                    reserva[2],
                    reserva[3],
                    reserva[4],
                    reserva[5],
                    reserva[6],
                    reserva[7],
                    reserva[8]
            });
        }
    }
}