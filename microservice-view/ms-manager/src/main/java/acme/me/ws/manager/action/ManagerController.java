package acme.me.ws.manager.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import acme.me.ws.manager.entity.Manager;
import acme.me.ws.manager.service.ManagerService;

@RestController
public class ManagerController {
    @Autowired
    public ManagerService managerService;

    @RequestMapping(value = "/managers", method = RequestMethod.GET)
    @ResponseBody
    public List<Manager> getAll() {
        return managerService.getAllManagers();
    }

    @RequestMapping(value = "/managers/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Manager getByName(@PathVariable(value = "id") String name) {
        return managerService.getManagerByName(name);
    }

    @RequestMapping(value = "/managers", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody Manager manager) {
        managerService.saveManager(manager);
    }

    @RequestMapping(value = "/managers/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable(value = "id") String name) {
        managerService.deleteManager(name);
    }

    @RequestMapping(value = "/managers/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody Manager manager, @PathVariable(value = "id") String name) {
        managerService.updateManager(manager);
    }
}
