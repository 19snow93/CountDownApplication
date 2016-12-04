package com.example.vpcsd.countdownapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */

public class RecommendActivitiesAdpater extends RecyclerView.Adapter<RecommendActivitiesAdpater.MyViewHolder> {

    private RecyclerView mRecyclerView;

    private Context context;
    private List<RecommendActivitiesBean.ResultListBean> data;
    private View footerView;
    public static final int TYPE_FOOT = 1;
    public static final int TYPE_NOMAL = 0;
    public List<MyViewHolder> myViewHolderList = new ArrayList<>();

    private RecommendActivitiesAdpater.OnLoadMoreListener mOnLoadMoreListener;

    public RecommendActivitiesAdpater(Context context, List<RecommendActivitiesBean.ResultListBean> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public RecommendActivitiesAdpater.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_FOOT && footerView != null){
            view = footerView;
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_recommend_activities, parent, false);
        }
        return new RecommendActivitiesAdpater.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecommendActivitiesAdpater.MyViewHolder holder, final int position) {
        //用holder绑定对应的position
        holder.setDataPosition(position);
        //判断list里面是否含有该holder，没有就增加
        //因为list已经持有holder的引用，所有数据自动会改变
        if(!(myViewHolderList.contains(holder))){
            myViewHolderList.add(holder);
        }

        Glide.with(context).load(data.get(position).getPicAPP()).into(holder.ivRecommendActivitiesPic);
        holder.tvRecommendActivitiesTitle.setText(data.get(position).getName());
        holder.tvRecommendActivitiesCategory.setText("*租赁品类:" + data.get(position).getProductTypes());
        if(!TextUtils.isEmpty(data.get(position).getStartTime()) &&  !TextUtils.isEmpty(data.get(position).getEndTime())){
            String startTime = SystemUtil.timeChange(data.get(position).getStartTime());
            String endTime = SystemUtil.timeChange(data.get(position).getEndTime());
            holder.tvRecommendActivitiesTime.setText("*时间:" + startTime + " - " + endTime);
        }

        if(!TextUtils.isEmpty(data.get(position).getPrice())){
            String[] d = data.get(position).getPrice().split("-");
            if(d.length == 2){
                holder.tvRecommendActivitiesMoney.setText("*¥" + d[0] + "-¥" + d[1]);
            }else if(d.length == 1){
                holder.tvRecommendActivitiesMoney.setText("*¥" + d[0]);
            }else {
                holder.tvRecommendActivitiesMoney.setText("*¥0");
            }

        }

        if(data.get(position).getmType() == 1) {
            holder.llRecommendActivities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            holder.tvRecommendActivitiesPanic.setText("正在抢批");
            holder.tvRecommendActivitiesPanic.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.activity_btn));
            holder.ivRecommendActivityEndBac.setVisibility(View.GONE);

            holder.tvRecommendActivitiesStartTime.setText(data.get(position).getTime());
        }else if(data.get(position).getmType() == 2) {
            holder.llRecommendActivities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            holder.tvRecommendActivitiesPanic.setText("明日开抢");
            holder.tvRecommendActivitiesPanic.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.activity_unbtn));
            holder.ivRecommendActivityEndBac.setVisibility(View.GONE);
            holder.tvRecommendActivitiesStartTime.setText("未开始");
        }else if(data.get(position).getmType() == 3) {
            holder.llRecommendActivities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            holder.tvRecommendActivitiesPanic.setText("已结束");
            holder.tvRecommendActivitiesPanic.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.activity_unbtn));
            holder.ivRecommendActivityEndBac.setVisibility(View.VISIBLE);
            holder.tvRecommendActivitiesStartTime.setText("已结束");
        }
    }

    //遍历list，刷新相应holder的TextView
    public void notifyData(){
        for(int i = 0;i < myViewHolderList.size(); i++){
            myViewHolderList.get(i).tvRecommendActivitiesStartTime
                    .setText(data.get(myViewHolderList.get(i).position).getTime());
        }
    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 :data.size());
        if(footerView != null){
            count++;
        }
        return count;
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout llRecommendActivities;
        private ImageView ivRecommendActivitiesPic;
        private TextView tvRecommendActivitiesTitle,tvRecommendActivitiesCategory
                ,tvRecommendActivitiesStartTime,tvRecommendActivitiesMoney,tvRecommendActivitiesTime
                ,tvRecommendActivitiesPanic;
        private ImageView ivRecommendActivityEndBac;
        private int position;

        private void setDataPosition(int position){
            this.position = position;
        }

        public MyViewHolder(View view)
        {
            super(view);
            llRecommendActivities = (LinearLayout) view.findViewById(R.id.ll_recommend_activities);
            ivRecommendActivitiesPic = (ImageView) view.findViewById(R.id.iv_recommend_activities_pic);
            tvRecommendActivitiesTitle = (TextView) view.findViewById(R.id.tv_recommend_activities_title);
            tvRecommendActivitiesCategory = (TextView) view.findViewById(R.id.tv_recommend_activities_category);
            tvRecommendActivitiesStartTime = (TextView) view.findViewById(R.id.tv_recommend_activities_starttime);
            tvRecommendActivitiesMoney = (TextView) view.findViewById(R.id.tv_recommend_activities_money);
            tvRecommendActivitiesTime = (TextView) view.findViewById(R.id.tv_recommend_activities_time);
            tvRecommendActivitiesPanic = (TextView) view.findViewById(R.id.tv_recommend_activities_panic);
            ivRecommendActivityEndBac = (ImageView) view.findViewById(R.id.iv_recommend_activities_pic_end);
        }

    }


    @Override
    public int getItemViewType(int position) {
        if(footerView == null)
            return TYPE_NOMAL;
        if(position == getItemCount() - 1)
            return TYPE_FOOT;
        return TYPE_NOMAL;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if(!recyclerView.canScrollVertically(1)){
                        if(mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.loadMore();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFooterView(View footerView){
        this.footerView = footerView;
        footerView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        notifyItemInserted(getItemCount() - 1);
    }

    public View getFooterView(){
        return footerView;
    }

    public interface OnLoadMoreListener{
        void loadMore();
    }

    public void setOnLoadMoreListener(RecommendActivitiesAdpater.OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    private RecommendActivitiesAdpater.OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(RecommendActivitiesAdpater.OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
