package com.example.vpcsd.countdownapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rlvRecommendActivities;

    private RecommendActivitiesAdpater mRecommendActivitiesAdapter;

    private List<RecommendActivitiesBean.ResultListBean> mActivitiesList = new ArrayList<>();

    //活动状态：1.活动中，2.活动未开始，3活动已结束
    private int mType;
    private MyThread timeThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rlvRecommendActivities = (RecyclerView) findViewById(R.id.rlv_recommend_activities);
        rlvRecommendActivities.setLayoutManager(new LinearLayoutManager(this));
        mRecommendActivitiesAdapter = new RecommendActivitiesAdpater(this,mActivitiesList);
        rlvRecommendActivities.setAdapter(mRecommendActivitiesAdapter);

        mRecommendActivitiesAdapter.setOnLoadMoreListener(new RecommendActivitiesAdpater.OnLoadMoreListener() {
            @Override
            public void loadMore() {

            }
        });

        initData();

    }

    private void initData(){

        RecommendActivitiesBean.ResultListBean resultListBean1 = new RecommendActivitiesBean.ResultListBean();
        resultListBean1.setPicAPP("http://img2.3lian.com/2014/c7/25/d/41.jpg");
        resultListBean1.setName("牧马人键盘");
        resultListBean1.setProductSource("实力档口");
        resultListBean1.setProductTypes("键盘");
        resultListBean1.setStartTime("2016-10-01 12:30:30");
        resultListBean1.setEndTime("2017-01-15 18:50:23");
        resultListBean1.setPrice("30-186");
        resultListBean1.setNowTime("2016-12-04 15:24:43");
        resultListBean1.setmType(1);
        mActivitiesList.add(resultListBean1);

        RecommendActivitiesBean.ResultListBean resultListBean2 = new RecommendActivitiesBean.ResultListBean();
        resultListBean2.setPicAPP("http://imgsrc.baidu.com/forum/pic/item/b64543a98226cffc8872e00cb9014a90f603ea30.jpg");
        resultListBean2.setName("牧马人鼠标");
        resultListBean2.setProductSource("实力档口");
        resultListBean2.setProductTypes("鼠标");
        resultListBean2.setStartTime("2016-12-03 16:10:24");
        resultListBean2.setEndTime("2017-01-20 19:34:00");
        resultListBean2.setPrice("30-186");
        resultListBean2.setNowTime("2016-12-04 15:24:43");
        resultListBean2.setmType(1);
        mActivitiesList.add(resultListBean2);

        RecommendActivitiesBean.ResultListBean resultListBean3 = new RecommendActivitiesBean.ResultListBean();
        resultListBean3.setPicAPP("http://imgsrc.baidu.com/forum/pic/item/b64543a98226cffc8872e00cb9014a90f603ea30.jpg");
        resultListBean3.setName("艾瑞克鼠标");
        resultListBean3.setProductSource("实力档口");
        resultListBean3.setProductTypes("鼠标");
        resultListBean3.setStartTime("2016-12-03 16:10:24");
        resultListBean3.setEndTime("2016-12-10 14:34:23");
        resultListBean3.setPrice("30-186");
        resultListBean3.setNowTime("2016-12-04 15:24:43");
        resultListBean3.setmType(1);
        mActivitiesList.add(resultListBean3);


        for(int i = 0;i < 8; i++){
            RecommendActivitiesBean.ResultListBean resultListBean = new RecommendActivitiesBean.ResultListBean();
            resultListBean.setPicAPP("http://img2.3lian.com/2014/c7/25/d/40.jpg");
            resultListBean.setName("狼牙龙珠键盘");
            resultListBean.setProductSource("实力档口");
            resultListBean.setProductTypes("键盘");
            resultListBean.setStartTime("2016-11-01 15:40:53");
            resultListBean.setEndTime("2017-01-10 15:40:53");
            resultListBean.setNowTime("2016-12-04 15:24:53");
            resultListBean.setmType(1);
            mActivitiesList.add(resultListBean);
        }

        //遍历所有数据，算出时间差并保存在每个商品的counttime属性内
        for(int i = 0;i < mActivitiesList.size(); i++){
            long counttime = SystemUtil.timeDifference(mActivitiesList.get(i).getNowTime()
                    ,mActivitiesList.get(i).getEndTime());
            mActivitiesList.get(i).setCountTime(counttime);
        }
        timeThread = new MyThread(mActivitiesList);
        new Thread(timeThread).start();
        mRecommendActivitiesAdapter.notifyDataSetChanged();
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    //刷新适配器
                //    mRecommendActivitiesAdapter.notifyDataSetChanged();
                    //优化刷新adapter的方法
                    mRecommendActivitiesAdapter.notifyData();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    class MyThread implements Runnable{
        //用来停止线程
        boolean endThread;
        List<RecommendActivitiesBean.ResultListBean> mRecommendActivitiesList;

        public MyThread(List<RecommendActivitiesBean.ResultListBean> mRecommendActivitiesList){
            this.mRecommendActivitiesList = mRecommendActivitiesList;
        }

        @Override
        public void run() {
            while(!endThread){
                try{
                    Thread.sleep(1000);
                    for(int i = 0;i < mRecommendActivitiesList.size();i++){
                        //拿到每件商品的时间差，转化为具体的多少天多少小时多少分多少秒
                        //并保存在商品time这个属性内
                        long counttime = mRecommendActivitiesList.get(i).getCountTime();
                        long days = counttime / (1000 * 60 * 60 * 24);
                        long hours = (counttime-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
                        long minutes = (counttime-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
                        long second = (counttime-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60)-minutes*(1000*60))/1000;
                        //并保存在商品time这个属性内
                        String finaltime = days + "天" + hours + "时" + minutes + "分" + second + "秒";
                        mRecommendActivitiesList.get(i).setTime(finaltime);
                        //如果时间差大于1秒钟，将每件商品的时间差减去一秒钟，
                        // 并保存在每件商品的counttime属性内
                        if(counttime > 1000) {
                            mRecommendActivitiesList.get(i).setCountTime(counttime - 1000);
                        }
                    }
                    Message message = new Message();
                    message.what = 1;
                    //发送信息给handler
                    handler.sendMessage(message);
                }catch (Exception e){

                }
            }
        }
    }
}
