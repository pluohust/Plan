package com.luoares.plan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    public static List<ListInformation> planList;
    private Button setLife;
    private Button setThreeyear;
    private Button setEveryday;

    public static String dayDate;
    public static List<ListInformation> lifeList;
    public static List<ListInformation> threeyearList;
    public static List<ListInformation> everydayList;

    public static final String planDir = Environment.getExternalStorageDirectory() + "/plan/";
    public static final String planFile =
            Environment.getExternalStorageDirectory() + "/plan/plan.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applyWritePermission();
        ActivityCollector.addActivity(this);
        if (null == planList) { initPlan(); }
        final PlanAdapter planAdapter = new PlanAdapter(MainActivity.this, R.layout.plan_list, planList);
        listView = (ListView) findViewById(R.id.main_list);
        listView.setAdapter(planAdapter);
        planAdapter.notifyDataSetChanged();

        setLife = (Button) findViewById(R.id.plan_life);
        setLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, set_life.class);
                startActivity(intent);
            }
        });
        setThreeyear = (Button) findViewById(R.id.plan_threeyears);
        setThreeyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, set_threeyears.class);
                startActivity(intent);
            }
        });
        setEveryday = (Button) findViewById(R.id.plan_everyday);
        setEveryday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, set_everyday.class);
                startActivity(intent);
            }
        });
    }

    private void initPlan() {
        applyWritePermission();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dayDate = simpleDateFormat.format(date);
        File file = new File(planDir);
        if (!file.exists()) { file.mkdir(); }
        File writeName = new File(planFile);
        try {
            if (!writeName.exists()) { writeName.createNewFile(); }
        } catch (IOException e) {
            e.printStackTrace();
        }
        planList = new ArrayList<>();
        lifeList = new ArrayList<>();
        threeyearList = new ArrayList<>();
        everydayList = new ArrayList<>();
        readPlan();
    }

    public static void readPlan() {
        try {
            FileReader reader = new FileReader(planFile);
            BufferedReader br = new BufferedReader(reader);
            String firstDate = br.readLine();
            if (firstDate == null) { return; }
            firstDate = firstDate.trim();
            while (true) {
                String eachLine = br.readLine();
                if (null == eachLine) { break; }
                ListInformation listInformation = new ListInformation();
                String[] array = eachLine.split(" ");
                if (array.length < 3) { break; }
                listInformation.setPlan(array[0]);
                listInformation.setChecked(Boolean.valueOf(array[1]));
                listInformation.setStyle(Integer.valueOf(array[2]));
                if (!dayDate.equals(firstDate)) { listInformation.setChecked(Boolean.FALSE); }
                planList.add(listInformation);
                switch (listInformation.getStyle()) {
                    case 0:
                        lifeList.add(listInformation);
                        break;
                    case 1:
                        threeyearList.add(listInformation);
                        break;
                    case 2:
                        everydayList.add(listInformation);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writePlan() {
        File writeName = new File(planFile);
        try {
            writeName.createNewFile();
            FileWriter writer = new FileWriter(writeName);
            BufferedWriter out = new BufferedWriter(writer);
            writer.write(dayDate + "\n");
            for (int i = 0; i < planList.size(); i++) {
                ListInformation listInformation = planList.get(i);
                writer.write(listInformation.getPlan() + " "
                        + listInformation.getChecked().toString() + " "
                        + listInformation.getStyle().toString() + "\n");
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void applyWritePermission() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (Build.VERSION.SDK_INT >= 23) {
            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权 DINIED---拒绝
            if (check != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        1);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            writePlan();
            ActivityCollector.finishAll();
        }
        return false;
    }
}
