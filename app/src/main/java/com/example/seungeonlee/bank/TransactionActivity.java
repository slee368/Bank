package com.example.seungeonlee.bank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Stack;

public class TransactionActivity extends AppCompatActivity {

    private TextView balance;
    private static int cmoney;
    private SharedPreferences myPrefs;

    private int currTrans;
    private TextView transaction;

    private Stack<Integer> transLog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Context context = getApplicationContext();  // app level storage
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        cmoney = myPrefs.getInt("currentBalance", 0);
        dispBalance();

        currTrans = 0;
        dispTrans();

        transLog = new Stack<>();
    }

    public void clickedPenny(View view){
        addToUndoStack(1);
        addTrans(1);
    }

    public void clickedNickel(View view){
        addToUndoStack(5);
        addTrans(5);
    }

    public void clickedDime(View view){
        addToUndoStack(10);
        addTrans(10);
    }

    public void clickedQuat(View view){
        addToUndoStack(25);
        addTrans(25);
    }

    public void clickedOne(View view){
        addToUndoStack(100);
        addTrans(100);
    }

    public void clickedFive(View view){
        addToUndoStack(500);
        addTrans(500);
    }

    public void clickedTen(View view){
        addToUndoStack(1000);
        addTrans(1000);
    }

    public void clickedTwenty(View view){
        addToUndoStack(2000);
        addTrans(2000);
    }

    protected void addToUndoStack(int n){
        transLog.push((-1) * n);
    }

    public void clickedDeposit(View view){
        if (currTrans != 0) {
            cmoney = cmoney + currTrans;
            currTrans = 0;

            dispTrans();
            dispBalance();
            saveBalance();
        }
        finish();
    }

    public void clickedWithdrawal(View view){
        if (currTrans != 0) {
            cmoney = cmoney - currTrans;
            if (cmoney < 0) {
                cmoney = 0;
            }
            currTrans = 0;

            dispTrans();
            dispBalance();
            saveBalance();
        }
        finish();
    }

    public void clickedUndo(View view){
        if (!transLog.isEmpty()) {
            int temp = transLog.pop();
            addTrans(temp);
            dispTrans();
        }
    }

    protected void addTrans(int add){
        currTrans = currTrans + add;
        dispTrans();
    }

    protected void dispTrans(){
        double transDec = ((double) currTrans) / 100.00;
        transaction = (TextView) findViewById(R.id.currtrans);
        transaction.setText(String.format("$ %.2f", transDec));
    }

    protected void dispBalance(){
        double balanceDec = ((double) cmoney) / 100.00;
        balance = (TextView) findViewById(R.id.mAmount);
        balance.setText(String.format("$ %.2f", balanceDec));
    }

    protected void saveBalance(){
        Context context = getApplicationContext();  // app level storage
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor peditor = myPrefs.edit();

        peditor.putInt("currentBalance", cmoney);
        peditor.commit();
    }


}
