insert into `user`(id, data_id, username, display_name, super_user, use_state, active_time, invalid_time, create_time, password_salt, password_md5)
values(1, 'admin', 'admin', '管理员', false, 1, now(), -1, now(), 'ABCDEFG', 'this is password md5');

insert into role(id, data_id, role_name)
values(1, 'role_admin', '管理员角色');
insert into role(id, data_id, role_name)
values(2, 'role_admin_2', '管理员角色2');

insert into permission_resource(id, data_id, resource_id, resource_name, resource_type, resource_value, parent_resource_data_id, serial_number)
values(1, 'resource_1', 'index', '首页', 2, '/index', 'ROOT', 1);

insert into rel_role_resource(id, data_id, role_data_id, resource_data_id)
values(1, 'rel_role_resource_1', 'role_admin', 'resource_1');

insert into rel_user_role(id, data_id, user_data_id, role_data_id)
values(1, 'rel_user_role_1', 'admin', 'role_admin');
insert into rel_user_role(id, data_id, user_data_id, role_data_id)
values(2, 'rel_user_role_2', 'admin', 'role_admin_2');