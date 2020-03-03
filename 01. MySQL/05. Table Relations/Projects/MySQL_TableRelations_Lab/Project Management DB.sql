CREATE TABLE employees(
id INT PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(30) NOT NULL,
last_name VARCHAR(30) NOT NULL,
project_id INT
);

CREATE TABLE projects(
id INT PRIMARY KEY AUTO_INCREMENT,
client_id INT NOT NULL,
project_lead_id INT NOT NULL,
CONSTRAINT fk_projects_employees
FOREIGN KEY (project_lead_id)
REFERENCES projects(id)
);

ALTER TABLE employees
ADD CONSTRAINT fk_employees_projects
FOREIGN KEY (project_id)
REFERENCES employees(id);

CREATE TABLE clients (
id INT PRIMARY KEY AUTO_INCREMENT,
client_name VARCHAR(100) NOT NULL,
project_id INT NOT NULL,
CONSTRAINT fk_clients_projects
FOREIGN KEY (project_id)
REFERENCES projects(id)
);

ALTER TABLE projects
ADD CONSTRAINT fk_projects_clients
FOREIGN KEY (client_id)
REFERENCES clients(id);