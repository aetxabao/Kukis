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
        // NO HAY QUE HACER NADA AQUÍ

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
        for (Map.Entry<String, Usuario> entry : mapaUsuarios.entrySet()) {
            if (entry.getKey().equals(idUsuario)) {
                return entry.getValue().getIdSesion();
            }
        }
        return null;
    }

    public static String crearKukiSesionUsuario(String idUsuario) {
        // TODO 2. crearKukiSesionUsuario
        Usuario usuario = mapaUsuarios.get(idUsuario);
        if (usuario == null) {
            return null;
        }
        String idSesion = Security.generateRandomId();
        usuario.setIdSesion(idSesion);
        Kuki kuki = new Kuki(idSesion);
        mapaKukis.put(idSesion, kuki);
        Map<String, Object> mapa = new HashMap<>();
        mapaSesiones.put(idSesion, mapa);
        mapa.put("idUsuario", idUsuario);
        mapa.put("nombreUsuario", usuario.getNombre());
        mapa.put("quiereSugerencias", true);
        mapa.put("intereses", new ArrayList<>());
        return idSesion;
    }

    public static int eliminarInfoCaducada() {
        // TODO 3. eliminarInfoCaducada
        long ahora = System.currentTimeMillis();
        List<String> idsSesionesCaducadas = new ArrayList<>();
        for (Map.Entry<String, Kuki> entrada : mapaKukis.entrySet()) {
            Kuki kuki = entrada.getValue();
            if (ahora > kuki.getFecha() + kuki.getVida()) {
                idsSesionesCaducadas.add(entrada.getKey());
            }
        }
        for (String idSesion : idsSesionesCaducadas) {
            mapaKukis.remove(idSesion);
            mapaSesiones.remove(idSesion);
        }
        for (Usuario usuario : mapaUsuarios.values()) {
            if (usuario.getIdSesion() != null && idsSesionesCaducadas.contains(usuario.getIdSesion())) {
                usuario.setIdSesion(null);
            }
        }
        return idsSesionesCaducadas.size();
    }

    public static List<String> obtenerIdsNombresItems() {
        // TODO 4.1 obtenerIdsNombresItems
        Set<String> idsnombres = new TreeSet<>();
        for (Item item : mapaItems.values()) {
            idsnombres.add(item.getId() + " - " + item.getNombre());
        }
        return new ArrayList<>(idsnombres);
    }

    public static boolean filtrarListadoIdNombres(List<String> listado, String filtro) {
        // TODO 4.2 filtrarListadoIdNombres
        boolean b = false;
        Iterator<String> it = listado.iterator();
        while (it.hasNext()) {
            String idNombre = it.next();
            if (!idNombre.toLowerCase().contains(filtro.toLowerCase())) {
                it.remove();
                b = true;
            }
        }
        return b;
    }

    public static boolean meterItemInteresesUsuario(String idUsuario, String idItem) {
        // TODO 5. meterItemInteresesUsuario
        Usuario usuario = mapaUsuarios.get(idUsuario);
        if (usuario == null) {
            return false;
        }
        Item item = mapaItems.get(idItem);
        if (item == null) {
            return false;
        }
        String idSesion = usuario.getIdSesion();
        if (idSesion == null) {
            return false;
        }
        Map<String, Object> mapa = mapaSesiones.get(idSesion);
        if (mapa == null) {
            return false;
        }
        List<Item> intereses = (List<Item>) mapa.get("intereses");
        if (intereses == null) {
            intereses = new ArrayList<>();
            mapa.put("intereses", intereses);
        }
        if (!intereses.contains(item)) {
            intereses.add(item);
            //return true;
        }
        //return false;
        return true;
    }

    public static List<String> obtenerItemsInteresantes() {
        // TODO 6. obtenerItemsInteresantes
        Set<String> set = new TreeSet<>();
        for (Map<String, Object> value : mapaSesiones.values()) {
            List<Item> itemsIteresantes = (List<Item>) value.get("intereses");
            if (itemsIteresantes != null) {
                for (Item item : itemsIteresantes) {
                    set.add(item.getNombre());
                }
            }
        }
        return new ArrayList<>(set);
    }

    public static List<String> obtenerItemsNoInteresantes() {
        // TODO 7. obtenerItemsNoInteresantes
        Set<String> set = new TreeSet<>(mapaItems.keySet());
        for (Map<String, Object> value : mapaSesiones.values()) {
            List<Item> itemsIteresantes = (List<Item>) value.get("intereses");
            if (itemsIteresantes != null) {
                for (Item item : itemsIteresantes) {
                    set.remove(item.getId());
                }
            }
        }
        List<String> lista = new ArrayList<>();
        for (String s : set) {
            Item item = mapaItems.get(s);
            lista.add(item.getId() + " - " + item.getNombre());
        }
        return lista;
    }

}
