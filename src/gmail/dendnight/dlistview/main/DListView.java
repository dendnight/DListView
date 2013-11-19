package gmail.dendnight.dlistview.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * �»��ϻ�����
 * 
 * <pre>
 * Description
 * Author:		Dendnight
 * Version:		1.0  
 * Create at:	2013-11-19 ����11:22:32  
 *  
 * �޸���ʷ:
 * ����    ����    �汾  �޸�����
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
		case MotionEvent.ACTION_DOWN: // ��ָ����ʱ����
			if (mLastDownY == 0f && mDistance == 0) {
				mLastDownY = event.getY();
				return true;
			}
			break;

		case MotionEvent.ACTION_CANCEL:
			break;

		case MotionEvent.ACTION_UP: // ��ָ̧��֮�󴥷�
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

		case MotionEvent.ACTION_MOVE: // ��ָ����֮�󻬶�����
			if (mLastDownY != 0f) {
				mDistance = (int) (mLastDownY - event.getY());
				if ((mDistance < 0 && getFirstVisiblePosition() == 0 && getChildAt(0).getTop() == 0)
						|| (mDistance > 0 && getLastVisiblePosition() == getCount() - 1)) {
					// ��һ��λ�ò��������������ͻ����������һ��λ��������
					// ����жϵ��������ڷǶ��˵Ĳ��ֲ����д˹���
					mDistance /= 2; // ������Ϊ�˼��ٹ����ľ���
					scrollTo(0, mDistance); // ����
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
