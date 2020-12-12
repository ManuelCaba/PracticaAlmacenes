EXECUTE sys.sp_addmessage @msgnum = 50001, @severity = 16, @msgtext = N'There is not enough space in the warehouse', @lang = 'us_english', @replace = 'replace';
EXECUTE sys.sp_addmessage @msgnum = 50001, @severity = 16, @msgtext = N'No hay suficiente espacion en el almac�n', @lang = 'spanish', @replace = 'replace';

GO
CREATE OR ALTER TRIGGER EspacioAlmacen ON Asignaciones AFTER INSERT AS
BEGIN
		IF EXISTS(
			SELECT A.ID AS IDAlmacen, A.Capacidad, Sum(E.NumeroContenedores) AS Ocupado, A.Capacidad - Sum(E.NumeroContenedores) AS disponible From Almacenes AS A 
			Inner Join Asignaciones As Ag ON A.ID = Ag.IDAlmacen
			Inner Join Envios AS E ON Ag.IDEnvio = E.ID
			Group By A.ID, A.Capacidad
			HAVING A.Capacidad - Sum(E.NumeroContenedores) < 0)
		BEGIN
			DECLARE @ErrorEspacioAlmacen NVarchar(255) = FormatMessage(50001);
			THROW 50001, @ErrorEspacioAlmacen ,1
			ROLLBACK
		END
END

GO
CREATE OR ALTER TRIGGER FechaAsignacion ON Asignaciones AFTER INSERT AS
BEGIN
		SELECT * FROM inserted
		UPDATE Envios
		SET FechaAsignacion = GETDATE()
		WHERE ID IN (SELECT IDEnvio FROM inserted)
END
GO

sp_settriggerorder @triggername = 'FechaAsignacion', @order = 'last', @stmttype = 'INSERT'

			SELECT A.ID AS IDAlmacen, A.Capacidad, Sum(E.NumeroContenedores) AS Ocupado, A.Capacidad - Sum(E.NumeroContenedores) AS disponible From Almacenes AS A 
			Inner Join Asignaciones As Ag ON A.ID = Ag.IDAlmacen
			Inner Join Envios AS E ON Ag.IDEnvio = E.ID
			Group By A.ID, A.Capacidad

SELECT * FROM Envios
SELECT * FROM Asignaciones

