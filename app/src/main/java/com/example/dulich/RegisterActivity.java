package com.example.dulich;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.dulich.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    //view binding
    private ActivityRegisterBinding binding;
    //chung thuc firebase
    private FirebaseAuth firebaseAuth;
    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebase auth
        firebaseAuth=FirebaseAuth.getInstance();

        //setup progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Hãy ");
        progressDialog.setCanceledOnTouchOutside(false);

        //xu ly click va quay lai
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //xu ly click va bat dau dang ky
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDate();
            }
        });
    }

    private String name="", email="",password="";
    private void validateDate(){
        //truoc khi tao TK, thuc hien cac buoc xac thuc du lieu

        //get data
        name=binding.nameEt.getText().toString().trim();
        email=binding.emailEt.getText().toString().trim();
        password=binding.passwordEt.getText().toString().trim();
        String cPassword = binding.CpasswordEt.getText().toString().trim();
        //kiem tra o du lieu con trong hay khong
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Nhập tên cua bạn...", Toast.LENGTH_LONG).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Email lỗi.....", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Nhập mật khẩu...", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(cPassword)){
            Toast.makeText(this,"Nhập lại mật khẩu...", Toast.LENGTH_LONG).show();
        }
        else if(!password.equals(cPassword)){
            Toast.makeText(this,"Mật khẩu không trùng...", Toast.LENGTH_LONG).show();
        }
        else {
            createUserAccount();
        }
    }

    private void createUserAccount(){
        //show progress
        progressDialog.setMessage("tạo tài khoảng....");
        progressDialog.show();

        //tao TK trong firebase auth
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //tao tai khaan thanh cong va add them vao firebase realtime date
                        progressDialog.dismiss();
                        updateUserInfo();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        //tao tai khoan that bai
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void updateUserInfo(){

    }
}