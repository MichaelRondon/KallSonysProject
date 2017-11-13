create or replace package     PKG_ORDENES as

   TYPE T_CUR_REF   IS  REF CURSOR;

procedure ranking_fact_clientes (
    p_fecha_ini DATE,
    p_fecha_fin DATE,
    IO_CURSOR OUT T_CUR_REF
);

procedure ordenes_x_producto (
    p_id_producto NUMBER,
    IO_CURSOR OUT T_CUR_REF
);

procedure resumen_ordenes_mes (
    p_anio NUMBER,
    p_mes  NUMBER,
    IO_CURSOR OUT T_CUR_REF
);

end;


---------------------------------------------------
---------------------------------------------------

create or replace package body    PKG_ORDENES as

procedure ranking_fact_clientes (
    p_fecha_ini DATE,
    p_fecha_fin DATE,
    IO_CURSOR OUT T_CUR_REF
)
    is
    RESPUESTA T_CUR_REF;
begin
    OPEN RESPUESTA FOR
    SELECT
    ORDERS.CUSTID
    ,SUM(ORDERS.PRICE) AS PRICE
    FROM
    "ORDERS"
    WHERE
    "ORDERS"."ORDERDATE" BETWEEN (TRUNC(p_fecha_ini)) AND (TRUNC(p_fecha_fin) + 1)
    AND "ORDERS"."STATUS" NOT IN ('NUEVA', 'RECHAZADA')
    GROUP BY ORDERS.CUSTID
    ORDER BY 2 DESC;

    IO_CURSOR := RESPUESTA;
    
    end ranking_fact_clientes;
    
    procedure ordenes_x_producto (
    p_id_producto NUMBER,
    IO_CURSOR OUT T_CUR_REF
    )
    is
    RESPUESTA T_CUR_REF;
    begin
    OPEN RESPUESTA FOR
    SELECT
    ORDERS.CUSTID
    ,ORDERS.ORDID
    ,ORDERS.PRICE
    ,ORDERS.ORDERDATE
    ,ORDERS.STATUS
    FROM
    "ORDERS", "ITEMS"
    WHERE
    "ITEMS"."ORDID" = "ORDERS"."ORDID"
    AND "ITEMS"."PRODID" = p_id_producto
    AND "ORDERS"."STATUS" NOT IN ('NUEVA', 'RECHAZADA')
    GROUP BY     
    ORDERS.CUSTID
    ,ORDERS.ORDID
    ,ORDERS.PRICE
    ,ORDERS.ORDERDATE
    ,ORDERS.STATUS;

    IO_CURSOR := RESPUESTA;
    
    end ordenes_x_producto;

    procedure resumen_ordenes_mes (
    p_anio NUMBER,
    p_mes  NUMBER,
    IO_CURSOR OUT T_CUR_REF
    )
    IS
    RESPUESTA T_CUR_REF;
begin
    OPEN RESPUESTA FOR
    SELECT 
    NVL(SUM(NVL(ORDERS.PRICE, 0)), 0) AS PRICE
    ,COUNT(ORDERS.STATUS) AS CANTIDAD
    FROM ORDERS 
    WHERE
    TO_NUMBER(TO_CHAR(ORDERDATE, 'MM')) = p_mes
    AND TO_NUMBER(TO_CHAR(ORDERDATE, 'YYYY')) = p_anio
    AND STATUS = 'ENTREGADA';

    IO_CURSOR := RESPUESTA;

    END resumen_ordenes_mes;

END PKG_ORDENES;
