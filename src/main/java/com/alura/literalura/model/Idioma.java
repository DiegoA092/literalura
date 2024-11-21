package com.alura.literalura.model;

public enum Idioma {
    ES("es", "Español"),
    EN("en", "Ingles"),
    FR("fr", "Frances"),
    PT("pt", "Portugues");

    private String idiomaApi;
    private String idiomaEspanol;

    Idioma (String idiomaApi, String idiomaEspanol) {
        this.idiomaApi = idiomaApi;
        this.idiomaEspanol = idiomaEspanol;
    }

    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaApi.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("No se encontro el lenguaje: " + text);
    }

    public static Idioma fromEspanol(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaEspanol.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("No se encontro el lenguaje: " + text);
    }
}
