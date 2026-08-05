package Utilidades;

import Controller.GestionAPP;

import java.util.Scanner;

public class Menus {
        public static Scanner scanner = new Scanner(System.in);

        public static String menuInicio(GestionAPP fernanpop) {
            if (fernanpop.permisoSinLogeo()) {
                System.out.print("""
                        ===================================================
                        BIENVENIDO A FERNANPOP
                        ===================================================
                        1. buscar productos
                        2. Iniciar sesión
                        3. Registrarse
                        4. Salir
                        Introduce la opción deseada:\s""");
            } else {
                System.out.print("""
                        ===================================================
                        BIENVENIDO A FERNANPOP
                        ===================================================
                        1. Iniciar sesión
                        2. Registrarse
                        3. Salir
                        Introduce la opción deseada:\s""");
            }
            return scanner.nextLine();
        }

        public static String menuPrincipal(int tratos,boolean admin) {
            if (!admin) {
                System.out.printf("""
                        \n**************************************************
                                             Menú de usuario
                        **************************************************
                                Tiene usted %d tratos que valorar
                        1. Mostrar mi perfil
                        2. Cambiar mis datos personales
                        3. Gestionar mis productos en venta
                        4. Poner un producto a la venta
                        5. Ver productos disponibles para comprar
                        6. Menú de valoraciones (Pendientes)
                        7. Ver mi histórico de tratos
                        8. Preguntar a Alfred
                        9. Borrar mi perfil de usuario
                        10. Cerrar sesión
                        11. Salir
                        Introduce la opción deseada:\s""", tratos);
            } else {
                System.out.printf("""
                        \n**************************************************
                                             Menú de usuario
                        **************************************************
                                Tiene usted %d tratos que valorar
                        1. Mostrar mi perfil
                        2. Cambiar mis datos personales
                        3. Gestionar mis productos en venta
                        4. Poner un producto a la venta
                        5. Ver productos disponibles para comprar
                        6. Menú de valoraciones (Pendientes)
                        7. Ver mi histórico de tratos
                        8. Preguntar a Alfred
                        9. Borrar mi perfil de usuario
                        10. Menu administrador
                        11. Cerrar sesión
                        12. Salir
                        Introduce la opción deseada:\s""", tratos);
            }
            return scanner.nextLine();
        }

        public static String menuCambiarDatos() {
            System.out.print("""
            \n--- Cambiar Datos Personales ---
            1. Cambiar email
            2. Cambiar clave
            3. Cambiar nombre
            4. Cambiar apellidos
            5. Cambiar teléfono
            6. Volver atrás
            Seleccione qué desea modificar:\s""");
            return scanner.nextLine();
        }

        public static String menuMisProductos() {
            System.out.print("""
            \n--- Gestión de mis Productos ---
            1. Mostrar todos mis productos en venta
            2. Borrar un producto
            3. Vender un producto (Cerrar venta con un comprador)
            4. Volver atrás
            Seleccione una opción:\s""");
            return scanner.nextLine();
        }

        public static String menuValoraciones() {
            System.out.print("""
            \n--- Valoraciones ---
            1. Ver valoraciones pendientes
            2. Valorar una compra
            3. Volver atrás
            Seleccione una opción:\s""");
            return scanner.nextLine();
        }
        public static String menuHistorialTratos(){
            System.out.print("""
                    \n--- Historial ---
                    1. Ver histórico de compras
                    2. Ver histórico de ventas
                    3. Salir
                    Seleccione una opción:\s""");
            return scanner.nextLine();
        }
        public static String menuEstados(){
            System.out.print("""
                    \n--- Estado ---
                    1. Como nuevo
                    2. Poco usado
                    3. Bien cuidado
                    4. Usado
                    5. Deteriorado
                    Selecciona una opción:\s""");
            return scanner.nextLine();
    }
    public static String menuBusquedaProductos(){
        System.out.print("""
                \n--- Menú de búsqueda de productos ---
                1. Explorar
                2. Buscar producto ordenado por precio
                3. Buscar producto por una id
                4. Buscar producto por texto
                5. Voy a tener suerte
                6. Volver
                Seleccione una opción:\s""");
        return scanner.nextLine();
    }
    public static String menuOpcionesCompra(){
        System.out.print("""
                1. Comprar por el precio del vendedor
                2. Hacer oferta
                3. Salir
                Seleccione una opción:\s""");
        return scanner.nextLine();
    }
    public static String menuAdmin(){
        System.out.print("""
                1. Mostrar configuración del programa
                2. Enviar lista de productos por correo
                3. Copia de seguridad
                4. Volver
                Seleccione una opción:\s""");
        return scanner.nextLine();
    }
}
