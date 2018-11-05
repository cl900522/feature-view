package acme.me.user.manager.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.me.user.manager.dao.ManagerDao;
import acme.me.user.manager.entity.Manager;

@Service
public class ManagerService {

    @Autowired
    private ManagerDao managerDao;

    private static List<Manager> managers = Arrays.asList(new Manager("root", "root123", "Sichuan"), new Manager("admin", "admin123", "Anhui"));

    @PostConstruct
    public void init() {
        managerDao.save(managers);
    }

    public List<Manager> getAllManagers() {
        Iterable<Manager> findAll = managerDao.findAll();
        List<Manager> managers = new ArrayList<Manager>();
        Iterator<Manager> iterator = findAll.iterator();
        while (iterator.hasNext()) {
            Manager next = iterator.next();
            managers.add(next);
        }
        return managers;
    }

    public Manager getManagerByName(String name) {
        return managerDao.findOne(name);
    }

    public void deleteManager(String name) {
        managerDao.delete(name);

    }

    public void saveManager(Manager manager) {
        managerDao.save(manager);
    }

    public void updateManager(Manager manager) {
        managerDao.save(manager);
    }
}
