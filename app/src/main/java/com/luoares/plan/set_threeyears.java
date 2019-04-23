package com.luoares.plan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import static com.luoares.plan.MainActivity.planList;
import static com.luoares.plan.MainActivity.lifeList;
import static com.luoares.plan.MainActivity.everydayList;
import static com.luoares.plan.MainActivity.threeyearList;
import static com.luoares.plan.MainActivity.writePlan;

public class set_threeyears extends AppCompatActivity {
    Button threeyears_return;
    Button threeyears_store;
    EditText threeyears_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_threeyears);
        ActivityCollector.addActivity(this);
        threeyears_return = (Button) findViewById(R.id.threeyears_return);
        threeyears_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(set_threeyears.this, MainActivity.class);
                startActivityForResult(intent,0);
            }
        });
        threeyears_edit = (EditText) findViewById(R.id.edit_threeyears);
        setEditTxt();
        threeyears_store = (Button) findViewById(R.id.threeyears_store);
        threeyears_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] threeyearsPlanTxts = threeyears_edit.getText().toString().split("\n");
                List<ListInformation> tmplist = new ArrayList<>();
                for(String eachline : threeyearsPlanTxts) {
                    String realline = eachline.trim();
                    if (realline.length() > 0) {
                        ListInformation information = new ListInformation();
                        information.setPlan(realline);
                        information.setChecked(isChecked(threeyearList, realline));
                        information.setStyle(1);
                        tmplist.add(information);
                    }
                }
                threeyearList.clear();
                threeyearList.addAll(tmplist);
                planList.clear();
                planList.addAll(lifeList);
                planList.addAll(threeyearList);
                planList.addAll(everydayList);
                writePlan();
                Intent intent = new Intent(set_threeyears.this, MainActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    private void setEditTxt() {
        String string = new String();
        for(ListInformation listInformation : threeyearList) {
            string = string + listInformation.getPlan() + "\n";
        }
        threeyears_edit.setText(string);
    }

    public boolean isChecked(List<ListInformation> list, String plan) {
        for (ListInformation information : list) {
            if(information.getPlan().equals(plan.trim())) {
                return information.getChecked();
            }
        }
        return false;
    }
}
