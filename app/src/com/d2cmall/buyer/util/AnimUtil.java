package com.d2cmall.buyer.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.ResizeAnimation;

public class AnimUtil {

    public static Animation animationArrowUp;
    public static Animation animationArrowDown;
    public static Animation animationToastIn;
    public static Animation animationToastOut;

    public static Animation getAnimArrowUp(Context context) {
        if (animationArrowUp == null) {
            animationArrowUp = AnimationUtils.loadAnimation(context, R.anim.arrow_up);
        }
        return animationArrowUp;
    }

    public static Animation getAnimArrowDown(Context context) {
        if (animationArrowDown == null) {
            animationArrowDown = AnimationUtils.loadAnimation(context, R.anim.arrow_down);
        }
        return animationArrowDown;
    }

    public static void showMenu2Animation(final View menuBgView, final View view) {
        menuBgView.setVisibility(View.VISIBLE);
        Animation scaleAnimation = new ScaleAnimation(1, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0);
        scaleAnimation.setDuration(150);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                menuBgView.setBackgroundResource(R.color.trans_50_color_black);
            }
        });
        view.startAnimation(scaleAnimation);
    }

    public static void hideMenu2Animation(final View menuBgView, final View view) {
        Animation scaleAnimation = new ScaleAnimation(1, 1, 1, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0);
        scaleAnimation.setDuration(150);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
                menuBgView.setBackgroundResource(R.color.transparent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                menuBgView.setVisibility(View.GONE);
            }
        });
        view.startAnimation(scaleAnimation);
    }

    public static void showMenu3Animation(final View view, final View view2) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(),
                R.anim.slide_in_right);
        view.setVisibility(View.VISIBLE);
        view2.requestLayout();
        view.startAnimation(animation);
    }


    public static void hideMenu3Animation(final View view, final View view2) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(),
                R.anim.slide_out_right);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                view2.requestLayout();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    public static void showResizeAnimation(final View view, int height) {
        if (height > 0) {
            ResizeAnimation resizeAnimation = new ResizeAnimation(view, 0, height);
            view.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = 0;
            resizeAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            resizeAnimation.setDuration(400);
            view.startAnimation(resizeAnimation);
        }
    }

    public static void hideResizeAnimation(final View view, int height) {
        if (height > 0) {
            ResizeAnimation resizeAnimation = new ResizeAnimation(view, height, 0);
            resizeAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            resizeAnimation.setDuration(400);
            view.startAnimation(resizeAnimation);
        }
    }

    public static void showMenuAnimation(final View menuBgView, final View view) {
        menuBgView.setVisibility(View.VISIBLE);
        TranslateAnimation scaleAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0);
        scaleAnimation.setDuration(250);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                menuBgView.setBackgroundResource(R.color.trans_50_color_black);
            }
        });
        view.startAnimation(scaleAnimation);
    }

    public static void hideMenuAnimation(final View menuBgView, final View view) {
        TranslateAnimation scaleAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1);
        scaleAnimation.setDuration(250);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
                menuBgView.setBackgroundResource(R.color.transparent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                menuBgView.setVisibility(View.GONE);
            }
        });
        view.startAnimation(scaleAnimation);
    }

    //推送下单信息的提示
    public static Animation getAnimationToastIn(Context context) {
        if (animationToastIn == null) {
            animationToastIn = AnimationUtils.loadAnimation(context, R.anim.toast_fade_in);
        }
        return animationToastIn;
    }

    public static Animation getAnimationToastOut(Context context) {
        if (animationToastOut == null) {
            animationToastOut = AnimationUtils.loadAnimation(context, R.anim.toast_fade_out);
        }
        return animationToastOut;
    }

}

