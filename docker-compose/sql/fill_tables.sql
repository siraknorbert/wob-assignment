-- Insert into MARKETPLACE table
INSERT INTO MARKETPLACE (id, marketplace_name) VALUES (1, 'EBAY');

-- Insert into LISTING_STATUS table
INSERT INTO LISTING_STATUS (id, status_name) VALUES (1, 'ACTIVE');

-- Insert into LOCATION table
INSERT INTO LOCATION (id, manager_name, phone, address_primary, address_secondary, country, town, postal_code)
VALUES ('0fe479bb-fe39-4265-b59f-bb4ac5a060d4', 'Chelsy Jellybrand', '415-411-0008', '82416 Rutledge Drive', NULL, 'Colombia', 'Algeciras', '413048');

-- Insert into LISTING table
INSERT INTO LISTING (id, title, description, location_id, listing_price, currency, quantity, listing_status_id, marketplace_id, upload_time, owner_email_address)
VALUES ('f210acb5-3ca5-4f2e-acf2-7605bd327648', 'Prostrate Mountain Phlox', 'Phlox austromontana Coville ssp. prostrata (E.E. Nelson) Wherry', '0fe479bb-fe39-4265-b59f-bb4ac5a060d4', 184.38, 'USD', 3, 1, 1, '2017-01-14', 'krueger@yahoo.com');
