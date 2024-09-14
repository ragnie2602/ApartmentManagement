CREATE DATABASE ApartmentManagement
GO
USE ApartmentManagement
GO

CREATE TABLE [User] (
    userId NUMERIC IDENTITY(1,1) NOT NULL,
    [name] NVARCHAR(50),
    birthday DATE,
    phoneNumber INT,
    [image] VARBINARY(MAX),
    [address] NVARCHAR(120)
)
ALTER TABLE [User] ADD CONSTRAINT pk_User PRIMARY KEY (userId)
ALTER TABLE [User] ADD CONSTRAINT unique_User_phoneNumber UNIQUE (phoneNumber)

CREATE TABLE [Login] (
    userId NUMERIC NOT NULL,
    username VARCHAR(100),
    [password] VARCHAR(100),
)
ALTER TABLE [Login] ADD CONSTRAINT pk_Login PRIMARY KEY (userId)
ALTER TABLE [Login] ADD CONSTRAINT unique_Login_username UNIQUE (username)
ALTER TABLE [Login] ADD CONSTRAINT fk_Login_User FOREIGN KEY (userId) REFERENCES [User](userId)

CREATE TABLE Apartment (
    apartmentId INT NOT NULL,
    ownerId BIGINT,
    area FLOAT,
)
ALTER TABLE Apartment ADD CONSTRAINT pk_Apartment PRIMARY KEY (apartmentId)

CREATE TABLE Resident (
    id BIGINT NOT NULL,
    [name] NVARCHAR(50),
    birthday DATE,
    gender BIT,
    phoneNumber INT,
    nationality NVARCHAR(50),
    ethnic NVARCHAR(50),
    apartmentId INT,
    relationship NVARCHAR(50),
    [status] INT
)
ALTER TABLE Resident ADD CONSTRAINT pk_Resident PRIMARY KEY (id)
ALTER TABLE Resident ADD CONSTRAINT fk_Resident_Apartment FOREIGN KEY (apartmentId) REFERENCES Apartment(apartmentId)

CREATE TABLE Activity (
    id NUMERIC IDENTITY(1, 1) NOT NULL,
    residentId BIGINT,
    [status] INT, -- Thường trú: 0, tạm trú: 1, tạm vắng: 2, cút: 3, dời hộ: 4
    timeIn DATE,
    [timeOut] DATE,
    note NVARCHAR(4000),
)
ALTER TABLE Activity ADD CONSTRAINT pk_Activity PRIMARY KEY (id)
ALTER TABLE Activity ADD CONSTRAINT fk_Activity_Resident FOREIGN KEY (residentId) REFERENCES Resident(id)

CREATE TABLE Fee (
    id NUMERIC IDENTITY(1, 1) NOT NULL,
    [name] NVARCHAR(50),
    cost INT,
    mandatory BIT,
    cycle INT,
    expiration DATE,
    [status] BIT
)
ALTER TABLE Fee ADD CONSTRAINT pk_id PRIMARY KEY (id)

CREATE TABLE Payment (
    paymentId NUMERIC IDENTITY(1, 1) NOT NULL,
    apartmentId INT NOT NULL,
    feeId NUMERIC NOT NULL,
    payee NVARCHAR(100),
    [number] INT,
    timeValidate DATE,
    [month] INT,
    [year] INT,
    paid BIGINT
    
)
ALTER TABLE Payment ADD CONSTRAINT pk_Payment PRIMARY KEY (paymentId)
ALTER TABLE Payment ADD CONSTRAINT fk_Payment_Apartment FOREIGN KEY (apartmentId) REFERENCES Apartment(apartmentId)
ALTER TABLE Payment ADD CONSTRAINT fk_Payment_Fee FOREIGN KEY (feeId) REFERENCES Fee(id)

CREATE TABLE Vehicle (
    license_plates VARCHAR(9) NOT NULL,
    apartmentId INT,
    [type] INT, -- 0: Xe máy, 1: Ô tô
)
ALTER TABLE Vehicle ADD CONSTRAINT pk_Vehicle PRIMARY KEY (license_plates)
ALTER TABLE Vehicle ADD CONSTRAINT fk_Vehicle_Apartment FOREIGN KEY (apartmentId) REFERENCES Apartment(apartmentId)

INSERT INTO [User]([name], birthday, phoneNumber, [image], [address]) VALUES (N'Phạm Hoàng Thành', '2023-06-15', 966322513, null, N'Số 1, Đại Cồ Việt, Hai Bà Trưng, Hà Nội')

INSERT INTO [Login](userId, username, [password]) VALUES (01, 'admin', 'admin')

INSERT INTO Fee([name], cost, mandatory, cycle, expiration, [status]) VALUES
(N'Phí gửi xe máy', 70000, 1, 1, null, 1),
(N'Phí gửi ô tô', 1200000, 1, 1, null, 1),
(N'Tiền điện', 3500, 1, 1, null, 1),
(N'Tiền nước', 40000, 1, 1, null, 1),
(N'Phí Internet', 180000, 1, 1, null, 1)

INSERT INTO Apartment(apartmentId, ownerId, area) VALUES
(601, NULL, 80),
(602, NULL, 80),
(603, NULL, 80),
(604, NULL, 80),
(605, NULL, 80),
(701, NULL, 80),
(702, NULL, 80),
(703, NULL, 80),
(704, NULL, 80),
(705, NULL, 80),
(801, NULL, 80),
(802, NULL, 80),
(803, NULL, 80),
(804, NULL, 80),
(805, NULL, 80),
(901, NULL, 80),
(902, NULL, 80),
(903, NULL, 80),
(904, NULL, 80),
(905, NULL, 80),
(1001, NULL, 80),
(1002, NULL, 80),
(1003, NULL, 80),
(1004, NULL, 80),
(1005, NULL, 80),
(1101, NULL, 80),
(1102, NULL, 80),
(1103, NULL, 80),
(1104, NULL, 80),
(1105, NULL, 80),
(1201, NULL, 80),
(1202, NULL, 80),
(1203, NULL, 80),
(1204, NULL, 80),
(1205, NULL, 80),
(1301, NULL, 80),
(1302, NULL, 80),
(1303, NULL, 80),
(1304, NULL, 80),
(1305, NULL, 80),
(1401, NULL, 80),
(1402, NULL, 80),
(1403, NULL, 80),
(1404, NULL, 80),
(1405, NULL, 80),
(1501, NULL, 80),
(1502, NULL, 80),
(1503, NULL, 80),
(1504, NULL, 80),
(1505, NULL, 80),
(1601, NULL, 80),
(1602, NULL, 80),
(1603, NULL, 80),
(1604, NULL, 80),
(1605, NULL, 80),
(1701, NULL, 80),
(1702, NULL, 80),
(1703, NULL, 80),
(1704, NULL, 80),
(1705, NULL, 80),
(1801, NULL, 80),
(1802, NULL, 80),
(1803, NULL, 80),
(1804, NULL, 80),
(1805, NULL, 80),
(1901, NULL, 80),
(1902, NULL, 80),
(1903, NULL, 80),
(1904, NULL, 80),
(1905, NULL, 80),
(2001, NULL, 80),
(2002, NULL, 80),
(2003, NULL, 80),
(2004, NULL, 80),
(2005, NULL, 80),
(2101, NULL, 80),
(2102, NULL, 80),
(2103, NULL, 80),
(2104, NULL, 80),
(2105, NULL, 80),
(2201, NULL, 80),
(2202, NULL, 80),
(2203, NULL, 80),
(2204, NULL, 80),
(2205, NULL, 80),
(2301, NULL, 80),
(2302, NULL, 80),
(2303, NULL, 80),
(2304, NULL, 80),
(2305, NULL, 80),
(2401, NULL, 80),
(2402, NULL, 80),
(2403, NULL, 80),
(2404, NULL, 80),
(2405, NULL, 80),
(2501, NULL, 80),
(2502, NULL, 80),
(2503, NULL, 80),
(2504, NULL, 80),
(2505, NULL, 80),
(2601, NULL, 80),
(2602, NULL, 80),
(2603, NULL, 80),
(2604, NULL, 80),
(2605, NULL, 80),
(2701, NULL, 80),
(2702, NULL, 80),
(2703, NULL, 80),
(2704, NULL, 80),
(2705, NULL, 80),
(2801, NULL, 80),
(2802, NULL, 80),
(2803, NULL, 80),
(2804, NULL, 80),
(2805, NULL, 80),
(2901, NULL, 80),
(2902, NULL, 80),
(2903, NULL, 80),
(2904, NULL, 80),
(2905, NULL, 80)

-- INSERT INTO Resident(id, [name], birthday, gender, phoneNumber, nationality, ethnic, apartmentId, relationship) VALUES
-- (2312000100, N'Nguyễn Văn An', '03-01-2003', 0, 0100000001, N'Việt Nam', N'Kinh', 601, N'Chủ hộ'),
-- (2312000101, N'Lộc Thị Vân Anh', '04-02-2003', 1, 0100000002, N'Việt Nam', N'Kinh', 601, N'Vợ'),
-- (2312000102, N'Nguyễn Tuấn Anh', '10-04-2003', 0, 0100000003, N'Việt Nam', N'Kinh', 601, N'Con'),
-- (2312000103, N'Trần Xuân Bách', '10-12-2003', 0, 0100000004, N'Việt Nam', N'Kinh', 601, N'Con'),
-- (2312000104, N'Vũ Trí Bình', '9-09-2003', 0, 0100000005, N'Việt Nam', N'Kinh', 701, N'Chủ hộ'),
-- (2312000105, N'Kim Ngọc Chung', '01-01-2003', 0, 0100000006, N'Việt Nam', N'Kinh', 701, N'Bố'),
-- (2312000106, N'Bùi Quang Dũng', '05-01-2003', 0, 0100000007, N'Việt Nam', N'Kinh', 701, N'Con'),
-- (2312000107, N'Nguyễn Tiến Dũng', '04-11-2003', 0, 0100000008, N'Việt Nam', N'Kinh', 701, N'Con'),
-- (2312000108, N'Trần Đức Duy', '11-01-2003' , 0, 0100000009, N'Việt Nam', N'Kinh', 801, N'Chủ hộ'),
-- (2312000109, N'Đào Nam Dương', '05-12-2003' , 0, 0100000010, N'Việt Nam', N'Kinh', 801, N'bạn'),
-- (2312000110, N'Nguyễn Bình Dương', '04-03-2003', 0, 0100000011, N'Việt Nam', N'Kinh', 801, N'bạn'),
-- (2312000111, N'Phạm Đăng Đạt', '07-06-2002', 0, 0100000012, N'Việt Nam', N'Kinh', 801, N'bạn'),
-- (2312000112, N'Ngô Ngọc Đăng', '02-18-2003', 0, 0100000013, N'Việt Nam', N'Kinh', 901, N'Chủ hộ'),
-- (2312000113, N'Hoàng Minh Đức', '04-12-2003', 0, 0100000014, N'Việt Nam', N'Kinh', 901, N'Con'),
-- (2312000114, N'Lê Hữu Hải', '06-05-2003', 0, 0100000015, N'Việt Nam', N'Kinh', 901, N'Con'),
-- (2312000115, N'Hà Thế Hiển', '12-11-2003', 0, 0100000016, N'Việt Nam', N'Kinh', 901, N'Vợ'),
-- (2312000116, N'Vũ Đức Hiếu', '05-12-2003', 0, 0100000017, N'Việt Nam', N'Kinh', 1001, N'Chủ hộ'),
-- (2312000117, N'Nguyễn Đức Hoàng', '06-03-2003', 0, 0100000018, N'Việt Nam', N'Kinh', 1001, N'Bố'),
-- (2312000118, N'Vũ Trần Hoàng', '02-01-2003', 0, 0100000019, N'Việt Nam', N'Kinh', 1001, N'Mẹ'),
-- (2312000119, N'Trịnh Công Hùng', '07-04-2003', 0, 0100000020, N'Việt Nam', N'Kinh', 1001, N'Em')

-- INSERT INTO Activity(residentId, [status], timeIn, [timeOut], note) VALUES
-- (2312000100, 1, '10-01-2023', NULL, NULL),
-- (2312000101, 1, '10-01-2023', NULL, NULL),
-- (2312000102, 1, '10-01-2023', NULL, NULL),
-- (2312000103, 1, '10-01-2023', NULL, NULL),
-- (2312000104, 1, '10-01-2023', NULL, NULL),
-- (2312000105, 1, '10-01-2023', NULL, NULL),
-- (2312000106, 1, '10-01-2023', NULL, NULL),
-- (2312000107, 1, '10-01-2023', NULL, NULL),
-- (2312000108, 2, '10-01-2023', NULL, NULL),
-- (2312000109, 2, '10-01-2023', NULL, NULL),
-- (2312000110, 2, '10-01-2023', NULL, NULL),
-- (2312000111, 1, '10-01-2023', NULL, NULL),
-- (2312000112, 1, '10-01-2023', NULL, NULL),
-- (2312000113, 1, '10-01-2023', NULL, NULL),
-- (2312000114, 1, '10-01-2023', NULL, NULL),
-- (2312000115, 1, '10-01-2023', NULL, NULL),
-- (2312000116, 1, '10-01-2023', NULL, NULL),
-- (2312000117, 1, '10-01-2023', NULL, NULL),
-- (2312000118, 1, '10-01-2023', NULL, NULL),
-- (2312000119, 1, '10-01-2023', NULL, NULL)

-- INSERT INTO Payment(apartmentId, feeId, [number], timeValidate) VALUES
-- (601, 01, 1, '10-30-2023'),
-- (601, 02, 1, '10-30-2023'),
-- (601, 03, 200, '10-30-2023'),
-- (601, 04, 15, '10-30-2023'),
-- (601, 05, 1, '10-30-2023'),
-- (601, 06, 1, '10-30-2023'),
-- (601, 07, 1, '10-30-2023'),
-- (601, 08, 1, '10-30-2023'),
-- (702, 01, 1, '10-30-2023'),
-- (702, 02, 1, '10-30-2023'),
-- (702, 03, 180, '10-30-2023'),
-- (702, 04, 14, '10-30-2023'),
-- (702, 05, 1, '10-30-2023'),
-- (702, 06, 1, '10-30-2023'),
-- (702, 07, 1, '10-30-2023'),
-- (702, 08, 1, '10-30-2023'),
-- (803, 01, 1, '10-30-2023'),
-- (803, 02, 1, '10-30-2023'),
-- (803, 03, 250, '10-30-2023'),
-- (803, 04, 20, '10-30-2023'),
-- (803, 05, 1, '10-30-2023'),
-- (803, 06, 1, '10-30-2023'),
-- (803, 07, 1, '10-30-2023'),
-- (803, 08, 1, '10-30-2023'),
-- (904, 01, 1, '10-30-2023'),
-- (904, 02, 1, '10-30-2023'),
-- (904, 03, 215, '10-30-2023'),
-- (904, 04, 16, '10-30-2023'),
-- (904, 05, 1, '10-30-2023'),
-- (904, 06, 1, '10-30-2023'),
-- (904, 07, 1, '10-30-2023'),
-- (904, 08, 1, '10-30-2023'),
-- (1005, 01, 1, '10-30-2023'),
-- (1005, 02, 1, '10-30-2023'),
-- (1005, 03, 150, '10-30-2023'),
-- (1005, 04, 10, '10-30-2023'),
-- (1005, 05, 1, '10-30-2023'),
-- (1005, 06, 1, '10-30-2023'),
-- (1005, 07, 1, '10-30-2023'),
-- (1005, 08, 1, '10-30-2023')

-- INSERT INTO Vehicle VALUES
-- ('89H704953', 601, 1),
-- ('29K112345', 601, 2),
-- ('30U111111', 701, 1),
-- ('29K188888', 801, 2),
-- ('29H155555', 901, 1),
-- ('15H144444', 1001, 2)

-- USE master
-- GO
-- DROP DATABASE ApartmentManagement
-- GO

-- BACKUP DATABASE ApartmentManagement TO DISK = 'C:\sqlbackup\ApartmentManagement.bak'