import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteArchivo {

    private static final String ARCHIVO = "reportes.csv";

    public static void crearArchivoSiNoExiste() {
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {
                writer.println("idReporte,tipoReporte,fechaGeneracion,contenido");
            } catch (IOException e) {
                System.out.println("Error al crear archivo de reportes: " + e.getMessage());
            }
        }
    }

    public static String generarNuevoIdReporte() {
        crearArchivoSiNoExiste();

        int ultimoId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",", 4);

                if (datos.length > 0) {
                    try {
                        String idSinLetra = datos[0].replace("R", "");
                        int id = Integer.parseInt(idSinLetra);

                        if (id > ultimoId) {
                            ultimoId = id;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ID de reporte inválido: " + datos[0]);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error al generar ID de reporte: " + e.getMessage());
        }

        return String.format("R%03d", ultimoId + 1);
    }

    public static boolean guardarReporte(String idReporte,
                                         String tipoReporte,
                                         String fechaGeneracion,
                                         String contenido) {
        crearArchivoSiNoExiste();

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO, true))) {

            contenido = contenido.replace("\n", " | ");

            writer.println(idReporte + "," + tipoReporte + "," + fechaGeneracion + "," + contenido);

            return true;

        } catch (IOException e) {
            System.out.println("Error al guardar reporte: " + e.getMessage());
            return false;
        }
    }

    public static List<String[]> leerReportes() {
        crearArchivoSiNoExiste();

        List<String[]> reportes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",", 4);

                if (datos.length == 4) {
                    reportes.add(datos);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer reportes: " + e.getMessage());
        }

        return reportes;
    }
}