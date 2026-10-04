/**

* Representa a un estudiante dentro del sistema.
 */
public class Estudiante {
    private String nombre;
    private String matricula;
    private String carrera;
    private double promedio;

    /**
     * Constructor de la clase Estudiante.
     */
    public Estudiante(String nombre, String matricula, String carrera, double promedio) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.carrera = carrera;
        this.promedio = promedio;
    }

    public String getNombre() { return nombre; }
    public String getMatricula() { return matricula; }
    public String getCarrera() { return carrera; }
    public double getPromedio() { return promedio; }

    /** Actualiza el promedio del estudiante. */
    public void actualizarPromedio(double nuevoPromedio) {
        if (nuevoPromedio >= 0 && nuevoPromedio <= 100) {
            promedio = nuevoPromedio;
        } else {
            System.out.println("El promedio debe estar entre 0 y 100.");
        }
    }

    /** Determina si el estudiante aprobó. */
    public boolean estaAprobado() {
        return promedio >= 70;
    }

    /** Muestra la información completa del estudiante. */
    public void mostrarInformacion() {
        System.out.println("----------------------------------------");
        System.out.println("Nombre: " + nombre);
        System.out.println("Matrícula: " + matricula);
        System.out.println("Carrera: " + carrera);
        System.out.printf("Promedio: %.2f%n", promedio);
        System.out.println("Estatus: " + (estaAprobado() ? "APROBADO" : "NO APROBADO"));
    }
}
