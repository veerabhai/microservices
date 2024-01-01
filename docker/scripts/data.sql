
    -- Lookup Table: user_asset_type
    CREATE TABLE IF NOT EXISTS  mbk_auth_schema.user_asset_type
    (
    code VARCHAR(32) NOT NULL PRIMARY KEY,
    value VARCHAR(64) NOT NULL
    );

    -- Table: user_asset
    CREATE TABLE IF NOT EXISTS mbk_auth_schema.user_asset
    (
    id         VARCHAR(36)  NOT NULL PRIMARY KEY,
    name       VARCHAR(64) NOT NULL UNIQUE,
    path       VARCHAR(512) NOT NULL,
    type_code  VARCHAR(32) NOT NULL REFERENCES mbk_auth_schema.user_asset_type(code)
    );

    -- Table: user
    CREATE TABLE IF NOT EXISTS mbk_auth_schema.user
    (
    id serial   NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(32) NOT NULL,
    asset_id character varying(255),
    PRIMARY KEY (id)
    );

    -- Adding the constraints in user table.
    ALTER TABLE IF EXISTS mbk_auth_schema.user DROP CONSTRAINT IF EXISTS fk_user_asset;

    ALTER TABLE IF EXISTS mbk_auth_schema.user
    ADD CONSTRAINT fk_user_asset FOREIGN KEY (asset_id)
    REFERENCES mbk_auth_schema.user_asset (id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

    -- Table: role
    CREATE TABLE IF NOT EXISTS mbk_auth_schema.role
    (
    id character varying(36) NOT NULL,
    name character varying(64)  NOT NULL,
    PRIMARY KEY (id)
    );

    -- Table: user_role
    CREATE TABLE IF NOT EXISTS mbk_auth_schema.user_role
    (
    user_id serial  NOT NULL,
    role_id character varying(36) NOT NULL,
    FOREIGN KEY (user_id)
    REFERENCES mbk_auth_schema.user (id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    FOREIGN KEY (role_id)
    REFERENCES mbk_auth_schema.role (id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    PRIMARY KEY(user_id, role_id)
    );

    -- Table: user_status
    CREATE TABLE  IF NOT EXISTS mbk_auth_schema.user_status(
    code		 VARCHAR(32)  PRIMARY KEY,
    value		 VARCHAR(64)  NOT NULL
    );
CREATE TABLE IF NOT EXISTS mbk_auth_schema.reset_password_request
(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    user_id serial  REFERENCES mbk_auth_schema.user(id),
    token VARCHAR(512) NOT NULL,
    token_expiration_time BIGINT NOT NULL
);
-- Lookup Table: user_status
INSERT INTO mbk_auth_schema.user_status(code, value)
VALUES ('NEW', 'New'),
       ('PENDING', 'Pending'),
       ('ACTIVE', 'Active'),
       ('INACTIVE', 'Inactive'),
       ('DELETED', 'Deleted');

insert into mbk_auth_schema.user_asset_type (code, value)
SELECT 'PROFILE_PICTURE', 'Profile Picture' where not exists(select code from mbk_auth_schema.user_asset_type where code = 'PROFILE_PICTURE');

insert into mbk_auth_schema.role (id,name) select
 '38a47da9-8077-4872-9d75-8bb43b041f9a','editor'
 where not exists (select name from mbk_auth_schema.role where name='editor');
insert into mbk_auth_schema.role (id,name) select
 '91660574-9963-4944-a66c-9fdab6fbd7f4','Administrator'
 where not exists (select name from mbk_auth_schema.role where name='Administrator');

insert into mbk_auth_schema.user (id,username,password)
select 21,'admin','$2a$04$UDQWU8myYBaj.FQunbWdLObd8/wBgRRc0dLng3NndYYiEUPqND0LS'
where not exists (select username from mbk_auth_schema.user where username='admin');

insert into mbk_auth_schema.user_role (user_id,role_id) select
(SELECT id FROM mbk_auth_schema.user WHERE username='admin'),
(SELECT id FROM mbk_auth_schema.role WHERE name='Administrator')
where not exists
(select user_id,role_id from mbk_auth_schema.user_role where user_id in(select id from mbk_auth_schema.user WHERE username='admin')
and role_id in(select id from mbk_auth_schema.role where name ='Administrator'));



