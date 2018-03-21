CREATE TABLE `project_plugin` (
  `id`  int NOT NULL AUTO_INCREMENT ,
  `projectId`  char(12) CHARACTER SET utf8 NOT NULL ,
  `pluginId`  varchar(32) CHARACTER SET utf8 NOT NULL ,
  `createTime`  datetime NOT NULL ,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET=utf8
  COMMENT='项目插件关联表'
;

