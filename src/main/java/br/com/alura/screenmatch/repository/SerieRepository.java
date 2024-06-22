package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

//Essa interface vai herdar do JPA Repository, que já abstrairá todos os
//métodos necessários para realizarmos as operações básicas no banco
public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);

    //Consulta usando JPA
    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int totalTemporadas, double avaliacao);

    //Mesma consulta usando JPQL
    @Query("select s from Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.avaliacao >= :avaliacao")
    List<Serie> seriesPorTemporadaEAValiacao(int totalTemporadas, double avaliacao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
    List<Episodio> episodiosPorTrecho(String trechoEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.dataLancamento) >= :anoLancamento")
    List<Episodio> episodiosPorSerieEAno(Serie serie, int anoLancamento);

    @Query("SELECT s FROM Serie s " +
            "JOIN s.episodios e " +
            "GROUP BY s " +
            "ORDER BY MAX(e.dataLancamento) DESC LIMIT 5")
    List<Serie> lancamentosMaisRecentes();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numero")
    List<Episodio> obterEpisodioPorTemporada(Long id, Long numero);



    /*
    JPA - Consultas derivadas ("derived queries")
    A JPA tem diversos recursos, e um dos mais legais que podemos utilizar são as derived queries,
    em que trabalhamos com métodos específicos que consultam o banco de forma personalizada.
    Esses métodos são criados na interface que herda de JpaRepository. Neles, utilizaremos
    palavras-chave (em inglês) para indicar qual a busca que queremos fazer.

    A estrutura básica de uma derived query na JPA consiste em:
        verbo introdutório + palavra-chave “By” + critérios de busca

    Como verbos introdutórios, temos find, read, query, count e get. Já os critérios são variados.
    Os critérios mais simples envolvem apenas os atributos da classe mapeada no Repository.
    No nosso caso, um exemplo desse critério seria o findByTitulo, em que fazemos uma busca por
    séries com um atributo específico da classe Serie. Mas podemos acrescentar condições a esses
    critérios. É aí que surge o findByTituloContainingIgnoreCase(). Para fazer os filtros,
    poderíamos utilizar várias outras palavras. Dentre elas, podemos citar:

    Palavras relativas à igualdade:
        Is, para ver igualdades
        Equals, para ver igualdades (essa palavra-chave e a anterior têm os mesmos princípios,
                                     e são mais utilizadas para a legibilidade do método).
        IsNot, para checar desigualdades
        IsNull, para verificar se um parâmetro é nulo

    Palavras relativas à similaridade:
        Containing, para palavras que contenham um trecho
        StartingWith, para palavras que comecem com um trecho
        EndingWith, para palavras que terminem com um trecho
        Essas palavras podem ser concatenadas com outras condições, como o ContainingIgnoreCase,
        para não termos problemas de Case Sensitive.

    Palavras relacionadas à comparação:
        LessThan, para buscar registros menores que um valor
        LessThanEqual, para buscar registros menores ou iguais a um valor
        GreaterThan, para identificar registros maiores que um valor
        GreaterThanEqual, para identificar registros maiores ou iguais a um valor
        Between, para saber quais registros estão entre dois valores
        Essas são apenas algumas das palavras que podemos utilizar, e podemos combiná-las de muitas formas!

    Para trabalhar com a ordenação de registros pesquisados, também existem algumas palavras-chave.
    Podemos utilizar o OrderBy para ordenar os registros por algum atributo deles, como a série pela avaliação.
    Também podemos encadear atributos. Se uma Série tem um Ator e queremos ordenar pelo nome do ator, podemos
    utilizar OrderByAtorNome, por exemplo.

    Além do OrderBy, ainda existem alguns outros recursos de filtros que podem ser utilizados:
        Distinct, para remover dados duplicados
        First, para pegar o primeiro registro
        Top, para limitar o número de dados


     */
}
