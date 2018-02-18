DROP TABLE IF EXISTS `photo`;
CREATE TABLE IF NOT EXISTS `photo`(
    `idPhoto` int(11) NOT NULL AUTO_INCREMENT,
    `cheminPhoto` varchar(30) NOT NULL,
    `idAnnonce` int(11),
    PRIMARY KEY (`idPhoto`)
);

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur`(
  `idUtilisateur` int(11) NOT NULL AUTO_INCREMENT,
  `nomUtilisateur` varchar(15) NOT NULL,
  `prenomUtilisateur` varchar(15) NOT NULL,
  `telephoneUtilisateur` varchar(15),
  `mailUtilisateur` varchar(30) NOT NULL,
  `promoUtilisateur` char(2) NOT NULL,
  `administrateur` boolean,
  `mdpUtilisateur` varchar(20) NOT NULL,
  PRIMARY KEY (`idUtilisateur`)
);

DROP TABLE IF EXISTS `categorie`;
CREATE TABLE IF NOT EXISTS `categorie`(
  `idCategorie` int(11) NOT NULL AUTO_INCREMENT,
  `nomCategorie` varchar(20) NOT NULL,
  PRIMARY KEY (`idCategorie`)
);

DROP TABLE IF EXISTS `commentaire`;
CREATE TABLE IF NOT EXISTS `commentaire`(
  `texteCommentaire` varchar(200) NOT NULL,
  `idUtilisateur` int(11) NOT NULL,
  `idAnnonce` int(11) NOT NULL
);

DROP TABLE IF EXISTS `annonce`;
CREATE TABLE IF NOT EXISTS `annonce`(
  `idAnnonce` int(11) NOT NULL AUTO_INCREMENT,
  `titreAnnonce` varchar(20) NOT NULL,
  `descriptionAnnonce` longtext NOT NULL,
  `motsClefsAnnonce` varchar(20) NOT NULL,
  `dateAnnonce` DATE NOT NULL,
  `evenement` boolean,
  `idCategorie` int(11) NOT NULL,
  `idUtilisateur` int(11) NOT NULL,
  PRIMARY KEY (`idAnnonce`)
);

ALTER TABLE `commentaire`
   ADD FOREIGN KEY (`idUtilisateur`) REFERENCES `utilisateur` (`idUtilisateur`),
   ADD FOREIGN KEY (`idAnnonce`) REFERENCES `annonce` (`idAnnonce`);
ALTER TABLE `annonce`
  ADD FOREIGN KEY (`idCategorie`) REFERENCES `categorie` (`idCategorie`),
  ADD FOREIGN KEY (`idUtilisateur`) REFERENCES `utilisateur` (`idUtilisateur`) ;
ALTER TABLE `photo`
  ADD FOREIGN KEY (`idAnnonce`) REFERENCES `annonce` (`idAnnonce`);


DROP TABLE IF EXISTS `publier`;
CREATE TABLE IF NOT EXISTS `publier`(
  `idAnnonce` int(11) NOT NULL,
  `idUtilisateur` int(11) NOT NULL,
  `compteur` int(11) NOT NULL,
  PRIMARY KEY (`idAnnonce`),
  PRIMARY KEY (`idUtilisateur`),
  KEY `idAnnonce_fk` (`idAnnonce`),
  CONSTRAINT `idAnnonce_fk` FOREIGN KEY (`idAnnonce`) REFERENCES `annonce` (`idAnnonce`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  KEY `idUtilisateur_fk` (`idUtilisateur`),
  CONSTRAINT `idUtilisateur_fk` FOREIGN KEY (`idUtilisateur`) REFERENCES `utilisateur` (`idUtilisateur`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
