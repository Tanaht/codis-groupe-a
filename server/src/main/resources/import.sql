REPLACE INTO role values(1,'ROLE_CODIS_USER');
REPLACE INTO role values(2,'ROLE_SIMPLE_USER');
REPLACE INTO role values(3,'ROLE_DRONE_USER');


-- userlogin = password
REPLACE INTO user values(1,'codis_user','codis_user',1);
REPLACE INTO user values(2,'simple_user','simple_user',2);
REPLACE INTO user values(3,'drone_user','drone_user',3);
