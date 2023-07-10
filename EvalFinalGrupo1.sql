/* 
Nombre: Fabian Angel Samuel Marroquin Chali
carne: 2018482
codigo Tecnico: IN5AV
Fecha: 20/7/2022
Hora: 13:00 PM
 */
 
Drop database if exists DBKinalMall;
Create database DBKinalMall;

use DBKinalMall;

Create table Departamentos(
	codigoDepartamento int auto_increment not null,
    nombreDepartamento varchar(45) not null,
    primary key PK_codigoDepartamento (codigoDepartamento)
);

Create table Cargos(
	codigoCargo int auto_increment not null,
    nombreCargo varchar(45) not null,
    primary key PK_codigoCargo (codigoCargo)
);

Create table Horarios(
	codigoHorario int auto_increment not null,
    horarioEntrada varchar(5) not null,
    horarioSalida varchar(5) not null,
    lunes boolean,
    martes boolean,
    miercoles boolean,
    jueves boolean,
    viernes boolean,
    primary key PK_codigoHorario (codigoHorario)
);

Create table Administracion(
	codigoAdministracion int auto_increment not null,
    direccion varchar(45) not null,
    telefono varchar(8) not null,
    primary key PK_codigoAdministracion (codigoAdministracion)
);

Create table TipoCliente(
	codigoTipoCliente int auto_increment not null,
    descripcion varchar(45) not null,
    primary key PK_codigoTipoCliente (codigoTipoCliente)
);

Create table Locales(
	codigoLocal int auto_increment not null,
    saldoFavor double(11,2) default 0.0,
    saldoContra double(11,2) default 0.0,
    mesesPendientes int default 0,
    disponibilidad boolean not null,
    valorLocal double(11,2) not null,
    valorAdministracion double(11,2) not null,
    primary key PK_codigoLocal (codigoLocal)
);

Create table Empleados(
	codigoEmpleado int auto_increment not null,
    nombreEmpleado varchar(45) not null,
    apellidoEmpleado varchar(45) not null,
    correoElectronico varchar(45) not null,
    telefonoEmpleado varchar(8) not null,
    fechaContratacion date not null,
    sueldo double(11,2) not null,
    codigoDepartamento int not null,
    codigoCargo int not null,
    codigoHorario int not null,
    codigoAdministracion int not null,
    primary key PK_codigoEmpleado (codigoEmpleado),
    constraint FK_Empleados_Departamentos foreign key (codigoDepartamento)
		references Departamentos (codigoDepartamento),
    constraint FK_Empleados_Cargos foreign key (codigoCargo)
		references Cargos(codigoCargo),
	constraint FK_Empleados_Horarios foreign key (codigoHorario)
		references Horarios(codigoHorario),
	constraint FK_Empleados_Administracion foreign key (codigoAdministracion)
		references Administracion(codigoAdministracion)
);

Create table Proveedores(
	codigoProveedor int auto_increment not null,
    NITProveedor varchar(45) not null,
    servicioPrestado varchar(45) not null,
    telefonoProveedor varchar(8) not null,
    direccionProveedor varchar(45) not null,
    saldoFavor double(11,2) not null,
    saldoContra double(11,2) not null,
    codigoAdministracion int not null,
    primary key PK_codigoProveedor (codigoProveedor),
    constraint FK_Proveedores_Administracion foreign key (codigoAdministracion)
		references Administracion(codigoAdministracion)
);

Create table CuentasPorPagar(
	codigoCuentasPorPagar int auto_increment not null,
    numeroFactura varchar(45) not null,
    fechaLimitePago date not null,
    estadoPago varchar(45) not null,
    valorNetoPago double(11,2) not null,
    codigoAdministracion int not null,
    codigoProveedor int not null,
    primary key PK_codigoCuentasPorPagar (codigoCuentasPorPagar),
    constraint FK_CuentasPorPagar_Administracion foreign key (codigoAdministracion)
		references Administracion (codigoAdministracion),
	constraint FK_CuentasPorPagar_Proveedores foreign key (codigoProveedor)
		references Proveedores (codigoProveedor)
);

Create table Clientes(
	codigoCliente int auto_increment not null,
    nombresCliente varchar(45) not null,
    apellidosCliente varchar(45) not null,
    telefonoCliente varchar(8) not null,
    direccionCliente varchar(60) not null,
    email varchar(45) not null,
    codigoLocal int not null,    
    codigoAdministracion int not null,
    codigoTipoCliente int not null,
    primary key PK_codigoCliente (codigoCliente),
    constraint FK_Clientes_Locales foreign key (codigoLocal)
		references Locales (codigoLocal),	
	constraint FK_Clientes_Administracion foreign key (codigoAdministracion)
		references Administracion (codigoAdministracion),
	constraint FK_Clientes_TipoCliente foreign key (codigoTipoCliente)
		references TipoCliente (codigoTipoCliente) on UPDATE Cascade
);

Create table CuentasPorCobrar(
	codigoCuentaPorCobrar int auto_increment not null,
    numeroFactura varchar(45) not null,
    anio year(4) not null,
    mes int(2) not null,
    valorNetoPago double(11,2) not null,
    estadoPago varchar(45) not null,
    codigoAdministracion int not null,
    codigoCliente int not null,
    codigoLocal int not null,
    primary key PK_codigoCuentaPorCobrar (codigoCuentaPorCobrar),
    constraint FK_CuentasPorCobrar_Administracion foreign key (codigoAdministracion)
		references Administracion (codigoAdministracion),
	constraint FK_CuentasPorCobrar_Clientes foreign key (codigoCliente) 
		references Clientes (codigoCliente),
	constraint FK_CuentasPorCobrar_Locales foreign key (codigoLocal)
		references Locales (codigoLocal)
);

-- ------------------------------------------------------------
-- Procedimientos almacenados Administración --
-- Agregar Administración
Delimiter $$
	Create procedure sp_AgregarAdministracion (in direccion varchar(45), in telefono varchar(8))
		Begin
			Insert into Administracion (direccion, telefono) 
				values (direccion, telefono);
        End$$
Delimiter ;


Delimiter $$
	Create procedure sp_ListarAdministracion()
		Begin
			Select
				A.codigoAdministracion, 
                A.direccion, 
                A.telefono 
                from Administracion A;        
		End $$
Delimiter ;

Delimiter $$
	Create procedure sp_BuscarAdministracion(in codAdmin int)
		Begin
			Select
				A.codigoAdministracion, 
                A.direccion, 
                A.telefono 
                from Administracion A where codAdmin = codigoAdministracion;        
		End $$
Delimiter ;

Delimiter $$
	Create procedure sp_EliminarAdministracion(in codAdmin int)
		Begin
			delete from Administracion
				where codAdmin = codigoAdministracion;
        End $$
Delimiter ;


Delimiter $$
	Create procedure sp_EditarAdministracion(in codAdmin int, in dir varchar(45), in tel varchar(8))
		Begin
			Update Administracion
				set
					direccion = dir,
                    telefono = tel
                    where codigoAdministracion = codAdmin;                
        End $$
Delimiter ;

call sp_AgregarAdministracion('Mixco, Guatemala', 58741236);
call sp_AgregarAdministracion('6 call A 12-50 zona 1','24356914');
call sp_AgregarAdministracion('Antigua Guatemala','78325698');



-- Procedimientos almacenados Tipo Cliente --
Delimiter $$
	Create procedure sp_AgregarTipoCliente (in descripcion varchar(45))
		Begin
			Insert into TipoCliente (descripcion) 
				values (descripcion);
        End$$
Delimiter ;


Delimiter $$
	Create procedure sp_ListarTipoCliente()
		Begin
			Select
				TC.codigoTipoCliente, 
                TC.descripcion                
                from TipoCliente TC;        
		End $$
Delimiter ;

Delimiter $$
	Create procedure sp_BuscarTipoCliente(in codTiC int)
		Begin
			Select
				TC.codigoTipoCliente, 
                TC.descripcion                
                from TipoCliente TC where codTiC = codigoTipoCliente;        
		End $$
Delimiter ;

Delimiter $$
	Create procedure sp_EliminarTipoCliente(in codTiC int)
		Begin
			delete from TipoCliente
				where codTiC = codigoTipoCliente;
        End $$
Delimiter ;


Delimiter $$
	Create procedure sp_EditarTipoCliente(in codTiC int, in descrip varchar(45))
		Begin
			Update TipoCliente
				set
					descripcion = descrip                   
                    where codigoTipoCliente = codTiC;                
        End $$
Delimiter ;

call sp_AgregarTipoCliente ('Frecuente');
call sp_AgregarTipoCliente ('Nuevo');


-- Procedimientos almacenados Locales --
Delimiter $$
	Create procedure sp_AgregarLocal(in disponibilidad boolean, in valorLocal double(11,2), in valorAdministracion double(11,2))
		Begin
			Insert into Locales (disponibilidad, valorLocal, valorAdministracion) 
				values (disponibilidad, valorLocal, valorAdministracion);
        End$$
Delimiter ;
 call sp_AgregarLocal(true, 5000.50,2000.00);
 call sp_AgregarLocal(false, 50,25.00);
Delimiter $$
	Create procedure sp_ListarLocales()
		Begin
			Select
				L.codigoLocal, 
                L.saldoFavor, 
                L.saldoContra, 
                L.mesesPendientes, 
                L.disponibilidad, 
                L.valorLocal, 
                L.valorAdministracion          
                from Locales L;        
		End $$
Delimiter ;

call sp_ListarLocales();

Delimiter $$
	Create procedure sp_BuscarLocal(in codLoc int)
		Begin
			Select
				L.codigoLocal, 
                L.saldoFavor, 
                L.saldoContra, 
                L.mesesPendientes, 
                L.disponibilidad, 
                L.valorLocal, 
                L.valorAdministracion                
                from Locales L where codLoc = codigoLocal;        
		End $$
Delimiter ;

Delimiter $$
	Create procedure sp_EliminarLocal(in codLoc int)
		Begin
			delete from Locales
				where codLoc = codigoLocal;
        End $$
Delimiter ;


Delimiter $$
	Create procedure sp_EditarLocal(in codigTic int, in disp boolean, in valLocal double(11,2), in valorAdmin double(11,2))
		Begin
			Update Locales
				set
					disponibilidad = disp,
                    valorLocal = valLocal,
                    valorAdministracion = valorAdmin
                    where codigoTipoCliente = codTiC;                
        End $$
Delimiter ;


call sp_ListarLocales();

-- Procedimientos almacenados Clientes --

Delimiter $$
	Create procedure sp_AgregarCliente(in nombresCliente varchar(45), in apellidosCliente varchar(45), 
			in telefonoCliente varchar(8), in direccionCliente varchar(60), in email varchar(45), 
            in codigoLocal int, in codigoAdministracion int, in codigoTipoCliente int)
		Begin
			Insert into Clientes (nombresCliente, apellidosCliente, telefonoCliente, direccionCliente, 
					email, codigoLocal, codigoAdministracion, codigoTipoCliente) 
				values (nombresCliente, apellidosCliente, telefonoCliente, direccionCliente, 
					email, codigoLocal, codigoAdministracion ,codigoTipoCliente );
        End$$
Delimiter ;

call sp_AgregarCliente('Pedro Manuel', 'Armas Chang', '12345678', 'Mixco, Guatemala', 'pedroarmas@kinal.org.gt',1,1,1);


Delimiter $$
	Create procedure sp_ListarClientes()
		Begin
			Select
				C.codigoCliente, 
                C.nombresCliente, 
                C.apellidosCliente, 
                C.telefonoCliente, 
                C.direccionCliente, 
                C.email, 
                C.codigoLocal,                  
                C.codigoAdministracion,
                C.codigoTipoCliente
                from Clientes C;        
		End $$
Delimiter ;

Delimiter $$
	Create procedure sp_BuscarCliente(in codCli int)
		Begin
			Select
				C.codigoCliente, 
                C.nombresCliente, 
                C.apellidosCliente, 
                C.telefonoCliente, 
                C.direccionCliente, 
                C.email, 
                C.codigoLocal,                 
                C.codigoAdministracion,
                C.codigoTipoCliente 
                from Clientes C where codCli = codigoCliente;        
		End $$
Delimiter ;

Delimiter $$
	Create procedure sp_EliminarCliente(in codCli int)
		Begin
			delete from Clientes
				where codCli = codigoCliente;
        End $$
Delimiter ;


Delimiter $$
	Create procedure sp_EditarCliente(in codCli int, in nomCli varchar(45), in apeCli varchar(45), in telCli varchar(8),
						in dirCli varchar(60), in correo varchar(45))
		Begin
			Update Clientes
				set
					nombresCliente = nomCli, 
                    apellidosCliente = apeCli, 
                    telefonoCliente = telCli, 
                    direccionCliente = dirCli, 
                    email = correo
                    where codigoTipoCliente = codCli;                
        End $$
Delimiter ;

call sp_ListarClientes();

-- Procedimientos almacenados Horarios --

Delimiter $$
	Create procedure sp_AgregarHorario(in horarioEntrada varchar(5), in horarioSalida varchar(5), in lunes boolean, in martes boolean, 
		in miercoles boolean, in jueves boolean, in viernes boolean)
		Begin
			Insert into Horarios (horarioEntrada, horarioSalida, lunes, martes, miercoles, jueves, viernes) 
				values (horarioEntrada, horarioSalida, lunes, martes, miercoles, jueves, viernes);
        End$$
Delimiter ;


-- Procedimientos almacenados Departamentos --
-- Agregar Departamento ---
Delimiter $$
	Create procedure sp_AgregarDepartamento (in nombreDepartamento varchar(45))
		Begin
			Insert into Departamentos (nombreDepartamento) 
				values (nombreDepartamento);
        End$$
Delimiter ;

call sp_ListarAdministracion();
call sp_ListarLocales();
call sp_ListarTipoCliente();
call sp_ListarClientes();


call sp_AgregarCliente('Carlos Rodolfo', 'Morales García', '11111111', 'Zona 1, Guatemala', 'cmorales@gmail.com',2,3,2);

Select * from Clientes, Locales, Administracion, TipoCliente;

select now();





call sp_BuscarAdministracion(1);
call sp_BuscarLocal(1);

call sp_ListarClientes();

call sp_ListarLocales();

-- Procedimiento almacenado para calcular el saldo líquido del local X

Delimiter $$
Create Procedure sp_CalculoLiquido (in codLocal int)
	Begin
		
        select saldoFavor - saldoContra From Locales where codigoLocal = codLocal;
    
    End$$
Delimiter ;



call sp_CalculoLiquido(1);


-- Procedimientos almacenados Proveedores --

Delimiter $$
	Create procedure sp_AgregarProveedor(in NITProveedor varchar(45),in servicioPrestado varchar(45), in telefonoProveedor varchar(8), 
		in direccionProveedor varchar(45), in saldoFavor double(11,2), in saldoContra double(11,2),in codigoAdministracion int)
		Begin
			Insert into Proveedores(NITProveedor, servicioPrestado, telefonoProveedor, direccionProveedor, saldoFavor, saldoContra, codigoAdministracion) 
				values (NITProveedor, servicioPrestado, telefonoProveedor, direccionProveedor, saldoFavor, saldoContra, codigoAdministracion);
        End$$
Delimiter ;

call sp_AgregarProveedor('4445050-8','Venta de bolsas', '12345678', 'Mixco', 0.00, 50.00, 1);

Delimiter $$
	Create procedure sp_ListarProveedores()
		Begin
			Select
				P.codigoProveedor, 
                P.NITProveedor, 
                P.servicioPrestado, 
                P.telefonoProveedor, 
                P.direccionProveedor, 
                P.saldoFavor, 
                P.saldoContra, 
                P.codigoAdministracion
                from Proveedores P;        
		End $$
Delimiter ;

Delimiter $$
	Create procedure sp_BuscarProveedor(in codProv int)
		Begin
			Select
				P.codigoProveedor, 
                P.NITProveedor, 
                P.servicioPrestado, 
                P.telefonoProveedor, 
                P.direccionProveedor, 
                P.saldoFavor, 
                P.saldoContra, 
                P.codigoAdministracion
                from Proveedores P where codProv = codigoProveedor;        
		End $$
Delimiter ;

Delimiter $$
	Create procedure sp_EliminarProveedor(in codProv int)
		Begin
			delete from Proveedor
				where codProv = coigoProveedor;
        End $$
Delimiter ;


Delimiter $$
	Create procedure sp_EditarProveedor(in codProv int, in NITPro varchar(45), in serPre varchar(45),
		in telPro varchar(8), in dirPro varchar(45), in salFa double(11,2), in salCo double(11,2))
		Begin
			Update Proveedor
				set
					NITProveedor = NITPro, 
                    servicioPrestado = serPre, 
                    telefonoProveedor = telPro, 
                    direccionProveedor = dirPro, 
                    saldoFavor = salFa, 
                    saldoContra = salCo
                    where codigoProveedor = codProv;                
        End $$
Delimiter ;


-- Procedimientos almacenados Cuentas por Pagar --

Delimiter $$
	Create procedure sp_AgregarCuentaPorPagar(in numeroFactura varchar(45), in fechaLimitePago date, in estadoPago varchar(45), 
		in valorNetoPago double(11,2), in codigoAdministracion int, in codigoProveedor int)
		Begin
			Insert into CuentasPorPagar(numeroFactura, fechaLimitePago, estadoPago, valorNetoPago, codigoAdministracion, codigoProveedor) 
				values (numeroFactura, fechaLimitePago, estadoPago, valorNetoPago, codigoAdministracion, codigoProveedor);
        End$$
Delimiter ;



Delimiter $$
	Create procedure sp_ListarCuentasPorPagar()
		Begin
			Select
				CP.codigoCuentasPorPagar, 
                CP.numeroFactura, 
                CP.fechaLimitePago, 
                CP.estadoPago, 
                CP.valorNetoPago, 
                CP.codigoAdministracion, 
                CP.codigoProveedor
                from CuentasPorPagar CP;        
		End $$
Delimiter ;

Delimiter $$
	Create procedure sp_BuscarCuentaPorPagar(in codCP int)
		Begin
			Select
				CP.codigoCuentasPorPagar, 
                CP.numeroFactura, 
                CP.fechaLimitePago, 
                CP.estadoPago, 
                CP.valorNetoPago, 
                CP.codigoAdministracion, 
                CP.codigoProveedor
                from CuentasPorPagar CP where codCP = codigoCuentasPorPagar;        
		End $$
Delimiter ;

Delimiter $$
	Create procedure sp_EliminarCuentaPorPagar(in codCP int)
		Begin
			delete from CuentasPorPagar
				where codCP = coigoCuentaPorPagar;
        End $$
Delimiter ;


Delimiter $$
	Create procedure sp_EditarCuentaPorPagar(in codCP int, in numFac varchar(45), in feLim date, in estPa varchar(45),
			in valNePa double(11,2))
		Begin
			Update CuentasPorPagar
				set
					numeroFactura = numFac,  
                    fechaLimitePago = feLim, 
                    estadoPago = estPa, 
                    valorNetoPago = valNePa                     
                    where codigoCuentaPorPagar = codCP;                
        End $$
Delimiter ;




call sp_ListarProveedores();
call sp_ListarCuentasPorPagar();

call sp_ListarLocales();



ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password By'admin';



call sp_BuscarAdministracion(3);

call sp_ListarAdministracion();

select * from TipoCliente TC right join Clientes C on
	TC.codigotipoCliente = C.codigoTipoCliente where TC.codigoTipoCliente = C.codigoTipoCliente;

select * from tipoCliente;



Create table Usuario(
	codigoUsuario int not null auto_increment,
    nombreUsuario varchar(100) not null,
    apellidoUsuario varchar(100) not null,
    usuarioLogin varchar(50) not null,
    contrasena varchar(50) not null,
    primary key PK_codigoUsuario (codigoUsuario)
);


-- ------------------------ PROCEDIMIENTO ALMACENADO AGREGAR USUARIO -----------------------------------------------------
Delimiter $$
	create procedure sp_AgregarUsuario(in nombreUsuario varchar(100),in apellidoUsuario varchar(100),in usuarioLogin varchar(100),in contrasena varchar(100))
    begin
		insert into Usuario (nombreUsuario, apellidoUsuario, usuarioLogin, contrasena) values(nombreUsuario, apellidoUsuario, usuarioLogin, contrasena);
    end$$
Delimiter ;


Delimiter $$
	Create procedure sp_ListarUsuarios()
		Begin
			Select
				U.codigoUsuario,
                U.nombreUsuario,
                U.apellidoUsuario,
                U.usuarioLogin,
                U.contrasena
            From Usuario U;
		End$$
Delimiter ;



call sp_AgregarUsuario('Pedro','Armas','parmas','12345');
call sp_AgregarUsuario('Fabian','Marroquin','fmarroquin','@123');
call sp_ListarUsuarios();

select * from Usuario;


Create table Login(
	usuarioMaster varchar(50) not null,
    passwordLogin varchar(50) not null,
    primary key PK_usuarioMaster (usuarioMaster)
);





