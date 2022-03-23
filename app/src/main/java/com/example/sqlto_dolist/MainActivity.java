package com.example.sqlto_dolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database;
    ListView lvTask;
    ArrayList<Task> taskArrayList;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTask = findViewById(R.id.listviewTask);
        taskArrayList = new ArrayList<>();
        adapter = new TaskAdapter(this, R.layout.task_line, taskArrayList);
        lvTask.setAdapter(adapter);

        database = new Database(this, "To-do.sqlite", null , 1);

        //create table
        database.QueryData("CREATE TABLE IF NOT EXISTS Task(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TaskName VARCHAR(200))");
        //insert data
//        database.QueryData("INSERT INTO Task VALUES(null, 'Do Homework')");
//        database.QueryData("INSERT INTO Task VALUES(null, 'Have lunch')");
        // select data
        updateListView();
    }

    public void showEditDialog(String name, int id){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_task);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.40);
        dialog.getWindow().setLayout(width, height);

        EditText etName = dialog.findViewById(R.id.editTextTask);
        Button btnEdit = dialog.findViewById(R.id.buttonConfirm);
        Button btnCancel = dialog.findViewById(R.id.buttonCancel);

        etName.setText(name);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = etName.getText().toString().trim();
                database.QueryData("UPDATE Task SET TaskName = '"+ newName +"' WHERE Id = '"+ id +"'");
                Toast.makeText(MainActivity.this, "Updated successfully !", Toast.LENGTH_SHORT).show();
                updateListView();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showDialogDelete(String name, int id){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure you want to delete task " + name + " ?");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM Task WHERE Id = '"+ id +"'");
                Toast.makeText(MainActivity.this, "Deleted successfully !", Toast.LENGTH_SHORT).show();
                updateListView();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd){
            showAddDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateListView(){
        Cursor TaskData = database.GetData("SELECT * FROM Task");
        taskArrayList.clear();
        while (TaskData.moveToNext()){
            int id = TaskData.getInt(0);
            String name = TaskData.getString(1);
            taskArrayList.add(new Task(id, name));
        }
        adapter.notifyDataSetChanged();
    }

    private void showAddDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_task);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.40);
        dialog.getWindow().setLayout(width, height);

        EditText etName = (EditText) dialog.findViewById(R.id.editTextTask);
        Button btnAdd = (Button) dialog.findViewById(R.id.buttonConfirm);
        Button btnCancel = (Button) dialog.findViewById(R.id.buttonCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = etName.getText().toString();
                if(taskName.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter the task's name !", Toast.LENGTH_SHORT).show();
                }
                else{
                    database.QueryData("INSERT INTO Task VALUES(null, '"+ taskName +"')");
                    Toast.makeText(MainActivity.this, "Successfully added your task !", Toast.LENGTH_SHORT).show();
                    updateListView();
                    dialog.cancel();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}