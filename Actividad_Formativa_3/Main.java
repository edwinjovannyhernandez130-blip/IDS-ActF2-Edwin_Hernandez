import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Programa principal del Sistema de Gestión de Estudiantes (versión 2.0).
 *
 * <p>Amplía el sistema de la Actividad Formativa 2 con herencia: la lista
 * {@code List<Estudiante>} guarda objetos de la clase base y de sus
 * subclases (EstudianteBecado y EstudianteDeportivo) mediante referencias
 * de tipo Estudiante. Al llamar a {@code mostrarInformacion()} o
 * {@code getTipo()} sobre esas referencias, Java ejecuta la versión
 * sobrescrita de cada subclase (polimorfismo).</p>
 *
 * <p>Mejora adicional: el sistema ya no acepta matrículas duplicadas,
 * limitación identificada en las pruebas de la versión anterior.</p>
 *
 * @author Edwin Jovanny Hernández Molina
 * @version 2.0 (Actividad Formativa 3. Herencia)
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    /** Colección polimórfica: admite Estudiante, EstudianteBecado y EstudianteDeportivo. */
    private static final List<Estudiante> estudiantes = new ArrayList<>();

    private static final int OPCION_SALIR = 10;

    /**
     * Agrupa los cuatro datos que comparten todos los tipos de estudiante.
     * Permite capturarlos con un solo método y evitar código duplicado
     * en los tres procesos de registro.
     */
    private record DatosBase(String nombre, String matricula, String carrera, double promedio) { }

    public static void main(String[] args) {

        cargarDatosEjemplo();

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1 -> registrarEstudianteRegular();
                case 2 -> registrarEstudianteBecado();
                case 3 -> registrarEstudianteDeportivo();
                case 4 -> mostrarEstudiantes();
                case 5 -> consultarTipoEstudiante();
                case 6 -> ejecutarMetodosHeredados();
                case 7 -> ejecutarMetodosExclusivos();
                case 8 -> actualizarPromedio();
                case 9 -> mostrarResumen();
                case OPCION_SALIR -> System.out.println("\nPrograma finalizado. ¡Hasta luego!");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != OPCION_SALIR);

        scanner.close();
    }

    /** Muestra el menú principal. */
    private static void mostrarMenu() {
        System.out.println("\n========================================");
        System.out.println(" SISTEMA DE GESTIÓN DE ESTUDIANTES 2.0");
        System.out.println("========================================");
        System.out.println(" 1. Registrar estudiante regular");
        System.out.println(" 2. Registrar estudiante becado");
        System.out.println(" 3. Registrar estudiante deportivo");
        System.out.println(" 4. Mostrar estudiantes");
        System.out.println(" 5. Consultar tipo de estudiante");
        System.out.println(" 6. Ejecutar métodos heredados");
        System.out.println(" 7. Ejecutar métodos exclusivos");
        System.out.println(" 8. Actualizar promedio");
        System.out.println(" 9. Mostrar resumen");
        System.out.println("10. Salir");
    }

    // ==================================================================
    // Registro de estudiantes
    // ==================================================================

    /** Opción 1: registra un objeto de la clase base Estudiante. */
    private static void registrarEstudianteRegular() {
        System.out.println("\n--- Registrar estudiante regular ---");
        DatosBase datos = leerDatosBase();
        if (datos == null) {
            System.out.println("Registro cancelado.");
            return;
        }
        agregar(new Estudiante(datos.nombre(), datos.matricula(), datos.carrera(), datos.promedio()));
    }

    /** Opción 2: registra un objeto de la subclase EstudianteBecado. */
    private static void registrarEstudianteBecado() {
        System.out.println("\n--- Registrar estudiante becado ---");
        DatosBase datos = leerDatosBase();
        if (datos == null) {
            System.out.println("Registro cancelado.");
            return;
        }

        String tipoBeca = leerOpcion("Tipo de beca:", EstudianteBecado.TIPOS_BECA);
        if (tipoBeca == null) {
            System.out.println("Registro cancelado.");
            return;
        }

        Double porcentaje = leerDoubleCancelable("Porcentaje de beca (1-100): ", 1, 100,
                "El porcentaje debe estar entre 1 y 100.");
        if (porcentaje == null) {
            System.out.println("Registro cancelado.");
            return;
        }

        agregar(new EstudianteBecado(datos.nombre(), datos.matricula(), datos.carrera(),
                datos.promedio(), tipoBeca, porcentaje));
    }

    /** Opción 3: registra un objeto de la subclase EstudianteDeportivo. */
    private static void registrarEstudianteDeportivo() {
        System.out.println("\n--- Registrar estudiante deportivo ---");
        DatosBase datos = leerDatosBase();
        if (datos == null) {
            System.out.println("Registro cancelado.");
            return;
        }

        String disciplina = leerTexto("Disciplina deportiva: ");
        if (disciplina == null) {
            System.out.println("Registro cancelado.");
            return;
        }

        String nivel = leerOpcion("Nivel deportivo:", EstudianteDeportivo.NIVELES);
        if (nivel == null) {
            System.out.println("Registro cancelado.");
            return;
        }

        agregar(new EstudianteDeportivo(datos.nombre(), datos.matricula(), datos.carrera(),
                datos.promedio(), disciplina, nivel));
    }

    /**
     * Captura los datos comunes a los tres tipos de estudiante.
     * La matrícula se valida para que no exista otra igual.
     *
     * @return los datos capturados o {@code null} si el usuario escribió CANCELAR
     */
    private static DatosBase leerDatosBase() {
        System.out.println("Escribe CANCELAR en cualquier momento para regresar al menú.");

        String nombre = leerTexto("Nombre: ");
        if (nombre == null) return null;

        String matricula = leerMatriculaNueva();
        if (matricula == null) return null;

        String carrera = leerTexto("Carrera: ");
        if (carrera == null) return null;

        Double promedio = leerDoubleCancelable("Promedio (0-100): ", 0, 100,
                "El promedio debe estar entre 0 y 100.");
        if (promedio == null) return null;

        return new DatosBase(nombre, matricula, carrera, promedio);
    }

    /**
     * Agrega a la lista cualquier objeto Estudiante o de sus subclases.
     * El parámetro es de tipo Estudiante, por lo que acepta las tres clases.
     *
     * @param estudiante objeto a registrar
     */
    private static void agregar(Estudiante estudiante) {
        estudiantes.add(estudiante);
        System.out.println("\n¡Estudiante " + estudiante.getTipo().toLowerCase()
                + " registrado correctamente!");
    }

    // ==================================================================
    // Consultas
    // ==================================================================

    /**
     * Opción 4: muestra todos los estudiantes. Cada objeto ejecuta su propia
     * versión de mostrarInformacion() (polimorfismo).
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

    /** Opción 5: identifica la clase real de un estudiante buscado por matrícula. */
    private static void consultarTipoEstudiante() {
        System.out.println("\n--- Consultar tipo de estudiante ---");
        Estudiante estudiante = solicitarEstudianteExistente();
        if (estudiante == null) return;

        System.out.println("Nombre: " + estudiante.getNombre());
        System.out.println("Tipo de estudiante (getTipo()): " + estudiante.getTipo());
        System.out.println("Clase del objeto: " + estudiante.getClass().getSimpleName());
        System.out.println("Clase padre: " + estudiante.getClass().getSuperclass().getSimpleName());
        System.out.println("¿Es un Estudiante? " + (estudiante instanceof Estudiante ? "Sí" : "No"));
    }

    /**
     * Opción 6: ejecuta sobre todos los objetos métodos que solo están
     * escritos en la clase base, para demostrar que las subclases los heredan.
     */
    private static void ejecutarMetodosHeredados() {
        System.out.println("\n--- Métodos heredados de Estudiante ---");
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        System.out.println("getNombre(), getMatricula(), getPromedio() y estaAprobado()");
        System.out.println("se definen solo en Estudiante y funcionan en las tres clases:\n");
        System.out.printf("%-20s %-10s %-32s %8s  %s%n",
                "Clase", "Matrícula", "Nombre", "Promedio", "Aprobado");
        for (Estudiante e : estudiantes) {
            System.out.printf("%-20s %-10s %-32s %8.2f  %s%n",
                    e.getClass().getSimpleName(), e.getMatricula(), e.getNombre(),
                    e.getPromedio(), e.estaAprobado() ? "Sí" : "No");
        }
    }

    /**
     * Opción 7: ejecuta el método exclusivo que corresponde a la subclase.
     * Se usa instanceof con patrón (Java 16+) para convertir la referencia
     * de tipo Estudiante al tipo de la subclase de forma segura.
     */
    private static void ejecutarMetodosExclusivos() {
        System.out.println("\n--- Métodos exclusivos de las subclases ---");
        Estudiante estudiante = solicitarEstudianteExistente();
        if (estudiante == null) return;

        if (estudiante instanceof EstudianteBecado becado) {
            Double colegiatura = leerDoubleCancelable("Colegiatura del periodo ($): ", 0.01, 1_000_000,
                    "La colegiatura debe ser mayor que 0.");
            if (colegiatura == null) {
                System.out.println("Operación cancelada.");
                return;
            }
            double beneficio = becado.calcularBeneficio(colegiatura);
            System.out.println("calcularBeneficio():");
            System.out.printf("  Beca %s del %.0f%%: $%,.2f%n",
                    becado.getTipoBeca(), becado.getPorcentajeBeca(), beneficio);
            System.out.printf("  Monto a pagar: $%,.2f%n", colegiatura - beneficio);
            System.out.println("conservaBeca(): " + (becado.conservaBeca()
                    ? "SÍ conserva la beca."
                    : "NO conserva la beca (promedio menor a 85)."));

        } else if (estudiante instanceof EstudianteDeportivo deportivo) {
            System.out.println("evaluarParticipacion(): " + deportivo.evaluarParticipacion());

        } else {
            System.out.println(estudiante.getNombre() + " es un estudiante regular: "
                    + "solo dispone de los métodos de la clase base.");
        }
    }

    /** Opción 8: actualiza el promedio con el método heredado actualizarPromedio(double). */
    private static void actualizarPromedio() {
        System.out.println("\n--- Actualizar promedio ---");
        Estudiante estudiante = solicitarEstudianteExistente();
        if (estudiante == null) return;

        Double nuevoPromedio = leerDoubleCancelable("Nuevo promedio (0-100): ", 0, 100,
                "El promedio debe estar entre 0 y 100.");
        if (nuevoPromedio == null) {
            System.out.println("Operación cancelada.");
            return;
        }

        if (estudiante.actualizarPromedio(nuevoPromedio)) {
            System.out.println("Promedio de " + estudiante.getNombre() + " ("
                    + estudiante.getTipo() + ") actualizado correctamente.");
        }
    }

    /** Opción 9: muestra el resumen académico, ahora con el conteo por tipo. */
    private static void mostrarResumen() {
        if (estudiantes.isEmpty()) {
            System.out.println("No hay datos para generar el resumen.");
            return;
        }

        int aprobados = 0;
        int regulares = 0;
        int becados = 0;
        int deportivos = 0;
        int conservanBeca = 0;
        double suma = 0;

        for (Estudiante e : estudiantes) {
            suma += e.getPromedio();
            if (e.estaAprobado()) aprobados++;

            if (e instanceof EstudianteBecado becado) {
                becados++;
                if (becado.conservaBeca()) conservanBeca++;
            } else if (e instanceof EstudianteDeportivo) {
                deportivos++;
            } else {
                regulares++;
            }
        }

        System.out.println("\n--- Resumen académico ---");
        System.out.println("Total de estudiantes: " + estudiantes.size());
        System.out.println("  Regulares: " + regulares);
        System.out.println("  Becados: " + becados + " (conservan la beca: " + conservanBeca + ")");
        System.out.println("  Deportivos: " + deportivos);
        System.out.println("Estudiantes aprobados: " + aprobados);
        System.out.println("Estudiantes no aprobados: " + (estudiantes.size() - aprobados));
        System.out.printf("Promedio general: %.2f%n", suma / estudiantes.size());
    }

    // ==================================================================
    // Búsqueda y datos de ejemplo
    // ==================================================================

    /**
     * Busca un estudiante por matrícula sin distinguir mayúsculas y minúsculas.
     *
     * @param matricula matrícula buscada
     * @return el estudiante encontrado o {@code null} si no existe
     */
    private static Estudiante buscarPorMatricula(String matricula) {
        for (Estudiante e : estudiantes) {
            if (e.getMatricula().equalsIgnoreCase(matricula)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Solicita una matrícula y devuelve el estudiante correspondiente.
     *
     * @return el estudiante o {@code null} si se canceló o no existe
     */
    private static Estudiante solicitarEstudianteExistente() {
        System.out.println("Escribe CANCELAR para regresar al menú.");
        String matricula = leerTexto("Ingresa la matrícula: ");
        if (matricula == null) {
            System.out.println("Operación cancelada.");
            return null;
        }
        Estudiante estudiante = buscarPorMatricula(matricula);
        if (estudiante == null) {
            System.out.println("No se encontró un estudiante con esa matrícula.");
        }
        return estudiante;
    }

    /**
     * Lee una matrícula que todavía no esté registrada.
     * Corrige la limitación de la versión 1.0, que aceptaba duplicados.
     *
     * @return matrícula nueva o {@code null} si se escribió CANCELAR
     */
    private static String leerMatriculaNueva() {
        while (true) {
            String matricula = leerTexto("Matrícula: ");
            if (matricula == null) return null;

            Estudiante existente = buscarPorMatricula(matricula);
            if (existente == null) return matricula;

            System.out.println("Ya existe un estudiante con la matrícula " + existente.getMatricula()
                    + " (" + existente.getNombre() + "). Ingresa otra matrícula.");
        }
    }

    /**
     * Carga estudiantes de ejemplo de los tres tipos. Las referencias son de
     * tipo Estudiante, pero los objetos pueden ser de cualquier subclase.
     */
    private static void cargarDatosEjemplo() {
        String carrera = "Ingeniería en Desarrollo de Software";

        estudiantes.add(new Estudiante("Edwin Jovanny Hernández Molina", "107651", carrera, 92));
        estudiantes.add(new Estudiante("Ana López", "107652", carrera, 86));
        estudiantes.add(new Estudiante("Carlos Martínez", "107653", carrera, 68));
        estudiantes.add(new EstudianteBecado("María Fernanda Garza", "107654", carrera, 91,
                "Excelencia", 50));
        estudiantes.add(new EstudianteDeportivo("Luis Ángel Treviño", "107655", carrera, 78,
                "Fútbol soccer", "Selección universitaria"));
    }

    // ==================================================================
    // Lectura y validación de datos
    // ==================================================================

    /**
     * Lee un texto no vacío y permite cancelar.
     *
     * @param mensaje texto que se muestra al usuario
     * @return el texto capturado o {@code null} si se escribió CANCELAR
     */
    private static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim();

            if (valor.equalsIgnoreCase("CANCELAR")) {
                return null;
            }
            if (valor.isEmpty()) {
                System.out.println("El campo no puede estar vacío.");
            } else {
                return valor;
            }
        }
    }

    /**
     * Lee un número entero; vuelve a pedirlo si la entrada no es válida.
     *
     * @param mensaje texto que se muestra al usuario
     * @return número entero capturado
     */
    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número entero válido.");
            }
        }
    }

    /**
     * Lee un número decimal dentro de un rango y permite cancelar.
     * Generaliza el método de la versión 1.0 para reutilizarlo con el
     * promedio, el porcentaje de beca y la colegiatura.
     *
     * @param mensaje      texto que se muestra al usuario
     * @param minimo       valor mínimo aceptado
     * @param maximo       valor máximo aceptado
     * @param mensajeRango aviso cuando el valor está fuera del rango
     * @return el valor capturado o {@code null} si se escribió CANCELAR
     */
    private static Double leerDoubleCancelable(String mensaje, double minimo, double maximo,
                                               String mensajeRango) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            if (entrada.equalsIgnoreCase("CANCELAR")) {
                return null;
            }
            try {
                double valor = Double.parseDouble(entrada);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
                System.out.println(mensajeRango);
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido o escribe CANCELAR.");
            }
        }
    }

    /**
     * Muestra una lista numerada de opciones y devuelve la elegida.
     *
     * @param titulo   encabezado de la lista
     * @param opciones valores permitidos
     * @return la opción elegida o {@code null} si se escribió CANCELAR
     */
    private static String leerOpcion(String titulo, String[] opciones) {
        System.out.println(titulo);
        for (int i = 0; i < opciones.length; i++) {
            System.out.println("  " + (i + 1) + ". " + opciones[i]);
        }
        while (true) {
            System.out.print("Elige una opción (1-" + opciones.length + "): ");
            String entrada = scanner.nextLine().trim();

            if (entrada.equalsIgnoreCase("CANCELAR")) {
                return null;
            }
            try {
                int indice = Integer.parseInt(entrada);
                if (indice >= 1 && indice <= opciones.length) {
                    return opciones[indice - 1];
                }
            } catch (NumberFormatException e) {
                // Se informa abajo junto con el caso fuera de rango.
            }
            System.out.println("Opción no válida. Elige un número de la lista o escribe CANCELAR.");
        }
    }
}
