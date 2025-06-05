select b.title, sum(b.price*o.quantity) as total_price
from books b
join orders o on b.id = o.book_id
where b.category = 'fiction'
group by b.title
having total_price >= 50000
order by total_price desc
limit 2;

-- 부서별 연봉 평균 순서대로 출력
-- 숫자 앞 달러 표시 추가
-- 소수점 제거
-- 상위 3개 부서만
||```sql
select d.dept_name, concat('$ ',round(avg(salary),0)) as avg_sal
from salaries s
join dept_emp de on s.emp_no = de.emp_no
join departments d on de.dept_no = d.dept_no
group by d.dept_name
order by avg_sal desc
limit 3;
```||