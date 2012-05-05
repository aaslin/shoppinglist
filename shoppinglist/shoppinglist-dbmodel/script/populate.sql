insert into user (username, password) value('lars', 'abc123');
insert into user (username, password) value('linda', 'abc123');
insert into time_stamp(created) value(now());
insert into shopping_list (name, userID, time_stampID) value("Lars Shopping List", 1, 1);
insert into shopping_list (name, userID, time_stampID) value("Linda Shopping List", 2, 1);
insert into shopping_item(amount, name, shopping_listID, time_stampID) value("3", "Peppers", 1, 1);
insert into shopping_item(amount, name, shopping_listID, time_stampID) value("12", "Onion", 1, 1);
insert into shopping_item(amount, name, shopping_listID, time_stampID) value("2", "Tables", 2, 1);
insert into shopping_item(amount, name, shopping_listID, time_stampID) value("8", "Chairs", 2, 1);
