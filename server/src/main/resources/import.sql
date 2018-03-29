INSERT INTO role values(1,'ROLE_CODIS_USER');
INSERT INTO role values(2,'ROLE_SIMPLE_USER');
INSERT INTO role values(3,'ROLE_DRONE_USER');


-- userlogin = password
INSERT INTO user values(1,'codis_user','codis_user',1);
INSERT INTO user values(2,'simple_user','simple_user',2);
INSERT INTO user values(3,'drone_user','drone_user',3);
INSERT INTO user values(4,'user','user',1);
INSERT INTO user values(5,'codis_user1','codis_user1',1);
INSERT INTO user values(6,'codis_user2','codis_user2',1);
INSERT INTO user values(7,'codis_user3','codis_user3',1);
INSERT INTO user values(8,'simple_user1','simple_user1',2);
INSERT INTO user values(9,'simple_user2','simple_user2',2);
INSERT INTO user values(10,'simple_user3','simple_user3',2);

-- Insert Sinister code
INSERT INTO sinister_code VALUES (1,'INC');
INSERT INTO sinister_code VALUES (2,'SAP');

--Insert Vehicule_Type
INSERT INTO vehicle_type VALUES (1, 'VSAV');
INSERT INTO vehicle_type VALUES (2, 'FPT');
INSERT INTO vehicle_type VALUES (3, 'VLCG');

--Insert Vehicle
INSERT INTO vehicle VALUES (1, 'vehicule 1', 2, 1);
INSERT INTO vehicle VALUES (2, 'vehicule 2', 2, 1);

INSERT INTO vehicle VALUES (3, 'vehicule 3', 2, 2);
INSERT INTO vehicle VALUES (4, 'vehicule 4', 2, 2);

INSERT INTO vehicle VALUES (5, 'vehicule 5', 2, 3);
INSERT INTO vehicle VALUES (6, 'vehicule 6', 2, 3);

-- Insert Position
INSERT INTO position VALUES (1, 48.116487, -1.647416);
INSERT INTO position VALUES (2, 48.105727, -1.674720);

-- Insert Intervention
INSERT INTO intervention VALUES (1, '11 Rue du Bois Perrin', 1522159274, 1, 1, 1);
INSERT INTO intervention VALUES (2, 'Cours des Alliés, 35024 Rennes', 1522159274, 1, 2, 2);

