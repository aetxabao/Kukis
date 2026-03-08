package edu.masanz.da.kk.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import edu.masanz.da.kk.model.Item;
import edu.masanz.da.kk.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests del Dao")
class DaoTest {

    String idUsuario, nombreUsuario, idSesion;
    String filtro, idItem, ant, sig;
    boolean b, quiereSugerencias;
    Map<String, Object> map;
    List<String> lista;
    List<Item> itemList;
    Item item;

    @BeforeEach
    void setUp() {
        Dao.ini();
    }

    @Test
    @DisplayName("1. Obtener id de sesión de un usuario")
    void obtenerIdSesionUsuario() {
        idUsuario = "aetxabao";
        idSesion = "d519dca360045364abdb0b38f381f57bb1173106";
        assertEquals(idSesion, Dao.obtenerIdSesionUsuario(idUsuario));
        assertNull(Dao.obtenerIdSesionUsuario("noexiste"));
    }

    @Test
    @DisplayName("2. Crear una kuki de sesión para un usuario")
    void crearKukiSesionUsuario() {
        idUsuario = "lozancre";
        nombreUsuario = "Laura";
        idSesion = Dao.crearKukiSesionUsuario(idUsuario);
        assertNotNull(idSesion);
        assertNotNull(Dao.mapaSesiones.get(idSesion));
        assertEquals(idUsuario,Dao.mapaSesiones.get(idSesion).get("idUsuario"));
        assertEquals(nombreUsuario,Dao.mapaSesiones.get(idSesion).get("nombreUsuario"));
        assertEquals(true, Dao.mapaSesiones.get(idSesion).get("quiereSugerencias"));
    }

    @Test
    @DisplayName("3. Eliminar kukis y sesiones caducadas o huerfanas")
    void eliminarInfoCaducada() {
        int n = Dao.eliminarInfoCaducada();
        assertTrue(n > 0);
        // todas las kukis que quedan tienen sesión
        for (String idSesion : Dao.mapaKukis.keySet()) {
            assertNotNull(Dao.mapaSesiones.get(idSesion));
        }
        // todas las sesiones que quedan tienen kuki
        for (String idSesion : Dao.mapaSesiones.keySet()) {
            assertNotNull(Dao.mapaKukis.get(idSesion));
        }
        // todas las sesiones de usuario existen
        for(Usuario usuario : Dao.mapaUsuarios.values()) {
            if (usuario.getIdSesion() != null) {
                assertNotNull(Dao.mapaSesiones.get(usuario.getIdSesion()));
            }
        }
    }

    @Test
    @DisplayName("4.1. Obtener datos de todos los items ordenados")
    void obtenerIdsNombresItems() {
        lista = Dao.obtenerIdsNombresItems();
        ant = " ";
        for (String item : lista) {
            assertTrue(item.contains("-"));
            sig = item;
            assertTrue(ant.compareToIgnoreCase(sig) < 0);
            ant = sig;
        }
    }

    @Test
    @DisplayName("4.2. Mostrar los items ordenados filtrados")
    void filtrarListadoIdNombres() {
        filtro = "GT";
        lista = Dao.obtenerIdsNombresItems();
        b = Dao.filtrarListadoIdNombres(lista, filtro);
        assertTrue(b);
        ant = " ";
        for (String item : lista) {
            assertTrue(item.contains(filtro));
            sig = item;
            assertTrue(ant.compareToIgnoreCase(sig) < 0);
            ant = sig;
        }
    }

    @Test
    @DisplayName("5. Introducir un nuevo item en los intereses de un usuario")
    void meterItemInteresesUsuario() {
        idUsuario = "noexiste";
        idItem = "AMGT3";
        b = Dao.meterItemInteresesUsuario(idUsuario, idItem);
        assertFalse(b);
        idUsuario = "jgarciar";
        idItem = "noexiste";
        b = Dao.meterItemInteresesUsuario(idUsuario, idItem);
        assertFalse(b);
        idUsuario = "jgarciar";
        idItem = "AMGT3";
        item = Dao.mapaItems.get(idItem);
        b = Dao.meterItemInteresesUsuario(idUsuario, idItem);
        assertFalse(b);
        idUsuario = "jgarciar";
        item = Dao.mapaItems.get(idItem);
        b = Dao.meterItemInteresesUsuario(idUsuario, idItem);
        assertFalse(b);
        idUsuario = "aetxabao";
        b = Dao.meterItemInteresesUsuario(idUsuario, idItem);
        assertTrue(b);
        map = Dao.mapaSesiones.get(Dao.mapaUsuarios.get(idUsuario).getIdSesion());
        itemList = (List<Item>) map.get("intereses");
        assertTrue(itemList.contains(item));
        idUsuario = "murizmati";
        b = Dao.meterItemInteresesUsuario(idUsuario, idItem);
        assertTrue(b);
        map = Dao.mapaSesiones.get(Dao.mapaUsuarios.get(idUsuario).getIdSesion());
        itemList = (List<Item>) map.get("intereses");
        assertTrue(itemList.contains(item));
    }

    @Test
    @DisplayName("6. Mostrar el nombre de los items que tienen interés ordenados")
    void obtenerItemsInteresantes() {
        lista = Dao.obtenerItemsInteresantes();
        ant = " ";
        for (String item : lista) {
            sig = item;
            assertTrue(ant.compareToIgnoreCase(sig) < 0);
            ant = sig;
        }
        for (Usuario usuario : Dao.mapaUsuarios.values()) {
            if (usuario.getIdSesion() != null) {
                map = Dao.mapaSesiones.get(usuario.getIdSesion());
                itemList = (List<Item>) map.get("intereses");
                if (itemList != null) {
                    for (Item item : itemList) {
                        assertTrue(lista.contains(item.getNombre()));
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("7. Mostrar el id y nombre de los items que no interesan a nadie")
    void obtenerItemsNoInteresantes() {
        lista = Dao.obtenerItemsNoInteresantes();
        ant = " ";
        for (String item : lista) {
            sig = item;
            assertTrue(ant.compareToIgnoreCase(sig) < 0);
            ant = sig;
        }
        for (Usuario usuario : Dao.mapaUsuarios.values()) {
            if (usuario.getIdSesion() != null) {
                map = Dao.mapaSesiones.get(usuario.getIdSesion());
                itemList = (List<Item>) map.get("intereses");
                if (itemList != null) {
                    for (Item item : itemList) {
                        assertFalse(lista.contains(item.getNombre()));
                    }
                }
            }
        }
    }

}