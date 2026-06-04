public class Paquete {

    private int idPaquete;
    private String codigoPaquete;
    private int pesoKg;
    private boolean contieneAlimentos;
    private int nivelUrgencia;

    public Paquete(int id, String codigo, int peso, boolean contAliment, int urgencia){
        this.idPaquete = id;
        this.codigoPaquete = codigo;
        this.pesoKg = peso;
        this.contieneAlimentos = contAliment;
        this.nivelUrgencia = urgencia;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getCodigoPaquete() {
        return codigoPaquete;
    }

    public void setCodigoPaquete(String codigoPaquete) {
        this.codigoPaquete = codigoPaquete;
    }

    public int getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(int pesoKg) {
        this.pesoKg = pesoKg;
    }

    public boolean isContieneAlimentos() {
        return contieneAlimentos;
    }

    public void setContieneAlimentos(boolean contieneAlimentos) {
        this.contieneAlimentos = contieneAlimentos;
    }

    public boolean getContieneAlimentos(){
        return contieneAlimentos;
    }

    public int getNivelUrgencia() {
        return nivelUrgencia;
    }

    public void setNivelUrgencia(int nivelUrgencia) {
        this.nivelUrgencia = nivelUrgencia;
    }
}
