package tw.com.nec.justin_chen.connect3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 yellow; 1 red
    int activePlayer = 0;

    // 2 the slot is empty
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    // winning positions
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    boolean gameIsActive = true;

    public void dropin(View view){
        // 點中間的
        ImageView counter = (ImageView) view;

        int tagNum = Integer.parseInt(counter.getTag().toString());
        // System.out.println("tag number: "+tagNum);

        // 如果位置是空的&&gameIsActive為true，才能dropin
        if(gameState[tagNum]==2 && gameIsActive==true){
            gameState[tagNum] = activePlayer;

            // SETUP
            // 上移一千(從畫面消失)
            counter.setTranslationY(-1000f);

            // 嵌入圖片
            if(activePlayer==0){
                // 黃色
                counter.setImageResource(R.drawable.yellow);
                activePlayer=1;
            } else if(activePlayer==1) {
                // 紅色
                counter.setImageResource(R.drawable.red);
                activePlayer=0;
            }

            // DROP-IN
            // 下移一千(回歸原位)
            counter.animate().translationYBy(1000f).rotation(180).setDuration(300);
        }

        for(int[] winningPosition : winningPositions){


            // 三個位置都有圖
            boolean slotNotTwo = gameState[winningPosition[0]]!=2 && gameState[winningPosition[1]]!=2 && gameState[winningPosition[2]]!=2;
            // 並且三個位置的圖為同色
            boolean sameColor = gameState[winningPosition[0]]==gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]];

            // 有人贏的情況
            if(slotNotTwo && sameColor){
                System.out.println("we have a winner!!!");

                // 中間的訊息塊
                LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                layout.setVisibility(View.VISIBLE);

                String winningMessage = "Yellow has won";
                if(gameState[winningPosition[0]]==1){
                    winningMessage = "Red has won";
                }
                TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                winnerMessage.setText(winningMessage);

                gameIsActive = false;

            } else {
                // 非有人贏的狀況

                boolean gameIsOver = true;
                // 先判斷game結束了沒有? (如果任一格子為2，遊戲就還沒結束)
                for(int slot : gameState){
                    if(slot==2){
                        gameIsOver = false;
                    }
                }

                // 如果遊戲已結束
                if(gameIsOver){
                    // 中間的訊息塊
                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);

                    String winningMessage = "It's a Draw!";
                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winningMessage);
                }

            }


        }

    }

    public void playAgain(View view){

        gameIsActive = true;

        // 中間的訊息消失
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        // 重設狀態
        // 0 yellow; 1 red
        activePlayer = 0;
        // 2 the slot is empty
        for(int i = 0; i<gameState.length; i++){
            gameState[i] = 2;
        }

        // ImageView消失
        GridLayout gridLayout =(GridLayout) findViewById(R.id.gridLayout);
        // loop 9格子
        for(int i = 0; i<gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
