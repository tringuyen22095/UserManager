DROP TRIGGER IF EXISTS before_insert_employee ON PUBLIC.employee;
DROP FUNCTION IF EXISTS PUBLIC.log_insert;
DROP TRIGGER IF EXISTS before_update_employee ON PUBLIC.employee;
DROP FUNCTION IF EXISTS PUBLIC.log_update;
DROP TRIGGER IF EXISTS before_delete_employee ON PUBLIC.employee;
DROP FUNCTION IF EXISTS PUBLIC.log_delete;
CREATE FUNCTION PUBLIC.log_insert() RETURNS TRIGGER AS $$
	BEGIN
		INSERT INTO PUBLIC.log(user_modify,act,old_value,new_value) VALUES ('', 'I', '', NEW);
		RETURN NEW;
	END $$ LANGUAGE plpgsql;
CREATE TRIGGER before_insert_employee BEFORE INSERT ON PUBLIC.employee
	FOR EACH ROW EXECUTE PROCEDURE PUBLIC.log_insert();

CREATE FUNCTION PUBLIC.log_update() RETURNS TRIGGER AS $$
	BEGIN
		INSERT INTO PUBLIC.log(user_modify,act,old_value,new_value) VALUES ('', 'U', OLD, NEW);
		RETURN NEW;
	END $$ LANGUAGE plpgsql;
CREATE TRIGGER before_update_employee BEFORE UPDATE ON PUBLIC.employee
	FOR EACH ROW EXECUTE PROCEDURE PUBLIC.log_update();

CREATE FUNCTION PUBLIC.log_delete() RETURNS TRIGGER AS $$
	BEGIN
		INSERT INTO PUBLIC.log(user_modify,act,old_value,new_value) VALUES ('', 'D', OLD, '');
		RETURN NEW;
	END $$ LANGUAGE plpgsql;
CREATE TRIGGER before_delete_employee AFTER DELETE ON PUBLIC.employee
	FOR EACH ROW EXECUTE PROCEDURE PUBLIC.log_delete();