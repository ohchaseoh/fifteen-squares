package com.example.fifteen_squares;

import java.util.Random;

public class PuzzleController {

    protected final com.example.fifteen_squares.PuzzleView view;

    private int b_len;
    private final int size; //most likely to be changed/removed later
    private int [] vals;

    protected int[][] board = new int[4][4];

    public PuzzleController(PuzzleView view) {
        this.view = view;

        b_len = 4;
        size = 16;
        vals = new int[size];

        build();
    }

    // C: in charge of initially building the board
    private void build() {

        int cntr = 1;

        // C: build the board (very similar to solved() function)
        for (int i = 0; i < b_len; i++) {
            for (int j = 0; j < b_len; j++) {
                board[j][i] = cntr;
                vals[size-cntr] = cntr;
                cntr++;
            }
        }


        //board[3][3] = 0;
        reset();
    }

    // C: reset all tiles
    public void reset() {

        shuffle(board);

        int empty_y = 0;
        int empty_x = 0;

        // C: find the empty tile
        for (int i = 0; i < b_len; i++) {
            for (int j = 0; j < b_len; j++) {
                if (board[j][i] == 0) {
                    empty_y = i;
                    empty_x = j;
                }
            }
        }

        // C: switch last spot w zero
        int tmp = board[b_len - 1][b_len - 1];
        board[empty_x][empty_y] = tmp;
        board[b_len - 1][b_len - 1] = 0;

    }

    public void move(int x, int y, int new_x, int new_y) {

        // C: exclusive to adjacent pieces
        if (new_x > (x + 1) || new_x < (x - 1) || new_y > (y + 1) || new_y < (y - 1)) return;

        // C: diagonal OOB checking
        if      ((y == 0 && (new_y == (y - 1)))
            ||   (y == b_len - 1 && (new_y == (y + 1)))
            ||   (x == 0 && (new_x == (x - 1)))
            ||   (x == b_len - 1 && (new_x == (x + 1)))) return;

        // C: checking right side
        if (new_x == (x + 1)) {
            if (new_y == (y + 1)) return;
            if (new_y == (y - 1)) return;
        }

        // C: checking left side
        if (new_x == (x - 1)) {
            if (new_y == (y + 1)) return;
            if (new_y == (y - 1)) return;
        }

        if (board[new_x][new_y] != 0) return;

        // C: if no move errors have been found
        System.out.println("curr value = " + board[x][y]);
        board[new_x][new_y] = board[x][y];
        board[x][y] = 0;
    }

    /*
     * C: shuffling the "deck" of tiles on the board
     *
     * CITATION: Fisher-Yates Randomization Algorithm
     * AUTHOR(S): Ronald Fisher and Frank Yates
     * Date: 2014 StackOverflow
     * Link: https://stackoverflow.com/questions/20190110/2d-int-array-shuffle
     */

    void shuffle(int[][] arr) {

        Random rand = new Random();

        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = arr[i].length - 1; j > 0; j--) {

                int r1 = rand.nextInt(i + 1);
                int r2 = rand.nextInt(j + 1);

                int temp = arr[j][i];
                arr[j][i] = arr[r2][r1];
                arr[r2][r1] = temp;
            }
        }
    }

    // C: this is my attempt on getting that extra credit
    // cntr must be even for the puzzle to be solvable
    private boolean is_solvable() {

        int cntr = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < i; j++) {

                if (vals[j] > vals[i]) cntr++;
            }
        }

        return cntr % 2 == 0;
    }

    // C: check to see if the puzzle is solved
    public boolean solved() {

        int cntr = 0;

        for (int i = 0; i < b_len; i++) {
            for (int j = 0; j < b_len; j++) {
                cntr++;
                if (cntr == size) return true;
                else if (board[j][i] != vals[cntr]) return false;
            }
        }

        return true;
    }
}
