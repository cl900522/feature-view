package acme.me.sso.action;

import acme.me.sso.rpc.ManagerServiceRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthAction {
    @Autowired
    private ManagerServiceRpc managerServiceRpc;

    @RequestMapping(value = "all", method = RequestMethod.GET)
    @ResponseBody
    public String listAll() {
        return managerServiceRpc.getAll();
    }
}

