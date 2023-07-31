package com.cg.thithuchanh_md4.model;

//import com.cg.thithuchanh_md4.service.IVideoPlayListService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class VideoResDTO {

    private Long id;
    private String title;
    private String description;
    private List<PlaylistResDTO> playlists;
}
