package com.example.databasedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText edt_Name,edt_Number,edt_email;
    TextView Register_Lable;
    CardView card_insert,card_allshow;
    DatabaseHolder databaseHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        databaseHolder = new DatabaseHolder(this);
        find();
        click();

    }
    private void find() {
        edt_Name = findViewById(R.id.edt_Name);
        edt_email = findViewById(R.id.edt_email);
        edt_Number = findViewById(R.id.edt_Number);
        card_insert = findViewById(R.id.card_insert);
        card_allshow = findViewById(R.id.card_allshow);
    }

    private void click() {

        card_insert.setOnClickListener(new View.OnClickListener() {

            boolean inserted;
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    inserted = databaseHolder.insertData(edt_Name.getText().toString(),
                            edt_Number.getText().toString(),
                            edt_email.getText().toString()
                    );
                }

                if (inserted)
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });


        card_allshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Cursor res = databaseHolder.Viewalldata();
                StringBuffer buffer = new StringBuffer();
                if (res.getCount() == 0) {
                    // show message

                    showMessage("Error", "Nothing found");
                    return;
                }
                while (res.moveToNext())
                {
                    buffer.append("Id :"+res.getString(0)+"\n");
                    buffer.append("Name :"+res.getString(1)+"\n");
                    buffer.append("Email :"+res.getString(2)+"\n");
                    buffer.append("Password :"+res.getString(3)+"\n");
                }
                showMessage("Data",buffer.toString());*/
                startActivity(new Intent(MainActivity.this,ShowalldataActivity.class));
            }
        });
    }

    private void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }

}