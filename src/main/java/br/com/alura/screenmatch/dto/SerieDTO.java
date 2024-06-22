package br.com.alura.screenmatch.dto;

import br.com.alura.screenmatch.model.Categoria;

//DTO (Data Transfer Object, ou Objeto de Transferência de Dados)
public record SerieDTO(
        Long id,
        String titulo,
        Integer totalTemporadas,
        Double avaliacao,
        Categoria genero,
        String poster,
        String sinopse,
        String atores) {

}
