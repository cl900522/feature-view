package acme.me.sso.rpc;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "microservice-provider-user", fallback = ManagerServiceRpcFallBackImpl.class, primary = true)
public interface ManagerServiceRpc {
    @RequestMapping(value = "/managers", method = RequestMethod.GET)
    String getAll();

}
