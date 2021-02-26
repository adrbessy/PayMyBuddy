/* Setting up PROD DB */
create database prod;
\c prod;


CREATE TABLE public.User_account (
                email_address VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                password VARCHAR(100) DEFAULT 'password' NOT NULL,
                first_name VARCHAR(100) DEFAULT 'first name' NOT NULL,
                name VARCHAR(100) DEFAULT 'name' NOT NULL,
                amount NUMERIC(10,2) DEFAULT 0 NOT NULL,
                CONSTRAINT user_account_pk PRIMARY KEY (email_address)
);


CREATE SEQUENCE public.transaction_id_seq;

CREATE TABLE public.Transaction (
                id INTEGER NOT NULL DEFAULT nextval('public.transaction_id_seq'),
                email_address_emitter VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                description VARCHAR DEFAULT 'This is a description',
                amount NUMERIC(10,2) DEFAULT 5.00 NOT NULL,
                CONSTRAINT transaction_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.transaction_id_seq OWNED BY public.Transaction.id;

CREATE TABLE public.Friend_transaction (
                id INTEGER NOT NULL,
                email_address VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                CONSTRAINT friend_transaction_pk PRIMARY KEY (id)
);


CREATE TABLE public.Friend (
                email_address_user1 VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                email_address_user2 VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                CONSTRAINT friend_pk PRIMARY KEY (email_address_user1, email_address_user2)
);

create unique index on public.Friend (least(email_address_user1, email_address_user2), greatest(email_address_user1, email_address_user2));


CREATE TABLE public.Bank_account (
                iban VARCHAR(34) NOT NULL,
                email_address VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                CONSTRAINT bank_account_pk PRIMARY KEY (iban)
);


CREATE TABLE public.Bank_transaction (
                id INTEGER NOT NULL,
                iban VARCHAR(34) NOT NULL,
                CONSTRAINT bank_transaction_pk PRIMARY KEY (id)
);


ALTER TABLE public.Bank_account ADD CONSTRAINT user_account_bank_account_fk
FOREIGN KEY (email_address)
REFERENCES public.User_account (email_address)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Friend ADD CONSTRAINT user_account_friend_fk1
FOREIGN KEY (email_address_user1)
REFERENCES public.User_account (email_address)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Transaction ADD CONSTRAINT user_account_transaction_fk
FOREIGN KEY (email_address_emitter)
REFERENCES public.User_account (email_address)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Friend_transaction ADD CONSTRAINT user_account_friend_transaction_fk
FOREIGN KEY (email_address)
REFERENCES public.User_account (email_address)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Friend ADD CONSTRAINT user_account_friend_fk2
FOREIGN KEY (email_address_user2)
REFERENCES public.User_account (email_address)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Friend_transaction ADD CONSTRAINT transaction_friend_transaction_fk
FOREIGN KEY (id)
REFERENCES public.Transaction (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Bank_transaction ADD CONSTRAINT transaction_bank_transaction_fk
FOREIGN KEY (id)
REFERENCES public.Transaction (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Bank_transaction ADD CONSTRAINT bank_account_bank_transaction_fk
FOREIGN KEY (iban)
REFERENCES public.Bank_account (iban)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;


INSERT INTO public.user_account 
(email_address, password, first_name, name) 
VALUES 
('adrien@mail.fr','test','Adrien','Bessy'),
('isabelle@mail.fr','test','Isabelle','Bessy'),
('marie@mail.fr','test','Marie','Regnier'),
('Jacky@mail.fr','test','Jacky','Bernardin')
;

INSERT INTO public.friend 
(email_address_user1, email_address_user2) 
VALUES 
('adrien@mail.fr','isabelle@mail.fr'),
('adrien@mail.fr','marie@mail.fr'),
('Jacky@mail.fr','adrien@mail.fr')
;

INSERT INTO public.transaction 
(email_address_emitter, description, amount) 
VALUES 
('adrien@mail.fr','pour le loyer',300)
;

INSERT INTO public.friend_transaction
(id, email_address) 
VALUES 
(1, 'isabelle@mail.fr')
;

INSERT INTO public.bank_account
(iban, email_address) 
VALUES 
('NL06ABNA1869773616', 'adrien@mail.fr')
;



/* Setting up TEST DB */
create database test;
\c test;


CREATE TABLE public.User_account (
                email_address VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                password VARCHAR(100) DEFAULT 'password' NOT NULL,
                first_name VARCHAR(100) DEFAULT 'first name' NOT NULL,
                name VARCHAR(100) DEFAULT 'name' NOT NULL,
                amount NUMERIC(10,2) DEFAULT 0 NOT NULL,
                CONSTRAINT user_account_pk PRIMARY KEY (email_address)
);


CREATE SEQUENCE public.transaction_id_seq;

CREATE TABLE public.Transaction (
                id INTEGER NOT NULL DEFAULT nextval('public.transaction_id_seq'),
                email_address_emitter VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                description VARCHAR DEFAULT 'This is a description',
                amount NUMERIC(10,2) DEFAULT 5.00 NOT NULL,
                CONSTRAINT transaction_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.transaction_id_seq OWNED BY public.Transaction.id;

CREATE TABLE public.Friend_transaction (
                id INTEGER NOT NULL,
                email_address VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                CONSTRAINT friend_transaction_pk PRIMARY KEY (id)
);


CREATE TABLE public.Friend (
                email_address_user1 VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                email_address_user2 VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                CONSTRAINT friend_pk PRIMARY KEY (email_address_user1, email_address_user2)
);

create unique index on public.Friend (least(email_address_user1, email_address_user2), greatest(email_address_user1, email_address_user2));


CREATE TABLE public.Bank_account (
                iban VARCHAR(34) NOT NULL,
                email_address VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                CONSTRAINT bank_account_pk PRIMARY KEY (iban)
);


CREATE TABLE public.Bank_transaction (
                id INTEGER NOT NULL,
                iban VARCHAR(34) NOT NULL,
                CONSTRAINT bank_transaction_pk PRIMARY KEY (id)
);


ALTER TABLE public.Bank_account ADD CONSTRAINT user_account_bank_account_fk
FOREIGN KEY (email_address)
REFERENCES public.User_account (email_address)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Friend ADD CONSTRAINT user_account_friend_fk1
FOREIGN KEY (email_address_user1)
REFERENCES public.User_account (email_address)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Transaction ADD CONSTRAINT user_account_transaction_fk
FOREIGN KEY (email_address_emitter)
REFERENCES public.User_account (email_address)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Friend_transaction ADD CONSTRAINT user_account_friend_transaction_fk
FOREIGN KEY (email_address)
REFERENCES public.User_account (email_address)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Friend ADD CONSTRAINT user_account_friend_fk2
FOREIGN KEY (email_address_user2)
REFERENCES public.User_account (email_address)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Friend_transaction ADD CONSTRAINT transaction_friend_transaction_fk
FOREIGN KEY (id)
REFERENCES public.Transaction (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Bank_transaction ADD CONSTRAINT transaction_bank_transaction_fk
FOREIGN KEY (id)
REFERENCES public.Transaction (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Bank_transaction ADD CONSTRAINT bank_account_bank_transaction_fk
FOREIGN KEY (iban)
REFERENCES public.Bank_account (iban)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;


INSERT INTO public.user_account 
(email_address, password, first_name, name) 
VALUES 
('adrien@mail.fr','test','Adrien','Bessy'),
('isabelle@mail.fr','test','Isabelle','Bessy'),
('marie@mail.fr','test','Marie','Regnier'),
('Jacky@mail.fr','test','Jacky','Bernardin')
;

INSERT INTO public.friend 
(email_address_user1, email_address_user2) 
VALUES 
('adrien@mail.fr','isabelle@mail.fr'),
('adrien@mail.fr','marie@mail.fr'),
('Jacky@mail.fr','adrien@mail.fr')
;

INSERT INTO public.transaction 
(email_address_emitter, description, amount) 
VALUES 
('adrien@mail.fr','pour le loyer',300)
;

INSERT INTO public.friend_transaction
(id, email_address) 
VALUES 
(1, 'isabelle@mail.fr')
;

INSERT INTO public.bank_account
(iban, email_address) 
VALUES 
(1000, 'adrien@mail.fr')
;