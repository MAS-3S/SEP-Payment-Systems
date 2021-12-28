package acquirer.bank.acquirerbankbackend.dto;

import java.time.LocalDateTime;

public class PspResponse {

    private String acquirerOrderId;
    private String paymentId;
    private String merchantOrderId;
    private LocalDateTime acquirerTimeStamp;
    private boolean isSuccess;

    public PspResponse() {
    }

    public PspResponse(String acquirerOrderId, String paymentId, String merchantOrderId, LocalDateTime acquirerTimeStamp, boolean isSuccess) {
        this.acquirerOrderId = acquirerOrderId;
        this.paymentId = paymentId;
        this.merchantOrderId = merchantOrderId;
        this.acquirerTimeStamp = acquirerTimeStamp;
        this.isSuccess = isSuccess;
    }

    public String getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(String acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public LocalDateTime getAcquirerTimeStamp() {
        return acquirerTimeStamp;
    }

    public void setAcquirerTimeStamp(LocalDateTime acquirerTimeStamp) {
        this.acquirerTimeStamp = acquirerTimeStamp;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
