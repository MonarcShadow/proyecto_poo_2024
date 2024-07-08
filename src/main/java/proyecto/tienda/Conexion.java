package proyecto.tienda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.ComboBox;
import javax.swing.*;
import java.sql.*;

/**
 * Clase para manejar la conexión a la base de datos y operaciones relacionadas.
 */
public class Conexion {
    private String dB;
    Connection conectar = null;

    /**
     * Método para establecer la conexión a la base de datos.
     *
     * @return La conexión establecida.
     */
    public Connection conectar() {
        try {
            Class.forName("org.sqlite.JDBC");
            if (dB.equals("items.db")) {
                conectar = DriverManager.getConnection("jdbc:sqlite:" + dB);
            } else {
                conectar = DriverManager.getConnection("jdbc:sqlite:" + dB);
            }
            if (conectar != null) {
                System.out.println("Conexión exitosa!");
            }
        } catch (Exception ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.out.println("Error en la conexión");
        }
        return conectar;
    }

    /**
     * Constructor de la clase Conexion.
     *
     * @param DB Nombre de la base de datos a conectar.
     */
    public Conexion(String DB) {
        this.dB = DB;
    }

    /**
     * Método para listar datos de una tabla específica.
     *
     * @param cn_DB           Conexión a la base de datos.
     * @param tabla_a_mostrar Nombre de la tabla a mostrar.
     * @param columna_a_mostrar Columna de la tabla a mostrar.
     * @throws SQLException Si ocurre un error SQL.
     */
    public void listar(Connection cn_DB, String tabla_a_mostrar, String columna_a_mostrar) throws SQLException {
        String query = "SELECT * FROM " + tabla_a_mostrar + ";";
        try {
            Statement st_DB = cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query);
            String lista_usuarios = "";
            while (rs_DB.next()) {
                lista_usuarios = lista_usuarios + rs_DB.getString(columna_a_mostrar) + "\n";
            }
            JOptionPane.showMessageDialog(null, lista_usuarios, "Lista de usuarios registrados",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println(e);
            if (dB.equals("items")) {
                // Lógica específica en caso de errores con una base de datos de items
            } else {
                crear_guardado_usuarios_db(Shop.Path_save_usurious);
            }
        }
    }

    /**
     * Método para listar todos los items disponibles en la tienda.
     *
     * @param cn_DB Conexión a la base de datos.
     * @throws SQLException Si ocurre un error SQL.
     */
    public void listar_items(Connection cn_DB) throws SQLException {
        String query = "Select id_item,(select nombre_clase from clases_item where items.id_clase=clases_item.id_clase) as tienda,"
                + "(select nombre_tipo from tipos_item where tipos_item.id_tipos=items.id_tipo) as tipo_item,"
                + "(Select nombre_subtipo from subtipo_item where items.id_subtipo=subtipo_item.id_subtipo) as subtipo_item,"
                + "nombre_item,costo_bajo,costo_normal,costo_alto,precio_custom,limite_de_stock,locacion_rural,"
                + "locacion_urbana,locacion_premium,devote_rural,devote_urbana,devote_premium from items;";
        try {
            Statement st_DB = cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query);
            while (rs_DB.next()) {
                Item item = new Item(rs_DB.getInt(1), rs_DB.getString(2), rs_DB.getString(3), rs_DB.getString(4),
                        rs_DB.getString(5), rs_DB.getInt(6), rs_DB.getInt(7), rs_DB.getInt(8), rs_DB.getInt(9),
                        rs_DB.getInt(10), rs_DB.getInt(11), rs_DB.getInt(12), rs_DB.getInt(13), rs_DB.getInt(14),
                        rs_DB.getInt(15), rs_DB.getInt(16));
                Shop.lista_items_tienda.add(item);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Método para listar las partidas de un usuario específico.
     *
     * @param cn_DB           Conexión a la base de datos.
     * @param usuario_partida Nombre del usuario para el cual se listarán las partidas.
     * @throws SQLException Si ocurre un error SQL.
     */
    public void listar_partidas_usuario(Connection cn_DB, String usuario_partida) throws SQLException {
        String query = "SELECT nombre_de_partida from nombre_partida inner join nombre_usuario where "
                + "usuario_de_la_partida=id_usuario AND nombre_usuario='" + usuario_partida + "';";
        try {
            Statement st_DB = cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query);
            String lista_partida_del_usuario = "";
            while (rs_DB.next()) {
                lista_partida_del_usuario = lista_partida_del_usuario + rs_DB.getString("nombre_de_partida") + "\n";
            }
            JOptionPane.showMessageDialog(null, lista_partida_del_usuario, "Lista de partidas el usuario",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println(e);
            if (dB.equals("items")) {
                // Lógica específica en caso de errores con una base de datos de items
            } else {
                crear_guardado_usuarios_db(Shop.Path_save_usurious);
            }
        }
    }

    /**
     * Método estático para crear las tablas de la base de datos de usuarios y partidas.
     *
     * @param nombre_db Nombre de la base de datos a crear.
     */
    public static void crear_guardado_usuarios_db(String nombre_db) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection cn = DriverManager.getConnection("jdbc:sqlite:" + nombre_db);
            Statement st = cn.createStatement();
            // Creación de tablas
            st.executeUpdate("CREATE TABLE nombre_usuario ("
                    + "    id_usuario     INTEGER      PRIMARY KEY AUTOINCREMENT"
                    + "                                NOT NULL"
                    + "                                UNIQUE,"
                    + "    nombre_usuario TEXT (1, 16) NOT NULL"
                    + ");");
            st.executeUpdate("CREATE TABLE nombre_partida ("
                    + "    id_partida              INTEGER      PRIMARY KEY AUTOINCREMENT"
                    + "                                         NOT NULL,"
                    + "    nombre_de_partida     TEXT (0, 16) NOT NULL,"
                    + "    usuario_de_la_partida              NOT NULL"
                    + "                                         REFERENCES nombre_usuario (id_usuario)"
                    + ");");
            // Creación de otras tablas y datos iniciales
            st.executeUpdate("CREATE TABLE trabajos ("
                    + "    id_trabajo     INTEGER PRIMARY KEY AUTOINCREMENT"
                    + "                           NOT NULL,"
                    + "    nombre_trabajo TEXT    NOT NULL"
                    + "                           UNIQUE"
                    + ");");
            st.executeUpdate("CREATE TABLE jugadores ("
                    + "    id_jugador     INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "    nombre_jugador      TEXT    NOT NULL"
                    + "                                UNIQUE,"
                    + "    id_trabajo          INTEGER NOT NULL"
                    + "                           DEFAULT ninguno"
                    + "                           REFERENCES trabajos (id_trabajo),"
                    + "    dinero         NUMERIC NOT NULL"
                    + "                           DEFAULT (0),"
                    + "    pertenece_a_partida         REFERENCES nombre_partida (id_partida) \n"
                    + "                                NOT NULL"
                    + ");");
            st.executeUpdate("CREATE TABLE inventario_jugadores ("
                    + "    id_inventario INTEGER PRIMARY KEY AUTOINCREMENT"
                    + "                          NOT NULL"
                    + "                          UNIQUE,"
                    + "    id_item       NUMERIC NOT NULL,"
                    + "    nombre_jugador TEXT   REFERENCES jugadores (nombre_jugador)"
                    + "                          NOT NULL,"
                    + "    cantidad_item NUMERIC NOT NULL"
                    + "                          DEFAULT (0) "
                    + ");");
            // Inserción de datos iniciales
            st.executeUpdate("INSERT INTO trabajos (nombre_trabajo) VALUES ('Barbarian');");
            st.executeUpdate("INSERT INTO trabajos (nombre_trabajo) VALUES ('Bard');");
            st.executeUpdate("INSERT INTO trabajos (nombre_trabajo) VALUES ('Cleric');");
            st.executeUpdate("INSERT INTO trabajos (nombre_trabajo) VALUES ('Druid');");
            st.executeUpdate("INSERT INTO trabajos (nombre_trabajo) VALUES ('Fighter');");
            st.executeUpdate("INSERT INTO trabajos (nombre_trabajo) VALUES ('Monk');");
            st.executeUpdate("INSERT INTO trabajos (nombre_trabajo) VALUES ('Paladin');");
            st.executeUpdate("INSERT INTO trabajos (nombre_trabajo) VALUES ('Ranger');");
            st.executeUpdate("INSERT INTO trabajos (nombre_trabajo) VALUES ('Rogue');");
            st.executeUpdate("INSERT INTO trabajos (nombre_trabajo) VALUES ('Sorcerer');");
            st.executeUpdate("INSERT INTO trabajos (nombre_trabajo) VALUES ('Warlock');");
            st.executeUpdate("INSERT INTO trabajos (nombre_trabajo) VALUES ('Wizard');");
            st.executeUpdate("INSERT INTO trabajos (nombre_trabajo) VALUES ('Blood Hunter');");
            st.executeUpdate("INSERT INTO trabajos (nombre_trabajo) VALUES ('Artificer');");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Método para iniciar la conexión a la base de datos.
     *
     * @return La conexión establecida.
     */
    public Connection abrir() {
        try {
            Class.forName("org.sqlite.JDBC");
            conectar = DriverManager.getConnection("jdbc:sqlite:items.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return conectar;
    }

    /**
     * Método para cerrar la conexión a la base de datos.
     *
     * @return Verdadero si la conexión se cerró correctamente, falso de lo contrario.
     */
    public boolean cerrar() {
        try {
            conectar.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método para establecer una conexión a una base de datos diferente.
     *
     * @param DB Nombre de la base de datos a conectar.
     */
    public void otro(String DB) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    /**
     * Inserta una nueva partida en la base de datos.
     *
     * @param cn_DB conexión a la base de datos
     * @param nombre_partida nombre de la partida a insertar
     * @param nombre_usuario nombre del usuario asociado a la partida
     */
    public void iniciar_nueva_partida(Connection cn_DB, String nombre_partida, String nombre_usuario) {
        try {
            // Prepara la consulta SQL para insertar una nueva partida
            PreparedStatement ps = cn_DB.prepareStatement("insert into nombre_partida (nombre_de_partida, usuario_de_la_partida) values (?, (select id_usuario from nombre_usuario where nombre_usuario=?));");
            ps.setString(1, nombre_partida);     // Establece el nombre de la partida
            ps.setString(2, nombre_usuario);    // Establece el nombre del usuario
            ps.executeUpdate();                 // Ejecuta la actualización en la base de datos
        } catch (Exception e) {
            System.out.println(e);  // Captura y muestra cualquier excepción ocurrida
        }
    }

    /**
     * Continúa una partida existente cargando los jugadores asociados.
     *
     * @param cn_DB conexión a la base de datos
     * @param nombre_usuario nombre del usuario asociado a la partida
     * @param nombre_partida nombre de la partida a cargar
     */
    public void continuar_partida(Connection cn_DB, String nombre_usuario, String nombre_partida) {
        // Construye la consulta SQL para obtener los jugadores de la partida
        String query ="SELECT id_jugador, nombre_jugador, dinero, (SELECT nombre_trabajo from trabajos where trabajos.id_trabajo=jugadores.id_trabajo) as trabajo from jugadores where pertenece_a_partida=(select id_partida from nombre_partida where nombre_de_partida='"+ nombre_partida +"' AND usuario_de_la_partida=(select id_usuario from nombre_usuario where nombre_usuario='"+ nombre_usuario +"'));";
        Jugador.cantidad_jugadores = 0; // Reinicia el contador de jugadores

        try {
            Statement st_DB = cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query); // Ejecuta la consulta SQL
            while (rs_DB.next()) {
                // Crea un objeto Jugador con los datos obtenidos y lo añade a la lista de jugadores de la partida
                Jugador jugador = new Jugador(Integer.parseInt(rs_DB.getString("id_jugador")), rs_DB.getString("nombre_jugador"), rs_DB.getString("trabajo"), Integer.parseInt(rs_DB.getString("dinero")));
                Shop.lista_jugadores_partida.add(jugador); // Añade el jugador a la lista
                Jugador.cantidad_jugadores++; // Incrementa el contador de jugadores
            }
        } catch (Exception e) {
            System.out.println(e);  // Captura y muestra cualquier excepción ocurrida
        }
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param cn_DB conexión a la base de datos
     * @param nombre_usuario nombre del nuevo usuario a guardar
     */
    public void guardar_nuevo_usuario(Connection cn_DB, String nombre_usuario) {
        try {
            // Prepara la consulta SQL para insertar un nuevo usuario
            PreparedStatement ps = cn_DB.prepareStatement("insert into nombre_usuario (nombre_usuario) values (?);");
            ps.setString(1, nombre_usuario); // Establece el nombre del nuevo usuario
            ps.executeUpdate();             // Ejecuta la actualización en la base de datos
        } catch (Exception e) {
            System.out.println(e);  // Captura y muestra cualquier excepción ocurrida
        }
    }

    /**
     * Guarda un nuevo jugador en la base de datos y lo asocia a la partida actual.
     *
     * @param cn_db conexión a la base de datos
     * @param nombre_jugador nombre del nuevo jugador a guardar
     * @param trabajo trabajo del nuevo jugador
     * @param dinero cantidad de dinero del nuevo jugador
     */
    public void guardar_nuevo_jugador(Connection cn_db, String nombre_jugador, String trabajo, int dinero) {
        try {
            // Prepara la consulta SQL para insertar un nuevo jugador asociado a la partida actual
            PreparedStatement ps = cn_db.prepareStatement("insert into jugadores (nombre_jugador, id_trabajo, dinero, pertenece_a_partida) values (?, (select id_trabajo from trabajos where nombre_trabajo=?), ?, (select id_partida from nombre_partida where nombre_de_partida=?));");
            ps.setString(1, nombre_jugador);     // Establece el nombre del jugador
            ps.setString(2, trabajo);           // Establece el trabajo del jugador
            ps.setInt(3, dinero);               // Establece el dinero del jugador
            ps.setString(4, Shop.nombre_partida_actual);  // Establece el nombre de la partida actual desde la clase Shop (asumiendo que existe)
            ps.executeUpdate();                 // Ejecuta la actualización en la base de datos
            agregar_a_lista_al_ultimo_jugador_agregado(cn_db); // Agrega al jugador recién agregado a la lista de jugadores de la partida
        } catch (Exception e) {
            System.out.println(e);  // Captura y muestra cualquier excepción ocurrida
        }
    }

    /**
     * Llena un ComboBox con los datos obtenidos de una columna específica de una tabla en la base de datos.
     *
     * @param cn_DB conexión a la base de datos
     * @param comboBox ComboBox a llenar con los datos
     * @param nombre_tabla nombre de la tabla de donde se obtendrán los datos
     * @param nombre_columna nombre de la columna cuyos datos se añadirán al ComboBox
     */
    public void llenar_combobox(Connection cn_DB, ComboBox comboBox, String nombre_tabla, String nombre_columna) {
        // Construye la consulta SQL para seleccionar los datos de la columna especificada
        String query = "SELECT " + nombre_columna + " FROM " + nombre_tabla + ";";
        try {
            Statement st_DB = cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query); // Ejecuta la consulta SQL
            while (rs_DB.next()) {
                comboBox.getItems().add(rs_DB.getString(1)); // Añade cada resultado al ComboBox
            }
        } catch (Exception e) {
            System.out.println(e);  // Captura y muestra cualquier excepción ocurrida
        }
    }

    /**
     * Agrega al último jugador insertado a la lista de jugadores de la partida actual.
     *
     * @param cn_DB conexión a la base de datos
     */
    public void agregar_a_lista_al_ultimo_jugador_agregado(Connection cn_DB) {
        // Construye la consulta SQL para obtener los datos del último jugador insertado
        String query = "SELECT id_jugador, nombre_jugador, (SELECT nombre_trabajo FROM trabajos WHERE trabajos.id_trabajo = jugadores.id_trabajo) AS trabajo, dinero FROM jugadores ORDER BY id_jugador DESC LIMIT 1";
        try {
            Statement st_DB = cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query); // Ejecuta la consulta SQL
            if (rs_DB.next()) {
                // Crea un objeto Jugador con los datos obtenidos y lo añade a la lista de jugadores de la partida
                Jugador jugador = new Jugador(Integer.parseInt(rs_DB.getString(1)), rs_DB.getString(2), rs_DB.getString(3), Integer.parseInt(rs_DB.getString(4)));
                Shop.lista_jugadores_partida.add(jugador); // Añade el jugador a la lista
                Jugador.cantidad_jugadores++; // Incrementa el contador de jugadores
            }
        } catch (Exception e) {
            System.out.println(e);  // Captura y muestra cualquier excepción ocurrida
        }
    }

    /**
     * Agrega al ComboBox el nombre del último jugador insertado en la base de datos.
     *
     * @param cn_DB conexión a la base de datos
     * @param comboBox ComboBox donde se añadirá el nombre del último jugador
     */
    public void agregar_a_combobox_ultimo_jugador(Connection cn_DB, ComboBox comboBox) {
        // Construye la consulta SQL para obtener el nombre del último jugador insertado
        String query = "SELECT nombre_jugador FROM jugadores ORDER BY id_jugador DESC LIMIT 1";
        try {
            Statement st_DB = cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query); // Ejecuta la consulta SQL
            if (rs_DB.next()) {
                comboBox.getItems().add(rs_DB.getString(1)); // Añade el nombre del jugador al ComboBox
            }
        } catch (Exception e) {
            System.out.println(e);  // Captura y muestra cualquier excepción ocurrida
        }
    }

    /**
     * Realiza una consulta a la base de datos para listar items aplicando filtros opcionales.
     *
     * @param cn_DB conexión a la base de datos
     * @param tienda nombre de la tienda a filtrar (puede ser null o vacío para no filtrar por tienda)
     * @param tipo_item tipo de item a filtrar (puede ser null o vacío para no filtrar por tipo de item)
     * @param sub_tipo_item subtipo de item a filtrar (puede ser null o vacío para no filtrar por subtipo de item)
     * @param lugar tipo de lugar a filtrar (Rural, Urbano, Premium, puede ser null o vacío para no filtrar por lugar)
     * @param fe si el lugar tiene devoción (Si, No, puede ser null o vacío para no filtrar por devoción)
     * @param limite si el item tiene límite de stock (Si, No, puede ser null o vacío para no filtrar por límite de stock)
     * @throws SQLException si ocurre algún error al ejecutar la consulta SQL
     */
    public void listar_items_con_filtro(Connection cn_DB, String tienda, String tipo_item, String sub_tipo_item, String lugar, String fe, String limite) throws SQLException {
        // Variables para las condiciones de filtro
        String condicion_tienda = "";
        String condicion_tipo_item = "";
        String condicion_sub_tipo_item = "";
        String condicion_lugar = "";
        String condicion_fe = "";
        String condicion_limite = "";
        String Where = "";
        String AND1 = "";
        String AND2 = "";
        String AND3 = "";
        String AND4 = "";
        String AND5 = "";

        // Construcción de la cláusula WHERE en función de los parámetros recibidos
        if (tienda == null && tipo_item == null && sub_tipo_item == null && lugar == null && fe == null && limite == null) {
            Where = "";
        } else {
            Where = "where ";
        }

        // Construcción de las condiciones individuales según los parámetros recibidos
        if (!tienda.equals(" ")) {
            condicion_tienda = "tienda='" + tienda + "'";
            if (!tipo_item.equals(" ") || !sub_tipo_item.equals(" ") || !lugar.equals(" ") || !fe.equals(" ") || !limite.equals(" ")) {
                AND1 = " and ";
            }
        }
        if (!tipo_item.equals(" ")) {
            condicion_tipo_item = "tipo_item='" + tipo_item + "'";
            if (!sub_tipo_item.equals(" ") || !lugar.equals(" ") || !fe.equals(" ") || !limite.equals(" ")) {
                AND2 = " and ";
            }
        }
        if (!sub_tipo_item.equals(" ")) {
            condicion_sub_tipo_item = "subtipo_item='" + sub_tipo_item + "'";
            if (!lugar.equals(" ") || !fe.equals(" ") || !limite.equals(" ")) {
                AND3 = " and ";
            }
        }
        if (lugar.equals("Rural")) {
            condicion_lugar = " locacion_rural=1 ";
            if (!fe.equals(" ") || !limite.equals(" ")) {
                AND4 = " and ";
            }
        } else if (lugar.equals("Urbano")) {
            condicion_lugar = " locacion_urbana=1 ";
            if (!fe.equals(" ") || !limite.equals(" ")) {
                AND4 = " and ";
            }
        } else if (lugar.equals("Premium")) {
            condicion_lugar = " locacion_premium=1 ";
            if (!fe.equals(" ") || !limite.equals(" ")) {
                AND4 = " and ";
            }
        }
        if (lugar.equals(" ") && fe.equals("Si")) {
            condicion_fe = " (devote_rural=1 or devote_urbana=1 or devote_premium=1) ";
            if (!limite.equals(" ")) {
                AND5 = " and ";
            }
        } else if (lugar.equals(" ") && fe.equals("No")) {
            condicion_fe = " (devote_rural=0 and devote_urbana=0 and devote_premium=0) ";
            if (!limite.equals(" ")) {
                AND5 = " and ";
            }
        } else if (lugar.equals("Rural") && fe.equals("Si")) {
            condicion_fe = " devote_rural=1 ";
            if (!limite.equals(" ")) {
                AND5 = " and ";
            }
        } else if (lugar.equals("Rural") && fe.equals("No")) {
            condicion_fe = " devote_rural=0 ";
            if (!limite.equals(" ")) {
                AND5 = " and ";
            }
        } else if (lugar.equals("Urbano") && fe.equals("Si")) {
            condicion_fe = " devote_urbana=1 ";
            if (!limite.equals(" ")) {
                AND5 = " and ";
            }
        } else if (lugar.equals("Urbano") && fe.equals("No")) {
            condicion_fe = " devote_urbana=0 ";
            if (!limite.equals(" ")) {
                AND5 = " and ";
            }
        } else if (lugar.equals("Premium") && fe.equals("Si")) {
            condicion_fe = " devote_premium=1 ";
            if (!limite.equals(" ")) {
                AND5 = " and ";
            }
        } else if (lugar.equals("Premium") && fe.equals("No")) {
            condicion_fe = " devote_premium=0 ";
            if (!limite.equals(" ")) {
                AND5 = " and ";
            }
        }
        if (limite.equals("Si")) {
            condicion_limite = " limite_de_stock=1 ";
        } else if (limite.equals("No")) {
            condicion_limite = " limite_de_stock=0 ";
        }

        // Construcción de la condición final para la consulta SQL
        String condicion = Where + condicion_tienda + AND1 + condicion_tipo_item + AND2 + condicion_sub_tipo_item + AND3 + condicion_lugar + AND4 + condicion_fe + AND5 + condicion_limite;

        // Construcción de la consulta SQL final con la condición aplicada
        String query = "SELECT id_item, (SELECT nombre_clase FROM clases_item WHERE items.id_clase=clases_item.id_clase) AS tienda, (SELECT nombre_tipo FROM tipos_item WHERE tipos_item.id_tipos=items.id_tipo) AS tipo_item, (SELECT nombre_subtipo FROM subtipo_item WHERE items.id_subtipo=subtipo_item.id_subtipo) AS subtipo_item, nombre_item, costo_bajo, costo_normal, costo_alto, precio_custom, limite_de_stock, locacion_rural, locacion_urbana, locacion_premium, devote_rural, devote_urbana, devote_premium FROM items " + condicion + ";";

        try {
            Statement st_DB = cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query); // Ejecución de la consulta SQL
            while (rs_DB.next()) {
                // Creación de un objeto Item con los datos obtenidos y lo añade a la lista de items de la tienda
                Item item = new Item(rs_DB.getInt(1), rs_DB.getString(2), rs_DB.getString(3), rs_DB.getString(4), rs_DB.getString(5), rs_DB.getInt(6), rs_DB.getInt(7), rs_DB.getInt(8), rs_DB.getInt(9), rs_DB.getInt(10), rs_DB.getInt(11), rs_DB.getInt(12), rs_DB.getInt(13), rs_DB.getInt(14), rs_DB.getInt(15), rs_DB.getInt(16));
                Shop.lista_items_tienda.add(item);
            }
        } catch (Exception e) {
            System.out.println(e + "\n error en listar_items_tienda"); // Captura y muestra cualquier excepción ocurrida durante la ejecución
        }
    }
    /**
     * Agrega una cantidad específica de dinero al jugador especificado en la base de datos.
     *
     * @param cn_DB conexión a la base de datos
     * @param jugador nombre del jugador al que se le agregará dinero
     * @param dinero cantidad de dinero a agregar
     */
    public void agregar_dinero_a_jugador(Connection cn_DB, String jugador, int dinero) {
        String query = "UPDATE jugadores SET dinero=dinero+" + dinero + " WHERE nombre_jugador='" + jugador + "';";

        try {
            Statement st_DB = cn_DB.createStatement();
            st_DB.executeUpdate(query); // Ejecuta la actualización del dinero del jugador en la base de datos
        } catch (Exception e) {
            System.out.println(e); // Captura y muestra cualquier excepción ocurrida durante la ejecución
        }
    }

    /**
     * Actualiza el precio personalizado de un ítem específico en la base de datos.
     *
     * @param cn_DB conexión a la base de datos
     * @param idItem identificador único del ítem cuyo precio personalizado se actualizará
     * @param nuevoPrecio nuevo precio personalizado a establecer
     */
    public void actualizar_precio_custom(Connection cn_DB, int idItem, int nuevoPrecio) {
        try {
            // Prepara la declaración SQL para actualizar el precio personalizado del ítem
            PreparedStatement ps = cn_DB.prepareStatement("UPDATE items SET precio_custom=? WHERE id_item=?;");
            ps.setInt(1, nuevoPrecio); // Establece el nuevo precio personalizado
            ps.setInt(2, idItem); // Especifica el ID del ítem que se actualizará
            ps.executeUpdate(); // Ejecuta la actualización en la base de datos
        } catch (Exception e) {
            System.out.println(e); // Captura y muestra cualquier excepción ocurrida durante la ejecución
        }
    }

    /**
     * Recupera y lista los ítems del inventario de un jugador específico desde la base de datos.
     *
     * @param cn_DBusuario conexión a la base de datos del usuario
     * @param nombre_jugador nombre del jugador cuyo inventario se desea listar
     */
    public void listar_inventario_jugador(Connection cn_DBusuario, String nombre_jugador) {
        try {
            // Consulta para obtener los ítems y cantidades del inventario del jugador
            String query_usuario = "SELECT id_item, cantidad_item FROM inventario_jugadores WHERE nombre_jugador='" + nombre_jugador + "';";
            Statement st_DBusuario = cn_DBusuario.createStatement();
            ResultSet rs_DBusuario = st_DBusuario.executeQuery(query_usuario);

            while (rs_DBusuario.next()) {
                try {
                    // Consulta para obtener el nombre del ítem basado en su ID
                    String query_item = "SELECT nombre_item FROM items WHERE id_item=" + rs_DBusuario.getInt(1) + ";";
                    Statement st_DBitem = Shop.cn_items.createStatement();
                    ResultSet rs_DBitem = st_DBitem.executeQuery(query_item);
                    rs_DBitem.getString(1); // Se asume que esta línea debería ser rs_DBitem.getString(1); para obtener el nombre del ítem

                    // Crea un objeto Inventario con la información obtenida y lo añade a la lista del inventario del jugador actual
                    Inventario inventario = new Inventario(rs_DBusuario.getInt(1), rs_DBitem.getString(1), rs_DBusuario.getInt(2), nombre_jugador);
                    Shop.lista_items_inventario_jugador_actual.add(inventario);
                } catch (Exception e) {
                    System.out.println(e); // Captura y muestra cualquier excepción ocurrida al recuperar información del ítem
                }
            }
            cn_DBusuario.close(); // Cierra la conexión a la base de datos del usuario al finalizar
        } catch (Exception e) {
            System.out.println(e); // Captura y muestra cualquier excepción ocurrida durante el proceso de listar el inventario del jugador
        }
    }

    /**
     * Permite a un jugador comprar un cierto número de un ítem específico.
     * Actualiza el inventario del jugador y su dinero según el costo del ítem.
     *
     * @param cn_DB conexión a la base de datos principal
     * @param nombre_jugador nombre del jugador que realiza la compra
     * @param idItem ID del ítem que se desea comprar
     * @param cantidad cantidad del ítem que se desea comprar (puede ser positiva para comprar o negativa para vender)
     * @param precio_a_usar tipo de precio a usar para calcular el costo total del ítem (Costo Bajo, Costo Normal, Costo Alto, Costo Custom)
     * @throws SQLException si ocurre un error durante la ejecución de las consultas SQL
     */
    public void comprar_item(Connection cn_DB, String nombre_jugador, int idItem, int cantidad, String precio_a_usar) throws SQLException {
        if (nombre_jugador.equals("")) {
            // Muestra un mensaje de error si no se ha seleccionado un jugador
            JOptionPane.showConfirmDialog(null, "No se ha seleccionado un jugador", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }

        int dinero_jugador = 0;
        String query = "SELECT dinero FROM jugadores WHERE nombre_jugador = ?";

        try (PreparedStatement pst = cn_DB.prepareStatement(query)) {
            pst.setString(1, nombre_jugador);
            try (ResultSet rs_DB = pst.executeQuery()) {
                while (rs_DB.next()) {
                    dinero_jugador = rs_DB.getInt("dinero");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }

        int costo_total_item = 0;
        // Determina el tipo de precio a usar para el cálculo del costo total del ítem
        if (precio_a_usar.equals("Costo Bajo")) {
            precio_a_usar = "costo_bajo";
        } else if (precio_a_usar.equals("Costo Normal")) {
            precio_a_usar = "costo_normal";
        } else if (precio_a_usar.equals("Costo Alto")) {
            precio_a_usar = "costo_alto";
        } else if (precio_a_usar.equals("Costo Custom")) {
            precio_a_usar = "precio_custom";
        }

        try {
            // Consulta para obtener el precio del ítem basado en el tipo de precio seleccionado
            query = "SELECT " + precio_a_usar + " FROM items WHERE id_item = " + idItem + ";";
            Statement st_DB = Shop.cn_items.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query);
            while (rs_DB.next()) {
                costo_total_item = rs_DB.getInt(1) * cantidad;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // Verifica si el jugador tiene suficiente dinero para realizar la compra
        if (dinero_jugador - costo_total_item < 0) {
            JOptionPane.showConfirmDialog(null, "El jugador no tiene suficiente dinero para comprar el ítem", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        } else {
            // Realiza la compra o venta del ítem según la cantidad especificada
            if (cantidad > 0) {
                // Consulta para verificar si el ítem ya existe en el inventario del jugador
                query = "SELECT id_item FROM inventario_jugadores WHERE nombre_jugador = ? AND id_item = ?";

                try (PreparedStatement pst = cn_DB.prepareStatement(query)) {
                    pst.setString(1, nombre_jugador);
                    pst.setInt(2, idItem);

                    try (ResultSet rs_DB = pst.executeQuery()) {
                        if (rs_DB.next()) {
                            // El ítem ya existe en el inventario del jugador, actualiza la cantidad
                            try (PreparedStatement ps = cn_DB.prepareStatement(
                                    "UPDATE inventario_jugadores SET cantidad_item = cantidad_item + ? WHERE nombre_jugador = ? AND id_item = ?")) {
                                ps.setInt(1, cantidad);
                                ps.setString(2, nombre_jugador);
                                ps.setInt(3, idItem);
                                ps.executeUpdate();
                                // Actualiza el dinero del jugador después de la compra
                                agregar_dinero_a_jugador(cn_DB, nombre_jugador, -costo_total_item);
                            } catch (SQLException e) {
                                System.out.println("Error al actualizar el inventario: " + e.getMessage());
                            }
                        } else {
                            // El ítem no existe en el inventario del jugador, inserta un nuevo registro
                            try (PreparedStatement ps = cn_DB.prepareStatement(
                                    "INSERT INTO inventario_jugadores (nombre_jugador, id_item, cantidad_item) VALUES (?, ?, ?)")) {
                                ps.setString(1, nombre_jugador);
                                ps.setInt(2, idItem);
                                ps.setInt(3, cantidad);
                                ps.executeUpdate();
                                // Actualiza el dinero del jugador después de la compra
                                agregar_dinero_a_jugador(cn_DB, nombre_jugador, -costo_total_item);
                            } catch (SQLException e) {
                                System.out.println("Error al insertar en el inventario: " + e.getMessage());
                            }
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Error al ejecutar la consulta de verificación: " + e.getMessage());
                }
            } else if (cantidad < 0) {
                // Si la cantidad es negativa, se considera como una venta del ítem
                int cantidad_inventario = 0;
                query = "SELECT cantidad_item FROM inventario_jugadores WHERE id_item = ? ";

                try (PreparedStatement pst = cn_DB.prepareStatement(query)) {
                    pst.setInt(1, idItem);
                    try (ResultSet rs_DB = pst.executeQuery()) {
                        while (rs_DB.next()) {
                            cantidad_inventario = rs_DB.getInt(1);
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Error al ejecutar la consulta: " + e.getMessage());
                }

                // Verifica si el jugador tiene suficientes ítems para realizar la venta
                if ((cantidad + cantidad_inventario) < 0) {
                    JOptionPane.showConfirmDialog(null, "El jugador no tiene suficientes ítems para vender", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                } else if ((cantidad + cantidad_inventario > 0)) {
                    // Consulta para verificar si el ítem ya existe en el inventario del jugador
                    query = "SELECT id_item FROM inventario_jugadores WHERE nombre_jugador = ? AND id_item = ?";

                    try (PreparedStatement pst = cn_DB.prepareStatement(query)) {
                        pst.setString(1, nombre_jugador);
                        pst.setInt(2, idItem);

                        try (ResultSet rs_DB = pst.executeQuery()) {
                            if (rs_DB.next()) {
                                // El ítem ya existe en el inventario del jugador, actualiza la cantidad
                                try (PreparedStatement ps = cn_DB.prepareStatement(
                                        "UPDATE inventario_jugadores SET cantidad_item = cantidad_item + ? WHERE nombre_jugador = ? AND id_item = ?")) {
                                    ps.setInt(1, cantidad);
                                    ps.setString(2, nombre_jugador);
                                    ps.setInt(3, idItem);
                                    ps.executeUpdate();
                                    // Actualiza el dinero del jugador después de la venta
                                    agregar_dinero_a_jugador(cn_DB, nombre_jugador, -costo_total_item);
                                } catch (SQLException e) {
                                    System.out.println("Error al actualizar el inventario: " + e.getMessage());
                                }
                            } else {
                                // El ítem no existe en el inventario del jugador, inserta un nuevo registro
                                try (PreparedStatement ps = cn_DB.prepareStatement(
                                        "INSERT INTO inventario_jugadores (nombre_jugador, id_item, cantidad_item) VALUES (?, ?, ?)")) {
                                    ps.setString(1, nombre_jugador);
                                    ps.setInt(2, idItem);
                                    ps.setInt(3, cantidad);
                                    ps.executeUpdate();
                                    // Actualiza el dinero del jugador después de la venta
                                    agregar_dinero_a_jugador(cn_DB, nombre_jugador, -costo_total_item);
                                } catch (SQLException e) {
                                    System.out.println("Error al insertar en el inventario: " + e.getMessage());
                                }
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al ejecutar la consulta de verificación: " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Actualiza la cantidad de un ítem en el inventario de un jugador.
     *
     * @param cn_DB conexión a la base de datos principal
     * @param nombre_jugador nombre del jugador cuyo inventario se actualizará
     * @param id_item ID del ítem que se desea actualizar en el inventario
     * @param cantidad cantidad a restar del ítem en el inventario (puede ser positiva o negativa)
     * @throws SQLException si ocurre un error durante la ejecución de las consultas SQL
     */
    public void actualizar_inventario(Connection cn_DB, String nombre_jugador, int id_item, int cantidad) throws SQLException {
        // Prepara la consulta SQL para actualizar la cantidad del ítem en el inventario del jugador
        PreparedStatement ps = cn_DB.prepareStatement("UPDATE inventario_jugadores SET cantidad_item = cantidad_item - ? WHERE nombre_jugador = ? AND id_item = ?");
        ps.setInt(1, cantidad);
        ps.setString(2, nombre_jugador);
        ps.setInt(3, id_item);
        ps.executeUpdate();
    }

}