package com.alura.literalura.principal;

import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;

import java.util.Scanner;

public class Principal extends Acciones {
    private Scanner teclado = new Scanner(System.in);

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
                        4 - Listar autores vivos en determinado periodo
                        5 - Listar libros por idioma
                        6 - Top 10 libros mas descargados
                        7 - Listar libros registrados por autor
                        8 - Estadisticas

                        0 - Salir
                        """;
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
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
                        listarLibrosPorIdioma();
                        break;
                    case 6:
                        top10LibrosMasDescargados();
                        break;
                    case 7:
                        listarLibrosPorAutor();
                        break;
                    case 8:
                        estadisticas();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            }

        }

}
