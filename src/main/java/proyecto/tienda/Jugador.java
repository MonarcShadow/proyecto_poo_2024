package proyecto.tienda;

public class Jugador {
    public static int id_partida;
    public static int cantidad_jugadores=-1;
    private String nombre;
    private String trabajo;
    private int dinero_total;
    private int id_jugador;
    public Jugador(int id_jugador,String nombre, String trabajo, int dinero_total){
        this.id_jugador = id_jugador;
        this.nombre = nombre;
        this.trabajo = trabajo;
        this.dinero_total = dinero_total;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }
    public void setDinero_total(int dinero_total) {
        this.dinero_total = dinero_total;
    }
    public void setId_jugador(int id_jugador) {
        this.id_jugador = id_jugador;
    }
    public String getNombre() {
        return this.nombre;
    }
    public String getTrabajo() {
        return this.trabajo;
    }
    public int getDinero_total() {
        return this.dinero_total;
    }
    public int getId_jugador() {
        return this.id_jugador;
    }

}