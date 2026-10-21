CREATE OR REPLACE PACKAGE pkg_reservas AS
    -- ===== Parte pública =====
    FUNCTION total_reserva (p_id_reserva IN NUMBER) RETURN NUMBER;
    PROCEDURE cancelar_reserva (p_id_reserva IN NUMBER);
    PROCEDURE recalcular_reserva (p_id_reserva IN NUMBER);
    FUNCTION cancelaciones_sesion RETURN NUMBER;
END pkg_reservas;
/

CREATE OR REPLACE PACKAGE BODY pkg_reservas AS

    -- ===== Parte privada (no está en la especificación) =====
    g_cancelaciones NUMBER;

    FUNCTION existe_reserva (p_id_reserva IN NUMBER) RETURN BOOLEAN
    IS
        v_cantidad NUMBER;
BEGIN
SELECT COUNT(*)
INTO v_cantidad
FROM reservas
WHERE id_reserva = p_id_reserva;

RETURN v_cantidad > 0;
END existe_reserva;

    -- ===== Implementación de lo público =====
    FUNCTION total_reserva (p_id_reserva IN NUMBER) RETURN NUMBER
    IS
        v_total reservas.total%TYPE;
BEGIN
        IF NOT existe_reserva(p_id_reserva) THEN
            RAISE_APPLICATION_ERROR(-20001, 'La reserva ' || p_id_reserva || ' no existe');
END IF;

SELECT total
INTO v_total
FROM reservas
WHERE id_reserva = p_id_reserva;

RETURN v_total;
END total_reserva;

    PROCEDURE cancelar_reserva (p_id_reserva IN NUMBER)
IS
BEGIN
        IF NOT existe_reserva(p_id_reserva) THEN
            RAISE_APPLICATION_ERROR(-20001, 'La reserva ' || p_id_reserva || ' no existe');
END IF;

UPDATE reservas
SET estado = 'CANCELADA'
WHERE id_reserva = p_id_reserva
  AND estado <> 'CANCELADA';

IF SQL%ROWCOUNT = 0 THEN
            RAISE_APPLICATION_ERROR(-20002, 'La reserva ya estaba cancelada');
END IF;

        g_cancelaciones := g_cancelaciones + 1;
END cancelar_reserva;

    PROCEDURE recalcular_reserva (p_id_reserva IN NUMBER)
IS
BEGIN
UPDATE reservas
SET total = fn_calcular_total(id_habitacion, fecha_entrada, fecha_salida)
WHERE id_reserva = p_id_reserva;
END recalcular_reserva;

    FUNCTION cancelaciones_sesion RETURN NUMBER
    IS
BEGIN
RETURN g_cancelaciones;
END cancelaciones_sesion;

-- ===== Bloque de inicialización del package (se ejecuta una vez por sesión) =====
BEGIN
    g_cancelaciones := 0;
END pkg_reservas;
/
