package google.zxing.client.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.d2cmall.buyer.R;
import com.google.zxing.ResultPoint;

import java.util.Collection;
import java.util.HashSet;

import google.zxing.client.camera.CameraManager;

public final class ViewfinderView extends View {

    private static final long ANIMATION_DELAY = 100L;
    private static final int OPAQUE = 0xFF;
    private Rect mRect;
    private int slideTop;
    private int slideSpeed = 5;
    private final Paint paint;
    private Bitmap resultBitmap;
    private final int maskColor;
    private final int resultColor;
    private Collection<ResultPoint> possibleResultPoints;
    private CameraManager cameraManager;
    private Drawable lineDrawable;

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        Resources resources = getResources();
        maskColor = resources.getColor(R.color.trans_40_color_black);
        resultColor = resources.getColor(R.color.trans_50_color_black);
        possibleResultPoints = new HashSet<>(5);
        mRect = new Rect();
        lineDrawable = getResources().getDrawable(R.mipmap.ic_code_line);

    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (cameraManager == null) {
            return; // not ready yet, early draw before done configuring
        }
        Rect frame = cameraManager.getFramingRect();
        if (frame == null) {
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (resultBitmap != null) {
            paint.setAlpha(OPAQUE);
            canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
        } else {
            if ((slideTop += slideSpeed) < (frame.bottom - frame.top)) {
                mRect.set(frame.left + 6, frame.top + slideTop, frame.right - 6,
                        frame.top + 1 + slideTop);
                lineDrawable.setBounds(mRect);
                lineDrawable.draw(canvas);
                invalidate();
            } else {
                slideTop = 0;
            }

            paint.setColor(getResources().getColor(R.color.color_green2));
            canvas.drawRect(frame.left - 10, frame.top - 10, frame.left + 70, frame.top - 5, paint);
            canvas.drawRect(frame.left - 10, frame.top - 10, frame.left - 5, frame.top + 70, paint);
            canvas.drawRect(frame.right - 70, frame.top - 10, frame.right + 10, frame.top - 5, paint);
            canvas.drawRect(frame.right + 5, frame.top - 10, frame.right + 10, frame.top + 70, paint);
            canvas.drawRect(frame.left - 10, frame.bottom + 5, frame.left + 70, frame.bottom + 10, paint);
            canvas.drawRect(frame.left - 10, frame.bottom - 70, frame.left - 5, frame.bottom + 10, paint);
            canvas.drawRect(frame.right - 70, frame.bottom + 5, frame.right + 10, frame.bottom + 10, paint);
            canvas.drawRect(frame.right + 5, frame.bottom - 70, frame.right + 10, frame.bottom + 10, paint);
            postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
        }
    }

    public void drawViewfinder() {
        resultBitmap = null;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        possibleResultPoints.add(point);
    }

    public void recycleLineDrawable() {
        if (lineDrawable != null) {
            lineDrawable.setCallback(null);
        }
    }

}
