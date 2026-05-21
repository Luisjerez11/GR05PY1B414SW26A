import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GestionVehiculosFrame extends JFrame {

    private JTextField txtIdVehiculo;
    private JTextField txtPlaca;
    private JTextField txtModelo;
    private JTextField txtMarca;
    private JTextField txtAnio;
    private JTextField txtEstado;
    private JTextField txtPrecioAlquiler;
    private JTextField txtColor;
    private JTextField txtKilometraje;

    private JTable tablaVehiculos;
    private DefaultTableModel modeloTabla;

    public GestionVehiculosFrame() {
        setTitle("Gestión de Vehículos");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
        cargarVehiculos();
    }

    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Gestión de Vehículos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(360, 15, 250, 30);
        panel.add(lblTitulo);

        JLabel lblIdVehiculo = new JLabel("ID Vehículo:");
        lblIdVehiculo.setBounds(30, 70, 120, 25);
        panel.add(lblIdVehiculo);

        txtIdVehiculo = new JTextField(VehiculoArchivo.generarNuevoIdVehiculo());
        txtIdVehiculo.setEditable(false);
        txtIdVehiculo.setBounds(150, 70, 180, 25);
        panel.add(txtIdVehiculo);

        JLabel lblPlaca = new JLabel("Placa:");
        lblPlaca.setBounds(30, 105, 120, 25);
        panel.add(lblPlaca);

        txtPlaca = new JTextField();
        txtPlaca.setBounds(150, 105, 180, 25);
        panel.add(txtPlaca);

        JLabel lblModelo = new JLabel("Modelo:");
        lblModelo.setBounds(30, 140, 120, 25);
        panel.add(lblModelo);

        txtModelo = new JTextField();
        txtModelo.setBounds(150, 140, 180, 25);
        panel.add(txtModelo);

        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setBounds(30, 175, 120, 25);
        panel.add(lblMarca);

        txtMarca = new JTextField();
        txtMarca.setBounds(150, 175, 180, 25);
        panel.add(txtMarca);

        JLabel lblAnio = new JLabel("Año:");
        lblAnio.setBounds(30, 210, 120, 25);
        panel.add(lblAnio);

        txtAnio = new JTextField();
        txtAnio.setBounds(150, 210, 180, 25);
        panel.add(txtAnio);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(30, 245, 120, 25);
        panel.add(lblEstado);

        txtEstado = new JTextField();
        txtEstado.setBounds(150, 245, 180, 25);
        panel.add(txtEstado);

        JLabel lblPrecio = new JLabel("Precio Alquiler:");
        lblPrecio.setBounds(30, 280, 120, 25);
        panel.add(lblPrecio);

        txtPrecioAlquiler = new JTextField();
        txtPrecioAlquiler.setBounds(150, 280, 180, 25);
        panel.add(txtPrecioAlquiler);

        JLabel lblColor = new JLabel("Color:");
        lblColor.setBounds(30, 315, 120, 25);
        panel.add(lblColor);

        txtColor = new JTextField();
        txtColor.setBounds(150, 315, 180, 25);
        panel.add(txtColor);

        JLabel lblKilometraje = new JLabel("Kilometraje:");
        lblKilometraje.setBounds(30, 350, 120, 25);
        panel.add(lblKilometraje);

        txtKilometraje = new JTextField();
        txtKilometraje.setBounds(150, 350, 180, 25);
        panel.add(txtKilometraje);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(30, 400, 100, 30);
        panel.add(btnAgregar);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(140, 400, 100, 30);
        panel.add(btnLimpiar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(250, 400, 100, 30);
        panel.add(btnVolver);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(360, 400, 120, 30);
        panel.add(btnActualizar);

        btnActualizar.addActionListener(e -> actualizarVehiculo());

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
        scroll.setBounds(360, 70, 600, 320);
        panel.add(scroll);

        btnAgregar.addActionListener(e -> agregarVehiculo());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnVolver.addActionListener(e -> dispose());

        add(panel);
    }

    private void agregarVehiculo() {
        String idVehiculo = txtIdVehiculo.getText();
        String placa = txtPlaca.getText();
        String modelo = txtModelo.getText();
        String marca = txtMarca.getText();
        String anio = txtAnio.getText();
        String estado = txtEstado.getText();
        String precioAlquiler = txtPrecioAlquiler.getText();
        String color = txtColor.getText();
        String kilometraje = txtKilometraje.getText();

        if (idVehiculo.isEmpty() || placa.isEmpty() || modelo.isEmpty()
                || marca.isEmpty() || anio.isEmpty() || estado.isEmpty()
                || precioAlquiler.isEmpty() || color.isEmpty()
                || kilometraje.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Complete todos los campos",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean guardado = VehiculoArchivo.guardarVehiculo(
                idVehiculo, placa, modelo, marca, anio,
                estado, precioAlquiler, color, kilometraje
        );

        if (guardado) {
            modeloTabla.addRow(new Object[]{
                    idVehiculo, placa, modelo, marca, anio,
                    estado, precioAlquiler, color, kilometraje
            });

            JOptionPane.showMessageDialog(this, "Vehículo guardado correctamente");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Ya existe un vehículo con ese ID o placa",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtIdVehiculo.setText(VehiculoArchivo.generarNuevoIdVehiculo());
        txtPlaca.setText("");
        txtModelo.setText("");
        txtMarca.setText("");
        txtAnio.setText("");
        txtEstado.setText("");
        txtPrecioAlquiler.setText("");
        txtColor.setText("");
        txtKilometraje.setText("");
    }

    private void cargarVehiculos() {
        modeloTabla.setRowCount(0);

        for (String[] vehiculo : VehiculoArchivo.leerVehiculos()) {
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

    private void actualizarVehiculo() {
        String idVehiculo = txtIdVehiculo.getText();
        String placa = txtPlaca.getText();
        String modelo = txtModelo.getText();
        String marca = txtMarca.getText();
        String anio = txtAnio.getText();
        String estado = txtEstado.getText();
        String precioAlquiler = txtPrecioAlquiler.getText();
        String color = txtColor.getText();
        String kilometraje = txtKilometraje.getText();

        if (idVehiculo.isEmpty() || placa.isEmpty() || modelo.isEmpty()
                || marca.isEmpty() || anio.isEmpty() || estado.isEmpty()
                || precioAlquiler.isEmpty() || color.isEmpty()
                || kilometraje.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Complete todos los campos para actualizar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean actualizado = VehiculoArchivo.actualizarVehiculo(
                idVehiculo, placa, modelo, marca, anio,
                estado, precioAlquiler, color, kilometraje
        );

        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Vehículo actualizado correctamente");
            cargarVehiculos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No existe un vehículo con ese ID",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}