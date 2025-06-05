-- create database mydb;
create table books (
	id int primary key,
    title varchar(100),
    category varchar(50),
    price int,
    stock int);
    
select * from books;

-- 테이블에 테스트 데이터를 삽입합니다. 아래 코드를 실행하여 8권의 책 데이터를 추가합니다.

INSERT INTO books (id, title, category, price, stock) VALUES
(1, '소설 A', 'Fiction', 35000, 10),
(2, '소설 B', 'Fiction', 30000, 5),
(3, '소설 C', 'Fiction', 25000, 8),
(4, '소설 D', 'Fiction', 18000, 12),
(5, '논픽션 E', 'Non-Fiction', 40000, 3),
(6, '논픽션 F', 'Non-Fiction', 22000, 7),
(7, '소설 G', 'Fiction', 28000, 9),
(8, '논픽션 H', 'Non-Fiction', 15000, 15);

select * from books
where category = 'Fiction'
and price >= 20000
order by price desc
limit 3;


CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    book_id INT,
    quantity INT,
    order_date DATE,
    FOREIGN KEY (book_id) REFERENCES books(id)
);
INSERT INTO orders (order_id, book_id, quantity, order_date) VALUES
(1, 1, 2, '2025-05-01'), -- 소설 A, 2권
(2, 2, 1, '2025-05-02'), -- 소설 B, 1권
(3, 3, 3, '2025-05-03'), -- 소설 C, 3권
(4, 5, 1, '2025-05-04'), -- 논픽션 E, 1권
(5, 7, 2, '2025-05-05'), -- 소설 G, 2권
(6, 6, 2, '2025-05-06'); -- 논픽션 F, 2권
select * from orders;

select b.category, sum(b.price*o.quantity) as sum
from books b
join orders o on o.book_id = b.id
group by category
order by category asc
limit 2;

select sum(price) as f_sum
from books
group by category;

-- 고객이 소설(Fiction) 카테고리의 책을 주문한 경우, where category = 'fiction'
-- 각 카테고리별 총 주문 금액을 계산하고, price * quantity


select b.category, sum(b.price*o.quantity) as total_price
from books b
join orders o on b.id = o.book_id
where b.category = 'fiction'
group by b.category
having sum(b.price*o.quantity) >= 50000
order by total_price desc
limit 2;

select b.title, sum(b.price*o.quantity) as total_price
from books b
join orders o on b.id = o.book_id
where b.category = 'fiction'
group by b.title
having total_price >= 50000
order by total_price desc
limit 2;