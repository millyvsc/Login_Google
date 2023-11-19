package com.example.login_google;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseUser;

public class SecondActivity extends AppCompatActivity {

    TextView email;
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        email = findViewById(R.id.email);
        btn_logout = findViewById(R.id.btn_logout);

        // Obtem o usuário autenticado
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Obtem o e-mail do usuário e exibe no TextView
            String userEmail = user.getEmail();
            email.setText(userEmail);
        }

        // Botão de Logout
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    private void logoutUser() {
        // Desconecta do Firebase Auth
        FirebaseAuth.getInstance().signOut();

        // Desconecta do Google SignIn (revoga o acesso)
        GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()).signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // Redireciona para a tela de login
                Intent it = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(it);
                finish();
            }
        });
    }
}