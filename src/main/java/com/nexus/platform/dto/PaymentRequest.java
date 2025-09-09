package com.nexus.platform.dto;

import com.nexus.platform.entity.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private BigDecimal amount;
    private TransactionType type;
    private Long recipientId;
}
