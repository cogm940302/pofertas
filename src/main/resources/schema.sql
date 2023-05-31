drop table POfer01_PROPERTIES if exists;
drop table POfer02_CLIENT if exists;

create table POfer01_PROPERTIES
(
    id_properties VARCHAR(50) not null,
    cd_valor      VARCHAR(500)
);

alter table POfer01_PROPERTIES
    add constraint PKAPG001_IDPROPERTIES primary key (ID_PROPERTIES);

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
