package com.cg.thithuchanh_md4.repository;

import com.cg.thithuchanh_md4.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

}
