package com.fy.companylibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.widget.editext.PointLengthFilter;
import com.fy.androidlibrary.widget.editext.SpaceFilter;
import com.fy.companylibrary.R;


/**
 * 作者： ${桑小年} on 2018/8/2.
 */
public class ItemTextView extends FrameLayout {

    String textLeft;
    String textCenter;
    String textRight;

    float sizeleft;
    float sizecenter;
    float sizeright;


    private boolean editable;
    private String textCenterHide;
    TextView tvLeft;
    EditText etCenter;
    TextView tvRight;
    ImageView imgGo;
    private View viewFound;
    private OnClickListener onClickListener;
    private int maxLength;
    private int centerType;
    private int centerGravity = -1;

    Paint mPaint;
    private boolean showLine;

    private int srcRight = -1;
    private boolean srcshow;
    private int colorRight;
    private int colorLeft;
    private boolean isSingleLine = true;
    private int colorCenter;
    private int colorCenterHide;
    private int srcLeftShow;//是否显示左侧必填数据
    private ImageView imgLeft;
    private View inflate;
    private boolean centerBold;

    OnCenterChangeListener changeListener;
    private float srcRightSize;
    private float srcLeftSize=-1;
    private int srcLeft;

    public ItemTextView(@NonNull Context context) {
        this(context, null, 0);
    }

    public ItemTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }


    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(getResources().getDimension(R.dimen.line_height));
        mPaint.setColor(getResources().getColor(R.color.color_of_c7c7c7));

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemTextView);
            sizeleft = ta.getDimension(R.styleable.ItemTextView_sizeleft, -1);
            sizecenter = ta.getDimension(R.styleable.ItemTextView_sizecenter, -1);
            sizeright = ta.getDimension(R.styleable.ItemTextView_sizeright, -1);
            textLeft = ta.getString(R.styleable.ItemTextView_textleft);
            textCenter = ta.getString(R.styleable.ItemTextView_textcenter);
            textCenterHide = ta.getString(R.styleable.ItemTextView_textcenterhide);
            textRight = ta.getString(R.styleable.ItemTextView_textright);
            editable = ta.getBoolean(R.styleable.ItemTextView_editable, true);
            showLine = ta.getBoolean(R.styleable.ItemTextView_showline, true);
            centerBold = ta.getBoolean(R.styleable.ItemTextView_centerBold, false);
            maxLength = ta.getInt(R.styleable.ItemTextView_centerLength, -1);
            centerType = ta.getInt(R.styleable.ItemTextView_centerType, 0);
            centerGravity = ta.getInt(R.styleable.ItemTextView_centerGravity, -1);
            srcRight = ta.getResourceId(R.styleable.ItemTextView_srcRight, 0);
            srcRightSize = ta.getDimension(R.styleable.ItemTextView_srcRightSize, DeviceUtils.dip2px(context,13));
            colorRight = ta.getColor(R.styleable.ItemTextView_colorRight, Color.parseColor("#333333"));
            colorCenter = ta.getColor(R.styleable.ItemTextView_colorCenter, Color.parseColor("#333333"));
            colorCenterHide = ta.getColor(R.styleable.ItemTextView_colorCenterHide, Color.parseColor("#c8c8c8"));
            colorLeft = ta.getColor(R.styleable.ItemTextView_colorLeft, Color.parseColor("#333333"));
            srcshow = ta.getBoolean(R.styleable.ItemTextView_srcshow, false);
            srcLeftShow = ta.getInt(R.styleable.ItemTextView_srcLeftshow, 0);
            srcLeft = ta.getResourceId(R.styleable.ItemTextView_srcLeft, 0);
            srcLeftSize = ta.getDimension(R.styleable.ItemTextView_srcLeftSize, -1);
            isSingleLine = ta.getBoolean(R.styleable.ItemTextView_isSingleLine, true);
            ta.recycle();
        }

        inflate = LayoutInflater.from(context).inflate(R.layout.common_item, this, false);
        addView(inflate);
        tvLeft = inflate.findViewById(R.id.tv_left);
        etCenter = inflate.findViewById(R.id.et_center);
        tvRight = inflate.findViewById(R.id.tv_right);
        imgGo = inflate.findViewById(R.id.img_go);
        imgLeft = inflate.findViewById(R.id.img_left);
        viewFound = inflate.findViewById(R.id.bg);

        setTextLeft(textLeft);
        setTextRight(textRight);
        setTextCenterHide(textCenterHide);
        setTextCenter(textCenter);
        setTextCenterGravite(centerGravity);

        if (srcRight != 0) {
            setSrcRight(srcRight);
        }
        setSrcshow(srcshow);
        setColorRight(colorRight);
        setColorRightSize(srcRightSize);
        setColorLeft(colorLeft);
        setColorCenter(colorCenter);
        setColorCenterHide(colorCenterHide);

        setSrcLeft(srcLeft);
        setSrcLeftSize(srcLeftSize);
        setSRCLeftShow(srcLeftShow);

        setTextSizeLeft(sizeleft);
        setTextSizeCenter(sizecenter);
        setTextSizeRight(sizeright);

        setIsSingleLine(isSingleLine);
        setCenter(maxLength, centerType);
        setEditable(editable);
        setTextCenterBold(centerBold);

        etCenter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (changeListener != null) {
                    changeListener.onCenterChange(s.toString());
                }
            }
        });

    }

    private void setSrcLeftSize(float srcLeftSize) {
        if (srcLeft>0){
            this.srcLeftSize = srcLeftSize;
            ViewGroup.LayoutParams params = imgLeft.getLayoutParams();
            params.width = (int) srcLeftSize;
            params.height = (int) srcLeftSize;
            imgLeft.setLayoutParams(params);
        }
    }

    public void setSrcLeft(int srcLeft) {
        if (srcLeft!=0){
            imgLeft.setImageResource(srcLeft);
        }
    }

    public void setColorRightSize(float srcRightSize) {
        this.srcRightSize = srcRightSize;
        ViewGroup.LayoutParams params = imgGo.getLayoutParams();
        params.width = (int) srcRightSize;
        params.height = (int) srcRightSize;
        imgGo.setLayoutParams(params);
    }

    public void setTextCenterGravite(int centerGravity) {
        switch (centerGravity) {
            case 0:
                etCenter.setGravity(Gravity.LEFT);
                break;
            case 1:
                etCenter.setGravity(Gravity.RIGHT);
                break;
            case 2:
                etCenter.setGravity(Gravity.CENTER);
                break;
            case 3:
                etCenter.setGravity(Gravity.TOP);
                break;
            case 4:
                etCenter.setGravity(Gravity.BOTTOM);
                break;
        }
    }

    public void setTextSizeRight(float sizeright) {
        if (sizeright > 0) {
            this.sizeright = sizeright;
            tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeright);
        }
    }

    public void setTextSizeCenter(float sizecenter) {
        if (sizecenter > 0) {
            this.sizecenter = sizecenter;
            etCenter.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizecenter);
        }
    }

    public void setTextSizeLeft(float sizeleft) {
        if (sizeleft > 0) {
            this.sizeleft = sizeleft;
            tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeleft);
        }
    }

    public void setSRCLeftShow(int srcLeftShow) {
        this.srcLeftShow = srcLeftShow;
        switch (srcLeftShow) {
            case 0:
                imgLeft.setVisibility(GONE);
                break;
            case 1:
                imgLeft.setVisibility(INVISIBLE);
                break;
            case 2:
                imgLeft.setVisibility(VISIBLE);
                break;
        }
    }

    public void setIsSingleLine(boolean isSingleLine) {
        this.isSingleLine = isSingleLine;
        etCenter.setSingleLine(isSingleLine);
//        etCenter.setMaxLines(isSingleLine ? 1 : Integer.MAX_VALUE);
//        etCenter.setMaxLines(Integer.MAX_VALUE);
    }

    private void setColorRight(int colorRight) {
        this.colorRight = colorRight;
        if (colorRight != 0) {
            tvRight.setTextColor(colorRight);
        }
    }

    private void setColorLeft(int colorLeft) {
        this.colorLeft = colorLeft;
        if (colorLeft != 0) {
            tvLeft.setTextColor(colorLeft);
        }
    }

    private void setColorCenter(int colorCenter) {
        this.colorCenter = colorCenter;
        if (colorCenter != 0) {
            etCenter.setTextColor(colorCenter);
        }
    }

    private void setColorCenterHide(int colorCenterHide) {
        this.colorCenterHide = colorCenterHide;
        if (colorCenterHide != 0) {
            etCenter.setHintTextColor(colorCenterHide);
        }
    }

    public void setSrcRight(int srcRight) {
        this.srcRight = srcRight;
        imgGo.setImageResource(srcRight);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (showLine) {
            canvas.drawLine(tvLeft.getLeft(), getMeasuredHeight() - mPaint.getStrokeWidth() / 2, getMeasuredWidth(), getMeasuredHeight() - mPaint.getStrokeWidth() / 2, mPaint);
        }
    }

    private void setCenter(int maxLength, int centerType) {
        if (centerType == 1) {//手机号
            etCenter.setInputType(InputType.TYPE_CLASS_PHONE);
            etCenter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength < 0 ? 11 : maxLength)});
            etCenter.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        } else if (centerType == 2) {//身份证号

            etCenter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength < 0 ? 18 : maxLength)});
            etCenter.setKeyListener(DigitsKeyListener.getInstance("xX0123456789"));
        } else if (centerType == 3) {//数字
            etCenter.setFilters(new InputFilter[]{new PointLengthFilter()});
            etCenter.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//            etCenter.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        } else if (centerType == 4) {//密码
            etCenter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength < 0 ? Integer.MAX_VALUE : maxLength), new SpaceFilter()});
            etCenter.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        }else if (centerType == 5) {//密码
            etCenter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength < 0 ? Integer.MAX_VALUE : maxLength), new SpaceFilter()});
            etCenter.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            etCenter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength < 0 ? Integer.MAX_VALUE : maxLength), new SpaceFilter()});
        }
    }

    public void setCenterType(int centerType) {
        this.centerType = centerType;
        setCenter(maxLength,centerType);
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        setCenter(maxLength,centerType);

    }

    public void setEditable(boolean editable) {

        this.editable = editable;
        if (editable) {
            viewFound.setVisibility(GONE);
            viewFound.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    etCenter.requestFocus();
                    etCenter.setSelection(etCenter.getText().toString().length());
                    DeviceUtils.openSoft(etCenter);

                }
            });
        } else {
            viewFound.setVisibility(VISIBLE);
            etCenter.clearFocus();
            viewFound.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(ItemTextView.this);
                    }
                }
            });
        }

        etCenter.setFocusableInTouchMode(editable);
        etCenter.setFocusable(editable);
        inflate.setFocusableInTouchMode(!editable);
        inflate.setFocusable(!editable);


        if (!isEnabled()) {
            viewFound.setVisibility(VISIBLE);
            viewFound.setOnClickListener(null);
            etCenter.setTextColor(colorCenterHide);
            tvLeft.setTextColor(colorCenterHide);
            tvRight.setTextColor(colorCenterHide);
        } else {
            etCenter.setTextColor(colorCenter);
            tvLeft.setTextColor(colorLeft);
            tvRight.setTextColor(colorRight);
        }


    }

    public void setSrcshow(boolean srcshow) {
        this.srcshow = srcshow;
        imgGo.setVisibility(!srcshow ? GONE : VISIBLE);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        onClickListener = l;

    }

    public void setTextRight(String textRight) {
        this.textRight = textRight;
        tvRight.setText(textRight == null ? "" : textRight);
    }

    public void setTextCenterHide(String textCenterHide) {
        this.textCenterHide = textCenterHide;
        etCenter.setHint(textCenterHide == null ? "" : textCenterHide);
    }

    public void setTextCenter(String textCenter) {
        this.textCenter = textCenter;
        etCenter.setText(textCenter == null ? "" : textCenter);
    }


    public void setTextCenter(CharSequence textCenter) {
        etCenter.setText(textCenter == null ? "" : textCenter);
    }

    public void setTextLeft(String textLeft) {
        this.textLeft = textLeft;
        tvLeft.setText(textLeft == null ? "" : textLeft);
        if (TextUtils.isEmpty(textLeft)) {
            tvLeft.setVisibility(GONE);
        } else {
            tvLeft.setVisibility(VISIBLE);
        }
    }

    public String getTextLeft() {

        return textLeft = tvLeft.getText().toString().trim();
    }


    public String getTextCenter() {
        return textCenter = etCenter.getText().toString().trim();
    }

    public EditText getEtCenter() {
        return etCenter;
    }

    public String getTextRight() {
        return textRight = tvRight.getText().toString().trim();
    }

    public boolean isEditable() {
        return editable;
    }

    public String getTextCenterHide() {
        return textCenterHide = etCenter.getHint().toString().trim();
    }


    public void setOnCenterChangeListener(OnCenterChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setEditable(editable);
    }


    public void setTextCenterBold(boolean centerBold) {
        etCenter.setTypeface(Typeface.defaultFromStyle(centerBold ? Typeface.BOLD : Typeface.NORMAL));
    }



    public interface OnCenterChangeListener {
        void onCenterChange(String msg);
    }


}
