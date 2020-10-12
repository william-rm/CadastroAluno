package br.usjt.ucsist.cadbasico.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import br.usjt.ucsist.cadbasico.R;
import br.usjt.ucsist.cadbasico.model.Usuario;
import br.usjt.ucsist.cadbasico.model.UsuarioViewModel;

public class CadastroActivity extends AppCompatActivity {

    private UsuarioViewModel usuarioViewModel;
    private Usuario usuarioCorrente;
    private EditText editTextNome;
    private EditText editTextCpf;
    private EditText editTextEmail;
    private EditText editTextSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Hawk.init(this). build();

        editTextNome = findViewById(R.id.editTextNome);
        editTextCpf = findViewById(R.id.editTextCpf);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.getUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(@Nullable final Usuario usuario) {
                updateView(usuario);
            }
        });
    }

    private void updateView(Usuario usuario){
        if(usuario != null && usuario.getId() > 0){
            usuarioCorrente = usuario;
            editTextNome.setText(usuario.getNome());
            editTextCpf.setText(usuario.getCpf());
            editTextEmail.setText(usuario.getEmail());
            editTextSenha.setText(usuario.getSenha());
        }
    }


    public void cadastrar(View view) {

        if(usuarioCorrente == null){
            usuarioCorrente = new Usuario();
        }
        usuarioCorrente.setNome(editTextNome.getText().toString());
        usuarioCorrente.setCpf(editTextCpf.getText().toString());
        usuarioCorrente.setEmail(editTextEmail.getText().toString());
        usuarioCorrente.setSenha(editTextSenha.getText().toString());
        usuarioViewModel.insert(usuarioCorrente);
        Toast.makeText(this,"Usuario salvo com sucesso",
                Toast.LENGTH_SHORT).show();
        Hawk.put("tem_cadastro",true);
        finish();
    }
}