package com.hzsoft.lib.picture.listener;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author：zhou
 * @date：2022-06-30 17:58
 * @describe：长按事件
 */
public interface OnItemLongClickListener {
    void onItemLongClick(RecyclerView.ViewHolder holder, int position, View v);
}
