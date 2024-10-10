CREATE DATABASE Carmaster;

USE Carmaster;

-- Bảng product
CREATE TABLE product (
    productID VARCHAR(10) PRIMARY KEY NOT NULL,
    supplierID VARCHAR(10) NOT NULL,
    productName VARCHAR(255) NOT NULL,
    productImg VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    basicPrice BIGINT NOT NULL, 
    sellPrice BIGINT NOT NULL,
    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(10) NOT NULL
);

-- Bảng productDetail
CREATE TABLE productDetail (
    productID VARCHAR(10) PRIMARY KEY NOT NULL,
    brandName VARCHAR(255) NOT NULL,
    yearOfManufacture INT NOT NULL,
    numberOfSeat INT NOT NULL,
    styleName VARCHAR(255) NOT NULL,
    fuelType VARCHAR(50) NOT NULL,
    colorName VARCHAR(255) NOT NULL,
    gearBox VARCHAR(50) NOT NULL,
    discountPercent INT NOT NULL
);

-- Bảng brand
CREATE TABLE brand (
    brandID VARCHAR(10) PRIMARY KEY NOT NULL,
    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    brandName VARCHAR(255) NOT NULL
);

-- Bảng manufacture
CREATE TABLE fuel (
    fuelID VARCHAR(10) PRIMARY KEY NOT NULL,
    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fuelType VARCHAR(10) NOT NULL
);

-- Bảng seat
CREATE TABLE seat (
    seatID VARCHAR(10) PRIMARY KEY NOT NULL,
    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    numberOfSeat INT NOT NULL
);

-- Bảng series
CREATE TABLE style (
    styleID VARCHAR(10) PRIMARY KEY NOT NULL,
    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    styleName VARCHAR(255) NOT NULL
);

-- Bảng color
CREATE TABLE color (
    colorID VARCHAR(10) PRIMARY KEY NOT NULL,
    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    colorName VARCHAR(255) NOT NULL
);

-- Bảng discount
CREATE TABLE discount (
    discountID VARCHAR(10) PRIMARY KEY NOT NULL,
    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    discountPercent INT NOT NULL
);

-- Bảng import
CREATE TABLE import (
    importID VARCHAR(10) PRIMARY KEY NOT NULL,
    supplierID VARCHAR(10) NOT NULL,
    employeeID VARCHAR(10) NOT NULL,
    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    totalCost BIGINT NOT NULL
);

-- Bảng importDetail
CREATE TABLE importDetail (
    importID VARCHAR(10) NOT NULL,
    productID VARCHAR(10) NOT NULL,
    price BIGINT NOT NULL,
    quantity INT NOT NULL,
    cost BIGINT NOT NULL,
    PRIMARY KEY (importID, productID)
);

-- Bảng invoice
CREATE TABLE invoice (
    invoiceID VARCHAR(10) PRIMARY KEY NOT NULL,
    customerID VARCHAR(10) NOT NULL,
    employeeID VARCHAR(10) NOT NULL,
    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    totalCost BIGINT NOT NULL
);

-- Bảng invoiceDetail
CREATE TABLE invoiceDetail (
    invoiceID VARCHAR(10) NOT NULL,
    productID VARCHAR(10) NOT NULL,
    price BIGINT NOT NULL,
    quantity INT NOT NULL,
    cost BIGINT NOT NULL,
    PRIMARY KEY (invoiceID, productID)
);

-- Bảng customer
CREATE TABLE customer (
    customerID VARCHAR(10) PRIMARY KEY NOT NULL,
    customerName VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    phone VARCHAR(10) NOT NULL
);

-- Bảng supplier
CREATE TABLE supplier (
    supplierID VARCHAR(10) PRIMARY KEY NOT NULL,
    supplierName VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    phone VARCHAR(20) NOT NULL
);

-- Bảng employee
CREATE TABLE employee (
    employeeID VARCHAR(10) PRIMARY KEY NOT NULL,
    employeeName VARCHAR(255) NOT NULL,
    permissionID VARCHAR(10) NOT NULL,
    gender VARCHAR(3) NOT NULL,
    DOB DATE NOT NULL,
    phone VARCHAR(10) NOT NULL,
    email VARCHAR(255) NOT NULL,
    salary INT NOT NULL,
    password VARCHAR(255) NOT NULL,
    OTP INT NULL,
    hireDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(15) NOT NULL
);

-- Bảng function
CREATE TABLE functional (
    functionID VARCHAR(10) PRIMARY KEY NOT NULL,
    functionName VARCHAR(255) NOT NULL
);

-- Bảng rightsGroup
CREATE TABLE permission (
    permissionID VARCHAR(10) PRIMARY KEY NOT NULL,
    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    permissionName VARCHAR(255) NOT NULL
);

-- Bảng rightsGroupDetail 
CREATE TABLE permissionDetail (
    permissionID VARCHAR(10),
    functionID VARCHAR(10),
    action VARCHAR(50) NOT NULL,
    PRIMARY KEY (permissionID, functionID, action)
)