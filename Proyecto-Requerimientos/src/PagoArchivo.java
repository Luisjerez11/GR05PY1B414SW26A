import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PagoArchivo {

    private static final String ARCHIVO = "pagos.csv";

    public static void crearArchivoSiNoExiste() {
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {
                writer.println("idPago,fechaPago,monto,metodoPago,estado,idFactura,idReserva,usuarioCliente");
            } catch (IOException e) {
                System.out.println("Error al crear archivo de pagos: " + e.getMessage());
            }
        }
    }

    public static String generarNuevoIdPago() {
        crearArchivoSiNoExiste();

        int ultimoId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length > 0) {
                    try {
                        String idSinLetra = datos[0].replace("P", "");
                        int id = Integer.parseInt(idSinLetra);

                        if (id > ultimoId) {
                            ultimoId = id;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ID de pago inválido: " + datos[0]);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error al generar ID pago: " + e.getMessage());
        }

        return String.format("P%03d", ultimoId + 1);
    }

    public static boolean guardarPago(String idPago,
                                      String fechaPago,
                                      String monto,
                                      String metodoPago,
                                      String estado,
                                      String idFactura,
                                      String idReserva,
                                      String usuarioCliente) {
        crearArchivoSiNoExiste();

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO, true))) {

            writer.println(idPago + "," + fechaPago + "," + monto + ","
                    + metodoPago + "," + estado + "," + idFactura + ","
                    + idReserva + "," + usuarioCliente);

            return true;

        } catch (IOException e) {
            System.out.println("Error al guardar pago: " + e.getMessage());
            return false;
        }
    }

    public static List<String[]> leerPagos() {
        crearArchivoSiNoExiste();

        List<String[]> pagos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length == 8) {
                    pagos.add(datos);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer pagos: " + e.getMessage());
        }

        return pagos;
    }

    public static boolean reservaPagada(String idReserva) {
        crearArchivoSiNoExiste();

        for (String[] pago : leerPagos()) {
            if (pago[6].equals(idReserva)
                    && pago[4].equalsIgnoreCase("Confirmado")) {
                return true;
            }
        }

        return false;
    }
}