package gmail.dendnight.dlistview.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 下滑上滑阻尼
 * 
 * <pre>
 * Description
 * Author:		Dendnight
 * Version:		1.0  
 * Create at:	2013-11-19 上午11:22:32  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class DListView extends ListView implements Runnable {

	public DListView(Context context) {
		super(context);
	}

	public DListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private float mLastDownY = 0f;
	private int mDistance = 0;
	private int mStep = 0;
	private boolean mPositive = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 手指按下时触发
			if (mLastDownY == 0f && mDistance == 0) {
				mLastDownY = event.getY();
				return true;
			}
			break;

		case MotionEvent.ACTION_CANCEL:
			break;

		case MotionEvent.ACTION_UP: // 手指抬起之后触发
			if (mDistance != 0) {
				System.out.println("---post");
				mStep = 1;
				mPositive = (mDistance >= 0);
				this.post(this);
				return true;
			}
			mLastDownY = 0f;
			mDistance = 0;
			break;

		case MotionEvent.ACTION_MOVE: // 手指按下之后滑动触发
			if (mLastDownY != 0f) {
				mDistance = (int) (mLastDownY - event.getY());
				if ((mDistance < 0 && getFirstVisiblePosition() == 0 && getChildAt(0).getTop() == 0)
						|| (mDistance > 0 && getLastVisiblePosition() == getCount() - 1)) {
					// 第一个位置并且是想下拉，就滑动或者最后一个位置向上拉
					// 这个判断的作用是在非顶端的部分不会有此滚动
					mDistance /= 2; // 这里是为了减少滚动的距离
					scrollTo(0, mDistance); // 滚动
					return true;
				}
			}
			mDistance = 0;
			break;
		}
		return super.onTouchEvent(event);
	}

	public void run() {
		mDistance += mDistance > 0 ? -mStep : mStep;
		scrollTo(0, mDistance);
		if ((mPositive && mDistance <= 0) || (!mPositive && mDistance >= 0)) {
			scrollTo(0, 0);
			mDistance = 0;
			mLastDownY = 0f;
			return;
		}
		mStep += 1;
		// this.postDelayed(this, 10);
		this.post(this);
	}
}
