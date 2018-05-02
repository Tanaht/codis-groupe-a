INSERT INTO role values(1,'ROLE_CODIS_USER');
INSERT INTO role values(2,'ROLE_SIMPLE_USER');
INSERT INTO role values(3,'ROLE_DRONE_USER');


-- userlogin = password
INSERT INTO user values(1,'codis','codis',1);
INSERT INTO user values(2,'simple','simple',2);
INSERT INTO user values(3,'drone','drone',3);
INSERT INTO user values(4,'user','user',1);
INSERT INTO user values(5,'codis1','codis1',1);
INSERT INTO user values(6,'codis2','codis2',1);
INSERT INTO user values(7,'codis3','codis3',1);
INSERT INTO user values(8,'simple1','simple1',2);
INSERT INTO user values(9,'simple2','simple2',2);
INSERT INTO user values(10,'simple3','simple3',2);

-- Insert Sinister code
INSERT INTO sinister_code VALUES (1,'INC');
INSERT INTO sinister_code VALUES (2,'SAP');

-- Insert Vehicule_Type
INSERT INTO vehicle_type VALUES (1, 'VSAV');
INSERT INTO vehicle_type VALUES (2, 'FPT');
INSERT INTO vehicle_type VALUES (3, 'VLCG');

-- Insert Vehicle
INSERT INTO vehicle VALUES (1, 'VSAV 1', 1);
INSERT INTO vehicle VALUES (2, 'FPT 1', 2);
INSERT INTO vehicle VALUES (3, 'VLCG 1', 3);

-- Insert Position
INSERT INTO position VALUES (1, 48.116487, -1.647416);
INSERT INTO position VALUES (2, 48.105727, -1.674720);
INSERT INTO position VALUES (3, 48.1153379, -1.6391757);
INSERT INTO position VALUES (4, 48.1161849, -1.6390014);
INSERT INTO position VALUES (5, 48.1164571, -1.6373706);
INSERT INTO position VALUES (6, 48.1155689, -1.6360724);
INSERT INTO position VALUES (7, 48.1152322, -1.6378534);

-- Insert Path
INSERT INTO path VALUES (1, 30, 0);

-- Insert Path_Points
INSERT INTO path_points VALUES (1, 3);
INSERT INTO path_points VALUES (1, 4);
INSERT INTO path_points VALUES (1, 5);
INSERT INTO path_points VALUES (1, 6);
INSERT INTO path_points VALUES (1, 7);

-- Insert Intervention
INSERT INTO intervention VALUES (1, '11 Rue du Bois Perrin', 1522159274, 1, 1, 1, 1);
INSERT INTO intervention VALUES (2, 'Cours des Alliés, 35024 Rennes', 1522159274, 1, NULL, 2, 2);

-- Insert symbol
insert into symbol VALUE  (1, 0, 0);
insert into symbol VALUE  (2, 1, 0);
insert into symbol VALUE  (3, 2, 0);
insert into symbol VALUE  (4, 3, 0);
insert into symbol VALUE  (5, 4, 0);

insert into symbol VALUE  (6, 0, 1);
insert into symbol VALUE  (7, 1, 1);
insert into symbol VALUE  (8, 2, 1);
insert into symbol VALUE  (9, 3, 1);
insert into symbol VALUE  (10, 4, 1);

insert into symbol VALUE  (11, 0, 2);
insert into symbol VALUE  (12, 1, 2);
insert into symbol VALUE  (13, 2, 2);
insert into symbol VALUE  (14, 3, 2);
insert into symbol VALUE  (15, 4, 2);

insert into symbol VALUE  (16, 0, 3);
insert into symbol VALUE  (17, 1, 3);
insert into symbol VALUE  (18, 2, 3);
insert into symbol VALUE  (19, 3, 3);
insert into symbol VALUE  (20, 4, 3);

insert into symbol VALUE  (21, 0, 4);
insert into symbol VALUE  (22, 1, 4);
insert into symbol VALUE  (23, 2, 4);
insert into symbol VALUE  (24, 3, 4);
insert into symbol VALUE  (25, 4, 4);

insert into symbol VALUE  (26, 0, 5);
insert into symbol VALUE  (27, 1, 5);
insert into symbol VALUE  (28, 2, 5);
insert into symbol VALUE  (29, 3, 5);
insert into symbol VALUE  (30, 4, 5);

insert into symbol VALUE  (31, 0, 6);
insert into symbol VALUE  (32, 1, 6);
insert into symbol VALUE  (33, 2, 6);
insert into symbol VALUE  (34, 3, 6);
insert into symbol VALUE  (35, 4, 6);

insert into symbol VALUE  (36, 0, 7);
insert into symbol VALUE  (37, 1, 7);
insert into symbol VALUE  (38, 2, 7);
insert into symbol VALUE  (39, 3, 7);
insert into symbol VALUE  (40, 4, 7);

insert into symbol VALUE  (41, 0, 8);
insert into symbol VALUE  (42, 1, 8);
insert into symbol VALUE  (43, 2, 8);
insert into symbol VALUE  (44, 3, 8);
insert into symbol VALUE  (45, 4, 8);
-- Insert payload
Insert INTO payload VALUES (1, 'detail test 1', 'identifier test 1');
Insert INTO payload VALUES (2, 'detail test 2', 'identifier test 2');



-- insert Default Vehicle Symbol (TODO: It's stub for instance, we need to implement the true Decision Table mentioned in specs
insert into default_vehicle_symbol VALUE (1, 1, 1);
insert into default_vehicle_symbol VALUE (2, 2, 2);
insert into default_vehicle_symbol VALUE (3, 1, 3);
INSERT INTO payload VALUES (3, '', '');


-- Insert symbol_sitac (ID, Intervention, Location, Payload, Symbol
Insert INTO symbol_sitac VALUES (1, 1, 1, 1, 4);
Insert INTO symbol_sitac VALUES (2, 2, 2, 2, 5);
Insert INTO symbol_sitac VALUES (4, 2, 2, 3, 4);
-- Insert unit_vehicle

-- Insert unit (id, accept_date, commited_date, moving, released_date, request_date, intervention, symbol)
--Insert INTO unit VALUES ( 1, '00/12/25', '00/12/24', 1, '00/12/31', '00/12/23', 1, 1);
--Insert INTO unit VALUES ( 2, '00/12/25', '00/12/24', 1, '00/12/31', '00/12/23', 2, 2);
--Insert INTO unit VALUES ( 3, '00/12/25', '00/12/24', 1, '00/12/31', '00/12/23', 2, 4);

-- Insert unit_vehicle (ID, Status, Vehicle, Type, Unit)
--Insert INTO unit_vehicle VALUES (1, 1, 1, 1, 1);
--Insert INTO unit_vehicle VALUES (2, 2, 2, 2, 2);
--Insert INTO unit_vehicle VALUES (3, 0, null, 3, 3);