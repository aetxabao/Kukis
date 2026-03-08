package edu.masanz.da.kk.gui;

import java.util.List;
import java.util.Scanner;

public class Gui {

    private static Scanner scanner = new Scanner(System.in);

    public static void mostrarTituloPrincipal() {
        System.out.print("""
####################################################################
#   PROGRAMA  DE  GESTIÓN  DE  KUKIS  DE  SESIONES  DE  USUARIOS   #
####################################################################
""");
    }

    public static void mostrarMenuPrincipal() {
        System.out.print("""

1. Obtener id de sesión de un usuario
2. Crear una kuki de sesión para un usuario
3. Eliminar kukis y sesiones caducadas o huerfanas
4. Mostrar el id seguido del nombre de los items
   ordenados alfabéticamente sin repetir filtrados por un texto
5. Introducir un nuevo item en los intereses de un usuario
6. Mostrar el nombre de los items
   sobre los que todos los usuarios tienen interés
   ordenados alfabéticamente sin repetir
7. Mostrar el id y nombre de los items que no interesan a nadie
0. Exit
""");
    }

    public static int leerOpcion(String txt, int min, int max) {
        int opc = -1;
        if (max == Integer.MAX_VALUE) {
            System.out.printf("%s: ", txt);
        } else {
            System.out.printf("%s [%d,%d]: ", txt, min, max);
        }
        while(opc<min || opc>max) {
            try {
                opc = Integer.parseInt(scanner.nextLine().trim());
            }catch (Exception e) { }
            System.out.println();
        }
        return opc;
    }


    public static String leerTexto(String txt) {
        System.out.printf(txt);
        return scanner.nextLine().trim();
    }

    public static String leerTexto(String txt, int tam) {
        System.out.printf("%-" + tam + "s", txt);
        return scanner.nextLine().trim();
    }


    public static void mostrarIdSesion(String idSesion) {
        System.out.printf("Id de sesión: %s\n", idSesion);
    }

    public static void mostrarEliminadaInfoCaducada(int n) {
        System.out.printf("Se han eliminado %d sesiones caducadas.\n", n);
    }

    public static void mostrarItemIntroducido(boolean b) {
        if (b) {
            System.out.println("Item introducido.");
        } else {
            System.out.println("No se ha podido introducir el item.");
        }
    }

    public static void mostrarListadoItemsFiltrado(boolean b) {
        if (b) {
            System.out.println("\nListado de items filtrado\n");
        } else {
            System.out.println("\nListado de items completo\n");
        }
    }

    public static void mostrarListado(List<String> lista) {
        for (String s : lista) {
            System.out.println(s);
        }
        if (lista.isEmpty()) {
            System.out.println("No hay ocurrencias.");
        }
    }

}
