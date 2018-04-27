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
INSERT INTO vehicle VALUES (1, 'vehicule 1', 1);
INSERT INTO vehicle VALUES (2, 'vehicule 2', 2);
INSERT INTO vehicle VALUES (3, 'vehicule 3', 3);

-- Insert Position
INSERT INTO position VALUES (1, 48.116487, -1.647416);
INSERT INTO position VALUES (2, 48.105727, -1.674720);

-- Insert Intervention
INSERT INTO intervention VALUES (1, '11 Rue du Bois Perrin', 1522159274, 1, 1, 1);
INSERT INTO intervention VALUES (2, 'Cours des Alliés, 35024 Rennes', 1522159274, 1, 2, 2);




#   ID For Color:
#     0: RED,
#     1: GREEN,
#     2: BLUE,
#     3: ORANGE,
#     4: PURPLE
#
#   ID For Shape:
#   0: TRIANGLEUP,
#   1: TRIANGLEDOWN,
#   2: CIRCLE,
#   3: VEHICLE,
#   4: STAR,
#   5: ACTIONZONE


-- Insert symbol (ID, Color, Shape)
insert into symbol VALUE  (1, 0, 3)
insert into symbol VALUE  (2, 2, 3)
insert into symbol VALUE  (3, 4, 3)

-- insert Default Vehicle Symbol (TODO: It's stub for instance, we need to implement the true Decision Table mentioned in specs
insert into default_vehicle_symbol VALUE (1, 1, 1);
insert into default_vehicle_symbol VALUE (2, 2, 2);
insert into default_vehicle_symbol VALUE (3, 1, 3);