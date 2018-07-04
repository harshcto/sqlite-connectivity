package com.example.codehead.sqlite_example;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper myDB;
    private EditText mnameEditText,msurnameEditText,mmarksEditText,mIdEditText;
    private Button maddButton,mviewButton,mUpdateButton,mdeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB=new DatabaseHelper(this); //creating database

        //initializing variables
        mnameEditText=findViewById(R.id.name);
        msurnameEditText=findViewById(R.id.surname);
        mmarksEditText=findViewById(R.id.marks);
        maddButton=findViewById(R.id.addbutton);
        mviewButton=findViewById(R.id.viewbutton);
        mIdEditText=findViewById(R.id.Id);
        mUpdateButton=findViewById(R.id.updatebutton);
        mdeleteButton=findViewById(R.id.deletebutton);

        addData();
        viewData();
        updateData();
        datadeletion();
    }

    private void addData(){
        //adding data on button click
        maddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isInserted;
                String name=mnameEditText.getText().toString();   //getting data from editText
                String surname=msurnameEditText.getText().toString();
                String marks=mmarksEditText.getText().toString();
                String id=mIdEditText.getText().toString();

                isInserted=myDB.insertData(name,surname,marks,id); //adding data to table

                //response to data insertion and failure
                if(isInserted)
                    Toast.makeText(MainActivity.this,"Data inserted successfully",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this,"Data insertion not successful",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void viewData(){
        mviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=myDB.getData();
                StringBuilder buffer=new StringBuilder();

                if(cursor.getCount()==0) {
                  showMessage("Error","No data available");
                  return;
                }
                while (cursor.moveToNext()){
                    buffer.append("ID: ").append(cursor.getString(0)).append("\n");
                    buffer.append("Name: ").append(cursor.getString(1)).append("\n");
                    buffer.append("Surname: ").append(cursor.getString(2)).append("\n");
                    buffer.append("Marks: ").append(cursor.getString(3)).append("\n\n");
                }

                //show data using alert dialog
                showMessage("Data",buffer.toString());
            }
        });
    }
    private void showMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateData(){
                mUpdateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated;
                        String name=mnameEditText.getText().toString();   //getting data from editText
                        String surname=mnameEditText.getText().toString();
                        String marks=mmarksEditText.getText().toString();
                        String id=mIdEditText.getText().toString();

                        isUpdated=myDB.updateData(name,surname,marks,id); //adding data to table

                        //response to data insertion and failure
                        if(isUpdated)
                            Toast.makeText(MainActivity.this,"Data updated successfully",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this,"Operation failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void datadeletion(){
        mdeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedrows=myDB.deleteData(mIdEditText.getText().toString());
                if(deletedrows>0)
                    Toast.makeText(MainActivity.this,"Data deleted successfully",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this,"Operation failed",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
