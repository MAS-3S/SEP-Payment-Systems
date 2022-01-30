insert into payment_method_types(id, name, image, description, service_name) values ('c0079450-7db8-442b-98c5-6488709ac526', 'Credit card', 'CreditCard.png', 'A credit card is a payment card issued to users (cardholders) to enable the cardholder to pay a merchant for goods and services based on the cardholder accrued debt.', 'bank-service');
insert into payment_method_types(id, name, image, description, service_name) values ('02f5059d-4b51-491c-ae18-0a42733f5a14', 'QR code', 'QRCode.png', 'QR code payment is a contactless payment method where payment is performed by scanning a QR code from a mobile app.', 'qr-service');
insert into payment_method_types(id, name, image, description, service_name) values ('0462137e-f196-4f13-9660-b81bcbf25ce6', 'PayPal', 'PayPal.png', 'PayPal provides an easy and quick way to send and request money online. You can transfer money (abroad) to family, friends, online shops, and auction sites like eBay.', 'paypal-service');
insert into payment_method_types(id, name, image, description, service_name) values ('f8a5841a-5957-4550-a417-20c7504f59ff', 'BitCoin', 'Bitcoin.png', 'Bitcoin is a decentralized digital currency, without a central bank or single administrator, that can be sent from user to user on the peer-to-peer bitcoin network without the need for intermediaries.', 'bitcoin-service');

-- Web Shops
insert into merchants(id, merchant_id, name, success_url, failed_url, error_url) values ('8ce8e7f3-5da0-4ace-8b52-af561e3aca79', '902a1e99-87e9-43b1-8927-9fbd4c2d9ca8', 'Products Shop', 'https://192.168.1.7:3000/success-transaction/', 'https://192.168.1.7:3000/failed-transaction/', 'https://192.168.1.7:3000/error-transaction/');
insert into merchants(id, merchant_id, name, success_url, failed_url, error_url) values ('1e3ba3a8-5369-4301-92fa-4b534c6b5144', '999c0c7c-aff3-41df-957d-d0677d43007b', 'Conferences Shop', 'https://192.168.1.7:3000/success-transaction/', 'https://192.168.1.7:3000/failed-transaction/', 'https://192.168.1.7:3000/error-transaction/');
insert into merchants(id, merchant_id, name, success_url, failed_url, error_url) values ('aa9b922c-0106-4316-9eff-e29b0ce63fc3', '4086f1d4-4002-4f1e-9bf8-ab0e82048584', 'Accommodations Shop', 'https://192.168.1.7:3000/success-transaction/', 'https://192.168.1.7:3000/failed-transaction/', 'https://192.168.1.7:3000/error-transaction/');

-- Products Web Shop
insert into merchants_payment_method_types(merchant_id, payment_method_types_id) values ('8ce8e7f3-5da0-4ace-8b52-af561e3aca79', 'c0079450-7db8-442b-98c5-6488709ac526');
insert into merchants_payment_method_types(merchant_id, payment_method_types_id) values ('8ce8e7f3-5da0-4ace-8b52-af561e3aca79', '02f5059d-4b51-491c-ae18-0a42733f5a14');
-- Conferences Web Shop
insert into merchants_payment_method_types(merchant_id, payment_method_types_id) values ('1e3ba3a8-5369-4301-92fa-4b534c6b5144', '0462137e-f196-4f13-9660-b81bcbf25ce6');
insert into merchants_payment_method_types(merchant_id, payment_method_types_id) values ('1e3ba3a8-5369-4301-92fa-4b534c6b5144', 'c0079450-7db8-442b-98c5-6488709ac526');
-- Accommodations Web Shop
insert into merchants_payment_method_types(merchant_id, payment_method_types_id) values ('aa9b922c-0106-4316-9eff-e29b0ce63fc3', 'f8a5841a-5957-4550-a417-20c7504f59ff');

insert into payments(id, amount, currency, merchant_order_id, merchant_time_stamp, return_url, payment_method_type_id, is_possible_subscription) values ('d24a16c6-6533-4fb4-87b9-b4d76ab568a0', 1400.0, 'EUR', 'a06e51b7-cbcf-4223-be6f-4381becdfeec', '2021-11-30 08:00:00', '', 'c0079450-7db8-442b-98c5-6488709ac526', false);