INSERT INTO role values(1,'ROLE_CODIS_USER');
INSERT INTO role values(2,'ROLE_SIMPLE_USER');
INSERT INTO role values(3,'ROLE_DRONE_USER');


-- userlogin = password
INSERT INTO user values(1,'codis_user','codis_user',1);
INSERT INTO user values(2,'simple_user','simple_user',2);
INSERT INTO user values(3,'drone_user','drone_user',3);

-- Insert Sinister code
INSERT INTO sinister_code VALUES (1,'INC');
INSERT INTO sinister_code VALUES (2,'SAP');
