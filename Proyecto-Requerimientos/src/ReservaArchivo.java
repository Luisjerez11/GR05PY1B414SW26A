import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaArchivo {

    private static final String ARCHIVO = "reservas.csv";

    public static void crearArchivoSiNoExiste() {
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {
                writer.println("idReserva,fechaInicio,fechaFin,fechaReserva,estado,costoTotal,tipoReserva,idVehiculo,usuarioCliente");
            } catch (IOException e) {
                System.out.println("Error al crear archivo: " + e.getMessage());
            }
        }
    }

    public static boolean guardarReserva(String idReserva,
                                         String fechaInicio,
                                         String fechaFin,
                                         String fechaReserva,
                                         String estado,
                                         String costoTotal,
                                         String tipoReserva,
                                         String idVehiculo,
                                         String usuarioCliente) {

        crearArchivoSiNoExiste();

        if (existeReserva(idReserva)) {
            return false;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO, true))) {

            writer.println(idReserva + "," + fechaInicio + "," + fechaFin + ","
                    + fechaReserva + "," + estado + "," + costoTotal + ","
                    + tipoReserva + "," + idVehiculo + "," + usuarioCliente);

            return true;

        } catch (IOException e) {
            System.out.println("Error al guardar reserva: " + e.getMessage());
            return false;
        }
    }

    public static boolean existeReserva(String idReserva) {
        crearArchivoSiNoExiste();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length > 0 && datos[0].equals(idReserva)) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.out.println("Error al verificar reserva: " + e.getMessage());
        }

        return false;
    }

    public static List<String[]> leerReservas() {
        crearArchivoSiNoExiste();

        List<String[]> reservas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length == 9) {
                    reservas.add(datos);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer reservas: " + e.getMessage());
        }

        return reservas;
    }

    public static boolean actualizarEstadoReserva(String idReserva, String nuevoEstado) {
        crearArchivoSiNoExiste();

        List<String[]> reservas = leerReservas();
        boolean encontrada = false;

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {
            writer.println("idReserva,fechaInicio,fechaFin,fechaReserva,estado,costoTotal,tipoReserva,idVehiculo,usuarioCliente");

            for (String[] r : reservas) {
                if (r[0].equals(idReserva)) {
                    r[4] = nuevoEstado;
                    encontrada = true;
                }

                writer.println(String.join(",", r));
            }

        } catch (IOException e) {
            System.out.println("Error al actualizar reserva: " + e.getMessage());
            return false;
        }

        return encontrada;
    }

    public static String generarNuevoIdReserva() {
        crearArchivoSiNoExiste();

        int ultimoId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length > 0) {
                    try {
                        int id = Integer.parseInt(datos[0]);
                        if (id > ultimoId) {
                            ultimoId = id;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ID inválido: " + datos[0]);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error al generar ID: " + e.getMessage());
        }

        int nuevoId = ultimoId + 1;

        return String.format("%03d", nuevoId);
    }
}