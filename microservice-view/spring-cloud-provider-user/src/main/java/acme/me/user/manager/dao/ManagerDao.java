package acme.me.user.manager.dao;

import org.springframework.data.repository.CrudRepository;

import acme.me.user.manager.entity.Manager;

public interface ManagerDao extends CrudRepository<Manager, String>{

}
