package com.d2cmall.buyer.bean;


/**
 * 当前直播信息页面
 */
public class LiveInfo {
    private static int members;
    private static int admires;
    private static String title;
    private static double lat1;
    private static double long1;
    private static String address = "";
    private static String coverurl = "";

    public static int roomNum;

    public static String hostID;
    public static String hostName;
    public static String hostAvator;

    public static int currentRequestCount = 0;

    public static int getCurrentRequestCount() {
        return currentRequestCount;
    }

    public static int getIndexView() {
        return indexView;
    }

    public static void setIndexView(int indexView) {
        LiveInfo.indexView = indexView;
    }

    public static int indexView = 0;

    public static void setCurrentRequestCount(int currentRequestCount) {
        LiveInfo.currentRequestCount = currentRequestCount;
    }

    public static String getHostID() {
        return hostID;
    }

    public static void setHostID(String hostID) {
        LiveInfo.hostID = hostID;
    }

    public static String getHostName() {
        return hostName;
    }

    public static void setHostName(String hostName) {
        LiveInfo.hostName = hostName;
    }

    public static String getHostAvator() {
        return hostAvator;
    }

    public static void setHostAvator(String hostAvator) {
        LiveInfo.hostAvator = hostAvator;
    }

    public static int getMembers() {
        return members;
    }

    public static void setMembers(int members) {
        LiveInfo.members = members;
    }

    public static int getAdmires() {
        return admires;
    }

    public static void setAdmires(int admires) {
        LiveInfo.admires = admires;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        LiveInfo.title = title;
    }

    public static double getLat1() {
        return lat1;
    }

    public static void setLat1(double lat1) {
        LiveInfo.lat1 = lat1;
    }

    public static double getLong1() {
        return long1;
    }

    public static void setLong1(double long1) {
        LiveInfo.long1 = long1;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        LiveInfo.address = address;
    }

    public static int getRoomNum() {
        return roomNum;
    }

    public static void setRoomNum(int roomNum) {
        LiveInfo.roomNum = roomNum;
    }

    public static String getCoverurl() {
        return coverurl;
    }

    public static void setCoverurl(String coverurl) {
        LiveInfo.coverurl = coverurl;
    }

    public static String getChatRoomId() {
        return "" + roomNum;
    }
}
