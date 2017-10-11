package orgs.androidtown.tetris2;

import android.graphics.Canvas;

/**
 * Created by Jisang on 2017-09-29.
 */

public class Preview implements Block.Parent{
    // 크기단위
    float unit;
    // 좌표
    float x;
    float y;
    // 가로 세로 사이즈
    int columns;  // pixel = columns * unit
    int rows;     // pixel = rows * unit

    // 현재 프리뷰에 있는 블럭
    Block block;

    public Preview(float x, float y, int columns, int rows, float unit){
        this.unit = unit;
        this.x = x;
        this.y = y;
        this.columns = columns;
        this.rows = rows;
    }

    public void addBlock(Block block) {
        this.block = block;
        block.setParent(this);
    }

    public Block popBlock() {
        return block;
    }

    public void onDraw(Canvas canvas){
        // 자기 자신 그리기

        // 가지고 있는 유닛 그리기
        block.onDraw(canvas);
    }

    @Override
    public float getX() {
        return x*unit;
    }

    @Override
    public float getY() {
        return y*unit;
    }
}
