-- Создаем таблицу "dishes"
CREATE TABLE dishes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    recipe_id INT UNIQUE,
    title VARCHAR(255),
    preview VARCHAR(255),
    duration INT,
    complexity ENUM('easy', 'medium', 'hard')
);

-- Создаем таблицу "ingredients"
CREATE TABLE ingredients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    recipe_id INT,
    ingredient VARCHAR(255),
    quantity INT,
    FOREIGN KEY (recipe_id) REFERENCES dishes(id)
);

-- Создаем таблицу "recipies"
CREATE TABLE recipies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    recipe_id INT,
    step INT,
    decription TEXT,
    image VARCHAR(255),
    FOREIGN KEY (recipe_id) REFERENCES dishes(id)
);