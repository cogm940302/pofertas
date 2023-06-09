insert into POfer01_PROPERTIES (ID_PROPERTIES, CD_VALOR) values
                                                             ('hde.data', '62714ECA644813E0BC6F0728E763D8AF'),
                                                             ('hde.key', 'axapci'),
                                                             ('hde.key.pass', 'axapcipass'),
                                                             ('hde.key.pass.pub', 'anypassword'),
                                                             ('hde.key.path.file', '/Users/migueldelaconcha/Documents/home/link2b/server/data/axapg/certs/client/axapci.jks'),
                                                             ('hde.key.path.file.pub', '/Users/migueldelaconcha/Documents/home/link2b/server/data/axapg/certs/client/HDE_Auth_pub.jks'),
                                                             ('hde.key.pub', 'HDE_Auth_pub'),
                                                             ('wpp.dir_email','/email'),
                                                             ('wpp.host','smtp.gmail.com'),
                                                             ('wpp.port','587'),
                                                             ('wpp.username','fernando.perez060198@gmail.com'),
                                                             ('wpp.password','ezmvblledhuparms'),
                                                             ('mail.username','fernando.perez060198@gmail.com');

INSERT INTO POfer03_CLIENT (CD_ID, CD_IDCLIENT, CD_BIN, CD_MCC, CD_SERVICE_INTELIPOS, TX_MERCHANT, TX_KEY, TX_CODE, TX_NAME, NU_TOTAL_AMOUNT, NU_NET_AMOUNT, ST_ACCEPTED, FH_CREATION, FH_UPDATE) VALUES
('8ca735ff-c768-45a3-8ff8-7357fb53e275', '8cc60a20-cbbb-4bfe-939a-b63125afb319', '557907', '3089', null, 1234567890, '5a6b55989667fc294bdd809a6f91489f40440722a373a9c743bc63a7a1592c28-772c537f-1db6-407d-9d7e-49b17b3a4660', '10147485347', 'Crédito preaprobado.', 51000, 50175.97, 0, TO_DATE('29/05/23', 'DD/MM/YY'), TO_DATE('29/05/23', 'DD/MM/YY')),
('c446a178-8ada-4cfc-97fa-288a6c84ca90', 'a2d57cbe-5cde-4ed1-8c31-b59d5269b5d5', '557907', '3089', null, 1234567890, '5a6b55989667fc294bdd809a6f91489f40440722a373a9c743bc63a7a1592c28-eaab4c39-a29a-466d-acd3-0da9d1176048', '10147485347', 'Crédito preaprobado.', 51000, 50175.97, 1, TO_DATE('29/05/23', 'DD/MM/YY'), TO_DATE('29/05/23', 'DD/MM/YY')),
('26245082-66ab-4f90-bd89-544eee7066dc', 'a2d57cbe-5cde-4ed1-8c31-b59d5269b5d5', '557907', '3089', null, 1234567890, '5a6b55989667fc294bdd809a6f91489f40440722a373a9c743bc63a7a1592c28-044c61f6-c424-406a-9d6c-f8f6a93291a2', '10147485347', 'Crédito preaprobado.', 51000, 50175.97, 0, TO_DATE('06/06/23', 'DD/MM/YY'), TO_DATE('06/06/23', 'DD/MM/YY'));
