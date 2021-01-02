package com.example.favoriteservice.dao;

import com.example.favoriteservice.pojo.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteDao extends JpaRepository<Favorite, Integer> {
    List<Favorite> findAllByUserId(String userId);
}
