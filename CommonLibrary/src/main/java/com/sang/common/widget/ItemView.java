package com.sang.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TextKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sang.common.R;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.JLog;
import com.sang.common.utils.PointLengthFilter;
import com.sang.common.utils.SpaceFilter;

/**
 * 作者： ${桑小年} on 2018/8/2.
 * 努力，为梦长留
 */
public class ItemView extends FrameLayout {

    String textLeft;
    String textCenter;
    String textRight;
    private boolean editable;
    private String textCenterHide;
    TextView tvLeft;
    EditText etCenter;
    TextView tvRight;
    ImageView imgGo;
    private View viewFound;
    private OnClickListener onClickListenr;
    private int maxLength;
    private int centerType;

    Paint mPaint;
    private boolean showLine;

    private int srcRight = -1;
    private boolean srcshow;
    private int colorRight;
    private boolean isSingleLine = true;

    public ItemView(@NonNull Context context) {
        this(context, null, 0);
    }

    public ItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }


    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(getResources().getDimension(R.dimen.line_height));
        mPaint.setColor(getResources().getColor(R.color.color_line));

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
            textLeft = ta.getString(R.styleable.ItemView_textleft);
            textCenter = ta.getString(R.styleable.ItemView_textcenter);
            textCenterHide = ta.getString(R.styleable.ItemView_textcenterhide);
            textRight = ta.getString(R.styleable.ItemView_textright);
            editable = ta.getBoolean(R.styleable.ItemView_editable, true);
            showLine = ta.getBoolean(R.styleable.ItemView_showline, true);
            maxLength = ta.getInt(R.styleable.ItemView_centerLength, -1);
            centerType = ta.getInt(R.styleable.ItemView_centerType, 0);
            srcRight = ta.getInt(R.styleable.ItemView_srcRight, -1);
            colorRight = ta.getInt(R.styleable.ItemView_colorRight, 0);
            srcshow = ta.getBoolean(R.styleable.ItemView_srcshow, false);
            isSingleLine = ta.getBoolean(R.styleable.ItemView_isSingleLine, true);
            ta.recycle();
        }

        View inflate = LayoutInflater.from(context).inflate(R.layout.common_item, this, false);
        addView(inflate);
        tvLeft = inflate.findViewById(R.id.tv_left);
        etCenter = inflate.findViewById(R.id.et_center);
        tvRight = inflate.findViewById(R.id.tv_right);
        imgGo = inflate.findViewById(R.id.img_go);
        viewFound = inflate.findViewById(R.id.bg);

        setTextLeft(textLeft);
        setTextRight(textRight);
        setTextCenterHide(textCenterHide);
        setTextCenter(textCenter);
        setEditable(editable);
        setSrcRight(srcRight);
        setSrcshow(srcshow);
        setColorRight(colorRight);
        setIsSingleLine(isSingleLine);
        setCenter(maxLength, centerType);

    }

    public void setIsSingleLine(boolean isSingleLine) {
        this.isSingleLine = isSingleLine;
        etCenter.setMaxLines(isSingleLine ? 1 : Integer.MAX_VALUE);
        etCenter.setSingleLine(isSingleLine);
    }

    private void setColorRight(int colorRight) {
        this.colorRight = colorRight;
        if (colorRight != 0) {
            tvRight.setTextColor(colorRight);
        }
    }

    public void setSrcRight(int srcRight) {
        this.srcRight = srcRight;
        if (srcRight > 0) {
            imgGo.setImageResource(srcRight);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (showLine) {
            canvas.drawLine(getPaddingLeft(), getMeasuredHeight() - mPaint.getStrokeWidth() / 2, getMeasuredWidth(), getMeasuredHeight() - mPaint.getStrokeWidth() / 2, mPaint);
        }
    }

    private void setCenter(int maxLength, int centerType) {
        if (centerType == 1) {//手机号
            etCenter.setInputType(InputType.TYPE_CLASS_PHONE);
            etCenter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
            etCenter.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        } else if (centerType == 2) {//身份证号
            etCenter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
            etCenter.setKeyListener(DigitsKeyListener.getInstance("xX0123456789"));
        } else if (centerType == 3) {//数字
            etCenter.setFilters(new InputFilter[]{new PointLengthFilter()});
            etCenter.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
//            etCenter.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        } else {
            if (maxLength > 0) {
                etCenter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength),new SpaceFilter()});
            }else {
                etCenter.setFilters(new InputFilter[]{ new SpaceFilter()});

            }
        }
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
            etCenter.setInputType(InputType.TYPE_NULL);
            viewFound.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListenr != null) {
                        onClickListenr.onClick(ItemView.this);
                    }
                }
            });
        }
    }

    public void setSrcshow(boolean srcshow) {
        this.srcshow = srcshow;
        imgGo.setVisibility(!srcshow ? GONE : VISIBLE);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        onClickListenr = l;

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

    public void setSingleLine(boolean sing) {
        etCenter.setSingleLine(showLine);
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
}
