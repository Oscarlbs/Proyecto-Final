DROP database IF EXISTS cafeteria;

CREATE database cafeteria;

use cafeteria;

create table articulo (

codigo_articulo int auto_increment PRIMARY KEY,
stock int NULL,
precio double NOT NULL,

);

create table ticket (

codigo_articulo int PRIMARY KEY,
fecha_hora date,

);

CREATE TABLE ticket_articulo(

articulo int,
ticket int,
articulos_vendidos int NOT NULL,

PRIMARY KEY (ticket,articulo)
	
FOREIGN KEY (articulo) REFERENCES articulo(codigo_articulo)
	ON DELETE CASCADE,
	ON UPDATE CASCADE,
	
foreign key (ticket) references ticket(codigo_ticket)
	on delete cascade
	on update cascade
);

create table bebidas (

articulos_bebidas int,
tipos_bebidas enum('agua','coca_cola', 'aquarius','nestea','batidos', 'cerveza', 'vino','fanta','sprite','gaseosas') NULL,
	
FOREIGN KEY (articulos_bebidas) REFERENCES articulo(codigo_articulo)	
	ON DELETE CASCADE 
	ON UPDATE cascade
	
);

create table pinchos (

articulo_pincho int,
tipo_pincho ('tortilla','chorizo','ternera','pulpo','queso','huevo','cerdo','salmon','jamón','lomo'),
tipos ('pincho', 'tapa', 'racion'),
alergenos ('lacteos','carnicos','pescado','marisco', 'aceites','huevo','harina','nueces','almendras','gluten'),
foreign key (articulo_pincho) references articulo(codigo_articulo)
	on delete cascade
	on update cascade
);

create table alergenos (

articulo_alergenos int,
numero_alergenos enum('lacteos','carnicos','pescado','marisco', 'aceites','huevo','harina','nueces','almendras','gluten' ),

foreign key (art_alergenos) references articulo(cod_art)
	on delete cascade
	on update cascade
);

INSERT INTO articulo (codigo_articulo, stock, precio) VALUES

( '1' , '65' , '5.50' ),
( '2' , '52' , '5' ),
( '3' , '41' , '6.50' ),
( '4' , '59' , '3.20' ),
( '5' , '15' , '7.5' ),
( '6' , '12'  , '2.5' ),
( '7' , '24' , '2' ),
( '8' , '62'  , '3' ),
( '9' , '45' , '4.5' ),
( '10', '85' , '2' ),
( '11', '45' , '1.5' ),
( '12', '62' , '3.5' ),
( '13', '41' , '4.5' ),
( '14', '51' , '3.0' ),
( '15', '26' , '2.25' ),
( '16', '54' , '2.10' ),
( '17', '54' , '1.65' ),
( '18', '69' , '1.35' ),
( '19', '45' , '1.25' ),
( '20', '8' , '3.00'),
( '21', '13' , '7.5' ),
( '22', '26' , '2.25' ),
( '23', '27' , '1.50' ),
( '24', '19' , '1.75' ),
( '25', '36' , '4.00' ),
( '26', '14' , '1.00' ),
( '27', '16' , '2.25' ),
( '28', '25' , '2.50' ),
( '29', '58' , '3.75' ),
( '30 ', '31' , '2.50' );


INSERT INTO ticket (codigo_ticket,fecha_hora) VALUES 

( '1'  , '2022-01-24 11:14:24.00' ),
( '2'  , '2022-03-14 07:24:02.00' ),
( '3'  , '2022-04-28 07:54:27.00' ),
( '4'  , '2022-04-7 07:06:20.00' ),
( '5'  , '2022-07-14 23:17:51.00' ),
( '6'  , '2022-07-26 09:08:50.00' ),
( '7'  , '2022-07-14 10:10:53.00' ),
( '8'  , '2022-07-17 20:13:14.00' ),
( '9'  , '2022-10-03 23:14:03.00' ),
( '10' , '2022-11-18 17:11:21.00' );

INSERT INTO ticket_articulo (articulo,articulo_vendido) VALUES 

( '1'  , '1' , '5'  ),
( '2'  , '2' , '3'  ),
( '3'  , '3' , '6'  ),
( '4'  , '4' , '2'  ),
( '5'  , '5' , '4'  ),
( '6'  , '6' , '5'  ),
( '7'  , '7' , '10' ),
( '16'  , '8' , '12' ),
( '9'  , '9' , '3'  ),
( '22' , '10', '6'  );


INSERT INTO bebidas (articulo_bebidas,tipos_bebidas) VALUES

( '1'  , 'agua'),
( '2'  , 'coca_cola'),
( '3'  , 'aquarius'),
( '4'  , 'nestea'),
( '5'  , 'batidos'),
( '6'  , 'cerveza'),
( '7'  , 'vino'),
( '8'  , 'fanta'	),
( '9'  , 'sprite'),
( '10' , 'gaseosas');


INSERT INTO pinchos (articulo_pinchos,tipo,alergenos) VALUES


( '11','pincho','tortilla','huevo'),
( '12','pincho','chorizo','carnicos'),
( '13','pincho','ternera','carnicos'),
( '14','pincho','pulpo','mariscos'),
( '15','tapa','queso','lacteos'),
( '16','tapa','huevo','huevo'),
( '17','racion','cerdo','carnicos'),
( '18','tapa','salmon','pescado'),
( '19','tapa','jamón','carnicos'),
( '20','tapa','lomo','carnicos');


INSERT INTO alergenos (articulo_alergenos,tipo_alergenos) VALUES 

( '21'  , 'lacteos'	),
( '22'  , 'carnicos'),
( '23'  , 'pescado'),
( '24'  , 'marisco'),
( '25'  , 'aceites'	),
( '26'  , 'huevo'),
( '27'  , 'harina'),
( '28'  , 'nueces'	),
( '29'  , 'almendras'),
( '30'  , 'gluten');


# TRIGGERS 1 Y 2

	
# 1. Evita ventas si NO hay stock.
# 2. Reduce el stock disponible con cada nuevo ticket que se añada.

delimiter $$

DROP TRIGGER IF EXISTS evitar_reducir_ventas $$
CREATE TRIGGER evitar_reducir_ventas
AFTER INSERT ON ticket_articulo for EACH ROW

BEGIN 
	
	DECLARE aux_stock int;
	DECLARE resultado int;

	SELECT articulo.stock INTO aux_stock
	FROM ticket_articulo ta
	INNER JOIN articulo a ON ta.art = a.cod_art
	WHERE ta.art = NEW.art;
	
	IF (NEW.articulo_vendido > aux_stock)
	
	THEN 
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Error, no disponemos de tanto stock";
	
		signal SQLSTATE '99999' SET message_text ="Error, no hay tanto stock disponible";
	else 
	
		SET resultado = aux_stock - NEW.articulo_vendido;
	
		update articulo a set a.stock = resultado where a.cod_art = new.art;
	end if;

		UPDATE articulo a SET articulo.stock = resultado WHERE articulo.codigo_articulo = NEW.articulo;
		END IF;
END;
$$

delimiter ;


# 3. TRIGGER, aumenta el stock con cada ticket que se borre.

delimiter $$
DROP TRIGGER IF EXISTS devolucion $$
CREATE TRIGGER devolucion
BEFORE DELETE ON ticket_articulo FOR EACH ROW 

begin 
	
	DECLARE aux_stock int;
	DECLARE aux_stock int;

	
	SELECT articulo.stock INTO aux_stock
	FROM ticket_articulo ta
	inner JOIN articulo a ON ta.art = a.codigo_articulo
	WHERE ta.art = OLD.art;

	SET resultado = aux_stock + OLD.articulo_vendidos;
	UPDATE articulo a SET articulo.stock = resultado WHERE articulo.codigo_articulo = OLD.articulo;
	
END;
$$

delimiter;


# 4. TRIGGER aumenta o reduce el stock disponible ante cambios en los ticket.


delimiter $$


DROP TRIGGER IF EXISTS update_stock $$
CREATE TRIGGER update_stock
AFTER UPDATE ON ticket_articulo FOR EACH ROW 

BEGIN
	
	
	DECLARE aux_stock int;
	DECLARE resultado int;
	SET resultado = aux_stock - NEW.articulo;

	SELECT articulo.stock INTO aux_stock
	FROM ticket_articulo ta
	INNER JOIN articulo a ON a.codigo_articulo = ta.articulo
	WHERE a.cod_art = NEW.articulo;
	
	UPDATE articulo SET articulo.stock = aux_stock + NEW.articulo
	

END;
$$

delimiter;


# 5. Vision de los articulos disponibles para el dependiente.


DROP VIEW IF EXISTS articulos_disponibles;
CREATE VIEW articulos_disponibles AS
SELECT *
FROM articulo articulos
LEFT JOIN pinchos p ON p.articulos_pinchos = articulos.codigo_articulos
LEFT JOIN bebidas b ON b.articulos_bebidas = articulos.codigo_articulos
LEFT JOIN alergenos al ON al.articulo_alergenos = articulos.codigo_articulos

WHERE articulos.stock > 0;


# 6. Vista del administrador con los datos ordenados del mas reciente al mas antiguo.


DROP VIEW IF EXISTS resum_tickets;
CREATE VIEW resum_tickets AS
SELECT*
FROM ticket t 
ORDER BY t.fecha_hora ASC;


# 7. Vista del administrador con los datos de los 5 articulos mas vendidos.


DROP VIEW IF EXISTS resum_art ;
CREATE VIEW resum-art AS 
SELECT articulo.codigo_articulo, p.*, b.*, al.*, sum(ta.articulo_vend) AS suma_precios
FROM articulo articulos
LEFT JOIN pinchos p ON p.articulos_pinchos = articulos.codigo_articulos
LEFT JOIN bebidas b ON b.articulos_bebidas = articulos.codigo_articulos
LEFT JOIN alergenos al ON al.articulo_alergenos = articulos.codigo_articulos
INNER JOIN ticket_articulo ta ON ta.articulo = a.cod_art
GROUP BY a.cod_art ORDER BY suma_preciosa DESC;

# Roles


DROP ROLE IF EXISTS administrador;
DROP ROLE IF EXISTS dependiente;

CREATE ROLE administrador;
CREATE ROLE dependiente;

GRANT ALL PRIVILEGES ON resum_tickets TO administrador;
GRANT ALL PRIVILEGES ON resum_articulos TO administrador;
GRANT ALL PRIVILEGES ON articulos_disponibles TO administrador;

GRANT INSERT, DROP, SELECT, UPDATE ON resum_tickets TO administrador;
GRANT INSERT, DROP, SELECT, UPDATE ON resum_art TO administrador;
GRANT SELECT ON arts_disponibles TO dependiente;











