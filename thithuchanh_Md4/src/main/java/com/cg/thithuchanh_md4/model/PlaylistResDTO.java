package com.cg.thithuchanh_md4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PlaylistResDTO {
    private Long id;
    private String name;

    public Playlist toPlaylist() {
        return new Playlist()
                .setId(id)
                .setName(name);
    }
}
