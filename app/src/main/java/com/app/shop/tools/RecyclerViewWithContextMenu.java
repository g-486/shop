package com.app.shop.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * create by 呵呵 on 2022/3/31.
 */
public class RecyclerViewWithContextMenu extends RecyclerView {
    private RecyclerViewContextInfo contextInfo=new RecyclerViewContextInfo();

    public RecyclerViewWithContextMenu(@NonNull Context context) {
        super(context);
    }

    public RecyclerViewWithContextMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewWithContextMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        LayoutManager layoutManager=getLayoutManager();
        if (layoutManager!=null){
            int position=layoutManager.getPosition(originalView);
            contextInfo.position=position;
        }
        return super.showContextMenuForChild(originalView);
    }

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return contextInfo;
    }

    public static class RecyclerViewContextInfo implements ContextMenu.ContextMenuInfo {
        private int position = -1;

        public int getPosition() {
            return position;
        }
    }


}
