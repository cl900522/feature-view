package acme.me.ws.manager.dao;

import org.springframework.data.repository.CrudRepository;

import acme.me.ws.manager.entity.Manager;

public interface ManagerDao extends CrudRepository<Manager, String>{

}
