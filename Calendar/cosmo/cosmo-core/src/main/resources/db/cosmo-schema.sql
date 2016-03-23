
DROP TABLE attribute IF EXISTS;
CREATE TABLE attribute (
  attributetype VARCHAR(16) NOT NULL,
  id BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  createdate BIGINT DEFAULT NULL,
  etag VARCHAR(255) DEFAULT NULL,
  modifydate BIGINT DEFAULT NULL,
  localname VARCHAR(255) NOT NULL,
  namespace VARCHAR(255) NOT NULL,
  booleanvalue boolean DEFAULT NULL,
  textvalue clob(2147483647),
  intvalue BIGINT DEFAULT NULL,
  stringvalue varchar(2048) DEFAULT NULL,
  binvalue blob(2147483647),
  decvalue NUMERIC(19,6) DEFAULT NULL,
  datevalue DATETIME DEFAULT NULL,
  tzvalue VARCHAR(32) DEFAULT NULL,
  itemid BIGINT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (itemid,namespace,localname)
);

DROP TABLE IF EXISTS collection_item;
CREATE TABLE collection_item (
  createdate BIGINT NOT NULL,
  itemid BIGINT NOT NULL,
  collectionid BIGINT NOT NULL,
  PRIMARY KEY (collectionid, itemid),
);

DROP TABLE IF EXISTS content_data;
CREATE TABLE content_data (
  id BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  content blob(102400000),
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS dictionary_values;
CREATE TABLE dictionary_values (
  attributeid BIGINT NOT NULL,
  stringvalue VARCHAR(2048) DEFAULT NULL,
  keyname VARCHAR(255) NOT NULL,
  PRIMARY KEY (attributeid, keyname)
);

DROP TABLE IF EXISTS event_log;
CREATE TABLE event_log (
  id BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  authid BIGINT NOT NULL,
  authtype VARCHAR(64) NOT NULL,
  entrydate BIGINT DEFAULT NULL,
  id1 BIGINT DEFAULT NULL,
  id2 BIGINT DEFAULT NULL,
  id3 BIGINT DEFAULT NULL,
  id4 BIGINT DEFAULT NULL,
  strval1 VARCHAR(255) DEFAULT NULL,
  strval2 VARCHAR(255) DEFAULT NULL,
  strval3 VARCHAR(255) DEFAULT NULL,
  strval4 VARCHAR(255) DEFAULT NULL,
  eventtype VARCHAR(64) NOT NULL,
  uid1 varchar(255) DEFAULT NULL,
  uid2 varchar(255) DEFAULT NULL,
  uid3 varchar(255) DEFAULT NULL,
  uid4 varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS event_stamp;
CREATE TABLE event_stamp (
  icaldata clob(102400000) NOT NULL,
  enddate varchar(16) DEFAULT NULL,
  isfloating boolean DEFAULT NULL,
  isrecurring boolean DEFAULT NULL,
  startdate varchar(16) DEFAULT NULL,
  stampid bigint NOT NULL,
  PRIMARY KEY (stampid)
);


DROP TABLE IF EXISTS item;
CREATE TABLE item (
  itemtype varchar(16) NOT NULL,
  id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  createdate bigint DEFAULT NULL,
  etag varchar(255) DEFAULT NULL,
  modifydate bigint DEFAULT NULL,
  clientcreatedate bigint DEFAULT NULL,
  clientmodifieddate bigint DEFAULT NULL,
  displayname varchar(1024) DEFAULT NULL,
  itemname varchar(255) NOT NULL,
  uid varchar(255) NOT NULL,
  version integer NOT NULL,
  lastmodification integer DEFAULT NULL,
  lastmodifiedby varchar(255) DEFAULT NULL,
  needsreply boolean DEFAULT NULL,
  sent boolean DEFAULT NULL,
  isautotriage boolean DEFAULT NULL,
  triagestatuscode integer DEFAULT NULL,
  triagestatusrank numeric(12,2) DEFAULT NULL,
  icaluid varchar(255) DEFAULT NULL,
  contentEncoding varchar(32) DEFAULT NULL,
  contentLanguage varchar(32) DEFAULT NULL,
  contentLength bigint DEFAULT NULL,
  contentType varchar(64) DEFAULT NULL,
  hasmodifications boolean DEFAULT NULL,
  ownerid bigint NOT NULL,
  contentdataid bigint DEFAULT NULL,
  modifiesitemid bigint DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE (uid)
);

DROP TABLE IF EXISTS multistring_values;
CREATE TABLE multistring_values (
  attributeid bigint NOT NULL,
  stringvalue varchar(2048) DEFAULT NULL
);


DROP TABLE IF EXISTS pwrecovery;
CREATE TABLE pwrecovery (
  id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  creationdate datetime DEFAULT NULL,
  pwrecoverykey varchar(255) NOT NULL,
  timeout bigint DEFAULT NULL,
  userid bigint DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE (pwrecoverykey)
);


DROP TABLE IF EXISTS server_properties;
CREATE TABLE server_properties (
  id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  propertyname varchar(255) NOT NULL,
  propertyvalue varchar(2048) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE (propertyname)
);

DROP TABLE IF EXISTS stamp;
CREATE TABLE stamp (
  stamptype varchar(16) NOT NULL,
  id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  createdate bigint DEFAULT NULL,
  etag varchar(255) DEFAULT NULL,
  modifydate bigint DEFAULT NULL,
  itemid bigint NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (itemid,stamptype)
);

DROP TABLE IF EXISTS subscription;
CREATE TABLE subscription (
  id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  createdate bigint DEFAULT NULL,
  etag varchar(255) DEFAULT NULL,
  modifydate bigint DEFAULT NULL,
  collectionuid varchar(255) NOT NULL,
  displayname varchar(255) NOT NULL,
  ticketkey varchar(255) NOT NULL,
  ownerid bigint NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (ownerid, displayname)
);

DROP TABLE IF EXISTS ticket_privilege;
CREATE TABLE ticket_privilege (
  ticketid bigint NOT NULL,
  privilege varchar(255) NOT NULL,
  PRIMARY KEY (ticketid,privilege)
);

DROP TABLE IF EXISTS tickets;
CREATE TABLE tickets (
  id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  createdate bigint DEFAULT NULL,
  etag varchar(255) DEFAULT NULL,
  modifydate bigint DEFAULT NULL,
  creationdate datetime DEFAULT NULL,
  ticketkey varchar(255) NOT NULL,
  tickettimeout varchar(255) NOT NULL,
  itemid bigint DEFAULT NULL,
  ownerid bigint DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE (ticketkey)
);

DROP TABLE IF EXISTS tombstones;
CREATE TABLE tombstones (
  tombstonetype varchar(16) NOT NULL,
  id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  removedate bigint NOT NULL,
  stamptype varchar(255) DEFAULT NULL,
  localname varchar(255) DEFAULT NULL,
  namespace varchar(255) DEFAULT NULL,
  itemuid varchar(255) DEFAULT NULL,
  itemid bigint NOT NULL,
  itemname varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
)


DROP TABLE IF EXISTS user_preferences;
CREATE TABLE user_preferences (
  id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  createdate bigint DEFAULT NULL,
  etag varchar(255) DEFAULT NULL,
  modifydate bigint DEFAULT NULL,
  preferencename varchar(255) NOT NULL,
  preferencevalue varchar(255) NOT NULL,
  userid bigint NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (userid, preferencename)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  createdate bigint DEFAULT NULL,
  etag varchar(255) DEFAULT NULL,
  modifydate bigint DEFAULT NULL,
  activationid varchar(255) DEFAULT NULL,
  admin boolean DEFAULT NULL,
  email varchar(128) DEFAULT NULL,
  firstname varchar(128) DEFAULT NULL,
  lastname varchar(128) DEFAULT NULL,
  locked boolean DEFAULT NULL,
  password varchar(255) NOT NULL,
  uid varchar(255) NOT NULL,
  username varchar(64) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (uid),
  UNIQUE (username),
  UNIQUE (email)
);

INSERT INTO users (id, createdate, etag, modifydate, activationid, admin, email, firstname, lastname, locked, password, uid, username) VALUES(1, 1404983699604, 'Y1PlUVPg/qxugBgLtXoQC9u8k8M=', 1404983699604, null, 1, 'root@localhost', 'Cosmo', 'Administrator', 0, '32a8bd4d676f4fef0920c7da8db2bad7', '648e2565-2081-4e60-9cac-306a4ffb8d64', 'root');


INSERT INTO ticket_privilege VALUES(2344, '12132311343');
