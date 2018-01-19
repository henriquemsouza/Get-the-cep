package com.ms.henrique.getcep.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.henrique.getcep.R;
import com.ms.henrique.getcep.helper.CityHelper;
import com.ms.henrique.getcep.model.cityRepo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.ms.henrique.getcep.helper.CityHelper.list;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        Button searchButton = (Button) findViewById(R.id.search_button);
        final EditText searchCEP = (EditText) findViewById(R.id.search_cep);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(searchCEP.getText())) {
                    Toast.makeText(getApplicationContext(), R.string.msgVazio, Toast.LENGTH_SHORT).show();
                } else {
                    BuscarCEP(searchCEP.getText().toString());
                    CityHelper.fetchCep(searchCEP.getText().toString(), new MainActivity());

                    //cityRepo list = list;

                    Log.i("WOW", String.valueOf(list));


                }
            }


        });
        //
    }
    //
    void BuscarCEP(String profile)
    {
        String requestUrl = "https://viacep.com.br/ws/" + profile+"/json/";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(requestUrl)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("getProfileInfo", "FAIL");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final TextView Urua = (TextView) findViewById(R.id.RuaID);
                final TextView Ubairro = (TextView) findViewById(R.id.BairroID);
                final TextView Ucep = (TextView) findViewById(R.id.CepID);
                final TextView Ucidade = (TextView) findViewById(R.id.CidadeID);
                final TextView Uestado = (TextView) findViewById(R.id.estadoID);
                final TextView Complem = (TextView) findViewById(R.id.ComplementoID);


                final String jsonData = response.body().string();
                Log.i("getProfileInfo", jsonData);
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CardView userCard = (CardView) findViewById(R.id.card_user_info);
                            try {
                                JSONObject rootObj = new JSONObject(jsonData);

                                if (rootObj != null) {

                                    Urua.setText("Rua:" + rootObj.getString("logradouro"));
                                    Ubairro.setText("Bairro:" + rootObj.getString("bairro"));
                                    Complem.setText(getText(R.string.Complet)+": "+rootObj.get("complemento").toString());
                                    Ucep.setText("Cep:" + rootObj.getString("cep"));
                                    Ucidade.setText("Cidade:" + rootObj.getString("localidade"));
                                    Uestado.setText("Estado:" + rootObj.getString("uf"));

                                    userCard.setVisibility(View.VISIBLE);
                                }

                            } catch (JSONException e) {
                                userCard.setVisibility(View.GONE);
                                Log.e("bad", e.getMessage());

                            }
                        }
                    });
                }
            }
        });

    }

    //

    void loadViews()
    {
        final TextView Urua = (TextView) findViewById(R.id.RuaID);
        final TextView Ubairro = (TextView) findViewById(R.id.BairroID);
        final TextView Ucep = (TextView) findViewById(R.id.CepID);
        final TextView Ucidade = (TextView) findViewById(R.id.CidadeID);
        final TextView Uestado = (TextView) findViewById(R.id.estadoID);
        final TextView Complem = (TextView) findViewById(R.id.ComplementoID);

        Log.i("WOW2", String.valueOf(list));
    }
    //

    //
}
