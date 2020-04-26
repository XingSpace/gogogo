package com.xing.game.gogogo.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxing on 16/1/6.
 */
public class MathCyclePath {

    /**
     * 圆心的x坐标
     */
    private float aFloat = -20;

    /**
     * 圆心的y坐标
     */
    private float bFloat = ScreenMethod.getScreenHeight()/2;

    /**
     * 圆的半径
     */
    private float rFloat = ScreenMethod.getScreenHeight()/3;


    private float xFloat,yFloat;

    private List<float[]> list,list1;

    public MathCyclePath(){

    }

    /**
     * @return 通过数学方法返回一个集合，这个集合是一个圆形路径
     */
    public List<float[]> getPath(){

        List<float[]> result = new ArrayList<float[]>();

        list = new ArrayList<float[]>();

        List<float[]> temp = new ArrayList<float[]>();

        list1 = new ArrayList<float[]>();

        List<float[]> temp1 = new ArrayList<float[]>();

        float maxY = rFloat/Float.parseFloat(String.valueOf(Math.sin(Math.PI/4)));

        float yStart = bFloat - (maxY/2);
        float yMax = bFloat + (maxY/2);

        float xStart = aFloat - maxY/2;
        float xMax = aFloat + (maxY/2);

        for (yFloat = yStart; yFloat < yMax; yFloat+=1f){
            xFloat = (float)(Math.sqrt(rFloat*rFloat-(yFloat-bFloat)*(yFloat-bFloat)) + aFloat);
            //第一加入，蓝色，按照顺时针方向加入结果集
            list.add(new float[]{xFloat,yFloat});
            //第三加入，绿色，按照逆时针放向加入结果集
            temp.add(new float[]{xFloat-2*(xFloat-aFloat),yFloat});

        }

        for (xFloat = xStart; xFloat < xMax; xFloat+=1f){
            yFloat = (float) (Math.sqrt(rFloat*rFloat-(xFloat-aFloat)*(xFloat-aFloat)) + bFloat);
            //第二加入，红色，逆时针加入
            list1.add(new float[]{xFloat,yFloat});
            //第四加入，黑色，顺时针加入
            temp1.add(new float[]{xFloat,yFloat-2*(yFloat-bFloat)});

        }

        for (int i = 0; i<list.size(); i++){
            result.add(list.get(i));
        }

        for (int i = list1.size()-1; i >= 0; i--){
            result.add(list1.get(i));
        }

        for (int i = temp.size()-1; i >= 0; i--){
            result.add(temp.get(i));
        }

        for (int i = 0; i<temp1.size(); i++){
            result.add(temp1.get(i));
        }

        return result;
    }

    public float getaFloat() {
        return aFloat;
    }

    public void setaFloat(float aFloat) {
        this.aFloat = aFloat;
    }

    public float getbFloat() {
        return bFloat;
    }

    public void setbFloat(float bFloat) {
        this.bFloat = bFloat;
    }

    public float getrFloat() {
        return rFloat;
    }

    public void setrFloat(float rFloat) {
        this.rFloat = rFloat;
    }

    public float getxFloat() {
        return xFloat;
    }

    public void setxFloat(float xFloat) {
        this.xFloat = xFloat;
    }

    public float getyFloat() {
        return yFloat;
    }

    public void setyFloat(float yFloat) {
        this.yFloat = yFloat;
    }

}
