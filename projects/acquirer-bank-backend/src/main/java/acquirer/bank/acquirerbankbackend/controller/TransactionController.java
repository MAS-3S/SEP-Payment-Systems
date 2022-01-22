package acquirer.bank.acquirerbankbackend.controller;

import acquirer.bank.acquirerbankbackend.dto.*;
import acquirer.bank.acquirerbankbackend.model.CreditCard;
import acquirer.bank.acquirerbankbackend.model.Transaction;
import acquirer.bank.acquirerbankbackend.qrcode.QRCodeGenerator;
import acquirer.bank.acquirerbankbackend.service.ITransactionService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@RestController
@RequestMapping(value = "api/transactions")
public class TransactionController {

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/images/QRCode.png";

    @Value("${encryption.key}")
    private String encryptionKey;
    @Value("${encryption.vector}")
    private String encryptionVector;

    @Autowired
    private ITransactionService transactionService;

    @PostMapping(value="/check", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> checkTransactionAndReturnUrl(@RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.checkTransactionAndReturnUrl(transactionRequest, "creditCard");
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

    @PostMapping(value="/qrCode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> checkTransactionAndReturnQrCode(@RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.checkTransactionAndReturnUrl(transactionRequest, "qrCode");
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/execute/{transactionId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse executeTransaction(@PathVariable String transactionId, @RequestBody CreditCardRequest creditCardRequest) throws URISyntaxException {
        return transactionService.executeTransaction(transactionId, creditCardRequest, "creditCard");
    }

    @PostMapping(value = "/execute/{transactionId}/qrCode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse executeQrCodeTransaction(@PathVariable String transactionId, @RequestBody CreditCardRequest creditCardRequest) throws URISyntaxException {
        return transactionService.executeTransaction(transactionId, creditCardRequest, "qrCode");
    }

    @PostMapping(value="/paymentWage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WageResponse> paymentWage(@RequestBody WageTransactionRequest wageTransactionRequest) {
        WageResponse wageResponse = transactionService.paymentWage(wageTransactionRequest);
        return new ResponseEntity<>(wageResponse, HttpStatus.OK);
    }

    @GetMapping(value="/qrCode/{transactionId}")
    public ResponseEntity<String> getQRCode(@PathVariable String transactionId){
        Transaction transaction = transactionService.findById(transactionId);
        if(transaction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CreditCard creditCard = transactionService.findCreditCardByMerchantPan(transaction.getMerchantPan());
        String data = formatQrCodeData(transaction, creditCard);
        byte[] image = new byte[0];
        try {

            // Generate and Return Qr Code in Byte Array
            image = QRCodeGenerator.getQRCodeImage(data,300,300);

            // Generate and Save Qr Code Image in static/images folder
            QRCodeGenerator.generateQRCodeImage(data,300,300, QR_CODE_IMAGE_PATH);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        // Convert Byte Array into Base64 Encode String
        String qrcode = Base64.getEncoder().encodeToString(image);

        return new ResponseEntity<>("data:image/jpg;base64," + qrcode, HttpStatus.OK);
    }

    private String formatQrCodeData(Transaction transaction, CreditCard merchantCreditCard) {
        String decryptedMerchantPan = this.decryptPan(transaction.getMerchantPan());
        return "Transaction{\n" +
                "amount=" + transaction.getAmount() + ",\n" +
                "currency=" + transaction.getCurrency() + ",\n" +
                "timestamp=" + transaction.getTimestamp().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + ",\n" +
                "merchantName=" + merchantCreditCard.getClient().getFirstName() + " " + merchantCreditCard.getClient().getLastName() + ",\n" +
                "merchantPan=" + decryptedMerchantPan.substring(0, 4) + " - **** - **** - " + decryptedMerchantPan.substring(12) + "\n" +
                '}';
    }

    public String decryptPan(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(this.encryptionVector.getBytes("UTF-8"));
            SecretKeySpec keySpec = new SecretKeySpec(this.encryptionKey.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
