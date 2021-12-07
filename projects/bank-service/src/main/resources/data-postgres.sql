insert into merchants(id, merchant_id, bank_merchant_id, bank_merchant_password) values ('efffe056-17fb-4684-9ca7-e07476f76135', '902a1e99-87e9-43b1-8927-9fbd4c2d9ca8', 'bc5d6b00ae2c49a69b546d5cc2c541', '84914458f046');

insert into transactions(id, order_id, bank_transaction_id, amount, timestamp, return_url, merchant_id, status) values ('0cae9d4d-8765-49a9-8eba-3b087e03f99d', 'a06e51b7-cbcf-4223-be6f-4381becdfeec', 'a81028c6-62b4-47ca-a20f-33ba75c327fc', 1400.0, '2021-11-30 08:00:00', 'http://localhost:3002/acquirer-bank/transaction/a81028c6-62b4-47ca-a20f-33ba75c327fc', 'efffe056-17fb-4684-9ca7-e07476f76135', 1);







