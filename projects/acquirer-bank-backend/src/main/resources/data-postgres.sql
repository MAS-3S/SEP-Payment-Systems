insert into clients (id, first_name, last_name, email) values ('2d484e2b-a295-4288-8b23-9bc033cf0f67', 'Products Shop', 'Web Shop', 'products@mail.com');
insert into clients (id, first_name, last_name, email) values ('43c92219-0217-431f-91a0-73034e7ef3d9', 'Conferences Shop', 'Web Shop', 'conferences@mail.com');
insert into clients (id, first_name, last_name, email) values ('ed8e04c9-2aa2-4b39-ab63-f0c509b4fa05', 'Accommodations Shop', 'Web Shop', 'accomodations@mail.com');
insert into clients (id, first_name, last_name, email) values ('f0b906c1-531a-4250-976c-8d714274fffa', 'Stefan', 'Beljic', 'accomodations@mail.com');

insert into credit_cards (id, pan, ccv, expiration_date, account_number, available_amount, reserved_amount, merchant_id, merchant_password, client_id) values ('51e6a597-980b-4b62-ace7-fdf74965ed69', 'pan', 'ccv', '2022-10-01', 'acc_num', 10000.0, 0.0, 'mid', 'mp', '2d484e2b-a295-4288-8b23-9bc033cf0f67');
insert into credit_cards (id, pan, ccv, expiration_date, account_number, available_amount, reserved_amount, merchant_id, merchant_password, client_id) values ('27688257-09a3-4547-87b5-3c05dde29ef0', 'pan', 'ccv', '2022-11-01', 'acc_num', 11000.0, 0.0, 'mid', 'mp', '43c92219-0217-431f-91a0-73034e7ef3d9');
insert into credit_cards (id, pan, ccv, expiration_date, account_number, available_amount, reserved_amount, merchant_id, merchant_password, client_id) values ('04c6051d-01d7-4c2a-a5a3-de0328aa7f2d', 'pan', 'ccv', '2022-12-01', 'acc_num', 12000.0, 0.0, 'mid', 'mp', 'ed8e04c9-2aa2-4b39-ab63-f0c509b4fa05');
insert into credit_cards (id, pan, ccv, expiration_date, account_number, available_amount, reserved_amount, merchant_id, merchant_password, client_id) values ('9b7a23dc-83a8-4383-b327-338c1fb6249b', 'pan', 'ccv', '2022-12-01', 'acc_num', 2000.0, 0.0, '', '', 'f0b906c1-531a-4250-976c-8d714274fffa');

insert into transactions (id, amount, timestamp, order_id, merchant_pan, customer_pan, success_url, failed_url, error_url, status) values ('a81028c6-62b4-47ca-a20f-33ba75c327fc', 1400.0, '2021-11-30 08:00:00', 'a06e51b7-cbcf-4223-be6f-4381becdfeec', '', '', '', '', '', 2);
