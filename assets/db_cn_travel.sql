CREATE TABLE tb_users (
                          id INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
                          username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
                          password VARCHAR(255) NOT NULL COMMENT '密码',
                          nick_name VARCHAR(50) COMMENT '昵称',
                          avatar VARCHAR(255) COMMENT '头像',
                          real_name VARCHAR(50) COMMENT '真实姓名',
                          identity_id VARCHAR(10) COMMENT '身份证号',
                          email VARCHAR(100) UNIQUE COMMENT '电子邮件地址',
                          phone VARCHAR(11) UNIQUE COMMENT '手机号码',
                          status INT DEFAULT 1 NOT NULL COMMENT '状态(1 正常/2 异常)',
                          created_by VARCHAR(50) COMMENT '创建人',
                          created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          updated_by VARCHAR(50) COMMENT '更新人',
                          updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          version INT DEFAULT 1 COMMENT '版本号',
                          is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除'
) COMMENT = '用户表';

CREATE TABLE tb_admins (
                           id INT AUTO_INCREMENT PRIMARY KEY COMMENT '管理员ID',
                           username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
                           password VARCHAR(255) NOT NULL COMMENT '密码（加密存储）',
                           nick_name VARCHAR(50) COMMENT '昵称',
                           avatar VARCHAR(255) COMMENT '头像',
                           created_by VARCHAR(50) COMMENT '创建人',
                           created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           updated_by VARCHAR(50) COMMENT '更新人',
                           updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           version INT DEFAULT 1 COMMENT '版本号',
                           is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除'
) COMMENT = '管理员表';

CREATE TABLE tb_roles (
                          id INT AUTO_INCREMENT PRIMARY KEY COMMENT '角色ID',
                          role_name VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
                          description VARCHAR(255) COMMENT '描述',
                          created_by VARCHAR(50) COMMENT '创建人',
                          created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          updated_by VARCHAR(50) COMMENT '更新人',
                          updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          version INT DEFAULT 1 COMMENT '版本号',
                          is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除'
) COMMENT = '角色表';

CREATE TABLE tb_permissions (
                                id INT AUTO_INCREMENT PRIMARY KEY COMMENT '权限ID',
                                permission_name VARCHAR(50) NOT NULL UNIQUE COMMENT '权限名称',
                                description VARCHAR(255) COMMENT '描述',
                                created_by VARCHAR(50) COMMENT '创建人',
                                created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                updated_by VARCHAR(50) COMMENT '更新人',
                                updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                version INT DEFAULT 1 COMMENT '版本号',
                                is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除'
) COMMENT = '权限表';


CREATE TABLE tb_admins_roles (
                                 id INT AUTO_INCREMENT PRIMARY KEY COMMENT '关联ID',
                                 admin_id INT NOT NULL COMMENT '管理员ID',
                                 role_id INT NOT NULL COMMENT '角色ID',
                                 created_by VARCHAR(50) COMMENT '创建人',
                                 created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 updated_by VARCHAR(50) COMMENT '更新人',
                                 updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 version INT DEFAULT 1 COMMENT '版本号',
                                 is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除',
                                 FOREIGN KEY (admin_id) REFERENCES tb_admins(id),
                                 FOREIGN KEY (role_id) REFERENCES tb_roles(id)
) COMMENT = '管理员角色关联表';

CREATE TABLE tb_roles_permissions (
                                      id INT AUTO_INCREMENT PRIMARY KEY COMMENT '关联ID',
                                      role_id INT NOT NULL COMMENT '角色ID',
                                      permission_id INT NOT NULL COMMENT '权限ID',
                                      created_by VARCHAR(50) COMMENT '创建人',
                                      created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      updated_by VARCHAR(50) COMMENT '更新人',
                                      updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      version INT DEFAULT 1 COMMENT '版本号',
                                      is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除',
                                      FOREIGN KEY (role_id) REFERENCES tb_roles(id),
                                      FOREIGN KEY (permission_id) REFERENCES tb_permissions(id)
) COMMENT = '角色权限关联表';

CREATE TABLE tb_trains (
                           id INT AUTO_INCREMENT PRIMARY KEY COMMENT '车次ID',
                           train_number VARCHAR(50) NOT NULL UNIQUE COMMENT '车次编号（如G123）',
                           train_type ENUM('高铁', '动车', '普快') NOT NULL COMMENT '车型（高铁/动车/普快）',
                           start_station_id INT NOT NULL COMMENT '始发站ID',
                           end_station_id INT NOT NULL COMMENT '终点站ID',
                           total_seats INT NOT NULL COMMENT '总座位数',
                           duration TIME NOT NULL COMMENT '运行时长',
                           status INT DEFAULT 1 NOT NULL COMMENT '状态（1 运营中/2 维护中）',
                           created_by VARCHAR(50) COMMENT '创建人',
                           created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           updated_by VARCHAR(50) COMMENT '更新人',
                           updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           version INT DEFAULT 1 COMMENT '版本号',
                           is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除'
) COMMENT='车次表';

CREATE TABLE tb_stations (
                             id INT AUTO_INCREMENT PRIMARY KEY COMMENT '站点ID',
                             station_name VARCHAR(100) NOT NULL UNIQUE COMMENT '站点名称',
                             city VARCHAR(100) NOT NULL COMMENT '所属城市',
                             longitude DECIMAL(9, 6) COMMENT '经度',
                             latitude DECIMAL(8, 6) COMMENT '纬度',
                             created_by VARCHAR(50) COMMENT '创建人',
                             created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             updated_by VARCHAR(50) COMMENT '更新人',
                             updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             version INT DEFAULT 1 COMMENT '版本号',
                             is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除'
) COMMENT = '站点表';

CREATE TABLE tb_train_schedules (
                                    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '时刻ID',
                                    train_id INT NOT NULL COMMENT '车次ID',
                                    station_id INT NOT NULL COMMENT '站点ID',
                                    departure_time TIME COMMENT '发车时间',
                                    arrival_time TIME COMMENT '到达时间',
                                    stay_duration TIME COMMENT '停留时长',
                                    station_order INT NOT NULL COMMENT '站点顺序',
                                    accumulate_mileage INT NOT NULL COMMENT '累计里程(公里)',
                                    created_by VARCHAR(50) COMMENT '创建人',
                                    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    updated_by VARCHAR(50) COMMENT '更新人',
                                    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    version INT DEFAULT 1 COMMENT '版本号',
                                    is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除',
                                    FOREIGN KEY (train_id) REFERENCES tb_trains(id),
                                    FOREIGN KEY (station_id) REFERENCES tb_stations(id)
) COMMENT = '车次时刻表';

CREATE TABLE tb_seat_types (
                               id INT AUTO_INCREMENT PRIMARY KEY COMMENT '座位类型ID',
                               type_name VARCHAR(50) NOT NULL UNIQUE COMMENT '类型名称（如一等座、二等座）',
                               base_price_factor DECIMAL(5, 2) NOT NULL COMMENT '基础价格系数',
                               description VARCHAR(255) COMMENT '描述',
                               created_by VARCHAR(50) COMMENT '创建人',
                               created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               updated_by VARCHAR(50) COMMENT '更新人',
                               updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               version INT DEFAULT 1 COMMENT '版本号',
                               is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除'
) COMMENT = '座位类型表';

CREATE TABLE tb_seat_inventory (
                                   id INT AUTO_INCREMENT PRIMARY KEY COMMENT '库存ID',
                                   train_id INT NOT NULL COMMENT '车次ID',
                                   departure_date DATE NOT NULL COMMENT '出发日期',
                                   seat_type_id INT NOT NULL COMMENT '座位类型ID',
                                   available_seats INT NOT NULL COMMENT '当前余票数量',
                                   created_by VARCHAR(50) COMMENT '创建人',
                                   created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   updated_by VARCHAR(50) COMMENT '更新人',
                                   updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   version INT DEFAULT 1 COMMENT '版本号',
                                   is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除',
                                   FOREIGN KEY (train_id) REFERENCES tb_trains(id),
                                   FOREIGN KEY (seat_type_id) REFERENCES tb_seat_types(id)
) COMMENT = '座位库存表';

CREATE TABLE tb_carriage_config (
                                    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '配置ID',
                                    train_id INT NOT NULL COMMENT '车次ID',
                                    carriage_number VARCHAR(10) NOT NULL COMMENT '车厢号',
                                    seat_type_id INT NOT NULL COMMENT '座位类型ID',
                                    total_rows INT NOT NULL COMMENT '总排数',
                                    seats_per_row INT NOT NULL COMMENT '每排座位数',
                                    seating_arrangement TEXT COMMENT '座位排列规则([A,B,C,D,E,F])',
                                    created_by VARCHAR(50) COMMENT '创建人',
                                    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    updated_by VARCHAR(50) COMMENT '更新人',
                                    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    version INT DEFAULT 1 COMMENT '版本号',
                                    is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除',
                                    FOREIGN KEY (train_id) REFERENCES tb_trains(id),
                                    FOREIGN KEY (seat_type_id) REFERENCES tb_seat_types(id)
) COMMENT = '车厢配置表(用来生成座位号)';

CREATE TABLE tb_orders (
                           id INT AUTO_INCREMENT PRIMARY KEY COMMENT '订单ID',
                           user_id INT NOT NULL COMMENT '用户ID',
                           order_amount DECIMAL(10, 2) NOT NULL COMMENT '订单金额',
                           order_status INT NOT NULL COMMENT '订单状态(1 待支付,2 已支付,3 已取消,4 已改签,5 已退票)',
                           payment_deadline TIMESTAMP COMMENT '支付截止时间',
                           contact_info VARCHAR(255) COMMENT '联系人信息',
                           created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           created_by VARCHAR(50) COMMENT '创建人',
                           updated_by VARCHAR(50) COMMENT '更新人',
                           updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           version INT DEFAULT 1 COMMENT '版本号',
                           is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除',
                           FOREIGN KEY (user_id) REFERENCES tb_users(id)
) COMMENT = '订单表';

CREATE TABLE tb_order_details (
                                  id INT AUTO_INCREMENT PRIMARY KEY COMMENT '详情ID',
                                  order_id INT NOT NULL COMMENT '订单ID',
                                  train_id INT NOT NULL COMMENT '车次ID',
                                  departure_date DATE NOT NULL COMMENT '出发日期',
                                  seat_type_id INT NOT NULL COMMENT '座位类型ID',
                                  passenger_name VARCHAR(100) NOT NULL COMMENT '乘客姓名',
                                  passenger_identity_id VARCHAR(18) NOT NULL COMMENT '乘客身份证号',
                                  seat_number VARCHAR(50) COMMENT '座位号（可选）',
                                  fare DECIMAL(10, 2) NOT NULL COMMENT '票价',
                                  travel_status INT NOT NULL COMMENT '乘车状态(1 已乘车,2 未乘车)',
                                  created_by VARCHAR(50) COMMENT '创建人',
                                  created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  updated_by VARCHAR(50) COMMENT '更新人',
                                  updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  version INT DEFAULT 1 COMMENT '版本号',
                                  is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除',
                                  FOREIGN KEY (order_id) REFERENCES tb_orders(id),
                                  FOREIGN KEY (train_id) REFERENCES tb_trains(id),
                                  FOREIGN KEY (seat_type_id) REFERENCES tb_seat_types(id)
) COMMENT = '订单详情表';

CREATE TABLE tb_payments (
                             id INT AUTO_INCREMENT PRIMARY KEY COMMENT '支付ID',
                             order_id INT NOT NULL COMMENT '订单ID',
                             payment_method INT NOT NULL COMMENT '支付方式(微信, 支付宝, 银行卡)',
                             payment_amount DECIMAL(10, 2) NOT NULL COMMENT '支付金额',
                             payment_status INT NOT NULL COMMENT '支付状态(成功, 失败)',
                             payment_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '支付时间',
                             third_party_transaction_id VARCHAR(100) COMMENT '第三方交易号',
                             created_by VARCHAR(50) COMMENT '创建人',
                             created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             updated_by VARCHAR(50) COMMENT '更新人',
                             updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             version INT DEFAULT 1 COMMENT '版本号',
                             is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除',
                             FOREIGN KEY (order_id) REFERENCES tb_orders(id)
) COMMENT = '支付记录表';

CREATE TABLE tb_refund_rules (
                                 id INT AUTO_INCREMENT PRIMARY KEY COMMENT '规则ID',
                                 train_type ENUM('高铁', '动车', '普快') NOT NULL COMMENT '车次类型',
                                 seat_type_id INT NOT NULL COMMENT '座位类型ID',
                                 refund_fee_percentage DECIMAL(5, 2) NOT NULL COMMENT '退票手续费比例',
                                 rescheduling_cutoff_time TIME NOT NULL COMMENT '改签截止时间（如发车前30分钟）',
                                 rule_effective_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '规则生效时间',
                                 created_by VARCHAR(50) COMMENT '创建人',
                                 created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 updated_by VARCHAR(50) COMMENT '更新人',
                                 updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 version INT DEFAULT 1 COMMENT '版本号',
                                 is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除',
                                 FOREIGN KEY (seat_type_id) REFERENCES tb_seat_types(id)
) COMMENT = '退改签规则表(保留但暂时不用)';

CREATE TABLE tb_news (
                         id INT AUTO_INCREMENT PRIMARY KEY COMMENT '资讯ID',
                         title VARCHAR(255) NOT NULL COMMENT '标题',
                         content TEXT NOT NULL COMMENT '内容',
                         category INT NOT NULL COMMENT '分类（1 公告/2 活动/3 政策）',
                         publish_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
                         status ENUM('显示', '隐藏') NOT NULL COMMENT '状态（显示/隐藏）',
                         created_by VARCHAR(50) COMMENT '创建人',
                         created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         updated_by VARCHAR(50) COMMENT '更新人',
                         updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         version INT DEFAULT 1 COMMENT '版本号',
                         is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除'
) COMMENT = '资讯表';

CREATE TABLE tb_logs (
                         id INT AUTO_INCREMENT PRIMARY KEY COMMENT '日志ID',
                         operation_type ENUM('用户注册', '订单创建', '支付成功', '其他') NOT NULL COMMENT '操作类型',
                         operation_content TEXT NOT NULL COMMENT '操作内容',
                         operator_id INT NOT NULL COMMENT '操作人（用户ID或管理员ID）',
                         operation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
                         ip_address VARCHAR(50) COMMENT 'IP地址',
                         created_by VARCHAR(50) COMMENT '创建人',
                         created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         updated_by VARCHAR(50) COMMENT '更新人',
                         updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         version INT DEFAULT 1 COMMENT '版本号',
                         is_delete BOOLEAN DEFAULT FALSE COMMENT '是否删除'
) COMMENT = '操作日志表';