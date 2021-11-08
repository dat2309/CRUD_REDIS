package com.example.repository;

import com.example.model.Employee;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class EmployeeRepository {

    private HashOperations hashOperations;//crud hash
    private ListOperations listOperations;
    private SetOperations setOperations;



    private RedisTemplate redisTemplate;

    public EmployeeRepository(RedisTemplate redisTemplate) {
    	this.setOperations = redisTemplate.opsForSet();
    	this.listOperations = redisTemplate.opsForList();
        this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;

    }

    public void saveEmployee(Employee employee){

//        hashOperations.put("EMPLOYEE", employee.getId(), employee);
//    	listOperations.leftPush("EMPLOYEE", employee);
    	setOperations.add("EMPLOYEE", employee);
    }
    public Set<Employee> findAll(){

//        return hashOperations.values("EMPLOYEE");
//    	return listOperations.range("EMPLOYEE", 0, listOperations.size("EMPLOYEE"));
    	return setOperations.members("EMPLOYEE");
    }
    public Employee findById(Integer id){

//        return (Employee) hashOperations.get("EMPLOYEE", id);
//        List<Employee> list = listOperations.range("EMPLOYEE", 0, listOperations.size("EMPLOYEE"));
//		for (Employee employee : list) {
//			if(employee.getId() == id)
//				return employee;
//		}
    	Set<Employee> employees = setOperations.members("EMPLOYEE");
		for (Employee employee : employees) {
			if(employee.getId() == id)
				return employee;
		}
		return null;
//		return (Employee) setOperations.intersect("EMPLOYEE", id);
    	
    }

    public void update(Employee employee){
        saveEmployee(employee);
        // chưa fix đc 
    }
    public void delete(Integer id){
//        hashOperations.delete("EMPLOYEE", id);
//        listOperations.rightPopAndLeftPush("EMPLOYEE", id);
    	Employee employee = this.findById(id);
    	setOperations.remove("EMPLOYEE", employee);
    }
}
