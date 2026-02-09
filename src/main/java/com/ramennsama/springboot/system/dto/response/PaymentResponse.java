package com.ramennsama.springboot.system.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    
    private Long id;
    
    private Long orderId;
    
    private String vnpTxnRef;
    
    private BigDecimal amount;
    
    private String responseCode;
    
    private LocalDateTime paidAt;
}
