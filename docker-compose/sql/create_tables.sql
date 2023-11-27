-- Create the MARKETPLACE table
CREATE TABLE IF NOT EXISTS MARKETPLACE (
    id INTEGER PRIMARY KEY,
    marketplace_name VARCHAR(16) NOT NULL CHECK (marketplace_name IN ('EBAY', 'AMAZON')),
    UNIQUE (marketplace_name)
);

-- Create the LISTING_STATUS table
CREATE TABLE IF NOT EXISTS LISTING_STATUS (
    id INTEGER PRIMARY KEY,
    status_name VARCHAR(16) NOT NULL CHECK (status_name IN ('ACTIVE', 'SCHEDULED', 'CANCELLED')),
    UNIQUE (status_name)
);

-- Create the LOCATION table
CREATE TABLE IF NOT EXISTS LOCATION (
    id VARCHAR(36) PRIMARY KEY,
    manager_name VARCHAR(128) NOT NULL,
    phone VARCHAR(32) NOT NULL,
    address_primary VARCHAR(128) NOT NULL,
    address_secondary VARCHAR(128),
    country VARCHAR(64) NOT NULL,
    town VARCHAR(64) NOT NULL,
    postal_code VARCHAR(32)
);

-- Create the LISTING table
CREATE TABLE listing (
     id VARCHAR(36) PRIMARY KEY,
     title VARCHAR(64) NOT NULL,
     description VARCHAR(128) NOT NULL,
     location_id VARCHAR(36) NOT NULL,
     listing_price NUMERIC(9, 2) NOT NULL,
     currency VARCHAR(8) NOT NULL,
     quantity INTEGER NOT NULL,
     listing_status_id INTEGER NOT NULL,
     marketplace_id INTEGER NOT NULL,
     upload_time DATE,
     owner_email_address VARCHAR(255) NOT NULL,
     CONSTRAINT fk_location FOREIGN KEY (location_id) REFERENCES location(id),
     CONSTRAINT fk_listing_status FOREIGN KEY (listing_status_id) REFERENCES listing_status(id),
     CONSTRAINT fk_marketplace FOREIGN KEY (marketplace_id) REFERENCES marketplace(id)
);
