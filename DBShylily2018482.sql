Drop database if exists DBShylily2018482;
Create Database DBShylily2018482;

use DBShylily2018482;

Create Table Pacientes(
	codigoPaciente int not null,
	nombresPaciente varchar(50) not null,
	apellidosPaciente varchar(50) not null,
	sexo char not null,
	fechaNacimiento date not null,
	direccionPaciente varchar(100) not null,
	telefonoPersonal varchar(8) not null,
	fechaPrimeraVisita date,
	primary key PK_codigoPaciente (codigoPaciente)
);

Create Table Especialidades(
	codigoEspecialidad int not null auto_increment,
	descripcion varchar(100) not null,
	primary key PK_codigoEspecialidad (codigoEspecialidad)
);

Create Table Medicamentos(
	codigoMedicamento int not null auto_increment,
	nombreMedicamento varchar (100) not null,
	primary key Pk_codigoMedicamento (codigoMedicamento)
);

Create Table Doctores(
	numeroColegiado int not null,
	nombresDoctor varchar(100) not null,
	apellidosDoctor varchar(100) not null,
	telefonoContacto varchar(8) not null,
	codigoEspecialidad int not null,
	primary key PK_numeroColegiado (numeroColegiado),
	constraint FK_Doctores_Especialidades foreign key (codigoEspecialidad) references Especialidades (codigoEspecialidad)
);

Create Table Recetas(
	codigoReceta int not null auto_increment,
	fechaReceta date not null,
	numeroColegiado int not null,
	primary key PK_codigoReceta (CodigoReceta),
	constraint FK_Receta_Doctores foreign key (numeroColegiado) references Doctores (numeroColegiado)
);

Create Table DetalleReceta(
	codigoDetalleReceta int not null auto_increment,
	dosis varchar(100) not null,
	codigoReceta int not null,
	codigoMedicamento int not null,
	primary key PK_codigoDetalleReceta (codigoDetalleReceta),
	constraint FK_DetalleReceta_Recetas foreign key (codigoReceta) references Recetas (codigoReceta),
	constraint FK_DetalleReceta_Medicamentos foreign key (codigoMedicamento) references Medicamentos (codigoMedicamento)
);

Create Table Citas(
	codigoCita int not null auto_increment,
	fechaCita date not null,
	horaCita time not null,
	tratamiento varchar(150),
	descripCondActual varchar(255) not null,
	codigoPaciente int not null,
	numeroColegiado int not null,
	primary key PK_codigoCita (codigoCita),
	constraint FK_Citas_Pacientes foreign key (codigoPaciente) references Pacientes (codigoPaciente),
	constraint FK_Citas_Doctores foreign key (numeroColegiado) references Doctores (numeroColegiado)
);

create table Usuario(
	codigoUsuario int not null auto_increment,
    nombreUsuario varchar(100) not null,
    apellidoUsuario varchar(100) not null,
    usuarioLogin varchar(50) not null,
    contrasena varchar(50) not null,
    primary key PK_codigoUsuario (codigoUsuario)
);

create table Login(
	usuarioMaster varchar(50) not null,
    passwordLogin varchar(50) not null,
    primary key PK_usuarioMaster (usuarioMaster)
);
-- ------------------------------------------------------------------------------------------------------------------------
-- ---------------------------------------- PROCEDIMENTOS ALMACENADOS -----------------------------------------------------

-- ---------------------------------------- Usuario --------------------------------
-- ---------------------------------------- AGREGAR USUARIO -------------------------------
delimiter $$
	create procedure sp_AgregarUsuario(in nombreUsuario varchar(100),in apellidoUsuario varchar(100),in usuarioLogin varchar(50),in contrasena varchar(50))
		begin
			Insert into Usuario (nombreUsuario, apellidoUsuario, usuarioLogin, contrasena) values (nombreUsuario, apellidoUsuario, usuarioLogin, contrasena);
        end$$
delimieter ;
-- ---------------------------------------- Listar Usuario
delimiter $$
	create procedure sp_ListarUsuarios()
		begin
			select codigoUsuario, nombreUsuario, apellidoUsuario, usuarioLogin, contrasena
            from Usuario;
        end $$
delimiter ;
call sp_AgregarUsuario('Fabian','Marroquin','fmarroquin','@123');
call sp_ListarUsuarios();

-- ---------------------------------------- PACIENTES ---------------------------------------------------------------------
-- ---------------------------------------- AGREGAR
Delimiter $$
	create procedure sp_AgregarPacientes (in codigoPaciente int,in nombresPaciente varchar(50),in apellidosPaciente varchar(50),in sexo char,in fechaNacimiento date,in direccionPaciente varchar(100),in telefonoPersonal varchar(8),in fechaPrimeraVisita date)
		Begin
			Insert into Pacientes (codigoPaciente, nombresPaciente, apellidosPaciente, sexo, fechaNacimiento, direccionPaciente, telefonoPersonal, fechaPrimeraVisita) 
			values (codigoPaciente, nombresPaciente, apellidosPaciente, upper(sexo), fechaNacimiento, direccionPaciente, telefonoPersonal, fechaPrimeraVisita);
		End$$
Delimiter ;
call sp_AgregarPacientes(2022,'Fabian','Marroquin','M','2005-01-20','Zona 3','12345678',now());
call sp_AgregarPacientes(2018482,'Angel','Chali','M','2005-01-20','Zona 15',42479250,now());
call sp_AgregarPacientes(2017362,'Carlos','Muralles','M','2004-09-30','Zona 21',51371087,now());
		-- ---------------------------------------- LISTAR
Delimiter $$
create procedure sp_ListarPacientes()
		begin
			select 
			codigoPaciente, nombresPaciente, apellidosPaciente, sexo, fechaNacimiento, direccionPaciente, telefonoPersonal, fechaPrimeraVisita from Pacientes;
		End$$
Delimiter ;
call sp_ListarPacientes();
-- ---------------------------------------- BUSCAR
Delimiter $$
	Create procedure sp_BuscarPacientes(in codigoPacienteBus int)
		Begin
			select 
			codigoPaciente, nombresPaciente, apellidosPaciente, sexo, fechaNacimiento, direccionPaciente, telefonoPersonal, fechaPrimeraVisita from Pacientes
			where codigoPaciente = codigoPacienteBus; 
		end$$
delimiter ;
call sp_BuscarPacientes(2022);
-- ---------------------------------------- ELIMINAR
Delimiter $$
	create procedure sp_EliminarPacientes(in codigoPacienteElim int)
		Begin
			delete from Pacientes
			where codigoPaciente = codigoPacienteElim;
		End$$
Delimiter ;
call sp_EliminarPacientes(2022);
call sp_ListarPacientes();
-- ---------------------------------------- UPDATE
Delimiter $$
	create procedure sp_UpdatePacientes (in codigoPacienteUp int,in nombresPacienteUp varchar(50),in apellidosPacienteUp varchar(50),in sexoUp char,in fechaNacimientoUp date,in direccionPacienteUp varchar(100), in telefonoPersonalUp varchar(8),in fechaPrimeraVisitaUp date)
		Begin
			Update Pacientes
			set nombresPaciente = nombresPacienteUp, 
			apellidosPaciente = apellidosPacienteUp, 
			sexo = sexoUp, 
			fechaNacimiento = fechaNacimientoUp, 
			direccionPaciente = direccionPacienteUp, 
			telefonoPersonal = telefonoPersonalUp,
			fechaPrimeraVisita = fechaPrimeraVisitaUP
			where codigoPaciente = codigoPacienteUp;
		End$$
Delimiter ;
call sp_UpdatePacientes(2018482,'Carlos','Chali','m','2004-01-20','42479250','Zona 1',now());
call sp_ListarPacientes();


-- ---------------------------------------- ESPECIALIDADES ---------------------------------------------------------------------
-- ---------------------------------------- AGREGAR
Delimiter $$
	Create procedure sp_AgregarEspecialidad(in descripcion varchar(100))
		Begin
			insert into Especialidades (descripcion) values (descripcion);
		End $$
Delimiter ;
call sp_AgregarEspecialidad('Endodoncia');
call sp_AgregarEspecialidad('Ortodoncia');
call sp_AgregarEspecialidad('Prostodoncia');
-- ---------------------------------------- LISTAR
Delimiter $$
	create procedure sp_ListarEspecialidades()
		Begin
			select
			codigoEspecialidad,descripcion
			from Especialidades;
		End$$
Delimiter ;
call sp_ListarEspecialidades();
-- ---------------------------------------- BUSCAR
Delimiter $$
	create procedure sp_BuscarEspecialidad (in codigoEspecialidadBus int)
		Begin
			select 
			codigoEspecialidad,descripcion from Especialidades where codigoEspecialidadBus = codigoEspecialidad;
		End$$
Delimiter ;
call sp_BuscarEspecialidad(1);
-- ---------------------------------------- ELIMINAR
Delimiter $$
	create procedure sp_EliminarEspecialidad(in codigoEspecialidadElim int)
		Begin
			delete from Especialidades 
			where codigoEspecialidad = codigoEspecialidadElim;
		End$$
Delimiter ;
call sp_EliminarEspecialidad(1);
call sp_ListarEspecialidades();

-- ---------------------------------------- UPDATE
Delimiter $$
	create procedure sp_UpdateEspecialidad (in codigoEspecialidadUp int,in descripcionUp varchar(100))
		Begin
			Update Especialidades
			set descripcion = descripcionUp 
			where codigoEspecialidad = codigoEspecialidadUp;
		End$$
Delimiter ;
call sp_UpdateEspecialidad(2,"Ortodoncia");
call sp_ListarEspecialidades();

-- ---------------------------------------- MEDICAMENTOS ---------------------------------------------------------------------
-- ---------------------------------------- AGREGAR
Delimiter $$
	create procedure sp_agregarMedicamento(in nombreMedicamento varchar(100))
		Begin
			insert into Medicamentos(nombreMedicamento) values (nombreMedicamento);
		End$$
Delimiter ;
call sp_agregarMedicamento('Penicilina');
call sp_agregarMedicamento('Anestecia');
call sp_agregarMedicamento('Naproxeno');
-- ---------------------------------------- LISTAR
Delimiter $$
	create procedure sp_listarMedicamentos()
		Begin
			select 
            codigoMedicamento, nombreMedicamento
            from Medicamentos;
        End$$
Delimiter ;
call sp_listarMedicamentos();
-- ---------------------------------------- BUSCAR
Delimiter $$
	create procedure sp_buscarMedicamento(in codigoMedicamentoBus int)
		Begin
			select 
			codigoMedicamento, nombreMedicamento
			from Medicamentos where codigoMedicamentoBus = codigoMedicamento;
		End$$
Delimiter ;
call sp_buscarMedicamento(1);
-- ---------------------------------------- ELIMINAR
Delimiter $$
	create procedure sp_eliminarMedicamento(in codigoMedicamentoElim int)
		begin
			delete from Medicamentos where codigoMedicamentoElim = codigoMedicamento;
		End$$
Delimiter ;
call sp_eliminarMedicamento(1);
call sp_listarMedicamentos();
-- ---------------------------------------- UPDATE
Delimiter $$
	create procedure sp_updateMedicamento(in codigoMedicamentoUp int, in nombreMedicamentoUp varchar(100))
		begin
			Update Medicamentos
				set nombreMedicamento = nombreMedicamentoUp
				where codigoMedicamentoUp = codigoMedicamento;
		end$$
Delimiter ;
call sp_updateMedicamento(2,'Ibuprofeno');
call sp_listarMedicamentos();

-- ---------------------------------------- DOCTORES ---------------------------------------------------------------------
-- ---------------------------------------- AGREGAR
drop procedure sp_agregarDoctor
Delimiter $$
	create procedure sp_agregarDoctor(in numeroColegiado int,in nombresDoctor varchar(100),in apellidosDoctor varchar(100),in telefonoContacto varchar(8),in codigoEspecialidad int)
		begin
			insert into Doctores(numeroColegiado, nombresDoctor, apellidosDoctor, telefonoContacto, codigoEspecialidad) values (numeroColegiado, nombresDoctor, apellidosDoctor, telefonoContacto, codigoEspecialidad);
		end$$
Delimiter ;
call sp_agregarDoctor(20234,'Fabian','Marroquin','42479250',2);
call sp_agregarDoctor(20225,'Angel','Chali','49906317',2);
call sp_agregarDoctor(2018362,'Samuel','Rivera','51371087',3);
-- ---------------------------------------- LISTAR
Delimiter $$
	create procedure sp_listarDoctores()
		begin
			select
			numeroColegiado, nombresDoctor, apellidosDoctor, telefonoContacto, codigoEspecialidad
			from Doctores;
		end$$
Delimiter ;
call sp_listarDoctores();
-- ---------------------------------------- BUSCAR
Delimiter $$
	create procedure sp_buscarDoctor(in numeroColegiadoBus int)
		begin
			select 
			numeroColegiado, nombresDoctor, apellidosDoctor, telefonoContacto, codigoEspecialidad from Doctores where numeroColegiadoBus = numeroColegiado;
		end$$
Delimiter ;
call sp_buscarDoctor(20234);
-- ---------------------------------------- ELIMINAR
Delimiter $$
	create procedure sp_eliminarDoctor(in numeroColegiadoElim int)
		begin
			delete from Doctores where numeroColegiadoElim = numeroColegiado;
		end$$
Delimiter ;
call sp_eliminarDoctor(20234);
call sp_listarDoctores();
-- ---------------------------------------- UPDATE
Delimiter $$
	create procedure sp_updateDoctor(in numeroColegiadoUp int, in nombresDoctorUpd varchar(100), in apellidosDoctorUpd varchar(100), in telefonoContactoUpd varchar(8),in codigoEspecialidadUpd int)
		begin
			Update Doctores 
				set nombresDoctor = nombresDoctorUpd,
				apellidosDoctor = apellidosDoctorUpd,
				telefonoContacto = telefonoContactoUpd,
				codigoEspecialidad = codigoEspecialidadUpd
				where numeroColegiadoUp = numeroColegiado;
		end$$
Delimiter ;
call sp_updateDoctor(20225,'Samuel','Chali','42479250',3);
call sp_listarDoctores();
-- ---------------------------------------- RECETAS ---------------------------------------------------------------------
-- ---------------------------------------- AGREGAR
Delimiter $$
	create procedure sp_agregarReceta(in fechaReceta date, in numeroColegiado int)
		begin
			insert into Recetas(fechaReceta,numeroColegiado) values (fechaReceta,numeroColegiado);
		end$$
Delimiter ;
call sp_agregarReceta(now(),20225);
call sp_agregarReceta(now(),2018362);
call sp_agregarReceta('2022-04-25',20225);

-- ---------------------------------------- LISTAR
Delimiter $$
	create procedure sp_listarRecetas()
		begin
			select codigoReceta, fechaReceta, numeroColegiado from recetas;
		end$$
Delimiter ;
call sp_listarRecetas();
-- ---------------------------------------- BUSCAR
Delimiter $$
	create procedure sp_buscarReceta(in codigoRecetaBus int)
		begin
			select
			codigoReceta, fechaReceta, numeroColegiado from Recetas where codigoRecetaBus = codigoReceta;
		end$$
Delimiter ;
call sp_buscarReceta(1);
-- ---------------------------------------- ELIMINAR
Delimiter $$
	create procedure sp_eliminarReceta(in codigoRecetaElim int)
		begin
			delete from Recetas where codigoRecetaElim = codigoReceta;
        end$$
Delimiter ;
call sp_eliminarReceta(1);
call sp_listarRecetas();
-- ---------------------------------------- UPDATE
Delimiter $$
	create procedure sp_actualizarReceta(in codigoRecetaUp int, in fechaRecetaUp date, in numeroColegiadoUp int)
		begin
			update Recetas
            set fechaReceta = fechaRecetaUp,
            numeroColegiado = numeroColegiadoUp
            where codigoRecetaUp = codigoReceta;
        end$$
Delimiter ;
call sp_actualizarReceta(2,'2022-05-24',20225);
call sp_listarRecetas();

-- ---------------------------------------- DETALLE RECETA ---------------------------------------------------------------------
-- ---------------------------------------- AGREGAR
Delimiter $$
	create procedure sp_agregarDetalleReceta(in dosis varchar(100), in codigoReceta int, in codigoMedicamento int)
		begin
			insert into detalleReceta(dosis, codigoReceta, codigoMedicamento) values(dosis, codigoReceta, codigoMedicamento);
        end$$
Delimiter ;
call sp_agregarDetalleReceta('cada 8 horas',2,2);
call sp_agregarDetalleReceta('cada 12 hroas',3,3)
-- ---------------------------------------- LISTAR
Delimiter $$
	create procedure sp_listarDetalleReceta()
		begin
			select 
            codigoDetalleReceta, dosis, codigoReceta, codigoMedicamento from detalleReceta;
        end$$
Delimiter ;
call sp_listarDetalleReceta();
-- ---------------------------------------- BUSCAR
Delimiter $$
	create procedure sp_buscarDetalleReceta(in codigoDetalleRecetaBus int)
		begin
			select 
            codigoDetalleReceta, dosis, codigoReceta, codigoMedicamento from detalleReceta where codigoDetalleRecetaBus = codigoDetalleReceta;
        end$$
Delimiter ;
call sp_buscarDetalleReceta(1);
-- ---------------------------------------- ELIMINAR
Delimiter $$
	create procedure sp_eliminarDetalleReceta(in codigoDetalleRecetaElim int)
		begin
			delete from detallereceta where codigoDetalleRecetaElim = codigoDetalleReceta;
        end$$
Delimiter ;
call sp_eliminarDetalleReceta(1);
call sp_listarDetalleReceta();
-- ---------------------------------------- UPDATE
Delimiter $$
	create procedure sp_updateDetalleReceta(in codigoDetalleRecetaUp int,in dosisUp varchar(100),in codigoRecetaUp int,in codigoMedicamentoUp int)
		begin
			update detalleReceta
            set dosis = dosisUp, 
            codigoReceta = codigoRecetaUp, 
            codigoMedicamento = codigoMedicamentoUp where codigoDetalleRecetaUp = codigoDetalleReceta;
        end$$
Delimiter ;
call sp_updateDetalleReceta(2,'cada 16 horas',3,3);
call sp_listarDetalleReceta();

-- ---------------------------------------- CITAS --------------------------------------------------------------------- 
-- ---------------------------------------- AGREGAR
Delimiter $$
	create procedure sp_agregarCita(in fechaCita date,in horaCita time,in tratamiento varchar(100),in descripCondActual varchar(255),in codigoPaciente int,in numeroColegiado int)
		begin
			insert into Citas(fechaCita, tratamiento, horaCita, descripCondActual, codigoPaciente, numeroColegiado) values(fechaCita, tratamiento, horaCita, descripCondActual, codigoPaciente, numeroColegiado);
        end$$
Delimiter ;
call sp_agregarCita('2022-05-28',now(),'Tratamiento de canales','Dientes con caries',2018482,2018362);
call sp_agregarCita('2022-05-26',now(),'Blanqueamiento','Dientes muy amarillos',2017362,20225);
-- ---------------------------------------- LISTAR
Delimiter $$
	create procedure sp_listarCitas()
		begin
			select 
            codigoCita, fechaCita,  horaCita, tratamiento, descripCondActual, codigoPaciente, numeroColegiado from Citas;
        end$$
Delimiter ;
call sp_listarCitas();
-- ---------------------------------------- BUSCAR
Delimiter $$
	create procedure sp_buscarCita(in codigoCitaBuscar int)
		begin
			select codigoCita, fechaCita, horaCita, tratamiento, descripCondActual, codigoPaciente, numeroColegiado
			from Citas where codigoCitaBuscar = codigoCita;
        end$$
Delimiter ;
call sp_buscarCita(1);
-- ---------------------------------------- ELIMINAR
Delimiter $$
	create procedure sp_eliminarCita(in codigoCitaElim int)
		begin
			delete from citas where codigoCitaElim = codigoCita;
		end$$
Delimiter ;
call sp_eliminarCita(1);
call sp_listarCitas();
-- ---------------------------------------- UPDATE
Delimiter $$
	create procedure sp_updateCita(in codigoCitaUp int,in fechaCitaUp date,in horaCitaUp time,in tratamientoUp varchar(250),in descripCondActualUp varchar(255),in codigoPacienteUp int,in numeroColegiadoUp int)
		begin
			update Citas
            set fechaCita = fechaCitaUp, 
            horaCita = horaCitaUp, 
            descripCondActual = descripCondActualUp, 
            tratamiento = tratamientoUp, 
            codigoPaciente = codigoPacienteUp, 
            numeroColegiado = numeroColegiadoUp 
            where codigoCitaUp = codigoCita;
        end$$
Delimiter ;
call sp_updateCita(2,'2022-05-24',now(),'Implante Dental','Sin muelas',2017362,20225);
call sp_listarCitas();
