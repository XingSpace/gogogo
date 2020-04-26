package com.xing.game.gogogo.Method;

import com.xing.game.gogogo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wangxing on 16/1/4.
 */
public class BallColor {

    private int Green = R.drawable.ball;

    private int violet = R.drawable.violetball;

    private int blue = R.drawable.blueball;

    private int yellow = R.drawable.yellowball;

    private List<Integer> list = new ArrayList<Integer>();

    public BallColor(){
        list.add(Green);
        //list.add(violet);
        list.add(blue);
        list.add(yellow);
    }

    /**
     * @return 随机给出一个圆的颜色
     */
    public int getBallColor(){
        Random random = new Random();

        int i = random.nextInt(list.size());

        return list.get(i);
    }

}
