import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;

    public LoginFrame() {
        setTitle("Sistema de Alquiler de Autos - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240));

        JLabel lblTitulo = new JLabel("Inicio de Sesión");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(110, 20, 200, 30);
        panel.add(lblTitulo);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(50, 80, 100, 25);
        panel.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(150, 80, 180, 25);
        panel.add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(50, 120, 100, 25);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 120, 180, 25);
        panel.add(txtPassword);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(140, 165, 120, 30);
        panel.add(btnIngresar);

        btnIngresar.addActionListener(e -> validarLogin());

        JButton btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setBounds(140, 200, 120, 30);
        panel.add(btnRegistrarse);

        btnRegistrarse.addActionListener(e -> {
            RegistroClienteFrame registro = new RegistroClienteFrame();
            registro.setVisible(true);
        });

        add(panel);
    }

    private void validarLogin() {
        String usuario = txtUsuario.getText();
        String password = String.valueOf(txtPassword.getPassword());

        // Validar administrador
        if (UsuarioArchivo.validarLogin(usuario, password, "ADMIN")) {
            JOptionPane.showMessageDialog(this, "Bienvenido Administrador");

            MenuAdminFrame menuAdmin = new MenuAdminFrame();
            menuAdmin.setVisible(true);
            this.dispose();
        }

        // Validar cliente
        else if (UsuarioArchivo.validarLogin(usuario, password, "CLIENTE")) {
            JOptionPane.showMessageDialog(this, "Bienvenido Cliente");

            MenuClienteFrame menuCliente = new MenuClienteFrame(usuario);
            menuCliente.setVisible(true);
            this.dispose();
        }

        else {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}