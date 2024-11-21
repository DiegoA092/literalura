package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//record para obtener los datos de "results" en el json
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosListaDeLibros(
        @JsonAlias("results") List<DatosLibro> resultados
        ) {
}
