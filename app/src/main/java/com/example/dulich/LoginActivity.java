package com.example.dulich;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dulich.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
//    //view binding
//    private ActivityLoginBinding binding;
//    //firebase auth
//    private FirebaseAuth firebaseAuth;
//
//    private ProgressDialog progressDialog;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityLoginBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Hãy đợi ");
//        progressDialog.setCanceledOnTouchOutside(false);
//
//        //xu ly click va di den trang dang ky
//        binding.noAccountTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
//            }
//        });
//
//        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                validateData();
//            }
//        });
//    }
//
//    private  String email="",password="";
//    private void validateData() {
//        email = binding.emailEt.getText().toString().trim();
//        password=binding.passwordEt.getText().toString().trim();
//
//        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            Toast.makeText(this,"Email sai...",Toast.LENGTH_SHORT).show();
//        }
//        else if(TextUtils.isEmpty(password)){
//            Toast.makeText(this,"Password sai...",Toast.LENGTH_SHORT).show();
//        }
//        else {
//            loginUser();
//        }
//    }
//
//    private void loginUser() {
//        progressDialog.setMessage("Loggin In...");
//        progressDialog.show();
//
//       firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//           @Override
//           public void onComplete(@NonNull Task<AuthResult> task) {
//               if(task.isSuccessful()){
//                  Toast.makeText(getApplicationContext(),"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
//                  Intent intent=new Intent(LoginActivity.this,DashboardUserActivity.class);
//                  startActivity(intent);
//               }
//               else {
//                   Toast.makeText(getApplicationContext(),"Đăng nhập không thành công!",Toast.LENGTH_SHORT).show();
//               }
//           }
//       });
//    }
    private LinearLayout layoutRegister;
    private EditText emailEt,passwordEt;
    private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUi();
        initListener();
    }

    private void initUi() {
        layoutRegister = findViewById(R.id.noAccountTv);
        emailEt = findViewById(R.id.emailEt);
        passwordEt=findViewById(R.id.passwordEt);
        loginBtn=findViewById(R.id.loginBtn);
    }
    private void initListener() {
        layoutRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRegister();
            }
        });
    }

    private void onClickRegister() {
        String strEmail = emailEt.getText().toString().trim();
        String strPassword = passwordEt.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()){
            Toast.makeText(this,"Email sai...",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(strPassword)){
            Toast.makeText(this,"Password sai...",Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(strEmail,strPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Đăng nhập thành công", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this,DashboardUserActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }else {
                            Toast.makeText(getApplicationContext(),"Đăng nhập không thành công", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}