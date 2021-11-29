insert into authority(id, name) values ('9fe91052-ac34-44cb-88a2-6915cfae2d20', 'ROLE_CUSTOMER');
insert into authority(id, name) values ('af1d6537-273c-4451-9b59-efe06f826e6e', 'ROLE_ADMIN');


insert into users (id, password, full_name, email, address, phone, role, registered) values ('1ee82676-4f9c-11ec-81d3-0242ac130003', '$2y$12$/YLs9Irv4CFIwl4J/JJukuounpOzs0FDtvG.rxaF5f4ZD2sr.VRQe', 'Stefan Aradjanin', 'sa@mail.com', 'Cara Dusana 15', '0602236975', 0, true);
insert into users (id, password, full_name, email, address, phone, role, registered) values ('27037478-4f9c-11ec-81d3-0242ac130003', '$2y$12$/YLs9Irv4CFIwl4J/JJukuounpOzs0FDtvG.rxaF5f4ZD2sr.VRQe', 'Stefan Savic', 'sa@mail.com', 'Zmaj Jovina 20', '0606974458', 0, true);
insert into users (id, password, full_name, email, address, phone, role, registered) values ('346f24f4-4f9c-11ec-81d3-0242ac130003', '$2y$12$/YLs9Irv4CFIwl4J/JJukuounpOzs0FDtvG.rxaF5f4ZD2sr.VRQe', 'Stefan Beljic', 'sa@mail.com', 'Josifa Runjanina 3', '0605498554', 1, true);


insert into user_authority(user_id, authority_id) values ('1ee82676-4f9c-11ec-81d3-0242ac130003', '9fe91052-ac34-44cb-88a2-6915cfae2d20');
insert into user_authority(user_id, authority_id) values ('27037478-4f9c-11ec-81d3-0242ac130003', '9fe91052-ac34-44cb-88a2-6915cfae2d20');
insert into user_authority(user_id, authority_id) values ('346f24f4-4f9c-11ec-81d3-0242ac130003', 'af1d6537-273c-4451-9b59-efe06f826e6e');

insert into web_shops (id, name, type) values ('902a1e99-87e9-43b1-8927-9fbd4c2d9ca8', 'Products Shop', 0);
insert into web_shops (id, name, type) values ('999c0c7c-aff3-41df-957d-d0677d43007b', 'Conferences Shop', 1);
insert into web_shops (id, name, type) values ('4086f1d4-4002-4f1e-9bf8-ab0e82048584', 'Accommodation Shop', 2);

insert into products (id, name, description, price, available_balance, image, currency, web_shop_id) values ('05ba8c89-607c-4632-9941-908b0c00e7e2', 'product1', 'description1', 500.0, 10, 'image1', 0, '902a1e99-87e9-43b1-8927-9fbd4c2d9ca8');
insert into products (id, name, description, price, available_balance, image, currency, web_shop_id) values ('36e49319-e965-434d-960c-2687c61abae1', 'product2', 'description2', 600.0, 12, 'image2', 0, '902a1e99-87e9-43b1-8927-9fbd4c2d9ca8');
insert into products (id, name, description, price, available_balance, image, currency, web_shop_id) values ('68016f3f-2383-4a86-a943-3bdce2b0a86f', 'conference1', 'description1', 500.0, 10, 'image1', 0, '999c0c7c-aff3-41df-957d-d0677d43007b');
insert into products (id, name, description, price, available_balance, image, currency, web_shop_id) values ('b17e3c8e-c954-486b-9aa2-c0c64893f564', 'conference2', 'description2', 600.0, 12, 'image2', 0, '999c0c7c-aff3-41df-957d-d0677d43007b');
insert into products (id, name, description, price, available_balance, image, currency, web_shop_id) values ('8b9df238-e395-41a8-88a5-7fec8e106a5b', 'accommodation1', 'description1', 500.0, 10, 'image1', 0, '4086f1d4-4002-4f1e-9bf8-ab0e82048584');
insert into products (id, name, description, price, available_balance, image, currency, web_shop_id) values ('59c3c356-9ba6-4b5e-b78e-093cd87cf670', 'accommodation2', 'description2', 600.0, 12, 'image2', 0, '4086f1d4-4002-4f1e-9bf8-ab0e82048584');

insert into conferences (id, address, start_time, end_time, is_course, is_subscription, web_shop_id) values ('68016f3f-2383-4a86-a943-3bdce2b0a86f', 'address1', '2021-11-30 08:00:00', '2021-11-30 09:00:00', false, false, '999c0c7c-aff3-41df-957d-d0677d43007b');
insert into conferences (id, address, start_time, end_time, is_course, is_subscription, web_shop_id) values ('b17e3c8e-c954-486b-9aa2-c0c64893f564', 'address2', '2021-12-25 14:00:00', '2021-12-25 15:00:00', false, false, '999c0c7c-aff3-41df-957d-d0677d43007b');

insert into transports (id, name, price, currency) values ('b885766b-430d-44c3-98ef-08d922a8275f', 'Avion', 4000.0, 0);
insert into transports (id, name, price, currency) values ('af5e134d-79fd-46b8-b366-81efd3808a81', 'Autobus', 2000.0, 0);
insert into transports (id, name, price, currency) values ('c44bef73-d9ca-4377-ba7f-6a645f11061e', 'Brod', 3000.0, 0);
insert into transports (id, name, price, currency) values ('1995073e-a59b-4735-a8e7-38ec5e6213c8', 'Voz', 2000.0, 0);

insert into accommodations (id, address, start_date, days, number_of_beds, transport_id, web_shop_id) values ('8b9df238-e395-41a8-88a5-7fec8e106a5b', 'address1', '2021-11-30', 3, 2, 'b885766b-430d-44c3-98ef-08d922a8275f', '4086f1d4-4002-4f1e-9bf8-ab0e82048584');
insert into accommodations (id, address, start_date, days, number_of_beds, transport_id, web_shop_id) values ('59c3c356-9ba6-4b5e-b78e-093cd87cf670', 'address2', '2021-12-20', 2, 1, 'af5e134d-79fd-46b8-b366-81efd3808a81', '4086f1d4-4002-4f1e-9bf8-ab0e82048584');

