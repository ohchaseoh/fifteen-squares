package com.example.fifteen_squares;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.logging.Handler;

/**
 * @author Chase Ohmstede
 * @version October 2022
 */

public class PuzzleView extends SurfaceView implements View.OnClickListener {

    private final Paint bg_color = new Paint();
    private final Paint tile_color = new Paint();
    private final Paint text_color = new Paint();

    private PuzzleController puzzle;// = new PuzzleController(this);

    private int b_len;// = puzzle.board.length;
    private final int m = 20;

    public PuzzleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        bg_color.setColor(Color.WHITE);
        tile_color.setColor(Color.parseColor("#149C58")); //super cool green I found
        text_color.setColor(Color.WHITE);
        text_color.setTextSize(32);
        
    }

    public void setController(PuzzleController puzzle) {
        this.puzzle = puzzle;
        b_len = puzzle.board.length;
    }

    @Override
    public void onDraw(Canvas c) {
        //add drawing stuff here later
        c.drawRect(0, 0, 800, 800, bg_color);
        drawGrid(c);
    }

    public void drawGrid(Canvas c) {

        // C: draws each tile
        for (int i = 0; i < b_len; i++){
            for (int j = 0; j < b_len; j++) {
                drawTile(c, j * 200, i * 200);
            }
        }

        // C: draws each num
        for (int i = 0; i < b_len; i++) {
            for (int j = 0; j < b_len; j++) {
                drawNum(c, j * 200, i * 200, j, i);
            }
        }
    }

    public void drawNum(Canvas c, int x, int y, int x_num, int y_num) {

        if (puzzle.board[x_num][y_num] != 0) {
            c.drawText(Integer.toString(puzzle.board[x_num][y_num]), x + (m*4), y + (m*4), text_color);

        } else if (puzzle.board[x_num][y_num] == 0) {
            c.drawText(" ", x + (m*4), y + (m*4), text_color);
        }

    }

    public void drawTile(Canvas c, int x, int y) {
        c.drawRect(x + m, y + m, x + (m*8), y + (m*8), tile_color);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.reset:
                puzzle.reset();
                bg_color.setColor(Color.WHITE);
                break;

            case R.id.verify:
                if(puzzle.solved()){
                    System.out.println("puzzle is solved!");
                    bg_color.setColor(Color.GREEN);
                } else {
                    System.out.println("puzzle is not solved");
                    bg_color.setColor(Color.RED);
                }

                break;
        }

        invalidate();

    }

    public boolean onTouchEvent(MotionEvent e) {

        float f_x = e.getX();
        float f_y = e.getY();

        int x = ((int) f_x) / 200;
        int y = ((int) f_y) / 200;

        System.out.println("x = " + x);
        System.out.println("y = " + y);

        // column #1 = 0-3
        // column #2 = 4-7
        // column #3 = 8-11
        // column #4 = 12-15

        // row #1 = 0-3
        // row #2 = 4-7
        // row #3 = 8-11
        // row #4 = 12-15

        int end_x = b_len - 1;
        int end_y = b_len - 1;

        if (x > end_x || y > end_y) return false;

        // C: time to find the zero (again)
        for (int i = 0; i < b_len; i++) {
            for (int j = 0; j < b_len; j++) {
                if (puzzle.board[j][i] == 0) {
                    end_x = j;
                    end_y = i;
                }
            }
        }

        // C: moves iff correct is touched
        puzzle.move(x, y, end_x, end_y);
        bg_color.setColor(Color.WHITE);

        invalidate();
        return true;
    }
}
