package acme.me.sso.rpc;

import org.springframework.stereotype.Component;

@Component
public class ManagerServiceRpcFallBackImpl implements  ManagerServiceRpc {
    @Override
    public String getAll() {
        return "Error";
    }
}
