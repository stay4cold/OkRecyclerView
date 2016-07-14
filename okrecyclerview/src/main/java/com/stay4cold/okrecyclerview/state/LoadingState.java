package com.stay4cold.okrecyclerview.state;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/12
 * Description:
 * 第一次进入界面或者下拉刷新时显示的状态
 */
public enum LoadingState {
    //正常状态(数据加载成功),正常显示Recyclerview
    Normal,

    //没有数据
    Empty,

    //加载中
    Loading,

    //网络异常或者其他异常情况，导致数据加载失败(只在第一次进入界面和下拉刷新时调用)
    Error
}
