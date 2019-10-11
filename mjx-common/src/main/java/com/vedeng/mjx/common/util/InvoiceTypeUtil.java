package com.vedeng.mjx.common.util;

import com.vedeng.mjx.common.enumUtils.InvoiceTypeEnum;

public class InvoiceTypeUtil {

    public static Integer getInvoiceType(Integer type){

        Integer invoiceType = 0;
        if(type.equals(InvoiceTypeEnum.TYPE_2.getType())){
            invoiceType = InvoiceTypeEnum.TYPE_1.getType();
        }else if(type.equals(InvoiceTypeEnum.TYPE_3.getType())){
            invoiceType = InvoiceTypeEnum.TYPE_1.getType();
        }else if(type.equals(InvoiceTypeEnum.TYPE_5.getType())){
            invoiceType = InvoiceTypeEnum.TYPE_4.getType();
        }else if(type.equals(InvoiceTypeEnum.TYPE_6.getType())){
            invoiceType = InvoiceTypeEnum.TYPE_4.getType();
        }else{
            invoiceType = InvoiceTypeEnum.TYPE_7.getType();
        }
        return invoiceType;
    }

}
