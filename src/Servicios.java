import java.util.*;
import java.io.*;

public class Servicios {

    private List<Camion> camiones;

    // para servicio 1
    private Map<String, Paquete> indiceCodigoPaquete; //tipo String con obj paquete

    // lista con todos los paquetes
    private List<Paquete> todosLosPaquetes;

     //Complejidad O(N + M), N camiones, m paquetes. Se recorre cada .csv una vez

    public Servicios(String pathCamiones, String pathPaquetes) {
        this.camiones = new ArrayList<>();
        this.indiceCodigoPaquete = new HashMap<>();
        this.todosLosPaquetes = new ArrayList<>();
        this.cargarCamiones(pathCamiones);
        this.cargarPaquetes(pathPaquetes);
    }

    private void cargarCamiones(String archivo) {

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            br.readLine(); // saltamos la primera linea que tiene el total
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";"); //arreglo con datos, cada dato en base a la separación ";" del .csv

                int id = Integer.parseInt(datos[0]); //parseInt ya que esta llegando como String desde el arreglo
                String patente = datos[1];
                boolean refrigerado = datos[2].equals("1");
                int capacidad = Integer.parseInt(datos[3]);

                Camion c = new Camion(id, patente, refrigerado, capacidad);
                this.camiones.add(c);
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    private void cargarPaquetes(String archivo) {

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            br.readLine();
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");

                int id = Integer.parseInt(datos[0]);
                String codigo = datos[1];
                int peso = Integer.parseInt(datos[2]);
                boolean alimentos = datos[3].equals("1");
                int urgencia = Integer.parseInt(datos[4]);

                Paquete p = new Paquete(id, codigo, peso, alimentos, urgencia);

                this.indiceCodigoPaquete.put(codigo, p);
                this.todosLosPaquetes.add(p);
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

     /*
      * Complejidad : O(1) ?
      *El hashmap permite acceso asociativo
     */

    public Paquete servicio1(String codigoPaquete) {
        return this.indiceCodigoPaquete.get(codigoPaquete);
    }

     /*Complejidad : O(n), n = paquetes;
     *Tiene que recorrer toda la lista para ver cual cumple, ya que es una lista la salida no un resultado solo.
     */
    public List<Paquete> servicio2(boolean contieneAlimentos) {
        List<Paquete> resultado = new ArrayList<>();
        for (Paquete p : todosLosPaquetes) {
            if (p.getContieneAlimentos() == contieneAlimentos) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /*
     * Complejidad: O(n), n = paquetes;
     * Igual que el anterior, se recorre toda la lista
     */
    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {
        List<Paquete> resultado = new ArrayList<>();
        for (Paquete p : todosLosPaquetes) {
            int urg = p.getNivelUrgencia();
            if (urg >= urgenciaMinima && urg <= urgenciaMaxima) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}