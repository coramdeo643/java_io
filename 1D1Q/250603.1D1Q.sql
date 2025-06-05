-- 세일즈 부서 매니저의 평균 연봉 구하기
select avg(s.salary) from salaries s
join dept_manager dm on s.emp_no = dm.emp_no
join departments d on dm.dept_no = d.dept_no
where d.dept_name = 'sales';

select * from dept_manager;
select * from departments;
select * from employees;

-- production 부서 매니저의 평균 출생년도 구하기

select substring(round(avg(e.birth_date), -4),1,4) as avg_birth_year 
from employees e
join dept_manager dm on e.emp_no = dm.emp_no
join departments d on dm.dept_no = d.dept_no
where d.dept_name = 'production';
