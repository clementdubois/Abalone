-- Table: "Commentaire"

-- DROP TABLE "Commentaire";

CREATE TABLE "Commentaire"
(
  contenu text NOT NULL,
  joueur_k integer NOT NULL,
  mouvement_k bit varying(15),
  CONSTRAINT "Commentaire_joueur_k_fkey" FOREIGN KEY (joueur_k)
      REFERENCES "Joueur" (joueur_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "Commentaire_mouvement_k_fkey" FOREIGN KEY (mouvement_k)
      REFERENCES "Mouvement" (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Commentaire" OWNER TO postgres;

-- Index: fki_

-- DROP INDEX fki_;

CREATE INDEX fki_
  ON "Commentaire"
  USING btree
  (mouvement_k);


  
  
  -- Table: "Commentaire"

-- DROP TABLE "Commentaire";

CREATE TABLE "Commentaire"
(
  contenu text NOT NULL,
  joueur_k integer NOT NULL,
  mouvement_k bit varying(15),
  CONSTRAINT "Commentaire_joueur_k_fkey" FOREIGN KEY (joueur_k)
      REFERENCES "Joueur" (joueur_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "Commentaire_mouvement_k_fkey" FOREIGN KEY (mouvement_k)
      REFERENCES "Mouvement" (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Commentaire" OWNER TO postgres;

-- Index: fki_

-- DROP INDEX fki_;

CREATE INDEX fki_
  ON "Commentaire"
  USING btree
  (mouvement_k);

-- Table: "Membres"

-- DROP TABLE "Membres";

CREATE TABLE "Membres"
(
  pseudo text,
  membre_id integer NOT NULL,
  membre_password character varying,
  CONSTRAINT "Membres_pkey" PRIMARY KEY (membre_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Membres" OWNER TO postgres;


-- Table: "Message_mail"

-- DROP TABLE "Message_mail";

CREATE TABLE "Message_mail"
(
  contenu text,
  date date,
  sender integer,
  receiver integer,
  CONSTRAINT "Message_mail_receiver_fkey" FOREIGN KEY (receiver)
      REFERENCES "Membres" (membre_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "Message_mail_sender_fkey" FOREIGN KEY (sender)
      REFERENCES "Membres" (membre_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Message_mail" OWNER TO postgres;

-- Index: nerf

-- DROP INDEX nerf;

CREATE INDEX nerf
  ON "Message_mail"
  USING btree
  (receiver);

-- Index: osef

-- DROP INDEX osef;

CREATE INDEX osef
  ON "Message_mail"
  USING btree
  (sender);

-- Table: "Mouvement"

-- DROP TABLE "Mouvement";

CREATE TABLE "Mouvement"
(
  code bit varying(16) NOT NULL,
  numero_mouvement integer NOT NULL,
  partie_k integer,
  branche_k integer,
  CONSTRAINT "Mouvement_pkey" PRIMARY KEY (code),
  CONSTRAINT "Mouvement_branche_k_fkey" FOREIGN KEY (branche_k)
      REFERENCES branche (branche_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "Mouvement_partie_k_fkey" FOREIGN KEY (partie_k)
      REFERENCES "Partie" (partie_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Mouvement" OWNER TO postgres;
COMMENT ON TABLE "Mouvement" IS 'modele basé sur un nombre binaire de 15 bit 
qui décrit un extrémité_depart
                     extrémité_arrivé
                     vecteur
';

-- Index: fki

-- DROP INDEX fki;

CREATE INDEX fki
  ON "Mouvement"
  USING btree
  (branche_k);

-- Index: fki__

-- DROP INDEX fki__;

CREATE INDEX fki__
  ON "Mouvement"
  USING btree
  (partie_k);


  -- Table: "Partie"

-- DROP TABLE "Partie";

CREATE TABLE "Partie"
(
  partie_id integer NOT NULL,
  joueur_blanc character varying NOT NULL,
  joueur_noir character varying NOT NULL,
  vainqueur character varying,
  score character(3),
  temps integer,
  spectateurs integer[],
  CONSTRAINT "Partie_pkey" PRIMARY KEY (partie_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Partie" OWNER TO postgres;


-- Table: branche

-- DROP TABLE branche;

CREATE TABLE branche
(
  branche_id integer NOT NULL,
  CONSTRAINT branche_pkey PRIMARY KEY (branche_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE branche OWNER TO postgres;

-- Table: message_chat

-- DROP TABLE message_chat;

CREATE TABLE message_chat
(
  sender integer,
  contenu text,
  CONSTRAINT message_chat_sender_fkey FOREIGN KEY (sender)
      REFERENCES "Membres" (membre_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE message_chat OWNER TO postgres;

-- Index: fkimml

-- DROP INDEX fkimml;

CREATE INDEX fkimml
  ON message_chat
  USING btree
  (sender);

-- Table: plateau

-- DROP TABLE plateau;

CREATE TABLE plateau
(

)
WITH (
  OIDS=FALSE
);
ALTER TABLE plateau OWNER TO postgres;
