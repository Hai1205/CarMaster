USE Carmaster;

-- Xóa toan bộ dữ liệu cũ
SET SQL_SAFE_UPDATES = 0;
DELETE FROM permissionDetail;
DELETE FROM permission;
DELETE FROM functional;
DELETE FROM employee;
DELETE FROM supplier;
DELETE FROM customer;
DELETE FROM invoiceDetail;
DELETE FROM invoice;
DELETE FROM importDetail;
DELETE FROM import;
DELETE FROM discount;
DELETE FROM color;
DELETE FROM style;
DELETE FROM seat;
DELETE FROM fuel;
DELETE FROM brand;
DELETE FROM productDetail;
DELETE FROM product;

-- Dữ liệu bảng customer
INSERT INTO customer (customerID, customerName, address, phone, creationDate) VALUES
('CTM000001', 'Trần Nhất Nhất', 'Hà Nội', '0912345678', '2021-06-15 09:30:00'),
('CTM000002', 'Hồ Minh Hưng', 'Hồ Chí Minh', '0987654321', '2018-05-10 15:00:00'),
('CTM000003', 'Nguyễn Thị Minh Anh', 'Đà Nẵng', '0909123456', '2024-01-21 14:00:00'),
('CTM000004', 'Trần Đức Minh', 'Hải Phòng', '0945123456', '2021-03-11 10:00:00'),
('CTM000005', 'Lê Hải Yến', 'Cần Thơ', '0915234567', '2017-12-08 09:30:00'),
('CTM000006', 'Phạm Thanh Hằng', 'Nha Trang', '0976345123', '2022-04-18 15:30:00'),
('CTM000007', 'Hoàng Đức Anh', 'Vũng Tàu', '0936123456', '2023-07-14 14:45:00'),
('CTM000008', 'Ngô Thanh Tùng', 'Bình Dương', '0909123456', '2022-02-25 09:15:00'),
('CTM000009', 'Võ Thị Kim Ngân', 'Đồng Nai', '0967123456', '2023-10-05 11:00:00'),
('CTM000010', 'Đỗ Văn Tú', 'Huế', '0945234567', '2024-09-16 12:00:00'),
('CTM000011', 'Lý Thanh Trúc', 'Quảng Ninh', '0987123456', '2022-11-22  12:05:04'),
('CTM000012', 'Bùi Văn Hoàng', 'Lạng Sơn', '0916234567', '2018-05-09 16:30:00'),
('CTM000013', 'Lê Văn Thành', 'Hà Giang', '0908123456', '2022-08-17 12:00:00'),
('CTM000014', 'Nguyễn Thị Lan Anh', 'Bắc Giang', '0939123456', '2019-03-14 10:30:00'),
('CTM000015', 'Phạm Thị Mai', 'Pleiku', '0968123456', '2021-05-19 12:00:00'),
('CTM000016', 'Hoàng Văn Nam', 'Bắc Giang', '0946234567', '2021-06-20 09:15:00'),
('CTM000017', 'Trần Nhật Sinh', 'Bắc Ninh', '0917234567', '2021-07-30 17:00:00'),
('CTM000018', 'Đỗ Nam Công Chính', 'Nam Định', '0989123456', '2017-12-12 12:00:00'),
('CTM000019', 'Đinh Ngọc Ân', 'Thái Bình', '0938123456', '2019-09-25 14:45:00'),
('CTM000020', 'Vũ Trung Hiếu', 'Thanh Hóa', '0918234567', '2022-01-15 15:00:00');

-- Dữ liệu bảng supplier
INSERT INTO supplier (supplierID, supplierName, address, email, phone, creationDate) VALUES
('SP000001', 'Toyota Motor Corporation', 'Nhật Bản', 'toyotamotor@gmail.com', '0912345678', '2018-05-12 12:05:04'), -- Toyota, Lexus
('SP000002', 'Volkswagen Group', 'Đức', 'volkswagengroup@gmail.com', '0987654321', '2019-12-16 12:00:00'), -- Volkswagen, Audi, Porsche, Bentley, LamboPMShini, Bugatti
('SP000003', 'General Motor', 'Hoa Kỳ', 'generalmotor@gmail.com', '0909123456', '2020-01-17 09:15:00'), -- Chevrolet, GMC, Cadillac, Buick
('SP000004', 'Ford Motor Company', 'Hoa Kỳ', 'fordmotor@gmail.com', '0945123456', '2022-01-18 14:45:00'), -- Ford, Lincoln
('SP000005', 'Hyundai Motor Group', 'Hàn Quốc', 'huyndaimotor@gmail.com', '0915234567', '2022-12-19 16:30:00'), -- Hyundai, Kia, Genesis
('SP000006', 'Honda Motor', 'Nhật Bản', 'hondamotor@gmail.com', '0976345123', '2023-01-20 11:00:00'), -- Honda, Acura
('SP000007', 'BMW Group', 'Đức', 'bmwgroup@gmail.com', '0936123456', '2023-04-21 15:00:00'), -- BMW, Mini, Rolls-Royce
('SP000008', 'Daimler AG', 'Đức', 'daimlerag@gmail.com', '0909123456', '2023-07-22 13:30:00'), -- Mercedes-Benz, Smart
('SP000009', 'Nissan Motor', 'Nhật Bản', 'nissanmotor@gmail.com', '0967123456', '2023-10-23 08:45:00'), -- Nissan, Infiniti
('SP000010', 'Tata Motor', 'Ấn Độ', 'tatamotor@gmail.com', '0945234567', '2024-01-24 17:00:00'), -- Tata, Jaguar, Land Rover
('SP000011', 'Suzuki Motor Corporation', 'Nhật Bản', 'suzukimotorcorporation@gmail.com', '0987123456', '2024-06-25 10:30:00'), -- Suzuki
('SP000012', 'VinFast', 'Việt Nam', 'vinfast@gmail.com', '0916234567', '2024-08-26 12:00:00'), -- VinFast
('SP000013', 'Thaco', 'Việt Nam', 'thaco@gmail.com', '0908123456', '2024-08-29 12:00:00'), -- Kia, Mazda, Peugeot, BMW
('SP000014', 'Tesla', 'Hoa Kỳ', 'tesla@gmail.com', '0909043456', '2024-09-26 12:00:00'); -- Tesla

-- Dữ liệu bảng employee
INSERT INTO employee (employeeID, employeeName, hireDate, gender, DOB, phone, salary, email, password, permissionID, OTP, status) VALUES
('EP000001', 'Nguyễn Văn An', '2018-05-01 10:30:00', 'Nam', '1990-01-01', '0912345678', 50000000, 'annguyenvan0101@gmail.com', '$2a$12$y6D4PCBAP5pOYH6NjWW/.uvzteSrKMo4HihyyY9IAsiktOaU6j07S', 'PMS000001', 0, 'Hoạt động'),
('EP000002', 'Lê Thị Bình', '2018-05-01 17:00:00', 'Nữ', '1989-02-02', '0987654321', 50000000, 'binhlethiP0000202@gmail.com', '$2a$12$SH/cRKdK6UKYfg802STmdeGMLNKZ2t0Jcwa8rJ6v0OQxLNw5eagMC', 'PMS000001', 0, 'Ngưng hoạt động'),
('EP000003', 'Trần Văn Cảnh', '2019-03-02 08:45:00', 'Nam', '1991-03-03', '0909123456', 20000000, 'canhtranvan0303@gmail.com', '$2a$12$eo3kBaD5npH64ZG0dsl.NeDtiPMSAYoknCIAeD9Q.JrJX9z5aYjl2W', 'PMS000002', 0, 'Ngưng hoạt động'),
('EP000004', 'Phạm Thị Duyên', '2021-03-10 13:30:00', 'Nữ', '1992-04-04', '0945123456', 20000000, 'duyenphamthiP0000404@gmail.com', '$2a$12$LTzo77POlp0qpxhXfHc9jO/rnpVbYsTCgFAps3SvpIuV44RDKCqj2', 'PMS000002', 0, 'Hoạt động'),
('EP000005', 'Nguyễn Văn Hùng', '2017-12-06 15:00:00', 'Nam', '1988-05-05', '0915234567', 20000000, 'hungnguyenvan0505@gmail.com', '$2a$12$anxb02QZjdP.dGJWhhLRW.h.G54mdU5gTLvcjD9Y2KOzc8geVW72.', 'PMS000003', 0, 'Ngưng hoạt động'),
('EP000006', 'Lê Thị Thu Trang', '2021-07-28 11:00:00', 'Nữ', '1987-06-06', '0976345123', 20000000, 'tranglethithu0606@gmail.com', '$2a$12$qW/Tcx7IB0FmwO6M5nQtuOpjS9TuE/RaZs.imtfX3N3bxxpMzyrCa', 'PMS000003', 0, 'Hoạt động'),
('EP000007', 'Trần Văn Giang', '2017-12-10 16:30:00', 'Nam', '1993-07-07', '0936123456', 20000000, 'giangtranvan0707@gmail.com', '$2a$12$MVj8DceH6.RigI.5BSnl7.rirRj6srddLG9jyzRpiTwUMUrZzTQEq', 'PMS000002', 0, 'Hoạt động'),
('EP000008', 'Phạm Thị Huyền', '2019-09-20 14:45:00', 'Nữ', '1994-08-08', '0909123456', 20000000, 'huyenphamthi@gmail.com', '$2a$12$nhtjCSCrEIv4Y6HduI8mWOVuBdKS00nL8xQlaNl9gRsVwrEKfvpt.', 'PMS000002', 0, 'Ngưng hoạt động'),
('EP000009', 'Nguyễn Văn Toàn', '2022-01-10 09:15:00', 'Nam', '1995-09-09', '0967123456', 20000000, 'toannguyenvan@gmail.com', '$2a$12$hfWwBHo/IMf6BeR7j1XFs.Dp8PMSHnDiPMSk/BjD.1o4/0nBZNDQH1C', 'PMS000003', 0, 'Ngưng hoạt động'),
('EP000010', 'Lê Thị Bích Ngoc', '2023-06-20 12:00:00', 'Nữ', '1996-10-10', '0945234567', 20000000, 'ngoclethibich1010@gmail.com', '$2a$12$WtNcNSpSsYkHSdquFafEOePYapdpqCMlz.khWZalgPTA.XwOqEm8G', 'PMS000003', 0, 'Hoạt động'),
('EP000011', 'Nguyễn Hoàng Hải', '2018-05-12  12:05:04', 'Nam', '2004-05-12', '0782748863', 50000000, 'hainguyenhoang1205@gmail.com', '$2a$12$W5ZcIAEtbQHrQnhsLYUw9.V0I5.EOvSC5NgYcX/NVMcfYQfPxLh.y', 'PMS000001', 0, 'Hoạt động');

-- Dữ liệu bảng brand
INSERT INTO brand (brandID, brandName, creationDate) VALUES
('B000001', 'Toyota', '2018-05-12 12:05:04'),
('B000002', 'Honda', '2019-12-16 12:00:00'),
('B000003', 'Ford', '2020-01-17 09:15:00'),
('B000004', 'BMW', '2022-01-18 14:45:00'),
('B000005', 'Audi', '2022-12-19 16:30:00'),
('B000006', 'Mercedes', '2023-01-20 11:00:00'),
('B000007', 'Hyundai', '2023-04-21 15:00:00'),
('B000008', 'Mazda', '2023-04-21 15:00:00'),
('B000009', 'Kia', '2023-04-21 15:00:00'),
('B000010', 'Chevrolet', '2023-04-21 15:00:00'),
('B000011', 'Tesla', '2023-07-22 13:30:00'),
('B000012', 'Nissan', '2023-07-22 13:30:00'),
('B000013', 'Suzuki', '2023-10-23 08:45:00'),
('B000015', 'Vinfast', '2023-10-23 08:45:00'),
('B000017', 'Land Rover', '2024-01-24 17:00:00'),
('B000018', 'Porsche', '2024-01-24 17:00:00'),
('B000020', 'Jaguar', '2024-06-25 10:30:00'),
('B000021', 'Cadillac', '2024-06-25 10:30:00'),
('B000022', 'Acura', '2024-08-26 12:00:00'),
('B000023', 'Mini', '2024-08-26 12:00:00'),
('B000024', 'Peugeot', '2024-08-29 12:00:00'),
('B000025', 'Infiniti', '2024-09-26 12:00:00');

-- Dữ liệu bảng fuel
INSERT INTO fuel (fuelID, fuelType, creationDate) VALUES
('F000001', 'Xăng', '2018-05-12 12:05:04'),
('F000002', 'Điện', '2019-12-16 12:00:00'),
('F000003', 'Hybrid', '2020-01-17 09:15:00');

-- Dữ liệu bảng seat
INSERT INTO seat (seatID, numberOfSeat, creationDate) VALUES
('S000001', 4, '2018-05-12 12:05:04'),
('S000002', 7, '2019-12-16 12:00:00'),
('S000003', 2, '2020-01-17 09:15:00');

-- Dữ liệu bảng style
INSERT INTO style (styleID, styleName, creationDate) VALUES
('ST000001', 'Sedan', '2018-05-12 12:05:04'),
('ST000002', 'SUV', '2019-12-16 12:00:00'),
('ST000003', 'Hatchback', '2020-01-17 09:15:00'),
('ST000004', 'Coupe', '2022-01-18 14:45:00');

-- Dữ liệu bảng color
INSERT INTO color (colorID, colorName, creationDate) VALUES
('CL000001', 'Đỏ', '2018-05-12 12:05:04'),
('CL000002', 'Xanh dương', '2019-12-16 12:00:00'),
('CL000003', 'Trắng', '2020-01-17 09:15:00'),
('CL000004', 'Đen', '2022-01-18 14:45:00'),
('CL000005', 'Bạc', '2022-12-19 16:30:00'),
('CL000006', 'Xanh lục', '2023-01-20 11:00:00'),
('CL000007', 'vàng', '2023-04-21 15:00:00'),
('CL000008', 'Cam', '2023-04-21 15:00:00'),
('CL000009', 'Xám', '2023-07-22 13:30:00'),
('CL000010', 'Tím', '2023-10-23 08:45:00'),
('CL000011', 'Nâu', '2024-01-24 17:00:00'),
('CL000018', 'Hồng', '2024-06-25 10:30:00');

-- Dữ liệu bảng discount
INSERT INTO discount (discountID, discountPercent, creationDate) VALUES
('DC000001', 0, '2018-05-12 12:05:04'),
('DC000002', 10, '2019-12-16 12:00:00'),
('DC000003', 15, '2020-01-17 09:15:00'),
('DC000004', 20, '2022-01-18 14:45:00'),
('DC000005', 25, '2022-12-19 16:30:00'),
('DC000006', 30, '2023-01-20 11:00:00'),
('DC000007', 35, '2023-04-21 15:00:00'),
('DC000008', 40, '2023-07-22 13:30:00'),
('DC000009', 45, '2023-10-23 08:45:00');

-- Dữ liệu bảng produc
INSERT INTO product (productID, supplierID, productName, productImg, quantity, basicPrice, sellPrice, status, creationDate) VALUES
('PD000001', 'SP000001', 'Toyota Vios', 'Toyota-Vios.png', 50-1-1, 24000*25000, 21600*25000, 'Còn bán', '2018-05-12 12:05:04'),
('PD000002', 'SP000001', 'Toyota Fortuner', 'Toyota-Fortuner.png', 50-1-2, 40000*25000, 34000*25000, 'Còn bán', '2018-05-12 12:05:04'),
('PD000003', 'SP000002', 'Audi E-Tron', 'Audi-E-Tron.png', 50-2-1, 68000*25000, 64600*25000, 'Còn bán', '2019-12-16 12:00:00'),
('PD000004', 'SP000003', 'Porsche Taycan', 'Porsche-Taycan.png', 50-2-1, 380000*25000, 304000*25000, 'Còn bán', '2019-12-16 12:00:00'),
('PD000005', 'SP000003', 'Chevrolet Cruze', 'Chevrolet-Cruze.png', 50-1-1, 22000*25000, 16500*25000, 'Còn bán', '2020-01-17 09:15:00'),
('PD000006', 'SP000003', 'Cadillac Escalade', 'Cadillac-Escalade.png', 50-1-1, 470000*25000, 329000*25000, 'Còn bán', '2020-01-17 09:15:00'),
('PD000007', 'SP000004', 'Ford Everest Sport', 'Ford-Everest-Sport.png', 50-2-1, 48000*25000, 45600*25000, 'Còn bán', '2022-01-18 14:45:00'),
('PD000008', 'SP000004', 'Ford Everest Ambiente', 'Ford-Everest-Ambiente.png', 50-2-1, 40000*25000, 36000*25000, 'Còn bán', '2022-01-18 14:45:00'),
('PD000009', 'SP000005', 'Hyundai Creta', 'Hyundai-Creta.png', 50-1-1, 24000*25000, 20400*25000, 'Còn bán', '2022-12-19 16:30:00'),
('PD000010', 'SP000005', 'Kia K5', 'Kia-K5.png', 50-1-1, 35000*25000, 28000*25000, 'Còn bán', '2022-12-19 16:30:00'),
('PD000011', 'SP000006', 'Honda Civic', 'Honda-Civic.png', 50-1-1, 30000*25000, 21000*25000, 'Còn bán', '2023-01-20 11:00:00'),
('PD000012', 'SP000006', 'Acura MDX', 'Acura-MDX.png', 50-1-1, 47000*25000, 42300*25000, 'Còn bán', '2023-01-20 11:00:00'),
('PD000013', 'SP000007', 'BMW 530i', 'BMW-530i.png', 50-1-1, 60000*25000, 45000*25000, 'Còn bán', '2023-04-21 15:00:00'),
('PD000014', 'SP000007', 'Mini Cooper S', 'Mini-Cooper-S.png', 50-1-1, 96000*25000, 91200*25000, 'Còn bán', '2023-04-21 15:00:00'),
('PD000015', 'SP000008', 'Mercedes C200', 'Mercedes-C200.png', 50-1-1, 640000*25000, 576000*25000, 'Còn bán', '2023-07-22 13:30:00'),
('PD000016', 'SP000008', 'Mercedes GLS 450', 'Mercedes-GLS-450.png', 50-1-1, 216000*25000, 183600*25000, 'Còn bán', '2023-07-22 13:30:00'),
('PD000017', 'SP000009', 'Nissan Almera', 'Nissan-Almera.png', 50-1-1, 21600*25000, 17280*25000, 'Còn bán', '2023-10-23 08:45:00'),
('PD000018', 'SP000009', 'Infiniti QX60', 'Infiniti-QX60.png', 50-2-1, 51000*25000, 38250*25000, 'Còn bán', '2023-10-23 08:45:00'),
('PD000019', 'SP000010', 'Jaguar F-Pace', 'Jaguar-F-Pace.png', 50-1-1, 70000*25000, 49000*25000, 'Còn bán', '2024-01-24 17:00:00'),
('PD000020', 'SP000010', 'Land Rover Discovery', 'Land-Rover-Discovery.png', 50-1-1, 172000*25000, 154800*25000, 'Còn bán', '2024-01-24 17:00:00'),
('PD000021', 'SP000011', 'Suzuki XL7', 'Suzuki-XL7.png', 50-1-1, 24000*25000, 21600*25000, 'Còn bán', '2024-06-25 10:30:00'),
('PD000022', 'SP000012', 'Vinfast VF8', 'Vinfast-VF8.png', 50-1-1, 28000*25000, 25200*25000, 'Còn bán', '2024-08-26 12:00:00'),
('PD000023', 'SP000013', 'Mazda 3', 'Mazda-3.png', 50-1-1, 24000*25000, 21600*25000, 'Còn bán', '2024-08-29 12:00:00'),
('PD000024', 'SP000013', 'Peugeot 408 GT', 'Peugeot-408-GT.png', 50-1-1, 52000*25000, 46800*25000, 'Còn bán', '2024-08-29 12:00:00'),
('PD000025', 'SP000014', 'Tesla Roadter', 'Tesla-Roadter.png', 50-1-1, 80000*25000, 72000*25000, 'Còn bán', '2024-09-26 12:00:00');

-- Dữ liệu bảng productDetail
INSERT INTO productDetail (productID, brandName, yearOfManufacture, numberOfSeat, styleName, fuelType, colorName, gearBox, discountPercent) VALUES
('PD000001', 'Toyota', 2021, 4, 'Sedan', 'Xăng', 'Xám', 'Số sàn', 10),
('PD000002', 'Toyota', 2018, 7, 'SUV', 'Xăng', 'Đen', 'Số sàn', 15),
('PD000003', 'Audi', 2024, 7, 'SUV', 'Xăng', 'Xanh dương', 'Số tự động', 5),
('PD000004', 'Porsche', 2021, 4, 'Sedan', 'Điện', 'Trắng', 'Số tự động', 20),
('PD000005', 'Chevrolet', 2017, 4, 'Sedan', 'Xăng', 'Đen', 'Số tự động', 25),
('PD000006', 'Cadillac', 2022, 7, 'SUV', 'Xăng', 'Đen', 'Số tự động', 30),
('PD000007', 'Ford', 2023, 7, 'SUV', 'Xăng', 'Xanh dương', 'Số tự động', 5),
('PD000008', 'Ford', 2022, 7, 'SUV', 'Xăng', 'Nâu', 'Số tự động', 10),
('PD000009', 'Hyundai', 2023, 4, 'SUV', 'Xăng', 'Trắng', 'Số tự động', 15),
('PD000010', 'Kia', 2024, 4, 'Sedan', 'Xăng', 'Xanh dương', 'Số tự động', 20),
('PD000011', 'Honda', 2022, 4, 'Sedan', 'Xăng', 'Xám', 'Số tự động', 30),
('PD000012', 'Acura', 2011, 7, 'SUV', 'Xăng', 'Đen', 'Số tự động', 10),
('PD000013', 'BMW', 2022, 4, 'Sedan', 'Xăng', 'Xám', 'Số tự động', 25),
('PD000014', 'Mini', 2019, 5, 'Hatchback', 'Xăng', 'Đen', 'Số tự động', 5),
('PD000015', 'Mercedes', 2021, 4, 'Sedan', 'Xăng', 'Xanh dương', 'Số tự động', 10),
('PD000016', 'Mercedes', 2021, 4, 'SUV', 'Xăng', 'Trắng', 'Số tự động', 15),
('PD000017', 'Nissan', 2021, 4, 'Sedan', 'Xăng', 'Xám', 'Số tự động', 20),
('PD000018', 'Infiniti', 2017, 7, 'SUV', 'Xăng', 'Đỏ', 'Số tự động', 25),
('PD000019', 'Jaguar', 2019, 7, 'SUV', 'Xăng', 'Xám', 'Số tự động', 30),
('PD000020', 'Land Rover', 2022, 7, 'SUV', 'Xăng', '', 'Số tự động', 10),
('PD000021', 'Suzuki', 2024, 7, 'SUV', 'Hybrid', 'Trắng', 'Số tự động', 10),
('PD000022', 'Vinfast', 2022, 4, 'SUV', 'Điện', 'Đỏ', 'Số tự động', 10),
('PD000023', 'Mazda', 2022, 4, 'Sedan', 'Xăng', 'Đỏ', 'Số tự động', 10),
('PD000024', 'Peugeot', 2023, 7, 'Coupe', 'Xăng', 'Xanh dương', 'Số tự động', 10),
('PD000025', 'Tesla', 2023, 2, 'Sedan', 'Điện', 'Đỏ', 'Số tự động', 10);

-- Dữ liệu bảng import
INSERT INTO import (importID, supplierID, employeeID, creationDate, totalCost) VALUES
('IP000001', 'SP000001', 'EP000001', '2018-05-12 12:05:04', (22000+47000+51000)*25000*50/2),
('IP000002', 'SP000002', 'EP000002', '2019-12-16 12:00:00', 40000*25000*50/2),
('IP000003', 'SP000003', 'EP000003', '2020-01-17 09:15:00', (96000+70000)*25000*50/2),
('IP000004', 'SP000004', 'EP000004', '2022-01-18 14:45:00', (24000+380000)*25000*50/2),
('IP000005', 'SP000005', 'EP000005', '2022-12-19 16:30:00', (640000+216000)*25000*50/2),
('IP000006', 'SP000006', 'EP000006', '2023-01-20 11:00:00', (470000+40000)*25000*50/2),
('IP000007', 'SP000007', 'EP000007', '2023-04-21 15:00:00', (30000+60000)*25000*50/2),
('IP000008', 'SP000008', 'EP000008', '2023-07-22 13:30:00', (21600+172000)*25000*50/2),
('IP000009', 'SP000009', 'EP000009', '2023-10-23 08:45:00', (28000+24000)*25000*50/2),
('IP000010', 'SP000010', 'EP000010', '2024-01-24 17:00:00', (48000+24000)*25000*50/2),
('IP000011', 'SP000011', 'EP000001', '2024-06-25 10:30:00', (52000+80000)*25000*50/2),
('IP000012', 'SP000012', 'EP000002', '2024-08-26 12:00:00', (68000+35000+24000)*25000*50/2),
('IP000013', 'SP000001', 'EP000001', '2024-10-12 12:00:00', 24000*25000/2),
('IP000014', 'SP000002', 'EP000002', '2024-10-13 12:00:00', 40000*25000/2),
('IP000015', 'SP000003', 'EP000003', '2024-10-14 12:00:00', 68000*25000/2),
('IP000016', 'SP000004', 'EP000004', '2024-10-15 12:00:00', 380000*25000/2),
('IP000017', 'SP000005', 'EP000005', '2024-10-16 12:00:00', 22000*25000/2),
('IP000018', 'SP000006', 'EP000006', '2024-10-17 12:00:00', 470000*25000/2),
('IP000019', 'SP000007', 'EP000007', '2024-10-18 12:00:00', 48000*25000/2);

-- Dữ liệu bảng importDetail
INSERT INTO importDetail (importID, productID, price, quantity, cost) VALUES
('IP000001', 'PD000005', 22000*25000/2, 50, 22000*25000*50/2),
('IP000001', 'PD000012', 47000*25000/2, 50, 47000*25000*50/2),
('IP000001', 'PD000018', 51000*25000/2, 50, 51000*25000*50/2),
('IP000002', 'PD000002', 40000*25000/2, 50, 40000*25000*50/2),
('IP000003', 'PD000014', 96000*25000/2, 50, 96000*25000*50/2),
('IP000003', 'PD000019', 70000*25000/2, 50, 70000*25000*50/2),
('IP000004', 'PD000001', 24000*25000/2, 50, 24000*25000*50/2),
('IP000004', 'PD000004', 380000*25000/2, 50, 380000*25000*50/2),
('IP000005', 'PD000015', 640000*25000/2, 50, 640000*25000*50/2),
('IP000005', 'PD000016', 216000*25000/2, 50, 216000*25000*50/2),
('IP000006', 'PD000006', 470000*25000/2, 50, 470000*25000*50/2),
('IP000006', 'PD000008', 40000*25000/2, 50, 40000*25000*50/2),
('IP000007', 'PD000011', 30000*25000/2, 50, 30000*25000*50/2),
('IP000007', 'PD000013', 60000*25000/2, 50, 60000*25000*50/2),
('IP000008', 'PD000017', 21600*25000/2, 50, 21600*25000*50/2),
('IP000008', 'PD000020', 172000*25000/2, 50, 172000*25000*50/2),
('IP000009', 'PD000022', 28000*25000/2, 50, 28000*25000*50/2),
('IP000009', 'PD000023', 24000*25000/2, 50, 24000*25000*50/2),
('IP000010', 'PD000007', 48000*25000/2, 50, 48000*25000*50/2),
('IP000010', 'PD000009', 24000*25000/2, 50, 24000*25000*50/2),
('IP000011', 'PD000024', 52000*25000/2, 50, 52000*25000*50/2),
('IP000011', 'PD000025', 80000*25000/2, 50, 80000*25000*50/2),
('IP000012', 'PD000003', 68000*25000/2, 50, 68000*25000*50/2),
('IP000012', 'PD000010', 35000*25000/2, 50, 35000*25000*50/2),
('IP000013', 'PD000001', 24000*25000/2, 1, 24000*25000/2),
('IP000014', 'PD000002', 40000*25000/2, 1, 40000*25000/2),
('IP000015', 'PD000003', 68000*25000/2, 1, 68000*25000/2),
('IP000016', 'PD000004', 380000*25000/2, 1, 380000*25000/2),
('IP000017', 'PD000005', 22000*25000/2, 1, 22000*25000/2),
('IP000018', 'PD000006', 470000*25000/2, 1, 470000*25000/2),
('IP000019', 'PD000007', 48000*25000/2, 1, 48000*25000/2);

-- Dữ liệu bảng invoice
INSERT INTO invoice (invoiceID, customerID, employeeID, creationDate, totalCost) VALUES
('INV000001', 'CTM000001', 'EP000001', '2021-06-15 10:30:00', 540000000),
('INV000002', 'CTM000002', 'EP000002', '2018-05-10 16:00:00', 1200000000),
('INV000003', 'CTM000003', 'EP000003', '2024-01-21 15:00:00', 1360000000),
('INV000004', 'CTM000004', 'EP000004', '2021-03-11 11:00:00', 7600000000),
('INV000005', 'CTM000005', 'EP000005', '2017-12-08 10:30:00', 495000000),
('INV000006', 'CTM000006', 'EP000006', '2022-04-18 16:30:00', 9600000000),
('INV000007', 'CTM000007', 'EP000007', '2023-07-14 14:45:00', 240000000),
('INV000008', 'CTM000008', 'EP000008', '2022-02-25 09:15:00', 1500000000),
('INV000009', 'CTM000009', 'EP000009', '2023-10-05 11:00:00', 960000000),
('INV000010', 'CTM000010', 'EP000010', '2024-09-16 12:00:00', 840000000),
('INV000011', 'CTM000011', 'EP000011', '2022-11-22  12:05:04', 540000000),
('INV000012', 'CTM000012', 'EP000001', '2018-05-09 16:30:00', 300000000),
('INV000013', 'CTM000013', 'EP000002', '2022-08-17 12:00:00', 675000000),
('INV000014', 'CTM000014', 'EP000003', '2019-03-14 10:30:00', 1440000000),
('INV000015', 'CTM000015', 'EP000004', '2021-05-19 12:00:00', 5760000000),
('INV000016', 'CTM000016', 'EP000005', '2021-06-20 09:15:00', 734400000),
('INV000017', 'CTM000017', 'EP000006', '2021-07-30 17:00:00', 1728000000),
('INV000018', 'CTM000018', 'EP000007', '2017-12-12 12:00:00', 1138500000),
('INV000019', 'CTM000019', 'EP000008', '2019-09-25 14:45:00', 1470000000),
('INV000020', 'CTM000020', 'EP000009', '2022-01-15 15:00:00', 3870000000),
('INV000021', 'CTM000001', 'EP000001', '2024-05-11 11:00:00', 540000000),
('INV000022', 'CTM000002', 'EP000002', '2023-03-22 12:00:00', 450000000),
('INV000023', 'CTM000003', 'EP000003', '2023-11-30  12:05:04', 468000000),
('INV000024', 'CTM000004', 'EP000004', '2023-04-12 16:30:00', 720000000),
('INV000025', 'CTM000005', 'EP000005', '2023-07-17 09:15:00', 720000000),
('INV000026', 'CTM000006', 'EP000006', '2023-05-21 12:00:00', 612000000),
('INV000027', 'CTM000007', 'EP000007', '2023-08-02  12:05:04', 486000000),
('INV000028', 'CTM000008', 'EP000008', '2023-10-09 17:00:00', 1764000000),
('INV000029', 'CTM000009', 'EP000009', '2023-09-15 14:45:00', 1500000000),
('INV000030', 'CTM000010', 'EP000010', '2023-06-22 15:00:00', 504000000),
('INV000031', 'CTM000001', 'EP000001', '2024-10-12 15:00:00', 21600*25000),
('INV000032', 'CTM000002', 'EP000002', '2024-10-13 15:00:00', 34000*25000),
('INV000033', 'CTM000003', 'EP000003', '2024-10-14 15:00:00', 64600*25000),
('INV000034', 'CTM000004', 'EP000004', '2024-10-15 15:00:00', 304000*25000),
('INV000035', 'CTM000005', 'EP000005', '2024-10-16 15:00:00', 16500*25000),
('INV000036', 'CTM000006', 'EP000006', '2024-10-17 15:00:00', 329000*25000),
('INV000037', 'CTM000007', 'EP000007', '2024-10-18 15:00:00', 45600*25000);

-- Dữ liệu bảng invoiceDetail
INSERT INTO invoiceDetail (invoiceID, productID, price, quantity, cost) VALUES
('INV000001', 'PD000001', 216000000, 1, 216000000),
('INV000001', 'PD000002', 324000000, 1, 324000000),
('INV000002', 'PD000003', 340000000, 2, 680000000),
('INV000003', 'PD000004', 432000000, 2, 864000000),
('INV000003', 'PD000005', 247000000, 1, 247000000),
('INV000003', 'PD000006', 249000000, 1, 249000000),
('INV000004', 'PD000007', 3040000000, 2, 6080000000),
('INV000005', 'PD000008', 165000000, 2, 330000000),
('INV000005', 'PD000009', 330000000, 1, 330000000),
('INV000006', 'PD000010', 4800000000, 2, 9600000000),
('INV000007', 'PD000011', 120000000, 1, 120000000),
('INV000007', 'PD000012', 120000000, 1, 120000000),
('INV000008', 'PD000013', 500000000, 1, 500000000),
('INV000008', 'PD000014', 500000000, 1, 500000000),
('INV000008', 'PD000015', 500000000, 1, 500000000),
('INV000009', 'PD000016', 480000000, 1, 480000000),
('INV000009', 'PD000017', 480000000, 1, 480000000),
('INV000010', 'PD000018', 280000000, 2, 560000000),
('INV000010', 'PD000019', 140000000, 1, 140000000),
('INV000010', 'PD000020', 140000000, 1, 140000000),
('INV000011', 'PD000021', 540000000, 1, 540000000),
('INV000012', 'PD000022', 300000000, 1, 300000000),
('INV000013', 'PD000023', 337500000, 1, 337500000),
('INV000013', 'PD000024', 337500000, 1, 337500000),
('INV000014', 'PD000025', 1440000000, 1, 1440000000),
('INV000015', 'PD000026', 5760000000, 1, 5760000000),
('INV000016', 'PD000001', 216000000, 1, 216000000),
('INV000016', 'PD000002', 324000000, 2, 648000000),
('INV000017', 'PD000003', 340000000, 1, 340000000),
('INV000018', 'PD000004', 304000000, 1, 304000000),
('INV000018', 'PD000005', 165000000, 2, 330000000),
('INV000018', 'PD000006', 450000000, 1, 450000000),
('INV000019', 'PD000007', 640000000, 1, 640000000),
('INV000019', 'PD000008', 750000000, 1, 750000000),
('INV000020', 'PD000009', 204000000, 1, 204000000),
('INV000021', 'PD000010', 280000000, 1, 280000000),
('INV000022', 'PD000011', 120000000, 1, 120000000),
('INV000022', 'PD000012', 240000000, 1, 240000000),
('INV000023', 'PD000013', 200000000, 1, 200000000),
('INV000023', 'PD000014', 250000000, 1, 250000000),
('INV000023', 'PD000015', 300000000, 1, 300000000),
('INV000024', 'PD000016', 500000000, 1, 500000000),
('INV000025', 'PD000017', 172800000, 1, 172800000),
('INV000025', 'PD000018', 280000000, 1, 280000000),
('INV000026', 'PD000019', 490000000, 1, 490000000),
('INV000027', 'PD000020', 204000000, 1, 204000000),
('INV000027', 'PD000021', 240000000, 1, 240000000),
('INV000028', 'PD000022', 150000000, 1, 150000000),
('INV000029', 'PD000023', 250000000, 1, 250000000),
('INV000029', 'PD000024', 240000000, 1, 240000000),
('INV000029', 'PD000025', 300000000, 1, 300000000),
('INV000030', 'PD000026', 140000000, 1, 140000000),
('INV000031', 'PD000001', 21600*25000, 1, 21600*25000),
('INV000032', 'PD000002', 34000*25000, 1, 34000*25000),
('INV000033', 'PD000003', 64600*25000, 1, 64600*25000),
('INV000034', 'PD000004', 304000*25000, 1, 304000*25000),
('INV000035', 'PD000005', 16500*25000, 1, 16500*25000),
('INV000036', 'PD000006', 329000*25000, 1, 329000*25000),
('INV000037', 'PD000007', 45600*25000, 1, 45600*25000);

-- Dữ liệu bảng functional
INSERT INTO functional (functionID, functionName) VALUES
('FT000001', 'Quản lý khách hàng'),
('FT000002', 'Quản lý nhà cung cấp'),
('FT000003', 'Quản lý nhân viên'),
('FT000004', 'Quản lý nhập hàng'),
('FT000005', 'Quản lý nhóm quyền'),
('FT000006', 'Quản lý sản phẩm'),
('FT000007', 'Quản lý thống kê'),
('FT000008', 'Quản lý thuộc tính'),
('FT000009', 'Quản lý bán hàng');

-- Dữ liệu bảng rightsGroup
INSERT INTO permission (permissionID, permissionName, creationDate) VALUES
('PMS000001', 'Admin', '2017-12-06 15:00:00'),
('PMS000002', 'Nhân viên nhập hàng', '2017-12-10 16:30:00'),
('PMS000003', 'Nhân viên bán hàng', '2018-05-01 17:00:00');

-- Dữ liệu bảng rightsGroupDetail
INSERT INTO permissionDetail (permissionID, functionID, action) VALUES
('PMS000001', 'FT000001', 'view'),
('PMS000001', 'FT000001', 'create'),
('PMS000001', 'FT000001', 'update'),
('PMS000001', 'FT000002', 'view'),
('PMS000001', 'FT000002', 'create'),
('PMS000001', 'FT000002', 'update'),
('PMS000001', 'FT000003', 'view'),
('PMS000001', 'FT000003', 'create'),
('PMS000001', 'FT000003', 'update'),
('PMS000001', 'FT000004', 'create'),
('PMS000001', 'FT000004', 'update'),
('PMS000001', 'FT000004', 'view'),
('PMS000001', 'FT000005', 'view'),
('PMS000001', 'FT000005', 'create'),
('PMS000001', 'FT000005', 'update'),
('PMS000001', 'FT000006', 'view'),
('PMS000001', 'FT000006', 'create'),
('PMS000001', 'FT000006', 'update'),
('PMS000001', 'FT000007', 'create'),
('PMS000001', 'FT000007', 'update'),
('PMS000001', 'FT000007', 'view'),
('PMS000001', 'FT000008', 'view'),
('PMS000001', 'FT000008', 'create'),
('PMS000001', 'FT000008', 'update'),
('PMS000001', 'FT000009', 'create'),
('PMS000001', 'FT000009', 'update'),
('PMS000001', 'FT000009', 'view'),
('PMS000002', 'FT000002', 'view'),
('PMS000002', 'FT000002', 'create'),
('PMS000002', 'FT000002', 'update'),
('PMS000002', 'FT000004', 'view'),
('PMS000002', 'FT000004', 'create'),
('PMS000002', 'FT000004', 'update'),
('PMS000002', 'FT000006', 'view'),
('PMS000002', 'FT000006', 'create'),
('PMS000002', 'FT000006', 'update'),
('PMS000002', 'FT000008', 'create'),
('PMS000002', 'FT000008', 'update'),
('PMS000002', 'FT000008', 'view'),
('PMS000003', 'FT000001', 'view'),
('PMS000003', 'FT000001', 'create'),
('PMS000003', 'FT000001', 'update'),
('PMS000003', 'FT000006', 'view'),
('PMS000003', 'FT000006', 'update'),
('PMS000003', 'FT000009', 'create'),
('PMS000003', 'FT000009', 'update'),
('PMS000003', 'FT000009', 'view');