package com.alura.literalura.repository;

import com.alura.literalura.entities.Autor;
import com.alura.literalura.entities.Libro;
import com.alura.literalura.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByIdioma(Idioma idiomaDisponible);
    List<Libro> findTop10ByOrderByNumeroDeDescargasDesc();

    @Query("SELECT l FROM Autor a JOIN a.libros l WHERE a.nombre ILIKE %:autorSeleccionado%")
    List<Libro> listarLibrosPorAutor(String autorSeleccionado);

    Libro findByTitulo(String nombreLibro);
}
