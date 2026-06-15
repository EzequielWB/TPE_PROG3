import java.util.*;

public class Solucion {

    private Map<Camion, List<Paquete>> asignaciones; //Cada camión con un paquete
    private double pesoNoAsignado;
    private int metrica; // Candidatos para cada tipo de solución, greedy o back

    public Solucion() {
        this.asignaciones = new HashMap<>();
        this.pesoNoAsignado = 0;
        this.metrica = 0;
    }

    public void agregarAsignacion(Camion c, Paquete p) {

        List<Paquete> lista = this.asignaciones.get(c);

        if (lista == null) {
            lista = new ArrayList<>();
            this.asignaciones.put(c, lista);
        }

        lista.add(p);
    }

    public void setPesoNoAsignado(double peso) {
        this.pesoNoAsignado = peso;
    }

    public void setMetrica(int valor) {
        this.metrica = valor;
    }

    public double getPesoNoAsignado() {
        return pesoNoAsignado;
    }
    public int getMetrica() {
        return metrica;
    }

    public Map<Camion, List<Paquete>> getAsignaciones() {
        return asignaciones;
    }
}