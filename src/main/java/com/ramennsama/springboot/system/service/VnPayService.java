package com.ramennsama.springboot.system.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ramennsama.springboot.system.utils.VnPayUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VnPayService {

    @Value("${vnpay.version}")
    private String version;

    @Value("${vnpay.command}")
    private String command;

    @Value("${vnpay.currCode}")
    private String currCode;

    @Value("${vnpay.ortherType}")
    private String ortherType;

    @Value("${vnpay.locale}")
    private String locale;

    @Value("${vnpay.tmnCode}")
    private String tmnCode;

    @Value("${vnpay.hashSecret}")
    private String hashSecret;

    @Value("${vnpay.payUrl}")
    private String payUrl;

    @Value("${vnpay.returnUrl}")
    private String returnUrl;

    public String createPaymentUrl(int amount, String txnRef) {

        Map<String, String> vnpParams = new HashMap<>();

        vnpParams.put("vnp_Version", version);
        vnpParams.put("vnp_Command", command);
        vnpParams.put("vnp_TmnCode", tmnCode);

        vnpParams.put("vnp_Amount", String.valueOf(amount * 100));
        vnpParams.put("vnp_CurrCode", currCode);

        // dùng txnRef từ ngoài
        vnpParams.put("vnp_TxnRef", txnRef);

        vnpParams.put("vnp_OrderInfo", "Thanh toan don hang " + txnRef);
        vnpParams.put("vnp_OrderType", ortherType);

        vnpParams.put("vnp_Locale", locale);
        vnpParams.put("vnp_ReturnUrl", returnUrl);
        vnpParams.put("vnp_IpAddr", "127.0.0.1");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        vnpParams.put("vnp_CreateDate", formatter.format(new Date()));

        String query = VnPayUtil.buildQuery(vnpParams);
        String secureHash = VnPayUtil.hmacSHA512(hashSecret, query);
        
        String fullUrl = payUrl + "?" + query + "&vnp_SecureHash=" + secureHash;
        
        log.info("=== VNPay Payment URL Created ===");
        log.info("TxnRef: {}", txnRef);
        log.info("Amount: {}", amount);
        log.info("Payment URL: {}", fullUrl);
        log.info("===================================");

        return fullUrl;
    }

    public boolean verifyReturn(Map<String,String> params){

        String vnpSecureHash = params.remove("vnp_SecureHash");

        String signData = VnPayUtil.buildQuery(params);
        String checkHash = VnPayUtil.hmacSHA512(hashSecret, signData);

        return checkHash.equals(vnpSecureHash);
    }
}
