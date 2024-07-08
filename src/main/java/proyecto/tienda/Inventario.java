package proyecto.tienda;

/**
 * Clase que representa un elemento en el inventario de un jugador.
 */
public class Inventario {
    private int id_item;
    private String nombre_item;
    private int cantidad_item;
    private String nombre_jugador;

    /**
     * Constructor para inicializar un objeto Inventario.
     *
     * @param id_item       identificador único del item en el inventario
     * @param nombre_item   nombre del item en el inventario
     * @param cantidad_item cantidad del item en el inventario
     * @param nombre_jugador nombre del jugador dueño del inventario
     */
    public Inventario(int id_item, String nombre_item, int cantidad_item, String nombre_jugador) {
        this.id_item = id_item;
        this.nombre_item = nombre_item;
        this.cantidad_item = cantidad_item;
        this.nombre_jugador = nombre_jugador;
    }

    /**
     * Obtiene el identificador único del item en el inventario.
     *
     * @return identificador único del item
     */
    public int getId_item() {
        return id_item;
    }

    /**
     * Establece el identificador único del item en el inventario.
     *
     * @param id_item identificador único del item
     */
    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    /**
     * Obtiene el nombre del item en el inventario.
     *
     * @return nombre del item
     */
    public String getNombre_item() {
        return nombre_item;
    }

    /**
     * Establece el nombre del item en el inventario.
     *
     * @param nombre_item nombre del item
     */
    public void setNombre_item(String nombre_item) {
        this.nombre_item = nombre_item;
    }

    /**
     * Obtiene la cantidad del item en el inventario.
     *
     * @return cantidad del item
     */
    public int getCantidad_item() {
        return cantidad_item;
    }

    /**
     * Establece la cantidad del item en el inventario.
     *
     * @param cantidad_item cantidad del item
     */
    public void setCantidad_item(int cantidad_item) {
        this.cantidad_item = cantidad_item;
    }

    /**
     * Obtiene el nombre del jugador dueño del inventario.
     *
     * @return nombre del jugador
     */
    public String getNombre_jugador() {
        return nombre_jugador;
    }

    /**
     * Establece el nombre del jugador dueño del inventario.
     *
     * @param nombre_jugador nombre del jugador
     */
    public void setId_jugador(String nombre_jugador) {
        this.nombre_jugador = nombre_jugador;
    }
}
