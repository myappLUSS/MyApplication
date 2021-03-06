package com.artqueen.logicuniversitystationerysystem.Employee.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.artqueen.logicuniversitystationerysystem.Employee.Data.Items;
import com.artqueen.logicuniversitystationerysystem.Employee.ListAdapters.MyAdapter;
import com.artqueen.logicuniversitystationerysystem.R;

import java.util.ArrayList;
import java.util.List;


public class MakeRequest extends ActionBarActivity   {
    EditText searchEt;
    Button searchBtn,checkoutBtn;
    public static List<Items> cart=new ArrayList<Items>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);
        searchEt = (EditText)findViewById(R.id.searchET);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        checkoutBtn = (Button) findViewById(R.id.checkoutBtn);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UpdateCart.flag!=1) {
                    if (cart.isEmpty()) {
                        Toast.makeText(MakeRequest.this, "No Items in Cart, Please add items to Proceed", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(MakeRequest.this, Cart.class);
                        startActivity(i);

                    }
                }else{
                    Intent i = new Intent(MakeRequest.this, UpdateCart.class);
                    startActivity(i);
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                String search = searchEt.getText().toString();
                StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
                List<Items> list = Items.list(search);
                if(list.isEmpty())
                {
                    new AlertDialog.Builder(MakeRequest.this)
                            .setTitle("Oops, No such Item!")
                            .setMessage("Please Try Again or View Catalog Below")
                            .setPositiveButton("Ok, Got it!", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setNegativeButton("View Catalogue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {
                    MyAdapter adapter = new MyAdapter(list, MakeRequest.this);
                    ListView lView = (ListView)findViewById(R.id.searchLV);
                    lView.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_make_request, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
