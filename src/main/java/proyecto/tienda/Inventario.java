package proyecto.tienda;

public class Inventario {
    private int id_item;
    private String nombre_item;
    private int cantidad_item;
    private String nombre_jugador;
    public Inventario(int id_item, String nombre_item, int cantidad_item, String nombre_jugador) {
        this.id_item = id_item;
        this.nombre_item = nombre_item;
        this.cantidad_item = cantidad_item;
        this.nombre_jugador = nombre_jugador;
    }
    public int getId_item() {
        return id_item;
    }
    public void setId_item(int id_item) {
        this.id_item = id_item;
    }
    public String getNombre_item() {
        return nombre_item;
    }
    public void setNombre_item(String nombre_item) {
        this.nombre_item = nombre_item;
    }
    public int getCantidad_item() {
        return cantidad_item;
    }
    public void setCantidad_item(int cantidad_item) {
        this.cantidad_item = cantidad_item;
    }
    public String getNombre_jugador() {
        return nombre_jugador;
    }
    public void setId_jugador(String nombre_jugador) {
        this.nombre_jugador = nombre_jugador;
    }
}
