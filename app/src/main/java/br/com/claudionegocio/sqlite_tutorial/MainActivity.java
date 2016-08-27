package br.com.claudionegocio.sqlite_tutorial;

import android.database.Cursor;
import android.inputmethodservice.KeyboardView;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editName, editSurname, editMarks, editID;
    Button btnAddData, btnview, btnupdate, btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_Name);
        editSurname = (EditText) findViewById(R.id.editText_Surname);
        editMarks = (EditText) findViewById(R.id.editText_Marks);
        editID = (EditText) findViewById(R.id.editText_id);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnview = (Button) findViewById(R.id.button_View);
        btnupdate= (Button) findViewById(R.id.button_update);
        btnDelete= (Button) findViewById(R.id.button_delete);

        AddData();
        viewAll();
        UpdateData();
        DeleteData();

    }


    public void AddData(){
        btnAddData.setOnClickListener(

                new View.OnClickListener(){

                    public void onClick(View v){
                        boolean isInserted = myDB.insertData(editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString());

                        if (isInserted)
                            Toast.makeText(MainActivity.this,"Dados Inseridos",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Dados Não Foram Inseridos",Toast.LENGTH_LONG).show();
                    }

                }

        );
    }


    public void UpdateData(){
        btnupdate .setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer isUpdate = myDB.updateData(editID.getText().toString(),editName.getText().toString(),
                                                    editSurname.getText().toString(),editMarks.getText().toString());
                                if (isUpdate>0)
                                    Toast.makeText(MainActivity.this,"Dados Atualizados",Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(MainActivity.this,"Dados Não Foram Atualizados",Toast.LENGTH_LONG).show();
                    }
                }


        );
    }



    public void viewAll(){
        btnview.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Cursor res  = myDB.getAllData();
                        if(res.getCount() == 0){
                            showMessage("Erro","Sem dados");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("id: "+ res.getString(0)+"\n");
                            buffer.append("Name: "+ res.getString(1)+"\n");
                            buffer.append("Surname: "+ res.getString(2)+"\n");
                            buffer.append("Marks: "+ res.getString(3)+"\n\n");
                        }

                        //show all data
                        showMessage("Data",buffer.toString());
                    }
                }

        );


    }


    public void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Integer deletedRows = myDB.deleteData(editID.getText().toString());
                        if (deletedRows > 0) Toast.makeText(MainActivity.this,"Dados Apagados",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Dados Não Foram Apagados",Toast.LENGTH_LONG).show();

                    }
                }

        );
    }


    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();



    }


}
