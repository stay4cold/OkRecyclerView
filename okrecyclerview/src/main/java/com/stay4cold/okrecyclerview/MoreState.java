package com.stay4cold.okrecyclerview;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/12
 * Description:
 *      标示底部"加载更多"的状态
 */
public enum MoreState {
    //正常状态(即无状态),List还未滑动到需要加载的位置
    Normal,

    //加载完成，没有更多数据
    TheEnd,

    //加载中
    Loading,

    //网络异常或者其他异常情况，导致数据没有正常加载
    Error
}
