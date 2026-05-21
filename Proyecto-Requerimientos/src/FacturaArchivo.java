import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaArchivo {

    private static final String ARCHIVO = "facturas.csv";

    public static void crearArchivoSiNoExiste() {
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {
                writer.println("idFactura,fecha,total,impuesto,estado,idPago,idReserva,usuarioCliente");
            } catch (IOException e) {
                System.out.println("Error al crear archivo de facturas: " + e.getMessage());
            }
        }
    }

    public static String generarNuevoIdFactura() {
        crearArchivoSiNoExiste();

        int ultimoId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length > 0) {
                    try {
                        String idSinLetra = datos[0].replace("F", "");
                        int id = Integer.parseInt(idSinLetra);

                        if (id > ultimoId) {
                            ultimoId = id;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ID de factura inválido: " + datos[0]);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error al generar ID factura: " + e.getMessage());
        }

        return String.format("F%03d", ultimoId + 1);
    }

    public static boolean guardarFactura(String idFactura,
                                         String fecha,
                                         String total,
                                         String impuesto,
                                         String estado,
                                         String idPago,
                                         String idReserva,
                                         String usuarioCliente) {
        crearArchivoSiNoExiste();

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO, true))) {

            writer.println(idFactura + "," + fecha + "," + total + ","
                    + impuesto + "," + estado + "," + idPago + ","
                    + idReserva + "," + usuarioCliente);

            return true;

        } catch (IOException e) {
            System.out.println("Error al guardar factura: " + e.getMessage());
            return false;
        }
    }

    public static List<String[]> leerFacturas() {
        crearArchivoSiNoExiste();

        List<String[]> facturas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length == 8) {
                    facturas.add(datos);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer facturas: " + e.getMessage());
        }

        return facturas;
    }
}