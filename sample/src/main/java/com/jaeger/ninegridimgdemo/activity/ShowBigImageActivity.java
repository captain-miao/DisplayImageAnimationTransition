package com.jaeger.ninegridimgdemo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.jaeger.ninegridimgdemo.R;
import com.jaeger.ninegridimgdemo.adapter.BasePagerAdapter;
import com.jaeger.ninegridimgdemo.model.PictureOption;
import com.jaeger.ninegridimgdemo.utils.HardwareUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;


public class ShowBigImageActivity extends BaseActivity {
    private static final String TAG = "ActivityShowImage";
    private boolean mFinishActivityOnSingleTap = true;

    private ViewPager mViewPager;
    private CirclePageIndicator mCirclePageIndicator;
    private SimplePagerAdapter mAdapter;
    private List<String> mImageUrls;
    private int mCurrentIndex;
    private ImageView mPreviewImage;
    private ProgressBar mProgressBar;
    private PictureOption mPictureOption;
    private View mRootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_show_image);
        mPreviewImage = (ImageView) findViewById(R.id.preview_image);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_loading);
        mRootView = findViewById(R.id.root_view);
        Bundle bundle = getIntent().getExtras();
        mPictureOption = bundle.getParcelable("pictureOption");
        mImageUrls = mPictureOption.pictures;
        mCurrentIndex = mPictureOption.index;
        mViewPager = (ViewPager) findViewById(R.id.vp_single_viewpager);
        mCirclePageIndicator = (CirclePageIndicator) findViewById(R.id.vp_single_indicator);
        mCirclePageIndicator.setRadius(3 * getResources().getDisplayMetrics().density);
        mCirclePageIndicator.setPadding(4, 0, 4, 10);
        //未选中的填充色
        mCirclePageIndicator.setPageColor(0x33000000);
        //描边
        mCirclePageIndicator.setStrokeWidth(2);
        mCirclePageIndicator.setStrokeColor(0x33FFFFFF);
        //填充色，选中状态
        mCirclePageIndicator.setFillColor(0x7fFFFFFF);
        mAdapter = new SimplePagerAdapter(this);
        Picasso.with(this).load(mImageUrls.get(mCurrentIndex)).into(mPreviewImage, new Callback() {
            @Override
            public void onSuccess() {
                mAdapter.setData(mImageUrls);
                mViewPager.setAdapter(mAdapter);
                if (mImageUrls != null && mImageUrls.size() > 1) {
                    mCirclePageIndicator.setViewPager(mViewPager);
                } else {
                    mCirclePageIndicator.setVisibility(View.GONE);
                }
                mViewPager.setCurrentItem(mCurrentIndex);
            }

            @Override
            public void onError() {

            }
        });

    }


    @Override
    public void onBackPressed() {
        int position = mViewPager.getCurrentItem();
        mProgressBar.setVisibility(View.GONE);
        mCirclePageIndicator.setVisibility(View.GONE);

        float centerX = (float) HardwareUtil.getScreenWidth(ShowBigImageActivity.this);
        float centerY = (float) HardwareUtil.getScreenHeight(ShowBigImageActivity.this);
        Rect rect = mPictureOption.rects.get(position);

        // View activeView = getCurrentView(mViewPager);
        // float height = activeView != null ? activeView.getHeight() : centerY;
        //if(activeView instanceof ImageView){
        //    height = ((ImageView) activeView).getDrawable().getBounds().height();
        //    height = ((BitmapDrawable)((ImageView) activeView).getDrawable()).getBitmap().getHeight();
        //}
        // should has image's width and height
        // 这里的图片都是正方形的 todo
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(mViewPager, "scaleX", rect.width() / centerX);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(mViewPager, "scaleY", rect.width() / centerX);

        ObjectAnimator translateX = ObjectAnimator.ofFloat(mViewPager, "translationX", rect.centerX() - centerX / 2);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(mViewPager, "translationY", rect.centerY() - centerY / 2);
        AnimatorSet pictureAnim = new AnimatorSet();


        pictureAnim.playTogether(translateX, translateY, scaleDownX,scaleDownY);
        pictureAnim.setDuration(600);
        pictureAnim.setInterpolator(new AccelerateDecelerateInterpolator());


        pictureAnim.addListener(
            new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    Log.d(TAG, "onAnimationStart");
                    mRootView.setBackgroundResource(android.R.color.transparent);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    finish();
                    overridePendingTransition(0, 0);
                }
            }
        );

        pictureAnim.start();
    }
    private class SimplePagerAdapter extends BasePagerAdapter<String> implements View.OnClickListener {

        public SimplePagerAdapter(Context c) {
            super(c);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final ImageView iv = new ImageView(ShowBigImageActivity.this);
            container.addView(iv);
            iv.setOnClickListener(this);
            Picasso.with(container.getContext()).load(mData.get(position)).into(iv, new Callback() {
                @Override
                public void onSuccess() {
                    if(mPreviewImage.getVisibility() == View.VISIBLE) {
                        mPreviewImage.setVisibility(View.INVISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                        mViewPager.setVisibility(View.VISIBLE);
                        mCirclePageIndicator.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onError() {
                }
            });
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (object instanceof ImageView) {
                ImageView imageView = (ImageView) object;
                container.removeView(imageView);
                imageView = null;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void onClick(View v) {
            if (mFinishActivityOnSingleTap) {
                onBackPressed();
            }
        }
    }


    public View getCurrentView(ViewPager pager) {
        for (int i = 0; i < pager.getChildCount(); i++) {
            View child = pager.getChildAt(i);
            if (child.getX() <= pager.getScrollX() + pager.getWidth() &&
                child.getX() + child.getWidth() >= pager.getScrollX() + pager.getWidth()) {
                return child;
             }
         }
         return pager.getChildAt(0);
    }
}
