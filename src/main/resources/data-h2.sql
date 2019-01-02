insert into T_B_MERAPI_ORG (org_id, org_name, public_key) values ('0001', 'Test Org', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgFGVfrY4jQSoZQWWygZ83roKXWD4YeT2x2p41dGkPixe73rT2IW04glagN2vgoZoHuOPqa5and6kAmK2ujmCHu6D1auJhE2tXP+yLkpSiYMQucDKmCsWMnW9XlC5K7OSL77TXXcfvTvyZcjObEz6LIBRzs6+FqpFbUO9SJEfh6wIDAQAB');
insert into T_B_MERCHANT (merchant_id) values ('123456789012345');
insert into T_B_APIORG_MERLIST (org_id, merchant_id) values ('0001', '123456789012345');
insert into T_B_TERMINAL_BATCH (merchant_id, terminal_id, batch_no) values ('123456789012345', '12345678', '000001');