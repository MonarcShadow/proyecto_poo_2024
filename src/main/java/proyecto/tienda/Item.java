package proyecto.tienda;

/**
 * Representa un ítem en la tienda con sus características.
 */
public class Item {
    private int id;
    private String nombre;
    private String tienda;
    private String tipo;
    private String subtipo;
    private int precio_normal;
    private int precio_bajo;
    private int precio_alto;
    private int custom;
    private boolean limited;
    private boolean rural_location;
    private boolean urban_location;
    private boolean premium_location;
    private boolean devote_rural;
    private boolean devote_urban;
    private boolean devote_premium;

    /**
     * Constructor para inicializar un objeto Item con sus atributos.
     *
     * @param id identificador del ítem
     * @param tienda nombre de la tienda
     * @param tipo tipo del ítem
     * @param subtipo subtipo del ítem
     * @param nombre nombre del ítem
     * @param precio_bajo precio bajo del ítem
     * @param precio_normal precio normal del ítem
     * @param precio_alto precio alto del ítem
     * @param custom precio personalizado del ítem
     * @param limited indica si el ítem es limitado
     * @param rural_location indica si el ítem está disponible en ubicación rural
     * @param urban_location indica si el ítem está disponible en ubicación urbana
     * @param premium_location indica si el ítem está disponible en ubicación premium
     * @param devote_rural indica si el ítem es devoto a ubicación rural
     * @param devote_urban indica si el ítem es devoto a ubicación urbana
     * @param devote_premium indica si el ítem es devoto a ubicación premium
     */
    public Item(int id, String tienda, String tipo, String subtipo, String nombre, int precio_bajo, int precio_normal, int precio_alto, int custom, int limited, int rural_location, int urban_location, int premium_location, int devote_rural, int devote_urban, int devote_premium) {
        this.id = id;
        this.nombre = nombre;
        this.tienda = tienda;
        this.tipo = tipo;
        this.subtipo = subtipo;
        this.precio_normal = precio_normal;
        this.precio_bajo = precio_bajo;
        this.precio_alto = precio_alto;
        this.custom = custom;
        this.limited = (limited == 1);
        this.rural_location = (rural_location == 1);
        this.urban_location = (urban_location == 1);
        this.premium_location = (premium_location == 1);
        this.devote_rural = (devote_rural == 1);
        this.devote_urban = (devote_urban == 1);
        this.devote_premium = (devote_premium == 1);
    }

    // Getters y Setters para todos los atributos de Item

    /**
     * Establece el identificador del ítem.
     *
     * @param id identificador a establecer
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Establece el nombre del ítem.
     *
     * @param nombre nombre a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece la tienda del ítem.
     *
     * @param tienda tienda a establecer
     */
    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    /**
     * Establece el tipo del ítem.
     *
     * @param tipo tipo a establecer
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Establece el subtipo del ítem.
     *
     * @param subtipo subtipo a establecer
     */
    public void setSubtipo(String subtipo) {
        this.subtipo = subtipo;
    }

    /**
     * Establece el precio normal del ítem.
     *
     * @param precio_normal precio normal a establecer
     */
    public void setPrecioNormal(int precio_normal) {
        this.precio_normal = precio_normal;
    }

    /**
     * Establece el precio bajo del ítem.
     *
     * @param precio_bajo precio bajo a establecer
     */
    public void setPrecioBajo(int precio_bajo) {
        this.precio_bajo = precio_bajo;
    }

    /**
     * Establece el precio alto del ítem.
     *
     * @param precio_alto precio alto a establecer
     */
    public void setPrecioAlto(int precio_alto) {
        this.precio_alto = precio_alto;
    }

    /**
     * Establece el precio personalizado del ítem.
     *
     * @param precio_custom precio personalizado a establecer
     */
    public void setPrecioCustom(int precio_custom) {
        this.custom = precio_custom;
    }

    /**
     * Establece si el ítem es limitado.
     *
     * @param limited true si es limitado, false de lo contrario
     */
    public void setLimited(boolean limited) {
        this.limited = limited;
    }

    /**
     * Establece si el ítem está disponible en ubicación rural.
     *
     * @param rural_location true si está disponible en ubicación rural, false de lo contrario
     */
    public void setRuralLocation(boolean rural_location) {
        this.rural_location = rural_location;
    }

    /**
     * Establece si el ítem está disponible en ubicación urbana.
     *
     * @param urban_location true si está disponible en ubicación urbana, false de lo contrario
     */
    public void setUrbanLocation(boolean urban_location) {
        this.urban_location = urban_location;
    }

    /**
     * Establece si el ítem está disponible en ubicación premium.
     *
     * @param premium_location true si está disponible en ubicación premium, false de lo contrario
     */
    public void setPremiumLocation(boolean premium_location) {
        this.premium_location = premium_location;
    }

    /**
     * Establece si el ítem es devoto a ubicación rural.
     *
     * @param devote_rural true si es devoto a ubicación rural, false de lo contrario
     */
    public void setDevoteRural(boolean devote_rural) {
        this.devote_rural = devote_rural;
    }

    /**
     * Establece si el ítem es devoto a ubicación urbana.
     *
     * @param devote_urban true si es devoto a ubicación urbana, false de lo contrario
     */
    public void setDevoteUrban(boolean devote_urban) {
        this.devote_urban = devote_urban;
    }

    /**
     * Establece si el ítem es devoto a ubicación premium.
     *
     * @param devote_premium true si es devoto a ubicación premium, false de lo contrario
     */
    public void setDevotePremium(boolean devote_premium) {
        this.devote_premium = devote_premium;
    }

    /**
     * Retorna el identificador del ítem.
     *
     * @return identificador del ítem
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna el nombre del ítem.
     *
     * @return nombre del ítem
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna la tienda del ítem.
     *
     * @return tienda del ítem
     */
    public String getTienda() {
        return tienda;
    }

    /**
     * Retorna el tipo del ítem.
     *
     * @return tipo del ítem
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Retorna el subtipo del ítem.
     *
     * @return subtipo del ítem
     */
    public String getSubtipo() {
        return subtipo;
    }

    /**
     * Retorna el precio normal del ítem.
     *
     * @return precio normal del ítem
     */
    public int getPrecioNormal() {
        return precio_normal;
    }

    /**
     * Retorna el precio bajo del ítem.
     *
     * @return precio bajo del ítem
     */
    public int getPrecioBajo() {
        return precio_bajo;
    }

    /**
     * Retorna el precio alto del ítem.
     *
     * @return precio alto del ítem
     */
    public int getPrecioAlto() {
        return precio_alto;
    }

    /**
     * Retorna el precio personalizado del ítem.
     *
     * @return precio personalizado del ítem
     */
    public int getCustom() {
        return custom;
    }

    /**
     * Retorna si el ítem es limitado.
     *
     * @return true si es limitado, false de lo contrario
     */
    public boolean getLimited() {
        return limited;
    }

    /**
     * Retorna si el ítem está disponible en ubicación rural.
     *
     * @return true si está disponible en ubicación rural, false de lo contrario
     */
    public boolean getRuralLocation() {
        return rural_location;
    }

    /**
     * Retorna si el ítem está disponible en ubicación urbana.
     *
     * @return true si está disponible en ubicación urbana, false de lo contrario
     */
    public boolean getUrbanLocation() {
        return urban_location;
    }

    /**
     * Retorna si el ítem está disponible en ubicación premium.
     *
     * @return true si está disponible en ubicación premium, false de lo contrario
     */
    public boolean getPremiumLocation() {
        return premium_location;
    }

    /**
     * Retorna si el ítem es devoto a ubicación rural.
     *
     * @return true si es devoto a ubicación rural, false de lo contrario
     */
    public boolean getDevoteRural() {
        return devote_rural;
    }

    /**
     * Retorna si el ítem es devoto a ubicación urbana.
     *
     * @return true si es devoto a ubicación urbana, false de lo contrario
     */
    public boolean getDevoteUrban() {
        return devote_urban;
    }

    /**
     * Retorna si el ítem es devoto a ubicación premium.
     *
     * @return true si es devoto a ubicación premium, false de lo contrario
     */
    public boolean getDevotePremium() {
        return devote_premium;
    }
}
