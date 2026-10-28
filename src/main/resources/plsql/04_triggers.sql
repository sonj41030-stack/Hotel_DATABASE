-- Trigger a nivel de FILA: registra cada cambio de estado en el historial
CREATE OR REPLACE TRIGGER trg_reservas_auditoria
AFTER INSERT OR UPDATE OF estado ON reservas
    FOR EACH ROW
    WHEN (OLD.estado IS NULL OR NEW.estado <> OLD.estado)
DECLARE
v_accion VARCHAR2(20);
BEGIN
    IF INSERTING THEN
        v_accion := 'INSERT';
ELSE
        v_accion := 'UPDATE';
END IF;

INSERT INTO historial_reservas
(id_reserva, accion, estado_antes, estado_despues, usuario_bd, fecha_registro)
VALUES
    (:NEW.id_reserva, v_accion, :OLD.estado, :NEW.estado, USER, SYSTIMESTAMP);
END trg_reservas_auditoria;
/

-- Trigger a nivel de SENTENCIA: impide borrar reservas (se deben cancelar)
CREATE OR REPLACE TRIGGER trg_reservas_sin_borrado
BEFORE DELETE ON reservas
BEGIN
    RAISE_APPLICATION_ERROR(-20010,
        'No se pueden eliminar reservas. Use pkg_reservas.cancelar_reserva');
END trg_reservas_sin_borrado;
/