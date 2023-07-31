package com.cg.thithuchanh_md4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "playlists")
public class Playlist extends BaseEntity{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "name",nullable = false )
        private String name;

        @ManyToMany(mappedBy = "playlists")
        private List<Video> videos;

        public PlaylistResDTO toPlaylistResDTO (){
                return new PlaylistResDTO()
                        .setId(id)
                        .setName(name);
        }
}
