package com.gzeinnumer.mypagingstylev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gzeinnumer.mypagingstylev2.ui.productV1.ProductActivityV1;
import com.gzeinnumer.mypagingstylev2.ui.productV2.ProductActivityV2;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_menu_1).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ProductActivityV1.class));
        });

        findViewById(R.id.btn_menu_2).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ProductActivityV2.class));
        });
    }
}