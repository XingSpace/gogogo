package com.xing.game.gogogo.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xing.game.gogogo.Method.BallColor;
import com.xing.game.gogogo.Method.DensityUtil;
import com.xing.game.gogogo.Method.GameEngine;
import com.xing.game.gogogo.Method.ScreenMethod;
import com.xing.game.gogogo.Method.TargetRandomMove;
import com.xing.game.gogogo.R;
import com.xing.game.gogogo.View.Ball;
import com.xing.game.gogogo.View.CustomDialog;
import com.xing.game.gogogo.View.Missile;
import com.xing.game.gogogo.View.Warning;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * ----------Dragon be here!----------/
 * ┏┛   ┻━━━┛    ┻┓
 * ┃ ｜｜｜｜｜｜｜ ┃
 * ┃       -      ┃
 * ┃   ┳┛    ┗┳   ┃
 * ┃              ┃
 * ┃       ┻      ┃
 * ┃              ┃
 * ┗━┓          ┏━┛
 * 丨    神   丨
 * 丨    兽   丨
 * 丨    保   丨
 * 丨    佑   丨
 * 丨　　　    ┗━━━┓
 * 　　┃代码无ＢＵＧ　　┣┓
 * 　　┃！！！！！！　　┃
 * 　　┗┓ ┓　　　┏━┳┓┏┛
 * 　 　┃┫┫　　　┃┫┫
 * 　　 ┗┻┛　　　┗┻┛
 * ━━━━━━亦余心之所善兮，虽九死其犹未悔━━━━━━by:Xing
 * <p/>
 * Created by wangxing on 15/12/29.
 * 游戏主界面
 */
public class GameMain extends ActivityBase implements Runnable {

    /**
     * 游戏主要角色
     */
    private Ball ball;

    /**
     * 游戏的目标球（就是拿来吃掉的东西）
     */
    private Ball target;

    /**
     * 导弹
     */
    private Missile missile1, missile2;

    /**
     * 导弹来临之前的警告
     */
    private Warning warning1, warning2;

    /**
     * 一个显示得分，一个显示游戏时间
     */
    private TextView score, time;

    /**
     * 计时器和计时数
     */
    private Timer timer;
    private int times = 0;

    /**
     * 记录游戏分数
     */
    private int scores = 0;

    /**
     * 由此得出圆心的坐标
     */
    private float BallCenterX, BallCenterY;

    /**
     * 游戏主循环
     * 通过线程来开启游戏循环
     */
    private Thread thread;

    /**
     * 游戏循环开关，当其为false时结束循环
     */
    private boolean isCycle = true;

    /**
     * 获取球的随机色
     */
    private BallColor ballColor = new BallColor();

    /**
     * 两个变量分别是音效播放类和一个音乐资源id集合
     */
    private SoundPool soundPool;
    private Map<Integer, Integer> soundID;

    /**
     * 由于游戏结束后弹出的Dialog会导致线程启动方法的位置重新运行
     * 所以，为了避免报错，需要这个参数来表明是否为第一次打开
     */
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamemain);
    }

    /**
     * 游戏循环
     */
    @Override
    public void run() {
        long updateDurationMillis = 0;
        long sleepDurationMills = 0;

        //开启计时
        timer.schedule(timerTask, 1000, 1000);

        while (isCycle) {
            long beforeUpdateRender = System.nanoTime();

            ballJump.sendMessage(new Message());
            moveBall.sendMessage(new Message());

            missileGoing.sendMessage(new Message());

            updata();
            isOver();

            updateDurationMillis = (System.nanoTime() - beforeUpdateRender) / 1000000L;
            sleepDurationMills = Math.max(2, 17 - updateDurationMillis);

            try {
                //在循环中控制游戏休眠时间
                Thread.sleep(sleepDurationMills);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //游戏结束时，取消计时
        timer.cancel();
    }

    @SuppressLint("HandlerLeak")
    private Handler OverDialog = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
            builder.setTitle("提示");
            builder.setMessage("游戏结束啦☺\n你的得分" + scores);
            builder.setLeftButtonClickListener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
            builder.setRightButtonClickListener(new DatePickerDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
            builder.onCreate().show();
        }
    };

    /**
     * 当玩家发出移动指令时，使用本方法进行渲染
     */
    @SuppressLint("HandlerLeak")
    private Handler moveBall = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ball.move();
        }
    };

    /**
     * 游戏一旦开始，就从游戏循环中调用本方法开启跳跃
     */
    @SuppressLint("HandlerLeak")
    private Handler ballJump = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ball.jump();
        }
    };

    /**
     * 游戏开始之后始终开始之后
     * 导弹从这里发射
     */
    @SuppressLint("HandlerLeak")
    private Handler missileGoing = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            missile1.move();
            missile2.move();
        }
    };

    /**
     * 游戏循环中的每一次，都要调用这个方法，用于刷新更新后的各个控件数据
     */
    private void updata() {
        BallCenterX = (ball.getWidth() / 2) + ball.getX();
        BallCenterY = (ball.getHeight() / 2) + ball.getY();
    }

    /**
     * 判断球是否被方块撞击
     * 顺便判断一下是否撞击到目标球
     */
    private void isOver() {

        RandomMove.sendMessage(new Message());

        //判断是否撞上火箭
        boolean b = GameEngine.isCollsion(BallCenterX, BallCenterY, DensityUtil.dip2px(mContext, 30), missile1.getHeight(), missile1.getWidth(), missile1.getX(), missile1.getY());

        boolean o = GameEngine.isCollsion(BallCenterX, BallCenterY, DensityUtil.dip2px(mContext, 30), missile2.getHeight(), missile2.getWidth(), missile2.getX(), missile2.getY());

        if (b || o) {
            soundPool.play(soundID.get(1),1,1,0,0,1);
            isCycle = false;
            OverDialog.sendMessage(new Message());
        }
    }

    /**
     * 使用本方法将目标球变换到随机的位置
     * 计分器+1也在这里完成
     *
     * @param view
     */
    private Handler RandomMove = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            doitRandomMove();
        }
    };

    private synchronized void doitRandomMove() {
        //判断目标球与用户球是否发生了碰撞
        boolean b = GameEngine.isCollsion(BallCenterX, BallCenterY, (target.getWidth() / 2) + target.getX(), (target.getHeight() / 2) + target.getY(), ball.getWidth() / 2, target.getWidth() / 2);

        if (b) {
            int[] ints = TargetRandomMove.getXY(mContext);
            target.setX(ints[0] - target.getHeight() / 2);
            target.setY(ints[1] - target.getHeight() / 2);

            scores++;
            score.setText("" + scores);
            ball.setImageResource(target.getImageResource());
            target.setImageResource(ballColor.getBallColor());
        }
    }

    /**
     * 每隔一秒通过计时器调用一次，以此增加时间栏的计时
     */
    private Handler timeGrowth = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time.setText(times + "");
        }
    };

    /**
     * 计时任务，通过本对象每隔一秒就可以调用一次楼上的UI线程，保证每一秒都让计时数加一
     */
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            times++;
            timeGrowth.sendMessage(new Message());
        }
    };

    private Handler warningShow1 = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            warning1.setY(missile1.getY());
            warning1.setVisibility(View.VISIBLE);
        }
    };
    private Handler warningClose1 = new Handler(Looper.getMainLooper()) {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            warning1.setVisibility(View.INVISIBLE);
            soundPool.play(soundID.get(2),1,1,0,0,1);
        }
    };
    private Handler warningShow2 = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            warning2.setY(missile2.getY());
            warning2.setVisibility(View.VISIBLE);
        }
    };
    private Handler warningClose2 = new Handler(Looper.getMainLooper()) {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            warning2.setVisibility(View.INVISIBLE);
            soundPool.play(soundID.get(2),1,1,0,0,1);
        }
    };


    /**
     * 绑定所有所需控件
     */
    @Override
    protected void findViews() {

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundID = new HashMap<Integer, Integer>();
        soundID.put(1, soundPool.load(mContext, R.raw.end, 1));//缓冲一个导弹碰撞到球的声音
        soundID.put(2, soundPool.load(mContext, R.raw.explosion, 0));
        ball = (Ball) findViewById(R.id.ball);
        ball.post(new Runnable() {
            @Override
            public void run() {
                //以下三行，设置了球跳跃的最高点
                ball.setMoveConstant(mContext, ball.getMoveConstant());
                ball.setMaxJumpConstant(ball.getY());

                //得出圆的中心坐标（BallCenterX,BallCenterY）
                BallCenterX = (ball.getWidth() / 2) + ball.getX();
                BallCenterY = (ball.getHeight() / 2) + ball.getY();
            }
        });
        target = (Ball) findViewById(R.id.target);
        time = (TextView) findViewById(R.id.time);
        score = (TextView) findViewById(R.id.score);
        warning1 = (Warning) findViewById(R.id.warning1);
        warning2 = (Warning) findViewById(R.id.warning2);
        missile1 = (Missile) findViewById(R.id.missile1);
        missile2 = (Missile) findViewById(R.id.missile2);
        missile1.setOverListenner(new Missile.OverListenner() {
            @Override
            public void Over() {
                warning1.setIsRight(missile1.getIsRight());
                warningShow1.sendMessage(new Message());
                warningClose1.sendMessageDelayed(new Message(), 1300);
                new Handler() {
                    @Override
                    public void dispatchMessage(Message msg) {
                        super.dispatchMessage(msg);
                        //if解决导弹速度快慢
                        if (TargetRandomMove.getHalf() == 0) {
                            missile1.setSpeed(Missile.SPEED_SLOW);
                        } else {
                            missile1.setSpeed(Missile.SPEED_FAST);
                        }
                        missile1.setIsGoing(true);
                    }
                }.sendMessageDelayed(new Message(), 1500);
            }
        });
        missile2.setOverListenner(new Missile.OverListenner() {
            @Override
            public void Over() {
                warning2.setIsRight(missile2.getIsRight());
                warningShow2.sendMessage(new Message());
                warningClose2.sendMessageDelayed(new Message(), 1300);
                new Handler() {
                    @Override
                    public void dispatchMessage(Message msg) {
                        super.dispatchMessage(msg);
                        //if解决导弹速度快慢
                        if (TargetRandomMove.getHalf() == 0) {
                            missile2.setSpeed(Missile.SPEED_SLOW);
                        } else {
                            missile2.setSpeed(Missile.SPEED_FAST);
                        }
                        missile2.setIsGoing(true);
                    }
                }.sendMessageDelayed(new Message(), 1500);
            }
        });

        timer = new Timer();
        thread = new Thread(this);
    }

    /**
     * 在该方法中启动线程，原因是只有在这个方法中，可以准确的获取到控件的宽高和坐标数据
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (isFirst) {
            //判断是否第一次启动
            //随机的初始化目标球位置
            int[] ints = TargetRandomMove.getXY(mContext);
            target.setX(ints[0] - target.getHeight() / 2);
            target.setY(ints[1] - target.getHeight() / 2);

            thread.start();

            missile2.setIsRight(false);
            warning2.setIsRight(missile2.getIsRight());
            warningShow2.sendMessage(new Message());
            warningClose2.sendMessageDelayed(new Message(), 700);
            new Handler() {
                @Override
                public void dispatchMessage(Message msg) {
                    super.dispatchMessage(msg);
                    missile2.setSpeed(Missile.SPEED_FAST);
                    missile2.setIsGoing(true);
                }
            }.sendMessageDelayed(new Message(), 1000);

            warning1.setIsRight(missile1.getIsRight());
            warningShow1.sendMessage(new Message());
            warningClose1.sendMessageDelayed(new Message(), 1300);
            new Handler() {
                @Override
                public void dispatchMessage(Message msg) {
                    super.dispatchMessage(msg);
                    missile1.setSpeed(Missile.SPEED_SLOW);
                    missile1.setIsGoing(true);
                }
            }.sendMessageDelayed(new Message(), 1500);

            isFirst = false;
        }
    }

    /**
     * 游戏控制
     * 如果玩家触击了屏幕左侧则向左边移动，反之亦然
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int wmHarf = ScreenMethod.getScreenWidth() / 2;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下后开始相应运动
            if (event.getX() > wmHarf) {
                ball.setI(1);
                ball.setIsMove(true);
            } else {
                ball.setI(0);
                ball.setIsMove(true);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指松开后停止响应
            ball.setIsMove(false);
        }

        return false;
    }

    //本变量专门用于给两次back键计时
    private long mExitTime;
    /**
     * 游戏进行中如果使用了back键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {

                Toast.makeText(this, "再按一次退出游戏", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                isCycle = false;
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
