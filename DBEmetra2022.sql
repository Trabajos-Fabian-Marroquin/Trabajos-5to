/* NOMBRE: Fabian Angel Samuel Marroquin Chali
   CODIGO: IN5AV
   FECHA DE CREACION: 26/09/2022
   FECHA DE MODIFICACION: 27/09/2022 
*/
Drop database if exists DBEmetra2022;
Create database DBEmetra2022;
use DBEmetra2022;

create table Vecinos(
	NIT varchar(15) not null,
	DPI bigint(13) not null,
	nombres varchar(100) not null,
	apellidos varchar(100) not null,
	direccion varchar(200) not null,
	municipalidad varchar(45) not null,
	codigoPostal int not null,
	telefono varchar(8) not null,
	primary key PK_NIT (NIT)
);

create table Vehiculos(
	placa varchar(15) not null,
    marca varchar(45) not null,
    modelo varchar(45) not null,
    tipoDeVehiculo varchar(60) not null,
    NIT varchar(15) not null,
    primary key PK_placa (placa),
    constraint FK_Vehiculos_Vecinos foreign key (NIT) references Vecinos(NIT)
);
/* procedimientos almacenados  */
/* -------- Vecinos -----------*/
-- ----agregar
Delimiter $$
	create procedure sp_AgregarVecino(in NIT varchar(15), in DPI bigint(13),nombres varchar(100),in apellidos varchar(100), in direccion varchar(200), in municipalidad varchar(45), in codigoPostal int, in telefono varchar(8))
		begin
			insert into Vecinos (NIT, DPI, nombres, apellidos, direccion, municipalidad, codigoPostal, telefono)
				values (NIT, DPI, nombres, apellidos, direccion, municipalidad, codigoPostal, telefono);
        end$$
Delimiter ;
call sp_AgregarVecino('123456789012345',123456788888,'Fabian','Marroquin','zona 3','de la zona 3',321,'12345555');
call sp_AgregarVecino('123456789012378',123456788878,'Angel','Chali','zona 15','de la zona 15',381,'12348888');

-- -------listar
Delimiter $$
	create procedure sp_ListarVecinos()
		begin
			select 
            NIT, DPI, nombres, apellidos, direccion, municipalidad, codigoPostal, telefono
            from Vecinos;
        end$$
delimiter ;
call sp_ListarVecinos();

-- -----------Buscar Por codigo
Delimiter $$
	create procedure sp_BuscarVecino(in NITBus varchar(15))
		begin
			select NIT, DPI, nombres, apellidos, direccion, municipalidad, codigoPostal, telefono
				from Vecinos where NIT = NITBus;
        end$$
Delimiter ;
-- ----call sp_BuscarVecino('123456789012378');

-- -----------------Eliminar
Delimiter $$
	create procedure sp_EliminarVecino(in NITDelete varchar(15))
		begin
			delete from Vecinos
				where NIT = NITDelete;
        end$$
Delimiter ;
-- --------call sp_EliminarVecino('123456789012345');
-- --------call sp_ListarVecinos();

-- -----------------Actualizar
Delimiter $$
	create procedure sp_UpdateVecino(in NITUp Varchar(15), in DPIUp bigint(13), in nombresUp Varchar(100), in apellidosUp varchar(100), in direccionUp varchar(200), in municipalidadUp varchar(45), in codigoPostalUp int, in telefonoUp varchar(8))
		begin
			Update Vecinos 
			set DPI = DPIUp,
            nombres = nombresUp, 
            apellidos = apellidosUp, 
            municipalidad = municipalidadUp,
            direccion = direccionUp, 
            codigoPostal = codigoPostalUP, 
            telefono = telefonoUp
            where NIT = NITUp;
        end$$
Delimiter ;
-- call sp_UpdateVecino('123456789012378',7878787878787,'Carlos','Guillermo','Zona21','de la zona 21',382, 78787878);
-- call sp_ListarVecinos();




/* -------- Vehiculos ---------*/
-- agregar
Delimiter $$
	create procedure sp_AgregarVehiculo(in placa varchar(15), in marca varchar(45), in modelo varchar(45), in tipoDeVehiculo varchar(45), in NIT Varchar(15))
		begin
			insert into Vehiculos(placa, marca, modelo, tipoDeVehiculo, NIT)
				values (placa, marca, modelo, tipoDeVehiculo, NIT);
        end$$
delimiter ;
call sp_AgregarVehiculo('454545454545455','KIA','tsuru tuneado','compacto','123456789012345');
call sp_AgregarVehiculo('858585858585858','Chevrolet','tsuru tuneado','robusto','123456789012378');

-- listar 
Delimiter $$
	create procedure sp_ListarVehiculos()
		begin
			select placa, marca, modelo, tipoDeVehiculo, NIT
				from Vehiculos;
        end$$
Delimiter ;
call sp_ListarVehiculos();

-- Buscar por codigo
Delimiter $$
	create procedure sp_BuscarVehiculo(in placaBus varchar(15))
		begin
			select placa, marca, modelo, tipoDeVehiculo, NIT
				from Vehiculos where placa = placaBus;
        end$$
Delimiter ;
call sp_BuscarVehiculo('454545454545455');

-- Eliminar
Delimiter $$
	create procedure sp_EliminarVehiculo(in placaDelete varchar(15))
		begin
			Delete from Vehiculos
				where placa = placaDelete;
        end$$
Delimiter ;
-- call sp_EliminarVehiculo('454545454545455');
-- call sp_ListarVehiculos();

-- Editar
Delimiter $$
	create procedure sp_UpdateVehiculo (in placaUp varchar(15),in marcaUp varchar(45),in modeloUp varchar(45),in tipoDeVehiculoUp varchar(45),in NITUp varchar(15))
		begin
			update Vehiculos
				set
                marca = marcaUp,
                modelo = modeloUp,
                tipoDeVehiculo = tipoDeVehiculoUp, 
                NIT = NITUp
                where placa =  placaUp;
        end$$
Delimiter ;
-- call sp_UpdateVehiculo('858585858585858','Ferrari','Formula 1','tsuru tuneado','123456789012378');
-- call sp_ListarVehiculos();