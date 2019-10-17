package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class InsertConsultApi extends BaseApi {

    private Long productId;
    private String productName;
    private String question;

    @Override
    protected String getPath() {
        return Constants.COMDITY_INSERT_CONSULT;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
