package com.superpeer.tutuyoudian.redbag;

/**
 * @author ChayChan
 * @description: 红包
 * @date 2017/11/28  14:23
 */

public class RedPacketEntity{
    public String name;
    public int avatar;
    public String remark;

    public RedPacketEntity(String name, int avatar, String remark) {
        this.name = name;
        this.avatar = avatar;
        this.remark = remark;
    }
}
