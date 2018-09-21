package com.toobei.common.entity;

/**
 * Created by Administrator on 2016/11/10 0010.
 */
public class LiecaiHasUnReadActivitiesDetail {
    /**
     * activityReaded	活动	number
     * classroomReaded	课堂	number
     * liecaiReaded	貅比特	number
     * newsReaded	资讯	number
     * unFinishNewcomerTaskCount	未完成新手任务数量	number
     */
    private String activityReaded;
    private String newsReaded;
    private String classroomReaded;
    private String liecaiReaded;
    private String unFinishNewcomerTaskCount;

//    activityReaded	活动	number
//    liecaiReaded	貅比特	number
//    newsReaded	资讯	number

    public String getActivityReaded() {
        return activityReaded;
    }

    public void setActivityReaded(String activityReaded) {
        this.activityReaded = activityReaded;
    }

    public String getNewsReaded() {
        return newsReaded;
    }

    public void setNewsReaded(String newsReaded) {
        this.newsReaded = newsReaded;
    }

    public String getLiecaiReaded() {
        return liecaiReaded;
    }

    public void setLiecaiReaded(String liecaiReaded) {
        this.liecaiReaded = liecaiReaded;
    }

    public String getClassroomReaded() {
        return classroomReaded;
    }

    public void setClassroomReaded(String classroomReaded) {
        this.classroomReaded = classroomReaded;
    }

    public String getUnFinishNewcomerTaskCount() {
        return unFinishNewcomerTaskCount;
    }

    public void setUnFinishNewcomerTaskCount(String unFinishNewcomerTaskCount) {
        this.unFinishNewcomerTaskCount = unFinishNewcomerTaskCount;
    }
}
