package proyecto.tienda;

import java.sql.SQLException;

/**
 * La clase {@code app} es la clase principal que permite la ejecución del programa.
 * Esta clase es necesaria para que el programa se pueda ejecutar cuando se compila en un archivo JAR.
 * <p>
 * En el método {@code main}, se llama al método {@code main} de la clase {@code Shop},
 * lo que inicia la aplicación.
 * </p>
 */
public class app {

    /**
     * El método principal que se ejecuta al iniciar el programa.
     *
     * @param args Los argumentos de la línea de comandos.
     * @throws SQLException Si ocurre un error de acceso a la base de datos.
     */
    public static void main(String[] args) throws SQLException {
        Shop.main(args);
    }
}
