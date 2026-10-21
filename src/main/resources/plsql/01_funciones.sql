CREATE OR REPLACE FUNCTION fn_calcular_total (
    p_id_habitacion IN NUMBER,
    p_entrada       IN DATE,
    p_salida        IN DATE
) RETURN NUMBER
IS
    v_precio habitaciones.precio_noche%TYPE;
BEGIN
SELECT precio_noche
INTO v_precio
FROM habitaciones
WHERE id_habitacion = p_id_habitacion;

RETURN v_precio * (p_salida - p_entrada);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN NULL;
END fn_calcular_total;
/

CREATE OR REPLACE FUNCTION fn_total_reservas_activas
RETURN NUMBER
IS
    v_total NUMBER;
BEGIN
SELECT NVL(SUM(total), 0)
INTO v_total
FROM reservas
WHERE estado = 'CONFIRMADA';

RETURN v_total;
END fn_total_reservas_activas;
/