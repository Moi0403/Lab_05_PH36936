package fpoly.anhntph36936.adr_lab_05;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Title_ADT extends RecyclerView.Adapter<Title_ADT.ViewHolder> {
    private Context context;
    private List<Title> list;

    public Title_ADT(Context context, List<Title> list){
        this.context = context;
        this.list = list;
    }
    public void setData(List<Title> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Title title = list.get(position);
        holder.tv_title.setText("Title: "+title.getName());

        holder.imv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showXoa(position);
            }
        });

        holder.imv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSua(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title, tv_stt;
        ImageView imv_edit, imv_del;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            imv_edit = itemView.findViewById(R.id.imv_edit);
            imv_del = itemView.findViewById(R.id.imv_del);
        }
    }
    private void showSua(int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog alertDialog= builder.create();


        EditText edt_sua_tt = view.findViewById(R.id.edt_sua_tt);
        edt_sua_tt.setText(list.get(i).getName());

        view.findViewById(R.id.btn_thoat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.btn_sua).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = list.get(i).get_id();
                String tt = edt_sua_tt.getText().toString();
                Title title = new Title();
                title.setName(tt);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIService.DOMAIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // get data
                APIService apiService = retrofit.create(APIService.class);
                Call<List<Title>> call = apiService.upData(id, title);
                call.enqueue(new Callback<List<Title>>() {
                    @Override
                    public void onResponse(Call<List<Title>> call, Response<List<Title>> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            LoadData();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Title>> call, Throwable t) {
                        Log.e("Update", t.getMessage());
                    }
                });
            }
        });
        alertDialog.show();
    }
    private void showXoa(int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn muốn xóa không ?");
        builder.setCancelable(false);


        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String id = list.get(i).get_id();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIService.DOMAIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // get data
                APIService apiService = retrofit.create(APIService.class);
                Call<List<Title>> call = apiService.delData(id);

                call.enqueue(new Callback<List<Title>>() {
                    @Override
                    public void onResponse(Call<List<Title>> call, Response<List<Title>> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(context, "Xóa Thành công", Toast.LENGTH_SHORT).show();
                            LoadData();
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Title>> call, Throwable t) {
                    }
                });
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
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
                    setData(titles);
                }
            }

            @Override
            public void onFailure(Call<List<Title>> call, Throwable t) {

            }
        });
    }
}
