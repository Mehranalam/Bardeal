package com.example.bardeal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class Categories extends AppCompatActivity {

    private ChipGroup chipGroup;
    private TextView notNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        notNow = findViewById(R.id.notNow);
        notNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categories.this, MainActivity.class);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(Categories.this)
                                .toBundle());
            }
        });

        getWindow().setStatusBarColor(getResources().getColor(R.color.purple_200));
        chipGroup = findViewById(R.id.chipGroup);

        List<Integer> chipIds = chipGroup.getCheckedChipIds();

        for (int i : chipIds){
            Chip chip = findViewById(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO : ADD this CATEGOIS to MAINACTIVITY *****
                }
            });
        }

    }
}