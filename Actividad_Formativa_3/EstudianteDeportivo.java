/**
 * Clase derivada (subclase) que representa a un estudiante que pertenece
 * a un equipo deportivo de la universidad.
 *
 * <p>{@code EstudianteDeportivo extends Estudiante}: hereda los atributos y
 * métodos de la clase base, agrega la disciplina y el nivel deportivo,
 * define el método exclusivo {@code evaluarParticipacion()} y sobrescribe
 * {@code getTipo()} y {@code mostrarInformacion()}.</p>
 *
 * @author Edwin Jovanny Hernández Molina
 * @version 1.0 (Actividad Formativa 3. Herencia)
 */
public class EstudianteDeportivo extends Estudiante {

    /** Niveles deportivos permitidos en el sistema. */
    public static final String[] NIVELES = {"Recreativo", "Competitivo", "Selección universitaria"};

    private final String disciplinaDeportiva;
    private final String nivelDeportivo;

    /**
     * Crea un estudiante deportivo.
     *
     * @param nombre              nombre completo
     * @param matricula           matrícula
     * @param carrera             carrera
     * @param promedio            promedio entre 0 y 100
     * @param disciplinaDeportiva deporte que practica (no puede estar vacío)
     * @param nivelDeportivo      Recreativo, Competitivo o Selección universitaria
     * @throws IllegalArgumentException si algún dato no es válido
     */
    public EstudianteDeportivo(String nombre, String matricula, String carrera, double promedio,
                               String disciplinaDeportiva, String nivelDeportivo) {
        // Inicializa la parte heredada mediante el constructor de Estudiante.
        super(nombre, matricula, carrera, promedio);
        this.disciplinaDeportiva = validarTexto(disciplinaDeportiva, "disciplina deportiva");
        this.nivelDeportivo = validarTexto(nivelDeportivo, "nivel deportivo");
    }

    /** @return disciplina deportiva */
    public String getDisciplinaDeportiva() { return disciplinaDeportiva; }

    /** @return nivel deportivo */
    public String getNivelDeportivo() { return nivelDeportivo; }

    /**
     * Método exclusivo: evalúa si el estudiante puede representar a la
     * universidad en competencias. Reutiliza el método heredado
     * {@code estaAprobado()}: solo los alumnos aprobados son elegibles.
     *
     * @return texto con el resultado de la evaluación
     */
    public String evaluarParticipacion() {
        if (!estaAprobado()) {
            return "NO ELEGIBLE: debe alcanzar un promedio mínimo de "
                    + (int) PROMEDIO_APROBATORIO + " para competir.";
        }
        if (nivelDeportivo.equals("Recreativo")) {
            return "ELEGIBLE para torneos internos de " + disciplinaDeportiva + ".";
        }
        return "ELEGIBLE para representar a la universidad en " + disciplinaDeportiva
                + " (nivel " + nivelDeportivo + ").";
    }

    /** Sobrescritura: identifica el objeto como estudiante deportivo. */
    @Override
    public String getTipo() {
        return "Deportivo";
    }

    /**
     * Sobrescritura: reutiliza la impresión de la clase base y agrega
     * los datos deportivos.
     */
    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Disciplina: " + disciplinaDeportiva);
        System.out.println("Nivel deportivo: " + nivelDeportivo);
    }
}
