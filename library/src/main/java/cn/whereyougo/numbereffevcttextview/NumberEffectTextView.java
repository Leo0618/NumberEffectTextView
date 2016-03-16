package cn.whereyougo.numbereffevcttextview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * <p>function : 滚动效果TextView显示数字</p>
 * Created by lzj on 2015/12/3.
 */
public class NumberEffectTextView extends TextView {
    /** 动画播放时长 */
    private long mDuration = 700;
    final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};
    /** 整数部分 */
    private int mNumberInt;
    /** 小数部分 */
    private int mNumberDecimal;
    /** 整数部分起始值 */
    private int mNumberIntStart;
    /** 小数部分起始值 */
    private int mNumberDecimalStart;
    /** 中断动画 */
    private boolean mIntercept = false;
    /** 是否支持小数 默认支持 */
    private boolean mEnableDecimal = true;
    /** 小数位数是否为两位 默认是的 */
    private boolean mDecimal2Bit = true;

    public NumberEffectTextView(Context context) {
        super(context);
    }

    public NumberEffectTextView(Context context, AttributeSet attr) {
        super(context, attr);
    }


    /**
     * 设置一个数字符串进来
     *
     * @param numberStrIn 数字字符 eg:99,999.99 or 99
     * @return 当前类
     */
    public NumberEffectTextView withNumber(String numberStrIn) {//"99,999.99"
        String numberStr = numberStrIn;
        if (TextUtils.isEmpty(numberStr)) {
            throw new RuntimeException("numberStr can not be null or empty");
        }
        if (numberStr.contains(",")) {
            numberStr = numberStr.replace(",", "");//"99999.99"
        }
        if (!numberStr.contains(".")) {//追加小数位
            numberStr += ".00";
        }
        String[] numbers = numberStr.split("\\.");
        if (numbers.length != 2) {
            throw new RuntimeException("numberStr must be a format number eg:99,999.99 or 99");
        }
        try {
            mNumberInt = Integer.parseInt(numbers[0]);
            mNumberDecimal = Integer.parseInt(numbers[1]);
            mDecimal2Bit = numbers[1].startsWith("0");
        } catch (Exception e) {
            mIntercept = true;
            setText(numberStrIn);
            return this;
        }

        if (mNumberInt > 1000) {
            mNumberIntStart = mNumberInt - (int) Math.pow(10, sizeOfInt(mNumberInt) - 1);
        } else {
            mNumberIntStart = mNumberInt / 2;
        }

        if (mNumberDecimal > 1000) {
            mNumberDecimalStart = mNumberDecimal - (int) Math.pow(10, sizeOfInt(mNumberDecimal) - 1);
        } else {
            mNumberDecimalStart = mNumberDecimal / 2;
        }
        return this;
    }

    /**
     * 设置动画播放时间
     *
     * @param duration 时长
     * @return 当前对象
     */
    public NumberEffectTextView setDuration(long duration) {
        this.mDuration = duration;
        return this;
    }

    /**
     * 是否支持小数部分
     *
     * @param enableDecimal 是否启用小数位数
     * @return 当前对象
     */
    public NumberEffectTextView enableDecimal(boolean enableDecimal) {
        this.mEnableDecimal = enableDecimal;
        return this;
    }

    /**
     * 开始跑数
     */
    public void start() {
        if (!mIntercept) {
            runNumber();
        }
    }

    /**
     * 跑数
     */
    private void runNumber() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mNumberIntStart, mNumberInt);
        valueAnimator.setDuration(mDuration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                int numberInt = Integer.parseInt(valueAnimator.getAnimatedValue().toString());
                final String numberIntStr = addComma(String.valueOf(numberInt));
                setText(numberIntStr + (mEnableDecimal ? ".00" : ""));
                if (valueAnimator.getAnimatedFraction() >= 1) {
                    if (mEnableDecimal) {
                        ValueAnimator va = ValueAnimator.ofInt(mNumberDecimalStart, mNumberDecimal);
                        va.setDuration(50);
                        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                int numberDecimal = Integer.parseInt(animation.getAnimatedValue().toString());
                                String numberDecimalStr = String.valueOf(numberDecimal);
                                if (numberDecimalStr.length() < 2) {
									if (mDecimal2Bit) {
                                        numberDecimalStr = "0" + numberDecimalStr;
                                    } else {
                                        numberDecimalStr = numberDecimalStr + "0";
                                    }
                                }
                                setText(String.valueOf(numberIntStr) + "." + numberDecimalStr);
                            }
                        });
                        va.start();
                    }
                }
            }
        });
        valueAnimator.start();
    }

    /**
     * 将每三个数字加上逗号处理
     *
     * @param numberIntStr 无逗号的数字
     * @return 加上逗号的数字
     */
    private String addComma(String numberIntStr) {
        // 将传进数字反转
        String reverseStr = new StringBuilder(numberIntStr).reverse().toString();
        String strTemp = "";
        for (int i = 0; i < reverseStr.length(); i++) {
            if (i * 3 + 3 > reverseStr.length()) {
                strTemp += reverseStr.substring(i * 3, reverseStr.length());
                break;
            }
            strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
        }
        if (strTemp.endsWith(",")) {// 去除最后一个,
            strTemp = strTemp.substring(0, strTemp.length() - 1);
        }
        // 将数字重新反转
        return new StringBuilder(strTemp).reverse().toString();
    }

    /**
     * 测量值
     *
     * @param x 当前值
     * @return 当前对象
     */
    static int sizeOfInt(int x) {
        for (int i = 0; ; i++) {
            if (x <= sizeTable[i])
                return i + 1;
        }
    }
}