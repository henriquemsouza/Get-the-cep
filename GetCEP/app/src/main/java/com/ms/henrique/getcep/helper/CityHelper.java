package com.ms.henrique.getcep.helper;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ms.henrique.getcep.R;
import com.ms.henrique.getcep.model.cityRepo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by henrique on 18/01/18.
 */

public class CityHelper {
   public static cityRepo list  ;
    //
    public static void fetchCep(String cep, final Activity act)
    {

        String requestUrl = "https://viacep.com.br/ws/"+cep+"/json/";

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


                final String jsonData = response.body().string();
                Log.i("city", jsonData);
                if (response.isSuccessful()) {
                   act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          ;
                            try {
                                JSONObject rootObj = new JSONObject(jsonData);

                                if (rootObj != null) {

                                    String logradouro = rootObj.getString("logradouro");
                                    String bairro  = rootObj.getString("bairro");
                                    String complemento = rootObj.get("complemento").toString();
                                    String cep = rootObj.getString("cep");
                                    String localidade = rootObj.getString("localidade");
                                    String uf =rootObj.getString("uf");
                                    String unidade =rootObj.getString("unidade");
                                    String ibge =rootObj.getString("ibge");
                                    String gia =rootObj.getString("gia");

                                    cityRepo cityre = new cityRepo(cep, logradouro ,complemento ,bairro ,localidade ,uf , unidade ,ibge ,gia);
                                   // return cityre;
                                    list =  cityre;
                                    Log.i("cityre", String.valueOf(cityre.getBairro()));
                                }

                            } catch (JSONException e) {

                                Log.e("bad city", e.getMessage());
                            }
                        }
                    });
                }
            }
        });

    }


}
