--------------------------------------------------------------------------------
--                             VISIT TYPES                                    --
--------------------------------------------------------------------------------

INSERT INTO visit_types(id,name,duration,price) VALUES (1, 'consultation', 30, 20.0);
INSERT INTO visit_types(id,name,duration,price) VALUES (2, 'revision', 30, 15.0);
INSERT INTO visit_types(id,name,duration,price) VALUES (3, 'operation', 60, 100.0);

--------------------------------------------------------------------------------
--                               PET TYPES                                    --
--------------------------------------------------------------------------------


INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');


--------------------------------------------------------------------------------
--                             VET SPECIALTIES                                --
--------------------------------------------------------------------------------

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');


--------------------------------------------------------------------------------
--                                  ADMINS                                    --
--------------------------------------------------------------------------------

-- admin1 / 4dm1n
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');


--------------------------------------------------------------------------------
--                                    VETS                                    --
--------------------------------------------------------------------------------

-- vet1 / v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities VALUES ('vet1','veterinarian');
INSERT INTO vets VALUES (1, 'James', 'Carter', 'vet1');

-- vet2 / vet2
INSERT INTO users(username,password,enabled) VALUES ('vet2','vet2',TRUE);
INSERT INTO authorities VALUES ('vet2','veterinarian');
INSERT INTO vets VALUES (2, 'Helen', 'Leary', 'vet2');
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (2, 1);

-- vet3 / vet3
INSERT INTO users(username,password,enabled) VALUES ('vet3','vet3',TRUE);
INSERT INTO authorities VALUES ('vet3','veterinarian');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas', 'vet3');
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (3, 2);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (3, 3);

-- vet4 / vet4
INSERT INTO users(username,password,enabled) VALUES ('vet4','vet4',TRUE);
INSERT INTO authorities VALUES ('vet4','veterinarian');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega', 'vet4');
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (4, 2);

-- vet5 / vet5
INSERT INTO users(username,password,enabled) VALUES ('vet5','vet5',TRUE);
INSERT INTO authorities VALUES ('vet5','veterinarian');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens', 'vet5');
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (5, 1);

-- vet6 / vet6
INSERT INTO users(username,password,enabled) VALUES ('vet6','vet6',TRUE);
INSERT INTO authorities VALUES ('vet6','veterinarian');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins', 'vet6');


--------------------------------------------------------------------------------
--                       OWNERS and their PETS                                --
--------------------------------------------------------------------------------

-- gfranklin/gfranklin
INSERT INTO users(username,password,enabled) VALUES ('gfranklin','gfranklin', TRUE);
INSERT INTO authorities VALUES ('gfranklin','owner');
INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'gfranklin');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);

-- bdavis/bdavis
INSERT INTO users(username,password,enabled) VALUES ('bdavis','bdavis',TRUE);
INSERT INTO authorities VALUES ('bdavis','owner');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'bdavis');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);

-- erodriguez/erodriguez
INSERT INTO users(username,password,enabled) VALUES ('erodriguez','erodriguez', TRUE);
INSERT INTO authorities VALUES ('erodriguez','owner');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'erodriguez');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);

-- hdavis/hdavis
INSERT INTO users(username,password,enabled) VALUES ('hdavis','hdavis',TRUE);
INSERT INTO authorities VALUES ('hdavis','owner');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'hdavis');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);

-- pmctavish/pmctavish
INSERT INTO users(username,password,enabled) VALUES ('pmctavish','pmctavish',
	TRUE);
INSERT INTO authorities VALUES ('pmctavish','owner');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way',
	'Madison', '6085552765', 'pmctavish');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George',
	'2010-01-20', 4, 5);

-- jcoleman/jcoleman
INSERT INTO users(username,password,enabled) VALUES ('jcoleman','jcoleman', TRUE);
INSERT INTO authorities VALUES ('jcoleman','owner');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'jcoleman');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);

-- jblack/jblack
INSERT INTO users(username,password,enabled) VALUES ('jblack','jblack',TRUE);
INSERT INTO authorities VALUES ('jblack','owner');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'jblack');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);

-- mescobito/mescobito
INSERT INTO users(username,password,enabled) VALUES ('mescobito','mescobito', TRUE);
INSERT INTO authorities VALUES ('mescobito','owner');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'mescobito');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);

-- dschroeder/dschroeder
INSERT INTO users(username,password,enabled) VALUES ('dschroeder','dschroeder', TRUE);
INSERT INTO authorities VALUES ('dschroeder','owner');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'dschroeder');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);

-- cestaban/cestaban
INSERT INTO users(username,password,enabled) VALUES ('cestaban','cestaban', TRUE);
INSERT INTO authorities VALUES ('cestaban','owner');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'cestaban');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);



--------------------------------------------------------------------------------
--                                SECRETARIES                                 --
--------------------------------------------------------------------------------

-- secretary1 / s3cr3tary
INSERT INTO users(username,password,enabled) VALUES ('secretary1','s3cr3tary',TRUE);
INSERT INTO authorities VALUES ('secretary1','secretary');
INSERT INTO secretaries VALUES (1, 'Maria', 'Fernandez', 'secretary1');

-- secretary2 / secretary2
INSERT INTO users(username,password,enabled) VALUES ('secretary2','secretary2',TRUE);
INSERT INTO authorities VALUES ('secretary2','secretary');
INSERT INTO secretaries VALUES (2, 'Lucas', 'Rodriguez', 'secretary2');



--------------------------------------------------------------------------------
--                            CREDITCARD                                      --
--------------------------------------------------------------------------------

INSERT INTO creditcards(id,holder,brand,number,exp_month,exp_year,security_code) VALUES 
	(1, 'Claudia Guerrero', 'visa', '1111 2222 3333 4444', 05, 21, 082);
	
INSERT INTO creditcards(id,holder,brand,number,exp_month,exp_year,security_code) VALUES 
	(2, 'Miguel Macarro', 'visa', '1144 5522 3366 8899', 10, 22, 144);
	
INSERT INTO creditcards(id,holder,brand,number,exp_month,exp_year,security_code) VALUES 
	(3, 'Josema Gonzalez', 'visa', '9999 8888 7777 4444', 01, 25, 455);



--------------------------------------------------------------------------------
--                            PAYMENTS                                        --
--------------------------------------------------------------------------------

INSERT INTO payments(id,method,moment,final_price,secretary_id,creditcard_id) VALUES 
	(1, 'creditcard', '2019-06-05 10:20', 30, 1, 1);

INSERT INTO payments(id,method,moment,final_price,secretary_id,creditcard_id) VALUES 
	(2, 'creditcard', '2017-02-01 15:40', 50, 2, 2);
	
INSERT INTO payments(id,method,moment,final_price,secretary_id,creditcard_id) VALUES 
	(3, 'creditcard', '2019-01-06 11:30', 80, 1, 3);
	
INSERT INTO payments(id,method,moment,final_price,secretary_id,creditcard_id) VALUES 
	(4, 'cash', '2019-10-20 19:20', 20, 1, null);
	
INSERT INTO payments(id,method,moment,final_price,secretary_id,creditcard_id) VALUES 
	(5, 'cash', '2018-09-09 16:30', 50, 2, null);
	
--------------------------------------------------------------------------------
--                                MEDICINES                                   --
--------------------------------------------------------------------------------
INSERT INTO medicines(id,brand,name) VALUES (1, 'Bayer', 'Betadine');
INSERT INTO medicines(id,brand,name) VALUES (2, 'Pfizer', 'Medicine X');
INSERT INTO medicines(id,brand,name) VALUES (3, 'Roche', 'Medicine Y');
INSERT INTO medicines(id,brand,name) VALUES (4, 'Sanofi', 'Medicine Z');	

--------------------------------------------------------------------------------
--                                PRESCRIPTIONS                               --
--------------------------------------------------------------------------------
INSERT INTO prescriptions(id,medicine_id,frequency,duration) VALUES (1, 1, '2 times per day', '1 week');
INSERT INTO prescriptions(id,medicine_id,frequency,duration) VALUES (2, 2, '2 times per day', '2 weeks');
INSERT INTO prescriptions(id,medicine_id,frequency,duration) VALUES (3, 3, '2 times per day', '3 weeks');
INSERT INTO prescriptions(id,medicine_id,frequency,duration) VALUES (4, 4, '2 times per day', '1 month');

--------------------------------------------------------------------------------
--                                DIAGNOSTICS                                 --
--------------------------------------------------------------------------------
INSERT INTO diagnostics(id, date,description) VALUES (1, '2013-01-01', 'rabies shot');
INSERT INTO diagnostics(id, date,description) VALUES (2, '2013-01-02', 'rabies shot');
INSERT INTO diagnostics(id, date,description) VALUES (3, '2013-01-03', 'neutered');
INSERT INTO diagnostics(id, date,description) VALUES (4, '2013-01-04', 'spayed');

--------------------------------------------------------------------------------
--                          DIAGNOSIS-PRESCRIPTION                            --
--------------------------------------------------------------------------------
INSERT INTO diagnosis_prescriptions VALUES (1, 1);
INSERT INTO diagnosis_prescriptions VALUES (1, 2);
INSERT INTO diagnosis_prescriptions VALUES (3, 3);
INSERT INTO diagnosis_prescriptions VALUES (4, 4);
	

--------------------------------------------------------------------------------
--                                VISITS                                      --
--------------------------------------------------------------------------------

INSERT INTO visits(id,pet_id,vet_id,visit_type_id,visit_moment,description,payment_id, diagnosis_id) VALUES (1, 7, 2, 1, '2013-01-01 10:00', 'rabies shot', 1, 1);
INSERT INTO visits(id,pet_id,vet_id,visit_type_id,visit_moment,description,payment_id, diagnosis_id) VALUES (2, 8, 2, 1, '2013-01-02 10:00', 'rabies shot', 2, 2);
INSERT INTO visits(id,pet_id,vet_id,visit_type_id,visit_moment,description,payment_id, diagnosis_id) VALUES (3, 8, 2, 2, '2013-01-03 10:00', 'neutered', 3, null);
INSERT INTO visits(id,pet_id,vet_id,visit_type_id,visit_moment,description,payment_id, diagnosis_id) VALUES (4, 7, 1, 3, '2013-01-04 10:00', 'spayed', 4, null);
INSERT INTO visits(id,pet_id,vet_id,visit_type_id,visit_moment,description,payment_id, diagnosis_id) VALUES (5, 7, 1, 3, '2019-03-10 15:30', 'Descrip with', 5, null);
INSERT INTO visits(id,pet_id,vet_id,visit_type_id,visit_moment,description,payment_id, diagnosis_id) VALUES (6, 8, 1, 2, '2015-05-06 14:00', 'Descrip without', null, null);
INSERT INTO visits(id,pet_id,vet_id,visit_type_id,visit_moment,description,payment_id, diagnosis_id) VALUES (7, 7, 2, 1, '2016-07-15 10:30', 'Rubies whitout', null, null);
INSERT INTO visits(id,pet_id,vet_id,visit_type_id,visit_moment,description,payment_id, diagnosis_id) VALUES (8, 1, 1, 1, '2020-04-01 10:30', 'Sample visit', null, null);
INSERT INTO visits(id,pet_id,vet_id,visit_type_id,visit_moment,description,payment_id, diagnosis_id) VALUES (9, 1, 1, 1, '2025-04-01 10:30', 'Sample visit', null, null);

