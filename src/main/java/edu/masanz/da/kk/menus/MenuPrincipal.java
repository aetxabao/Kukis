package edu.masanz.da.kk.menus;

import edu.masanz.da.kk.gui.Gui;
import edu.masanz.da.kk.dao.Dao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MenuPrincipal implements Menu {

    private static Logger logger = LogManager.getLogger();

    @Override
    public void run() {

        logger.info("MenuPrincipal");

        Gui.mostrarTituloPrincipal();
        Gui.mostrarMenuPrincipal();
        int opc = Gui.leerOpcion("Opción", 0, 7);
        while (opc != 0) {
            switch (opc) {
                case 1:
                    obtenerIdSesionUsuario();
                    break;
                case 2:
                    crearKukiSesionUsuario();
                    break;
                case 3:
                    eliminarInfoCaducada();
                    break;
                case 4:
                    mostrarIdsNombresItemsFiltrados();
                    break;
                case 5:
                    meterItemInteresesUsuario();
                    break;
                case 6:
                    mostrarItemsInteresantes();
                    break;
                case 7:
                    mostrarItemsNoInteresantes();
                    break;
                default:
            }
            Gui.mostrarMenuPrincipal();
            opc = Gui.leerOpcion("Opción", 0, 7);
        }
    }

    private void obtenerIdSesionUsuario() {
        String idUsuario = Gui.leerTexto("Id de usuario: ");
        logger.info("1. Obtener id de sesión de un usuario - idUsuario: {}", idUsuario);
        String idSesion = Dao.obtenerIdSesionUsuario(idUsuario);
        Gui.mostrarIdSesion(idSesion);
    }

    private void crearKukiSesionUsuario() {
        String idUsuario = Gui.leerTexto("Id de usuario: ");
        logger.info("2. Crear una kuki de sesión para un usuario - idUsuario: {}", idUsuario);
        String idSesion = Dao.crearKukiSesionUsuario(idUsuario);
        Gui.mostrarIdSesion(idSesion);
    }

    private void eliminarInfoCaducada() {
        int n = Dao.eliminarInfoCaducada();
        logger.info("3. Eliminar kukis y sesiones caducadas o huerfanas - n: {}", n);
        Gui.mostrarEliminadaInfoCaducada(n);
    }

    private void mostrarIdsNombresItemsFiltrados() {
        List<String> lista = Dao.obtenerIdsNombresItems();
        String filtro = Gui.leerTexto("Texto que debe contener (vacío para todos): ");
        logger.info("4. Mostrar el id - nombre de los items ordenados filtrados - filtro: {}", filtro);
        boolean b = Dao.filtrarListadoIdNombres(lista, filtro);
        Gui.mostrarListadoItemsFiltrado(b);
        Gui.mostrarListado(lista);
    }

    private void meterItemInteresesUsuario() {
        String idUsuario = Gui.leerTexto("Id de usuario: ");
        String idItem = Gui.leerTexto("Id de item: ");
        logger.info("5. Introducir un nuevo item en los intereses de un usuario - idUsuario: {}, idItem: {}", idUsuario, idItem);
        boolean b = Dao.meterItemInteresesUsuario(idUsuario, idItem);
        Gui.mostrarItemIntroducido(b);
    }

    private void mostrarItemsInteresantes() {
        List<String> lista = Dao.obtenerItemsInteresantes();
        logger.info("6. Mostrar el nombre de los items interesantes - tamaño: {}", lista.size());
        Gui.mostrarListado(lista);
    }

    private void mostrarItemsNoInteresantes() {
        List<String> lista = Dao.obtenerItemsNoInteresantes();
        logger.info("7. Mostrar el id y nombre de los items que no interesan - tamaño: {}", lista.size());
        Gui.mostrarListado(lista);
    }
}
