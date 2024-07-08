package proyecto.tienda;

/**
 * Clase que representa a un jugador en el juego.
 */
public class Jugador {
    /**
     * Identificador de la partida del jugador.
     */
    public static int id_partida;

    /**
     * Cantidad total de jugadores en la partida.
     */
    public static int cantidad_jugadores = -1;

    private String nombre;
    private String trabajo;
    private int dinero_total;
    private int id_jugador;

    /**
     * Constructor para inicializar un objeto Jugador.
     *
     * @param id_jugador   identificador único del jugador
     * @param nombre       nombre del jugador
     * @param trabajo      trabajo del jugador
     * @param dinero_total dinero total del jugador
     */
    public Jugador(int id_jugador, String nombre, String trabajo, int dinero_total) {
        this.id_jugador = id_jugador;
        this.nombre = nombre;
        this.trabajo = trabajo;
        this.dinero_total = dinero_total;
    }

    /**
     * Establece el nombre del jugador.
     *
     * @param nombre nombre del jugador
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el trabajo del jugador.
     *
     * @param trabajo trabajo del jugador
     */
    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }

    /**
     * Establece el dinero total del jugador.
     *
     * @param dinero_total dinero total del jugador
     */
    public void setDinero_total(int dinero_total) {
        this.dinero_total = dinero_total;
    }

    /**
     * Establece el identificador único del jugador.
     *
     * @param id_jugador identificador único del jugador
     */
    public void setId_jugador(int id_jugador) {
        this.id_jugador = id_jugador;
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return nombre del jugador
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Obtiene el trabajo del jugador.
     *
     * @return trabajo del jugador
     */
    public String getTrabajo() {
        return this.trabajo;
    }

    /**
     * Obtiene el dinero total del jugador.
     *
     * @return dinero total del jugador
     */
    public int getDinero_total() {
        return this.dinero_total;
    }

    /**
     * Obtiene el identificador único del jugador.
     *
     * @return identificador único del jugador
     */
    public int getId_jugador() {
        return this.id_jugador;
    }
}
