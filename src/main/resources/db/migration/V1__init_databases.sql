CREATE TABLE IF NOT EXISTS categories(
    id INTEGER PRIMARY KEY AUTOINCREMENT ,
    title VARCHAR NOT NULL ,
    image BLOB
);

CREATE TABLE IF NOT EXISTS products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    unit TEXT NOT NULL,
    price NUMERIC(10, 2) NOT NULL ,
    image BLOB,
    category_id INTEGER NOT NULL ,
    FOREIGN KEY (category_id) REFERENCES categories(id)
        ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    contact_name TEXT NOT NULL,
    contact_phone TEXT NOT NULL,
    delivery_address TEXT NOT NULL ,
    order_details TEXT NOT NULL ,
    created_at TIMESTAMP NOT NULL ,
    extra_cutting BOOLEAN DEFAULT 0,
    vet_certificate BOOLEAN DEFAULT 0,
    keep_rabbit_paw BOOLEAN DEFAULT 0,
    keep_organs BOOLEAN DEFAULT 0
);