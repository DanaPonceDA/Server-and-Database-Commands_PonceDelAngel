Create database Sprint3;
use Sprint3;

CREATE TABLE articles (
    id INT AUTO_INCREMENT PRIMARY KEY,           -- Identificador único
    title VARCHAR(255) NOT NULL,                 -- Título del artículo
    authors TEXT,                                -- Autores
    publication_date varchar(30),                  -- Fecha (solo año se convierte a 01-01)
    abstract TEXT,                               -- Resumen
    link VARCHAR(500) UNIQUE,                    -- Enlace único
    keywords TEXT,                               -- Palabras clave
    cited_by INT DEFAULT 0 
);

drop table articles;

select * From articles;