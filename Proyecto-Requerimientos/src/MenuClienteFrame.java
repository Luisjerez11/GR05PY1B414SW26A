import javax.swing.*;
import java.awt.*;

public class MenuClienteFrame extends JFrame {

    private String usuarioCliente;

    public MenuClienteFrame(String usuarioCliente) {
        this.usuarioCliente = usuarioCliente;

        setTitle("Sistema de Alquiler de Autos - Cliente");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
    }
    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Menú Cliente");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(170, 20, 200, 30);
        panel.add(lblTitulo);

        JButton btnVerVehiculos = new JButton("Ver Vehículos Disponibles");
        btnVerVehiculos.setBounds(140, 80, 220, 35);
        panel.add(btnVerVehiculos);

        btnVerVehiculos.addActionListener(e -> {
            VerVehiculosFrame verVehiculos = new VerVehiculosFrame();
            verVehiculos.setVisible(true);
        });

        JButton btnReservar = new JButton("Realizar Reserva");
        btnReservar.setBounds(140, 130, 220, 35);
        panel.add(btnReservar);

        btnReservar.addActionListener(e -> {
            RealizarReservaFrame reservaFrame = new RealizarReservaFrame(usuarioCliente);
            reservaFrame.setVisible(true);
        });

        JButton btnMisReservas = new JButton("Mis Reservas");
        btnMisReservas.setBounds(140, 180, 220, 35);
        panel.add(btnMisReservas);

        btnMisReservas.addActionListener(e -> {
            MisReservasFrame misReservas = new MisReservasFrame(usuarioCliente);
            misReservas.setVisible(true);
        });

        JButton btnCancelar = new JButton("Cancelar Reserva");
        btnCancelar.setBounds(140, 230, 220, 35);
        panel.add(btnCancelar);

        btnCancelar.addActionListener(e -> {
            CancelarReservaFrame cancelarFrame = new CancelarReservaFrame(usuarioCliente);
            cancelarFrame.setVisible(true);
        });

        JButton btnPagoFactura = new JButton("Realizar Pago / Factura");
        btnPagoFactura.setBounds(140, 280, 220, 35);
        panel.add(btnPagoFactura);

        btnPagoFactura.addActionListener(e -> {
            PagoFacturaFrame pagoFrame = new PagoFacturaFrame(usuarioCliente);
            pagoFrame.setVisible(true);
        });

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(140, 330, 220, 35);
        panel.add(btnSalir);

        btnSalir.addActionListener(e -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
            dispose();
        });

        add(panel);
    }
}