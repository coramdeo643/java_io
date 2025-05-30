-- employees 테이블에서 
-- last_name이 L로 시작하고 
-- birth_date가 11월 13일인 
-- 직원의 수를 모두 count 해주세요

select count(*)
from employees 
where last_name like 'L%'
and birth_date like '%11_13';


-- employees 테이블에서
-- last_name이 E로 시작하고(like), 
-- 3번째 글자가 E인 직원을(예시 : erez)
-- last_name을 대문자로 바꾸어서(ucase) 
-- 중복없이(distinct) 출력해주세요
select distinct ucase(last_name)
from employees
where last_name like 'E_E%' ;
