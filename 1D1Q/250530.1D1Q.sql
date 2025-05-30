-- 개발 부서 직원들의 현재 평균 연봉을 구하시오
-- (avg 함수 사용)
-- (departments 부서 번호와 dept_emp 부서번호와 조인)
-- (dept_emp 직원 번호와 salaries 직원번호를 조인)
-- (salary, to_date 이 9999로 시작)
-- (departments, dept_name 이 dev 로 시작)

select avg(s.salary) from salaries s 
join dept_emp de on s.emp_no = de.emp_no
join departments d on de.dept_no = d.dept_no
where d.dept_name like 'dev%'
and s.to_date like '9999%';