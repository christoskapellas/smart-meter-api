INSERT INTO ACCOUNTS (ACCOUNT_ID) VALUES (1);
INSERT INTO ACCOUNTS (ACCOUNT_ID) VALUES (2);
INSERT INTO ACCOUNTS (ACCOUNT_ID) VALUES (3);

INSERT INTO GAS_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(1, 1500.5, '2022-01-01', 1);
INSERT INTO GAS_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(1, 1731.9, '2022-02-01', 1);
INSERT INTO GAS_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(1, 1731.9, '2022-03-01', 1);
INSERT INTO GAS_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(2, 700.6, '2022-01-01', 2);
INSERT INTO GAS_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(2, 2152.4, '2022-02-01', 2);
INSERT INTO GAS_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(3, 100.0, '2021-11-01', 3);
INSERT INTO GAS_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(3, 150.1, '2021-12-01', 3);
INSERT INTO GAS_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(3, 211.2, '2022-01-01', 3);
INSERT INTO GAS_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(3, 302.3, '2022-02-01', 3);

INSERT INTO ELEC_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(1, 600.3, '2022-01-01', 1);
INSERT INTO ELEC_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(1, 965.9, '2022-02-01', 1);
INSERT INTO ELEC_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(1, 1111.2, '2022-03-01', 1);
INSERT INTO ELEC_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(2, 700.6, '2022-01-01', 2);
INSERT INTO ELEC_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(2, 2152.4, '2022-02-01', 2);
INSERT INTO ELEC_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(3, 50.6, '2021-11-01', 3);
INSERT INTO ELEC_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(3, 75.4, '2021-12-01', 3);
INSERT INTO ELEC_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(3, 91.2, '2022-01-01', 3);
INSERT INTO ELEC_READINGS (METER_ID, READING, READING_DATE, ACCOUNT_ID) VALUES(3, 99.9, '2022-02-01', 3);