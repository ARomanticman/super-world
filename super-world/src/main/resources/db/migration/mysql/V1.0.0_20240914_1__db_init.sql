CREATE TABLE `enum_data` (
      `id` VARCHAR(32) NOT NULL COMMENT '主键',
      `type` VARCHAR(64) NOT NULL COMMENT '类型',
      `name` VARCHAR(64) NOT NULL COMMENT '名称',
      `code` INT NOT NULL COMMENT '值',
      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='枚举表';

insert into enum_data (id, type, name, code) values ('7f3b9f0a2c9f4f56b12d0a45cb8b4f65', 'componentType', 'Vertica', 1);
insert into enum_data (id, type, name, code) values ('5b1c4a723b9a4a3f9f9d4e9a7e9f3b34', 'componentType', '社保费', 2);
insert into enum_data (id, type, name, code) values ('93bcff34d2c94f4ea4b9f9a9d4f3b34c', 'componentType', 'Impala', 3);
insert into enum_data (id, type, name, code) values ('4a7b0a2c9f3b4d26a9f9a9d4f3b34d2e', 'componentType', '大数据门户', 4);
insert into enum_data (id, type, name, code) values ('2f3c9f0a2c9f4f56b12d0a45cb8b4e13', 'componentType', '接口平台', 5);
insert into enum_data (id, type, name, code) values ('8f4a9f0a2c9f4f56b12d0a45cb8b4f65', 'componentType', 'HUE', 6);
insert into enum_data (id, type, name, code) values ('6b3c4a723b9a4a3f9f9d4e9a7e9f3b34', 'distinguishType', '异常行为', 1);
insert into enum_data (id, type, name, code) values ('92bcff34d2c94f4ea4b9f9a9d4f3b354', 'distinguishType', '敏感数据疑似泄露', 2);
insert into enum_data (id, type, name, code) values ('4d7e0a2c9f3b4d26a9f9a9d4f3b34d2e', 'distinguishType', '网络攻击', 3);
insert into enum_data (id, type, name, code) values ('3f4c9f0a2c9f4f56b12d0a45cb8b4ac5', 'distinguishType', '爬虫机器人访问', 4);
insert into enum_data (id, type, name, code) values ('7b2a4a723b9a4a3f9f9d4e9a7e9f3b12', 'isNotified', '未通知', 0);
insert into enum_data (id, type, name, code) values ('93dcff34d2c94f4ea4b9f9a9d4f3b34a', 'isNotified', '通知中', 1);
insert into enum_data (id, type, name, code) values ('4b7d0a2c9f3b4d26a9f9a9d4f3b34d24', 'isNotified', '通知成功', 2);
insert into enum_data (id, type, name, code) values ('2e3f9f0a2c9f4f56b12d0a45cb8b4c96', 'isNotified', '通知失败', 3);