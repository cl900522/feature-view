package acme.me.sso.action;

import acme.me.sso.rpc.ManagerServiceRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("auth")
public class AuthAction {
    @Autowired
    private ManagerServiceRpc managerServiceRpc;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap map) {
        Date date = new Date();
        map.addAttribute("vv", date.toString());
        return "index";
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    @ResponseBody
    public String listAll() {
        return managerServiceRpc.getAll();
    }
}

