package com.alura.literalura.repository;

import com.alura.literalura.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento >= :fechaInicial")
    List<Autor> autoresPorFecha(int fechaInicial);
}
