package com.uniftec.apppessoa.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import com.uniftec.apppessoa.Uteis.DatabaseUtil;
import com.uniftec.apppessoa.model.ProdutoModel;

public class ProdutoRepository {

    DatabaseUtil databaseUtil;

    public ProdutoRepository(Context context){

        /***
         * CONSTRUTOR
         * @param context
         */

        databaseUtil =  new DatabaseUtil(context);

    }

    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param produtoModel
     */

    public void Salvar(ProdutoModel produtoModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("ds_nomeProduto",       produtoModel.getNomeProduto());
        contentValues.put("ds_preco",             produtoModel.getPreco());
        contentValues.put("fl_ativo",             produtoModel.getRegistroAtivo());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        databaseUtil.GetConexaoDataBase().insert("tb_produto",null,contentValues);

    }

    /***
     * ATUALIZA UM REGISTRO JÁ EXISTENTE NA BASE
     * @param produtoModel
     */

    public void Atualizar(ProdutoModel produtoModel){

        ContentValues contentValues =  new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("ds_nomeProduto",       produtoModel.getNomeProduto());
        contentValues.put("ds_preco",             produtoModel.getPreco());
        contentValues.put("fl_ativo",             produtoModel.getRegistroAtivo());

        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        databaseUtil.GetConexaoDataBase().update("tb_produto", contentValues, "id_produto = ?", new String[]{Integer.toString(produtoModel.getCodigo())});
    }

    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param codigo
     * @return
     */

    public Integer Excluir(int codigo){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return databaseUtil.GetConexaoDataBase().delete("tb_produto","id_produto = ?", new String[]{Integer.toString(codigo)});
    }

    /***
     * CONSULTA UMA PESSOA CADASTRADA PELO CÓDIGO
     * @param codigo
     * @return
     */

    public ProdutoModel GetPessoa(int codigo){


        Cursor cursor =  databaseUtil.GetConexaoDataBase().rawQuery("SELECT * FROM tb_produto WHERE id_produto = "+ codigo,null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        ProdutoModel produtoModel =  new ProdutoModel();

        //ADICIONANDO OS DADOS DA PESSOA
        produtoModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_produto")));
        produtoModel.setNomeProduto(cursor.getString(cursor.getColumnIndex("ds_nomeProduto")));
        produtoModel.setPreco(cursor.getString(cursor.getColumnIndex("ds_preco")));
        produtoModel.setRegistroAtivo((byte)cursor.getShort(cursor.getColumnIndex("fl_ativo")));

        //RETORNANDO A PESSOA
        return produtoModel;

    }

    /***
     * CONSULTA TODAS AS PESSOAS CADASTRADAS NA BASE
     * @return
     */
    public List<ProdutoModel> SelecionarTodos(){

        List<ProdutoModel> produtos = new ArrayList<ProdutoModel>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id_produto,      ");
        stringBuilderQuery.append("        ds_nomeProduto,  ");
        stringBuilderQuery.append("        ds_preco,        ");
        stringBuilderQuery.append("        fl_ativo         ");
        stringBuilderQuery.append("  FROM  tb_produto       ");
        stringBuilderQuery.append(" ORDER BY ds_nomeProduto ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        ProdutoModel produtoModel;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){

            /* CRIANDO UMA NOVA PESSOAS */
            produtoModel =  new ProdutoModel();

            //ADICIONANDO OS DADOS DA PESSOA
            produtoModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_produto")));
            produtoModel.setNomeProduto(cursor.getString(cursor.getColumnIndex("ds_nomeProduto")));
            produtoModel.setPreco(cursor.getString(cursor.getColumnIndex("ds_preco")));
            produtoModel.setRegistroAtivo((byte)cursor.getShort(cursor.getColumnIndex("fl_ativo")));

            //ADICIONANDO UMA PESSOA NA LISTA
            produtos.add(produtoModel);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return produtos;

    }
}
