package mx.com.server.cae.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import mx.com.server.cae.models.Role;
import mx.com.server.cae.support.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MyUserPrincipal extends User {

    private final mx.com.server.cae.models.User user;

    @Autowired
    private UserUtil userUtil;

    public MyUserPrincipal(final String username, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
            final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, final mx.com.server.cae.models.User user) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : this.user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(this.userUtil.roleNameToRoleNameString(role.getName())));
        }
        return grantedAuthorities;
    }

    public String getId() {
        return this.user.getId();
    }

    public String getName() {
        return this.user.getUsername();
    }

    public String getEmail() {
        return this.user.getEmail();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }

}
