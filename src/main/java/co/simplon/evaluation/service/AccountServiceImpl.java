package co.simplon.evaluation.service;

import co.simplon.evaluation.dao.AppRoleRepository;
import co.simplon.evaluation.dao.AppUserRepository;
import co.simplon.evaluation.entities.AppRole;
import co.simplon.evaluation.entities.AppUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService
{

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;

    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository) 
    {
    
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
  
    }

    @Override
    public AppUser saveUser(String username, String password, String confirmedPassword) 
    {
    
        AppUser  user=appUserRepository.findByUsername(username);
        
        if(user!=null) throw 
            new RuntimeException("User already exists");
        
        if(!password.equals(confirmedPassword)) throw 
            new RuntimeException("Please confirm your password");
            
        AppUser appUser=new AppUser();
        appUser.setUsername(username);
        appUser.setActived(true);
        appUser.setPassword(/*bCryptPasswordEncoder.encode(*/password);
        appUserRepository.save(appUser);
        addRoleToUser(username,"USER");
        
        return appUser;
        
    }

    @Override
    public AppRole save(AppRole role) 
    {
        return appRoleRepository.save(role);
    }

    @Override
    public AppUser loadUserByUsername(String username) 
    {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public void addRoleToUser(String username, String rolename) 
    {
    
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findByRoleName(rolename);
        appUser.getRoles().add(appRole);
        
    }
    
}
