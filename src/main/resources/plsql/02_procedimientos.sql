CREATE OR REPLACE PROCEDURE sp_recalcular_totales (
    p_estado IN  VARCHAR2,
    p_filas  OUT NUMBER
)
IS
BEGIN
UPDATE reservas
SET total = fn_calcular_total(id_habitacion, fecha_entrada, fecha_salida)
WHERE estado = p_estado;

p_filas := SQL%ROWCOUNT;
END sp_recalcular_totales;
/

CREATE OR REPLACE PROCEDURE sp_cancelar_reservas_vencidas
IS
BEGIN
UPDATE reservas
SET estado = 'CANCELADA'
WHERE estado = 'PENDIENTE'
  AND fecha_entrada < TRUNC(SYSDATE);

DBMS_OUTPUT.PUT_LINE('Reservas canceladas: ' || SQL%ROWCOUNT);
END sp_cancelar_reservas_vencidas;
/