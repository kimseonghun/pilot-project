insert into member(id, member_name, password) values (1, 'loginName', 'loginPassword');

insert into coupon(id, name, issuable_date, usable_date, price, amount)
values (1, '페리카나 3000원 쿠폰', '2021-07-12T22:47:34.158', '2022-07-12T22:47:34.158', 3000, 100);

insert into issued_coupon(id, coupon_code, status, coupon_id, member_id)
values (1, '1234', 2, 1, 1);