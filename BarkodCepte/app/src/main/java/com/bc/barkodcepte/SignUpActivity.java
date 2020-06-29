package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    String url = "http://ceptebarkod.tk/kaydol.php";
    EditText edt_SignUp_Email,edt_SignUp_Un,edt_SignUp_Pass;
    Button btn_SignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //FULL SCREEN CODES ------------------------------------------------------------------------
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //------------------------------------------------------------------------------------------

        edt_SignUp_Email = findViewById(R.id.edt_SignUp_Email);
        edt_SignUp_Un = findViewById(R.id.edt_SignUp_Un);
        edt_SignUp_Pass = findViewById(R.id.edt_SignUp_Pass);
        btn_SignUp = findViewById(R.id.btn_SignUp);

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

    }
    private void SignUp(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> deger = new HashMap<>();

               deger.put("userName", edt_SignUp_Un.getText().toString());
               deger.put("email", edt_SignUp_Email.getText().toString());
               deger.put("pass", edt_SignUp_Pass.getText().toString());
                Log.d("msj","gitti");
                return deger;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequest(stringRequest);

    }
}