package edu.masanz.da.kk.dao;

import java.util.*;
import edu.masanz.da.kk.model.*;
import edu.masanz.da.kk.utils.Security;

import static edu.masanz.da.kk.conf.Ini.*;

/**
 * Dao es la clase que simula la base de datos. Contiene colecciones que almacenan los datos y métodos para acceder a ellos.
 * En este ejercicio, se pide implementar los métodos de esta clase para que el programa funcione correctamente.
 * @author TODO: 10 AQUÍ PON TU NOMBRE
 */
public class Dao {

    // region Colecciones que simulan la base de datos
    public static Map<String, Usuario> mapaUsuarios = new HashMap<>();;
    public static Map<String, Kuki> mapaKukis = new HashMap<>();
    public static Map<String, Item> mapaItems = new HashMap<>();
    public static Map<String, Map<String, Object>> mapaSesiones = new HashMap<>();;
    // endregion

    // region Inicialización de las colecciones con ini()
    public static void ini() {
        iniMapaUsuarios();
        iniMapaKukis();
        iniMapaItems();
        iniMapaSesiones();
    }

    private static void iniMapaUsuarios() {
        for (String txt : USUARIOS) {
            String[] campos = txt.split(",");
            Usuario usuario = new Usuario(
                    campos[0],
                    campos[1],
                    campos[2],
                    campos[3].equals("null")?null:campos[3]
            );
            mapaUsuarios.put(campos[0], usuario);
        }
    }

    private static void iniMapaKukis() {
        for (String txt : KUKIS) {
            String[] campos = txt.split(",");
            Kuki kuki = new Kuki(
                    campos[0],
                    Long.parseLong(campos[1]),
                    Long.parseLong(campos[2])
            );
            mapaKukis.put(campos[0], kuki);
        }
    }

    private static void iniMapaItems() {
        for (String txt : ITEMS) {
            String[] campos = txt.split(",");
            Item item = new Item(
                    campos[0],
                    campos[1],
                    Integer.parseInt(campos[2])
            );
            mapaItems.put(item.getId(), item);
        }
    }


    private static void iniMapaSesiones() {

        for (Usuario usuario : mapaUsuarios.values()) {
            if (usuario.getIdSesion() != null) {
                Map<String,Object> mapaAtributos = new HashMap<>();
                mapaAtributos.put("idUsuario", usuario.getId());
                mapaAtributos.put("nombreUsuario", usuario.getNombre());
                mapaSesiones.put(usuario.getIdSesion(), mapaAtributos);
            }
        }

        for (String txt : SUGERENCIAS) {
            String[] campos = txt.split(",");
            String idSesion = campos[0];
            boolean quiereSugerencias = Boolean.valueOf(campos[1]);
            Map<String, Object> mapaAtributos = mapaSesiones.get(idSesion);
            if (mapaAtributos == null) {
                mapaAtributos = new HashMap<>();
                mapaSesiones.put(idSesion, mapaAtributos);
            }
            mapaAtributos.put("quiereSugerencias", quiereSugerencias);
        }

        for (String txt : INTERESES) {
            String[] campos = txt.split(",");
            String idSesion = campos[0];
            List<Item> intereses = new ArrayList<>();
            for (int i = 1; i < campos.length; i++) {
                String idItem = campos[i];
                Item item = mapaItems.get(idItem);
                if (item != null) {
                    intereses.add(item);
                }
            }
            Map<String, Object> mapaAtributos = mapaSesiones.get(idSesion);
            mapaAtributos.put("intereses", intereses);
        }
    }
    // endregion


    public static String obtenerIdSesionUsuario(String idUsuario) {
        // TODO 1. obtenerIdSesionUsuario
        return null;
    }

    public static String crearKukiSesionUsuario(String idUsuario) {
        // TODO 2. crearKukiSesionUsuario
        return null;
    }

    public static int eliminarInfoCaducada() {
        // TODO 3. eliminarInfoCaducada
        return 0;
    }

    public static List<String> obtenerIdsNombresItems() {
        // TODO 4.1 obtenerIdsNombresItems
        List<String> lista = new ArrayList<>();
        return lista;
    }

    public static boolean filtrarListadoIdNombres(List<String> listado, String filtro) {
        // TODO 4.2 filtrarListadoIdNombres
        return true;
    }

    public static boolean meterItemInteresesUsuario(String idUsuario, String idItem) {
        // TODO 5. meterItemInteresesUsuario
        return true;
    }

    public static List<String> obtenerItemsInteresantes() {
        // TODO 6. obtenerItemsInteresantes
        List<String> lista = new ArrayList<>();
        return lista;
    }

    public static List<String> obtenerItemsNoInteresantes() {
        // TODO 7. obtenerItemsNoInteresantes
        List<String> lista = new ArrayList<>();
        return lista;
    }

}
