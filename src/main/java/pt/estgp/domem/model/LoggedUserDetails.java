package pt.estgp.domem.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoggedUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3751800833064812271L;
	private  User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return null;
	}

	@Override
	public String getPassword() {
		
		return null;
	}

	@Override
    public String getUsername() {
            if (this.user == null) {
                    return null;
            }
            return this.user.getUsername();
    }

	@Override
	public boolean isAccountNonExpired() {
		
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return false;
	}

	@Override
	public boolean isEnabled() {
		
		return false;
	}
   
	public User getUser() {
        return user;
	}
	
}