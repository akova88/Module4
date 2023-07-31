package com.cg.thithuchanh_md4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "videos")
public class Video extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",nullable = false )
    private String title;

    @Column(name = "description",nullable = false )
    private String description;

    @ManyToMany
    @JoinTable(
            name = "playlist_video",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    private List<Playlist> playlists;

    public VideoResDTO toVideoResDTO (List<PlaylistResDTO> playlists) {
        return new VideoResDTO()
                .setId(id)
                .setTitle(title)
                .setDescription(description)
                .setPlaylists(playlists);
    }
}
