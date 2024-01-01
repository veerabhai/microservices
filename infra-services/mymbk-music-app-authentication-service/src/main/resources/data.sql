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

--Table: USERS_ADMIN_APPROVAL
CREATE TABLE IF NOT EXISTS mbk_auth_schema.users_admin_approval
(
    id serial NOT NULL ,
    approved_date timestamp without time zone,
    role_id character varying(36) ,
    uid integer NOT NULL,
    username character varying(36) ,
    approval_status character varying(15) ,
    approved_by character varying(25) ,
    created_date timestamp without time zone,
    reason character varying(512) ,
     PRIMARY KEY (id)
);
-- Table: password_token
CREATE TABLE IF NOT EXISTS mbk_auth_schema.password_token
(
    id serial NOT NULL ,
    uid integer NOT NULL,
    password_token bigint NOT NULL,
    PRIMARY KEY (id)
);


-- Lookup Table: user_status
INSERT INTO mbk_auth_schema.user_status(code, value) select
'NEW', 'New' where not exists (select code from mbk_auth_schema.user_status where code='NEW');

INSERT INTO mbk_auth_schema.user_status(code, value) select
'PENDING', 'Pending' where not exists (select code from mbk_auth_schema.user_status where code='PENDING');

INSERT INTO mbk_auth_schema.user_status(code, value) select
'ACTIVE', 'Active' where not exists (select code from mbk_auth_schema.user_status where code='ACTIVE');

INSERT INTO mbk_auth_schema.user_status(code, value) select
'INACTIVE', 'Inactive' where not exists (select code from mbk_auth_schema.user_status where code='INACTIVE');

INSERT INTO mbk_auth_schema.user_status(code, value) select
'DELETED', 'Deleted' where not exists (select code from mbk_auth_schema.user_status where code='DELETED');

insert into mbk_auth_schema.user_asset_type (code, value)
SELECT 'PROFILE_PICTURE', 'Profile Picture'  where not exists(select code from mbk_auth_schema.user_asset_type where code = 'PROFILE_PICTURE');

insert into mbk_auth_schema.role (id,name) select
 '38a47da9-8077-4872-9d75-8bb43b041f9a','Contributor'
 where not exists (select name from mbk_auth_schema.role where name='Contributor');

 insert into mbk_auth_schema.role (id,name) select
 'b65d0f04-4795-4cff-b8f8-67a03608876f','Viewer'
 where not exists (select name from mbk_auth_schema.role where name='Viewer');
insert into mbk_auth_schema.role (id,name) select
 '91660574-9963-4944-a66c-9fdab6fbd7f4','Administrator'
 where not exists (select name from mbk_auth_schema.role where name='Administrator');

insert into mbk_auth_schema.user (id,username,password)
select 21,'mbkadmin@gmail.com','$2a$04$KcNcwrv/IOzjxYa0vEGiM.ZBeoxGP09jAsgmc/e3rjsixZjUNTyOG'
where not exists (select username from mbk_auth_schema.user where username='mbkadmin@gmail.com');

insert into mbk_auth_schema.user_role (user_id,role_id) select
(SELECT id FROM mbk_auth_schema.user WHERE username='mbkadmin@gmail.com'),
(SELECT id FROM mbk_auth_schema.role WHERE name='Administrator')
where not exists
(select user_id,role_id from mbk_auth_schema.user_role where user_id in(select id from mbk_auth_schema.user WHERE username='mbkadmin@gmail.com')
and role_id in(select id from mbk_auth_schema.role where name ='Administrator'));



