import javax.swing.*;
import java.awt.*;

public class RegistroClienteFrame extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JTextField txtNombre;
    private JTextField txtCedula;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtDireccion;
    private JCheckBox chkLicencia;

    public RegistroClienteFrame() {
        setTitle("Registro de Cliente");
        setSize(430, 470);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Registro de Cliente");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(110, 20, 250, 30);
        panel.add(lblTitulo);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(40, 70, 120, 25);
        panel.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(160, 70, 200, 25);
        panel.add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(40, 105, 120, 25);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(160, 105, 200, 25);
        panel.add(txtPassword);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(40, 140, 120, 25);
        panel.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(160, 140, 200, 25);
        panel.add(txtNombre);

        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setBounds(40, 175, 120, 25);
        panel.add(lblCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(160, 175, 200, 25);
        panel.add(txtCedula);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(40, 210, 120, 25);
        panel.add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(160, 210, 200, 25);
        panel.add(txtTelefono);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(40, 245, 120, 25);
        panel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(160, 245, 200, 25);
        panel.add(txtEmail);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(40, 280, 120, 25);
        panel.add(lblDireccion);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(160, 280, 200, 25);
        panel.add(txtDireccion);

        chkLicencia = new JCheckBox("Tiene licencia de conducir");
        chkLicencia.setBounds(160, 315, 220, 25);
        panel.add(chkLicencia);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(90, 365, 110, 30);
        panel.add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(220, 365, 110, 30);
        panel.add(btnCancelar);

        btnRegistrar.addActionListener(e -> registrarCliente());
        btnCancelar.addActionListener(e -> dispose());

        add(panel);
    }

    private void registrarCliente() {
        String usuario = txtUsuario.getText();
        String password = String.valueOf(txtPassword.getPassword());
        String nombre = txtNombre.getText();
        String cedula = txtCedula.getText();
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();
        String direccion = txtDireccion.getText();
        boolean licencia = chkLicencia.isSelected();

        if (usuario.isEmpty() || password.isEmpty() || nombre.isEmpty()
                || cedula.isEmpty() || telefono.isEmpty()
                || email.isEmpty() || direccion.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Complete todos los campos",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean registrado = UsuarioArchivo.registrarCliente(
                usuario,
                password,
                nombre,
                cedula,
                telefono,
                email,
                direccion,
                licencia
        );

        if (registrado) {
            JOptionPane.showMessageDialog(this, "Cliente registrado correctamente");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Ese usuario ya existe",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}