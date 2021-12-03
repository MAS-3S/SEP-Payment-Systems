package acquirer.bank.acquirerbankbackend.model;

import acquirer.bank.acquirerbankbackend.model.enums.TransactionStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="transactions")
public class Transaction {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "timestamp", nullable = false)
    private LocalDate timestamp;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "merchant_pan", nullable = false)
    private String merchantPan;

    @Column(name = "customer_pan", nullable = false)
    private String customerPan;

    @Column(name = "success_url", nullable = false)
    private String successUrl;

    @Column(name = "failed_url", nullable = false)
    private String failedUrl;

    @Column(name = "error_url", nullable = false)
    private String errorUrl;

    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    public Transaction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMerchantPan() {
        return merchantPan;
    }

    public void setMerchantPan(String merchantPan) {
        this.merchantPan = merchantPan;
    }

    public String getCustomerPan() {
        return customerPan;
    }

    public void setCustomerPan(String customerPan) {
        this.customerPan = customerPan;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFailedUrl() {
        return failedUrl;
    }

    public void setFailedUrl(String failedUrl) {
        this.failedUrl = failedUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
