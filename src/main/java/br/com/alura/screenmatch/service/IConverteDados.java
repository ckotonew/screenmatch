package br.com.alura.screenmatch.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
    /*
    Generics permitem criar classes, interfaces e métodos que podem trabalhar com tipos
    desconhecidos ou parâmetros genéricos. Eles fornecem uma forma de escrever código
    flexível e reutilizável, tornando-o independente de tipos específicos e permitindo
    que ele funcione com diferentes tipos de dados.

    Para criar um método ou classe genérico, você precisa usar parâmetros de tipo
    (tipos genéricos) que são representados entre colchetes angulares < >.
    Geralmente, usamos letras maiúsculas únicas para representar os tipos genéricos,
    mas você pode usar qualquer identificador válido em Java. Aqui está um exemplo
    de uma classe genérica chamada Caixa, que armazena um valor de um tipo desconhecido:

    public class Caixa<T> {
        private T conteudo;

        public T getConteudo() {
            return conteudo;
        }

        public void setConteudo(T conteudo) {
            this.conteudo = conteudo;
        }
    }

    A palavra T representa um tipo qualquer
    */
}
