-- Load productos ms-sql file
CREATE TABLE producto AS SELECT * FROM CSVREAD(@path_input ||
  'productos.csv', NULL,'charset=UTF-8 fieldSeparator=;');

CREATE INDEX IF NOT EXISTS productoIndex ON producto (d);