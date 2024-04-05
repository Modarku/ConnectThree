package com.example.connectthree;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.connectthree.classes.BitBoard;
import com.example.connectthree.databinding.ActivityConnectThreeBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConnectThree extends AppCompatActivity {

    private ActivityConnectThreeBinding binding;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConnectThreeBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<Integer> mainBitBoard = IntStream.of(new int[36])
                .boxed().collect(Collectors.toCollection(ArrayList::new));

        ArrayList<TextView> cells = new ArrayList<>();
        cells.add(binding.ctTvCell0Row0);
        cells.add(binding.ctTvCell1Row0);
        cells.add(binding.ctTvCell2Row0);
        cells.add(binding.ctTvCell3Row0);
        cells.add(binding.ctTvCell4Row0);

        cells.add(binding.ctTvCell0Row1);
        cells.add(binding.ctTvCell1Row1);
        cells.add(binding.ctTvCell2Row1);
        cells.add(binding.ctTvCell3Row1);
        cells.add(binding.ctTvCell4Row1);

        cells.add(binding.ctTvCell0Row2);
        cells.add(binding.ctTvCell1Row2);
        cells.add(binding.ctTvCell2Row2);
        cells.add(binding.ctTvCell3Row2);
        cells.add(binding.ctTvCell4Row2);

        cells.add(binding.ctTvCell0Row3);
        cells.add(binding.ctTvCell1Row3);
        cells.add(binding.ctTvCell2Row3);
        cells.add(binding.ctTvCell3Row3);
        cells.add(binding.ctTvCell4Row3);

        cells.add(binding.ctTvCell0Row4);
        cells.add(binding.ctTvCell1Row4);
        cells.add(binding.ctTvCell2Row4);
        cells.add(binding.ctTvCell3Row4);
        cells.add(binding.ctTvCell4Row4);

        int[] ctr = {0, 0, 0, 0, 0};
        boolean[] gameState = {false, false}; // 0 = player turn state (false = player 1, true = player 2), 1 = player win state

        binding.ctBtnBack.setOnClickListener(v -> finish());
        binding.ctBtnReset.setOnClickListener(v -> reset(cells, mainBitBoard, gameState, ctr));

        binding.ctBtnRow0.setOnClickListener(v -> cellBtnClick(0, gameState, ctr, mainBitBoard, cells));
        binding.ctBtnRow1.setOnClickListener(v -> cellBtnClick(1, gameState, ctr, mainBitBoard, cells));
        binding.ctBtnRow2.setOnClickListener(v -> cellBtnClick(2, gameState, ctr, mainBitBoard, cells));
        binding.ctBtnRow3.setOnClickListener(v -> cellBtnClick(3, gameState, ctr, mainBitBoard, cells));
        binding.ctBtnRow4.setOnClickListener(v -> cellBtnClick(4, gameState, ctr, mainBitBoard, cells));


    }

    void reset(ArrayList<TextView> cells, ArrayList<Integer> mainBitBoard,boolean[] gameState, int[] ctr){
        for(TextView tv : cells){
            tv.setBackground(getDrawable(R.drawable.ct_cell_empty));
        }
        binding.ctTvState.setText("Player 1 turn");
        Collections.fill(mainBitBoard, 0);
        ctr[0] = 0;
        ctr[1] = 0;
        ctr[2] = 0;
        ctr[3] = 0;
        ctr[4] = 0;
        gameState[0] = false;
        binding.ctBtnRow0.setEnabled(true);
        binding.ctBtnRow1.setEnabled(true);
        binding.ctBtnRow2.setEnabled(true);
        binding.ctBtnRow3.setEnabled(true);
        binding.ctBtnRow4.setEnabled(true);
    }

    void cellBtnClick(int cellRow, boolean[] gameState, int[] ctr, ArrayList<Integer> mainBitBoard, ArrayList<TextView> cells){
        int bitBoardJump = cellRow * 6;
        int cellJump = cellRow * 5;
        if(gameState[0]){
            //append tile
            if(ctr[cellRow] < 5){
                mainBitBoard.set(ctr[cellRow] + bitBoardJump, 2);
                cells.get(ctr[cellRow] + cellJump).setBackground(getDrawable(R.drawable.ct_cell_p2fill));
                binding.ctTvState.setText("Player 1 turn");
                gameState[0] = false;
                ctr[cellRow]++;
            }else{
                Toast.makeText(ConnectThree.this, "Row " + cellRow + " 0 is full!", Toast.LENGTH_SHORT).show();
            }
        }else{
            //append tile
            if(ctr[cellRow] < 5){
                mainBitBoard.set(ctr[cellRow] + bitBoardJump, 1);
                cells.get(ctr[cellRow] + cellJump).setBackground(getDrawable(R.drawable.ct_cell_p1fill));
                binding.ctTvState.setText("Player 2 turn");
                gameState[0] = false;
                ctr[cellRow]++;
            }else{
                Toast.makeText(ConnectThree.this, "Row " + cellRow + " is full!", Toast.LENGTH_SHORT).show();
            }
            gameState[0] = true;
        }
        binding.ctTvTip.setVisibility(View.INVISIBLE);
        checkState(gameState, mainBitBoard);
    }

    void checkState(boolean[] gameState, ArrayList<Integer> mainBitBoard){
        StringBuilder p1BitBoard = new StringBuilder();
        StringBuilder p2BitBoard = new StringBuilder();

        for(int i : mainBitBoard){
            System.out.print(i + " ");
            switch(i){
                case 0:
                    p1BitBoard.append(0);
                    p2BitBoard.append(0);
                    break;
                case 1:
                    p1BitBoard.append(1);
                    p2BitBoard.append(0);
                    break;
                case 2:
                    p1BitBoard.append(0);
                    p2BitBoard.append(1);
                    break;
            }
        }
        System.out.print("\n");
        System.out.println(p1BitBoard.toString());
        System.out.println(p2BitBoard.toString());

        BitBoard p1 = new BitBoard(p1BitBoard.toString());
        BitBoard p2 = new BitBoard(p2BitBoard.toString());

        gameState[1] = p1.check();
        if(gameState[1]){
            binding.ctTvState.setText("Player 1 win");
            binding.ctBtnRow0.setEnabled(false);
            binding.ctBtnRow1.setEnabled(false);
            binding.ctBtnRow2.setEnabled(false);
            binding.ctBtnRow3.setEnabled(false);
            binding.ctBtnRow4.setEnabled(false);
            return;
        }
        gameState[1] = p2.check();
        if(gameState[1]){
            binding.ctTvState.setText("Player 2 win");
            binding.ctBtnRow0.setEnabled(false);
            binding.ctBtnRow1.setEnabled(false);
            binding.ctBtnRow2.setEnabled(false);
            binding.ctBtnRow3.setEnabled(false);
            binding.ctBtnRow4.setEnabled(false);
        }

    }
}