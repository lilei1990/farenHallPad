package com.shyms.farendating.home.my_location;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.shyms.farendating.home.my_location.model.BluetoothInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import me.hokas.utils.ToastUtil;

/**
 * 蓝牙定位
 * Created by 洪开盛 on 2015/7/11.
 */
public class MapSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    private static final long DOUBLE_CLICK_TIME_SPACE = 300;

    //放大缩小
    private float mCurrentScaleMax;
    private float mCurrentScale;
    private float mCurrentScaleMin;

    //屏幕宽高
    private float windowWidth, windowHeight;

    private SurfaceHolder surfaceHolder;  //控制
    private Canvas canvas;
    private Paint myPaint = new Paint();
    private Bitmap mBitmap;

    private Paint mPaint; //画笔

    private PointF mStartPoint, mapCenter;//mStartPoint起始坐标， mapCenter表示地图中心在屏幕上的坐标
    private long lastClickTime;// 记录上一次点击屏幕的时间，以判断双击事件
    private Status mStatus = Status.NONE;

    private float oldRate = 1;
    private float oldDist = 1;
    private float offsetX, offsetY;

    private boolean isShu = true;  //用了判断屏幕的方向
    private boolean flag = true;

    private Thread thread;

    @Override
    public void run() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null && mBitmap != null) {
            canvas.drawColor(Color.WHITE);
            Matrix matrix = new Matrix();
            matrix.setScale(mCurrentScale, mCurrentScale, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
            matrix.postTranslate(mapCenter.x - mBitmap.getWidth() / 2, mapCenter.y - mBitmap.getHeight() / 2);
            //  缩放之后再平移 相当于中心点缩放 效果 参数代表品平移的距离，默认原点（0,0）
            canvas.drawBitmap(mBitmap, matrix, mPaint);
            for (BluetoothInfo bluetoothInfo : daobanInfos) {
                Bitmap headerMap = bluetoothInfo.getBitmap();
                matrix.setScale(1.0f, 1.0f);
                // 使用Matrix使得Bitmap的宽和高发生变化，在这里使用的mapX和mapY都是相对值
                matrix.postTranslate(
                        mapCenter.x - headerMap.getWidth() / 2 - mBitmap.getWidth() * mCurrentScale / 2
                                + mBitmap.getWidth() * bluetoothInfo.getMapX() * mCurrentScale,//缩放之后小图标偏移量的x值
                        mapCenter.y - headerMap.getHeight() - mBitmap.getHeight() * mCurrentScale / 2
                                + mBitmap.getHeight() * bluetoothInfo.getMapY() * mCurrentScale);
                canvas.drawBitmap(headerMap, matrix, mPaint);
            }

        }
        invalidate();
        if (canvas != null) {
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    //枚举
    private enum Status {
        NONE, ZOOM, DRAG
    }

    private List<BluetoothInfo> daobanInfos = new ArrayList<>();

    public MapSurfaceView(Context context) {
        super(context);
        init();
    }

    public MapSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MapSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);

        // 获取屏幕的宽和高
        windowWidth = getResources().getDisplayMetrics().widthPixels;
        windowHeight = getResources().getDisplayMetrics().heightPixels - getStatusBarHeight();
        mPaint = new Paint();

        mStartPoint = new PointF();
        mapCenter = new PointF();
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        // 设置最小缩放为铺满屏幕，最大缩放为最小缩放的4倍
        mCurrentScaleMin = Math.min(windowHeight / mBitmap.getHeight(),
                windowWidth / mBitmap.getWidth());
        mCurrentScale = mCurrentScaleMin;
        mCurrentScaleMax = mCurrentScaleMin * 4;
        mapCenter.set(mBitmap.getWidth() * mCurrentScale / 2, mBitmap.getHeight() * mCurrentScale / 2);
        float bitmapRatio = mBitmap.getHeight() / mBitmap.getWidth();
        float winRatio = windowHeight / windowWidth;
        // 判断屏幕铺满的情况，isShu为true表示屏幕横向被铺满，为false表示屏幕纵向被铺满
        isShu = bitmapRatio <= winRatio;
        draw();
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    /**
     * 为当前地图添加标记
     *
     * @param object 导办人员信息
     */
    public void addDaobanInfo(BluetoothInfo object) {
        daobanInfos.add(object);
    }

    /**
     * 地图放大
     */
    public void zoomIn() {
        mCurrentScale *= 1.5f;
        if (mCurrentScale > mCurrentScaleMax) {
            mCurrentScale = mCurrentScaleMax;
        }
        draw();
    }

    /**
     * 地图缩小
     */
    public void zoomOut() {
        mCurrentScale /= 1.5f;
        if (mCurrentScale < mCurrentScaleMin) {
            mCurrentScale = mCurrentScaleMin;
        }
        if (isShu) {
            if (mapCenter.x - mBitmap.getWidth() * mCurrentScale / 2 > 0) {
                mapCenter.x = mBitmap.getWidth() * mCurrentScale / 2;
            } else if (mapCenter.x + mBitmap.getWidth() * mCurrentScale / 2 < windowWidth) {
                mapCenter.x = windowWidth - mBitmap.getWidth() * mCurrentScale / 2;
            }
            if (mapCenter.y - mBitmap.getHeight() * mCurrentScale / 2 > 0) {
                mapCenter.y = mBitmap.getHeight() * mCurrentScale / 2;
            }
        } else {
            if (mapCenter.y - mBitmap.getHeight() * mCurrentScale / 2 > 0) {
                mapCenter.y = mBitmap.getHeight() * mCurrentScale / 2;
            } else if (mapCenter.y + mBitmap.getHeight() * mCurrentScale / 2 < windowHeight) {
                mapCenter.y = windowHeight - mBitmap.getHeight() * mCurrentScale / 2;
            }

            if (mapCenter.x - mBitmap.getWidth() * mCurrentScale / 2 > 0) {
                mapCenter.x = mBitmap.getWidth() * mCurrentScale / 2;
            }
        }
        draw();
    }

    // 处理拖拽事件
    private void drag(MotionEvent event) {
        PointF currentPoint = new PointF();
        currentPoint.set(event.getX(), event.getY());
        offsetX = currentPoint.x - mStartPoint.x;
        offsetY = currentPoint.y - mStartPoint.y;
        // 以下是进行判断，防止出现图片拖拽离开屏幕
        if (offsetX > 0 && mapCenter.x + offsetX - mBitmap.getWidth() * mCurrentScale / 2 > 0) {
            offsetX = 0;
        }
        if (offsetX < 0 && mapCenter.x + offsetX + mBitmap.getWidth() * mCurrentScale / 2 < windowWidth) {
            offsetX = 0;
        }
        if (offsetY > 0 && mapCenter.y + offsetY - mBitmap.getHeight() * mCurrentScale / 2 > 0) {
            offsetY = 0;
        }
        if (offsetY < 0 && mapCenter.y + offsetY + mBitmap.getHeight() * mCurrentScale / 2 < windowHeight) {
            offsetY = 0;
        }
        mapCenter.x += offsetX;
        mapCenter.y += offsetY;
        draw();
        mStartPoint = currentPoint;
    }

    // 处理多点触控缩放事件
    private void zoomAction(MotionEvent event) {
        float newDist = spacing(event);
        if (newDist > 10.0f) {
            mCurrentScale = oldRate * (newDist / oldDist);
            if (mCurrentScale < mCurrentScaleMin) {
                mCurrentScale = mCurrentScaleMin;
            } else if (mCurrentScale > mCurrentScaleMax) {
                mCurrentScale = mCurrentScaleMax;
            }

            if (isShu) {
                if (mapCenter.x - mBitmap.getWidth() * mCurrentScale / 2 > 0) {
                    mapCenter.x = mBitmap.getWidth() * mCurrentScale / 2;
                } else if (mapCenter.x + mBitmap.getWidth() * mCurrentScale / 2 < windowWidth) {
                    mapCenter.x = windowWidth - mBitmap.getWidth() * mCurrentScale / 2;
                }
                if (mapCenter.y - mBitmap.getHeight() * mCurrentScale / 2 > 0) {
                    mapCenter.y = mBitmap.getHeight() * mCurrentScale / 2;
                }
            } else {

                if (mapCenter.y - mBitmap.getHeight() * mCurrentScale / 2 > 0) {
                    mapCenter.y = mBitmap.getHeight() * mCurrentScale / 2;
                } else if (mapCenter.y + mBitmap.getHeight() * mCurrentScale / 2 < windowHeight) {
                    mapCenter.y = windowHeight - mBitmap.getHeight() * mCurrentScale / 2;
                }

                if (mapCenter.x - mBitmap.getWidth() * mCurrentScale / 2 > 0) {
                    mapCenter.x = mBitmap.getWidth() * mCurrentScale / 2;
                }
            }
        }
        draw();

    }

    // 计算两个触摸点的距离
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    // 处理点击标记的事件
    private void clickAction(MotionEvent event) {

        int clickX = (int) event.getX();
        int clickY = (int) event.getY();

        for (BluetoothInfo daobanInfo : daobanInfos) {
            Bitmap location = daobanInfo.getBitmap();
            int objX = (int) (mapCenter.x - location.getWidth() / 2 -
                    mBitmap.getWidth() * mCurrentScale / 2 + mBitmap.getWidth() * daobanInfo.getMapX() * mCurrentScale);
            int objY = (int) (mapCenter.y - location.getHeight() -
                    mBitmap.getHeight() * mCurrentScale / 2 + mBitmap.getHeight() * daobanInfo.getMapY() * mCurrentScale);
            // 判断当前object是否包含触摸点，在这里为了得到更好的点击效果，我将标记的区域放大了
            if (objX < clickX && objX + location.getWidth() > clickX && objY + location.getHeight() > clickY && objY < clickY) {
                if (daobanInfo.getListener() != null) {
                    daobanInfo.getListener().onDaoBanClick(clickX, clickY);
                }
                break;
            }

        }

    }

    public void draw() {
        run();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                if (event.getPointerCount() == 1) {
//                    // 如果两次点击时间间隔小于一定值，则默认为双击事件
//                    if (event.getEventTime() - lastClickTime < DOUBLE_CLICK_TIME_SPACE) {
//                        zoomIn();
//                    } else {
//                        mStartPoint.set(event.getX(), event.getY());
//                        mStatus = Status.DRAG;
//                    }
//                }
//                lastClickTime = event.getEventTime();
//                break;
//
//            case MotionEvent.ACTION_POINTER_DOWN:
//                float distance = spacing(event);
//                if (distance > 10f) {
//                    mStatus = Status.ZOOM;
//                    oldDist = distance;
//                }
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//
//                if (mStatus == Status.DRAG) {
//                    drag(event);
//                } else if (mStatus == Status.ZOOM) {
//                    zoomAction(event);
//                }
//                break;
            case MotionEvent.ACTION_UP:
                if (mStatus != Status.ZOOM) {
//                    clickAction(event);
                }

//            case MotionEvent.ACTION_POINTER_UP:
//                oldRate = mCurrentScale;
//                mStatus = Status.NONE;
//                break;
            default:
                break;
        }

        return true;
    }

    public void onDrawXs(Canvas canvas) {
        invalidate();
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        flag = true;
        draw();
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
        if (mBitmap != null) {
            mBitmap.recycle();
        }
        for (BluetoothInfo object : daobanInfos) {
            if (object.getBitmap() != null) {
                object.getBitmap().recycle();
            }
        }
    }


    // 获得状态栏高度
    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
            return 75;
        }
    }
}
