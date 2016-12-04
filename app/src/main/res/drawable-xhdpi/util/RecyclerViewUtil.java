package com.messcat.shisanhang.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


/**
 * Created by admin on 2016/4/7.
 */
public class RecyclerViewUtil {

    public static int getHeaderHeight(RecyclerView recyclerView){
        int headerCildHeight = 0;

        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstHeaderPos = layoutManager.findFirstCompletelyVisibleItemPosition();
        View headerCild = layoutManager.findViewByPosition(firstHeaderPos);

        if(headerCild != null){
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) headerCild.getLayoutParams();
            headerCildHeight = headerCild.getHeight() + params.topMargin + params.bottomMargin;
        }
        return  headerCildHeight;
    }

    public static int getItemHeight(RecyclerView recyclerView) {
        int itemHeight = 0;
        View child = null;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            int firstPos = layoutManager.findFirstCompletelyVisibleItemPosition();
            child = layoutManager.findViewByPosition(firstPos);
        } else {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int firstPos = layoutManager.findFirstCompletelyVisibleItemPosition();
            int lastPos = layoutManager.findLastCompletelyVisibleItemPosition();
            child = layoutManager.findViewByPosition(lastPos);
        }

        if (child != null) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            itemHeight = child.getHeight() + params.topMargin + params.bottomMargin;
        }

        return itemHeight;
    }

    public static int getItemWidth(RecyclerView recyclerView) {
        int itemWidth = 0;
        View child = null;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            int firstPos = layoutManager.findFirstCompletelyVisibleItemPosition();
            child = layoutManager.findViewByPosition(firstPos);
        } else {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int firstPos = layoutManager.findFirstCompletelyVisibleItemPosition();
            child = layoutManager.findViewByPosition(firstPos);
        }

        if (child != null) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            itemWidth = child.getWidth() + params.leftMargin + params.rightMargin;
        }

        return itemWidth;
    }

    public static int getGridScrollY(RecyclerView recyclerView) {
        int scrollY = 0;
        GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        int firstPos = layoutManager.findFirstVisibleItemPosition();
        int spanCount = layoutManager.getSpanCount();
        View child = layoutManager.findViewByPosition(firstPos);
        if (child != null) {
            int line = firstPos / spanCount;
            int itemHeght = getItemHeight(recyclerView);
            int firstItemBottom = layoutManager.getDecoratedBottom(child);

        //    Log.e("RecyclerViewUtil", "firstPos:" + firstPos + "  itemHeight:" + itemHeght + "  top:" + firstItemBottom);
            scrollY = itemHeght * (line + 1) - firstItemBottom;

            int firstComPos = layoutManager.findFirstCompletelyVisibleItemPosition();
            if (firstComPos == 0) {
                scrollY = 0;
            }
        }

        return scrollY;
    }

    public static int getGridTotalHeight(RecyclerView recyclerView) {
        int totalHeight = 0;

        GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        int spanCount = layoutManager.getSpanCount();
        View child = layoutManager.findViewByPosition(layoutManager.findFirstVisibleItemPosition());
        if (child != null) {
            int itemHeight = getItemHeight(recyclerView);
            int childCount = layoutManager.getItemCount();
            int line = childCount / spanCount;
            if (childCount % spanCount != 0) {
                line++;
            }
            totalHeight = line * itemHeight;
        }

        return totalHeight;
    }

    public static boolean isGridBottom(RecyclerView recyclerView) {
        boolean isBottom = true;
        int scrollY = getGridScrollY(recyclerView);
        int totalHeight = getGridTotalHeight(recyclerView);
        int height = recyclerView.getHeight();
        Log.e("RecyclerViewUtil", "totalHeight:" + totalHeight + "  height:" + height + "  scrollY:" + scrollY);

        if (scrollY + height < totalHeight) {
            isBottom = false;
        }

        return isBottom;
    }

        public static int getLinearScrollY(RecyclerView recyclerView) {
        int scrollY = 0;
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int headerCildHeight = getHeaderHeight(recyclerView);
        int firstPos = layoutManager.findFirstVisibleItemPosition();
        View child = layoutManager.findViewByPosition(firstPos);
        int itemHeight = getItemHeight(recyclerView);
        if (child != null) {
            int firstItemBottom = layoutManager.getDecoratedBottom(child);
            scrollY = headerCildHeight + itemHeight * firstPos - firstItemBottom;
            if(scrollY < 0){
                scrollY = 0;
            }
        }

        return scrollY;
    }

    public static int getLinearTotalHeight(RecyclerView recyclerView) {
        int totalHeight = 0;

        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        View child = layoutManager.findViewByPosition(layoutManager.findFirstVisibleItemPosition());
        int headerCildHeight = getHeaderHeight(recyclerView);

        if (child != null) {
            int itemHeight = getItemHeight(recyclerView);
            int childCount = layoutManager.getItemCount();
            totalHeight = headerCildHeight + (childCount - 1) * itemHeight;
        }

        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();

//        Log.e("onScrollStateChanged", "visibleItemCount " + visibleItemCount);
//        Log.e("onScrollStateChanged", "lastVisibleItemPosition " + lastVisibleItemPosition);
//        Log.e("onScrollStateChanged", "totalItemCount " + totalItemCount);

        return totalHeight;
    }

    public static boolean isLinearBottom(RecyclerView recyclerView) {
        boolean isBottom = true;
        int scrollY = getLinearScrollY(recyclerView);
        int totalHeight = getLinearTotalHeight(recyclerView);
        int height = recyclerView.getHeight();

    //    Log.e("height","scrollY  " + scrollY + "  totalHeight  " + totalHeight + "  recyclerHeight  " + height);

        if (scrollY + height < totalHeight) {
            isBottom = false;
        }

        return isBottom;
    }

    public static boolean isGridTop(RecyclerView recyclerView) {
        boolean isTop = false;

        int scrollY = getGridScrollY(recyclerView);
        if (scrollY == 0) {
            isTop = true;
        }

        return isTop;
    }

    public static boolean isLinearTop(RecyclerView recyclerView) {
        boolean isTop = false;

        int scrollY = getLinearScrollY(recyclerView);
        if (scrollY == 0) {
            isTop = true;
        }

        return isTop;
    }

    public static int getScrollY(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return getGridScrollY(recyclerView);
        } else if (layoutManager instanceof LinearLayoutManager) {
            return getLinearScrollY(recyclerView);
        }

        return 0;
    }

    public static int getTotalHeight(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return getGridTotalHeight(recyclerView);
        } else if (layoutManager instanceof LinearLayoutManager) {
            return getLinearTotalHeight(recyclerView);
        }

        return 0;
    }

    public static boolean isBottom(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return isGridBottom(recyclerView);
        } else if (layoutManager instanceof LinearLayoutManager) {
            return isLinearBottom(recyclerView);
        }

        return true;
    }

    public static boolean isTop(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return isGridTop(recyclerView);
        } else if (layoutManager instanceof LinearLayoutManager) {
            return isLinearTop(recyclerView);
        }

        return true;
    }


    public static int getGridScrollX(RecyclerView recyclerView) {
        int scrollX = 0;
        GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        int spanCount = layoutManager.getSpanCount();
        View child = layoutManager.findViewByPosition(layoutManager.findFirstVisibleItemPosition());
        if (child != null) {
            int firstPos = layoutManager.findFirstVisibleItemPosition();
            int line = firstPos / spanCount;
            int itemWidth = getItemWidth(recyclerView);
            scrollX = itemWidth * line - child.getLeft();
        }

        return scrollX;
    }

    public static int getGridTotalWidth(RecyclerView recyclerView) {
        int totalWidth = 0;

        GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        int spanCount = layoutManager.getSpanCount();
        View child = layoutManager.findViewByPosition(layoutManager.findFirstVisibleItemPosition());
        if (child != null) {
            int itemWidth = getItemWidth(recyclerView);
            int childCount = layoutManager.getItemCount();
            int line = childCount / spanCount;
            if (childCount % spanCount != 0) {
                line++;
            }
            totalWidth = line * itemWidth;
        }

        return totalWidth;
    }

    public static boolean isGridRight(RecyclerView recyclerView) {
        boolean isRight = true;
        int scrollX = getGridScrollX(recyclerView);
        int totalWidth = getGridTotalWidth(recyclerView);
        int height = recyclerView.getHeight();

        if (scrollX + height < totalWidth) {
            isRight = false;
        }

        return isRight;
    }

    public static int getLinearScrollX(RecyclerView recyclerView) {
        int scrollX = 0;
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        View child = layoutManager.findViewByPosition(layoutManager.findFirstVisibleItemPosition());
        if (child != null) {
            int firstPos = layoutManager.findFirstVisibleItemPosition();
            int itemWidth = getItemWidth(recyclerView);
            scrollX = itemWidth * firstPos - child.getLeft();
        }

        return scrollX;
    }

    public static int getLinearTotalWidth(RecyclerView recyclerView) {
        int totalWidth = 0;

        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        layoutManager.canScrollVertically();
        View child = layoutManager.findViewByPosition(layoutManager.findFirstVisibleItemPosition());
        if (child != null) {
            int itemWidth = getItemWidth(recyclerView);
            int childCount = layoutManager.getItemCount();
            totalWidth = childCount * itemWidth;
        }

        return totalWidth;
    }

    public static boolean isLinearRight(RecyclerView recyclerView) {
        boolean isRight = true;
        int scrollX = getLinearScrollX(recyclerView);
        int totalWidth = getLinearTotalWidth(recyclerView);
        int width = recyclerView.getWidth();

        if (scrollX + width < totalWidth) {
            isRight = false;
        }

        return isRight;
    }

    public static boolean isGridLeft(RecyclerView recyclerView) {
        boolean isLeft = false;

        int scrollX = getGridScrollX(recyclerView);
        if (scrollX == 0) {
            isLeft = true;
        }

        return isLeft;
    }

    public static boolean isLinearLeft(RecyclerView recyclerView) {
        boolean isLeft = false;

        int scrollX = getLinearScrollX(recyclerView);
        if (scrollX == 0) {
            isLeft = true;
        }

        return isLeft;
    }

    public static int getScrollX(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return getGridScrollX(recyclerView);
        } else if (layoutManager instanceof LinearLayoutManager) {
            return getLinearScrollX(recyclerView);
        }

        return 0;
    }

    public static int getTotalWidth(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return getGridTotalWidth(recyclerView);
        } else if (layoutManager instanceof LinearLayoutManager) {
            return getLinearTotalWidth(recyclerView);
        }

        return 0;
    }

    public static boolean isRight(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return isGridRight(recyclerView);
        } else if (layoutManager instanceof LinearLayoutManager) {
            return isLinearRight(recyclerView);
        }

        return true;
    }

    public static boolean isLeft(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return isGridLeft(recyclerView);
        } else if (layoutManager instanceof LinearLayoutManager) {
            return isLinearLeft(recyclerView);
        }

        return true;
    }

    public static boolean isVisBottom(RecyclerView recyclerView){
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
        int state = recyclerView.getScrollState();
        if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE){
            return true;
        }else {
            return false;
        }

    }


    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        Log.e("onScrollStateChanged", "computeVerticalScrollExtent " + recyclerView.computeVerticalScrollExtent());
        Log.e("onScrollStateChanged", "computeVerticalScrollOffset " + recyclerView.computeVerticalScrollOffset());
        Log.e("onScrollStateChanged", "computeVerticalScrollRange " + recyclerView.computeVerticalScrollRange());

        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}
