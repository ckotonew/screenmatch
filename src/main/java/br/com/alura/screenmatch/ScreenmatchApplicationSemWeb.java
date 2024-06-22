package br.com.alura.screenmatch;

import br.com.alura.screenmatch.principal.Principal;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
@SpringBootApplication
public class ScreenmatchApplicationSemWeb implements CommandLineRunner {
*//*
A interface CommandLineRunner é um recurso poderoso dentro do universo do Spring Framework,
amplamente utilizado no desenvolvimento de aplicações Java. Ela permite que executemos
alguma ação logo após a inicialização de nossa aplicação. Pode ser muito útil, por exemplo,
se quisermos carregar alguns dados em nosso banco de dados logo na inicialização de nossa
aplicação, como a criação de beans, entre outros.
*//*

@Autowired
private SerieRepository repositorio;
*//*
Injeção de Dependência com Spring
Vamos usar um recurso do Spring chamado injeção de dependência, isso significa que delegaremos
para o Spring — para o framework — a responsabilidade de instanciar uma classe que vamos usar.
O repositório é uma interface que vamos precisar usar direto. Precisamos salvar, buscar e
queremos que o Spring tenha a responsabilidade de deixar isso disponível para quando precisarmos.
Para implementar a injeção de dependência, nós usamos uma anotação chamada autowired.
 *//*
	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplicationSemWeb.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio);
		//Principal2 principal = new Principal2();
		principal.exibeMenu();
	}
}*/
