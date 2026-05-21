import javax.swing.*;

public class SistemaAlquilerDeAutos {
    public static void main(String[] args) {

        UsuarioArchivo.crearArchivoSiNoExiste();
        VehiculoArchivo.crearArchivoSiNoExiste();
        ReservaArchivo.crearArchivoSiNoExiste();
        ReporteArchivo.crearArchivoSiNoExiste();
        DevolucionArchivo.crearArchivoSiNoExiste();
        IncidenteArchivo.crearArchivoSiNoExiste();
        PagoArchivo.crearArchivoSiNoExiste();
        FacturaArchivo.crearArchivoSiNoExiste();

        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}