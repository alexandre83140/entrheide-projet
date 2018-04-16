
DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur`(
  `idUtilisateur` int(11) NOT NULL AUTO_INCREMENT,
  `nomUtilisateur` varchar(15) NOT NULL,
  `prenomUtilisateur` varchar(15) NOT NULL,
  `telephoneUtilisateur` varchar(15),
  `mailUtilisateur` varchar(60) NOT NULL,
  `promoUtilisateur` char(2) NOT NULL,
  `administrateur` int(1) NOT NULL,
  `mdpUtilisateur` varchar(20) NOT NULL,
  PRIMARY KEY (`idUtilisateur`)
);

DROP TABLE IF EXISTS `categorie`;
CREATE TABLE IF NOT EXISTS `categorie`(
  `idCategorie` int(11) NOT NULL AUTO_INCREMENT,
  `nomCategorie` varchar(20) NOT NULL,
  PRIMARY KEY (`idCategorie`)
);


DROP TABLE IF EXISTS `annonce`;
CREATE TABLE IF NOT EXISTS `annonce`(
  `idAnnonce` int(11) NOT NULL AUTO_INCREMENT,
  `titreAnnonce` varchar(20) NOT NULL,
  `descriptionAnnonce` longtext NOT NULL,
  `motsClefsAnnonce` varchar(20) NOT NULL,
  `dateAnnonce` DATE NOT NULL,
  `evenement` boolean,
  `signalee` boolean,
  `idCategorie` int(11) NOT NULL,
  `idUtilisateur` int(11) NOT NULL,
  PRIMARY KEY (`idAnnonce`)
);

ALTER TABLE `annonce`
  ADD FOREIGN KEY (`idCategorie`) REFERENCES `categorie` (`idCategorie`),
  ADD FOREIGN KEY (`idUtilisateur`) REFERENCES `utilisateur` (`idUtilisateur`) ;


INSERT INTO categorie (nomCategorie) VALUES ('Evenement');
INSERT INTO categorie (nomCategorie) VALUES ('Entraide');
INSERT INTO categorie (nomCategorie) VALUES ('Matériels scolaire');
INSERT INTO categorie (nomCategorie) VALUES ('Logement');
INSERT INTO categorie (nomCategorie) VALUES ('Covoiturage');
INSERT INTO categorie (nomCategorie) VALUES ('Meubles');
INSERT INTO utilisateur (nomUtilisateur, prenomUtilisateur, telephoneUtilisateur, mailUtilisateur, promoUtilisateur, administrateur, mdpUtilisateur) VALUES ('Prevert','Alexandre','0615433216','alexandre.prevert@hei.yncrea.fr','H4',true,'root');
INSERT INTO utilisateur (nomUtilisateur, prenomUtilisateur, telephoneUtilisateur, mailUtilisateur, promoUtilisateur, administrateur, mdpUtilisateur) VALUES ('Pereira','Maxime','0659920852','maxime.pereira@hei.yncrea.fr','H4',true,'root');
INSERT INTO annonce (idAnnonce, titreAnnonce, descriptionAnnonce, motsClefsAnnonce, dateAnnonce, evenement, signalee, idCategorie, idUtilisateur) VALUES (1,'Titre #1', 'Describ #1', 'Mots #1', '2018-01-01', false, true, 1, 1);
INSERT INTO annonce (idAnnonce, titreAnnonce, descriptionAnnonce, motsClefsAnnonce, dateAnnonce, evenement, signalee, idCategorie, idUtilisateur) VALUES (2,'Titre #2', 'Describ #2', 'Mots #2', '2018-02-02', false, false,2, 2);
INSERT INTO annonce (idAnnonce, titreAnnonce, descriptionAnnonce, motsClefsAnnonce, dateAnnonce, evenement, signalee, idCategorie, idUtilisateur) VALUES (3,'Titre #3', 'Describ #3', 'Mots #3', '2018-03-03', true, false, 1, 2);


