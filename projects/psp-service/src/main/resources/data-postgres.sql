insert into payment_method_types(id, name) values ('c0079450-7db8-442b-98c5-6488709ac526', 'Credit card');
insert into payment_method_types(id, name) values ('02f5059d-4b51-491c-ae18-0a42733f5a14', 'QR code');
insert into payment_method_types(id, name) values ('0462137e-f196-4f13-9660-b81bcbf25ce6', 'PayPal');
insert into payment_method_types(id, name) values ('f8a5841a-5957-4550-a417-20c7504f59ff', 'BitCoin');

--prvi merchant je prvi webshop
insert into merchants(id, merchant_id, name, return_url) values ('8ce8e7f3-5da0-4ace-8b52-af561e3aca79', '902a1e99-87e9-43b1-8927-9fbd4c2d9ca8', 'Products Shop', '');

--test ima paypal
insert into merchants_payment_method_types(merchant_id, payment_method_types_id) values ('8ce8e7f3-5da0-4ace-8b52-af561e3aca79', '0462137e-f196-4f13-9660-b81bcbf25ce6');