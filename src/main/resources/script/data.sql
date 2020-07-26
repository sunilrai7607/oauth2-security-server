
INSERT INTO PERMISSION (NAME) VALUES
('can_create_user'),
('can_update_user'),
('can_read_user'),
('can_delete_user');


INSERT INTO ROLE (NAME) VALUES
('role_admin'),('role_user');

INSERT INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES
    (1,1), /* can_create_user assigned to role_admin */
    (2,1), /* can_update_user assigned to role_admin */
    (3,1), /* can_read_user assigned to role_admin */
    (4,1), /* can_delete_user assigned to role_admin */
    (3,2);  /* can_read_user assigned to role_user */


	INSERT INTO ACCOUNT (
    user_name,PASSWORD,
    EMAIL,ENABLED,ACCOUNT_EXPIRED,CREDENTIALS_EXPIRED,ACCOUNT_LOCKED)
    VALUES
    (
    'admin','admin',
    'sunilultra@gmail.com',1,1,1,1),
    ('user','$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi',
    'sunilultra@gmail.com',1,1,1,1);


    INSERT INTO ROLE_USER (ROLE_ID, USER_ID)
    VALUES
    (1, 1) /* role_admin assigned to admin user */,
    (2, 2) /* role_user assigned to user user */ ;


 INSERT INTO OAUTH_CLIENT_DETAILS (
	CLIENT_ID,CLIENT_SECRET,
	RESOURCE_IDS,
	SCOPE,
	AUTHORIZED_GRANT_TYPES,
	WEB_SERVER_REDIRECT_URI,AUTHORITIES,
	ACCESS_TOKEN_VALIDITY,REFRESH_TOKEN_VALIDITY,
	ADDITIONAL_INFORMATION,AUTOAPPROVE)
	VALUES
	(
    'c825bfbd-c202-40a9-8fcd-d9386195687a','$2y$12$olTuy84WC.rsx0/BNg4TDOqE2uiOd.5g5UK6eMwGqpWiRINAywBG6',
	'USER_CLIENT_RESOURCE,USER_ADMIN_RESOURCE',
	'role_admin,role_user',
	'authorization_code,password,refresh_token,implicit',
	NULL,NULL,
	900,3600,
	'{}',NULL);