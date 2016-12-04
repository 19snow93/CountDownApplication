package com.example.vpcsd.countdownapplication;

import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */

public class RecommendActivitiesBean {

    /**
     * pageSize : 10
     * pageNo : 1
     * rowCount : 5
     * pageCount : 1
     * pageSizeList : [5,10,30,50,100]
     * resultList : [{"id":1,"shopName":null,"header":null,"name":null,"picAPP":null,"present":null,"sort":null,"productSource":null,"productTypes":null,"price":null,"startTime":"2016-06-04 15:40:53","endTime":"2016-07-04 15:40:53","productPager":null},{"id":2,"shopName":null,"header":null,"name":null,"picAPP":null,"present":null,"sort":null,"productSource":null,"productTypes":null,"price":null,"startTime":"2016-07-05 15:40:53","endTime":"2016-08-04 15:40:53","productPager":null},{"id":3,"shopName":null,"header":null,"name":null,"picAPP":null,"present":null,"sort":null,"productSource":null,"productTypes":null,"price":null,"startTime":"2016-10-10 15:40:53","endTime":"2016-09-06 15:40:53","productPager":null},{"id":4,"shopName":null,"header":null,"name":null,"picAPP":null,"present":null,"sort":null,"productSource":null,"productTypes":null,"price":null,"startTime":"2016-11-10 15:40:53","endTime":"2016-10-12 15:40:53","productPager":null},{"id":5,"shopName":null,"header":null,"name":null,"picAPP":null,"present":null,"sort":null,"productSource":null,"productTypes":null,"price":null,"startTime":"2017-01-10 15:40:53","endTime":"2016-12-15 15:40:53","productPager":null}]
     */

    private int pageSize;
    private int pageNo;
    private int rowCount;
    private int pageCount;
    private List<Integer> pageSizeList;
    /**
     * id : 1
     * shopName : null
     * header : null
     * name : null
     * picAPP : null
     * present : null
     * sort : null
     * productSource : null
     * productTypes : null
     * price : null
     * startTime : 2016-06-04 15:40:53
     * endTime : 2016-07-04 15:40:53
     * productPager : null
     */

    private List<ResultListBean> resultList;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<Integer> getPageSizeList() {
        return pageSizeList;
    }

    public void setPageSizeList(List<Integer> pageSizeList) {
        this.pageSizeList = pageSizeList;
    }

    public List<ResultListBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<ResultListBean> resultList) {
        this.resultList = resultList;
    }

    public static class ResultListBean {
        private int id;
        private String shopName;
        private String header;
        private String name;
        private String picAPP;
        private String present;
        private String sort;
        private String productSource;
        private String productTypes;
        private String price;
        private String startTime;
        private String endTime;
        private Object productPager;
        private String startLongTime;
        private String endLongTime;
        private String nowLongTime;
        private String nowTime;
        private long countTime;
        private String time;
        private int mType;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public long getCountTime() {
            return countTime;
        }

        public void setCountTime(long countTime) {
            this.countTime = countTime;
        }

        public String getNowTime() {
            return nowTime;
        }

        public void setNowTime(String nowTime) {
            this.nowTime = nowTime;
        }

        public String getNowLongTime() {
            return nowLongTime;
        }

        public void setNowLongTime(String nowLongTime) {
            this.nowLongTime = nowLongTime;
        }

        public String getStartLongTime() {
            return startLongTime;
        }

        public void setStartLongTime(String startLongTime) {
            this.startLongTime = startLongTime;
        }

        public String getEndLongTime() {
            return endLongTime;
        }

        public void setEndLongTime(String endLongTime) {
            this.endLongTime = endLongTime;
        }

        public int getmType() {
            return mType;
        }

        public void setmType(int mType) {
            this.mType = mType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicAPP() {
            return picAPP;
        }

        public void setPicAPP(String picAPP) {
            this.picAPP = picAPP;
        }

        public String getPresent() {
            return present;
        }

        public void setPresent(String present) {
            this.present = present;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getProductSource() {
            return productSource;
        }

        public void setProductSource(String productSource) {
            this.productSource = productSource;
        }

        public String getProductTypes() {
            return productTypes;
        }

        public void setProductTypes(String productTypes) {
            this.productTypes = productTypes;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Object getProductPager() {
            return productPager;
        }

        public void setProductPager(Object productPager) {
            this.productPager = productPager;
        }
    }
}
