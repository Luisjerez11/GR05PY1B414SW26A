import javax.swing.*;
import java.awt.*;

public class MenuAdminFrame extends JFrame {

    public MenuAdminFrame() {
        setTitle("Sistema de Alquiler de Autos - Menú Administrador");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Menú Administrador");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(180, 30, 280, 30);
        panel.add(lblTitulo);

        JButton btnClientes = new JButton("Clientes");
        btnClientes.setBounds(200, 90, 200, 35);
        panel.add(btnClientes);

        btnClientes.addActionListener(e -> {
            GestionClientesFrame clientesFrame = new GestionClientesFrame();
            clientesFrame.setVisible(true);
        });

        JButton btnVehiculos = new JButton("Vehículos");
        btnVehiculos.setBounds(200, 140, 200, 35);
        panel.add(btnVehiculos);

        btnVehiculos.addActionListener(e -> {
            GestionVehiculosFrame vehiculosFrame = new GestionVehiculosFrame();
            vehiculosFrame.setVisible(true);
        });

        JButton btnReservas = new JButton("Reservas");
        btnReservas.setBounds(200, 190, 200, 35);
        panel.add(btnReservas);

        btnReservas.addActionListener(e -> {
            GestionReservasFrame reservasFrame = new GestionReservasFrame();
            reservasFrame.setVisible(true);
        });

        JButton btnDevoluciones = new JButton("Devoluciones e Incidentes");
        btnDevoluciones.setBounds(200, 240, 200, 35);
        panel.add(btnDevoluciones);

        btnDevoluciones.addActionListener(e -> {
            DevolucionIncidenteFrame devolucionFrame = new DevolucionIncidenteFrame();
            devolucionFrame.setVisible(true);
        });

        JButton btnReportes = new JButton("Generar Reporte");
        btnReportes.setBounds(200, 290, 200, 35);
        panel.add(btnReportes);

        btnReportes.addActionListener(e -> {
            ReporteFrame reporteFrame = new ReporteFrame();
            reporteFrame.setVisible(true);
        });

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(200, 340, 200, 35);
        panel.add(btnSalir);

        btnSalir.addActionListener(e -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
            dispose();
        });

        add(panel);
    }
}