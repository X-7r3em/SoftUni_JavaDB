ALTER TABLE `minions`
ADD COLUMN `town_id` INT;
ALTER TABLE `minions`
ADD CONSTRAINT `foreign_key`
  FOREIGN KEY (`town_id`)
  REFERENCES `towns` (`id`);
