-- MySQL Workbench Forward Engineering (adaptado)
--
-- Ajustes aplicados em relação ao export original do Workbench, combinados com o autor do modelo:
--   1) `Funcionario`.`Departamento_Nome` passou de NOT NULL para NULLABLE, para quebrar a
--      dependência circular NOT NULL: Departamento -> Administrativo -> Funcionario -> Departamento
--      (um funcionário pode existir antes de ser alocado a um departamento).
--   2) Toda coluna que representa CPF (`Pessoa.Cpf` e as FKs que a referenciam) passou de INT para
--      BIGINT, pois CPF tem 11 dígitos e não cabe em INT (limite ~2,1 bilhões).

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Pessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Pessoa` (
  `Cpf` BIGINT NOT NULL,
  `sexo` VARCHAR(1) NOT NULL,
  `nascimento` DATE NOT NULL,
  `nome` VARCHAR(60) NOT NULL,
  `email` VARCHAR(65) NOT NULL,
  PRIMARY KEY (`Cpf`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `Cpf_UNIQUE` (`Cpf` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Telefone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Telefone` (
  `Telefone` INT NOT NULL,
  `Pessoa_Cpf` BIGINT NOT NULL,
  PRIMARY KEY (`Telefone`, `Pessoa_Cpf`),
  INDEX `fk_Telefone_Pessoa_idx` (`Pessoa_Cpf` ASC) VISIBLE,
  CONSTRAINT `fk_Telefone_Pessoa`
    FOREIGN KEY (`Pessoa_Cpf`)
    REFERENCES `mydb`.`Pessoa` (`Cpf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Endereco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Endereco` (
  `Id_endereco` INT NOT NULL,
  `cep` INT NOT NULL,
  `rua` VARCHAR(45) NOT NULL,
  `numero` INT NOT NULL,
  PRIMARY KEY (`Id_endereco`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Mora_em`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Mora_em` (
  `Endereco_Id_endereco` INT NOT NULL,
  `Pessoa_Cpf` BIGINT NOT NULL,
  PRIMARY KEY (`Endereco_Id_endereco`, `Pessoa_Cpf`),
  INDEX `fk_Endereco_has_Pessoa_Pessoa1_idx` (`Pessoa_Cpf` ASC) VISIBLE,
  INDEX `fk_Endereco_has_Pessoa_Endereco1_idx` (`Endereco_Id_endereco` ASC) VISIBLE,
  CONSTRAINT `fk_Endereco_has_Pessoa_Endereco1`
    FOREIGN KEY (`Endereco_Id_endereco`)
    REFERENCES `mydb`.`Endereco` (`Id_endereco`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Endereco_has_Pessoa_Pessoa1`
    FOREIGN KEY (`Pessoa_Cpf`)
    REFERENCES `mydb`.`Pessoa` (`Cpf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Casa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Casa` (
  `Endereco_Id_endereco` INT NOT NULL,
  PRIMARY KEY (`Endereco_Id_endereco`),
  INDEX `fk_Casa_Endereco1_idx` (`Endereco_Id_endereco` ASC) VISIBLE,
  CONSTRAINT `fk_Casa_Endereco1`
    FOREIGN KEY (`Endereco_Id_endereco`)
    REFERENCES `mydb`.`Endereco` (`Id_endereco`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Apartamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Apartamento` (
  `Endereco_Id_endereco` INT NOT NULL,
  `numero_apt` INT NOT NULL,
  `nome_torre` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`Endereco_Id_endereco`),
  INDEX `fk_Apartamento_Endereco1_idx` (`Endereco_Id_endereco` ASC) VISIBLE,
  CONSTRAINT `fk_Apartamento_Endereco1`
    FOREIGN KEY (`Endereco_Id_endereco`)
    REFERENCES `mydb`.`Endereco` (`Id_endereco`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Cliente` (
  `Pessoa_Cpf` BIGINT NOT NULL,
  PRIMARY KEY (`Pessoa_Cpf`),
  INDEX `fk_Cliente_Pessoa1_idx` (`Pessoa_Cpf` ASC) VISIBLE,
  CONSTRAINT `fk_Cliente_Pessoa1`
    FOREIGN KEY (`Pessoa_Cpf`)
    REFERENCES `mydb`.`Pessoa` (`Cpf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Funcionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Funcionario` (
  `Pessoa_Cpf` BIGINT NOT NULL,
  `Departamento_Nome` VARCHAR(15) NULL,
  `id_funcionario` INT NOT NULL,
  PRIMARY KEY (`Pessoa_Cpf`),
  INDEX `fk_Funcionario_Pessoa1_idx` (`Pessoa_Cpf` ASC) VISIBLE,
  INDEX `fk_Funcionario_Departamento1_idx` (`Departamento_Nome` ASC) VISIBLE,
  CONSTRAINT `fk_Funcionario_Pessoa1`
    FOREIGN KEY (`Pessoa_Cpf`)
    REFERENCES `mydb`.`Pessoa` (`Cpf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Funcionario_Departamento1`
    FOREIGN KEY (`Departamento_Nome`)
    REFERENCES `mydb`.`Departamento` (`Nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Administrativo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Administrativo` (
  `Funcionario_Cpf` BIGINT NOT NULL,
  PRIMARY KEY (`Funcionario_Cpf`),
  INDEX `fk_Administrativo_Funcionario1_idx` (`Funcionario_Cpf` ASC) VISIBLE,
  CONSTRAINT `fk_Administrativo_Funcionario1`
    FOREIGN KEY (`Funcionario_Cpf`)
    REFERENCES `mydb`.`Funcionario` (`Pessoa_Cpf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Departamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Departamento` (
  `Nome` VARCHAR(15) NOT NULL,
  `Administrativo_Cpf` BIGINT NOT NULL,
  PRIMARY KEY (`Nome`, `Administrativo_Cpf`),
  INDEX `fk_Departamento_Administrativo1_idx` (`Administrativo_Cpf` ASC) VISIBLE,
  CONSTRAINT `fk_Departamento_Administrativo1`
    FOREIGN KEY (`Administrativo_Cpf`)
    REFERENCES `mydb`.`Administrativo` (`Funcionario_Cpf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Operacional`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Operacional` (
  `Funcionario_Cpf` BIGINT NOT NULL,
  `Administrativo_Cpf` BIGINT NOT NULL,
  PRIMARY KEY (`Funcionario_Cpf`),
  INDEX `fk_Operacional_Funcionario1_idx` (`Funcionario_Cpf` ASC) VISIBLE,
  INDEX `fk_Operacional_Administrativo1_idx` (`Administrativo_Cpf` ASC) VISIBLE,
  CONSTRAINT `fk_Operacional_Funcionario1`
    FOREIGN KEY (`Funcionario_Cpf`)
    REFERENCES `mydb`.`Funcionario` (`Pessoa_Cpf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Operacional_Administrativo1`
    FOREIGN KEY (`Administrativo_Cpf`)
    REFERENCES `mydb`.`Administrativo` (`Funcionario_Cpf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Pedido` (
  `Id_Pedido` INT NOT NULL,
  `data` DATE NOT NULL,
  `hora` TIME NOT NULL,
  `valor_pago` DOUBLE NOT NULL,
  `Cliente_Cpf` BIGINT NOT NULL,
  PRIMARY KEY (`Id_Pedido`),
  INDEX `fk_Pedido_Cliente1_idx` (`Cliente_Cpf` ASC) VISIBLE,
  CONSTRAINT `fk_Pedido_Cliente1`
    FOREIGN KEY (`Cliente_Cpf`)
    REFERENCES `mydb`.`Cliente` (`Pessoa_Cpf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Filme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Filme` (
  `Id_Filme` INT NOT NULL,
  `titulo` VARCHAR(45) NOT NULL,
  `duracao` DECIMAL NOT NULL,
  `linguagem` VARCHAR(10) NOT NULL,
  `sinopse` VARCHAR(105) NOT NULL,
  PRIMARY KEY (`Id_Filme`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Genero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Genero` (
  `Genero` VARCHAR(15) NOT NULL,
  `Filme_Id_Filme` INT NOT NULL,
  PRIMARY KEY (`Genero`, `Filme_Id_Filme`),
  INDEX `fk_Genero_Filme1_idx` (`Filme_Id_Filme` ASC) VISIBLE,
  CONSTRAINT `fk_Genero_Filme1`
    FOREIGN KEY (`Filme_Id_Filme`)
    REFERENCES `mydb`.`Filme` (`Id_Filme`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Sala`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Sala` (
  `Id_Sala` INT NOT NULL,
  PRIMARY KEY (`Id_Sala`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Sessao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Sessao` (
  `Id_Sessao` INT NOT NULL,
  `data` TIME NOT NULL,
  `hora_inicial` TIME NOT NULL,
  `hora_final` DATE NOT NULL,
  `tipo` VARCHAR(3) NOT NULL,
  `valor_sessao` FLOAT NOT NULL,
  `Sala_Id_Sala` INT NOT NULL,
  `Administrativo_Cpf` BIGINT NOT NULL,
  `Filme_Id_Filme` INT NOT NULL,
  PRIMARY KEY (`Id_Sessao`),
  INDEX `fk_Sessao_Sala1_idx` (`Sala_Id_Sala` ASC) VISIBLE,
  INDEX `fk_Sessao_Administrativo1_idx` (`Administrativo_Cpf` ASC) VISIBLE,
  INDEX `fk_Sessao_Filme1_idx` (`Filme_Id_Filme` ASC) VISIBLE,
  CONSTRAINT `fk_Sessao_Sala1`
    FOREIGN KEY (`Sala_Id_Sala`)
    REFERENCES `mydb`.`Sala` (`Id_Sala`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Sessao_Administrativo1`
    FOREIGN KEY (`Administrativo_Cpf`)
    REFERENCES `mydb`.`Administrativo` (`Funcionario_Cpf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Sessao_Filme1`
    FOREIGN KEY (`Filme_Id_Filme`)
    REFERENCES `mydb`.`Filme` (`Id_Filme`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Assento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Assento` (
  `Id_Assento` INT NOT NULL,
  `fila` CHAR(1) NOT NULL,
  `coluna` VARCHAR(2) NOT NULL,
  `prioritario` VARCHAR(10) NOT NULL,
  `Sala_Id_Sala` INT NOT NULL,
  PRIMARY KEY (`Id_Assento`),
  INDEX `fk_Assento_Sala1_idx` (`Sala_Id_Sala` ASC) VISIBLE,
  CONSTRAINT `fk_Assento_Sala1`
    FOREIGN KEY (`Sala_Id_Sala`)
    REFERENCES `mydb`.`Sala` (`Id_Sala`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Ingresso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Ingresso` (
  `Id_Ingresso` INT NOT NULL,
  `valor_ingresso` FLOAT NOT NULL,
  `tipo` VARCHAR(12) NOT NULL,
  `Pedido_Id_Pedido` INT NOT NULL,
  `Assento_Id_Assento` INT NOT NULL,
  `Sessao_Id_Sessao` INT NOT NULL,
  PRIMARY KEY (`Id_Ingresso`),
  INDEX `fk_Ingresso_Pedido1_idx` (`Pedido_Id_Pedido` ASC) VISIBLE,
  INDEX `fk_Ingresso_Sessao1_idx` (`Sessao_Id_Sessao` ASC) VISIBLE,
  INDEX `fk_Ingresso_Assento1_idx` (`Assento_Id_Assento` ASC) VISIBLE,
  CONSTRAINT `fk_Ingresso_Pedido1`
    FOREIGN KEY (`Pedido_Id_Pedido`)
    REFERENCES `mydb`.`Pedido` (`Id_Pedido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ingresso_Sessao1`
    FOREIGN KEY (`Sessao_Id_Sessao`)
    REFERENCES `mydb`.`Sessao` (`Id_Sessao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ingresso_Assento1`
    FOREIGN KEY (`Assento_Id_Assento`)
    REFERENCES `mydb`.`Assento` (`Id_Assento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Trabalha_em`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Trabalha_em` (
  `Operacional_Cpf` BIGINT NOT NULL,
  `Sessao_Id_Sessao` INT NOT NULL,
  PRIMARY KEY (`Operacional_Cpf`, `Sessao_Id_Sessao`),
  INDEX `fk_Operacional_has_Sessao_Sessao1_idx` (`Sessao_Id_Sessao` ASC) VISIBLE,
  INDEX `fk_Operacional_has_Sessao_Operacional1_idx` (`Operacional_Cpf` ASC) VISIBLE,
  CONSTRAINT `fk_Operacional_has_Sessao_Operacional1`
    FOREIGN KEY (`Operacional_Cpf`)
    REFERENCES `mydb`.`Operacional` (`Funcionario_Cpf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Operacional_has_Sessao_Sessao1`
    FOREIGN KEY (`Sessao_Id_Sessao`)
    REFERENCES `mydb`.`Sessao` (`Id_Sessao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
