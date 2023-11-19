package com.example.login_google;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ImageButton btn_google;
    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient gsic;
    int RC_SIGN_IN = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_google = findViewById(R.id.btn_google);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Configuração do login com o Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        gsic = GoogleSignIn.getClient(this, gso);

        // Botão de Login com Google
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });

        // Se ja tiver logado redireciona pra segunda tela
        if(auth.getCurrentUser() != null){
            Intent it = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(it);
            finish();
        }
    }

    private void googleSignIn() {
        Intent it = gsic.getSignInIntent();
        startActivityForResult(it, RC_SIGN_IN);
    }

    // Callback para processar o resultado do login do Google
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            } catch(Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    // Autenticação no Firebase com as credenciais do Google
    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();

                    // Salva informações do usuário no banco de dados
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", user.getUid());
                    map.put("name", user.getDisplayName());

                    // Verifica se o número de telefone está disponível
                    if (user.getPhoneNumber() != null) {
                        map.put("profile", user.getPhoneNumber().toString());
                    }

                    database.getReference().child("users").child(user.getUid()).setValue(map);

                    // Redireciona para a SecondActivity após login bem-sucedido
                    Intent it = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(it);
                } else {
                    Toast.makeText(MainActivity.this, "Algo está errado!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}