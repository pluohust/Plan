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

public class set_life extends AppCompatActivity {
    Button life_return;
    Button life_store;
    EditText life_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_life);
        ActivityCollector.addActivity(this);
        life_return = (Button) findViewById(R.id.life_return);
        life_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(set_life.this, MainActivity.class);
                startActivityForResult(intent,0);
            }
        });
        life_edit = (EditText) findViewById(R.id.edit_life);
        setEditTxt();
        life_store = (Button) findViewById(R.id.life_store);
        life_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] lifePlanTxts = life_edit.getText().toString().split("\n");
                List<ListInformation> tmplist = new ArrayList<>();
                for(String eachline : lifePlanTxts) {
                    String realline = eachline.trim();
                    if (realline.length() > 0) {
                        ListInformation information = new ListInformation();
                        information.setPlan(realline);
                        information.setChecked(isChecked(lifeList, realline));
                        information.setStyle(0);
                        tmplist.add(information);
                    }
                }
                lifeList.clear();
                lifeList.addAll(tmplist);
                planList.clear();
                planList.addAll(lifeList);
                planList.addAll(threeyearList);
                planList.addAll(everydayList);
                writePlan();
                Intent intent = new Intent(set_life.this, MainActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    private void setEditTxt() {
        String string = new String();
        for(ListInformation listInformation : lifeList) {
            string = string + listInformation.getPlan() + "\n";
        }
        life_edit.setText(string);
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
