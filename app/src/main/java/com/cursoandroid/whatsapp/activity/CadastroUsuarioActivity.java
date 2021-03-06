package com.cursoandroid.whatsapp.activity;

import android.app.Activity;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.whatsapp.R;
import com.cursoandroid.whatsapp.modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroUsuarioActivity extends Activity {

    private FirebaseAuth mAuth;
    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button botao;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome = (EditText) findViewById(R.id.cadastro_nome);
        email = (EditText) findViewById(R.id.cadastro_email);
        senha = (EditText) findViewById(R.id.cadastro_senha);
        botao = (Button) findViewById(R.id.cadastro_botao);

        mAuth = FirebaseAuth.getInstance();

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                enviaDadosFirebase();
            }
        });

    }

    private void enviaDadosFirebase() {
        mAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CadastroUsuarioActivity.this, "Cadastro " + usuario.getNome() + " cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                            usuario.setId(task.getResult().getUser().getUid());
                            usuario.salvar();
                            mAuth.signOut();
                        } else {
                            String erro="";
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                erro="Digite uma senha mais forte, com letras e números";

                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                erro="O e-email digitado é inválido";

                            } catch(FirebaseAuthUserCollisionException e) {
                                erro="Esse e-mail já foi cadastrado";

                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(CadastroUsuarioActivity.this,erro,Toast.LENGTH_SHORT).show();
                        }
                        new CountDownTimer(4000, 1000) {

                            public void onTick(long millisUntilFinished) {
                            }

                            public void onFinish() {
                                finish();
                            }
                        }.start();
                    }
                });
    }


}
