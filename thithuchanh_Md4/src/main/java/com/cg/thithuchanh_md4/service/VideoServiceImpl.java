package com.cg.thithuchanh_md4.service;

import com.cg.thithuchanh_md4.model.Video;
import com.cg.thithuchanh_md4.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VideoServiceImpl implements IVideoService{
    @Autowired
    private VideoRepository videoRepository;
    @Override
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    @Override
    public Optional<Video> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Video save(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public void delete(Video video) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
