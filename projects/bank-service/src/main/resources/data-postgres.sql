insert into merchants(id, merchant_id, bank_merchant_id, bank_merchant_password) values ('efffe056-17fb-4684-9ca7-e07476f76135', '902a1e99-87e9-43b1-8927-9fbd4c2d9ca8', 'bc5d6b00ae2c49a69b546d5cc2c541', '84914458f046');
insert into merchants(id, merchant_id, bank_merchant_id, bank_merchant_password) values ('17269cca-1799-42d3-8d5b-b0033dd62fcd', '999c0c7c-aff3-41df-957d-d0677d43007b', '26458b6630e84676b7b2dd0a520452', 'e70fa9542b6b');
insert into merchants(id, merchant_id, bank_merchant_id, bank_merchant_password) values ('e8c039ad-eecc-4a79-b67a-f64a1b80eb2d', '4086f1d4-4002-4f1e-9bf8-ab0e82048584', '704e8c82433543998e2dee2ds3e90e', '90ca9b647e7b');

insert into transactions(id, order_id, bank_transaction_id, amount, timestamp, return_url, merchant_id, status) values ('0cae9d4d-8765-49a9-8eba-3b087e03f99d', 'a06e51b7-cbcf-4223-be6f-4381becdfeec', 'a81028c6-62b4-47ca-a20f-33ba75c327fc', 1400.0, '2021-11-30 08:00:00', 'http://localhost:3002/acquirer-bank/transaction/a81028c6-62b4-47ca-a20f-33ba75c327fc', 'efffe056-17fb-4684-9ca7-e07476f76135', 1);







