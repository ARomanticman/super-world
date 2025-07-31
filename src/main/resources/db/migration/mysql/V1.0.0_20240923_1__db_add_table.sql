CREATE TABLE `test_flyway` (
      `id` VARCHAR(32) NOT NULL COMMENT '主键',
      `type` VARCHAR(64) NOT NULL COMMENT '类型',
      `name` VARCHAR(64) NOT NULL COMMENT '名称',
      `code` INT NOT NULL COMMENT '值',
      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='枚举表';