package com.example.Instagramapp.dao;
import com.example.Instagramapp.model.User;
//domain companyName projectName packageName Class
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

}
