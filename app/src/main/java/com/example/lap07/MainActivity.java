package com.example.lap07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DataPerson dataPerson;
    EditText nameEdt;
    Button add_btn, remove_btn;
    DatabaseHandler databaseHandler;
    ListView lvName;
    ArrayList nameList;
    ArrayAdapter adapter;
    ArrayList idList;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataPerson = new DataPerson(this, "persondb.sqlite",null,1);

        idList = new ArrayList();
        nameList = new ArrayList();

        nameEdt = findViewById(R.id.edtName);
        add_btn = findViewById(R.id.Addbtn);
        remove_btn = findViewById(R.id.RemoveBtn);

        lvName = findViewById(R.id.name_lv);

        getNameList();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                nameList);
        lvName.setAdapter(adapter);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataPerson.addPerson(new Person(nameEdt.getText().toString()));
                getNameList();
                adapter.notifyDataSetChanged();
            }
        });
        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataPerson.removePerson((int)idList.get(index));
                getNameList();
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }
        });
        lvName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                index = i;
            }
        });

    }
    private ArrayList getNameList(){
        nameList.clear();
        idList.clear();

        for (Iterator iterator = dataPerson.getAll().iterator(); iterator.hasNext(); ) {
            Person person =  (Person) iterator.next();
             nameList.add(person.getName());
             idList.add(person.getId());
        }
        return nameList;
    }
}
