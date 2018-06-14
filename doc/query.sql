DROP TABLE IF EXISTS PUBLIC."common";
CREATE TABLE PUBLIC."common"
(
	"id"										SERIAL PRIMARY KEY,
	"type"										VARCHAR(64),
	"value"										VARCHAR(64),
	"text"										VARCHAR(128),
	"description"								VARCHAR(256),
	"sequence"									INT4,
	"parent_id"									INT4,
	"is_deleted"								BOOLEAN NOT NULL DEFAULT FALSE,
	"create_by"									INT4,
	"create_on"									TIMESTAMP,
	"modify_by"									INT4,
	"modify_on"									TIMESTAMP
);

DROP TABLE IF EXISTS PUBLIC."function";
CREATE TABLE PUBLIC."function"
(
	"id"										SERIAL PRIMARY KEY,
	"parent_id"									INT4,
	"code"										VARCHAR(64),
	"text"										VARCHAR(128),
	"is_deleted"								BOOLEAN NOT NULL DEFAULT FALSE,
	"create_by"									INT4,
	"create_on"									TIMESTAMP,
	"modify_by"									INT4,
	"modify_on"									TIMESTAMP
);

DROP TABLE IF EXISTS PUBLIC."role";
CREATE TABLE PUBLIC."role"
(
	"id"										SERIAL PRIMARY KEY,
	"name"										VARCHAR(64),
	"remarks"									VARCHAR(128),
	"is_deleted"								BOOLEAN NOT NULL DEFAULT FALSE,
	"create_by"									INT4,
	"create_on"									TIMESTAMP,
	"modify_by"									INT4,
	"modify_on"									TIMESTAMP
);

DROP TABLE IF EXISTS PUBLIC."user";
CREATE TABLE PUBLIC."user"
(
	"id" 										SERIAL PRIMARY KEY,
	"user_name"									VARCHAR(64),
	"email"										VARCHAR(128),
	"account_no"								VARCHAR(64),
	"first_name"								VARCHAR(32),
	"last_name"									VARCHAR(32),
	"contact_no"								VARCHAR(16),
	"remarks"									VARCHAR(128),
	"status"									CHARACTER(3) NOT NULL DEFAULT 'ACT',
	"uuid"										UUID,
	"eoth"										UUID,
	"eoth_expiry_on"							TIMESTAMP,
	"is_email_verified"							BOOLEAN NOT NULL DEFAULT FALSE,
	"password_hash"								VARCHAR(256),
	"password_salt"								VARCHAR(256),
	"poth"										VARCHAR(256),
	"poth_expiry_on"							TIMESTAMP,
	"pass_reminder_token"						VARCHAR(256),
	"pass_reminder_expire"						TIMESTAMP,
	"active_code"								VARCHAR(8),
	"activation_expire"							TIMESTAMP,
	"lock_expiry_on"							TIMESTAMP,
	"last_declaration_on"						TIMESTAMP,
	"last_login_on"								TIMESTAMP,
	"failed_auth_attempts"						INT4 NOT NULL DEFAULT 0,
	"is_locked"									BOOLEAN NOT NULL DEFAULT TRUE,
	"is_deleted"								BOOLEAN NOT NULL DEFAULT FALSE,
	"create_by"									INT4,
	"create_on"									TIMESTAMP,
	"modify_by"									INT4,
	"modify_on"									TIMESTAMP
);

DROP TABLE IF EXISTS PUBLIC."user_role";
CREATE TABLE PUBLIC."user_role"
(
	"id" 										SERIAL PRIMARY KEY,
	"user_id"									INT4,
	"role_id"									INT4,
	"is_deleted"								BOOLEAN NOT NULL DEFAULT FALSE,
	"create_by"									INT4,
	"create_on"									TIMESTAMP,
	"modify_by"									INT4,
	"modify_on"									TIMESTAMP
);

DROP TABLE IF EXISTS PUBLIC."role_function";
CREATE TABLE PUBLIC."role_function"
(
	"id"										SERIAL PRIMARY KEY,
	"role_id"									INT4,
	"function_id"								INT4,
	"is_deleted"								BOOLEAN NOT NULL DEFAULT FALSE,
	"create_by"									INT4,
	"create_on"									TIMESTAMP,
	"modify_by"									INT4,
	"modify_on"									TIMESTAMP
);

DROP TABLE IF EXISTS PUBLIC."auth_token";
CREATE TABLE PUBLIC."auth_token"
(
	"id"										SERIAL PRIMARY KEY,
	"client_key"								VARCHAR(64),
	"module"									VARCHAR(32),
	"token"										VARCHAR(8),
	"expire_on"									TIMESTAMP,
	"is_verified"								BOOLEAN NOT NULL DEFAULT FALSE,
	"create_by"									INT4,
	"create_on"									TIMESTAMP,
	"modify_by"									INT4,
	"modify_on"									TIMESTAMP
);

DROP TABLE IF EXISTS PUBLIC."team";
CREATE TABLE "team"
(
	"id"										SERIAL PRIMARY KEY,
	"name"										VARCHAR(64),
	"country"									VARCHAR(64),
	"coach"										VARCHAR(64),
	"logo"										VARCHAR(128),
	"history"									TEXT,
	"condition"									VARCHAR(256),
	"is_deleted"								BOOLEAN,
	"create_by"									INT4,
	"create_on"									TIMESTAMP,
	"modify_by"									INT4,
	"modify_on"									TIMESTAMP
);

DROP TABLE IF EXISTS PUBLIC."statement";
CREATE TABLE "statement"
(
	"id"										SERIAL PRIMARY KEY,
	"name"										VARCHAR(64),
	"from_date"									TIMESTAMP,
	"to_date"									TIMESTAMP,
	"history"									TEXT,
	"code"										INT4,
	"parent_id"									INT4,
	"is_rounds"									BOOLEAN,
	"is_deleted"								BOOLEAN,
	"create_by"									INT4,
	"create_on"									TIMESTAMP,
	"modify_by"									INT4,
	"modify_on"									TIMESTAMP
);

DROP TABLE IF EXISTS PUBLIC."calendar";
CREATE TABLE "calendar"
(
	"id"										SERIAL PRIMARY KEY,
	"competition_date"							TIMESTAMP,
	"start_guess"								TIMESTAMP,
	"team1_id"									INT4 REFERENCES PUBLIC."team"("id"),
	"team2_id"									INT4 REFERENCES PUBLIC."team"("id"),
	"stadium"									VARCHAR(64),
	"referee"									VARCHAR(64),
	"squad1"									TEXT,
	"squad2"									TEXT,
	"is_group_match"							BOOLEAN,
	"r1"										INT4,
	"r2"										INT4,
	"r11"										INT4,
	"r21"										INT4,
	"r12"										INT4,
	"r22"										INT4,
	"win_team"									INT4 REFERENCES PUBLIC."team"("id"),
	"is_deleted"								BOOLEAN,
	"create_by"									INT4,
	"create_on"									TIMESTAMP,
	"modify_by"									INT4,
	"modify_on"									TIMESTAMP
);

DROP TABLE IF EXISTS PUBLIC."join";
CREATE TABLE "join"
(
	"id"										SERIAL PRIMARY KEY,
	"team_id"									INT4 REFERENCES PUBLIC."team"("id"),
	"statement_id"								INT4 REFERENCES PUBLIC."statement"("id"),
	"match"										INT4,
	"win"										INT4,
	"due"										INT4,
	"lose"										INT4,
	"goals_for"									INT4,
	"goals_against"								INT4,
	"goal_difference"							INT4,
	"point"										INT4,
	"is_deleted"								BOOLEAN,
	"create_by"									INT4,
	"create_on"									TIMESTAMP,
	"modify_by"									INT4,
	"modify_on"									TIMESTAMP
);

DROP TABLE IF EXISTS PUBLIC."news";
CREATE TABLE "news"
(
	"id"										SERIAL PRIMARY KEY,
	"title"										VARCHAR(256),
	"content"									TEXT,
	"is_deleted"								BOOLEAN,
	"create_by"									INT4,
	"create_on"									TIMESTAMP,
	"modify_by"									INT4,
	"modify_on"									TIMESTAMP
);

DROP TABLE IF EXISTS PUBLIC."bet";
CREATE TABLE "bet"
(
	"id"										SERIAL PRIMARY KEY,
	"calendar_id"								INT4 REFERENCES PUBLIC."calendar"("id"),
	"g11"										INT4,
	"g21"										INT4,
	"g22"										INT4,
	"g31"										INT4,
	"g41"										INT4 REFERENCES PUBLIC."team"("id"),
	"sub_question"								INT4,
	"status"									VARCHAR(32),
	"user_id"									INT4,
	"is_deleted"								BOOLEAN,
	"create_by"									INT4,
	"create_on"									TIMESTAMP,
	"modify_by"									INT4,
	"modify_on"									TIMESTAMP
);

DROP TABLE IF EXISTS PUBLIC."prize";
CREATE TABLE "prize"
(
	"id"										SERIAL PRIMARY KEY,
	"bet_id"									INT4 REFERENCES PUBLIC."calendar"("id"),
	"is_deleted"								BOOLEAN,
	"create_by"									INT4,
	"create_on"									TIMESTAMP,
	"modify_by"									INT4,
	"modify_on"									TIMESTAMP
);