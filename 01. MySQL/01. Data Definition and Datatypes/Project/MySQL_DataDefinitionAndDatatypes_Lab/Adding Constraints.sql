ALTER TABLE `products` 
ADD CONSTRAINT `foreign_key`
  FOREIGN KEY (`category_id`)
  REFERENCES `categories` (`id`);