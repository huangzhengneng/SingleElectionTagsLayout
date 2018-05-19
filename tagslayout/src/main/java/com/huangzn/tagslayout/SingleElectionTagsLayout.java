package com.huangzn.tagslayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by huangzhengneng on 2018/5/19.
 * <p>
 * 带选中状态的单选标签布局，基于AndroidTagsLayout(https://github.com/whilu/AndroidTagView)
 */

public class SingleElectionTagsLayout extends LinearLayout {

    private TextView tv_main_tag;
    private TagContainerLayout tag_view_layout;

    private List<String> mTagsTextList;
    private List<String> mTagsLayoutDataList;

    private int mTagBackground;

    public SingleElectionTagsLayout(Context context) {
        this(context, null);
    }

    public SingleElectionTagsLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleElectionTagsLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SingleElectionTagsLayout, defStyleAttr, 0);
        mTagBackground = typedArray.getResourceId(R.styleable.SingleElectionTagsLayout_tag_background_id, R.drawable.tag_view_bg);
        typedArray.recycle();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_category_tags, this, true);
        tv_main_tag = findViewById(R.id.tv_main_tag);
        // 默认选中
        tv_main_tag.setSelected(true);
        tv_main_tag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null
                        && isNotEmptyList(mTagsTextList)
                        && !tv_main_tag.isSelected()) {
                    tv_main_tag.setSelected(true);
                    mListener.onClick(0, mTagsTextList.get(0));
                    tag_view_layout.selectTag(-1);
                }
            }
        });
        tag_view_layout = findViewById(R.id.tag_view_layout);
        tag_view_layout.setTheme(ColorFactory.NONE);
        tag_view_layout.setTagBackgroundResource(mTagBackground);
        tv_main_tag.setBackgroundResource(mTagBackground);

    }

    public interface OnItemClickListener {
        /**
         * item的点击回调
         *
         * @param position
         * @param tagText
         */
        void onClick(int position, String tagText);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setTags(List<String> mTagsTextList) {
        this.mTagsTextList = mTagsTextList;
        mTagsLayoutDataList = new ArrayList<>();
        if (isNotEmptyList(mTagsTextList)) {
            // 给按钮和tagLayout赋值
            tv_main_tag.setText(mTagsTextList.get(0));
            if (mTagsTextList.size() > 1) {
                mTagsLayoutDataList.addAll(mTagsTextList.subList(1, mTagsTextList.size()));
            }
        }
        if (tag_view_layout != null && isNotEmptyList(mTagsLayoutDataList)) {
            tag_view_layout.setTags(mTagsLayoutDataList);
            tag_view_layout.setOnTagClickListener(new TagView.OnTagClickListener() {
                @Override
                public void onTagClick(TagView view, int position, String text) {
                    Log.d("test", "onTagClick: " + position + ", " + text);
                    if (mListener != null && !tag_view_layout.isRepeatClick(position)) {
                        tag_view_layout.invalidateTagsWhenClicked(position);
                        if (tv_main_tag.isSelected()) {
                            tv_main_tag.setSelected(false);
                        }
                        mListener.onClick(position + 1, text);
                    }
                }

                @Override
                public void onTagLongClick(int position, String text) {
                    Log.d("test", "onTagLongClick: " + position + ", " + text);
                }

                @Override
                public void onTagCrossClick(int position) {
                    Log.d("test", "onTagCrossClick: " + position);
                }
            });
        }
    }

    /**
     * 设置当前选中标签
     *
     * @param tagText 标签文本，须包含在setTags()方法传入的List中
     */
    public void setCurrentSelectedTag(String tagText) {
        if (!TextUtils.isEmpty(tagText)) {
            int index = mTagsTextList.indexOf(tagText);
            if (index > 0) {
                tag_view_layout.selectTag(index - 1);
            }
        }
    }

    private boolean isNotEmptyList(List list) {
        return list != null && list.size() > 0;
    }
}
