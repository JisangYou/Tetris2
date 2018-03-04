package orgs.androidtown.tetris2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 동적으로 액티비티안에 테트리스 게임이 진행되는 뷰를 그려서 처리.
 */

public class MainActivity extends AppCompatActivity {
    // 0. 게임 세팅
    private static Setting setting;        // 설정값
    Stage stage;
    FrameLayout container;
    TextView txtScore, txtCountDown;
    CountDownTimer countDown = null;
    boolean check = false;
    public static String score 


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 0. 게임 세팅
        setGame();
        // 1. 그림판을 준비
        initView();

        timer();

    }

    private void setGame() {
        // 0. 화면의 사이즈를 구해서 게임판에 넘긴다
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        setting = new Setting(width, height, 18, 18);
    }

    private void initView() {
        container = (FrameLayout) findViewById(R.id.container);
        txtScore = (TextView) findViewById(R.id.txtScore);

        txtCountDown = (TextView) findViewById(R.id.txtCountDown);
        stage = new Stage(this, setting);
        // 뭔가 그릴것들을 다 준비해놔야 된다.
        stage.init();
        container.addView(stage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 앱이 화면에 보이면 블럭을 동작
        txtScore.setText(score);
        stage.runBlock();
    }

    // 키 패드 연결
    public void up(View view) {
        stage.up();
    }

    public void down(View view) {
        stage.down();
    }

    public void left(View view) {
        stage.left();
    }

    public void right(View view) {
        stage.right();
    }


    public void pause(View view) {
        if (!check) {
            stage.stopBlock();
            check = true;
            Toast.makeText(this, "pause", Toast.LENGTH_LONG).show();
        } else {
            stage.runBlock();
            check = false;
            Toast.makeText(this, "resume", Toast.LENGTH_LONG).show();
        }

    }

    String minutes;

    private void timer() {
        countDown = new CountDownTimer(1000 * 60 * 60 * 24, 100) {
            @Override
            public void onTick(long mili) {

                String seconds = "" + ((mili / 1000) % 60);            //초
                minutes = "" + (((mili / (1000 * 60)) % 60));  //분

                txtCountDown.setText(minutes + " : " + seconds);

            }

            @Override
            public void onFinish() {

                if (minutes == "0") {
                    stage.stopBlock();
                    Toast.makeText(getBaseContext(), "game over", Toast.LENGTH_LONG).show();
                }
            }
        }.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        /**
         * 앱이 화면에서 없어지면 블럭동작을 중단
         * thread 정지
         */

        stage.stopBlock();
    }


}























