package co.simplon.evaluation.service;

import co.simplon.evaluation.entities.AppRole;
import co.simplon.evaluation.entities.AppUser;

public interface AccountService 
{
    public AppUser saveUser(String username,String password,String confirmedPassword);
    public AppRole save(AppRole role);
    public AppUser loadUserByUsername(String username);
    public void addRoleToUser(String username,String rolename);
}
