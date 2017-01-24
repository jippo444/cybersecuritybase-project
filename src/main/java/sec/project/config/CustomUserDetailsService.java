package sec.project.config;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private Map<String, String> accountDetails;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // this data would typically be retrieved from a database
        this.accountDetails = new TreeMap<>();
        this.accountDetails.put("ted", "$2a$10$VQ7P0HwG3qY4t2WqRRjeXOhPbab3sS5QiFhmYqpit4oiuXaTYynPi");
        this.accountDetails.put("maxwell", "$2a$10$IVhshAjrfMFpOIvm3j.Gfums9DLjZ80g/4.rN3NqJT816dcqN969y");
//        System.out.println("password: senator -- encrypted: " + passwordEncoder.encode("senator"));
//        System.out.println("password: smart -- encrypted: " + passwordEncoder.encode("smart"));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!this.accountDetails.containsKey(username)) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        if(username.equals("maxwell")) {
            return new org.springframework.security.core.userdetails.User(
                    username,
                    this.accountDetails.get(username),
                    true,
                    true,
                    true,
                    true,
                    Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
        } else {
            return new org.springframework.security.core.userdetails.User(
                    username,
                    this.accountDetails.get(username),
                    true,
                    true,
                    true,
                    true,
                    Arrays.asList(new SimpleGrantedAuthority("USER")));
        }
    }
}
