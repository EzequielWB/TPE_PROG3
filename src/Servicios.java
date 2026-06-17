import java.util.*;
import java.io.*;

public class Servicios {

    private List<Camion> camiones;

    // Para servicio 1, Complejidad: O(1)
    private Map<String, Paquete> indiceCodigoPaquete; //tipo String con obj paquete

    // Lista con todos los paquetes para servicio 2 y 3, Complejidad O(n) (recorre toda la lista)
    private List<Paquete> todosLosPaquetes;
    private Solucion mejorSolucionBacktracking;
    private int estadosGenerados; //metrica back

     //Complejidad O(N + M), N archivo camiones.csv, M archivo paquetes.csv. Se recorre cada .csv una vez
    public Servicios(String pathCamiones, String pathPaquetes) {
        this.camiones = new ArrayList<>();
        this.indiceCodigoPaquete = new HashMap<>();
        this.todosLosPaquetes = new ArrayList<>();
        this.cargarCamiones(pathCamiones);
        this.cargarPaquetes(pathPaquetes);
    }

    private List<Camion> getCamiones() {
        return camiones;
    }

    private List<Paquete> getTodosLosPaquetes() {
        return todosLosPaquetes;
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
      *El hashmap permite acceso asociativo, por lo que se recupera en tiempo constante
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
    /*
        Estrategia Backtraking
        Explorar exhaustivamente todas las combinaciones posibles de asignación
        de paquetes a camiones. Para cada paquete, intentar asignarlo a cada
        camión que cumpla las restricciones (capacidad y refrigeración).
        Se mantiene la mejor solución encontrada (mínimo peso sin asignar).
     */
        public Solucion backtracking() {
            this.mejorSolucionBacktracking = null;
            this.estadosGenerados = 0;
            int[]pesoActualCamion= new int[this.camiones.size()];
            List<Paquete>[]caminoActualCamiones = new ArrayList[camiones.size()];
            for (int i = 0; i < this.camiones.size(); i++) {
                caminoActualCamiones[i] = new ArrayList<>();
            }
            back(0,pesoActualCamion,caminoActualCamiones);
            return this.mejorSolucionBacktracking;
        }
        public void back(int indicePaquete,int[] pesoActualCamion, List<Paquete>[] caminoActualCamiones) {
            //metrica back
            estadosGenerados++;

            //caso base si ya se recorrio todos los paquetes que corte
            if(indicePaquete== todosLosPaquetes.size()){
                Solucion candidato=ConstruirSolucion(caminoActualCamiones);
                //es mejor que la que se encontro?
                if(mejorSolucionBacktracking==null || candidato.getPesoNoAsignado()<mejorSolucionBacktracking.getPesoNoAsignado()){
                    mejorSolucionBacktracking=candidato;
                }
                return;
            }
            Paquete paqueteActual = todosLosPaquetes.get(indicePaquete);
            int pesoPaquete = paqueteActual.getPesoKg();
            boolean tieneAlimentos=paqueteActual.getContieneAlimentos();

            for(int i=0;i<camiones.size();i++){
                Camion camion = camiones.get(i);
                if(puedoAsignar(camion,pesoPaquete,tieneAlimentos,pesoActualCamion[i])){
                    //le sumo el peso del paquete y agrego al camino actual (camion) el paquete actual
                    pesoActualCamion[i]+=pesoPaquete;
                    caminoActualCamiones[i].add(paqueteActual);
                    //back
                    back(indicePaquete+1,pesoActualCamion,caminoActualCamiones);
                    //deshago lo que hice antes de volver a probar con otro camion
                    pesoActualCamion[i]-=pesoPaquete;
                    caminoActualCamiones[i].remove(caminoActualCamiones[i].size()-1);
                }
            }
            //no asigno el paquete a ningun camion
            back(indicePaquete + 1, pesoActualCamion, caminoActualCamiones);
        }

    public int getEstadosGenerados() {
        return estadosGenerados;
    }

    // Verificar si puedo asignar un paquete a un camión
    private boolean puedoAsignar(Camion camion, int peso, boolean tieneAlimentos, int pesoActual) {
        //No superar capacidad
        if (pesoActual + peso > camion.getCapacidadKg()) {
            return false;
        }
        // Alimentos solo en refrigerados
        if (tieneAlimentos && !camion.getEstaRefrigerado()) {
            return false;
        }
        return true;
    }
        public Solucion ConstruirSolucion(List<Paquete>[] caminoActualCamiones){
            Solucion solucion = new Solucion();
            double pesoNoAsignado = 0;
            for (int i = 0; i < camiones.size(); i++) {
                Camion c = camiones.get(i);
                List<Paquete> paquetesAsignados = caminoActualCamiones[i];
                for (Paquete p : paquetesAsignados) {
                    solucion.agregarAsignacion(c, p);
                }
                double pesoAsignado = paquetesAsignados.stream().mapToDouble(Paquete::getPesoKg).sum();
                if (pesoAsignado > c.getCapacidadKg()) {
                    pesoNoAsignado += pesoAsignado - c.getCapacidadKg();
                }
            }
            solucion.setPesoNoAsignado(pesoNoAsignado);
            return solucion;
        }

    /*
     * ESTRATEGIA GREEDY:
     * 1-Seleccionamos el paquete mas pesado disponible primero
     * 2-Buscamos el primer camión que pueda llevarlo respetando capacidad y frío.
     */
    public Solucion asignacionGreedy() {
        Solucion resultado = new Solucion();
        int candidatosConsiderados = 0;
        double pesoTotalNoAsignado = 0;

        List<Paquete> candidatos = new ArrayList<>(todosLosPaquetes); //Ordenamos los pquetes despues
        Collections.sort(candidatos, (p1, p2) -> Double.compare(p2.getPesoKg(), p1.getPesoKg()));

        Map<Integer, Double> cargaActualCamiones = new HashMap<>();
        for (Camion c : camiones) {
            cargaActualCamiones.put(c.getId_camion(), 0.0); //0.0 para evitar NUllPointer
        }

        //Greedy
        for (Paquete p : candidatos) {
            boolean asignado = false;
            int i = 0;

            while (i < camiones.size() && !asignado) {
                Camion c = camiones.get(i);
                candidatosConsiderados++;

                double cargaActual = cargaActualCamiones.get(c.getId_camion());
                boolean entraPorPeso = (cargaActual + p.getPesoKg()) <= c.getCapacidadKg();
                boolean cumpleFrio = (!p.getContieneAlimentos()) || (c.getEstaRefrigerado());

                if (entraPorPeso && cumpleFrio) {
                    resultado.agregarAsignacion(c, p);
                    cargaActualCamiones.put(c.getId_camion(), cargaActual + p.getPesoKg());
                    asignado = true;
                }
                i++;
            }

            if (!asignado) {
                pesoTotalNoAsignado += p.getPesoKg();
            }
        }

        resultado.setPesoNoAsignado(pesoTotalNoAsignado);
        resultado.setMetrica(candidatosConsiderados);
        return resultado;
    }
}