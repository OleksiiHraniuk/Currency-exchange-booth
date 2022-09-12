/*INSERT INTO CURRENCY_ORDER_DBT(ID, CURRENCY_BUY, CURRENCY_SELL, SUM_SELL, SUM_BUY, CLIENT_NAME, PHONE, ORDER_STATUS, OTP_PASSWORD)
VALUES (1, 'USD', 'UAH', 25, 0, 'TEST USER', '095-1234567', 'NEW', '0123');*/

--initializing some data

/*
INSERT INTO CURRENCY_EXCHANGE_RATE_DBT (ID, CURRENCY, CURRENCY_BASE, BUY, SELL, EXCHANGE_DATE, EXCHANGE_TIME)
VALUES (1, 'USD', 'UAH', '26.80000', '28.10000', DATE '2022-08-19', TIME '20:05:32');*/
INSERT INTO CURRENCY_ORDER_DBT(ID, CURRENCY_BUY, CURRENCY_SELL, SUM_BUY, SUM_SELL, CLIENT_NAME, PHONE, ORDER_STATUS, OTP_PASSWORD, ORDER_DATE, ORDER_TIME )
VALUES (11, 'USD', 'UAH', 25, 750, 'TEST USER', '095-1234567', '0', '0123', DATE '2022-08-16', TIME '14:20:42');
INSERT INTO CURRENCY_ORDER_DBT(ID, CURRENCY_BUY, CURRENCY_SELL, SUM_BUY, SUM_SELL, CLIENT_NAME, PHONE, ORDER_STATUS, OTP_PASSWORD, ORDER_DATE, ORDER_TIME)
VALUES (12, 'USD', 'UAH', 30, 900, 'TEST USER', '095-1234567', '1', '0123', DATE '2022-08-16', TIME '15:20:42');
INSERT INTO CURRENCY_ORDER_DBT(ID, CURRENCY_BUY, CURRENCY_SELL, SUM_BUY, SUM_SELL, CLIENT_NAME, PHONE, ORDER_STATUS, OTP_PASSWORD, ORDER_DATE, ORDER_TIME)
VALUES (13, 'EUR', 'UAH', 35, 1050, 'TEST USER', '095-1234567', '2', '3405', DATE '2022-08-16', TIME '16:20:42');
INSERT INTO CURRENCY_ORDER_DBT(ID, CURRENCY_BUY, CURRENCY_SELL, SUM_BUY, SUM_SELL, CLIENT_NAME, PHONE, ORDER_STATUS, OTP_PASSWORD, ORDER_DATE, ORDER_TIME)
VALUES (14, 'USD', 'UAH', 40, 1200, 'TEST USER', '095-1234567', '3', '0466', DATE '2022-08-17', TIME '11:20:42');
INSERT INTO CURRENCY_ORDER_DBT(ID, CURRENCY_BUY, CURRENCY_SELL, SUM_BUY, SUM_SELL, CLIENT_NAME, PHONE, ORDER_STATUS, OTP_PASSWORD, ORDER_DATE, ORDER_TIME )
VALUES (15, 'USD', 'UAH', 25, 750, 'TEST USER', '095-1234567', '0', '0123', DATE '2022-08-17', TIME '14:20:42');
INSERT INTO CURRENCY_ORDER_DBT(ID, CURRENCY_BUY, CURRENCY_SELL, SUM_BUY, SUM_SELL, CLIENT_NAME, PHONE, ORDER_STATUS, OTP_PASSWORD, ORDER_DATE, ORDER_TIME)
VALUES (16, 'USD', 'UAH', 30, 900, 'TEST USER', '095-1234567', '1', '0123', DATE '2022-08-18', TIME '15:20:42');
INSERT INTO CURRENCY_ORDER_DBT(ID, CURRENCY_BUY, CURRENCY_SELL, SUM_BUY, SUM_SELL, CLIENT_NAME, PHONE, ORDER_STATUS, OTP_PASSWORD, ORDER_DATE, ORDER_TIME)
VALUES (17, 'EUR', 'UAH', 35, 1050, 'TEST USER', '095-1234567', '2', '3405', DATE '2022-08-18', TIME '16:20:42');
INSERT INTO CURRENCY_ORDER_DBT(ID, CURRENCY_BUY, CURRENCY_SELL, SUM_BUY, SUM_SELL, CLIENT_NAME, PHONE, ORDER_STATUS, OTP_PASSWORD, ORDER_DATE, ORDER_TIME)
VALUES (18, 'UAH', 'USD', 1200, 40, 'TEST USER', '095-1234567', '3', '0466', DATE '2022-08-18', TIME '17:20:42');
INSERT INTO CURRENCY_ORDER_DBT(ID, CURRENCY_BUY, CURRENCY_SELL, SUM_BUY, SUM_SELL, CLIENT_NAME, PHONE, ORDER_STATUS, OTP_PASSWORD, ORDER_DATE, ORDER_TIME )
VALUES (19, 'EUR', 'UAH', 25, 750, 'TEST USER', '095-1234567', '0', '0123', DATE '2022-08-18', TIME '18:20:42');
INSERT INTO CURRENCY_ORDER_DBT(ID, CURRENCY_BUY, CURRENCY_SELL, SUM_BUY, SUM_SELL, CLIENT_NAME, PHONE, ORDER_STATUS, OTP_PASSWORD, ORDER_DATE, ORDER_TIME)
VALUES (20, 'USD', 'UAH', 30, 900, 'TEST USER', '095-1234567', '1', '0123', DATE '2022-08-18', TIME '19:20:42');
INSERT INTO CURRENCY_ORDER_DBT(ID, CURRENCY_BUY, CURRENCY_SELL, SUM_BUY, SUM_SELL, CLIENT_NAME, PHONE, ORDER_STATUS, OTP_PASSWORD, ORDER_DATE, ORDER_TIME)
VALUES (21, 'EUR', 'UAH', 35, 1050, 'TEST USER', '095-1234567', '2', '3405', DATE '2022-08-19', TIME '16:20:42');
INSERT INTO CURRENCY_ORDER_DBT(ID, CURRENCY_BUY, CURRENCY_SELL, SUM_BUY, SUM_SELL, CLIENT_NAME, PHONE, ORDER_STATUS, OTP_PASSWORD, ORDER_DATE, ORDER_TIME)
VALUES (22, 'UAH', 'EUR', 1200, 40, 'TEST USER', '095-1234567', '3', '0466', DATE '2022-08-19', TIME '17:20:42');
