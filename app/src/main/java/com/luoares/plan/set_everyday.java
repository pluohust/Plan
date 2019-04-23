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

public class set_everyday extends AppCompatActivity {
    Button everyday_return;
    Button everyday_store;
    EditText everyday_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_everyday);
        ActivityCollector.addActivity(this);
        everyday_return = (Button) findViewById(R.id.everyday_return);
        everyday_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(set_everyday.this, MainActivity.class);
                startActivityForResult(intent,0);
            }
        });
        everyday_edit = (EditText) findViewById(R.id.edit_everyday);
        setEditTxt();
        everyday_store = (Button) findViewById(R.id.everyday_store);
        everyday_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] everydayPlanTxts = everyday_edit.getText().toString().split("\n");
                List<ListInformation> tmplist = new ArrayList<>();
                for(String eachline : everydayPlanTxts) {
                    String realline = eachline.trim();
                    if (realline.length() > 0) {
                        ListInformation information = new ListInformation();
                        information.setPlan(realline);
                        information.setChecked(isChecked(everydayList, realline));
                        information.setStyle(2);
                        tmplist.add(information);
                    }
                }
                everydayList.clear();
                everydayList.addAll(tmplist);
                planList.clear();
                planList.addAll(lifeList);
                planList.addAll(threeyearList);
                planList.addAll(everydayList);
                writePlan();
                Intent intent = new Intent(set_everyday.this, MainActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    private void setEditTxt() {
        String string = new String();
        for(ListInformation listInformation : everydayList) {
            string = string + listInformation.getPlan() + "\n";
        }
        everyday_edit.setText(string);
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
