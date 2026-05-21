import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class VerFacturaFrame extends JFrame {

    private JTextArea txtFactura;
    private String usuarioCliente;

    public VerFacturaFrame(String usuarioCliente) {
        this.usuarioCliente = usuarioCliente;

        setTitle("Mis Facturas");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
        cargarFacturas();
    }

    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Mis Facturas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(250, 20, 200, 30);
        panel.add(lblTitulo);

        txtFactura = new JTextArea();
        txtFactura.setEditable(false);

        JScrollPane scroll = new JScrollPane(txtFactura);
        scroll.setBounds(40, 70, 550, 330);
        panel.add(scroll);

        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.setBounds(90, 430, 120, 30);
        panel.add(btnImprimir);

        JButton btnDescargar = new JButton("Descargar TXT");
        btnDescargar.setBounds(250, 430, 140, 30);
        panel.add(btnDescargar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(430, 430, 120, 30);
        panel.add(btnVolver);

        btnImprimir.addActionListener(e -> imprimirFactura());
        btnDescargar.addActionListener(e -> descargarFactura());
        btnVolver.addActionListener(e -> dispose());

        add(panel);
    }

    private void cargarFacturas() {
        StringBuilder contenido = new StringBuilder();

        for (String[] factura : FacturaArchivo.leerFacturas()) {
            if (factura[7].equals(usuarioCliente)) {

                contenido.append("FACTURA\n");
                contenido.append("-----------------------------\n");
                contenido.append("ID Factura: ").append(factura[0]).append("\n");
                contenido.append("Fecha: ").append(factura[1]).append("\n");
                contenido.append("Total: $").append(factura[2]).append("\n");
                contenido.append("Impuesto: $").append(factura[3]).append("\n");
                contenido.append("Estado: ").append(factura[4]).append("\n");
                contenido.append("ID Pago: ").append(factura[5]).append("\n");
                contenido.append("ID Reserva: ").append(factura[6]).append("\n");
                contenido.append("Cliente: ").append(factura[7]).append("\n");
                contenido.append("-----------------------------\n\n");
            }
        }

        if (contenido.length() == 0) {
            txtFactura.setText("No tiene facturas registradas.");
        } else {
            txtFactura.setText(contenido.toString());
        }
    }

    private void imprimirFactura() {
        try {
            txtFactura.print();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al imprimir la factura",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void descargarFactura() {
        try {
            String nombreArchivo = "factura_" + usuarioCliente + ".txt";

            FileWriter writer = new FileWriter(nombreArchivo);
            writer.write(txtFactura.getText());
            writer.close();

            JOptionPane.showMessageDialog(this,
                    "Factura descargada como: " + nombreArchivo);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al descargar la factura",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}