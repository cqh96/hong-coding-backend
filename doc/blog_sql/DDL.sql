-- 用户表
CREATE TABLE `user` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                          `username` varchar(50) NOT NULL COMMENT '用户名',
                          `password` varchar(100) NOT NULL COMMENT '密码',
                          `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
                          `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
                          `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
                          `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
                          `status` tinyint(4) DEFAULT '1' COMMENT '状态：0-禁用，1-正常',
                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `uk_username` (`username`),
                          KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
CREATE TABLE `role` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                          `name` varchar(50) NOT NULL COMMENT '角色名称',
                          `code` varchar(50) NOT NULL COMMENT '角色编码',
                          `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 用户角色关联表
CREATE TABLE `user_role` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                               `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                               `role_id` bigint(20) NOT NULL COMMENT '角色ID',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uk_user_role` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 文章表（按年份分表）
CREATE TABLE `article_2024` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                  `title` varchar(255) NOT NULL COMMENT '文章标题',
                                  `content` longtext NOT NULL COMMENT '文章内容',
                                  `summary` varchar(500) DEFAULT NULL COMMENT '文章摘要',
                                  `category_id` bigint(20) DEFAULT NULL COMMENT '分类ID',
                                  `tags` varchar(255) DEFAULT NULL COMMENT '标签，多个用逗号分隔',
                                  `view_count` int(11) DEFAULT '0' COMMENT '浏览量',
                                  `like_count` int(11) DEFAULT '0' COMMENT '点赞数',
                                  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
                                  `status` tinyint(4) DEFAULT '1' COMMENT '状态：0-草稿，1-已发布',
                                  `author_name` varchar(50) NOT NULL COMMENT '作者名称',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
                                  PRIMARY KEY (`id`),
                                  KEY `idx_category` (`category_id`),
                                  KEY `idx_create_time` (`create_time`),
                                  FULLTEXT KEY `idx_title_content` (`title`,`content`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表2024';

-- 分类表
CREATE TABLE `category` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                              `name` varchar(50) NOT NULL COMMENT '分类名称',
                              `description` varchar(255) DEFAULT NULL COMMENT '分类描述',
                              `sort` int(11) DEFAULT '0' COMMENT '排序',
                              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类表';

-- 搜索历史表（按用户ID分片）
CREATE TABLE `search_history_0` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                      `keyword` varchar(100) NOT NULL COMMENT '搜索关键词',
                                      `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                                      `search_count` int(11) DEFAULT '1' COMMENT '搜索次数',
                                      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
                                      PRIMARY KEY (`id`),
                                      KEY `idx_user_keyword` (`user_id`,`keyword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='搜索历史表0';

-- 热门搜索表
CREATE TABLE `hot_search` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                `keyword` varchar(100) NOT NULL COMMENT '搜索关键词',
                                `search_count` int(11) DEFAULT '0' COMMENT '搜索次数',
                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                PRIMARY KEY (`id`),
                                KEY `idx_search_count` (`search_count` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='热门搜索表';