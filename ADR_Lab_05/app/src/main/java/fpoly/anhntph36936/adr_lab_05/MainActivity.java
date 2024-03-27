package fpoly.anhntph36936.adr_lab_05;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView rc_view;
    Title_ADT titleAdt;
    List<Title> list;
    EditText edt_tim;
    ImageView imv_tim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(MainActivity.this);
        setContentView(R.layout.activity_main);
        rc_view = findViewById(R.id.rc_view);
        edt_tim = findViewById(R.id.edt_timkiem);
        imv_tim = findViewById(R.id.imv_tim);


        list = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // get data
        APIService apiService = retrofit.create(APIService.class);
        Call<List<Title>> call = apiService.getData();
        call.enqueue(new Callback<List<Title>>() {
            @Override
            public void onResponse(Call<List<Title>> call, Response<List<Title>> response) {
                if (response.isSuccessful()){
                    list = response.body();
                    titleAdt = new Title_ADT(MainActivity.this, list);
                    rc_view.setAdapter(titleAdt);
                    rc_view.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<Title>> call, Throwable t) {

            }
        });

        findViewById(R.id.fab_them).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdd();
            }
        });

        imv_tim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tim = edt_tim.getText().toString();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIService.DOMAIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIService apiService = retrofit.create(APIService.class);
                Call<List<Title>> call = apiService.timData(tim);
                call.enqueue(new Callback<List<Title>>() {
                    @Override
                    public void onResponse(Call<List<Title>> call, Response<List<Title>> response) {
                        if (response.isSuccessful()){
                            list = response.body();
                            LoadData();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Title>> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void showAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        EditText edt_tt = view.findViewById(R.id.edt_tt);

        view.findViewById(R.id.btn_them).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edt_tt.getText().toString();
                if (title.equals("")){
                    Toast.makeText(MainActivity.this, "Nhập thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Title title1 = new Title();
                    title1.setName(title);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(APIService.DOMAIN)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    // get data
                    APIService apiService = retrofit.create(APIService.class);
                    Call<List<Title>> call = apiService.addData(title1);
                    call.enqueue(new Callback<List<Title>>() {
                        @Override
                        public void onResponse(Call<List<Title>> call, Response<List<Title>> response) {
                            if (response.isSuccessful()){
                                list = response.body();
                                Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                LoadData();
                                alertDialog.dismiss();
                            } else {
                                Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Title>> call, Throwable t) {
                            Log.e("Main", t.getMessage());
                        }
                    });
                }


            }
        });

        view.findViewById(R.id.btn_huy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void LoadData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // get data
        APIService apiService = retrofit.create(APIService.class);
        Call<List<Title>> call = apiService.getData();
        call.enqueue(new Callback<List<Title>>() {
            @Override
            public void onResponse(Call<List<Title>> call, Response<List<Title>> response) {
                if (response.isSuccessful()){
                    List<Title> titles = response.body();
                    titleAdt.setData(titles);
                }
            }

            @Override
            public void onFailure(Call<List<Title>> call, Throwable t) {

            }
        });
    }
}