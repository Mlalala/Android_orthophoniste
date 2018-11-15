BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `SearchSyllableVisu` (
	`serie`	INTEGER,
	`name`	TEXT,
	`syllables`	TEXT,
	`answers`	TEXT,
	PRIMARY KEY(`serie`)
);
INSERT INTO `SearchSyllableVisu` VALUES (1,'B/D','ba,da,bo,do','b,d');
INSERT INTO `SearchSyllableVisu` VALUES (2,'P/Q','pa,qa','p,q');
CREATE TABLE IF NOT EXISTS `PictureToPhonemePhono` (
	`serie`	INTEGER,
	`No`	INTEGER,
	`answer`	INTEGER
);
INSERT INTO `PictureToPhonemePhono` VALUES (1,1,1);
INSERT INTO `PictureToPhonemePhono` VALUES (1,2,0);
CREATE TABLE IF NOT EXISTS `PictureToPhonemeMenuPhono` (
	`serie`	INTEGER,
	`name`	TEXT,
	`proposals`	TEXT,
	PRIMARY KEY(`serie`)
);
INSERT INTO `PictureToPhonemeMenuPhono` VALUES (1,'B/D','d,b');
CREATE TABLE IF NOT EXISTS `MemorySyllabesVisu` (
	`serie`	INTEGER,
	`elements`	TEXT,
	`name`	TEXT,
	PRIMARY KEY(`serie`)
);
INSERT INTO `MemorySyllabesVisu` VALUES (1,'ba,da,qa,pa','B/D/P/Q');
INSERT INTO `MemorySyllabesVisu` VALUES (2,'la,lo,ga,go,fo,fa,ai,oi','A/O');
CREATE TABLE IF NOT EXISTS `MemoryPhono` (
	`serie`	INTEGER,
	`elements`	TEXT,
	`name`	TEXT
);
INSERT INTO `MemoryPhono` VALUES (1,'ba,da,bo,do,de,be','P/B');
CREATE TABLE IF NOT EXISTS `AudioToWordPhono` (
	`No_serie`	INTEGER,
	`No`	INTEGER,
	`proposals`	TEXT,
	`answer`	INTEGER
);
INSERT INTO `AudioToWordPhono` VALUES (1,1,'hipou-hibou',1);
INSERT INTO `AudioToWordPhono` VALUES (1,2,'boulanger-poulanger',0);
INSERT INTO `AudioToWordPhono` VALUES (2,1,'casquette-gasquette',0);
INSERT INTO `AudioToWordPhono` VALUES (2,2,'cêpe-guêpe',1);
INSERT INTO `AudioToWordPhono` VALUES (1,3,'jambe-jampe',0);
INSERT INTO `AudioToWordPhono` VALUES (1,4,'bantalon-pantalon',1);
INSERT INTO `AudioToWordPhono` VALUES (1,5,'nabe-nappe',1);
INSERT INTO `AudioToWordPhono` VALUES (2,3,'baque-bague',1);
INSERT INTO `AudioToWordPhono` VALUES (2,4,'égolier-écolier',1);
INSERT INTO `AudioToWordPhono` VALUES (2,5,'fatigue-fatic',0);
CREATE TABLE IF NOT EXISTS `AudioToWordMenuPhono` (
	`serie`	INTEGER,
	`name`	TEXT,
	PRIMARY KEY(`serie`)
);
INSERT INTO `AudioToWordMenuPhono` VALUES (1,'P/B');
INSERT INTO `AudioToWordMenuPhono` VALUES (2,'C/G');
CREATE TABLE IF NOT EXISTS `Articulation` (
	`Lettre`	TEXT,
	PRIMARY KEY(`Lettre`)
);
INSERT INTO `Articulation` VALUES ('B');
INSERT INTO `Articulation` VALUES ('CH');
INSERT INTO `Articulation` VALUES ('D');
INSERT INTO `Articulation` VALUES ('F');
INSERT INTO `Articulation` VALUES ('G');
INSERT INTO `Articulation` VALUES ('J');
INSERT INTO `Articulation` VALUES ('K');
INSERT INTO `Articulation` VALUES ('L');
INSERT INTO `Articulation` VALUES ('BL FL GL KL PL');
INSERT INTO `Articulation` VALUES ('M');
COMMIT;
