package com.example.cs204database;

import com.example.cs204database.entity.Role;
import com.example.cs204database.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("Users not found!");
        }
        return user;
    }

    public Users findUserById(Integer userId){
        Optional<Users> userFormDb = usersRepository.findById(userId);
        return userFormDb.orElse(new Users());
    }

    public List<Users> allUsers(){
        return (List<Users>) usersRepository.findAll();
    }

    public boolean saveUser(Users user){
        if(usersRepository.findByUsername(user.getUsername()) !=null){
            return false;
        }
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return true;
    }
    public boolean deleteUser(Integer userId){
        if(usersRepository.findById(userId).isPresent()){
            usersRepository.deleteById(userId);
            return true;
        }else
            return false;
    }

    public List<Users> usersList(Integer idMin){
        return em.createQuery("SELECT u FROM Users u WHERE u.id> :paramId", Users.class)
                .setParameter("paramId",idMin)
                .getResultList();
    }

//    public List<Users> firstTenUsers(){
//        return em.createQuery("SELECT u FROM Users u LIMIT 10", Users.class).getResultList();
//    }
}
