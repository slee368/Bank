package com.example.seungeonlee.bank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity"; //
    private static int cmoney;
    private FloatingActionButton fab;
    private TextView balance;
    private SharedPreferences myPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, "onCreate was called");
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTransaction();
            }
        });


        Context context = getApplicationContext();  // app level storage
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor peditor = myPrefs.edit();
        peditor.putInt("currentBalance", 0);
        peditor.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println(myPrefs.getInt("currentBalance", 1337));
        cmoney = myPrefs.getInt("currentBalance", 0);

        double balanceDec = ((double) cmoney) / 100.00;

        balance = (TextView) findViewById(R.id.mAmount);
        balance.setText(String.format("$ %.2f", balanceDec));
    }

    @Override
    public void onRestart() {
        super.onRestart();
        System.out.println(myPrefs.getInt("currentBalance", 1337));
        cmoney = myPrefs.getInt("currentBalance", 0);

        double balanceDec = ((double) cmoney) / 100.00;

        balance = (TextView) findViewById(R.id.mAmount);
        balance.setText(String.format("$ %.2f", balanceDec));
    }

    @Override
    protected void onResume() {
        /**
        cmoney = myPrefs.getInt("currentBalance", 0);

        double balanceDec = ((double) cmoney) / 100.00;

        balance = (TextView) findViewById(R.id.mAmount);
        balance.setText(String.format("$ %.2f", balanceDec));
         **/
        super.onResume();
    }

    @Override
    protected void onPause() {

        SharedPreferences.Editor peditor = myPrefs.edit();
        peditor.putInt("currentBalance", cmoney);
        peditor.commit();

        super.onPause();
    }

    @Override
    protected void onStop() {
        SharedPreferences.Editor peditor = myPrefs.edit();
        peditor.putInt("currentBalance", cmoney);
        peditor.commit();

        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void addTransaction(){
        Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
        intent.putExtra("currentBalance", cmoney);
        startActivity(intent);
    }
}
