insert into authority(id, name) values ('9fe91052-ac34-44cb-88a2-6915cfae2d20', 'ROLE_CUSTOMER');
insert into authority(id, name) values ('af1d6537-273c-4451-9b59-efe06f826e6e', 'ROLE_ADMIN');

-- 11aaAA@@
insert into users (id, password, full_name, email, address, phone, role, registered) values ('1ee82676-4f9c-11ec-81d3-0242ac130003', '$2y$10$qtwLWVTQMKSjFvIviXg9teAdxqIyR.QgAgNKwDPm7iQBepQcp9Ade', 'Stefan Aradjanin', 'sa@mail.com', 'Cara Dusana 15', '0602236975', 1, true);
insert into users (id, password, full_name, email, address, phone, role, registered) values ('27037478-4f9c-11ec-81d3-0242ac130003', '$2y$10$qtwLWVTQMKSjFvIviXg9teAdxqIyR.QgAgNKwDPm7iQBepQcp9Ade', 'Stefan Savic', 'ss@mail.com', 'Zmaj Jovina 20', '0606974458', 1, true);
insert into users (id, password, full_name, email, address, phone, role, registered) values ('346f24f4-4f9c-11ec-81d3-0242ac130003', '$2y$10$qtwLWVTQMKSjFvIviXg9teAdxqIyR.QgAgNKwDPm7iQBepQcp9Ade', 'Stefan Beljic', 'sb@mail.com', 'Josifa Runjanina 3', '0605498554', 1, true);

insert into user_authority(user_id, authority_id) values ('1ee82676-4f9c-11ec-81d3-0242ac130003', '9fe91052-ac34-44cb-88a2-6915cfae2d20');
insert into user_authority(user_id, authority_id) values ('27037478-4f9c-11ec-81d3-0242ac130003', '9fe91052-ac34-44cb-88a2-6915cfae2d20');
insert into user_authority(user_id, authority_id) values ('346f24f4-4f9c-11ec-81d3-0242ac130003', 'af1d6537-273c-4451-9b59-efe06f826e6e');

insert into web_shops (id, name, type) values ('902a1e99-87e9-43b1-8927-9fbd4c2d9ca8', 'Products Shop', 0);
insert into web_shops (id, name, type) values ('999c0c7c-aff3-41df-957d-d0677d43007b', 'Conferences Shop', 1);
insert into web_shops (id, name, type) values ('4086f1d4-4002-4f1e-9bf8-ab0e82048584', 'Accommodation Shop', 2);

insert into products (id, name, description, price, available_balance, image, currency, web_shop_id) values ('05ba8c89-607c-4632-9941-908b0c00e7e2', 'Gaming dask', 'The best gaming dask', 700.0, 10, 'dask.jpg', 1, '902a1e99-87e9-43b1-8927-9fbd4c2d9ca8');
insert into products (id, name, description, price, available_balance, image, currency, web_shop_id) values ('36e49319-e965-434d-960c-2687c61abae1', 'Gaming chair', 'The best gaming chair', 200.0, 12, 'chair.jpg', 1, '902a1e99-87e9-43b1-8927-9fbd4c2d9ca8');
insert into products (id, name, description, price, available_balance, image, currency, web_shop_id) values ('68016f3f-2383-4a86-a943-3bdce2b0a86f', 'IT conference', 'The best it conference', 50.0, 10, 'itconference.jpg', 1, '999c0c7c-aff3-41df-957d-d0677d43007b');
insert into products (id, name, description, price, available_balance, image, currency, web_shop_id) values ('b17e3c8e-c954-486b-9aa2-c0c64893f564', 'Security course', 'The best security course', 60.0, 12, 'securityconference.jpg', 1, '999c0c7c-aff3-41df-957d-d0677d43007b');
insert into products (id, name, description, price, available_balance, image, currency, web_shop_id) values ('8b9df238-e395-41a8-88a5-7fec8e106a5b', 'Hotel', 'The best hotel', 30.0, 10, 'hotel.jpg', 1, '4086f1d4-4002-4f1e-9bf8-ab0e82048584');
insert into products (id, name, description, price, available_balance, image, currency, web_shop_id) values ('59c3c356-9ba6-4b5e-b78e-093cd87cf670', 'Motel', 'The best motel', 20.0, 12, 'motel.jpg', 1, '4086f1d4-4002-4f1e-9bf8-ab0e82048584');

insert into conferences (id, address, start_time, end_time, is_course, is_subscription, web_shop_id) values ('68016f3f-2383-4a86-a943-3bdce2b0a86f', 'Ustanicka 123, Beograd', '2021-11-30 08:00:00', '2021-11-30 09:00:00', false, false, '999c0c7c-aff3-41df-957d-d0677d43007b');
insert into conferences (id, address, start_time, end_time, is_course, is_subscription, web_shop_id) values ('b17e3c8e-c954-486b-9aa2-c0c64893f564', 'Cara Lazara 90, Novi Sad', '2021-12-25 14:00:00', '2021-12-25 15:00:00', true, false, '999c0c7c-aff3-41df-957d-d0677d43007b');

insert into transports (id, name, price, currency) values ('b885766b-430d-44c3-98ef-08d922a8275f', 'Airplane', 120.0, 1);
insert into transports (id, name, price, currency) values ('af5e134d-79fd-46b8-b366-81efd3808a81', 'Bus', 30.0, 1);
insert into transports (id, name, price, currency) values ('c44bef73-d9ca-4377-ba7f-6a645f11061e', 'Ship', 50.0, 1);
insert into transports (id, name, price, currency) values ('1995073e-a59b-4735-a8e7-38ec5e6213c8', 'Train', 30.0, 1);

insert into accommodations (id, address, start_date, days, number_of_beds, transport_id, web_shop_id) values ('8b9df238-e395-41a8-88a5-7fec8e106a5b', 'Ustanicka 15, Beograd', '2021-11-30', 3, 2, 'b885766b-430d-44c3-98ef-08d922a8275f', '4086f1d4-4002-4f1e-9bf8-ab0e82048584');
insert into accommodations (id, address, start_date, days, number_of_beds, transport_id, web_shop_id) values ('59c3c356-9ba6-4b5e-b78e-093cd87cf670', 'Cara Lazara 22, Novi Sad', '2021-12-20', 2, 1, 'af5e134d-79fd-46b8-b366-81efd3808a81', '4086f1d4-4002-4f1e-9bf8-ab0e82048584');

insert into shopping_carts (id, timestamp, total_price, user_id, web_shop_id) values ('6c0f1553-baaf-4d0e-9434-8d7dc3b352fa', '2021-11-30 08:00:00', 1400.0, '1ee82676-4f9c-11ec-81d3-0242ac130003', '902a1e99-87e9-43b1-8927-9fbd4c2d9ca8');

insert into items_to_purchase (id, quantity, product_id, shopping_cart_id) values ('099ff31b-0c67-4e19-aa78-1396de7faeca', 2, '05ba8c89-607c-4632-9941-908b0c00e7e2', '6c0f1553-baaf-4d0e-9434-8d7dc3b352fa');

insert into transactions (id, timestamp, amount, order_id, shopping_cart_id) values ('a06e51b7-cbcf-4223-be6f-4381becdfeec', '2021-11-30 08:00:00', 1400.0, '6c0f1553-baaf-4d0e-9434-8d7dc3b352fa', '6c0f1553-baaf-4d0e-9434-8d7dc3b352fa');

