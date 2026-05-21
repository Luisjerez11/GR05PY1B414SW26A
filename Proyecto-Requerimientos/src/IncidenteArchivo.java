import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IncidenteArchivo {

    private static final String ARCHIVO = "incidentes.csv";

    public static void crearArchivoSiNoExiste() {
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {
                writer.println("idIncidente,idDevolucion,idVehiculo,fechaIncidente,descripcion,costoReparacion");
            } catch (IOException e) {
                System.out.println("Error al crear archivo de incidentes: " + e.getMessage());
            }
        }
    }

    public static String generarNuevoIdIncidente() {
        crearArchivoSiNoExiste();

        int ultimoId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",", 6);

                if (datos.length > 0) {
                    try {
                        String idSinLetra = datos[0].replace("I", "");
                        int id = Integer.parseInt(idSinLetra);

                        if (id > ultimoId) {
                            ultimoId = id;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ID de incidente inválido: " + datos[0]);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error al generar ID incidente: " + e.getMessage());
        }

        return String.format("I%03d", ultimoId + 1);
    }

    public static boolean guardarIncidente(String idIncidente,
                                           String idDevolucion,
                                           String idVehiculo,
                                           String fechaIncidente,
                                           String descripcion,
                                           String costoReparacion) {
        crearArchivoSiNoExiste();

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO, true))) {
            descripcion = descripcion.replace("\n", " ");

            writer.println(idIncidente + "," + idDevolucion + "," + idVehiculo + ","
                    + fechaIncidente + "," + descripcion + "," + costoReparacion);

            return true;

        } catch (IOException e) {
            System.out.println("Error al guardar incidente: " + e.getMessage());
            return false;
        }
    }

    public static List<String[]> leerIncidentes() {
        crearArchivoSiNoExiste();

        List<String[]> incidentes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",", 6);

                if (datos.length == 6) {
                    incidentes.add(datos);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer incidentes: " + e.getMessage());
        }

        return incidentes;
    }
}