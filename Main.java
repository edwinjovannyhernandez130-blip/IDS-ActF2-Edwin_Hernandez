import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Programa principal del Sistema de Gestión de Estudiantes.
 * Demuestra clases, objetos, constructores y métodos.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Estudiante> estudiantes = new ArrayList<>();

    public static void main(String[] args) {

        cargarDatosEjemplo();

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {

                case 1:
                    registrarEstudiante();
                    break;

                case 2:
                    mostrarEstudiantes();
                    break;

                case 3:
                    actualizarPromedio();
                    break;

                case 4:
                    mostrarResumen();
                    break;

                case 5:
                    System.out.println("\nPrograma finalizado. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }

        } while (opcion != 5);

        scanner.close();
    }

    /**
     * Muestra el menú principal.
     */
    private static void mostrarMenu() {

        System.out.println("\n========================================");
        System.out.println(" SISTEMA DE GESTIÓN DE ESTUDIANTES");
        System.out.println("========================================");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Mostrar estudiantes");
        System.out.println("3. Actualizar promedio");
        System.out.println("4. Mostrar resumen");
        System.out.println("5. Salir");
    }

    /**
     * Registra un nuevo estudiante.
     * En cualquier campo se puede escribir CANCELAR
     * para regresar al menú principal.
     */
    private static void registrarEstudiante() {

        System.out.println("\n--- Registrar estudiante ---");
        System.out.println("Escribe CANCELAR en cualquier momento para regresar al menú.");

        String nombre = leerTexto("Nombre: ");

        if (nombre == null) {
            System.out.println("Registro cancelado.");
            return;
        }

        String matricula = leerTexto("Matrícula: ");

        if (matricula == null) {
            System.out.println("Registro cancelado.");
            return;
        }

        String carrera = leerTexto("Carrera: ");

        if (carrera == null) {
            System.out.println("Registro cancelado.");
            return;
        }

        Double promedio = leerDoubleCancelable("Promedio (0-100): ");

        if (promedio == null) {
            System.out.println("Registro cancelado.");
            return;
        }

        estudiantes.add(
                new Estudiante(
                        nombre,
                        matricula,
                        carrera,
                        promedio
                )
        );

        System.out.println("\n¡Estudiante registrado correctamente!");
    }

    /**
     * Muestra todos los estudiantes registrados.
     */
    private static void mostrarEstudiantes() {

        System.out.println("\n--- Lista de estudiantes ---");

        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }

        for (Estudiante estudiante : estudiantes) {
            estudiante.mostrarInformacion();
        }
    }

    /**
     * Actualiza el promedio de un estudiante.
     */
    private static void actualizarPromedio() {

        System.out.println("\n--- Actualizar promedio ---");
        System.out.println("Escribe CANCELAR para regresar al menú.");

        String matricula = leerTexto("Ingresa la matrícula: ");

        if (matricula == null) {
            System.out.println("Operación cancelada.");
            return;
        }

        for (Estudiante estudiante : estudiantes) {

            if (estudiante.getMatricula().equalsIgnoreCase(matricula)) {

                Double nuevoPromedio =
                        leerDoubleCancelable("Nuevo promedio (0-100): ");

                if (nuevoPromedio == null) {
                    System.out.println("Operación cancelada.");
                    return;
                }

                estudiante.actualizarPromedio(nuevoPromedio);

                System.out.println("Promedio actualizado correctamente.");
                return;
            }
        }

        System.out.println(
                "No se encontró un estudiante con esa matrícula."
        );
    }

    /**
     * Muestra un resumen académico.
     */
    private static void mostrarResumen() {

        if (estudiantes.isEmpty()) {
            System.out.println(
                    "No hay datos para generar el resumen."
            );
            return;
        }

        int aprobados = 0;
        double suma = 0;

        for (Estudiante estudiante : estudiantes) {

            suma += estudiante.getPromedio();

            if (estudiante.estaAprobado()) {
                aprobados++;
            }
        }

        double promedioGeneral =
                suma / estudiantes.size();

        System.out.println("\n--- Resumen académico ---");
        System.out.println(
                "Total de estudiantes: "
                        + estudiantes.size()
        );

        System.out.println(
                "Estudiantes aprobados: "
                        + aprobados
        );

        System.out.println(
                "Estudiantes no aprobados: "
                        + (estudiantes.size() - aprobados)
        );

        System.out.printf(
                "Promedio general: %.2f%n",
                promedioGeneral
        );
    }

    /**
     * Carga algunos estudiantes de ejemplo.
     * Sirve para demostrar la instanciación de objetos.
     */
    private static void cargarDatosEjemplo() {

        estudiantes.add(
                new Estudiante(
                        "Edwin Jovanny Hernández Molina",
                        "107651",
                        "Ingeniería en Desarrollo de Software",
                        92
                )
        );

        estudiantes.add(
                new Estudiante(
                        "Ana López",
                        "107652",
                        "Ingeniería en Desarrollo de Software",
                        86
                )
        );

        estudiantes.add(
                new Estudiante(
                        "Carlos Martínez",
                        "107653",
                        "Ingeniería en Desarrollo de Software",
                        68
                )
        );
    }

    /**
     * Lee un texto y permite cancelar la operación.
     */
    private static String leerTexto(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String valor = scanner.nextLine().trim();

            if (valor.equalsIgnoreCase("CANCELAR")) {
                return null;
            }

            if (valor.isEmpty()) {
                System.out.println(
                        "El campo no puede estar vacío."
                );
            } else {
                return valor;
            }
        }
    }

    /**
     * Lee un número entero.
     */
    private static int leerEntero(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                int valor =
                        Integer.parseInt(scanner.nextLine());

                return valor;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Ingresa un número entero válido."
                );
            }
        }
    }

    /**
     * Lee un promedio y permite cancelar.
     */
    private static Double leerDoubleCancelable(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String entrada =
                    scanner.nextLine().trim();

            if (entrada.equalsIgnoreCase("CANCELAR")) {
                return null;
            }

            try {

                double valor =
                        Double.parseDouble(entrada);

                if (valor >= 0 && valor <= 100) {
                    return valor;
                }

                System.out.println(
                        "El promedio debe estar entre 0 y 100."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Ingresa un número válido o escribe CANCELAR."
                );
            }
        }
    }
}