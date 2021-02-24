/* Setting up PROD DB */
create database prod;
\c prod;


CREATE TABLE public.UserAccount (
                emailAddress VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                password VARCHAR(100) DEFAULT 'password' NOT NULL,
                firstName VARCHAR(100) DEFAULT 'first name' NOT NULL,
                name VARCHAR(100) DEFAULT 'name' NOT NULL,
                amount NUMERIC(10,2) DEFAULT 0 NOT NULL,
                CONSTRAINT useraccount_pk PRIMARY KEY (emailAddress)
);


CREATE SEQUENCE public.transaction_id_seq;

CREATE TABLE public.Transaction (
                id INTEGER NOT NULL DEFAULT nextval('public.transaction_id_seq'),
                emailAddress_emitter VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                description VARCHAR DEFAULT 'This is a description',
                amount NUMERIC(10,2) DEFAULT 5.00 NOT NULL,
                CONSTRAINT transaction_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.transaction_id_seq OWNED BY public.Transaction.id;

CREATE TABLE public.FriendTransaction (
                id INTEGER NOT NULL,
                emailAddress VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                CONSTRAINT friendtransaction_pk PRIMARY KEY (id)
);


CREATE TABLE public.Friend (
                emailAddress_user1 VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                emailAddress_user2 VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                CONSTRAINT friend_pk PRIMARY KEY (emailAddress_user1, emailAddress_user2)
);

create unique index on public.Friend (least(emailAddress_user1, emailAddress_user2), greatest(emailAddress_user1, emailAddress_user2));


CREATE TABLE public.BankAccount (
                iban VARCHAR(34) NOT NULL,
                emailAddress VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                CONSTRAINT bankaccount_pk PRIMARY KEY (iban)
);


CREATE TABLE public.BankTransaction (
                id INTEGER NOT NULL,
                iban VARCHAR(34) NOT NULL,
                CONSTRAINT banktransaction_pk PRIMARY KEY (id)
);


ALTER TABLE public.BankAccount ADD CONSTRAINT useraccount_bankaccount_fk
FOREIGN KEY (emailAddress)
REFERENCES public.UserAccount (emailAddress)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Friend ADD CONSTRAINT useraccount_friend_fk1
FOREIGN KEY (emailAddress_user1)
REFERENCES public.UserAccount (emailAddress)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Transaction ADD CONSTRAINT useraccount_transaction_fk
FOREIGN KEY (emailAddress_emitter)
REFERENCES public.UserAccount (emailAddress)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.FriendTransaction ADD CONSTRAINT useraccount_friendtransaction_fk
FOREIGN KEY (emailAddress)
REFERENCES public.UserAccount (emailAddress)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Friend ADD CONSTRAINT useraccount_friend_fk2
FOREIGN KEY (emailAddress_user2)
REFERENCES public.UserAccount (emailAddress)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.FriendTransaction ADD CONSTRAINT transaction_friendtransaction_fk
FOREIGN KEY (id)
REFERENCES public.Transaction (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.BankTransaction ADD CONSTRAINT transaction_banktransaction_fk
FOREIGN KEY (id)
REFERENCES public.Transaction (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.BankTransaction ADD CONSTRAINT bankaccount_banktransaction_fk
FOREIGN KEY (iban)
REFERENCES public.BankAccount (iban)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;


INSERT INTO public.userAccount 
(emailAddress, password, firstName, name) 
VALUES 
('adrien@mail.fr','test','Adrien','Bessy'),
('isabelle@mail.fr','test','Isabelle','Bessy'),
('marie@mail.fr','test','Marie','Regnier'),
('Jacky@mail.fr','test','Jacky','Bernardin')
;

INSERT INTO public.friend 
(emailAddress_user1, emailAddress_user2) 
VALUES 
('adrien@mail.fr','isabelle@mail.fr'),
('adrien@mail.fr','marie@mail.fr'),
('Jacky@mail.fr','adrien@mail.fr')
;

INSERT INTO public.transaction 
(emailAddress_emitter, description, amount) 
VALUES 
('adrien@mail.fr','pour le loyer',300)
;

INSERT INTO public.friendTransaction
(id, emailAddress) 
VALUES 
(1, 'isabelle@mail.fr')
;

INSERT INTO public.bankAccount
(iban, emailAddress) 
VALUES 
(1000, 'adrien@mail.fr')
;



/* Setting up TEST DB */
create database test;
\c test;


CREATE TABLE public.UserAccount (
                emailAddress VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                password VARCHAR(100) DEFAULT 'password' NOT NULL,
                firstName VARCHAR(100) DEFAULT 'first name' NOT NULL,
                name VARCHAR(100) DEFAULT 'name' NOT NULL,
                amount NUMERIC(10,2) DEFAULT 0 NOT NULL,
                CONSTRAINT useraccount_pk PRIMARY KEY (emailAddress)
);


CREATE SEQUENCE public.transaction_id_seq;

CREATE TABLE public.Transaction (
                id INTEGER NOT NULL DEFAULT nextval('public.transaction_id_seq'),
                emailAddress_emitter VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                description VARCHAR DEFAULT 'This is a description',
                amount NUMERIC(10,2) DEFAULT 5.00 NOT NULL,
                CONSTRAINT transaction_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.transaction_id_seq OWNED BY public.Transaction.id;

CREATE TABLE public.FriendTransaction (
                id INTEGER NOT NULL,
                emailAddress VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                CONSTRAINT friendtransaction_pk PRIMARY KEY (id)
);


CREATE TABLE public.Friend (
                emailAddress_user1 VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                emailAddress_user2 VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                CONSTRAINT friend_pk PRIMARY KEY (emailAddress_user1, emailAddress_user2)
);

create unique index on public.Friend (least(emailAddress_user1, emailAddress_user2), greatest(emailAddress_user1, emailAddress_user2));


CREATE TABLE public.BankAccount (
                iban VARCHAR(34) NOT NULL,
                emailAddress VARCHAR(100) DEFAULT 'test@mail.fr' NOT NULL,
                CONSTRAINT bankaccount_pk PRIMARY KEY (iban)
);


CREATE TABLE public.BankTransaction (
                id INTEGER NOT NULL,
                iban VARCHAR(34) NOT NULL,
                CONSTRAINT banktransaction_pk PRIMARY KEY (id)
);


ALTER TABLE public.BankAccount ADD CONSTRAINT useraccount_bankaccount_fk
FOREIGN KEY (emailAddress)
REFERENCES public.UserAccount (emailAddress)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Friend ADD CONSTRAINT useraccount_friend_fk1
FOREIGN KEY (emailAddress_user1)
REFERENCES public.UserAccount (emailAddress)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Transaction ADD CONSTRAINT useraccount_transaction_fk
FOREIGN KEY (emailAddress_emitter)
REFERENCES public.UserAccount (emailAddress)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.FriendTransaction ADD CONSTRAINT useraccount_friendtransaction_fk
FOREIGN KEY (emailAddress)
REFERENCES public.UserAccount (emailAddress)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Friend ADD CONSTRAINT useraccount_friend_fk2
FOREIGN KEY (emailAddress_user2)
REFERENCES public.UserAccount (emailAddress)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.FriendTransaction ADD CONSTRAINT transaction_friendtransaction_fk
FOREIGN KEY (id)
REFERENCES public.Transaction (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.BankTransaction ADD CONSTRAINT transaction_banktransaction_fk
FOREIGN KEY (id)
REFERENCES public.Transaction (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.BankTransaction ADD CONSTRAINT bankaccount_banktransaction_fk
FOREIGN KEY (iban)
REFERENCES public.BankAccount (iban)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;


INSERT INTO public.userAccount 
(emailAddress, password, firstName, name) 
VALUES 
('adrien@mail.fr','test','Adrien','Bessy'),
('isabelle@mail.fr','test','Isabelle','Bessy'),
('marie@mail.fr','test','Marie','Regnier'),
('Jacky@mail.fr','test','Jacky','Bernardin')
;

INSERT INTO public.friend 
(emailAddress_user1, emailAddress_user2) 
VALUES 
('adrien@mail.fr','isabelle@mail.fr'),
('adrien@mail.fr','marie@mail.fr'),
('Jacky@mail.fr','adrien@mail.fr')
;

INSERT INTO public.transaction 
(emailAddress_emitter, description, amount) 
VALUES 
('adrien@mail.fr','pour le loyer',300)
;

INSERT INTO public.friendTransaction
(id, emailAddress) 
VALUES 
(1, 'isabelle@mail.fr')
;

INSERT INTO public.bankAccount
(iban, emailAddress) 
VALUES 
(1000, 'adrien@mail.fr')
;