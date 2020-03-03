CREATE VIEW `v_employees_job_titles` AS
	SELECT CONCAT(COALESCE(`first_name`, ''), ' ', COALESCE(`middle_name`, ''), ' ',COALESCE(`last_name`, '')) AS 'full_name',
    `job_title`
    FROM `employees`;
    
SELECT * FROM `v_employees_job_titles`;