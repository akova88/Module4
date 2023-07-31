package com.cg.thithuchanh_md4.api;

import com.cg.thithuchanh_md4.model.Playlist;
import com.cg.thithuchanh_md4.model.PlaylistResDTO;
import com.cg.thithuchanh_md4.model.Video;
import com.cg.thithuchanh_md4.model.VideoResDTO;
import com.cg.thithuchanh_md4.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoAPI {
    @Autowired
    private IVideoService videoService;

    @GetMapping
    public ResponseEntity<?> getAllVideos() {
        List<Video> videos = videoService.findAll();
        List<VideoResDTO> videoResDTOS = new ArrayList<>();

        for (Video item : videos) {
            List<Playlist> playlists = item.getPlaylists();
            List<PlaylistResDTO> playlistResDTOS = new ArrayList<>();
            for (Playlist playlist : playlists) {
                PlaylistResDTO playlistResDTO = playlist.toPlaylistResDTO();
                playlistResDTOS.add(playlistResDTO);
            }
            VideoResDTO videoResDTO = item.toVideoResDTO(playlistResDTOS);
            videoResDTOS.add(videoResDTO);
        }
        return new ResponseEntity<>(videoResDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> CreateVideo(@RequestBody VideoResDTO videoResDTO) {

            String title = videoResDTO.getTitle();
            String description = videoResDTO.getDescription();
            List<PlaylistResDTO> playlistResDTOS = videoResDTO.getPlaylists();
            List<Playlist> playlists = new ArrayList<>();
        for (PlaylistResDTO playlistResDTO : playlistResDTOS) {
            Playlist playlist = playlistResDTO.toPlaylist();
            playlists.add(playlist);
        }

        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setPlaylists(playlists);

        videoService.save(video);

        return new ResponseEntity<>(video, HttpStatus.OK);
    }
}
