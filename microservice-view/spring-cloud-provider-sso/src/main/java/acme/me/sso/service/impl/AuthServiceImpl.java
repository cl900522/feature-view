package acme.me.sso.service.impl;

import acme.me.sso.service.AuthService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public void login(String user, String name) {
        InstanceInfo nextServerFromEureka = eurekaClient.getNextServerFromEureka("", false);
    }
}
