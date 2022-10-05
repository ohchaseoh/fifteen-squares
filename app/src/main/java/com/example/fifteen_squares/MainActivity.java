package com.example.fifteen_squares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        PuzzleView puzzle = findViewById(R.id.puzzleView);
        PuzzleController puzzle_c = new PuzzleController(puzzle);

        puzzle.setController(puzzle_c);

        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(puzzle);

        Button verify = findViewById(R.id.verify);
        verify.setOnClickListener(puzzle);

    }
}