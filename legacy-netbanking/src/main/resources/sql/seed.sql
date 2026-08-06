-- Seed data for Legacy Netbanking
-- Passwords stored in plain text (intentionally insecure legacy app)

INSERT OR IGNORE INTO USERS (id, username, password, role) VALUES (1, 'admin', 'admin123', 'ADMIN');
INSERT OR IGNORE INTO USERS (id, username, password, role) VALUES (2, 'user1', 'password1', 'USER');
INSERT OR IGNORE INTO USERS (id, username, password, role) VALUES (3, 'user2', 'password2', 'USER');

INSERT OR IGNORE INTO ACCOUNTS (id, user_id, account_number, balance) VALUES (1, 1, 'ADMIN001', 10000.00);
INSERT OR IGNORE INTO ACCOUNTS (id, user_id, account_number, balance) VALUES (2, 2, 'ACC1001', 5000.00);
INSERT OR IGNORE INTO ACCOUNTS (id, user_id, account_number, balance) VALUES (3, 2, 'ACC1002', 2500.00);
INSERT OR IGNORE INTO ACCOUNTS (id, user_id, account_number, balance) VALUES (4, 3, 'ACC2001', 7500.00);

INSERT OR IGNORE INTO TRANSACTIONS (id, from_account, to_account, amount, description) VALUES (1, 2, 4, 100.00, 'Payment for services');
INSERT OR IGNORE INTO TRANSACTIONS (id, from_account, to_account, amount, description) VALUES (2, 4, 2, 50.00, 'Refund');
INSERT OR IGNORE INTO TRANSACTIONS (id, from_account, to_account, amount, description) VALUES (3, 2, 3, 200.00, 'Transfer between own accounts');
INSERT OR IGNORE INTO TRANSACTIONS (id, from_account, to_account, amount, description) VALUES (4, 1, 2, 500.00, 'Admin credit');
