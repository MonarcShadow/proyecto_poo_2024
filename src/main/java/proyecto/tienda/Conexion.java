package proyecto.tienda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.ComboBox;
import javax.swing.*;
import java.sql.*;

public class Conexion {
    private String dB;
    Connection conectar= null;
    public Connection conectar(){
        try{//conectar a la base de datos externa
            Class.forName("org.sqlite.JDBC");
            if (dB.equals("items.db")) {
                //conectar = DriverManager.getConnection("jdbc:sqlite::resource:proyecto/tienda/datos/items.db");
                conectar = DriverManager.getConnection("jdbc:sqlite:" + dB);
            }
            else {
                conectar = DriverManager.getConnection("jdbc:sqlite:" + dB);
            }
            if(conectar !=null){
                System.out.println("Conexión exitosa!");
            }
        }//para conectar con una base de datos externa para guardar los personajes e inventario de la partida
        catch ( Exception ex ) {
            System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
            System.out.println("Error en la conexión");
        }
        return conectar;
    }
    public Conexion(String DB){
        this.dB=DB;
    }
    public void listar(Connection cn_DB,String tabla_a_mostrar,String columna_a_mostrar) throws SQLException {
        String query = "SELECT * FROM " + tabla_a_mostrar + ";";
        try {
            Statement st_DB =cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query);
            String lista_usuarios = "";
            while (rs_DB.next()) {
                lista_usuarios = lista_usuarios + rs_DB.getString(columna_a_mostrar) + "\n";
            }
            JOptionPane.showMessageDialog(null, lista_usuarios , "Lista de usuarios registrados", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println(e);
            if (dB.equals("items")) {
            }
            else {
                crear_guardado_usuarios_db(Shop.Path_save_usurious);
            }
        }
    }
    public void listar_items(Connection cn_DB) throws SQLException {
        String query = "Select id_item,(select nombre_clase from clases_item where items.id_clase=clases_item.id_clase) as tienda,(select nombre_tipo from tipos_item where tipos_item.id_tipos=items.id_tipo) as tipo_item,(Select nombre_subtipo from subtipo_item where items.id_subtipo=subtipo_item.id_subtipo) as subtipo_item,nombre_item,costo_bajo,costo_normal,costo_alto,precio_custom,limite_de_stock,locacion_rural,locacion_urbana,locacion_premium,devote_rural,devote_urbana,devote_premium from items;";
        try {
            Statement st_DB = cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query);
            while (rs_DB.next()) {
                Item item =new Item(rs_DB.getInt(1),rs_DB.getString(2),rs_DB.getString(3),rs_DB.getString(4),rs_DB.getString(5),rs_DB.getInt(6),rs_DB.getInt(7),rs_DB.getInt(8),rs_DB.getInt(9),rs_DB.getInt(10),rs_DB.getInt(11),rs_DB.getInt(12),rs_DB.getInt(13),rs_DB.getInt(14),rs_DB.getInt(15),rs_DB.getInt(16));
                Shop.lista_items_tienda.add(item);
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    };
    public void listar_partidas_usuario(Connection cn_DB, String usuario_partida) throws SQLException {
        String query ="SELECT nombre_de_partida from nombre_partida inner join nombre_usuario where usuario_de_la_partida=id_usuario AND nombre_usuario='"+ usuario_partida + "';" ;
        try {
            Statement st_DB = cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query);
            String lista_partida_del_usuario = "";
            while (rs_DB.next()) {
                lista_partida_del_usuario = lista_partida_del_usuario + rs_DB.getString("nombre_de_partida") + "\n";
            }
            JOptionPane.showMessageDialog(null, lista_partida_del_usuario , "Lista de partidas el usuario", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println(e);
            if (dB.equals("items")) {
            }
            else {
                crear_guardado_usuarios_db(Shop.Path_save_usurious);
            }
        }
    }
    public static void crear_guardado_usuarios_db(String nombre_db) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection cn = DriverManager.getConnection("jdbc:sqlite:"+nombre_db);
            Statement st = cn.createStatement();
            st.executeUpdate("CREATE TABLE nombre_usuario (" +
                    "    id_usuario     INTEGER      PRIMARY KEY AUTOINCREMENT" +
                    "                                NOT NULL" +
                    "                                UNIQUE," +
                    "    nombre_usuario TEXT (1, 16) NOT NULL" +
                    ");");
            st.executeUpdate("CREATE TABLE nombre_partida (\n" +
                    "    id_partida              INTEGER      PRIMARY KEY AUTOINCREMENT" +
                    "                                         NOT NULL," +
                    "    nombre_de_partida     TEXT (0, 16) NOT NULL," +
                    "    usuario_de_la_partida              NOT NULL" +
                    "                                         REFERENCES nombre_usuario (id_usuario)" +
                    ");");
            st.executeUpdate("CREATE TABLE trabajos (" +
                    "    id_trabajo     INTEGER PRIMARY KEY AUTOINCREMENT" +
                    "                           NOT NULL," +
                    "    nombre_trabajo TEXT    NOT NULL" +
                    "                           UNIQUE" +
                    ");");
            st.executeUpdate("CREATE TABLE jugadores (" +
                    "    id_jugador     INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "    nombre_jugador      TEXT    NOT NULL" +
                    "                                UNIQUE," +
                    "    id_trabajo          INTEGER NOT NULL" +
                    "                           DEFAULT ninguno" +
                    "                           REFERENCES trabajos (id_trabajo)," +
                    "    dinero         NUMERIC NOT NULL" +
                    "                           DEFAULT (0)," +
                    "    pertenece_a_partida         REFERENCES nombre_partida (id_partida) \n" +
                    "                                NOT NULL" +
                    ");");
            st.executeUpdate("CREATE TABLE inventario_jugadores (" +
                    "    id_inventario INTEGER PRIMARY KEY AUTOINCREMENT" +
                    "                          NOT NULL" +
                    "                          UNIQUE," +
                    "    id_item       NUMERIC NOT NULL," +
                    "    nombre_jugador TEXT   REFERENCES jugadores (nombre_jugador)" +
                    "                          NOT NULL," +
                    "    cantidad_item NUMERIC NOT NULL" +
                    "                          DEFAULT (0) " +
                    ");");
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
    public void iniciar_nueva_partida(Connection cn_DB, String nombre_partida, String nombre_usuario) {
        try {
            PreparedStatement ps = cn_DB.prepareStatement("insert into nombre_partida (nombre_de_partida,usuario_de_la_partida) values (?,(select id_usuario from nombre_usuario where nombre_usuario=?));");
            ps.setString(1, nombre_partida);
            ps.setString(2, nombre_usuario);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void continuar_partida(Connection cn_DB,String nombre_usuario,String nombre_partida) {
        String query ="SELECT id_jugador, nombre_jugador, dinero,(SELECT nombre_trabajo from trabajos where trabajos.id_trabajo=jugadores.id_trabajo) as trabajo from jugadores where pertenece_a_partida=(select id_partida from nombre_partida where nombre_de_partida='"+ nombre_partida +"'AND usuario_de_la_partida=(select id_usuario from nombre_usuario where nombre_usuario='"+ nombre_usuario +"'));";
        Jugador.cantidad_jugadores=0;
        try {
            Statement st_DB = cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query);
            while (rs_DB.next()) {
                Jugador jugador = new Jugador(Integer.parseInt(rs_DB.getString("id_jugador")),rs_DB.getString("nombre_jugador"),rs_DB.getString("trabajo"),Integer.parseInt(rs_DB.getString("dinero")));
                Shop.lista_jugadores_partida.add(jugador);
                Jugador.cantidad_jugadores++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void guardar_nuevo_usuario(Connection cn_DB, String text) {
        try {
            PreparedStatement ps = cn_DB.prepareStatement("insert into nombre_usuario (nombre_usuario) values (?);");
            ps.setString(1, text);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void guardar_nuevo_jugador(Connection cn_db,String nombre_jugador, String trabajo,int dinero){
        try {
            PreparedStatement ps = cn_db.prepareStatement("insert into jugadores (nombre_jugador,id_trabajo,dinero,pertenece_a_partida) values (?,(select id_trabajo from trabajos where nombre_trabajo=?),?,(select id_partida from nombre_partida where nombre_de_partida=?));");
            ps.setString(1, nombre_jugador);
            ps.setString(2, trabajo);
            ps.setInt(3, dinero);
            ps.setString(4, Shop.nombre_partida_actual);
            ps.executeUpdate();
            agregar_a_lista_al_ultimo_jugador_agregado(cn_db);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void llenar_combobox(Connection cn_DB, ComboBox comboBox, String nombre_tabla, String nombre_columna) {
        String query ="SELECT "+ nombre_columna +" from "+ nombre_tabla + ";";
        try {
            Statement st_DB = cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query);
            while (rs_DB.next()) {
                comboBox.getItems().add(rs_DB.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void agregar_a_lista_al_ultimo_jugador_agregado(Connection cn_DB){
        String query = "SELECT id_jugador,nombre_jugador,(SELECT nombre_trabajo from trabajos where trabajos.id_trabajo=jugadores.id_trabajo) as trabajo,dinero from jugadores order by id_jugador desc limit 1";
        try {
            Statement st_DB = cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query);
            Jugador jugador = new Jugador(Integer.parseInt(rs_DB.getString(1)),rs_DB.getString(2),rs_DB.getString(3),Integer.parseInt(rs_DB.getString(4)));
            Shop.lista_jugadores_partida.add(jugador);
            Jugador.cantidad_jugadores++;
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void agregar_a_combobox_ultimo_jugador(Connection cn_DB, ComboBox comboBox){
        String query = "SELECT nombre_jugador from jugadores order by id_jugador desc limit 1";
        try {
            Statement st_DB = cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query);
            comboBox.getItems().add(rs_DB.getString(1));
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void listar_items_con_filtro(Connection cn_DB, String tienda, String tipo_item, String sub_tipo_item, String lugar, String fe, String limite) throws SQLException {
        //para el filtrado falta hacer las combinaciones posibles del where en el que cada uno de los filtros este en nulo o en algun estado y estaria listo el filtro de items
        String condicion_tienda="";
        String condicion_tipo_item="";
        String condicion_sub_tipo_item="";
        String condicion_lugar="";
        String condicion_fe="";
        String condicion_limite="";
        String Where="";
        String AND1="";
        String AND2="";
        String AND3="";
        String AND4="";
        String AND5="";
        if(tienda==null && tipo_item==null && sub_tipo_item==null && lugar==null && fe==null && limite==null){
            Where="";
        }else {
            Where="where ";
        }
        if(!tienda.equals(" ")){
            condicion_tienda="tienda='"+tienda+"'";
            if(!tipo_item.equals(" ") || !sub_tipo_item.equals(" ") || !lugar.equals(" ") || !fe.equals(" ") || !limite.equals(" ")){
                AND1=" and ";
            }
        }
        if(!tipo_item.equals(" ")){
            condicion_tipo_item="tipo_item='"+tipo_item+"'";
            if(!sub_tipo_item.equals(" ") || !lugar.equals(" ") || !fe.equals(" ") || !limite.equals(" ")){
                AND2=" and ";
            }
        }
        if(!sub_tipo_item.equals(" ")){
            condicion_sub_tipo_item="subtipo_item='"+sub_tipo_item+"'";
            if(!lugar.equals(" ") || !fe.equals(" ") || !limite.equals(" ")){
                AND3=" and ";
            }
        }
        if(lugar.equals("Rural")){
            condicion_lugar=" locacion_rural=1 ";
            if(!fe.equals(" ") || !limite.equals(" ")){
                AND4=" and ";
            }
        } else if (lugar.equals("Urbano")) {
            condicion_lugar=" locacion_urbana=1 ";
            if(!fe.equals(" ") || !limite.equals(" ")){
                AND4=" and ";
            }
        } else if (lugar.equals("Premium")) {
            condicion_lugar=" locacion_premium=1 ";
            if(!fe.equals(" ") || !limite.equals(" ")){
                AND4=" and ";
            }
        }
        if(lugar.equals(" ") && fe.equals("Si")) {
            condicion_fe = " (devote_rural=1 or devote_urbana=1 or devote_premium=1) ";
            if (!limite.equals(" ")) {
                AND5 = " and ";
            }
        }else if(lugar.equals(" ") && fe.equals("No")){
            condicion_fe=" (devote_rural=0 and devote_urbana=0 and devote_premium=0) ";
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
        if(limite.equals("Si")){
            condicion_limite=" limite_de_stock=1 ";
        }else if(limite.equals("No")){
            condicion_limite=" limite_de_stock=0 ";
        }
        String condicion = Where+condicion_tienda+AND1+condicion_tipo_item+AND2+condicion_sub_tipo_item+AND3+condicion_lugar+AND4+condicion_fe+AND5+condicion_limite;


        String query = "Select id_item,(select nombre_clase from clases_item where items.id_clase=clases_item.id_clase) as tienda,(select nombre_tipo from tipos_item where tipos_item.id_tipos=items.id_tipo) as tipo_item,(Select nombre_subtipo from subtipo_item where items.id_subtipo=subtipo_item.id_subtipo) as subtipo_item,nombre_item,costo_bajo,costo_normal,costo_alto,precio_custom,limite_de_stock,locacion_rural,locacion_urbana,locacion_premium,devote_rural,devote_urbana,devote_premium from items "+condicion+";";
        try {
            Statement st_DB = cn_DB.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query);
            while (rs_DB.next()) {
                Item item =new Item(rs_DB.getInt(1),rs_DB.getString(2),rs_DB.getString(3),rs_DB.getString(4),rs_DB.getString(5),rs_DB.getInt(6),rs_DB.getInt(7),rs_DB.getInt(8),rs_DB.getInt(9),rs_DB.getInt(10),rs_DB.getInt(11),rs_DB.getInt(12),rs_DB.getInt(13),rs_DB.getInt(14),rs_DB.getInt(15),rs_DB.getInt(16));
                Shop.lista_items_tienda.add(item);
            }
        }catch (Exception e) {
            System.out.println(e+"\n error en listar_items_tienda");
        }
    };
    public void agregar_dinero_a_jugador(Connection cn_DB, String jugador, int dinero) {
        String query = "update jugadores set dinero=dinero+"+dinero+" where nombre_jugador='"+jugador+"';";
        try {
            Statement st_DB = cn_DB.createStatement();
            st_DB.executeUpdate(query);
        }catch (Exception e) {
            System.out.println(e);
        }

    }
    public void actualizar_precio_custom(Connection cn_DB,int idItem, int nuevoPrecio) {
        try {
            PreparedStatement ps = cn_DB.prepareStatement("update items set precio_custom=? where id_item=?;");
            ps.setInt(1, nuevoPrecio);
            ps.setInt(2, idItem);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void listar_inventario_jugador(Connection cn_DBusuario, String nombre_jugador) {
        try{
            String query_usuario = "select id_item,cantidad_item from inventario_jugadores where nombre_jugador='"+nombre_jugador+"';";
            Statement st_DBusuario = cn_DBusuario.createStatement();
            ResultSet rs_DBusuario = st_DBusuario.executeQuery(query_usuario);
            while (rs_DBusuario.next()) {
                try {
                    String query_item = "select nombre_item from items where id_item=" + rs_DBusuario.getInt(1) + ";";
                    Statement st_DBitem = Shop.cn_items.createStatement();
                    ResultSet rs_DBitem = st_DBitem.executeQuery(query_item);
                    rs_DBitem.getString(1);
                    Inventario inventario = new Inventario(rs_DBusuario.getInt(1), rs_DBitem.getString(1), rs_DBusuario.getInt(2),nombre_jugador);
                    Shop.lista_items_inventario_jugador_actual.add(inventario);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            cn_DBusuario.close();
        }catch (Exception e) {
            System.out.println(e);
        }
    }
    public void comprar_item(Connection cn_DB, String nombre_jugador, int idItem, int cantidad,String precio_a_usar) throws SQLException {
        if (nombre_jugador.equals("")) {
            //System.out.println("se entro a la condicion sin jugador");
            JOptionPane.showConfirmDialog(null, "No se ha seleccionado un jugador", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        int dinero_jugador = 0;
        String query = "SELECT dinero FROM jugadores WHERE nombre_jugador = ?";

        try (PreparedStatement pst = cn_DB.prepareStatement(query)) {
            pst.setString(1, nombre_jugador);
            try (ResultSet rs_DB = pst.executeQuery()) {
                while (rs_DB.next()) {
                    dinero_jugador = rs_DB.getInt("dinero");
                    //System.out.println("\n"+dinero_jugador);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
        int costo_total_item = 0;
        if (precio_a_usar.equals("Costo Bajo")) {
            precio_a_usar = "costo_bajo";
        }else if (precio_a_usar.equals("Costo Normal")) {
            precio_a_usar = "costo_normal";
        }else if (precio_a_usar.equals("Costo Alto")) {
            precio_a_usar = "costo_alto";
        }else if (precio_a_usar.equals("Costo Custom")) {
            precio_a_usar = "precio_custom";
        }
        //System.out.println("\n"+precio_a_usar+" es el precio a usar");

        try {
            query = "select " + precio_a_usar + " from items where id_item=" + idItem + ";";
            Statement st_DB = Shop.cn_items.createStatement();
            ResultSet rs_DB = st_DB.executeQuery(query);
            while (rs_DB.next()) {
                costo_total_item = rs_DB.getInt(1) * cantidad;
                //System.out.println("\nmonto total: "+costo_total_item);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        if(dinero_jugador-costo_total_item<0){
            JOptionPane.showConfirmDialog(null, "El jugador no tiene suficiente dinero para comprar el item", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }else{
            if(cantidad>0){

                query = "SELECT id_item FROM inventario_jugadores WHERE nombre_jugador = ? AND id_item = ?";

                try (PreparedStatement pst = cn_DB.prepareStatement(query)) {
                    pst.setString(1, nombre_jugador);
                    pst.setInt(2, idItem);

                    try (ResultSet rs_DB = pst.executeQuery()) {
                        if (rs_DB.next()) {
                            // El item ya existe en el inventario del jugador, actualiza la cantidad
                            try (PreparedStatement ps = cn_DB.prepareStatement(
                                    "UPDATE inventario_jugadores SET cantidad_item = cantidad_item + ? WHERE nombre_jugador = ? AND id_item = ?")) {
                                ps.setInt(1, cantidad);
                                ps.setString(2, nombre_jugador);
                                ps.setInt(3, idItem);
                                ps.executeUpdate();
                                agregar_dinero_a_jugador(cn_DB, nombre_jugador, -costo_total_item);
                                // System.out.println("\n"+"-"+costo_total_item+"");
                            } catch (SQLException e) {
                                System.out.println("Error al actualizar el inventario: " + e.getMessage());
                            }
                        } else {
                            // El item no existe en el inventario del jugador, inserta un nuevo registro
                            try (PreparedStatement ps = cn_DB.prepareStatement(
                                    "INSERT INTO inventario_jugadores (nombre_jugador, id_item, cantidad_item) VALUES (?, ?, ?)")) {
                                ps.setString(1, nombre_jugador);
                                ps.setInt(2, idItem);
                                ps.setInt(3, cantidad);
                                ps.executeUpdate();
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
            else if(cantidad<0){
                int cantidad_inventario = 0;
                    query="SELECT cantidad_item from inventario_jugadores where id_item= ? ";
                    //String query = "SELECT dinero FROM jugadores WHERE nombre_jugador = ?";

                    try (PreparedStatement pst = cn_DB.prepareStatement(query)) {
                        pst.setInt(1, idItem);
                        try (ResultSet rs_DB = pst.executeQuery()) {
                            while (rs_DB.next()) {
                                cantidad_inventario = rs_DB.getInt(1);
                                //System.out.println("\n"+dinero_jugador);
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al ejecutar la consulta: " + e.getMessage());
                    }
                if((cantidad+cantidad_inventario)<0) {
                    JOptionPane.showConfirmDialog(null, "El jugador no tiene suficiente articulos para vender", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
                else if((cantidad+cantidad_inventario>0)){
                    query = "SELECT id_item FROM inventario_jugadores WHERE nombre_jugador = ? AND id_item = ?";

                    try (PreparedStatement pst = cn_DB.prepareStatement(query)) {
                        pst.setString(1, nombre_jugador);
                        pst.setInt(2, idItem);

                        try (ResultSet rs_DB = pst.executeQuery()) {
                            if (rs_DB.next()) {
                                // El item ya existe en el inventario del jugador, actualiza la cantidad
                                try (PreparedStatement ps = cn_DB.prepareStatement(
                                        "UPDATE inventario_jugadores SET cantidad_item = cantidad_item + ? WHERE nombre_jugador = ? AND id_item = ?")) {
                                    ps.setInt(1, cantidad);
                                    ps.setString(2, nombre_jugador);
                                    ps.setInt(3, idItem);
                                    ps.executeUpdate();
                                    agregar_dinero_a_jugador(cn_DB, nombre_jugador, -costo_total_item);
                                    // System.out.println("\n"+"-"+costo_total_item+"");
                                } catch (SQLException e) {
                                    System.out.println("Error al actualizar el inventario: " + e.getMessage());
                                }
                            } else {
                                // El item no existe en el inventario del jugador, inserta un nuevo registro
                                try (PreparedStatement ps = cn_DB.prepareStatement(
                                        "INSERT INTO inventario_jugadores (nombre_jugador, id_item, cantidad_item) VALUES (?, ?, ?)")) {
                                    ps.setString(1, nombre_jugador);
                                    ps.setInt(2, idItem);
                                    ps.setInt(3, cantidad);
                                    ps.executeUpdate();
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
    public void actualizar_inventario(Connection cn_DB, String nombre_jugador, int id_item, int cantidad) throws SQLException {
        PreparedStatement ps = cn_DB.prepareStatement("update inventario_jugadores set cantidad_item=cantidad_item-"+cantidad+" where nombre_jugador='"+nombre_jugador+"' and id_item="+id_item+";");
        ps.executeUpdate();
    }
}