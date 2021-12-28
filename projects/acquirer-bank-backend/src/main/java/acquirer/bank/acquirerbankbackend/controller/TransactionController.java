package acquirer.bank.acquirerbankbackend.controller;

import acquirer.bank.acquirerbankbackend.dto.CreditCardRequest;
import acquirer.bank.acquirerbankbackend.dto.TransactionRequest;
import acquirer.bank.acquirerbankbackend.dto.TransactionResponse;
import acquirer.bank.acquirerbankbackend.model.Transaction;
import acquirer.bank.acquirerbankbackend.qrcode.QRCodeGenerator;
import acquirer.bank.acquirerbankbackend.service.ITransactionService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;

@RestController
@RequestMapping(value = "api/transactions")
public class TransactionController {

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/images/QRCode.png";

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

    @GetMapping(value="/qrCode/{transactionId}")
    public ResponseEntity<String>  getQRCode(@PathVariable String transactionId){
        Transaction transaction = transactionService.findById(transactionId);
        if(transaction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String data = transaction.toString();
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
}
