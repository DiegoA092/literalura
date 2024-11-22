package com.alura.literalura.principal;

import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal extends Acciones {
    private Scanner teclado = new Scanner(System.in);
    private boolean confirmacion;

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        super(repository, autorRepository);
    }

    public void mensajeDeBienvenida(){
            System.out.println("\n*** Bienvenido a Literalura ***");
            System.out.println("Desarrollado por Diego Alvarado\n");
        }

        public void muestraElMenu() {
            var opcion = -1;
            while (opcion != 0) {
                var menu = """
                        -------------------------------------
                        1 - Buscar libro por titulo
                        2 - Listar libros registrados
                        3 - Listar autores registrados
                        4 - Listar autores vivos desde determinado año
                        5 - Listar libros por idioma
                        6 - Top 10 libros mas descargados
                        7 - Listar libros registrados por autor
                        8 - Estadisticas

                        0 - Salir
                        """;

                try {
                    System.out.println(menu);
                    opcion = teclado.nextInt();
                    teclado.nextLine();

                    switch (opcion) {
                        case 1:
                            confirmacion = false;
                            do {
                                try {
                                    confirmacion = buscarLibroPorTitulo();
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Libro no disponible, intente de nuevo");
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Ingrese titulo valido");
                                }} while (!confirmacion);
                            break;
                        case 2:
                            listarLibrosRegistrados();
                            break;
                        case 3:
                            listarAutoresRegistrados();
                            break;
                        case 4:
                            listarAutoresPorFecha();
                            break;
                        case 5:
                            confirmacion = false;
                            do {
                                try {
                                    confirmacion = listarLibrosPorIdioma();
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Ingrese idioma valido");
                                } } while (!confirmacion);
                            break;
                        case 6:
                            top10LibrosMasDescargados();
                            break;
                        case 7:
                            confirmacion = false;
                            do {
                            try {
                                confirmacion = listarLibrosPorAutor();
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Ingrese autor valido");
                            } } while (!confirmacion);
                            break;
                        case 8:
                            estadisticas();
                            break;
                        case 0:
                            System.out.println("Cerrando la aplicación...");
                            System.out.println("  *** Gracias por usar Literalura ***");
                            System.out.println("*** Desarrollado por Diego Alvarado ***");
                            break;
                        default:
                            System.out.println("Opción inválida");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Ingrese opcion valida");
                    teclado.next();
                }
            }
        }

}
