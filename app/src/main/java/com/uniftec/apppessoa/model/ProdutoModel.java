package com.uniftec.apppessoa.model;

public class ProdutoModel {

    private Integer codigo;
    private String nomeProduto;
    private String preco;
    private byte    registroAtivo;


    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNomeProduto() {return nomeProduto;}

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public byte getRegistroAtivo() {
        return registroAtivo;
    }

    public void setRegistroAtivo(byte registroAtivo) {
        this.registroAtivo = registroAtivo;
    }
}
