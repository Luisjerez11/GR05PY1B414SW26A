import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioArchivo {

    private static final String ARCHIVO = "usuarios.csv";

    public static void crearArchivoSiNoExiste() {
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {

                writer.println("usuario,password,rol,nombre,cedula,telefono,email,direccion,licencia");

                // Usuario administrador por defecto
                writer.println("admin,1234,ADMIN,Administrador,0000000000,0999999999,admin@gmail.com,Sistema,true");

            } catch (IOException e) {
                System.out.println("Error al crear archivo: " + e.getMessage());
            }
        }
    }

    public static boolean validarLogin(String usuario, String password, String rol) {
        crearArchivoSiNoExiste();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos[0].equals(usuario)
                        && datos[1].equals(password)
                        && datos[2].equals(rol)) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }

        return false;
    }

    public static boolean registrarCliente(String usuario, String password,
                                           String nombre, String cedula,
                                           String telefono, String email,
                                           String direccion, boolean licencia) {
        crearArchivoSiNoExiste();

        if (existeUsuario(usuario)) {
            return false;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO, true))) {

            writer.println(usuario + "," + password + ",CLIENTE," + nombre + ","
                    + cedula + "," + telefono + "," + email + ","
                    + direccion + "," + licencia);

            return true;

        } catch (IOException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
            return false;
        }
    }

    public static boolean existeUsuario(String usuario) {
        crearArchivoSiNoExiste();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos[0].equals(usuario)) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.out.println("Error al verificar usuario: " + e.getMessage());
        }

        return false;
    }

    public static List<String[]> leerUsuarios() {
        crearArchivoSiNoExiste();

        List<String[]> usuarios = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine(); // saltar encabezado

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length == 9) {
                    usuarios.add(datos);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer usuarios: " + e.getMessage());
        }

        return usuarios;
    }
}