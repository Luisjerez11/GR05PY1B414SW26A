import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ReporteFrame extends JFrame {

    private JTextField txtIdReporte;
    private JComboBox<String> cmbTipoReporte;
    private JTextField txtFechaGeneracion;
    private JTextArea txtContenido;

    public ReporteFrame() {
        setTitle("Generar Reporte");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Generar Reporte");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(230, 20, 250, 30);
        panel.add(lblTitulo);

        JLabel lblId = new JLabel("ID Reporte:");
        lblId.setBounds(40, 70, 130, 25);
        panel.add(lblId);

        txtIdReporte = new JTextField(ReporteArchivo.generarNuevoIdReporte());
        txtIdReporte.setEditable(false);
        txtIdReporte.setBounds(180, 70, 180, 25);
        panel.add(txtIdReporte);

        JLabel lblTipo = new JLabel("Tipo Reporte:");
        lblTipo.setBounds(40, 110, 130, 25);
        panel.add(lblTipo);

        cmbTipoReporte = new JComboBox<>(new String[]{
                "General",
                "Clientes",
                "Vehículos",
                "Reservas"
        });
        cmbTipoReporte.setBounds(180, 110, 180, 25);
        panel.add(cmbTipoReporte);

        JLabel lblFecha = new JLabel("Fecha Generación:");
        lblFecha.setBounds(40, 150, 130, 25);
        panel.add(lblFecha);

        txtFechaGeneracion = new JTextField(LocalDate.now().toString());
        txtFechaGeneracion.setBounds(180, 150, 180, 25);
        txtFechaGeneracion.setEditable(false);
        panel.add(txtFechaGeneracion);

        JLabel lblContenido = new JLabel("Contenido:");
        lblContenido.setBounds(40, 195, 130, 25);
        panel.add(lblContenido);

        txtContenido = new JTextArea();
        txtContenido.setEditable(false);

        JScrollPane scroll = new JScrollPane(txtContenido);
        scroll.setBounds(40, 225, 550, 200);
        panel.add(scroll);

        JButton btnGenerar = new JButton("Generar Reporte");
        btnGenerar.setBounds(130, 450, 150, 30);
        panel.add(btnGenerar);

        JButton btnImprimir = new JButton("Imprimir Reporte");
        btnImprimir.setBounds(310, 450, 150, 30);
        panel.add(btnImprimir);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(480, 450, 100, 30);
        panel.add(btnVolver);

        btnVolver.addActionListener(e -> dispose());

        btnGenerar.addActionListener(e -> generarReporte());
        btnImprimir.addActionListener(e -> imprimirReporte());

        add(panel);
    }

    private void generarReporte() {
        String tipo = cmbTipoReporte.getSelectedItem().toString();
        String contenido = "";

        int totalClientes = 0;
        int totalVehiculos = 0;
        int disponibles = 0;
        int alquilados = 0;
        int totalReservas = 0;
        int activas = 0;
        int canceladas = 0;
        double ingresos = 0;

        for (String[] usuario : UsuarioArchivo.leerUsuarios()) {
            if (usuario[2].equalsIgnoreCase("CLIENTE")) {
                totalClientes++;
            }
        }

        for (String[] vehiculo : VehiculoArchivo.leerVehiculos()) {
            totalVehiculos++;

            if (vehiculo[5].equalsIgnoreCase("Disponible")) {
                disponibles++;
            } else if (vehiculo[5].equalsIgnoreCase("Alquilado")) {
                alquilados++;
            }
        }

        for (String[] reserva : ReservaArchivo.leerReservas()) {
            totalReservas++;

            if (reserva[4].equalsIgnoreCase("Activa")) {
                activas++;
                ingresos += Double.parseDouble(reserva[5]);
            } else if (reserva[4].equalsIgnoreCase("Cancelada")) {
                canceladas++;
            }
        }

        if (tipo.equals("General")) {
            contenido =
                    "REPORTE GENERAL DEL SISTEMA\n\n" +
                            "Total de clientes registrados: " + totalClientes + "\n" +
                            "Total de vehículos registrados: " + totalVehiculos + "\n" +
                            "Vehículos disponibles: " + disponibles + "\n" +
                            "Vehículos alquilados: " + alquilados + "\n" +
                            "Total de reservas registradas: " + totalReservas + "\n" +
                            "Reservas activas: " + activas + "\n" +
                            "Reservas canceladas: " + canceladas + "\n" +
                            "Ingresos estimados por reservas activas: $" + ingresos;
        } else if (tipo.equals("Clientes")) {
            contenido =
                    "REPORTE DE CLIENTES\n\n" +
                            "Total de clientes registrados: " + totalClientes;
        } else if (tipo.equals("Vehículos")) {
            contenido =
                    "REPORTE DE VEHÍCULOS\n\n" +
                            "Total de vehículos registrados: " + totalVehiculos + "\n" +
                            "Vehículos disponibles: " + disponibles + "\n" +
                            "Vehículos alquilados: " + alquilados;
        } else if (tipo.equals("Reservas")) {
            contenido =
                    "REPORTE DE RESERVAS\n\n" +
                            "Total de reservas registradas: " + totalReservas + "\n" +
                            "Reservas activas: " + activas + "\n" +
                            "Reservas canceladas: " + canceladas + "\n" +
                            "Ingresos estimados: $" + ingresos;
        }

        txtContenido.setText(contenido);

        boolean guardado = ReporteArchivo.guardarReporte(
                txtIdReporte.getText(),
                tipo,
                txtFechaGeneracion.getText(),
                contenido
        );

        if (guardado) {
            JOptionPane.showMessageDialog(this, "Reporte generado y guardado correctamente");
            txtIdReporte.setText(ReporteArchivo.generarNuevoIdReporte());
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar el reporte",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void imprimirReporte() {
        if (txtContenido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Primero debe generar el reporte",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "ID Reporte: " + txtIdReporte.getText() + "\n" +
                        "Tipo Reporte: " + cmbTipoReporte.getSelectedItem() + "\n" +
                        "Fecha Generación: " + txtFechaGeneracion.getText() + "\n\n" +
                        txtContenido.getText(),
                "Reporte generado",
                JOptionPane.INFORMATION_MESSAGE);
    }
}