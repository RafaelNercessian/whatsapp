package com.cursoandroid.whatsapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cursoandroid.whatsapp.R;
import com.cursoandroid.whatsapp.helper.Permissao;
import com.cursoandroid.whatsapp.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends Activity {

    private EditText telefone;
    private EditText ddd;
    private EditText ddi;
    private Button botao;
    private EditText nome;
    private String[] permissoes=new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        telefone = (EditText) findViewById(R.id.login_telefone);
        ddd = (EditText) findViewById(R.id.login_ddd);
        ddi = (EditText) findViewById(R.id.login_ddi);
        botao = (Button) findViewById(R.id.login_botao);
        nome = (EditText) findViewById(R.id.login_nome);

        inserindoMascaraNosEditText();

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto = ddi.getText().toString() + ddd.getText().toString() + telefone.getText().toString();
                String telefoneSemMais = telefoneCompleto.replace("+", "");
                String telefoneSemFormatcao = telefoneSemMais.replace("-", "");

                Permissao.validaPermissoes(LoginActivity.this,permissoes);


                //Gerando token
                Random random = new Random();
                int numeroRandomico = random.nextInt(9999 - 1000) + 1000;
                String token = String.valueOf(numeroRandomico);
                String mensagemEnvio="Whatsapp código de confirmação "+token;

                //Salvar os dados para validação
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatcao, token);

                HashMap<String, String> usuario = preferencias.getDadosUsuario();

                //Envio do SMS
                telefoneSemFormatcao="5554";
                boolean enviadoSMS = enviaSMS("+" + telefoneSemFormatcao, mensagemEnvio);

            }
        });
    }

    private boolean enviaSMS(String telefone, String mensagem) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void inserindoMascaraNosEditText() {
        SimpleMaskFormatter mascaraTelefone = new SimpleMaskFormatter("NNNNN-NNNN");
        MaskTextWatcher insereMascaraTelefone = new MaskTextWatcher(telefone, mascaraTelefone);
        telefone.addTextChangedListener(insereMascaraTelefone);

        SimpleMaskFormatter mascaraDdd = new SimpleMaskFormatter("NN");
        MaskTextWatcher insereMascaraDdd = new MaskTextWatcher(ddd, mascaraDdd);
        ddd.addTextChangedListener(insereMascaraDdd);

        SimpleMaskFormatter mascaraDdi = new SimpleMaskFormatter("+NN");
        MaskTextWatcher insereMascaraDdi = new MaskTextWatcher(ddi, mascaraDdi);
        ddi.addTextChangedListener(insereMascaraDdi);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int resultado:grantResults){
            if(resultado== PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar esse app, é necessário utilizar as permissões");

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();

    }
}
