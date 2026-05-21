import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoArchivo {

    private static final String ARCHIVO = "vehiculos.csv";

    public static void crearArchivoSiNoExiste() {
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {
                writer.println("idVehiculo,placa,modelo,marca,anio,estado,precioAlquiler,color,kilometraje");
            } catch (IOException e) {
                System.out.println("Error al crear archivo: " + e.getMessage());
            }
        }
    }

    public static boolean guardarVehiculo(String idVehiculo, String placa,
                                          String modelo, String marca,
                                          String anio, String estado,
                                          String precioAlquiler, String color,
                                          String kilometraje) {
        crearArchivoSiNoExiste();

        if (existeVehiculo(idVehiculo, placa)) {
            return false;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO, true))) {
            writer.println(idVehiculo + "," + placa + "," + modelo + "," + marca + ","
                    + anio + "," + estado + "," + precioAlquiler + ","
                    + color + "," + kilometraje);
            return true;

        } catch (IOException e) {
            System.out.println("Error al guardar vehículo: " + e.getMessage());
            return false;
        }
    }

    public static boolean existeVehiculo(String idVehiculo, String placa) {
        crearArchivoSiNoExiste();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length >= 2 &&
                        (datos[0].equals(idVehiculo) || datos[1].equalsIgnoreCase(placa))) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.out.println("Error al verificar vehículo: " + e.getMessage());
        }

        return false;
    }

    public static List<String[]> leerVehiculos() {
        crearArchivoSiNoExiste();

        List<String[]> vehiculos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length == 9) {
                    vehiculos.add(datos);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer vehículos: " + e.getMessage());
        }

        return vehiculos;
    }

    public static boolean actualizarEstadoVehiculo(String idVehiculo, String nuevoEstado) {
        crearArchivoSiNoExiste();

        List<String[]> vehiculos = leerVehiculos();
        boolean encontrado = false;

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {
            writer.println("idVehiculo,placa,modelo,marca,anio,estado,precioAlquiler,color,kilometraje");

            for (String[] v : vehiculos) {
                if (v[0].equals(idVehiculo)) {
                    v[5] = nuevoEstado;
                    encontrado = true;
                }

                writer.println(String.join(",", v));
            }

        } catch (IOException e) {
            System.out.println("Error al actualizar vehículo: " + e.getMessage());
            return false;
        }

        return encontrado;
    }

    public static boolean actualizarVehiculo(String idVehiculo,
                                             String placa,
                                             String modelo,
                                             String marca,
                                             String anio,
                                             String estado,
                                             String precioAlquiler,
                                             String color,
                                             String kilometraje) {
        crearArchivoSiNoExiste();

        List<String[]> vehiculos = leerVehiculos();
        boolean encontrado = false;

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {
            writer.println("idVehiculo,placa,modelo,marca,anio,estado,precioAlquiler,color,kilometraje");

            for (String[] v : vehiculos) {
                if (v[0].equals(idVehiculo)) {
                    writer.println(idVehiculo + "," + placa + "," + modelo + "," + marca + ","
                            + anio + "," + estado + "," + precioAlquiler + ","
                            + color + "," + kilometraje);
                    encontrado = true;
                } else {
                    writer.println(String.join(",", v));
                }
            }

        } catch (IOException e) {
            System.out.println("Error al actualizar vehículo: " + e.getMessage());
            return false;
        }

        return encontrado;
    }

    public static String generarNuevoIdVehiculo() {
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
            System.out.println("Error al generar ID vehículo: " + e.getMessage());
        }

        return String.format("%03d", ultimoId + 1);
    }
}
