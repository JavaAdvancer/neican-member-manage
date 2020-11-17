package com.jiyou.nm.common.entity;

import java.io.Serializable;

/**
 * 极光推送信息
 *
 * @author CHQ
 * @create 2017-11-15 9:53
 **/
public class JpushMessage implements Serializable {

    /**
     * 极优铺货
     */
    public static final String APPKEY = "5245b01f1f6455d678fedb10";
    public static final String MASTERSECRET = "eb2960cd5172ba819a123ebe";

    /**
     * 极优库管
     */
    public static final String APPKEY_KG = "1c8c6f9be1558b9b5a4542ce";
    public static final String MASTERSECRET_KG = "cad40f51155f53404b51f9f6";

    /**
     * 极优BI
     */
    public static final String APPKEY_BI = "20652856952271e78d8742eb";
    public static final String MASTERSECRET_BI = "7f0c5ab725fe266e89fe3626 ";

    /**
     * 推送方向===》铺货
     */
    public static final Integer PUSH_TYPE_PH = 0;
    /**
     * 推送方向===》BI
     */
    public static final Integer PUSH_TYPE_BI = 1;
    /**
     * 推送方向===》库管
     */
    public static final Integer PUSH_TYPE_KG = 2;

    /**
     * 0:铺货;1:BI;2:库管
     */
    private int pushType;

    /**
     * 消息内容
     */

    private String content;

    /**
     * 用户ID
     * 每个用户只能指定一个别名。
     * 同一个应用程序内，对不同的用户，建议取不同的别名。
     * 这样，尽可能根据别名来唯一确定用户。
     */
    private String alias;

    /**
     * 消息推送中使用，用于app端区别页面跳转
     */

    private String appShowType;

    /**
     * app端声音文件
     */
    private String sound;

    /**
     * 消息类型(1:新订单通知;2:调拨请求提醒;3:调拨完成提醒;4:订单分派提醒5:已缴款提醒;7:调拨审核完成;8:公告))
     */
    private String messageType;

    /**
     * 编号
     */
    private String code;

    /**
     * 标题
     */
    private String title;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAppShowType() {
        return appShowType;
    }

    public void setAppShowType(String appShowType) {
        this.appShowType = appShowType;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public int getPushType() {
        return pushType;
    }

    public void setPushType(int pushType) {
        this.pushType = pushType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
