use domemdb;

/* popular tabela de perfis/roles */
insert into user_profile(created_at, type)values (now(), 'SUPER');  
insert into user_profile(created_at, type)values (now(), 'ADMIN');
insert into user_profile(created_at, type)values (now(), 'USER');
insert into user_profile(created_at, type)values (now(), 'MANUT');

/* popular tabela user com um user admin, para criar os outros users usar o gui do domem */
/*password encriptada corresponde a "amac"*/
insert into user(created_at, user_name, pass_word, first_name, last_name, email, telef, job)values (now(),'amac','$2a$10$KhTWO4Eo6yhXtFbCGWPVuuk6kJaN45iEJwj1OQsEULWXipSNfYoR6', 'arnaldo','canelas','amac_03@msn.com','919961694','it guy');
/*password encriptada corresponde a "a"*/
insert into user(created_at, user_name, pass_word, first_name, last_name, email, telef, job)values (now(),'a','$2a$10$SNpvGpBU/fzqr.z3gJZWAuo2lD507wmo6B8hZufR/c0souukGm8va', 'teste','teste','a@msn.com','','teste guy');
/*password encriptada corresponde a "manut"*/
insert into user(created_at, user_name, pass_word, first_name, last_name, email, telef, job)values (now(),'manut','$2a$10$J0183vz77TGVHW77jpXPHOlaHvZwMcOV7I.7RbIct.wLF8WtTBL8S', 'manuten��o da aplica��o web','manuten��o','manutentecao@domem.pt','','Programador');

/* popular tabela de perfis/roles */
insert into user__user_profile (user_id, user_profile_id) select user.id, userprofile.id from user user, user_profile userprofile  where user.user_name='amac' and userprofile.type='super';
insert into user__user_profile (user_id, user_profile_id)  select user.id, userprofile.id from user user, user_profile userprofile  where user.user_name='a' and userprofile.type='user';  
insert into user__user_profile (user_id, user_profile_id)  select user.id, userprofile.id from user user, user_profile userprofile  where user.user_name='manut' and userprofile.type='manut';

/*tarefas de exemplo*/
insert into tarefa (created_at, descricao, userid, estado)values(now(), "fazer websocket do domem!",1,0);
insert into tarefa (created_at, descricao, userid, estado)values(now(), "fazer o relatorio!",1,0);
insert into tarefa (created_at, descricao, userid, estado)values(now(), "comunica com socket do pilight!",2,0);
insert into tarefa (created_at, descricao, userid, estado)values(now(), "alterar password",2,1);
insert into tarefa (created_at, descricao, userid, estado)values(now(), "almocar com maria",1,0);

/*no do pilight de exemplo*/
insert into nodepilight (created_at, uuid, descricao, localizacao, ipaddress, estado)values(now(), "0000-b8-27-eb-28f6aa","domEm-pi_node1","cma","172.28.1.227",1);

/*grupos de dispositivos de exemplo*/
insert into devicegroup (created_at, descricao)values(now(), "Piso 0");
insert into devicegroup (created_at, descricao)values(now(), "Piso 1");
insert into devicegroup (created_at, descricao)values(now(), "Piso 2");

/*tipos de protocolos*/
insert into protocoltype (created_at, descricao)values(now(), "Generic");
insert into protocoltype (created_at, descricao)values(now(), "Network");
insert into protocoltype (created_at, descricao)values(now(), "Weather APIs");
insert into protocoltype (created_at, descricao)values(now(), "Program APIs");
insert into protocoltype (created_at, descricao)values(now(), "Weather Stations");
insert into protocoltype (created_at, descricao)values(now(), "GPIO Connected Sensors");
insert into protocoltype (created_at, descricao)values(now(), "Dimmers");
insert into protocoltype (created_at, descricao)values(now(), "Wireless Contact Sensors");
insert into protocoltype (created_at, descricao)values(now(), "Wireless Motion Sensors");
insert into protocoltype (created_at, descricao)values(now(), "Screens");
insert into protocoltype (created_at, descricao)values(now(), "Switches");
insert into protocoltype (created_at, descricao)values(now(), "Misc");

/*protocolos dos dispositivos*/
insert into deviceprotocol (created_at, protocoltypeid, nome, pilightnome, versaopilight)values(now(), 1,"Dimmer", "dio_switch", "v5.0, v6.0, v7.0");
insert into deviceprotocol (created_at, protocoltypeid, nome, pilightnome, versaopilight)values(now(), 1,"Generic Switch","generic_switch", "v5.0, v6.0, v7.0");   
insert into deviceprotocol (created_at, protocoltypeid, nome, pilightnome, versaopilight)values(now(), 3,"Weather Underground", "wunderground", "v5.0, v6.0, v7.0");
insert into deviceprotocol (created_at, protocoltypeid, nome, pilightnome, versaopilight)values(now(), 3,"Sunrise / Sunset", "sunriseset", "v5.0, v6.0, v7.0");
insert into deviceprotocol (created_at, protocoltypeid, nome, pilightnome, versaopilight)values(now(), 6,"CPU Temperature", "cpu_temp", "v5.0, v6.0, v7.0");
insert into deviceprotocol (created_at, protocoltypeid, nome, pilightnome, versaopilight)values(now(), 6,"GPIO Switch", "gpio_switch","v6.0, v7.0");
insert into deviceprotocol (created_at, protocoltypeid, nome, pilightnome, versaopilight)values(now(), 11,"D-IO", "dio_switch", "v5.0, v6.0, v7.0");
insert into deviceprotocol (created_at, protocoltypeid, nome, pilightnome, versaopilight)values(now(), 12,"Relay", "relay", "v5.0, v6.0, v7.0");

/*dispositivos de exemplo*/
insert into device (created_at, nome_pilight, descricao, estado, id_id, id_unit, nodepilightid, deviceprotocolid, devicegroupid, locked)values(now(), "p0_s02_quadro_01", "P0 Sala 02 - Quadro 01", "off", 2, 1, 1, 7, 1, 0);

/*"areas" ou views da web app (para modulo de manutencao)*/
insert into areamanutencao (created_at, estado, descricao, locked) values(now(), 1, "domotic", 0);
insert into areamanutencao (created_at, estado, descricao, locked) values(now(), 1, "tarefas", 0);
insert into areamanutencao (created_at, estado, descricao, locked) values(now(), 1, "chat", 0);
insert into areamanutencao (created_at, estado, descricao, locked) values(now(), 1, "energmon/monitenerg", 0);
insert into areamanutencao (created_at, estado, descricao, locked) values(now(), 1, "systemstate", 0);

