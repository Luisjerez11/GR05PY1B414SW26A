import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DevolucionArchivo {

    private static final String ARCHIVO = "devoluciones.csv";

    public static void crearArchivoSiNoExiste() {
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {
                writer.println("idDevolucion,idReserva,idVehiculo,usuarioCliente,fechaDevolucion,estadoVehiculo,observaciones");
            } catch (IOException e) {
                System.out.println("Error al crear archivo de devoluciones: " + e.getMessage());
            }
        }
    }

    public static String generarNuevoIdDevolucion() {
        crearArchivoSiNoExiste();

        int ultimoId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",", 7);

                if (datos.length > 0) {
                    try {
                        String idSinLetra = datos[0].replace("D", "");
                        int id = Integer.parseInt(idSinLetra);

                        if (id > ultimoId) {
                            ultimoId = id;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ID de devolución inválido: " + datos[0]);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error al generar ID devolución: " + e.getMessage());
        }

        return String.format("D%03d", ultimoId + 1);
    }

    public static boolean guardarDevolucion(String idDevolucion,
                                            String idReserva,
                                            String idVehiculo,
                                            String usuarioCliente,
                                            String fechaDevolucion,
                                            String estadoVehiculo,
                                            String observaciones) {
        crearArchivoSiNoExiste();

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO, true))) {
            observaciones = observaciones.replace("\n", " ");

            writer.println(idDevolucion + "," + idReserva + "," + idVehiculo + ","
                    + usuarioCliente + "," + fechaDevolucion + ","
                    + estadoVehiculo + "," + observaciones);

            return true;

        } catch (IOException e) {
            System.out.println("Error al guardar devolución: " + e.getMessage());
            return false;
        }
    }

    public static List<String[]> leerDevoluciones() {
        crearArchivoSiNoExiste();

        List<String[]> devoluciones = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",", 7);

                if (datos.length == 7) {
                    devoluciones.add(datos);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer devoluciones: " + e.getMessage());
        }

        return devoluciones;
    }
}