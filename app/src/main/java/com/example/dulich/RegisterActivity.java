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
import android.widget.Toast;

import com.example.dulich.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText  nameEt,emailEt,passwordEt,CpasswordEt;
    private Button registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUi();
        initListener();
    }

    private void initUi() {
        emailEt = findViewById(R.id.emailEt);
        passwordEt=findViewById(R.id.passwordEt);
        nameEt = findViewById(R.id.nameEt);
        CpasswordEt=findViewById(R.id.CpasswordEt);
        registerBtn=findViewById(R.id.registerBtn);

    }

    private void initListener() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRegister();
            }
        });
    }

    private void onClickRegister() {
        String strEmail = emailEt.getText().toString().trim();
        String strPassword = passwordEt.getText().toString().trim();
        String strName = nameEt.getText().toString().trim();
        String strCpassword = CpasswordEt.getText().toString().trim();
        if(TextUtils.isEmpty(strName)){
            Toast.makeText(this,"Nhập tên của bạn...", Toast.LENGTH_LONG).show();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()){
            Toast.makeText(this,"Nhập email...", Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(strPassword)){
            Toast.makeText(this,"Nhập mật khẩu...", Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(strCpassword)){
            Toast.makeText(this,"Nhập lại mật khẩu...", Toast.LENGTH_LONG).show();
            return;
        }
        else if(!strPassword.equals(strCpassword)){
            Toast.makeText(this,"Mật khẩu không trùng...", Toast.LENGTH_LONG).show();
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(strEmail,strPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Đăng nhập thành công", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this,DashboardUserActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }else {
                            Toast.makeText(getApplicationContext(),"Đăng nhập không thành công", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
//    //view binding
//    private ActivityRegisterBinding binding;
//    //chung thuc firebase
//    private FirebaseAuth firebaseAuth;
//    //progress dialog
//    private ProgressDialog progressDialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        //init firebase auth
//        firebaseAuth=FirebaseAuth.getInstance();
//
//        //setup progress dialog
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Hãy đợi ");
//        progressDialog.setCanceledOnTouchOutside(false);
//
//        //xu ly click va quay lai
//        binding.backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
//        //xu ly click va bat dau dang ky
//        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                validateDate();
//            }
//        });
//    }
//
//    private String name="", email="",password="";
//    private void validateDate(){
//        //truoc khi tao TK, thuc hien cac buoc xac thuc du lieu
//
//        //get data
//        name=binding.nameEt.getText().toString().trim();
//        email=binding.emailEt.getText().toString().trim();
//        password=binding.passwordEt.getText().toString().trim();
//        String cPassword = binding.CpasswordEt.getText().toString().trim();
//        //kiem tra o du lieu con trong hay khong
//        if(TextUtils.isEmpty(name)){
//            Toast.makeText(this,"Nhập tên của bạn...", Toast.LENGTH_LONG).show();
//        }
//        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            Toast.makeText(this,"Nhập email...", Toast.LENGTH_LONG).show();
//        }
//        else if(TextUtils.isEmpty(password)){
//            Toast.makeText(this,"Nhập mật khẩu...", Toast.LENGTH_LONG).show();
//        }
//        else if(TextUtils.isEmpty(cPassword)){
//            Toast.makeText(this,"Nhập lại mật khẩu...", Toast.LENGTH_LONG).show();
//        }
//        else if(!password.equals(cPassword)){
//            Toast.makeText(this,"Mật khẩu không trùng...", Toast.LENGTH_LONG).show();
//        }
//        else {
//            createUserAccount();
//        }
//    }
//
//    private void createUserAccount(){
//        //show progress
//        progressDialog.setMessage("tạo tài khoảng....");
//        progressDialog.show();
//
////        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
////            @Override
////            public void onComplete(@NonNull Task<AuthResult> task) {
////                if(task.isSuccessful()){
////                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công", Toast.LENGTH_LONG).show();
////                    Intent intent = new Intent(RegisterActivity.this,DashboardUserActivity.class);
////                    startActivity(intent);
////                }
////                else {
////                    Toast.makeText(getApplicationContext(),"Đăng nhập  thành công", Toast.LENGTH_LONG).show();
////                }
////            }
////        });
//
//        //tao TK trong firebase auth
//        firebaseAuth.createUserWithEmailAndPassword(email,password)
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        //tao tai khoan thanh cong va add them vao firebase realtime date
//                        updateUserInfo();
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure( Exception e) {
//                        //tao tai khoan that bai
//                        progressDialog.dismiss();
//                        Toast.makeText(RegisterActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                });
//    }
//    private void updateUserInfo(){
//        progressDialog.setMessage("Lưu thông tin người dùng....");
//        //timestamp
//        long timestamp = System.currentTimeMillis();
//
//        //get current user uid, since user is registered so we can get now
//        String uid=firebaseAuth.getUid();
//
//        //setup data chung bi add vao db
//        HashMap<String, Object> hashMap=new HashMap<>();
//        hashMap.put("uid",uid);
//        hashMap.put("email",email);
//        hashMap.put("name",name);
//        hashMap.put("profileImage", "");//add empty,lam them sau
//        hashMap.put("userType","user");//possible values are user, admin: will make admin manually in firebase realtime database by changing this value
//        hashMap.put("timestamp",timestamp);
//
//        //set data to db
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
//        ref.child(uid)
//                .setValue(hashMap)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        //data add to db
//                        progressDialog.dismiss();
//                        Toast.makeText(RegisterActivity.this,"Tạo tài khoản....",Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(RegisterActivity.this,DashboardUserActivity.class));
//                        finish();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(Exception e) {
//                        //data failed adding to db
//                        progressDialog.dismiss();
//                        Toast.makeText(RegisterActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                });
//    }
}