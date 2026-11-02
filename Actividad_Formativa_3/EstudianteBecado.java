/**
 * Clase derivada (subclase) que representa a un estudiante con beca.
 *
 * <p>{@code EstudianteBecado extends Estudiante}: hereda nombre, matrícula,
 * carrera y promedio, los getters, {@code actualizarPromedio(double)} y
 * {@code estaAprobado()}. Agrega dos atributos propios (tipo y porcentaje de
 * beca), dos métodos exclusivos ({@code calcularBeneficio} y
 * {@code conservaBeca}) y sobrescribe {@code getTipo()} y
 * {@code mostrarInformacion()}.</p>
 *
 * @author Edwin Jovanny Hernández Molina
 * @version 1.0 (Actividad Formativa 3. Herencia)
 */
public class EstudianteBecado extends Estudiante {

    /** Promedio mínimo que debe mantener un becado para conservar la beca. */
    public static final double PROMEDIO_MINIMO_BECA = 85.0;

    /** Tipos de beca permitidos en el sistema. */
    public static final String[] TIPOS_BECA = {"Académica", "Excelencia", "Socioeconómica"};

    private final String tipoBeca;
    private final double porcentajeBeca;

    /**
     * Crea un estudiante becado.
     *
     * @param nombre         nombre completo
     * @param matricula      matrícula
     * @param carrera        carrera
     * @param promedio       promedio entre 0 y 100
     * @param tipoBeca       tipo de beca (Académica, Excelencia o Socioeconómica)
     * @param porcentajeBeca porcentaje de descuento, mayor que 0 y hasta 100
     * @throws IllegalArgumentException si algún dato no es válido
     */
    public EstudianteBecado(String nombre, String matricula, String carrera, double promedio,
                            String tipoBeca, double porcentajeBeca) {
        // super(...) debe ser la primera instrucción: ejecuta el constructor de
        // Estudiante, que inicializa y valida los atributos heredados.
        super(nombre, matricula, carrera, promedio);

        // Reutiliza el método protegido heredado de la clase base.
        this.tipoBeca = validarTexto(tipoBeca, "tipo de beca");
        if (porcentajeBeca <= 0 || porcentajeBeca > 100) {
            throw new IllegalArgumentException("El porcentaje de beca debe ser mayor que 0 y máximo 100.");
        }
        this.porcentajeBeca = porcentajeBeca;
    }

    /** @return tipo de beca */
    public String getTipoBeca() { return tipoBeca; }

    /** @return porcentaje de beca */
    public double getPorcentajeBeca() { return porcentajeBeca; }

    /**
     * Método exclusivo: calcula el monto que cubre la beca.
     *
     * @param colegiatura monto de la colegiatura (mayor que 0)
     * @return colegiatura multiplicada por el porcentaje de beca
     * @throws IllegalArgumentException si la colegiatura no es positiva
     */
    public double calcularBeneficio(double colegiatura) {
        if (colegiatura <= 0) {
            throw new IllegalArgumentException("La colegiatura debe ser mayor que 0.");
        }
        return colegiatura * porcentajeBeca / 100;
    }

    /**
     * Método exclusivo: indica si el estudiante conserva la beca.
     * Reutiliza el getter heredado {@code getPromedio()}, porque el atributo
     * promedio es private en la clase base.
     *
     * @return {@code true} si el promedio es mayor o igual a 85
     */
    public boolean conservaBeca() {
        return getPromedio() >= PROMEDIO_MINIMO_BECA;
    }

    /** Sobrescritura: identifica el objeto como estudiante becado. */
    @Override
    public String getTipo() {
        return "Becado";
    }

    /**
     * Sobrescritura: reutiliza la impresión de la clase base con
     * {@code super.mostrarInformacion()} y agrega los datos de la beca.
     */
    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Tipo de beca: " + tipoBeca);
        System.out.printf("Porcentaje de beca: %.0f%%%n", porcentajeBeca);
        System.out.println("Conserva la beca: " + (conservaBeca() ? "SÍ" : "NO (requiere 85 o más)"));
    }
}
