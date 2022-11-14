package com.uniftec.apppessoa.Uteis;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.uniftec.apppessoa.ConsultarActivity;
import com.uniftec.apppessoa.EditarActivity;
import com.uniftec.apppessoa.R;
import com.uniftec.apppessoa.model.ProdutoModel;
import com.uniftec.apppessoa.repository.ProdutoRepository;

public class LinhaConsultarAdapter extends BaseAdapter {

    //CRIANDO UM OBJETO LayoutInflater PARA FAZER LINK A NOSSA VIEW(activity_linha_consultar.xml)
    private static LayoutInflater layoutInflater = null;

    //CRIANDO UMA LISTA DE PESSOAS
    List<ProdutoModel> produtoModels =  new ArrayList<ProdutoModel>();

    //CIRANDO UM OBJETO DA NOSSA CLASSE QUE FAZ ACESSO AO BANCO DE DADOS
    ProdutoRepository produtoRepository;

    //CRIANDO UM OBJETO DA NOSSA ATIVIDADE QUE CONTEM A LISTA
    private ConsultarActivity consultarActivity;

    //CONSTRUTOR QUE VAI RECEBER A NOSSA ATIVIDADE COMO PARAMETRO E A LISTA DE PESSOAS QUE VAI RETORNAR
    //DA NOSSA BASE DE DADOS
    public LinhaConsultarAdapter(ConsultarActivity consultarActivity, List<ProdutoModel> pessoaModels) {

        this.produtoModels       =  pessoaModels;
        this.consultarActivity  =  consultarActivity;
        this.layoutInflater     = (LayoutInflater) this.consultarActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.produtoRepository   = new ProdutoRepository(consultarActivity);
    }

    //RETORNA A QUANTIDADE DE REGISTROS DA LISTA
    @Override
    public int getCount(){

        return produtoModels.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    //ESSE MÉTODO SETA OS VALORES DE UM ITEM DA NOSSA LISTA DE PESSOAS PARA UMA LINHA DO NOSSO LISVIEW
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        //CRIANDO UM OBJETO DO TIPO View PARA ACESSAR O NOSSO ARQUIVO DE LAYOUT activity_linha_consultar.xml
        final View viewLinhaLista = layoutInflater.inflate(R.layout.activity_linha_consultar,null);

        //VINCULANDO OS CAMPOS DO ARQUIVO DE LAYOUT(activity_linha_consultar.xml) AOS OBJETOS DECLARADOS.

        //CAMPO QUE VAI MOSTRAR O CÓDIGO DA PESSOA
        TextView textViewCodigo          = (TextView) viewLinhaLista.findViewById(R.id.textViewCodigo);

        //CAMPO QUE VAI MOSTRAR O NOME DA PESSOA
        TextView textViewNomeProduto            = (TextView) viewLinhaLista.findViewById(R.id.textViewNomeProduto);

        //CAMPO QUE VAI MOSTRAR O ENDEREÇO DA PESSOA
        TextView textViewPreco        = (TextView) viewLinhaLista.findViewById(R.id.textViewPreco);

        //CAMPOS QUE VAI MOSTRAR SE O REGISTRO ESTÁ ATIVO OU NÃO
        TextView textViewRegsitroAtivo   = (TextView) viewLinhaLista.findViewById(R.id.textViewRegistroAtivo);

        //CRIANDO O BOTÃO  EXCLUIR PARA DELETARMOS UM REGISTRO DO BANCO DE DADOS
        Button buttonExcluir             = (Button)   viewLinhaLista.findViewById(R.id.buttonExcluir);

        //CRIANDO O BOTÃO PARA EDITAR UM REGISTRO CADASTRADO
        Button buttonEditar            = (Button)   viewLinhaLista.findViewById(R.id.buttonEditar);

        //SETANDO O CÓDIGO NO CAMPO DA NOSSA VIEW
        textViewCodigo.setText(String.valueOf(produtoModels.get(position).getCodigo()));

        //SETANDO O NOME NO CAMPO DA NOSSA VIEW
        textViewNomeProduto.setText(produtoModels.get(position).getNomeProduto());

        //SETANDO O ENDEREÇO NO CAMPO DA NOSSA VIEW
        textViewPreco.setText(produtoModels.get(position).getPreco());

        //SETANDO SE O REGISTRO ESTA ATIVO OU NÃO
        if(produtoModels.get(position).getRegistroAtivo() == 1)
            textViewRegsitroAtivo.setText("Registro Ativo:Sim");
        else
            textViewRegsitroAtivo.setText("Registro Ativo:Não");


        //CRIANDO EVENTO CLICK PARA O BOTÃO DE EXCLUIR REGISTRO
        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //EXCLUINDO UM REGISTRO
                produtoRepository.Excluir(produtoModels.get(position).getCodigo());

                //MOSTRA A MENSAGEM APÓS EXCLUIR UM REGISTRO
                Toast.makeText(consultarActivity, "Registro excluido com sucesso!", Toast.LENGTH_LONG).show();

                //CHAMA O MÉTODO QUE ATUALIZA A LISTA COM OS REGISTROS QUE AINDA ESTÃO NA BASE
                AtualizarLista();

            }
        });

        //CRIANDO EVENTO CLICK PARA O BOTÃO QUE VAI REDIRECIONAR PARA A TELA DE EDIÇÃO
        // DO REGISTRO.
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRedirecionar = new Intent(consultarActivity, EditarActivity.class);
                intentRedirecionar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentRedirecionar.putExtra("id_produto",produtoModels.get(position).getCodigo());
                consultarActivity.startActivity(intentRedirecionar);
                consultarActivity.finish();
            }
        });
        return viewLinhaLista;
    }

    //ATUALIZA A LISTTA DEPOIS DE EXCLUIR UM REGISTRO
    public void AtualizarLista(){
        this.produtoModels.clear();
        this.produtoModels = produtoRepository.SelecionarTodos();
        this.notifyDataSetChanged();
    }
}
