package edu.masanz.da.kk;

import edu.masanz.da.kk.menus.*;
import edu.masanz.da.kk.dao.*;

public class Main {

    public static void main(String[] args) {
        Dao.ini();
        Menu menu = new MenuPrincipal();
        menu.run();
    }

}