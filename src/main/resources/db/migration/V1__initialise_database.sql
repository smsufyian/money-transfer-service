DROP SCHEMA transactions IF EXISTS;

CREATE SCHEMA transactions;

CREATE TABLE transactions.transfers (
  id BIGINT PRIMARY KEY auto_increment,
  sender_id BIGINT NOT NULL,
  recipient_id BIGINT NOT NULL,
  amount BIGINT NOT NULL,
  status INT NOT NULL,
  created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE transactions.accounts (
  id BIGINT PRIMARY KEY auto_increment,
  title VARCHAR(100) NOT NULL ,
  branch_id INT NOT NULL,
  account_number BIGINT,
  account_balance BIGINT,
  created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE INDEX created_on_idx ON transactions.transfers(created_on);
CREATE INDEX acc_idx ON transactions.accounts(branch_id, account_number);
CREATE UNIQUE INDEX uq_accounts
  ON transactions.accounts(branch_id, account_number);