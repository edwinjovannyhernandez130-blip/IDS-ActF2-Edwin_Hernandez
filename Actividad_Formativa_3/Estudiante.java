/**
 * Clase base (superclase) del Sistema de Gestión de Estudiantes.
 *
 * <p>Representa a un estudiante regular y define los atributos y métodos
 * que comparten todos los tipos de estudiante. Las clases
 * {@link EstudianteBecado} y {@link EstudianteDeportivo} heredan de esta
 * clase mediante la palabra reservada {@code extends}, por lo que reutilizan
 * sus atributos (a través de los getters) y sus métodos sin volver a
 * escribirlos.</p>
 *
 * <p>Los atributos son {@code private}: ni siquiera las subclases pueden
 * modificarlos directamente. Se inicializan con el constructor, que las
 * subclases invocan con {@code super(...)}, y se consultan con los getters
 * heredados. Así se conserva el encapsulamiento de la Actividad Formativa 2.</p>
 *
 * @author Edwin Jovanny Hernández Molina
 * @version 2.0 (Actividad Formativa 3. Herencia)
 */
public class Estudiante {

    /** Calificación mínima para considerar aprobado a un estudiante. */
    public static final double PROMEDIO_APROBATORIO = 70.0;

    private final String nombre;
    private final String matricula;
    private final String carrera;
    private double promedio;

    /**
     * Crea un estudiante con sus datos básicos.
     *
     * <p>Las subclases llaman a este constructor en la primera línea de su
     * propio constructor con {@code super(nombre, matricula, carrera, promedio)}.</p>
     *
     * @param nombre    nombre completo (no puede estar vacío)
     * @param matricula identificador del estudiante (no puede estar vacío)
     * @param carrera   programa académico (no puede estar vacío)
     * @param promedio  calificación entre 0 y 100
     * @throws IllegalArgumentException si algún dato no es válido
     */
    public Estudiante(String nombre, String matricula, String carrera, double promedio) {
        this.nombre = validarTexto(nombre, "nombre");
        this.matricula = validarTexto(matricula, "matrícula");
        this.carrera = validarTexto(carrera, "carrera");
        if (!esPromedioValido(promedio)) {
            throw new IllegalArgumentException("El promedio debe estar entre 0 y 100.");
        }
        this.promedio = promedio;
    }

    // ------------------------------------------------------------------
    // Getters: se heredan sin cambios en EstudianteBecado y EstudianteDeportivo.
    // ------------------------------------------------------------------

    /** @return nombre completo del estudiante */
    public String getNombre() { return nombre; }

    /** @return matrícula del estudiante */
    public String getMatricula() { return matricula; }

    /** @return carrera del estudiante */
    public String getCarrera() { return carrera; }

    /** @return promedio actual del estudiante */
    public double getPromedio() { return promedio; }

    /**
     * Actualiza el promedio solo si el nuevo valor está entre 0 y 100.
     * Método heredado (no sobrescrito) por todas las subclases.
     *
     * @param nuevoPromedio valor que se desea asignar
     * @return {@code true} si se actualizó; {@code false} si se rechazó
     */
    public boolean actualizarPromedio(double nuevoPromedio) {
        if (esPromedioValido(nuevoPromedio)) {
            promedio = nuevoPromedio;
            return true;
        }
        System.out.println("El promedio debe estar entre 0 y 100.");
        return false;
    }

    /**
     * Determina si el estudiante aprobó (promedio mayor o igual a 70).
     * Método heredado (no sobrescrito); EstudianteDeportivo lo reutiliza
     * para decidir si el alumno es elegible para competir.
     *
     * @return {@code true} si está aprobado
     */
    public boolean estaAprobado() {
        return promedio >= PROMEDIO_APROBATORIO;
    }

    /**
     * Devuelve el tipo de estudiante. Las subclases sobrescriben este método
     * con {@code @Override}; gracias al polimorfismo, Java ejecuta la versión
     * que corresponde al objeto real aunque la referencia sea de tipo Estudiante.
     *
     * @return "Regular" para la clase base
     */
    public String getTipo() {
        return "Regular";
    }

    /**
     * Muestra la información común de cualquier estudiante.
     * Las subclases sobrescriben este método, llaman primero a
     * {@code super.mostrarInformacion()} y después agregan sus datos propios.
     */
    public void mostrarInformacion() {
        System.out.println("----------------------------------------");
        System.out.println("Tipo: " + getTipo());
        System.out.println("Nombre: " + nombre);
        System.out.println("Matrícula: " + matricula);
        System.out.println("Carrera: " + carrera);
        System.out.printf("Promedio: %.2f%n", promedio);
        System.out.println("Estatus: " + (estaAprobado() ? "APROBADO" : "NO APROBADO"));
    }

    // ------------------------------------------------------------------
    // Métodos auxiliares de validación (protected static): las subclases
    // los reutilizan para validar sus propios atributos.
    // ------------------------------------------------------------------

    /**
     * Verifica que un texto no sea nulo ni esté vacío.
     *
     * @param valor texto a validar
     * @param campo nombre del campo (para el mensaje de error)
     * @return el texto sin espacios al inicio ni al final
     * @throws IllegalArgumentException si el texto está vacío
     */
    protected static String validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacío.");
        }
        return valor.trim();
    }

    /**
     * @param valor promedio a evaluar
     * @return {@code true} si el valor está entre 0 y 100
     */
    protected static boolean esPromedioValido(double valor) {
        return valor >= 0 && valor <= 100;
    }
}
