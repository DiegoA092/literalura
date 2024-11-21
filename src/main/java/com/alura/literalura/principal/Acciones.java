package com.alura.literalura.principal;

import com.alura.literalura.entities.Autor;
import com.alura.literalura.model.DatosListaDeLibros;
import com.alura.literalura.entities.Libro;
import com.alura.literalura.model.Idioma;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Acciones {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;
    private AutorRepository autorRepositorio;
    private List<Libro> libros;
    private List<Autor> autores;
    private List<String> listaNombres;

    public Acciones(LibroRepository repository, AutorRepository autorRepository){
        this.repositorio = repository;
        this.autorRepositorio = autorRepository;
    }

    public DatosListaDeLibros obtenerListaDeLibros() {
        System.out.println("Introduzca el titulo del libro a buscar:");
        var libroBuscado = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE+libroBuscado.replace(" ", "%20"));
        return conversor.obtenerDatos(json, DatosListaDeLibros.class);
    }

    public void buscarLibroPorTitulo() {
        listarNombresDeAutores();
        DatosListaDeLibros datosListaDeLibros = obtenerListaDeLibros();
        Libro libro = new Libro(datosListaDeLibros.resultados().get(0));
        Autor autor = libro.getAutor();
        String nombreAutor = autor.getNombre();
        if (listaNombres.contains(nombreAutor)) {
            Autor autorExistente = autorRepositorio.findByNombre(nombreAutor);
            libro.setAutor(autorExistente);
            repositorio.save(libro);
            System.out.println(libro);
        } else {
            autorRepositorio.save(autor);
            repositorio.save(libro);
            System.out.println(libro);
        }
    }

    public void listarNombresDeAutores() {
        autores = autorRepositorio.findAll();
        listaNombres = new ArrayList<>();
        for (int i = 0; i < autores.size(); i++) {
            String autor = autores.get(i).getNombre();
            listaNombres.add(autor);
        }
    }

    public void listarLibrosRegistrados(){
        libros = repositorio.findAll();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(l -> System.out.printf("Libro: %s | Autor: %s | Idioma: %s | Descargas: %.1f \n", l.getTitulo(),
                        l.getAutor(), l.getIdioma(), l.getNumeroDeDescargas()));
    }

    public void listarAutoresRegistrados(){
        autores = autorRepositorio.findAll();

        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(a -> {
                    String tituloLibros = a.getLibros().stream()
                            .map(Libro::getTitulo)
                            .collect(Collectors.joining(" / "));

                    System.out.printf("Autor: %s | %d - %d | Libros: %s \n", a.getNombre(),
                            a.getFechaDeNacimiento(), a.getFechaDeDefuncion(), tituloLibros);
                });

    }

    public void listarAutoresPorFecha(){
        System.out.println("Introduce fecha inicial:");
        var fechaInicial = teclado.nextInt();
        teclado.nextLine();
        List<Autor> autoresPorFecha = autorRepositorio.autoresPorFecha(fechaInicial);
        autoresPorFecha.forEach(System.out::println);
    }

    public void listarLibrosPorIdioma(){
        System.out.println("Seleccione idioma (EN/ES/FR/PR):");
        var idiomaSeleccionado = teclado.nextLine();
        var idiomaDisponible = Idioma.fromString(idiomaSeleccionado);
        List<Libro> librosPorIdioma = repositorio.findByIdioma(idiomaDisponible);
        System.out.println("Obras en el idioma seleccionado: ");
        librosPorIdioma.forEach(l -> System.out.printf("Libro: %s | Autor: %s \n", l.getTitulo(), l.getAutor()));
    }

    public void top10LibrosMasDescargados() {
        libros = repositorio.findTop10ByOrderByNumeroDeDescargasDesc();
        System.out.println("Top 10 de libros mas descargados:");
        libros.forEach(l -> System.out.printf("Descargas: %.1f Libro: %s | Autor: %s \n", l.getNumeroDeDescargas(),l.getTitulo(), l.getAutor()));
    }

    public void listarLibrosPorAutor() {
        autores = autorRepositorio.findAll();
        System.out.println("Autores disponibles: ");
        autores.forEach(a -> System.out.printf("%s (%d - %d) \n", a.getNombre(), a.getFechaDeNacimiento(), a.getFechaDeDefuncion()));
        System.out.println("Ingrese autor a consultar:");
        var autorSeleccionado = teclado.nextLine();
        libros = repositorio.listarLibrosPorAutor(autorSeleccionado);
        System.out.printf("Obras de %s \n", libros.get(0).getAutor());
        libros.forEach(l -> System.out.printf("Libro: %s | Idioma: %s | Descargas: %.1f \n", l.getTitulo(),
                l.getIdioma(), l.getNumeroDeDescargas()));
    }

    public void estadisticas() {
        libros = repositorio.findAll();

        DoubleSummaryStatistics est = libros.stream()
                .filter(l -> l.getNumeroDeDescargas() > 0.0)
                .collect(Collectors.summarizingDouble(Libro::getNumeroDeDescargas));

        System.out.println("Promedio de descargas: " + est.getAverage());
        System.out.println("Mayor cantidad de descargas por libro: " + est.getMax());
        System.out.println("Menor cantidad de descargas por libro: " + est.getMin() );
    }
}
