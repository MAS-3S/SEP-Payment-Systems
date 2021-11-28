insert into authority(id, name) values ('9fe91052-ac34-44cb-88a2-6915cfae2d20', 'ROLE_CUSTOMER');
insert into authority(id, name) values ('af1d6537-273c-4451-9b59-efe06f826e6e', 'ROLE_ADMIN');


insert into users (id, full_name, password, email) values ('1ee82676-4f9c-11ec-81d3-0242ac130003', 'Pera', '$2y$12$/YLs9Irv4CFIwl4J/JJukuounpOzs0FDtvG.rxaF5f4ZD2sr.VRQe', 'c@c.com');
insert into users (id, full_name, password, email) values ('27037478-4f9c-11ec-81d3-0242ac130003', 'Mika', '$2y$12$/YLs9Irv4CFIwl4J/JJukuounpOzs0FDtvG.rxaF5f4ZD2sr.VRQe', 'a@a.com');
insert into users (id, full_name, password, email) values ('346f24f4-4f9c-11ec-81d3-0242ac130003', 'Zika', '$2y$12$/YLs9Irv4CFIwl4J/JJukuounpOzs0FDtvG.rxaF5f4ZD2sr.VRQe', 'b@b.com');

insert into user_authority(user_id, authority_id) values ('1ee82676-4f9c-11ec-81d3-0242ac130003', '9fe91052-ac34-44cb-88a2-6915cfae2d20');
insert into user_authority(user_id, authority_id) values ('27037478-4f9c-11ec-81d3-0242ac130003', 'af1d6537-273c-4451-9b59-efe06f826e6e');
