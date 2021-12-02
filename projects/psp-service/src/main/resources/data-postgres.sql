insert into payment_method_types(id, name, image, description) values ('c0079450-7db8-442b-98c5-6488709ac526', 'Credit card', 'CreditCard.png', 'A credit card is a payment card issued to users (cardholders) to enable the cardholder to pay a merchant for goods and services based on the cardholder accrued debt.');
insert into payment_method_types(id, name, image, description) values ('02f5059d-4b51-491c-ae18-0a42733f5a14', 'QR code', 'QRCode.png', 'QR code payment is a contactless payment method where payment is performed by scanning a QR code from a mobile app.');
insert into payment_method_types(id, name, image, description) values ('0462137e-f196-4f13-9660-b81bcbf25ce6', 'PayPal', 'PayPal.png', 'PayPal provides an easy and quick way to send and request money online. You can transfer money (abroad) to family, friends, online shops, and auction sites like eBay.');
insert into payment_method_types(id, name, image, description) values ('f8a5841a-5957-4550-a417-20c7504f59ff', 'BitCoin', 'Bitcoin.png', 'Bitcoin is a decentralized digital currency, without a central bank or single administrator, that can be sent from user to user on the peer-to-peer bitcoin network without the need for intermediaries.');

--prvi merchant je prvi webshop
insert into merchants(id, merchant_id, name, return_url) values ('8ce8e7f3-5da0-4ace-8b52-af561e3aca79', '902a1e99-87e9-43b1-8927-9fbd4c2d9ca8', 'Products Shop', '');

--test ima paypal
insert into merchants_payment_method_types(merchant_id, payment_method_types_id) values ('8ce8e7f3-5da0-4ace-8b52-af561e3aca79', '0462137e-f196-4f13-9660-b81bcbf25ce6');