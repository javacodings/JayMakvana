package com.example.databasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowalldataActivity extends AppCompatActivity {

    RecyclerView recycle;
    ArrayList<RegisterModel> arrayList = new ArrayList<RegisterModel>();
    DatabaseHolder databaseHolder;
    //Button delete_data;
    public static SwipeRefreshLayout swipeToRefresh;
    Cursor result;
    public AdapterRecycle ar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showalldata);
        databaseHolder = new DatabaseHolder(this);
        recycle = (RecyclerView) findViewById(R.id.recycle);
        swipeToRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        //  delete_data = (Button) findViewById(R.id.delete_data);

        result = databaseHolder.Viewalldata();

        getdata();

        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
                swipeToRefresh.setRefreshing(false);
            }
        });

    }

    public void getdata() {

        while (result.moveToNext()) {
            arrayList.add(new RegisterModel(String.valueOf(result.getString((0))),
                    String.valueOf(result.getString((1))),
                    String.valueOf(result.getString((2))),
                    String.valueOf(result.getString((3)))));
        }
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycle.setLayoutManager(lm);
        ar = new AdapterRecycle(this, arrayList);
        recycle.setAdapter(ar);


    }

    public class AdapterRecycle extends RecyclerView.Adapter<AdapterRecycle.MyHolder> {
        Context context;
        ArrayList<RegisterModel> arrayList;
        DatabaseHolder databaseHolder;
        String result, result1, result2, result3;
        AlertDialog alertDialog;

        public AdapterRecycle(Context context, ArrayList<RegisterModel> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            databaseHolder = new DatabaseHolder(context);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
            MyHolder myHolder = new MyHolder(view);
            return myHolder;
        }

        public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
            holder.txt_id.setText(arrayList.get(position).getId());
            holder.txt_name.setText(arrayList.get(position).getName());
            holder.txt_number.setText(arrayList.get(position).getNumber());
            holder.txt_email.setText(arrayList.get(position).getEmail());

            holder.btn_Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Update data" + arrayList.get(position).id, Toast.LENGTH_SHORT).show();
                    result = holder.txt_id.getText().toString();
                    result1 = holder.txt_name.getText().toString();
                    result2 = holder.txt_number.getText().toString();
                    result3 = holder.txt_email.getText().toString();

                    updatedialog(position);


                }
            });


            holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder message = new AlertDialog.Builder(context);
                    message.setMessage("You are sure !");
                    message.setCancelable(false);
                    message.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int datadelete = databaseHolder.deletedata(arrayList.get(position).id);
                            notifyDataSetChanged();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    message.show();

                /*Toast.makeText(context, "Delete data" + arrayList.get(position).id, Toast.LENGTH_SHORT).show();
                int datadelete = databaseHolder.deletedata(arrayList.get(position).id);*/

                }
            });


        }

        @Override
        public int getItemCount() {
            return arrayList.size();

        }

        public class MyHolder extends RecyclerView.ViewHolder {
            TextView txt_name, txt_number, txt_email, txt_id;
            TextView btn_Update, btn_delete;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                txt_id = (TextView) itemView.findViewById(R.id.txt_id);
                txt_name = (TextView) itemView.findViewById(R.id.txt_name);
                txt_email = (TextView) itemView.findViewById(R.id.txt_email);
                txt_number = (TextView) itemView.findViewById(R.id.txt_number);
                btn_Update = (TextView) itemView.findViewById(R.id.btn_Update);
                btn_delete = (TextView) itemView.findViewById(R.id.btn_delete);
            }
        }


        public void updatedialog(final int position) {
            final Dialog builder;
            final EditText edt_id, edt_name, edt_email, edt_number;
            TextView btn_submit, btn_cencle;


            LayoutInflater li = LayoutInflater.from(context);
            View custom = li.inflate(R.layout.custom_alert, null);
            builder = new Dialog(context);
            builder.setContentView(custom);
            builder.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            edt_id = (EditText) custom.findViewById(R.id.edt_id);
            edt_name = (EditText) custom.findViewById(R.id.edt_name);
            edt_email = (EditText) custom.findViewById(R.id.edt_email);
            edt_number = (EditText) custom.findViewById(R.id.edt_number);
            btn_submit = (TextView) custom.findViewById(R.id.btn_submit);
            btn_cencle = (TextView) custom.findViewById(R.id.btn_cencle);

            edt_id.setText(result);
            edt_name.setText(result1);
            edt_number.setText(result2);
            edt_email.setText(result3);

            btn_submit.setOnClickListener(new View.OnClickListener() {
                boolean updated;

                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Update Data :", Toast.LENGTH_SHORT).show();
                    updated = databaseHolder.updatedata(edt_id.getText().toString(),
                            edt_name.getText().toString(),
                            edt_number.getText().toString(),
                            edt_email.getText().toString());

                    if (updated) {
                        Toast.makeText(context, "Data Update", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        builder.dismiss();
                    } else {

                        Toast.makeText(context, "Data Not Update", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            btn_cencle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.dismiss();
                }
            });
            builder.show();
        }
    }

}
