package com.d2cmall.buyer.util;

import android.util.Log;

import com.d2cmall.buyer.Constants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class PayUtil {

    private static final String ALGORITHM = "RSA";
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static final String DEFAULT_CHARSET = "UTF-8";

    private static PayUtil instance;

    public static PayUtil getInstance() {
        if (instance == null) {
            synchronized (PayUtil.class) {
                instance = new PayUtil();
            }
        }
        return instance;
    }

    public String getPrepayId(String ordresn, String body, int price, String ip) throws Throwable {
        String notice_str = Util.getRandomString(15);
        String path = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        StringBuilder params = new StringBuilder();
        params.append("appid=");
        params.append(Constants.WEIXINAPPID);
        params.append("&");
        params.append("body=");
        params.append(body);
        params.append("&");
        params.append("mch_id=");
        params.append(Constants.WXMCHID);
        params.append("&");
        params.append("nonce_str=");
        params.append(notice_str);
        params.append("&");
        params.append("notify_url=");
        params.append(Constants.PAY_URL + "/weixinNotify");
        params.append("&");
        params.append("out_trade_no=");
        params.append(ordresn);
        params.append("&");
        params.append("spbill_create_ip=");
        params.append(ip);
        params.append("&");
        params.append("total_fee=");
        params.append(price);
        params.append("&");
        params.append("trade_type=");
        params.append("APP");
        String temp = params.toString() + "&key=" + Constants.WXPARTNERID;
        String s = Util.getMD5(temp).toUpperCase();
        StringBuilder param = new StringBuilder();
        param.append("<xml>").append("\n")
                .append("<appid>").append(Constants.WEIXINAPPID).append("</appid>").append("\n")
                .append("<body>").append(body).append("</body>").append("\n")
                .append("<mch_id>").append(Constants.WXMCHID).append("</mch_id>").append("\n")
                .append("<nonce_str>").append(notice_str).append("</nonce_str>").append("\n")
                .append("<notify_url>").append(Constants.PAY_URL + "/weixinNotify").append("</notify_url>").append("\n")
                .append("<out_trade_no>").append(ordresn).append("</out_trade_no>").append("\n")
                .append("<spbill_create_ip>").append(ip).append("</spbill_create_ip>").append("\n")
                .append("<total_fee>").append(price).append("</total_fee>").append("\n")
                .append("<trade_type>").append("APP").append("</trade_type>").append("\n")
                .append("<sign>").append(s).append("</sign>").append("\n")
                .append("</xml>");
        byte[] postData = param.toString().getBytes();
        URL url = new URL(path);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setConnectTimeout(5 * 1000);
        urlConn.setDoOutput(true);
        urlConn.setUseCaches(false);
        urlConn.setRequestMethod("POST");
        urlConn.setInstanceFollowRedirects(true);
        urlConn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencode");
        urlConn.connect();
        DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
        dos.write(postData);
        dos.flush();
        dos.close();
        if (urlConn.getResponseCode() == 200) {
            byte[] data = readStream(urlConn.getInputStream());
            String msg = new String(data, "utf-8");
            Log.d("han", "msg==" + msg);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new ByteArrayInputStream(data)));
            String pre_id = null;
            Element root = doc.getDocumentElement();
            NodeList firtstList = root.getChildNodes();
            for (int i = 0; i < firtstList.getLength(); i++) {
                Node itemNode = firtstList.item(i);
                if (itemNode.getNodeName().equals("prepay_id")) {
                    pre_id = itemNode.getTextContent();
                    break;
                }
            }
            Log.d("han", "pre_id=" + pre_id);
            return pre_id;
        } else {
            Log.i("han", "Post方式请求失败");
        }
        return null;
    }

    public String getPrepayId(String ordresn, String orderType, String body, int price, String ip) throws Throwable {
        String notice_str = Util.getRandomString(15);
        String path = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        StringBuilder params = new StringBuilder();
        params.append("appid=");
        params.append(Constants.WEIXINAPPID);
        params.append("&");
        params.append("attach=");
        params.append(orderType);
        params.append("&");
        params.append("body=");
        params.append(body);
        params.append("&");
        params.append("device_info=").append("APP-002").append("&");
        params.append("mch_id=");
        params.append(Constants.WXMCHID);
        params.append("&");
        params.append("nonce_str=");
        params.append(notice_str);
        params.append("&");
        params.append("notify_url=");
        params.append(Constants.PAY_URL + "/weixinNotify");
        params.append("&");
        params.append("out_trade_no=");
        params.append(ordresn);
        params.append("&");
        params.append("spbill_create_ip=");
        params.append(ip);
        params.append("&");
        params.append("total_fee=");
        params.append(price);
        params.append("&");
        params.append("trade_type=");
        params.append("APP");
        String temp = params.toString() + "&key=" + Constants.WXPARTNERID;
        String s = Util.getMD5(temp).toUpperCase();
        StringBuilder param = new StringBuilder();
        param.append("<xml>").append("\n")
                .append("<appid>").append(Constants.WEIXINAPPID).append("</appid>").append("\n")
                .append("<attach>").append(orderType).append("</attach>").append("\n")
                .append("<body>").append(body).append("</body>").append("\n")
                .append("<device_info>").append("APP-002").append("</device_info>").append("\n")
                .append("<mch_id>").append(Constants.WXMCHID).append("</mch_id>").append("\n")
                .append("<nonce_str>").append(notice_str).append("</nonce_str>").append("\n")
                .append("<notify_url>").append(Constants.PAY_URL + "/weixinNotify").append("</notify_url>").append("\n")
                .append("<out_trade_no>").append(ordresn).append("</out_trade_no>").append("\n")
                .append("<spbill_create_ip>").append(ip).append("</spbill_create_ip>").append("\n")
                .append("<total_fee>").append(price).append("</total_fee>").append("\n")
                .append("<trade_type>").append("APP").append("</trade_type>").append("\n")
                .append("<sign>").append(s).append("</sign>").append("\n")
                .append("</xml>");
        byte[] postData = param.toString().getBytes();
        URL url = new URL(path);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setConnectTimeout(5 * 1000);
        urlConn.setDoOutput(true);
        urlConn.setUseCaches(false);
        urlConn.setRequestMethod("POST");
        urlConn.setInstanceFollowRedirects(true);
        urlConn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencode");
        urlConn.connect();
        DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
        dos.write(postData);
        dos.flush();
        dos.close();
        if (urlConn.getResponseCode() == 200) {
            byte[] data = readStream(urlConn.getInputStream());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new ByteArrayInputStream(data)));
            String pre_id = null;
            Element root = doc.getDocumentElement();
            NodeList firtstList = root.getChildNodes();
            for (int i = 0; i < firtstList.getLength(); i++) {
                Node itemNode = firtstList.item(i);
                if (itemNode.getNodeName().equals("prepay_id")) {
                    pre_id = itemNode.getTextContent();
                    break;
                }
            }
            Log.d("han", "pre_id=" + pre_id);
            return pre_id;
        } else {
            Log.i("han", "Post方式请求失败");
        }
        return null;
    }

    public byte[] readStream(InputStream inputStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        inputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public String getAlipayInfo(String ordersn, String subject, String body, double price) {
        return getAlipayInfo(ordersn,subject,body,price,true);
    }

    public String getAlipayInfo(String ordersn, String subject, String body, double price,boolean isTokio) {
        String payinfo = null;
        String orderInfo = getOrderInfo(ordersn, subject, body, price,isTokio);
        String sign = sign(orderInfo);
        try {
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        payinfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();
        return payinfo;
    }

    public String getAlipayHBInfo(String orderSn,String subject,String body,double price,int hbCount,boolean sellPayCharge){
        String payinfo = null;
        String orderInfo = getHBOrderInfo(orderSn, subject, body, price,hbCount,sellPayCharge);
        String sign = sign(orderInfo);
        try {
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        payinfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();
        return payinfo;
    }

    public String getOrderInfo(String ordrersn, String subject, String body, double price,boolean isTokio) {
        String orderInfo = "partner=" + "\"" + Constants.ALIPAYPARTNER + "\"";
        orderInfo += "&seller_id=" + "\"" + Constants.ALIPAYSELLER + "\"";
        orderInfo += "&out_trade_no=" + "\"" + ordrersn + "\"";
        orderInfo += "&subject=" + "\"" + subject + "\"";
        orderInfo += "&body=" + "\"" + body + "\"";
        orderInfo += "&total_fee=" + "\"" + price + "\"";
        orderInfo += "&notify_url=" + "\"" + Constants.PAY_URL + "/alipayNotify"
                + "\"";
        orderInfo += "&service=\"mobile.securitypay.pay\"";
        orderInfo += "&payment_type=\"1\"";
        orderInfo += "&_input_charset=\"utf-8\"";
        orderInfo += "&it_b_pay=\"30m\"";
        if (!isTokio){
            orderInfo += "&goods_type=\"0\"";
        }else {
            orderInfo += "&goods_type=\"1\"";
        }
        return orderInfo;
    }

    public String getHBOrderInfo(String orderSn,String subject,String body,double price,int hbCount,boolean sellPayCharge){
        StringBuilder builder=new StringBuilder();
        builder.append("partner=").append("\"").append(Constants.ALIPAYPARTNER).append("\"")
                .append("&seller_id=").append("\"").append(Constants.ALIPAYSELLER).append("\"")
                .append("&out_trade_no=").append("\"").append(orderSn).append("\"")
                .append("&subject=").append("\"").append(subject).append("\"")
                .append("&body=").append("\"").append(body).append("\"")
                .append("&total_fee=").append("\"").append(price).append("\"")
                .append("&notify_url=").append("\"").append(Constants.PAY_URL).append("/alipayNotify").append("\"")
                .append("&service=\"mobile.securitypay.pay\"")
                .append("&payment_type=\"1\"")
                .append("&_input_charset=\"utf-8\"")
                .append("&it_b_pay=\"30m\"")
                .append("&goods_type=\"1\"")
                .append("&hb_fq_param=").append("\"").append(getHBparam(hbCount,sellPayCharge)).append("\"")
                .append("&enable_pay_channels=").append("\"").append("pcreditpayInstallment").append("\"");
        return builder.toString();
    }

    private String getHBparam(int hbCount,boolean sellPayCharge){
        StringBuilder builder=new StringBuilder();
        builder.append("{").append("\"hb_fq_num\":").append("\"").append(hbCount).append("\"")
        .append(",").append("\"hb_fq_seller_percent\":").append("\"").append(sellPayCharge?0:100).append("\"").append("}");
        return builder.toString();
    }

    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    public String sign(String content) {
        return sign(content, Constants.ALIPAYPRIVATEKEY);
    }

    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));

            byte[] signed = signature.sign();

            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
