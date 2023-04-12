drop table POfer01_PROPERTIES if exists;
drop table POfer02_CLIENT if exists;

--------------------------------------------------------
--  DDL for Table POfer01_PROPERTIES
--------------------------------------------------------
create table POfer01_PROPERTIES
(
  id_properties VARCHAR(50) not null,
  cd_valor      VARCHAR(500)
);

alter table POfer01_PROPERTIES
  add constraint PKAPG001_IDPROPERTIES primary key (ID_PROPERTIES);
  
insert into POfer01_PROPERTIES (ID_PROPERTIES, CD_VALOR) values ('hde.data',	'62714ECA644813E0BC6F0728E763D8AF');
insert into POfer01_PROPERTIES (ID_PROPERTIES, CD_VALOR) values ('hde.key',	'axapci');
insert into POfer01_PROPERTIES (ID_PROPERTIES, CD_VALOR) values ('hde.key.pass',	'axapcipass');
insert into POfer01_PROPERTIES (ID_PROPERTIES, CD_VALOR) values ('hde.key.pass.pub',	'anypassword');
insert into POfer01_PROPERTIES (ID_PROPERTIES, CD_VALOR) values ('hde.key.path.file',	'/Users/migueldelaconcha/Documents/home/link2b/server/data/axapg/certs/client/axapci.jks');
insert into POfer01_PROPERTIES (ID_PROPERTIES, CD_VALOR) values ('hde.key.path.file.pub',	'/Users/migueldelaconcha/Documents/home/link2b/server/data/axapg/certs/client/HDE_Auth_pub.jks');
insert into POfer01_PROPERTIES (ID_PROPERTIES, CD_VALOR) values ('hde.key.pub',	'HDE_Auth_pub');
  
 --------------------------------------------------------
--  DDL for Table POfer02_CLIENT
--------------------------------------------------------

create table POfer02_CLIENT
(
  cd_id         VARCHAR(50) not null,
  cd_idDevice      VARCHAR(100) not null,
  tx_version       VARCHAR(50),
  tx_model   VARCHAR(30),
  cd_branch    VARCHAR(30),
  cd_company   VARCHAR(30),
  tx_username       VARCHAR(30),
  tx_afiliacion		VARCHAR(15),
  st_terms      INTEGER,
  fh_creation DATE,
  fh_update DATE
);
alter table POfer02_CLIENT
  add constraint PK_APG002 primary key (cd_id);