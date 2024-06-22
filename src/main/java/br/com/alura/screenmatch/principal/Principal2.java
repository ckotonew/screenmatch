package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
//O consumoApi serve para outros tipos de endereço
//json = consumoApi.obterDados("https://coffee.alexflipnote.dev/random.json");
//System.out.println(json);

public class Principal2 {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    //Constantes - não se modificam - private final - padrão de nomemclatura MAÍUSCULA e separado por _ exemplo: NOME_EMPRESA
    private final String ENDERECO = "https://omdbapi.com/?t=";
    private final String API_KEY = "&apikey=f1bb402f";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

		List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i<=dados.totalTemporadas(); i++){
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") +"&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

        //Forma clássica
//        for(int i = 0; i < dados.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for(int j = 0; j< episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        //Utilizando lambda
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        /*
        As funções Lambda - chamadas de funções anônimas - são uma maneira de definir funções
        que são frequentemente usadas uma única vez, direto no local onde elas serão usadas.

        Exemplo de uso de funções Lambda
        Vamos a um exemplo concreto para entender melhor. Suponhamos que temos uma lista
        de números e queremos printar apenas os números pares dessa lista. Sem o uso de
        funções lambda, poderíamos fazer algo assim:

        List<Integer> lista = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        for(Integer i: lista) {
          if(i % 2 == 0) {
            System.out.println(i);
          }
        }

        Porém, com o uso de funções lambda, podemos simplificar esse código:

        List<Integer> lista = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        lista.stream().filter(i -> i % 2 == 0).forEach(System.out::println);

        No código acima, criamos um stream da nossa lista, filtramos esse stream para
        incluir apenas os números pares (isso é feito pela função lambda i -> i % 2 == 0),
        e finalmente usamos o método forEach para printar cada elemento do stream filtrado.
        
         */

        //Stream é um fluxo de dados
        System.out.println("Testando Stream");
        List<String> nomes = Arrays.asList("Jacque", "Iasmin", "Paulo", "Rodrigo", "Nico");

        nomes.stream()
                .sorted()                           //Ordenar
                .limit(3)                   //Limitar a 3 registros
                .filter(n -> n.startsWith("N"))     //Filtrar que começam com N
                .map(n -> n.toUpperCase())          //Transformar em maiusculo
                .forEach(System.out::println);

        //Novo exemplo Stream
        List<DadosEpisodio> dadosEpisodios = temporadas.stream()    //Pegar cada um dos episódios e aglutiná-los
                .flatMap(t -> t.episodios().stream())               //Gerar um fluxo de dados
                .collect(Collectors.toList());                      //Gerar uma lista que pode ser modificada

        System.out.println("\n Top 10 episódios");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))    //Ignorar avaliação "N/A"
                .peek(e -> System.out.println("Primeiro filtro(N/A) " + e))         //Log do resultado
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())  //Ordem descrecente
                .peek(e -> System.out.println("Ordenação " + e))                    //Log do resultado
                .limit(10)                                                  //Limitar a 10 registros
                .peek(e -> System.out.println("Limite " + e))                       //Log do resultado
                .map(e -> e.titulo().toUpperCase())                                 //Transformar para maiusculo
                .peek(e -> System.out.println("Mapeamento " + e))                   //Log do resultado
                .forEach(System.out::println);

        //Utilizando nova classe Episodio
        System.out.println("\n Listagem com Temporada e Episodios");
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());
        episodios.forEach(System.out::println);

        //Filtrando por data
        System.out.println("\n A partir de que ano você deseja ver os episódios?");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                        .forEach(e -> System.out.println(
                                "Temporada: " + e.getTemporada() +
                                        " Episódio: " + e.getTitulo() +
                                        " Data lançamento: " + e.getDataLancamento().format(formatador)
                ));

        //Pesquisando por parte do texto
        System.out.println("\n Digite um trecho do título do episódio");
        var trechoTitulo = leitura.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream() //Optional - Pode ou Não encontrar - evita erros de null
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase())) //Pesquisar
                .findFirst(); //Encontrar a primeira ocorrência - também existe o findAny, mas esse não é preciso
        if(episodioBuscado.isPresent()){
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
        } else {
            System.out.println("Episódio não encontrado!");
        }

        //Pesquisando realizando agrupamento
        System.out.println("\n Agrupamento por temporada");
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)                            //Retirar avaliações zeradas
                .collect(Collectors.groupingBy(Episodio::getTemporada,          //Agrupando por temporada
                        Collectors.averagingDouble(Episodio::getAvaliacao)));   //Média por avaliação
        System.out.println(avaliacoesPorTemporada);

        //Estatítiscas
        System.out.println("\n Estatíticas");
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());

    }
}
