package orgs.androidtown.tetris2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * 게임 전체를 그려주는 클래스
 */

public class Stage extends View {
    Setting setting;
    Board board;
    Preview preview;

    boolean RUNNING = true;

    public Stage(Context context, Setting setting) {
        super(context);

        this.setting = setting;
        // 보드와 프리뷰를 생성한다
        board = new Board(1, 1, 11, 16, setting.unit); // 테트리스가 시작하는 장소로써, 인자값으로 격자의 위치값을 주는 것
        preview = new Preview(13, 1, 4, 4, setting.unit); // 테트리스 옆에 다음에 뭐가 나올주 보여주는 장소.
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 자기 자신을 화면에 동적으로 그려준다.
         */
        preview.onDraw(canvas);
        board.onDraw(canvas);
    }

    // 0. 화면을 최초에 그리기 전에 기본세팅
    public void init(){
        addBlockToPreview();
        addBlockToBoardFromPreview();
        addBlockToPreview();
    }

    // 1. 블럭을 생성하는 함수
    public Block newBlock(){
        Property property = new Property();
        property.x = 0;
        property.y = 0;
        property.paint = new Paint();
        property.paint.setColor(Color.RED);
        property.unit = setting.unit;
        return new Block(property);
    }
    // 2. 블럭을 preview 에 담는 함수
    public void addBlockToPreview(){
        Block block = newBlock();
        preview.addBlock(block); // Block의 변화를 감지하여, preview에 setting
    }
    // 3. 블럭을 preview 에서 Board로 옴기는 함수
    public void addBlockToBoardFromPreview(){
        Block block = preview.popBlock();
        board.addBlock(block); // Block의 변화를 감지하여, board에 setting
    }

    // 키 패드 동작
    public void up() {
        board.up();
        invalidate();
    }
    public void down() {
        boolean check = board.down();
        // 다운시 충돌되면 map에 현재 블럭을 삽입하고
        // 새로운 블럭을 Preview에서 가져와서 담는다.
        if(!check) {
            board.addBlockToMap();
            // board 에 있는 map을 한줄씩 전체줄을 체크해서 꽉 차있으면 삭제후
            // 한줄씩 아래로 이동
            board.lineCheckAndRemove();
            addBlockToBoardFromPreview();
            addBlockToPreview();
        }
        postInvalidate();
    }
    public void left() {
        board.left();
        invalidate();
    }
    public void right() {
        board.right();
        invalidate();
    }
    // 1초에 한번씩 board에 있는 블럭을 아래로 이동시킨다.
    public void runBlock() {
        RUNNING = true;
        new Thread(){
            public void run(){
                while(RUNNING) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    down();
                }
            }
        }.start();
    }

    public void stopBlock(){
        RUNNING = false;
    }
}