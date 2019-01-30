DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getEmployeeCount`(OUT count int)
BEGIN
	select count(name) into count from employee;
END$$
DELIMITER ;
