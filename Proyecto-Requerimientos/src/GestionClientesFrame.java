import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GestionClientesFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtCedula;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtDireccion;
    private JCheckBox chkLicencia;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    public GestionClientesFrame() {
        setTitle("Gestión de Clientes");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
        cargarClientes();
    }

    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Gestión de Clientes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(380, 15, 300, 30);
        panel.add(lblTitulo);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(30, 70, 100, 25);
        panel.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(120, 70, 180, 25);
        panel.add(txtId);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 105, 100, 25);
        panel.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(120, 105, 180, 25);
        panel.add(txtNombre);

        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setBounds(30, 140, 100, 25);
        panel.add(lblCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(120, 140, 180, 25);
        panel.add(txtCedula);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(30, 175, 100, 25);
        panel.add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(120, 175, 180, 25);
        panel.add(txtTelefono);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 210, 100, 25);
        panel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(120, 210, 180, 25);
        panel.add(txtEmail);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(30, 245, 100, 25);
        panel.add(lblDireccion);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(120, 245, 180, 25);
        panel.add(txtDireccion);

        chkLicencia = new JCheckBox("Tiene licencia");
        chkLicencia.setBounds(120, 280, 150, 25);
        panel.add(chkLicencia);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(30, 400, 100, 30);
        panel.add(btnAgregar);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(140, 400, 100, 30);
        panel.add(btnLimpiar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(250, 400, 100, 30);
        panel.add(btnVolver);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Cédula");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Email");
        modeloTabla.addColumn("Dirección");
        modeloTabla.addColumn("Licencia");

        tablaClientes = new JTable(modeloTabla);

        JScrollPane scroll = new JScrollPane(tablaClientes);
        scroll.setBounds(380, 70, 560, 420);
        panel.add(scroll);

        btnAgregar.addActionListener(e -> agregarCliente());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnVolver.addActionListener(e -> dispose());

        add(panel);
    }

    private void agregarCliente() {
        String id = txtId.getText();
        String nombre = txtNombre.getText();
        String cedula = txtCedula.getText();
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();
        String direccion = txtDireccion.getText();
        String licencia = chkLicencia.isSelected() ? "Sí" : "No";

        if (id.isEmpty() || nombre.isEmpty() || cedula.isEmpty()
                || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Complete todos los campos",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        modeloTabla.addRow(new Object[]{
                id, nombre, cedula, telefono, email, direccion, licencia
        });

        JOptionPane.showMessageDialog(this, "Cliente agregado correctamente");
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtCedula.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        chkLicencia.setSelected(false);
    }

    private void cargarClientes() {
        modeloTabla.setRowCount(0);

        for (String[] usuario : UsuarioArchivo.leerUsuarios()) {
            if (usuario[2].equalsIgnoreCase("CLIENTE")) {
                modeloTabla.addRow(new Object[]{
                        usuario[0],
                        usuario[3],
                        usuario[4],
                        usuario[5],
                        usuario[6],
                        usuario[7],
                        usuario[8].equalsIgnoreCase("true") ? "Sí" : "No"
                });
            }
        }
    }
}