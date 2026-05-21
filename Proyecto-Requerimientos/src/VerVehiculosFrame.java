import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VerVehiculosFrame extends JFrame {

    private JTable tablaVehiculos;
    private DefaultTableModel modeloTabla;

    public VerVehiculosFrame() {
        setTitle("Vehículos Disponibles");
        setSize(900, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
        cargarVehiculosDisponibles();
    }

    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Vehículos Disponibles");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(320, 20, 250, 30);
        panel.add(lblTitulo);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID Vehículo");
        modeloTabla.addColumn("Placa");
        modeloTabla.addColumn("Modelo");
        modeloTabla.addColumn("Marca");
        modeloTabla.addColumn("Año");
        modeloTabla.addColumn("Estado");
        modeloTabla.addColumn("Precio Alquiler");
        modeloTabla.addColumn("Color");
        modeloTabla.addColumn("Kilometraje");

        tablaVehiculos = new JTable(modeloTabla);

        JScrollPane scroll = new JScrollPane(tablaVehiculos);
        scroll.setBounds(40, 70, 800, 250);
        panel.add(scroll);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(250, 350, 120, 30);
        panel.add(btnActualizar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(450, 350, 120, 30);
        panel.add(btnCerrar);

        btnActualizar.addActionListener(e -> cargarVehiculosDisponibles());
        btnCerrar.addActionListener(e -> dispose());

        add(panel);
    }

    private void cargarVehiculosDisponibles() {
        modeloTabla.setRowCount(0);

        for (String[] vehiculo : VehiculoArchivo.leerVehiculos()) {

            if (vehiculo[5].equalsIgnoreCase("Disponible")) {
                modeloTabla.addRow(new Object[]{
                        vehiculo[0],
                        vehiculo[1],
                        vehiculo[2],
                        vehiculo[3],
                        vehiculo[4],
                        vehiculo[5],
                        vehiculo[6],
                        vehiculo[7],
                        vehiculo[8]
                });
            }
        }
    }
}
