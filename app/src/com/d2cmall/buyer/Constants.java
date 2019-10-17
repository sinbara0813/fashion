package com.d2cmall.buyer;

import android.os.Environment;

public final class Constants {

    private static final String API_URL_DEV = "http://test4.d2cmall.com";
    private static final String API_TEST = "http://192.168.5.30:8070";
    private static final String API_TEST2 = "http://192.168.5.17:8070";
    private static final String API_TEST3 = "http://192.168.5.13:8070";
    private static final String API_TEST4 = "http://192.168.5.250:8070";
    private static final String API_TEST5 = "http://192.168.5.20:8070";
    public static final String API_URL = BuildConfig.DEBUG ? API_URL_DEV :  

    public static final int NO_DATA = 0;
    public static final int NET_DISCONNECT = 1;

    public static final int REQUEST_CODE_TAKE_VIDEO = 0x201;
    public static final int REQUEST_CODE_PICK_VIDEO = 0x200;

    public static class NETWORK_TYPE {
        public static final int NETWORK_TYPE_GPRS = 1;
        public static final int NETWORK_TYPE_EDGE = 2;
        public static final int NETWORK_TYPE_UMTS = 3;
        public static final int NETWORK_TYPE_CDMA = 4;
        public static final int NETWORK_TYPE_EVDO_0 = 5;
        public static final int NETWORK_TYPE_EVDO_A = 6;
        public static final int NETWORK_TYPE_1xRTT = 7;
        public static final int NETWORK_TYPE_HSDPA = 8;
        public static final int NETWORK_TYPE_HSUPA = 9;
        public static final int NETWORK_TYPE_HSPA = 10;
        public static final int NETWORK_TYPE_IDEN = 11;
        public static final int NETWORK_TYPE_EVDO_B = 12;
        public static final int NETWORK_TYPE_LTE = 13;
        public static final int NETWORK_TYPE_EHRPD = 14;
        public static final int NETWORK_TYPE_HSPAP = 15;
    }

    public static class Login {
        public static final int MAIN_LOGIN = 1;
        public static final int EXPLORE_CAMERA_LOGIN = 2;
        public static final int SEND_POST_LOGIN = 3;
        public static final int EXPLORE_LOGIN = 4;
        public static final int DESIGNER_LOGIN = 5;
        public static final int EXPLORE_INFO_LOGIN = 6;
        public static final int EXPLORE_DETAIL_LOGIN = 7;
        public static final int DESIGNER_INFO_LOGIN = 8;
        public static final int BUYCAR_LOGIN = 9;
        public static final int FOCUS_LOGIN = 10;
        public static final int MESSAGE_LOGIN = 11;
        public static final int WEB_LOGIN = 12;
        public static final int ACCOUNT_INFO_LOGIN = 13;
        public static final int SECURITY_INFO_LOGIN = 14;
        public static final int SCAN_LOGIN = 15;
        public static final int START_LIVE_LOGIN = 16;
        public static final int SELF_RETURN_LOGIN = 17;
        public static final int PROSS_LOGIN = 18;
        public static final int VIDEO = 19;
        public static final int LIVE_LIST = 20;
        public static final int PREVIEW_CLICK_REMIND = 21;
    }

    public static class ActionType {
        public static final int KEY_BACK = 1;
        public static final int UPLOAD_STATE = 2;
        public static final int UPLOAD_PROGRESS = 3;
        public static final int MAGIC_WINDOW = 4;
        public static final int ADD_CART_SUCCESS = 5;
        public static final int CLEAR_ALL_ACTIVITY = 6;
        public static final int REFRESH_CART = 7;
        public static final int CALCULTE_CART = 8;
        public static final int FINISH_CART = 9;
        public static final int CHANGE_PAGE = 10;
        public static final int CHANGE_PRODUCT_PAGE = 11;
        public static final int REFRESH_CART_COUNT = 12;
        public static final int HOME_UP = 13;
        public static final int HOME_UP_VISIABLE = 14;
        public static final int LIVE_LIST = 15;
        public static final int SPECAIL_UP = 16;
        public static final int MESSAGE_ALL_UNREANED_CHANGE = 17;
        public static final int XINREN = 18;
        public static final int PARTENER_USER_INFO_CHANGE = 19;
        public static final int WXPAYRESULT = 20;
        public static final int SELECTSTANDARD=21;
        public static final int STANDARDMORE=22;
        public static final int POP=23;
        public static final int GETWEATHER=24;
    }

    public static class RequestCode {
        public static final int LOGOUT = 0;
        public static final int EDIT_NICK = 2;
        public static final int EDIT_MAIL = 3;
        public static final int GET_COUPON = 4;
        public static final int SEND_POST = 5;
        public static final int COUNTRY_CODE = 6;
        public static final int PHOTO_FROM_GALLERY = 7;
        public static final int EXPLORE_INFO_PUBLISH = 8;
        public static final int APPLY_DISTRIBUTION = 9;
        public static final int LOGISTIC = 10;
        public static final int ADD_COMMEND = 11;
        public static final int ADDRESS = 12;
        public static final int ADDCART = 13;
        public static final int COLLECTPRODUCT = 14;
        public static final int BLUE_BEETH = 15;
        public static final int SELECT_DEVICE = 16;
        public static final int PERMISSION = 17;
        public static final int BUY = 18;
        public static final int BIND_PHONE = 19;
        public static final int INVOICE = 20;
        public static final int SELECT_COUPON = 21;
        public static final int SELECT_ADDRESS = 22;
        public static final int ORDER = 23;
        public static final int CHANGE_PROMOTION = 24;
        public static final int GO_TO_EXPORT_INFO = 25;
        public static final int BIND_PHONE_2_ORDERS = 26;
        public static final int BIND_PHONE_2_OBLIGATION = 27;
        public static final int BIND_PHONE_2_SIPPING = 28;
        public static final int BIND_PHONE_2_RECEIVING = 29;
        public static final int BIND_PHONE_2_ASSESS = 30;
        public static final int BIND_PHONE_2_SALE = 31;
        public static final int BIND_PHONE_2_PACKET = 32;
        public static final int DEPOSIT = 33;
        public static final int SELECT_PRODUCT = 34;
        public static final int SELECT_STAGES = 35;
        public static final int FITTING = 36;
        public static final int EXLOREVIDEO_PUBLISHACTIVITY_REQUESTCODE = 37;
        public static final int SEARCHADDRESS_ACTIVITY_REQUESTCODE = 38;
        public static final int RESET_PASSWORD = 39;
        public static final int REQUEST_PERMISSION = 40;
        public static final int REQUEST_TOPIC = 41;
        public static final int COUNTRY_CODE_FAST_LOGIN = 42;
        public static final int VIDEO_FROM_GALLERY = 43;
        public static final int WRITE_LOGSTICS = 44;
        public static final int WRITE_CASH_INFO = 45;
        public static final int REGISTER_CODE = 46;
        public static final int BUYER_CASH = 47;
        public static final int ID_CARD_FRONT = 48;
        public static final int ID_CARD_OPPOSITE = 49;
        public static final int ADD_CERTIFICATION_SUCCESS = 50;
        public static final int CHANGE_CASH_INFO_SUCCESS = 51;
        public static final int PUBLISH_WEAR_SUCCESS = 52;

    }

    public class LineType {
        public static final int NORMAL = 0;
        public static final int BEGIN = 1;
        public static final int END = 2;
        public static final int ONLYONE = 3;
    }


    public static class GlobalType {
        public static final int CARTNUM = 0;
        public static final int OFFLINE = 1;
        public static final int EXPIRED = 2;
        public static final int PUSH = 3;
        public static final int PASSWORD_OK = 4;
        public static final int TABCHANGE = 5;
        public static final int REFRESH_LIVE = 6;
        public static final int SHAREOUT = 7;
        public static final int AUCTIONPAY = 8;
        public static final int WEIBOCALLBACK = 9;
        public static final int PUBLISHBACK = 10;
        public static final int SAVETAG = 11;
        public static final int LOGIN_OK = 12;
        public static final int LOC_LIVE = 13;
        public static final int SHARE_OK = 14;
        public static final int LOGOUT = 15;
        public static final int NOT_NETWORK = 16;
        public static final int SHARE_CANCEL = 17;
        public static final int REFRESH_ADDRESSES = 18;
        public static final int COME_NETWORK = 19;
        public static final int MMLINK = 20;
        public static final int DELETE_CART = 21;
        public static final int REFRESH_CART = 22;
        public static final int UPLOAD_PROGRESS = 23;
        public static final int UPLOAD_FAIL = 24;
        public static final int UPLOAD_AGAIN = 25;
        public static final int UPLOAD_DELETE = 26;
        public static final int MARK_MESSAGE_READED = 27;
        public static final int MESSAGE_DELETE = 28;
        public static final int MARK_RECOMMEND_CATEGORYID_CHANGE = 29;
        public static final int ORDER_TALK = 30;
        public static final int SHOW_DELETE = 31;
        public static final int COLSE_MESSAGE_NOTIFY = 32;
        public static final int OPEN_MESSAGE_NOTIFY = 33;
        public static final int LIKETHIS_LIST_CHANGE = 34;
        public static final int MESSAGE_ALL_UNREANED_CHANGE = 35;
        public static final int DELETE_ORDER = 36;
        public static final int PRODUCT_REPORT_COUNT_CHANGE = 37;
        public static final int PRODUCT_SHOW_CHIOSE_ALL = 38;
        public static final int PRODUCT_SHOW_CHIOSE_PIC = 39;
        public static final int PRODUCT_SHOW_CHIOSE_SHOW = 40;
        public static final int MANI_WEB_FRAMENT_LOGIN = 41;
        public static final int PARTNER_APPLY_CASH = 42;   //申请提现成功
        public static final int PARTNER_OPEN_GONGMAO = 43;   //用户打开电签网页
        public static final int APPLY_AFTER=44;//申请售后成功
        public static final int REFRESH_WEB=45;//刷新网页
        public static final int WXBIND=46;//微信绑定
        public static final int PUBLISH_WEAR_SUCCESS=47;//发布穿搭
        public static final int WARDROBE_DELETE=48;//衣橱删除
    }

    public static String TOPIC_LIST_REAL = "/v3/api/topic/list";
    public static String TOPIC_LIST_URL = "/v3/api/theme/list";
    public static String PRODUCT_THEME = "/v3/api/product/theme";
    public static String BRAND_FOLLOW_URL = "/v3/api/brand/attention/list/%s";
    public static String TOPIC_TAG_LIST = "/v3/api/theme/tag/counts";
    public static String PASSWORD_LOGIN_URL = "/v3/api/member/do/login";
    public static String BRAND_SHOW_URL = "/v3/api/brand/share/%s";
    public static String BRAND_POMOTION ="/v3/api/brand/relation/%s";
    public static String THIRD_LOGIN_URL = "/v3/api/callback/%s";
    public static String RELATION_PRODUCT_URL = "/v3/api/share/select/product";
    public static String SHOW_LIKE_LIST = "/v3/api/share/like/list/%s";
    public static String BRAND_DETAIL_URL = "/v3/api/brand/%s";

    public static String BRAND_PRODUCT_URL = "/v3/api/brand/product/%s";
    public static String MAINS_URL = "/v3/api/page/submodule/%s";

    public static String ORDERS_URL = "/v3/api/myorder/list";
    public static String AFTER_SALE_COUNT = "/v3/api/aftersale/count";
    public static String SHARES_URL = "/v3/api/share/list";
    public static String SHARES_FOLLOW_URL = "/v3/api/share/myfollow/list";
    public static String DESIGNERS_URL = "/v3/api/brand/list";
    public static String SQUARE_URL = "/v3/api/page/index/SQUARE";
    public static String DESIGNER_INFO_URL = "/v3/api/designer/%s";
    public static String BRAND_HOT = "/v3/api/navigation/tag";

    public static String DESIGNER_LETTRE_URL = "/v3/api/navigation/alphabetical";

    public static String DELETE_ORDER_URL_V3 = "/v3/api/myorder/delete";
    public static String RANK_URL = "/v3/api/hot/%s";
    public static String NAVIGATION_PRODUCT_URL = "/v3/api/navigation/%s";
    public static String DESIGNER_KIND_URL = "/v3/api/navigation/category/%s";
    public static String FASHION_PUBLISH_URL="/v3/api/wardrobe/collocation/insert";
    public static String EXPLORE_PUBLISH_URL = "/v3/api/share/insert";

    // /v3/api/product/filter/list
    public static String SCREEN_PRODUCT_URL = "/v3/api/product/filter";

    public static String INSERT_MY_FOLLOWS_URL = "/v3/api/myinterest/follow/insert";

    public static String DELETE_MY_FOLLOWS_URL = "/v3/api/myinterest/follow/delete";

    public static String MY_FOLLOW_URL = "/v3/api/myinterest/myfollows";

    public static String FEED_BACK_URL = "/v3/api/feedBack/insert";
    public static String CART_COUNT_URL = "/v3/api/cart/count";
    public static String CART_DELETE_URL = "/v3/api/cart/delete";
    public static String CART_LIST_URL = "/v3/api/cart/list";
    public static String CALCULATE_CART = "/v3/api/cart/calculate";
    public static String CART_UPDATE_URL = "/v3/api/cart/update/%s";
    public static String CART_INSERT_URL = "/v3/api/cart/insert";
    public static String PRODUCT_URL = "/v3/api/product/%s";
    public static String PRODUCT_RECOM_URL="/v3/api/similar/brand/%1$d/%2$s";
    public static String COMB_PRODUCT_URL = "/v3/api/product/comb/%s";
    public static String SKUINFO_URL = "/v3/api/product/%s/skuInfo";
    public static String BUY_NOW_URL = "/v3/api/order/buynow";
    public static String BIG_CARD_URL = "/v3/api/page/mybrand/recommend";
    public static String COMB_BUY_NOW_URL = "/v3/api/order/comb/buynow";
    public static String ORDER_CONFIRM_URL = "/v3/api/order/confirm";
    public static String ORDER_INFO_URL = "/v3/api/myorder/%s";
    public static String ORDER_ITEM_DETAIL_URL = "/v3/api/myorder/item/%s";
    public static String AD_HOT_BRAND = "/v3/api/ad/NAV/BRANDCATEGORY_1";

    public static String CANCEL_ORDER_URL = "/v3/api/myorder/cancel";

    public static String DESIGNER_PRODUCTS_URL = "/v3/api/designer/product/%s";
    public static String DESIGNER_SHARES_URL = "/v3/api/designer/share/%s";
    public static String DESIGNER_ATTENTION_URL = "/v3/api/designer/attention/%s";

    public static String COUPONS_URL = "/v3/api/coupon/mine";

    public static String RESHIPS_URL = "/v3/api/aftersale/reship/list";

    public static String EXCHANGES_URL = "/v3/api/aftersale/exchange/list";

    public static String EXCHANGES_URL_V3 = "/v3/api/aftersale/exchange/list";

    public static String REFUNDS_URL = "/v3/api/aftersale/refund/list";

    public static String CANCEL_REFUND_URL = "/v3/api/aftersale/cancel/refund";

    public static String CANCEL_RESHIP_URL = "/v3/api/aftersale/cancel/reship";

    public static String CANCEL_EXCHANGE_URL = "/v3/api/aftersale/cancel/exchange";

    public static String CONFIRM_EXCHANGE_URL = "/v3/api/aftersale/exchange/receive/%s";

    public static String SCREEN_BRAND_URL = "/v3/api/product/brand/list";

    public static String MY_COLLECT_PRODUCT_URL = "/v3/api/myinterest/collection/list";
    public static String MY_COLLECT_URL = "/v3/api/myinterest/collection/list";
    public static String COMPLAIN_BACK_DETIAL = "/v3/api/feedBack/%s";

    public static String CANCEL_COLLECT_URL = "/v3/api/myinterest/collection/delete";
    public static String MY_FOCUS_BRAND_URL = "/v3/api/myinterest/attention/list";
    public static String UNFOCUS_DESIGNER_URL = "/v3/api/myinterest/attention/delete/%s";
    public static String FOCUS_DESIGNER_URL = "/v3/api/myinterest/attention/insert/%s";
    public static String EXPLORE_INFO_URL = "/v3/api/member/home";
    public static String COLLECT_PRODUCT_URL = "/v3/api/myinterest/collection/insert";
    public static String MESSAGES_URL = "/v3/api/message/list";
    public static String MESSAGE_URL = "/v3/api/message/major/list";
    public static String MESSAGE_UNREADED_URL = "/v3/api/message/list";
    public static String MESSAGE_MARK_READED_URL = "/v3/api/message/read/%s";
    public static String MESSAGE_READ = "/v3/api/message/read/major/%s";
    public static String MESSAGE_DELETE_URL = "/v3/api/message/delete/%s";
    public static String MESSAGES_DELETE_URL = "/v3/api/message/batch/delete";
    public static String MESSAGE_LIST_URL = "/v3/api/message/list";


    public static String DOWN_LOAD_SHOW_URL="/v3/api/share/add/down/%s";
    public static String SHARE_SHOW_URL="/v3/api/share/add/share/%s";

    public static String MY_FANS_URL = "/v3/api/myinterest/myfans";
    public static String UPDATE_ACCOUNT_URL = "/v3/api/member/update/info";

    public static String GET_COUPON_URL = "/v3/api/coupon/convert";
    public static String AD_MY_PAGE = "/v3/api/ad/MY/QUESTIONNAIRE";
    public static String PHYSICAL_LIST_URL = "/v3/api/store/list";
    public static String PHYSICAL_CITY = "/v3/api/store/province";
    public static String EXPLORE_DETAIL_URL = "/v3/api/share/%s";
    public static String EXPLORE_COMMENT_URL = "/v3/api/share/comment/list/%s";
    public static String TOPIC_DETAIL_URL = "/v3/api/topic/%s";
    public static String EXPLORE_COMMENT_INSERT_URL = "/v3/api/share/comment/insert";
    public static String FEEDBACK_DETAIL = "/v3/api/feedBack/%s";

    public static String LIKE_SHARE_URL = "/v3/api/share/like/insert/%s";
    public static String DELETE_SHARE_LIKE = "/v3/api/share/like/delete/%s";
    public static String LIKE_COMMENT = "/v3/api/share/comment/like/%s";
    public static String FOLLOW_MEMBER_URL = "/v3/api/myinterest/follow/insert/%s";
    public static String UNFOLLOW_MEMBER_URL = "/v3/api/myinterest/follow/delete";

    public static String FOLLOW_BRAND_URL = "/v3/api/myinterest/attention/insert/%s";

    public static String FOLLOW_BRAND_DELETE = "/v3/api/myinterest/attention/delete";

    public static String DELETE_SHOW_URL = "/v3/api/share/delete/%s";
    public static String MY_WALLET_INFO_URL = "/v3/api/account/info";
    public static String MY_RED_PACKET = "/v3/api/account/red";
    public static String MY_RED_PACKET_LIST = "/v3/api/account/red/list";

    public static String SEND_CODE_URL = "/v3/api/sms/send/encrypt";
    public static String SEND_AUDIO_CODE = "/v3/api/sms/send/audio";

    public static String BRAND_SERIES = "/v3/api/brand/series/list";

    public static String RESET_PASSWORD_URL = "/v3/api/security/reset/password";

    public static String CHECK_PHONE_EXIST_URL = "/v3/api/member/check2";

    public static String RESET_PASSWORD_EXIST_URL = "/v3/api/member/check/exist";

    public static String CHECK_PHONE_EXIST2_URL = "/v3/api/member/check";

    public static String REGISTER_URL = "/v3/api/member/do/register";

    public static String REGISTER_CHECK_URL = "/v3/api/member/check/register";

    public static String PRODUCTS_URL = "/v3/api/product/list";
    public static String GOOD_URL = "/v3/api/navigation/%s";
    public static String CHANGE_PASSWORD_URL = "/v3/api/security/member/password";
    public static String SET_PASSWORD_URL = "/v3/api/member/resetPassword";

    public static String SET_PAY_PASSWORD_URL = "/v3/api/security/account/password";

    public static String COMDITY_COMMENT_LIST = "/v3/api/comment/product/%s";
    public static String COMDITY_CONSULT_LIST = "/v3/api/consult/product/%s";
    public static String COMDITY_INSERT_CONSULT = "/v3/api/product/consult/insert";
    public static String DESIGNER_FANS_URL = "/v3/api/designer/attention/list/%s";
    public static String SHARE_LIKE_URL = "/v3/api/share/like/%s";
    public static String ARRIVAL_NOTICE = "/v3/api/product/remind/%s";

    public static String CONFIRM_RECEIVE_URL = "/v3/api/myorder/confirm";
    public static String PROMOTION_URL = "/v3/api/promotion/%s";
    public static String CROWD_URL = "/v3/api/crowd/%s";
    public static String BIND_MEMBER_PHONE_URL = "/v3/api/member/bind/member";
    public static String COUPON_COUNT_URL = "/v3/api/coupon/mine";
    public static String CHECK_PAY_PASSWORD_URL = "/v3/api/account/check/password";
    public static String FIND_SAME_PRODUCT = "/v3/api/similar/%s/top/%s";

    public static String PRODUCT_COMMEND_INSERT_URL = "/v3/api/comment/item/insert/%s";

    public static String PRODUCT_SHOW_LIST = "/v3/api/product/share/%s";
    public static String BIND_RECOMMEND_URL = "/v3/api/member/recommend";
    public static String EXCHANGE_DETAIL_URL = "/member/exchange/%s";
    public static String UPDATE_QUESTION = "/v3/api/feedBack/insert";
    public static String AD_QUESTION = "/v3/api/ad/MY/FEEDBACK";

    public static String REFUND_DETAIL_URL = "/member/refund/%s";

    public static String RESHIP_DETAIL_URL = "/member/reship/%s";

    public static String DELETE_EXPLORE_URL = "/v3/api/share/delete/%s";

    public static String COMMEND_DETAIL_URL = "/v3/api/comment/%s";

    public static String COMMEND_ADD_URL = "/v3/api/comment/item/additional/%s";
    public static String SEND_DEVICE_URL = "/v3/api/home/device";
    public static String CREATE_ORDER_URL = "/v3/api/order/create";
    public static String PAY_ORDER_URL = "/v3/api/payment";
    public static String COMMENT_COUNT_URL = "/v3/api/comment/count/detail/%s";

    public static String ADDRESSES_URL = "/v3/api/address/list";

    public static String DELETE_ADDRESS_URL = "/v3/api/address/delete/%s";

    public static String DEFAULT_ADDRESS_URL = "/v3/api/address/default/%s";

    public static String UPDATE_ADDRESS_URL = "/v3/api/address/update";

    public static String INSERT_ADDRESS_URL = "/v3/api/address/insert";

    public static String ORDER_ITEMS_URL = "/v3/api/myorder/item/list";


    public static String ORDER_COUNT_URL = "/v3/api/myorder/counts";

    public static String READ_MESSAGE_URL = "/v3/api/message/read/%s";
    public static String EXPLORE_LIKES_URL = "/v3/api/share/like/list/%s";
    public static String POST_EXCHANGE_URL = "/v3/api/after/exchange";
    public static String POST_RESHIP_URL = "/v3/api/aftersale/apply/reship";

    public static String POST_REFUND_URL = "/v3/api/aftersale/apply/refund";
    public static String POST_EXCHANGE_LOGISTIC_URL = "/v3/api/aftersale/exchange/logistic";

    public static String POST_RESHIP_LOGISTIC_URL = "/v3/api/aftersale/reship/logistic";
    public static String UPDATE_COVER_URL = "/v3/api/member/update";
    public static String DESIGNER_SUB_COUNT_URL = "/v3/api/designer/count/%s";

    public static String PHONE_CODE_LOGIN_URL = "/v3/api/member/code/login";
    public static String ORDER_DISTRIBUTION_URL = "/v3/api/partner/bill/list";
    public static String RECOMMEND_MEMBER_URL = "/v3/api/member/bindMember";
    public static String APPLY_DISTRIBUTION_URL = "/v3/api/partner/apply";
    public static String COMMENT_NUM_API = "/v3/api/comment/count/%s";
    public static String UPDATE_PIC_TAG_LIST_URL = "/v3/api/share/updatePicTags";
    public static String DESIGNER_GOOD_LIST_URL = "/v3/api/zegolive/select/product";
    public static String LIVE_PRODUCT_LIST_URL = "/v3/api/product/detail/list";
    public static String DELETE_MESSAGE_URL = "/v3/api/message/delete/%s";
    public static String GET_RATIO_URL = "/v3/api/partner/ratio";
    //   /v3/api/account/rule
    public static String DEPOSIT_URL = "/v3/api/account/recharge";

    public static String DEPOSIT_RULE_URL = "/v3/api/account/rule";

    public static String NEW_EXPLORE_INFO_URL = "/v3/api/member/info/%s";

    public static String LOGOUT_URL = "/v3/api/member/logout";

    public static String SMART_SEARCH_URL = "/v3/api/searchsum/membersearch";

    public static String COMMENT_DELETE_URL = "/v3/api/share/comment/delete/%s";

    public static String APPMENU_URL = "/v3/api/home/appmenu/list";

    public static String SHARE_OUT_URL = "/v3/api/home/share/out";
    public static String SHARE_IN_URL = "/v3/api/home/share/in";
    public static String ARRIVAL_URL = "/v3/api/member/arrival";
    public static String MAIN_TAG_URL = "/v3/api/page/index/MAIN";
    public static String SHARE_TAG_URL = "/v3/api/share/tag/list";
    public static String DESIGNER_TAG_URL = "/v3/api/designer/tag/list";
    public static String PRODUCT_REPORT_URL = "/v3/api/productreport";
    public static String PRODUCT_REPORT_LIST_URL = "/v3/api/productreport/list";
    public static String PRODUCT_REPORT_CANCEL_URL = "/v3/api/productreport/cancel/%s";
    public static String PRODUCT_REPORT_DELETE_URL = "/v3/api/productreport/delete/%s";
    public static String PRODUCT_REPORT_SUBMIT_URL = "/v3/api/productreport/submit";
    public static String GOOD_TAG_URL = "/v3/api/navigation/0";
    public static String CHANGE_BIND_PHONE = "/v3/api/member/change/mobile";

    public static String AUCTION_PAY_URL = "/v3/api/payment";
    public static String MEMBER_LIST_URL = "/v3/api/member/list";
    ///v3/api/account/payitem/list
    public static String DEPOSIT_DETAIL_URL = "/v3/api/account/payitem/list";
    public static String HOT_SEARCH_URL = "/v3/api/searchsum/list";
    public static String UPDATE_URL = "/v3/api/home/upgrade/plus";

    public static String SPLASH_URL = "/v3/api/home/splashscreen";
    public static String FILTRATION_URL = "/v3/api/product/search/help";
    public static String LEAD_COUPON_URL = "/v3/api/coupon/receive/%s";
    public static String ANALYZE_HOT_SEARCH_URL = "/v3/api/memberSearchSum/count";
    public static String DELETE_ORDER_URL = "/v3/api/order/delete/%s";
    public static String ZEGOLIVE_INSERT_URL = "/v3/api/zegolive/insert";
    public static String ZEGOLIVE_CLOSE_URL = "/v3/api/zegolive/close/%s";
    public static String ZEGOLIVE_COUNT_URL = "/v3/api/zegolive/count/%s";
    public static String ZEGOLIVE_IN_URL = "/v3/api/zegolive/in/%s";
    public static String ZEGOLIVE_OUT_URL = "/v3/api/zegolive/out/%s";
    public static String ZEGOLIVE_LIST_URL = "/v3/api/zegolive/list";
    public static String ZEGOLIVE_DELETE_URL = "/v3/api/zegolive/delete/%s";
    public static String RONG_TOKEN_URL = "/v3/api/zegolive/token";
    public static String CHECK_NOTICE_URL = "/v3/api/product/crowd/remind";
    public static String APP_LOG_URL = "/v3/api/home/log";
    public static String LIVE_BANNER_URL = "/v3/api/page/index/LIVE";
    public static String LIVE_PRESENTS_URL = "/v3/api/live/present/list";
    public static String RECOMMEND_GOOD_LIST_URL = "/v3/api/zegolive/recommend/list";
    public static String RECOMMEND_GOOD_URL = "/v3/api/zegolive/do/recommend";
    public static String ZEGOLIVE_DESIGNER_URL = "/v3/api/designer/attention/count/%s";
    public static String PAY_PRESENT_URL = "/v3/api/zegolive/pay/present";
    public static String WATCH_INFO_URL = "/v3/api/zegolive/%s";
    public static String RECOMMEND_HEAD_URL = "/v3/api/designer/marketing/%s";
    public static String PAY_DANMU_URL = "/v3/api/zegolive/pay/barrage";
    public static String MULTI_DELETE_MESSAGE_URL = "/v3/api/message/batch/delete";
    public static String PREVIEW_REMIND_URL = "/v3/api/zegolive/remind";
    public static String PREVIEW_UNREMIND_URL = "/v3/api/zegolive/cancel/remind";
    public static String ADD_PREVIEW_LIVE_URL = "/v3/api/zegolive/preview";
    public static String PREVIEW_PRODUCTS_URL = "/v3/api/zegolive/preview/product";
    public static String PREVIEW_START_URL = "/v3/api/zegolive/preview/start";
    public static String LIVE_MSG_PUSH = "/v3/api/zegolive/msg/%s";
    public static String STORES_URL = "/v3/api/designer/showroom/%s";
    public static String MOVE_COLLECT = "/v3/api/cart/move/collection";
    public static String GET_SIGN = "/instalment/mimepay/apply";
    public static String GET_COLOR_SIZE = "/v3/api/product/simple/%d";
    public static String UPDATE_SKU = "/v3/api/cart/update/sku";
    public static String USER_SIGN = "/instalment/mimepay/thduserid";
    public static String STAGES_QUERY = "/instalment/mimepay/query";
    public static String GET_COUPON_GROUP_URL = "/v3/api/coupondefgroup/receive/%s";
    public static String GET_LIVE_COUPON_URL = "/v3/api/zegolive/coupon";
    public static String QUERY_MEME_WALLET_URL = "/instalment/mimepay/query/wallet";
    public static String GET_PAY_MODE_URL = "/v3/api/home/channel";
    public static String GET_MAIN_TAG_LIST = "/v3/api/page/index/%s";
    public static String GET_MAIN_SUB_TAG = "/v3/api/page/index/HOME/%s";
    public static String GET_MAIN_SHOW_LIST = "/v3/api/page/share/list";
    public static String GET_SHOW_HEAD = "/v3/api/share/head/info";
    public static String GET_MAIN_LIVE_LIST = "/v3/api/page/live/list";
    public static String GET_RECOMMEND_PRODUCT = "/v3/api/product/recommend/product";
    public static String GET_STAR_TAG = "/v3/api/product/tag/list";
    public static String GET_STAR_STYLE = "/v3/api/product/list";
    public static String GET_PRODUCT_SHOW_LIST = "/v3/api/product/share/%s";
    public static String GET_PRODUCT_AD_PRODUCTREPORT = "/v3/api/ad/PRODUCT/PRODUCTREPORT_1";
    public static String GET_PRODUCT_AD_SHARE = "/v3/api/ad/PRODUCT/SHARE_1";
    public static String GET_PRODUCT_AD_REPORT = "/v3/api/ad/PRODUCT/PRODUCTREPORT_1";
    public static String GET_PRODUCT_REC_AD="/v3/api/ad/PRODUCT/RECPRO";
    public static String GET_ORDER_AD="/v3/api/ad/ORDER/PAYSUCCESS";
    public static String GET_PRODUCT_CROSS_AD="/v3/api/ad/PRODUCT/CROSS";
    public static String GET_PRODUCT_CONSULT_LIST = "/v3/api/product/consult/%s";
    public static String GET_PRODUCT_COMMENT_LIST = "/v3/api/product/comment/%s";
    public static String GET_UN_FOCUS_MEMBER = "/v3/api/share/activemember";
    public static String GET_MY_FOLLOW_URL = "/v3/api/share/myfollow/list";
    public static String GET_HOT_LIST = "/v3/api/hot/active/member";
    public static String GET_PRODUCT_MATCH_LIST = "/v3/api/similar/%1$s/top/%2$d";
    public static String GET_MAIN_PROMOTION_TOP = "/v3/api/similar/promotion/top/%d"; //情报�?
    public static String GET_TOP_RECOMMEND = "/v3/api/similar/category/top/%d"; //榜单推荐
    public static String GET_TOP_RECOMMEND_SUB = "/v3/api/similar/category/%d/top/%d"; //榜单推荐更多
    public static String GET_MAIN_LIKE_LIST = "/v3/api/similar/top/%d"; //猜你喜欢
    public static String GET_MAIN_RECOMMEND_LIST = "/v3/api/page/product/recommends";
    public static String GET_PRODUCT_COUNT = "/v3/api/product/counts/%d";
    public static String GET_ORDER_RECOMMEND = "/v3/api/similar/order/%s/top/%d";
    public static String GET_MAIN_PROMOTION_LIST = "/v3/api/page/section/%d";
    public static String GET_REFUND_DETAIL_URL = "/v3/api/aftersale/refund/%d";
    public static String GET_RESHIP_DETAIL_URL = "/v3/api/aftersale/reship/%d";
    public static String GET_EXCHANGE_DETAIL_URL = "/v3/api/aftersale/exchange/%d";
    public static String GET_MAIN_NOTICE_URL = "/v3/api/article/notice";
    public static String GET_MAIN_NOTICE_CANCEL_URL = "/v3/api/article/cancel";
    public static String GET_KAOLA_AFTER_SALE_LIST_URL = "/v3/api/aftersale/kaola/list";//考拉商品申请售后,拉取该商品同仓的所有商�?

    //用户行为埋点
    public static String POST_UPLOAD_DEVICE_INFO_URL = "/v3/api/behavior/event/onload";
    public static String POST_BEHAVIOR_EVENT_URL = "/v3/api/behavior/event/add";
    //足迹
    public static String GET_FOOTMARK_PRODUCT_LIST = "/v3/api/behavior/event/find/product";
    public static String GET_FOOTMARK_PRODUCT_LIST_COUNT = "/v3/api/behavior/event/find/product/count";
    public static String DELETE_FOOTMARK_URL="/v3/api/behavior/event/find/product/delete/%s";//这里跟的是eventId

    //买手中心
    public static String GET_PARTNER_CENTER_URL = "/v3/api/partner/mine";
    public static String GET_PARTNER_BILL_URL = "/v3/api/partner/bill";//返利单列�?
    public static String GET_PARTNER_CASH_URL = "/v3/api/partner/cash";//提现单列�?
    public static String GET_PARTNER_CHILDREN_URL = "/v3/api/partner/children";//团队
    public static String GET_PARTNER_CUSTOMER_URL = "/v3/api/partner/invite/list";//邀请记�?
    public static String GET_PARTNER_LOG_URL = "/v3/api/partner/log";//提现返利记录
    public static String GET_PARTNER_MIN_WITHDRAW_URL = "/v3/api/partner/min/withdraw";//最低提现金�?
    public static String POST_PARTNER_WITHDRAW_URL = "/v3/api/partner/withdraw/cash";//申请提现
    public static String GET_PARTNER_SALE_DATA_URL = "/v3/api/partner/summary";//返利单列�?
    public static String GET_PARTNER_VISITOR_URL = "/v3/api/behavior/eventstat/findVisitors";//访客
    public static String GET_PARTNER_VISITOR_YESTERDAY_TODAY_URL = "/v3/api/behavior/eventstat/findUvStat";//今日昨日访客
    public static String GET_PARTNER_NOTICE_URL = "/v3/api/article/list/%s";//买手公告
    public static String GET_PARTNER_INFO_UPDATA_URL = "/v3/api/partner/update";//更新买手信息
    public static String GET_PARTNER_SALE_SCHOOL_TAGS_URL = "/v3/api/theme/tag/list";//商学院入�?
    public static String GET_PARTNER_SALE_SCHOOL_LIST_URL = "/v3/api/theme/tag/%d";//商学院文章列�?
    public static String GET_PARTNER_REFRESH_ARRIVAL_URL = "/v3/api/home/arrival";//签到
    public static String GET_PARTNER_REMOVE_CHILD_URL = "/v3/api/partner/separate";//移除下级
    public static String BUYER_VISITOR_DATA = "/v3/api/partner/customers";//店铺访客数据
    public static String BUYER_CHILDREN_DATA = "/v3/api/partner/children/summary";
    public static String BUYER_LIST_URL = "/v3/api/analyze/partner/list";
    public static String BUYER_SUMMARY_MONTH = "/v3/api/analyze/partner/%s/month/list";
    public static String BUYER_LOOK_BUYER="/v3/api/member/info/%d";        //点击小头像查看买�?
    public static String GONGMAO_ELECTRIC_SIGN="/v3/api/partner/gongmall/contract";//工猫电签
    public static String BUYER_MONTH_CASHED_NUM="/v3/api/partner/month/withdraw";//本月已提现金�?
    public static String BUYER_UPDATE_GONGMAO="/v3/api/partner/gongmall/update";//工猫更新信息
    public static String BUYER_ADVISER_LIST="/v3/api/partner/counselor/list";//运营顾问列表
    public static String BUYER_ADVISER_INFO="/v3/api/partner/counselor/%s";//运营顾问信息
    public static String NORMAL_CUSTOMER_INFO="/v3/api/member/detail/%s";//普通用户销售信�?
    public static String BUYER_CERTIFICATE="/v3/api/partner/poster";//买手证书
    public static String NORMAL_CUSTOMER_LIKE_LIST="/v3/api/similar/user/%s/top/50";//普通用户可能喜欢商�?
    public static String PARTNER_GIFTS="/v3/api/partner/package/list";//买手我的礼包


    public static String SEARCH_URL="/v3/api/product/searchpic/list";

    //注销
    public static String DELETE_ACCOUNT_URL = "/v3/api/member/delete";
    //关联账号
    public static String RELEVANCE_ACCOUNT_LIST = "/v3/api/member/child/list";
    public static String UNBIND_RELEVANCE_URL = "/v3/api/member/cancel/bind";

    //直播
    public static String CREATE_LIVE_STREAM = "/v3/api/live/insert";//创建直播
    public static String CLOSE_LIVE_STREAM = "/v3/api/live/close";//关闭直播
    public static String LIVE_LIST_URL = "/v3/api/live/list";//直播数据列表
    public static String RONG_NEW_TOKEN_URL = "/v3/api/live/token";//获取融云token
    public static String LIVE_CUSTOM_OUT = "/v3/api/live/out/%s";//游客退出房�?
    public static String LIVE_CUSTOM_IN = "/v3/api/live/in/%s";//游客进入房间
    public static String DELETE_LIVE = "/v3/api/live/delete/%s";//删除直播
    public static String LIVE_ANCHOR_INFO = "/v3/api/member/info/%s";//获取主播信息
    public static String LIVE_RECOMMEND_GOOD_LIST_URL = "/v3/api/live/recommend/list";//获取主播推荐商品列表
    public static String LIVE_LIST_BANNER = "/v3/api/live/banner";//直播的banner
    public static String LIVE_ANCHOR_RECOMMEND_PRODUCT = "/v3/api/live/do/recommend";//主播推荐商品
    public static String LIVE_STATUS_INFO = "/v3/api/live/%s";//直播的状�?
    public static String LIVE_RECORD_IN = "/v3/api/live/watch/%s";//直播的状�?
    public static String MEMBER_MINE = "/v3/api/partner/mine";
    public static String LIVE_COUPON_URL = "/v3/api/live/coupon";//直播优惠�?
    public static String LIVE_COUPON_GROUP_GET = "/v3/api/coupon/batch/%s";
    public static String PARTNER_SUMMARY="/v3/api/partner/summary";
    public static String PARTNER_BILL_SUMMARY="/v3/api/partner/bill/summary";
    public static String ORDER_TODAY="/v3/api/analyze/partner/%s/today";
    public static String ORDER_YESTERDAY="/v3/api/analyze/partner/%s/day";
    public static String ORDER_SOMEDAY="/v3/api/analyze/partner/%s/day/merge";
    public static String ORDER_ALL="/v3/api/analyze/partner/%s";
    public static String MAIN_SIFT_GOOD = "/v3/api/similar/product/mix";
    public static String MAIN_CROSS_GOOD="/v3/api/similar/product/world/list";
    public static String MAIN_BOTTOM_PIC = "/v3/api/home/appnavigation";
    public static String SELECT_ORDER_COUPON = "/v3/api/order/coupon";
    public static String CHECK_ORDER_SKU="/v3/api/myorder/buylist/%s";
    public static String ORDER_ADD_CART="/v3/api/cart/batch/insert";
    public static String CREATE_COUPON_ORDER="/v3/api/coupon/create/order";
    public static String CREATE_KAOLA_OUTSEA_ORDER="/v3/api/order/kaola/confirm";
    public static String NEW_PEOPLE_COUPONS="/v3/api/coupon/fix/list";
    public static String COUPONS_PRODUCTS_RANGE="/v3/api/coupon/relations/%s";


    //实名认证
    public static String CERTIFICATION_LIST="/v3/api/membercertification/list"; //身份认证列表
    public static String CERTIFICATION_DELETE="/v3/api/membercertification/delete/%s"; //删除
    public static String CERTIFICATION_DEFAULT="/v3/api/membercertification/default/%s"; //设置默认
    public static String CERTIFICATION_CERTIFICATE="/v3/api/membercertification/certificate"; //认证
    public static String CERTIFICATION_REUPLOAD="/v3/api/membercertification/upload"; //重新上传身份证照�?
    public static String CERTIFICATION_BIZNO="/v3/api/membercertification/bizno"; //活体认证获取bizno
    public static String CERTIFICATION_CHECK="/v3/api/membercertification/check"; //是否超过活体认证次数
    public static String CERTIFICATION_ADD="/v3/api/membercertification/add"; //认证成功后添加活体认�?
    public static String CERTIFICATION_ADDRESS="/v3/api/order/certification/%s"; //选地址的时候重新获取身份信�?

    //拼团
    public static String COLLAGEDETAIL_PRODUCT_URL="/v3/api/collage/%s";// 详情 参数id get
    public static String GROUP_BUY_PRODUCT_LIST="/v3/api/collage/list";//拼团列表
    public static String GROUP_BUY_PRODUCT_REMIND="/v3/api/collage/remind/%s";//拼团提醒
    public static String GROUP_BUY_MINE="/v3/api/collage/mine";//我的拼团
    public static String COLLAGE_ORDER_CONFIRM="/v3/api/order/collage/buynow";
    public static String COLLAGE_ORDER_CREATE="/v3/api/collage/create/collage";
    public static String COLLAGE_DETAIL="/v3/api/collage/%s";
    public static String COLLAGEDETAIL_URL="/v3/api/collage/order/%s";// 详情订单 参数id get
    public static String GROUP_DETAIL_URL="/v3/api/collage/group/%s";//拼团详情
    public static String GROUP_CHECK_TERM="/v3/api/collage/check";//检查拼团条�?
    public static String GROUP_LIST="/v3/api/collage/groups/%s";//拼团列表


    //积分商城
    public static String DCION_SHOP_URL="/v3/api/ad/%1$s/%2$s";//积分商城3
    public static String DCION_PROCUCT_URL="/v3/api/pointproduct/%1$s";//积分商品
    public static String DCION_PROCUCT_LIST_URL="/v3/api/pointproduct/list";//积分列表
    public static String DCION_PROCUCT_EXCHANGE_URL="/v3/api/pointexchange/exchange/%d";//积分商品兑换
    public static String DCION_PROCUCT_EXCHANGE_HISTORY="/v3/api/pointexchange/records";//积分商品兑换记录
    public static String DCION_SIGNE_HISTORY="/v3/api/member/lastest/records";//积分商城签到最近记�?检查今天有没有签到
    public static String DCION_MY_POINT="/v3/api/member/point";//获取自己的积�?

    //每日上新
    public static String UPDATE_BRAND_CATEGORY="/v3/api/page/newup/category";
    public static String UPDATE_BRAND_CATEGORY_LIST="/v3/api/page/newup/goods/brand";

    public static String COMMENT_ORDER_POINT="/v3/api/member/task/find/%s";//会员任务
    public static String MY_TOP_BACK="/v3/api/ad/MY/TOPBACK";//我的背景�?

    //我的衣橱
    public static String WARDROBE_LIST="/v3/api/wardrobe/list";  // 我的衣橱
    public static String WARDROBE_INSERT="/v3/api/wardrobe/insert"; //新增
    public static String WARDROBE_CATEGORY="/v3/api/wardrobe/category"; //衣橱分类
    public static String WARDROBE_COLLOCATION_INSERT="/v3/api/wardrobe/collocation/insert";//新增衣橱搭配
    public static String WARDROBE_COLLOCATION_LIST="/v3/api/wardrobe/collocation/list";// 衣橱搭配列表
    public static String WARDROBE_RECOMMEND_LIST="/v3/api/wardrobe/collocation/recommend/list";// 推荐衣橱搭配列表
    public static String WARDROBE_DELETE_ITEM="/v3/api/wardrobe/delete";//衣橱删除
    public static String WARDROBE_UPDATE_ITEM="/v3/api/wardrobe/update";//衣橱更新
    public static String WARDROBE_DELETE="/v3/api/wardrobe/collocation/delete";
    public static String WARDROBE_UPDATE="/v3/api/wardrobe/collocation/update";



    //天气地址
    public static String WEATHER_URL="https://free-api.heweather.com/v5/weather?city=%s&key=4f7b1188c1674728ac1a5ad81def0388";

}