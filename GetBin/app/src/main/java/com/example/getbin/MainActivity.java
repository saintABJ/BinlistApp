package com.example.getbin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getbin.Response.BinInterface;
import com.example.getbin.Response.CardInfo;
import com.example.getbin.Response.GetRetrofitHandle;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
TextInputLayout Card_Num_Layout;
TextView Rst_TextView;
EditText txt_input;
ProgressDialog dialog;
Button Btn_SearchId;
String res="";
String strlen=" ";
int my_len=-1;
Boolean Isprepaid=false;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        dialog= new ProgressDialog(MainActivity.this);

        Btn_SearchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Card_Num_Layout.getEditText().getText().toString().isEmpty())
                {
                    txt_input.setError("Card Number Cannot be Empty");
                    return;
                }

                if (CheckInternet()==false) {
                    Toast.makeText(getApplicationContext(), "CHECK INTERNET PLS !!! ", Toast.LENGTH_SHORT).show();
                    return;
                }

                res= Card_Num_Layout.getEditText().getText().toString().trim();
                String res_url="https://lookup.binlist.net/" + res;
                dialog.setTitle("GETTING INFO");
                dialog.setMessage("PLS WAIT");
                dialog.setCancelable(false);
                dialog.show();

                GetInfo(res_url);





            }
        });

    }



    private  Boolean CheckInternet()
    {

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }


    private  void init()
    {
        Card_Num_Layout= (TextInputLayout)findViewById(R.id.cardnum);
        Rst_TextView=(TextView) findViewById(R.id.txtresult);
        Btn_SearchId=(Button)findViewById(R.id.getinfo);
        txt_input=(EditText)findViewById(R.id.input);
    }

    private void GetInfo(String url)
    {

        Retrofit retrofit=null;
          retrofit=  GetRetrofitHandle.GetRetrofitHandle();
           BinInterface binInterface=null;
        binInterface=  retrofit.create(BinInterface.class);
            Call<CardInfo> call= binInterface.getCardInfo(url);
            call.enqueue(new Callback<CardInfo>() {
                @Override
                public void onResponse(Call<CardInfo> call, Response<CardInfo> response) {
                   dialog.dismiss();
                    if(response.isSuccessful())
                    {

                        if (String.valueOf(response.body().getNumber().getLength()).isEmpty()==true)
                        {
                            strlen="Unknown";
                        }
                    if (String.valueOf(response.body().getPrepaid()).isEmpty()==true)
                    {
                        Isprepaid=false;
                    }
//                        if(my_len==-1)
//                        {
//                            strlen="Unknown";
//                        }
                                Rst_TextView.setText(
                           response.body().getScheme() + "\n" +
                           response.body().getType() + "\n" +
                           response.body().getBank().getName() + "\n" +
                           response.body().getCountry().getName() + "\n" +
                            strlen + "\n" + String.valueOf(Isprepaid)

                                                   //  response.body().getPrepaid().toString()
                        );

                      //  Toast.makeText(getApplicationContext(), String.valueOf(response.body().getBank().getName()), Toast.LENGTH_SHORT).show();


                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(), String.valueOf(response.errorBody()), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<CardInfo> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }



    }
