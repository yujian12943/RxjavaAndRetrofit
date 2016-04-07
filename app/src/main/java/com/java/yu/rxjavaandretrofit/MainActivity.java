package com.java.yu.rxjavaandretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.click_but)
    Button click_but;
    @Bind(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        text.setText("BUTTER");
    }

    @OnClick(R.id.click_but)
    public void onClick(){
        getMovie();
    }

    public void  getMovie() {
        String baseUrl = "https://";
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieEntity> call = movieService.getNewData(0, 10);

        try {
            Response<MovieEntity> S=call.execute();//同步
        } catch (IOException e) {
            e.printStackTrace();
        }
        call.enqueue(new Callback<MovieEntity>() {//异步
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                text.setText(response.body().toString());

            }
            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                    text.setText(t.getMessage());
            }
        });
    }
}
