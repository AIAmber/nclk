-- SELECT E.EXAMROOM_ID, count(*) FROM NCLK.T_NCLK_EXAMROOM E, NCLK.T_NCLK_ENDPOINT P WHERE E.STATE = "error" AND E.ENDPOINT_ID = P.ENDPOINT_ID GROUP BY E.EXAMROOM_ID;

-- SELECT P.ENDPOINT_NAME FROM NCLK.T_NCLK_EXAMROOM E, NCLK.T_NCLK_ENDPOINT P WHERE E.STATE = "error" AND E.ENDPOINT_ID = P.ENDPOINT_ID;

-- SELECT P.AREA_ID FROM NCLK.T_NCLK_EXAMROOM E, NCLK.T_NCLK_ENDPOINT P WHERE E.STATE = "error" AND E.ENDPOINT_ID = P.ENDPOINT_ID;

-- SELECT E.EXAMROOM_ID FROM NCLK.T_NCLK_EXAMROOM E, NCLK.T_NCLK_ENDPOINT P WHERE E.STATE = "error";

-- A
SELECT A.AREA_NAME FROM NCLK.T_NCLK_EXAMROOM E, NCLK.T_NCLK_ENDPOINT P, NCLK.T_NCLK_AREA A WHERE E.STATE = "error" AND E.ENDPOINT_ID = P.ENDPOINT_ID AND P.AREA_ID = A.AREA_ID AND A.AREA_NAME = "鼓楼区" AND A.PARENT_AREA_ID="3203";

-- B
-- SELECT B.AREA_NAME FROM NCLK.T_NCLK_EXAMROOM E, NCLK.T_NCLK_ENDPOINT P, NCLK.T_NCLK_AREA A, NCLK.T_NCLK_AREA B WHERE E.STATE = "error" AND E.ENDPOINT_ID = P.ENDPOINT_ID AND P.AREA_ID = A.AREA_ID AND A.PARENT_AREA_ID = B.AREA_ID AND B.AREA_NAME = "南京市";

-- SELECT A.PARENT_AREA_ID FROM NCLK.T_NCLK_EXAMROOM E, NCLK.T_NCLK_ENDPOINT P, NCLK.T_NCLK_AREA A WHERE E.STATE = "error" AND E.ENDPOINT_ID = P.ENDPOINT_ID AND P.AREA_ID = A.AREA_ID;


-- SELECT E.EXAMROOM_ID FROM NCLK.T_NCLK_EXAMROOM E WHERE E.STATE = "error";