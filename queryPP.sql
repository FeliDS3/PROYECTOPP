CREATE DATABASE PP;
USE PP
CREATE TABLE ALUMNOS(
  idAlumno varchar(5) NOT NULL,
   nombre varchar(45) NOT NULL,
   apP varchar(45) NOT NULL,
   apM varchar(45) NOT NULL,
   totalActividades int,
   totalAsistencias int,
   PRIMARY KEY (idAlumno)
)ENGINE=InnoDB;

CREATE TABLE TRABAJOS(
  idTrabajo int AUTO_INCREMENT,
  nombreTrabajo varchar(45) UNIQUE NOT NULL,
  fechaTrabajo varchar(45) NOT NULL,
  valor int DEFAULT 100,
  PRIMARY KEY(idTrabajo)
)ENGINE=InnoDB;

ALTER TABLE TRABAJOS MODIFY fechaTrabajo varchar(10);


CREATE TABLE ASISTENCIAS(
    fecha varchar(10) NOT NULL,
    PRIMARY KEY (fecha)

)ENGINE=InnoDB;

CREATE TABLE ASISTENCIASALUMNOS(
    idAl varchar(5) NOT NULL,
    fechaA varchar(10) NOT NULL,
    FOREIGN KEY (fechaA) REFERENCES ASISTENCIAS(fecha),
    Foreign Key (idAl) REFERENCES ALUMNOS(idAlumno),
    PRIMARY KEY (idAl,fechaA)
)ENGINE=InnoDB;

CREATE TABLE TRABAJOSALUMNOS(
    idAlu varchar(5) NOT NULL,
    idT int NOT NULL,
    FOREIGN KEY (idT) REFERENCES TRABAJOS(idTrabajo),
    FOREIGN KEY (idAlu) REFERENCES ALUMNOS(idAlumno),
    PRIMARY KEY (idAlu,idT)
)ENGINE=InnoDB;

DELIMITER
CREATE TRIGGER after_trabajosalumnos_insert 
AFTER INSERT ON TRABAJOSALUMNOS
FOR EACH ROW
BEGIN
    UPDATE ALUMNOS 
    SET totalActividades = (
        SELECT SUM(T.valor)
        FROM TRABAJOSALUMNOS TA
        JOIN TRABAJOS T ON TA.idT = T.idTrabajo
        WHERE TA.idAlu = NEW.idAlu
    )
    WHERE idAlumno = NEW.idAlu;
END
DELIMITER ;