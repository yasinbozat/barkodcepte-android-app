package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_Un, edt_Pass;
    private Button btn_Login, btn_Kaydol;
    String url = "http://ceptebarkod.tk/giris.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //FULL SCREEN CODES ------------------------------------------------------------------------
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //------------------------------------------------------------------------------------------

        //--Değişkenler--//
        btn_Login = findViewById(R.id.btn_Login);
        btn_Kaydol = findViewById(R.id.btn_Kaydol);
        edt_Un = findViewById(R.id.edt_Un);
        edt_Pass = findViewById(R.id.edt_Pass);
        //--------------//


        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignIn();



            }
        });
        btn_Kaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);

            }
        });


    }
    private void SignIn(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("resp",""+response);
                        int donendeger = Integer.parseInt(response);
                        if(donendeger == 1){

                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);

                        }else if(donendeger == 0){

                            Toast.makeText(getApplicationContext(),""+donendeger,Toast.LENGTH_LONG).show();

                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("err",""+error);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> deger = new HashMap<>();

                deger.put("userName", edt_Un.getText().toString());
                deger.put("pass", edt_Pass.getText().toString());
                return deger;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequest(stringRequest);

    }
}
