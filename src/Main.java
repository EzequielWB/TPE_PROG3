import java.util.List;

public static void main(String[] args) {

    Servicios sistema = new Servicios("Camiones.csv", "Paquetes.csv");

    /*SERVICIO 1;
    *Dado un código de paquete (String), retornar toda la información
    *del paquete asociado. En caso de no existir, retornar null.
    */
    String codigoBuscado = "P001";
    Paquete p = sistema.servicio1(codigoBuscado);

    if (p != null) {
        System.out.println("Paquete encontrado: " + p.getCodigoPaquete());
        System.out.println("Peso: " + p.getPesoKg() + "kg");
        System.out.println("Urgencia: " + p.getNivelUrgencia());
    } else {
        System.out.println("El paquete con codigo " + codigoBuscado + " no existe.");
    }

    /*SERVICIO 2
    *Dado un booleano que indica si se buscan paquetes que
    *contienen alimentos (true) o que no contienen alimentos (false), retornar el
    *listado de paquetes correspondiente.
    */
    System.out.println("\nPaquetes con alimentos:");
    List<Paquete> conAlimentos = sistema.servicio2(true);
    for(Paquete pac : conAlimentos) {
        System.out.println("- " + pac.getCodigoPaquete());
    }

    /* SERVICIO 3
    *Dados dos valores enteros que representan un nivel de urgencia
    *mínimo y máximo, retornar todos los paquetes cuyo nivel de urgencia se
    *encuentre dentro de ese rango (inclusive).
    */
    System.out.println("\nPaquetes con urgencia entre 90 y 100:");
    List<Paquete> urgentes = sistema.servicio3(90, 100);
    for(Paquete pac : urgentes) {
        System.out.println("- " + pac.getCodigoPaquete() + " (Urgencia: " + pac.getNivelUrgencia() + ")");
    }

}

