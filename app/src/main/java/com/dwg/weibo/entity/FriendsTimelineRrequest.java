package com.dwg.weibo.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/13.
 */

public class FriendsTimelineRrequest {
    public static  String access_token = "";
    public static String since_id = "0";
    public static String max_id = "0";
    public static final int count = 30;
    public static int  page = 1;
    public static int base_app = 0;
    public static int feature = 0;
    public static int trim_user = 0;
    private static Object sinceId;

    public static String getSinceId(ArrayList<Status> statuses) {
        if(statuses!=null&&statuses.size()>0){
            return statuses.get(0).id;
        }else{
            return since_id;
        }
    }

    public static String getMaxId(ArrayList<Status> statuses) {
        if (statuses != null && statuses.size() > 0) {
            return statuses.get(statuses.size() - 1).id;
        } else {
            return max_id;
        }
    }
}
