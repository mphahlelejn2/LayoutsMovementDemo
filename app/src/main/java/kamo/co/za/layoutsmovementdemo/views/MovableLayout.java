package kamo.co.za.layoutsmovementdemo.views;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.LinearLayout;
import kamo.co.za.layoutsmovementdemo.main.IMain;


public class MovableLayout extends LinearLayout implements ScaleGestureDetector.OnScaleGestureListener {

    //Zoom enum parameters
    private enum Mode {
        NONE,
        DRAG,
        ZOOM
    }
    //for double tap
    long startTime;
    static final int MAX_DURATION = 200;
    // zoom scaling
    private static final float MIN_ZOOM = 1.0f;
    private static final float MAX_ZOOM = 4.0f;
    //Zoom parameters
    private Mode mode = Mode.NONE;
    private float scale = 1.0f;
    private float lastScaleFactor = 0f;
    private float startX = 0f;
    private float startY = 0f;
    float dX, dY;

    //translate the canvas
    private float dx = 0f;
    private float dy = 0f;
    private float prevDx = 0f;
    private float prevDy = 0f;

    private boolean isLayoutMoving=false;
    private IMain.View myview ;

    public MovableLayout(IMain.View view) {
        super( view.getContext());
        this.myview=view;
    }

    public void init() {
        myview.layoutScrollViewEnabled(true);
        final ScaleGestureDetector scaleDetector = new ScaleGestureDetector(myview.getContext(), this);
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(final android.view.View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        //detect tab
                        if(System.currentTimeMillis() - startTime <= MAX_DURATION)
                        {
                            //check is layoutUseCase is already moving
                            if(!isLayoutMoving) {
                                //clear all previous settings
                                myview.clearLayout();
                                myview.layoutScrollViewEnabled(false);
                                setLayoutBackground();
                            }else
                            {   //disable moving settings
                                clearLayoutBackground();
                            }
                        }
                        //zoom and drag options
                        if (scale > MIN_ZOOM) {
                            startX = motionEvent.getX() - prevDx;
                            startY = motionEvent.getY() - prevDy;
                        }
                        //get current touched points
                        dX = view.getX() - motionEvent.getRawX();
                        dY = view.getY() - motionEvent.getRawY();

                        break;

                    //handle finger movement o
                    case MotionEvent.ACTION_MOVE:
                        //move a layoutUseCase cross the screen
                        if(isLayoutMoving)
                            view.animate()
                                    .x(motionEvent.getRawX() + dX)
                                    .y(motionEvent.getRawY() + dY)
                                    .setDuration(0)
                                    .start();

                        // zoom and drag
                        if (mode == Mode.DRAG ) {
                            dx = motionEvent.getX() - startX;
                            dy = motionEvent.getY() - startY;
                        }

                        break;

                    // multi touch down
                    case MotionEvent.ACTION_POINTER_DOWN:
                        //disable layoutUseCase movement
                        isLayoutMoving =false;
                        mode = Mode.ZOOM;
                        // clear any layoutUseCase movement settings
                        clearLayoutBackground();
                        break;

                    //multi touch up
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = Mode.DRAG;
                        myview.layoutScrollViewEnabled(true);
                        break;

                    // single touch up
                    case MotionEvent.ACTION_UP:
                        //record last touched time for tapping
                        startTime = System.currentTimeMillis();
                        mode = Mode.NONE;
                        prevDx = dx;
                        prevDy = dy;

                        break;

                }
                scaleDetector.onTouchEvent(motionEvent);

                if (((mode == Mode.DRAG && scale >= MIN_ZOOM) || mode == Mode.ZOOM))
                {
                    //animate image inside the layoutUseCase
                    getParent().requestDisallowInterceptTouchEvent(true);
                    float maxDx = (child().getWidth() - (child().getWidth() / scale)) / 2 * scale;
                    float maxDy = (child().getHeight() - (child().getHeight() / scale)) / 2 * scale;
                    dx = Math.min(Math.max(dx, -maxDx), maxDx);
                    dy = Math.min(Math.max(dy, -maxDy), maxDy);
                    applyScaleAndTranslation();
                }

                return true;
            }

        });

    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleDetector) {
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleDetector) {
        float scaleFactor = scaleDetector.getScaleFactor();
        if (lastScaleFactor == 0 || (Math.signum(scaleFactor) == Math.signum(lastScaleFactor))) {
            scale *= scaleFactor;
            scale = Math.max(MIN_ZOOM, Math.min(scale, MAX_ZOOM));
            lastScaleFactor = scaleFactor;
        } else {
            lastScaleFactor = 0;
        }
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleDetector) {

    }

    /**
     * scaling the image
     */
    private void applyScaleAndTranslation() {
        child().setScaleX(scale);
        child().setScaleY(scale);
        child().setTranslationX(dx);
        child().setTranslationY(dy);
    }

    /**
     * set background settings for layoutUseCase movement
     */
    private void setLayoutBackground() {
        mode = Mode.NONE;
        isLayoutMoving =true;
        myview.layoutScrollViewEnabled(false);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(7, Color.MAGENTA);
        setPadding(5,5,5,5);
        setBackground(drawable);
    }

    /**
     * clear any previous layoutUseCase background settings
     */

    public void clearLayoutBackground() {
        isLayoutMoving =false;
        myview.layoutScrollViewEnabled(true);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        setPadding(0,0,0,0);
        setBackground(drawable);

    }
    private android.view.View child() {
        return getChildAt(0);
    }


    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}
