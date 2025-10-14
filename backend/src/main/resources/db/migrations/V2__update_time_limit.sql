ALTER TABLE problems
ALTER COLUMN time_limit TYPE DOUBLE PRECISION;

ALTER TABLE problems DROP CONSTRAINT ck_problems_time_limit;

ALTER TABLE problems ADD CONSTRAINT ck_problems_time_limit
CHECK (time_limit > 0.0 AND time_limit <= 5.0)