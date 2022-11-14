package com.uniftec.apppessoa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.uniftec.apppessoa.Uteis.Uteis;
import com.uniftec.apppessoa.model.ProdutoModel;
import com.uniftec.apppessoa.repository.ProdutoRepository;

public class EditarActivity extends AppCompatActivity {

    /*COMPONENTES DA TELA*/
    EditText editTextCodigo;
    EditText editTextNomeProduto;
    EditText editTextPreco;
    CheckBox checkBoxRegistroAtivo;
    Button buttonAlterar;
    Button buttonVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        //CHAMA O MÉTODO PARA CRIAR OS COMPONENTES DA TELA
        this.CriarComponentes();
        //CHAMA O MÉTODO QUE CRIA EVENTOS PARA OS COMPONENTES
        this.CriarEventos();
        //CARREGA OS VALORES NOS CAMPOS DA TELA.
        this.CarregaValoresCampos();
    }

    //VINCULA OS COMPONENTES DA TELA(VIEW) AOS OBJETOS DECLARADOS.
    protected  void CriarComponentes(){

        editTextCodigo          = (EditText) this.findViewById(R.id.editTextCodigo);
        editTextNomeProduto     = (EditText) this.findViewById(R.id.editTextNomeProduto);
        editTextPreco           = (EditText) this.findViewById(R.id.editTextPreco);
        checkBoxRegistroAtivo   = (CheckBox)this.findViewById(R.id.checkBoxRegistroAtivo);
        buttonAlterar           = (Button) this.findViewById(R.id.buttonAlterar);
        buttonVoltar            = (Button) this.findViewById(R.id.buttonVoltar);
    }

    //MÉTODO CRIA OS EVENTOS PARA OS COMPONENTES
    protected  void CriarEventos(){

        //CRIANDO EVENTO CLICK PARA O BOTÃO ALTERAR
        buttonAlterar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Alterar_onClick();
            }
        });

        //CRIANDO EVENTO CLICK PARA O BOTÃO VOLTAR
        buttonVoltar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                finish();
            }
        });
    }

    //ALTERA UM REGISTRO
    protected  void Alterar_onClick(){

        //VALIDA SE OS CAMPOS ESTÃO VAZIOS ANTES DE ALTERAR O REGISTRO
        if(editTextNomeProduto.getText().toString().trim().equals("")){

            Uteis.Alert(this, "Nome Obrigatório");

            //FOCO NO CAMPO
            editTextNomeProduto.requestFocus();
        }
        else if(editTextPreco.getText().toString().trim().equals("")){
            Uteis.Alert(this, "Endereço Obrigatório");
            editTextPreco.requestFocus();

        }
        else{


            /*CRIANDO UM OBJETO PESSOA*/
            ProdutoModel produtoModel = new ProdutoModel();
            produtoModel.setCodigo(Integer.parseInt(editTextCodigo.getText().toString()));
            /*SETANDO O VALOR DO CAMPO NOME*/
            produtoModel.setNomeProduto(editTextNomeProduto.getText().toString().trim());
            /*SETANDO O ENDEREÇO*/
            produtoModel.setPreco(editTextPreco.getText().toString().trim());

            /*SETA O REGISTRO COMO INATIVO*/
            produtoModel.setRegistroAtivo((byte)0);
            /*SE TIVER SELECIONADO SETA COMO ATIVO*/
            if(checkBoxRegistroAtivo.isChecked())
                produtoModel.setRegistroAtivo((byte)1);

            /*ALTERANDO O REGISTRO*/
            new ProdutoRepository(this).Atualizar(produtoModel);
            /*MENSAGEM DE SUCESSO!*/

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
            alertDialog.setTitle(R.string.app_name);
            //MENSAGEM A SER EXIBIDA
            alertDialog.setMessage("Registro alterado com sucesso! ");

            //CRIA UM BOTÃO COM O TEXTO OK SEM AÇÃO
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //RETORNA PARA A TELA DE CONSULTA
                    Intent intentRedirecionar = new Intent(getApplicationContext(), ConsultarActivity.class);
                    startActivity(intentRedirecionar);
                    finish();
                }
            });
            //MOSTRA A MENSAGEM NA TELA
            alertDialog.show();
        }
    }

    //CARREGA OS VALORES NOS CAMPOS APÓS RETORNAR DO SQLITE
    protected  void CarregaValoresCampos(){
        ProdutoRepository produtoRepository = new ProdutoRepository(this);

        //PEGA O ID PESSOA QUE FOI PASSADO COMO PARAMETRO ENTRE AS TELAS
        Bundle extra =  this.getIntent().getExtras();
        int id_pessoa = extra.getInt("id_pessoa");

        //CONSULTA UMA PESSOA POR ID
        ProdutoModel produtoModel = produtoRepository.GetPessoa(id_pessoa);
        //SETA O CÓDIGO NA VIEW
        editTextCodigo.setText(String.valueOf(produtoModel.getCodigo()));
        //SETA O NOME NA VIEW
        editTextNomeProduto.setText(produtoModel.getNomeProduto());
        //SETA O ENDEREÇO NA VIEW
        editTextPreco.setText(produtoModel.getPreco());
        //SETA SE O  REGISTRO ESTÁ ATIVO
        if(produtoModel.getRegistroAtivo() == 1)
            checkBoxRegistroAtivo.setChecked(true);
    }
}
