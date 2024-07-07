package proyecto.tienda;

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

    public Item(int id,String tienda, String tipo, String subtipo,String nombre, int precio_bajo,int precio_normal, int precio_alto, int custom, int limited, int rural_location, int urban_location, int premium_location, int devote_rural, int devote_urban, int devote_premium){
        this.id = id;
        this.nombre = nombre;
        this.tienda = tienda;
        this.tipo = tipo;
        this.subtipo = subtipo;
        this.precio_normal = precio_normal;
        this.precio_bajo = precio_bajo;
        this.precio_alto = precio_alto;
        this.custom = custom;
        if(limited== 1){
            this.limited = true;
        }
        else{
            this.limited = false;
        }
        if(rural_location== 1){
            this.rural_location = true;
        }
        else{
            this.rural_location = false;
        }
        if(urban_location== 1){
            this.urban_location = true;
        }
        else{
            this.urban_location = false;
        }
        if(premium_location== 1){
            this.premium_location = true;
        }
        else{
            this.premium_location = false;
        }
        if(devote_rural== 1){
            this.devote_rural = true;
        }
        else{
            this.devote_rural = false;
        }
        if(devote_urban== 1){
            this.devote_urban = true;
        }
        else{
            this.devote_urban = false;
        }
        if(devote_premium== 1){
            this.devote_premium = true;
        }
        else{
            this.devote_premium = false;
        }
    }
    public void setID(int id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setTienda(String tienda) {
        this.tienda = tienda;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setSubtipo(String subtipo) {
        this.subtipo = subtipo;
    }
    public void setPrecioNormal(int precio_normal) {
        this.precio_normal = precio_normal;
    }
    public void setPrecioBajo(int precio_bajo) {
        this.precio_bajo = precio_bajo;
    }
    public void setPrecioAlto(int precio_alto) {
        this.precio_alto = precio_alto;
    }
    public void setPrecioCustom(int precio_custom) {
        this.custom = precio_custom;
    }
    public void setLimited(boolean limited) {
        this.limited = limited;
    }
    public void setRuralLocation(boolean rural_location) {
        this.rural_location = rural_location;
    }
    public void setUrbanLocation(boolean urban_location) {
        this.urban_location = urban_location;
    }
    public void setPremiumLocation(boolean premium_location) {
        this.premium_location = premium_location;
    }
    public void setDevoteRural(boolean devote_rural) {
        this.devote_rural = devote_rural;
    }
    public void setDevoteUrban(boolean devote_urban) {
        this.devote_urban = devote_urban;
    }
    public void setDevotePremium(boolean devote_premium) {
        this.devote_premium = devote_premium;
    }

    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getTienda() {
        return tienda;
    }
    public String getTipo() {
        return tipo;
    }
    public String getSubtipo() {
        return subtipo;
    }
    public int getPrecioNormal() {
        return precio_normal;
    }
    public int getPrecioBajo() {
        return precio_bajo;
    }
    public int getPrecioAlto() {
        return precio_alto;
    }
    public int getCustom() {
        return custom;
    }
    public boolean getLimited() {
        return limited;
    }
    public boolean getRuralLocation() {
        return rural_location;
    }
    public boolean getUrbanLocation() {
        return urban_location;
    }
    public boolean getPremiumLocation() {
        return premium_location;
    }
    public boolean getDevoteRural() {
        return devote_rural;
    }
    public boolean getDevoteUrban() {
        return devote_urban;
    }
    public boolean getDevotePremium() {
        return devote_premium;
    }
}