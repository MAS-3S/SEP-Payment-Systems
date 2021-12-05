insert into clients (id, first_name, last_name, email) values ('2d484e2b-a295-4288-8b23-9bc033cf0f67', 'Products Shop', 'Web Shop', 'products@mail.com');
insert into clients (id, first_name, last_name, email) values ('43c92219-0217-431f-91a0-73034e7ef3d9', 'Conferences Shop', 'Web Shop', 'conferences@mail.com');
insert into clients (id, first_name, last_name, email) values ('ed8e04c9-2aa2-4b39-ab63-f0c509b4fa05', 'Accommodations Shop', 'Web Shop', 'accomodations@mail.com');
insert into clients (id, first_name, last_name, email) values ('f0b906c1-531a-4250-976c-8d714274fffa', 'Stefan', 'Beljic', 'sb@mail.com');

insert into credit_cards (id, pan, ccv, expiration_date, available_amount, reserved_amount, merchant_id, merchant_password, client_id) values ('51e6a597-980b-4b62-ace7-fdf74965ed69', '1232647215115894542', '101', '2022-10-01', 10000.0, 0.0, 'bc5d6b00ae2c49a69b546d5cc2c541', '84914458f046', '2d484e2b-a295-4288-8b23-9bc033cf0f67');
insert into credit_cards (id, pan, ccv, expiration_date, available_amount, reserved_amount, merchant_id, merchant_password, client_id) values ('27688257-09a3-4547-87b5-3c05dde29ef0', '1233785441595214842', '102', '2022-11-01', 11000.0, 0.0, '26458b6630e84676b7b2dd0a520452', 'e70fa9542b6b', '43c92219-0217-431f-91a0-73034e7ef3d9');
insert into credit_cards (id, pan, ccv, expiration_date, available_amount, reserved_amount, merchant_id, merchant_password, client_id) values ('04c6051d-01d7-4c2a-a5a3-de0328aa7f2d', '1239157613414465942', '103', '2022-12-01', 12000.0, 0.0, '704e8c82433543998e2dee2ds3e90e', '90ca9b647e7b', 'ed8e04c9-2aa2-4b39-ab63-f0c509b4fa05');
insert into credit_cards (id, pan, ccv, expiration_date, available_amount, reserved_amount, merchant_id, merchant_password, client_id) values ('9b7a23dc-83a8-4383-b327-338c1fb6249b', '1239764335115145742', '104', '2022-12-01', 2000.0, 0.0, '', '', 'f0b906c1-531a-4250-976c-8d714274fffa');

insert into transactions (id, amount, timestamp, order_id, merchant_pan, customer_pan, success_url, failed_url, error_url, status) values ('a81028c6-62b4-47ca-a20f-33ba75c327fc', 1400.0, '2021-11-30 08:00:00', 'a06e51b7-cbcf-4223-be6f-4381becdfeec', '', '', '', '', '', 2);