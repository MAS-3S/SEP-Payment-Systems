insert into clients (id, first_name, last_name, email) values ('793df89b-4206-462a-8e51-b704715d92c7', 'Stefan', 'Aradjanin', 'sa@mail.com');
insert into clients (id, first_name, last_name, email) values ('91b49c00-a498-41af-8c77-d0efe0fbf8a6', 'Stefan', 'Savic', 'ss@mail.com');

insert into credit_cards (id, pan, ccv, expiration_date, available_amount, reserved_amount, merchant_id, merchant_password, client_id) values ('6ff14a7f-28e8-44f3-825f-d9aed9c359a8', 'KJhefFonNkh7IKkR4hDH9CEt27A1dUxDzjPs+iz2AG4=', '201', '2022-10-01', 2000.0, 0.0, '', '', '793df89b-4206-462a-8e51-b704715d92c7');
insert into credit_cards (id, pan, ccv, expiration_date, available_amount, reserved_amount, merchant_id, merchant_password, client_id) values ('05f98f98-caa0-42f5-95db-6de66f44daf5', 'FZWd9zNUyHJGvlOh8DQMpm15+bsX0PiJ3Jm2CEu1QKQ=', '202', '2022-11-01', 2000.0, 0.0, '', '', '91b49c00-a498-41af-8c77-d0efe0fbf8a6');

--insert into transactions (id, amount, timestamp, acquirer_order_id, acquirer_timestamp, status, credit_card_id) values ('16e7103c-d12d-49fb-8d1e-6c5eac64fa7b', 1400.0, '2021-11-30 08:00:00', 'acquirerOrderId', '2021-11-30 08:00:00', 2, '6ff14a7f-28e8-44f3-825f-d9aed9c359a8');
