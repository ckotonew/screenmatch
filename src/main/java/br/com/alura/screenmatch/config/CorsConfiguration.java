package br.com.alura.screenmatch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* CORS (Cross-Origin Resource Sharing) “compartilhamento de recursos com origens diferentes”

Quando desenvolvemos APIs e queremos que todos os seus recursos fiquem disponíveis
a qualquer cliente HTTP, uma das coisas que vem à nossa cabeça é o CORS.
O CORS é um mecanismo utilizado para adicionar cabeçalhos HTTP que informam aos
navegadores para permitir que uma aplicação Web seja executada em uma origem e
acesse recursos de outra origem diferente. Esse tipo de ação é chamada de requisição
cross-origin HTTP. Na prática, então, ele informa aos navegadores se um determinado
recurso pode ou não ser acessado.
*/

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5500")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
    }
}

