package mx.com.server.cae.support.utils;

import java.util.ArrayList;
import java.util.List;
import mx.com.server.cae.models.ERole;
import mx.com.server.cae.models.Role;
import mx.com.server.cae.models.User;
import mx.com.server.cae.services.MyUserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    public MyUserPrincipal localUser(User user) {
        return new MyUserPrincipal(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, buildSimpleGrantedAuthorities(user), user);
    }

    public static List<GrantedAuthority> buildSimpleGrantedAuthorities(User user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(roleNameToRoleNameString(role.getName())));
        }
        return grantedAuthorities;
    }

    public static String roleNameToRoleNameString(ERole eRole) {
        String r;
        switch (eRole) {
            case ROLE_GUEST:
                return r = "ROLE_GUEST";
            case ROLE_USER:
                return r = "ROLE_USER";
            case ROLE_ADMIN:
                return r = "ROLE_ADMIN";
            default:
                return r = "";
        }
    }

    public String userStatusToStatusString(Boolean status) {
        return (status == true ? "active" : "inactive");
    }

}
