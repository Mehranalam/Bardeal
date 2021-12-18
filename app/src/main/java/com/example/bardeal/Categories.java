package com.example.bardeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class Categories extends AppCompatActivity {

    private ChipGroup chipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

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