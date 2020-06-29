CREATE TABLE `contacts` (
`contactId` VARCHAR(200) AUTO_INCREMENT PRIMARY KEY,
`contactName` VARCHAR(200) NOT NULL,
`mobile_number` INT NOT NULL,
`contactEmail` VARCHAR(100),
`nickName` VARCHAR(40)
);
