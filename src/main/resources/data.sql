insert into member(id, member_name, password) values (1, 'loginName', 'loginPassword');

insert into menu(id, name, price) values (1, 'chicken', 20000);

insert into menu(id, name, price) values (2, 'pizza', 34000);

insert into menu(id, name, price) values (3, 'hamburger', 15000);

insert into coupon(id, name, issuable_date, usable_date, price, amount)
values (1, '페리카나 3000원 쿠폰', '2021-07-12T22:47:34', '2022-07-12T22:47:34', 3000, 100);

insert into coupon(id, name, issuable_date, usable_date, price, amount)
values (2, '교촌치킨 3000원 쿠폰', '2021-07-12T22:47:34', '2022-07-12T22:47:34', 3000, 100);

insert into coupon(id, name, issuable_date, usable_date, price, amount)
values (3, '미스터 피 3000원 쿠폰', '2021-07-12T22:47:34', '2022-07-12T22:47:34', 1000, 100);

insert into orders(id, total_price, total_discount_price, payment, status, member_id)
values (1, 54000, 2000, 1, 1, 1);

insert into orders(id, total_price, total_discount_price, payment, status, member_id)
values (2, 34000, 2000, 3, 1, 1);

insert into orders(id, total_price, total_discount_price, payment, status, member_id)
values (3, 15000, 1000, 3, 1, 1);

insert into orders(id, total_price, total_discount_price, payment, status, member_id)
values (4, 35000, 1000, 2, 2, 1);

insert into issued_coupon(id, coupon_code, status, coupon_id, member_id, order_id)
values (1, '1234', 2, 1, 1, null);

insert into issued_coupon(id, coupon_code, status, coupon_id, member_id, order_id)
values (2, '4321', 1, 2, null, null);

insert into issued_coupon(id, coupon_code, status, coupon_id, member_id, order_id)
values (3, '9876', 2, 3, 1, null);

insert into issued_coupon(id, coupon_code, status, coupon_id, member_id, order_id)
values (4, '1111', 2, 3, 1, 1);

insert into issued_coupon(id, coupon_code, status, coupon_id, member_id, order_id)
values (5, '2222', 2, 3, 1, 2);

insert into issued_coupon(id, coupon_code, status, coupon_id, member_id, order_id)
values (6, '3333', 2, 3, 1, 3);

insert into issued_coupon(id, coupon_code, status, coupon_id, member_id, order_id)
values (7, '4444', 2, 3, 1, 4);

insert into issued_coupon(id, coupon_code, status, coupon_id, member_id, order_id)
values (8, '5555', 2, 3, 1, 1);

insert into issued_coupon(id, coupon_code, status, coupon_id, member_id, order_id)
values (9, '6666', 2, 3, 1, 2);

insert into order_menu(id, order_id, menu_id) values (1, 1, 1);

insert into order_menu(id, order_id, menu_id) values (2, 1, 2);

insert into order_menu(id, order_id, menu_id) values (3, 2, 2);

insert into order_menu(id, order_id, menu_id) values (4, 3, 3);

insert into order_menu(id, order_id, menu_id) values (5, 4, 1);

insert into order_menu(id, order_id, menu_id) values (6, 4, 3);