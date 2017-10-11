package orgs.androidtown.tetris2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    // 0. 게임 세팅
    private static Setting setting;        // 설정값
    Stage stage;
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 0. 게임 세팅
        setGame();
        // 1. 그림판을 준비
        initView();
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
        stage = new Stage(this, setting);
        // 뭔가 그릴것들을 다 준비해놔야 된다.
        stage.init();
        container.addView(stage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 앱이 화면에 보이면 블럭을 동작
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

    @Override
    protected void onStop() {
        super.onStop();
        // 앱이 화면에서 없어지면 블럭동작을 중단
        stage.stopBlock();
    }
}























