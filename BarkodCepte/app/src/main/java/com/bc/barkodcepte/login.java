package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class login extends AppCompatActivity {

    private Button btn_Login;
    Button login,m_kayıtol;
    EditText username, password;
    ProgressBar progressBar;
    // End Declaring layout button, edit texts

    // Declaring connection variables
    Connection con;
    String un, pass, db, ip;
    String usernam, passwordd;
    //End Declaring connection variables
    @Override
    protected void onCreate(Bundle savedInstanceState){
        // Declaring layout button, edit texts
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Getting values from button, texts and progress bar
            login = (Button) findViewById(R.id.button);
            m_kayıtol = (Button) findViewById(R.id.m_kayıtol);
            username = (EditText) findViewById(R.id.editText);
            password = (EditText) findViewById(R.id.editText2);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            // End Getting values from button, texts and progress bar

            // Declaring Server ip, username, database name and password
            ip = "sql5005.site4now.net";
            db = "DB_A5E336_kullanici_admin";
            un = "DB_A5E336_kullanici_admin";
            pass = "bjkbjkbjk3838";
            // Declaring Server ip, username, database name and password


            // Setting up the function when button login is clicked
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    usernam = username.getText().toString();
                    passwordd = password.getText().toString();
                    CheckLogin checkLogin = new CheckLogin();// this is the Asynctask, which is used to process in background to reduce load on app process
                    checkLogin.execute("");
                }
            });
            //End Setting up the function when button login is clicked
            m_kayıtol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(login.this,kayitol.class);
                    startActivity(i);
                }
            });
        }

        public class CheckLogin extends AsyncTask<String, String, String> {
            String z = "";
            Boolean isSuccess = false;

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String r) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(login.this, r, Toast.LENGTH_SHORT).show();
                if (isSuccess) {

                    //finish();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                if (usernam.trim().equals(""))
                    z = "Please enter Username and Password";
                else {
                    try {
                        con = connectionclass(un, pass, db, ip);        // Connect to database
                        if (con == null) {
                            z = "Check Your Internet Access!";
                        } else {
                            String query = "select * from kullanici where kullaniciadi= '" + usernam.toString() + "' and sifre = '" + passwordd.toString() + "' ";

                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery(query);

                            if (rs.next()) {
                                z = "Login successful";
                                isSuccess = true;
                                con.close();
                                Intent i = new Intent(login.this,anasayfa.class);
                                startActivity(i);
                            } else {
                                z = "Invalid Credentials!";
                                isSuccess = false;
                            }
                            Log.d("asdasdasddfasdasdas",rs.toString()+" ");
                        }
                    } catch (Exception ex) {
                        isSuccess = false;
                        z = ex.getMessage();
                    }
                }
                return z;
            }
        }


        @SuppressLint("NewApi")
        public Connection connectionclass(String user, String password, String database, String server) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Connection connection = null;
            String ConnectionURL = null;
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                ConnectionURL = "jdbc:jtds:sqlserver://sql5005.site4now.net;database=DB_A5E336_kullanici_admin;user=DB_A5E336_kullanici_admin;password=bjkbjkbjk3838";
//            ConnectionURL = "jdbc:jtds:sqlserver://192.168.1.9;database=msss;instance=SQLEXPRESS;Network Protocol=NamedPipes" ;


                connection = DriverManager.getConnection(ConnectionURL);
            } catch (SQLException se) {
                Log.e("error here 1 : ", se.getMessage());
            } catch (ClassNotFoundException e) {
                Log.e("error here 2 : ", e.getMessage());
            } catch (Exception e) {
                Log.e("error here 3 : ", e.getMessage());
            }
            return connection;
        }
    }